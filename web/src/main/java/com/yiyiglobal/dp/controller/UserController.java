package com.yiyiglobal.dp.controller;

import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.common.RedisCons;
import com.yiyiglobal.dp.common.SMSCons;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.dto.user.InviteUserDto;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.service.IUserService;
import com.yiyiglobal.dp.util.TwoDimensionCode;
import com.yiyiglobal.dp.util.ValidateUtil;
import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
import com.yiyiglobal.dp.util.third.SMSService;
import com.yiyiglobal.dp.vo.UserVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/v1/user")
public class UserController extends BaseController{

    protected static final Logger logger = Logger.getLogger("infoLog");

    @Autowired
    IUserService userService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private ObjectRedisService objectRedisService;
    @Value("${h5.url}")
    private String h5Url;

    @ApiOperation(value="获取短信验证码，测试为：8888", notes="")
    @RequestMapping(value="/verifycode/get", method=RequestMethod.GET)
    public Map<String, Object> getVerifyCode(@RequestParam(required = true) String mobile,
                                             @RequestParam(required = false) String countryMobileCode
                                             ) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        //缺少必选参数
        if(ValidateUtil.isEmpty(mobile)) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }

        //默认国家区号为中国
        if((!ValidateUtil.isEmpty(countryMobileCode) && "+86".equals(countryMobileCode)) || ValidateUtil.isEmpty(countryMobileCode)){
            countryMobileCode = "86";
        }

        //手机号格式不正确
        if("86".equals(countryMobileCode) && !ValidateUtil.isMobile(mobile,true)){
            throw new BusinessException(ResultEnum.MOBILE_INVALID);
        }

        User user = userService.getUserByMobile(mobile);
        //用户因为违规操作，已被锁定
        if(user!= null && user.getActivateFlag().equals(Cons.AccountStatus.UNACTIVATED)){
            throw new BusinessException(ResultEnum.USER_LOCK);
        }

        Map<String, Object> oldRedisVerifyCode = (Map<String, Object>)objectRedisService.get(RedisCons.VERIFYCODE_ + countryMobileCode + mobile);
        if(oldRedisVerifyCode != null && oldRedisVerifyCode.size() != 0){
            long lastSendTime = (long)oldRedisVerifyCode.get("sendTime");
            long delay = (System.currentTimeMillis() - lastSendTime) / 1000; // 秒
            if (delay < 60){
                // 小于60s，验证码发送过于频繁
                throw new BusinessException(ResultEnum.VERIFYCODE_TOO_FREQUENCY);
            }
        }

//		String verifyCode = StringUtil.getRandStr(4, true);
        String verifyCode = "8888";

        long sendTime = System.currentTimeMillis();

        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("verifyCode", verifyCode);
        hm.put("verifyCount", 0);
        hm.put("sendTime", sendTime);
        objectRedisService.set(RedisCons.VERIFYCODE_ + countryMobileCode + mobile, hm, Cons.VERIFYCODE_EXPIRE_SECONDS);

        String smsContent = SMSCons.getVerifyCodeSMS(verifyCode);
//		String result = smsService.send(countryMobileCode,mobile, smsContent);
//		//验证码发送失败
//		if ("-1".equals(result)){
//			return error(ErrorCodeCons.VERIFYCODE_SEND_FAIL);
//		}

        return success(map);
    }

    @ApiOperation(value="短信验证码登录", notes="")
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public Map<String, Object> login(@RequestParam(required = false) String countryMobileCode,  //国家区号
                                     @RequestParam(required = true) String mobile,              //手机号
                                     @RequestParam(required = true) String verifyCode,          //短信验证码
                                     @RequestParam(required = false) String inviteCode,         //短信验证码
                                     @RequestParam(required = false) String deviceId            //设备id
                                    ) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        //默认国家区号为中国
        if((!ValidateUtil.isEmpty(countryMobileCode) && "+86".equals(countryMobileCode)) || ValidateUtil.isEmpty(countryMobileCode)){
            countryMobileCode = "86";
        }

        //手机号不可以为空
        if(ValidateUtil.isEmpty(mobile) ) {
            throw new BusinessException(ResultEnum.MOBILE_NULL);
        }

        //验证码不可以为空
        if(ValidateUtil.isEmpty(verifyCode)) {
            throw new BusinessException(ResultEnum.VERIFYCODE_NULL);
        }

        //手机号格式不正确
        if("86".equals(countryMobileCode) && !ValidateUtil.isMobile(mobile,true)){
            throw new BusinessException(ResultEnum.MOBILE_INVALID);
        }

        //验证码格式不正确
        if(!ValidateUtil.validateLength(verifyCode,4,4)){
            throw new BusinessException(ResultEnum.VERIFYCODE_INVALID);
        }

        //验证短信验证码，1-验证成功，其他值返回错误代码
        String result = userService.validateCode(mobile, countryMobileCode, verifyCode);
        if(!"1".equals(result))
            throw new BusinessException(ResultEnum.SYSTEM_ERROR);

        User user = userService.getUserByMobile(mobile);

        //如果用户不存在，则注册新用户
        if(user == null) {
            user = new User();
            user.setMobile(mobile);
            user.setCountryMobileCode(countryMobileCode);
            if(!ValidateUtil.isEmpty(inviteCode)){
                user.setInviteCode(inviteCode);
            }
            user = userService.register(user);
        }

        //用户因为违规操作，已被锁定
        if(user.getActivateFlag() == Cons.AccountStatus.UNACTIVATED) {
            throw new BusinessException(ResultEnum.USER_LOCK);
        }

        //设置上次登录时间
        user.setLoginTime(new Date());
        userService.update(user);

        //设置loginTime
        map = userService.generateAccessToken(user,deviceId);

        return success(map);
    }

    @ApiOperation(value="支持微博、QQ、微信、微信公众号登录，新用户需绑定手机号（ /u/loginByCode 配合使用", notes="")
    @RequestMapping(value="/login-by-Social", method=RequestMethod.GET)
    public Map<String, Object> loginBySocialId(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        return success(map);
    }

    @ApiOperation(value="退出", notes="")
    @RequestMapping(value="/logout", method=RequestMethod.POST)
    public Map<String, Object> logout(@RequestParam String accessToken) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken)) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }

        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }

        objectRedisService.remove(RedisCons.USER_+user.getId());

        return success(map);
    }

    @ApiOperation(value="更新用户坐标", notes="")
    @RequestMapping(value="/location/update", method=RequestMethod.POST)
    public Map<String, Object> updateLocation(@RequestParam(required = true) String accessToken,            //手机号
                                              @RequestParam(required = true) Double latitude,            //手机号
                                              @RequestParam(required = true) Double longitude            //手机号
                                              ) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken) || latitude == 0 || longitude == 0 ) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }

        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }

        User updateUser = userService.getUserById(user.getId());

        updateUser.setLatitude(latitude);
        updateUser.setLongitude(longitude);
        updateUser.setLocationTime(new Date());

        userService.update(updateUser);

//        userService.update(updateUser);

        return success(map);
    }

    @ApiOperation(value="获取用户个人主页信息", notes="")
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public Map<String, Object> profile() throws Exception {
        return success();
    }

    @ApiOperation(value="根据授权获取用户基本信息", notes="")
    @RequestMapping(value="/basic-info", method=RequestMethod.GET)
    public Map<String, Object> getUserByAccessToken(@RequestParam(required = true) String accessToken) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken)) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }

        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }

        //把user对象去掉部分无用字段并显示给用户
        UserVo uv = new UserVo();
        try {
            BeanUtils.copyProperties(uv,user);
        } catch (IllegalAccessException e) {
            logger.error("Exception{}",e);
        } catch (InvocationTargetException e) {
            logger.error("Exception{}",e);
        }
        map.put("user", uv);

        return success(map);
    }

    @ApiOperation(value="更新用户基本资料（昵称、性别、生日、头像等）", notes="")
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public Map<String, Object> update(@RequestParam(required = true) String accessToken,        //授权
                                      @RequestParam(required = false) String nickname,          //昵称
                                      @RequestParam(required = false) Integer sex,              //1-男；2-女
                                      @RequestParam(required = false) String profileImageUrl    //用户头像

                                      ) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken) ||
                ( ValidateUtil.isEmpty(nickname) && (sex == null ||sex == 0)&& ValidateUtil.isEmpty(profileImageUrl)) ) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }

        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }

        User updateUser = userService.getUserById(user.getId());

        if(!ValidateUtil.isEmpty(nickname)) {
            //昵称格式不正确，"u-"开头是用户默认的昵称
            if(!ValidateUtil.isNickname(nickname) || nickname.startsWith("u-"))
                throw new BusinessException(ResultEnum.NICKNAME_INVALID);

            String newNickname = userService.getNickname(nickname);
            //昵称已存在
            if(newNickname.equals(nickname))
                updateUser.setNickname(nickname);
            else
                throw new BusinessException(ResultEnum.NICKNAME_EXIST);
        }

        //性别
        if(sex != null) {
            updateUser.setSex(sex);
        }

        //头像
        if(!ValidateUtil.isEmpty(profileImageUrl)) {
            updateUser.setProfileImageUrl(profileImageUrl);
        }

        userService.update(updateUser);

        //重置token
        userService.updateToken(updateUser);

        return success(map);
    }

    @ApiOperation(value="获取邀请好友页面明细", notes="")
    @RequestMapping(value="/invite", method=RequestMethod.GET)
    public Map<String, Object> invite(@RequestParam(required = true) String accessToken) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();

        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken) ) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }

        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }

        map.put("selfInviteCode",user.getSelfInviteCode());
        String url = h5Url + Cons.H5RegisterPage + user.getSelfInviteCode();

        // 二维码
        String image = TwoDimensionCode.imageToBase64(url);
        map.put("image",image);
        List<InviteUserDto> users = userService.getInviteUsers(user.getSelfInviteCode());
        map.put("users",users);

        return success(map);
    }


}
