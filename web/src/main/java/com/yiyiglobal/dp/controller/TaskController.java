package com.yiyiglobal.dp.controller;

import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.service.ITaskService;
import com.yiyiglobal.dp.service.IUserService;
import com.yiyiglobal.dp.util.ValidateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/v1/task")
public class TaskController extends BaseController{

    @Autowired
    IUserService userService;

    @Autowired
    ITaskService taskService;

    @ApiOperation(value="任务中心首页信息", notes="")
    @RequestMapping(value="/", method=RequestMethod.GET)
    public Map<String, Object> getHome(@RequestParam String accessToken) throws Exception {
        Integer  userId =null;
        if(!ValidateUtil.isEmpty(accessToken)) {
            User user = userService.getUserByAccessToken(accessToken);
            if(user!=null){
                userId =user.getId();
            }
        }
        Map<String, Object>  map =  taskService.getTaskHomeData(userId);
        return success(map);
    }

    @ApiOperation(value="签到", notes="")
    @RequestMapping(value="/sign-in", method=RequestMethod.POST)
    public Map<String, Object> signIn(@RequestParam String accessToken) throws Exception {
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
        if(taskService.signIn(user.getId())){
            return  success();
        }else{
            throw new BusinessException(ResultEnum.SYSTEM_ERROR);
        }
    }
}
