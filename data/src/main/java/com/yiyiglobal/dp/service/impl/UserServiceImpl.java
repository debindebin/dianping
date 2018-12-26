package com.yiyiglobal.dp.service.impl;

import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.common.NotifyCons;
import com.yiyiglobal.dp.common.RedisCons;
import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.domain.Notification;
import com.yiyiglobal.dp.dto.user.InviteUserDto;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.mapper.UserMapper;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.service.IUserService;
import com.yiyiglobal.dp.service.IWalletService;
import com.yiyiglobal.dp.util.Base64;
import com.yiyiglobal.dp.util.StringUtil;
import com.yiyiglobal.dp.util.ValidateUtil;
import com.yiyiglobal.dp.util.push.NotificationTypeEnum;
import com.yiyiglobal.dp.util.redis.service.ObjectRedisService;
import com.yiyiglobal.dp.vo.UserVo;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserMapper userMapper;

    @Autowired
    private ObjectRedisService objectRedisService;

    @Autowired
    private IWalletService  walletService;

    @Value("${wx.mp.appID}")
    private String wxMpAppId;

    @Value("${wx.mp.appSecret}")
    private String wxMpAppSecret;

    @Value("${qiniu.image.prefix}")
    private String qiniuImagePrefix;

    public User getUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据手机号获取用户
     * @param mobile
     * @return
     */
    public User getUserByMobile(String mobile){
        return userMapper.getUserByMobile(mobile);
    }

    /**
     * 根据授权获取用户基本信息
     * @param accessToken
     * @return
     */
    public User getUserByAccessToken(String accessToken){
        if (ValidateUtil.isEmpty(accessToken)) return null;

        //解码后的accessToken
        String decodeToken = null;
        try {
            decodeToken = new String(Base64.decode(accessToken.getBytes(), 8));
        } catch (Exception e) {
            logger.info(e.toString());
            return null;
        }

        if(!ValidateUtil.isEmpty(decodeToken)){
            String uid = decodeToken.substring(decodeToken.indexOf("_") + 1);
            Map<String,Object> redisUser = (Map<String,Object>)objectRedisService.get(RedisCons.USER_+uid);

            if(redisUser != null && redisUser.size()>0){
                String sessionAccessToken = (String) redisUser.get("accessToken");
                if(sessionAccessToken.equals(decodeToken)) {
                    User user = (User)redisUser.get("user");

                    //重新设置redis user过期时间
                    objectRedisService.set(RedisCons.USER_+uid, redisUser, (long)Cons.SESSION_EXPIRE_SECONDS);
                    return user;
                }
            }
        }

        return null;
    }

    /**
     * 注册
     * @param user
     * @return
     */
    public User register(User user){
        User inviteUser = null;
        if(user.getInviteCode() != null){
            inviteUser=userMapper.getUserBySelfInviteCode(user.getInviteCode());
            if(inviteUser!=null){
                inviteUser.setInviteNum(inviteUser.getInviteNum()+1);
                userMapper.updateByPrimaryKey(inviteUser);
            }
        }
        else user.setInviteCode(null);

        int userId = ((Long)objectRedisService.hmIncrement(RedisCons.AUTO_INCREMENT, RedisCons.AutoIncrement.USER_ID)).intValue();
        user.setId(userId);

        String salt = UUID.randomUUID().toString();
        Date creTime = new Date();
        user.setSalt(salt);

        user.setNickname("u-"+userId);

        user.setFollowingNum(0);
        user.setFollowNum(0);
        user.setClickNum(0);
        user.setInviteNum(0);
        user.setContributionNum(0);
        user.setIsTestUser(0);
        user.setTokenSum(new BigDecimal(0));
        user.setTokenRemain(new BigDecimal(0));
        user.setIsTestUser(0);
        user.setActivateFlag(Cons.AccountStatus.ACTIVATED);
        user.setSex(Cons.Sex.UNKNOWN);
        user.setIsAllowMutilogin(1);

        user.setCreTime(creTime);

        user.setSelfInviteCode(StringUtil.parseRadix36(userId));

        Integer result = userMapper.insert(user);
        if(result >0 &&inviteUser!=null){
            Notification noti = new Notification(inviteUser.getId(), NotificationTypeEnum.CONTRIBUTION.getTitle(),
                    NotifyCons.contributionContent,NotificationTypeEnum.CONTRIBUTION.getType(),inviteUser.getId()+"",2,new Date(),0);
            walletService.addContributionByTask(Cons.TaskId.Harvest_Support,inviteUser.getId(),noti);
        }

        return userMapper.getUserByMobile(user.getMobile());
    }

    public void update(User user){
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 获取推荐的昵称
     * @param nickname
     * @return
     */
    public String getNickname(String nickname){
        List<String> users = userMapper.getUserByNickname(nickname);

        if(!users.contains(nickname)) return nickname;
        else {
            Boolean flag = true;	//循环标记
            int i = 1;				//用户id角标
            while (flag){
                nickname = nickname +i;
                if(users.contains(nickname)) i++;
                else {
                    flag = false;
                }
            }
            return nickname;
        }
    }

    /**
     * 获取被邀请用户
     * @param inviteCode
     * @return
     */
    public List<InviteUserDto> getInviteUsers(String inviteCode){
        List<InviteUserDto> users = userMapper.getUsersByInviteCode(inviteCode);
        return users;
    }

    /**
     * 允许H5和app同时登录，如果user.isAllowMutilogin = 1则允许多个app同时登录
     * @param user
     * @param deviceId
     * @return
     */
    public Map<String, Object> generateAccessToken(User user,String deviceId){
        Map<String, Object> map = new HashMap<String, Object>();

        String accessToken;

        Map<String,String> redisUser = (Map<String,String>)objectRedisService.get(RedisCons.USER_+user.getId());
        //如果采用H5登录，或者允许用户在多个设备同时登录，则获取原授权
        if(redisUser != null && redisUser.size() !=0 &&
                (ValidateUtil.isEmpty(deviceId) || user.getIsAllowMutilogin() ==1)){
            accessToken = redisUser.get("accessToken");
        }
        //更新上一次登录的设备等各种信息
        else{
            accessToken = StringUtil.getRandStr(6, false) + "-"+ (System.currentTimeMillis() + 12 * 60 * 60 * 1000) + "_" + user.getId();

        }

        Map<String,Object> hm = new HashMap<String,Object>();
        hm.put("accessToken", accessToken);
        hm.put("deviceId", deviceId);
        hm.put("user", user);
        objectRedisService.set(RedisCons.USER_+user.getId(), hm, (long)Cons.SESSION_EXPIRE_SECONDS);

        map.put("accessToken", Base64.encodeToString(accessToken.getBytes(),11));
        user.setProfileImageUrl(qiniuImagePrefix + user.getProfileImageUrl());

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

        return map;
    }


    /**
     * 1 -验证成功,不等于1时返回错误码
     * @param mobile
     * @param countryMobileCode
     * @return
     */
    public String validateCode(String mobile , String countryMobileCode, String verifyCode){

        //万能验证码直接登录成功
        if(objectRedisService.hmGet(RedisCons.CONFIG,RedisCons.Config.VERIFYCODE).equals(verifyCode)) return "1";

        Map<String,Object> redisVerifyCode = (Map<String,Object>)objectRedisService.get(RedisCons.VERIFYCODE_ + countryMobileCode + mobile);

        //短信验证码已过期：未获取或者超时
        if(redisVerifyCode == null || redisVerifyCode.size() == 0) {
            throw new BusinessException(ResultEnum.VERIFYCODE_EXPIRED);
        }

        int verifyCount = Integer.valueOf(redisVerifyCode.get("verifyCount").toString()) ;
        //验证码输入次数过多（超5次），已失效
        if(verifyCount >= Cons.VERIFYCODE_FAIL_COUNT) {
            throw new BusinessException(ResultEnum.VERIFYCODE_CHECK_TOO_MUCH);
        }

        String vc = (String)redisVerifyCode.get("verifyCode");
        //短信验证码不正确
        if(!vc.equals(verifyCode)) {
            verifyCount++;
            objectRedisService.hmSet(RedisCons.VERIFYCODE_ + countryMobileCode + mobile, "verifyCount", verifyCount);
            throw new BusinessException(ResultEnum.VERIFYCODE_NOT_CORRECT);
        }
        //登录校验通过，设置redis验证码过期
        else {
            objectRedisService.remove(RedisCons.VERIFYCODE_+countryMobileCode+mobile);
        }

        return "1";
    }

    public void updateToken(User user){
        Map<String,Object> redisUser = (Map<String,Object>)objectRedisService.get(RedisCons.USER_+user.getId());
        redisUser.put("user", user);
        objectRedisService.set(RedisCons.USER_+user.getId(), redisUser, (long)Cons.SESSION_EXPIRE_SECONDS);
    }

}
