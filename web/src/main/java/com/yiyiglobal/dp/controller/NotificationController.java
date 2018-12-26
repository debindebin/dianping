package com.yiyiglobal.dp.controller;

import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.domain.Notification;
import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.service.ICategoryService;
import com.yiyiglobal.dp.service.INotificationService;
import com.yiyiglobal.dp.service.IUserService;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;
import com.yiyiglobal.dp.util.ValidateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/v1/notification")
public class NotificationController extends BaseController{
    @Autowired
    INotificationService notificationService;
    @Autowired
    IUserService userService;

    @ApiOperation(value="获取通知", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "offset", value = "起始页", required = true, dataType  = "Int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页幅", required = true, dataType = "Int",paramType = "query")
    })
    @RequestMapping(value="/", method=RequestMethod.GET)
    public Map<String, Object> getNotifications(@ApiIgnore @Valid PageEntity<Integer> pageEntity, @RequestParam String accessToken) throws Exception {

        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken) ) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }

        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }
        pageEntity.setParams(user.getId());
        PageResult<Notification> result = notificationService.getNotifications(pageEntity);
        Map<String, Object>  map = new HashMap<>();
        map.put("data",result);
        return success(map);
    }


}
