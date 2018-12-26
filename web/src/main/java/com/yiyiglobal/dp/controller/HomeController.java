package com.yiyiglobal.dp.controller;

import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.service.IHomeService;
import com.yiyiglobal.dp.service.IUserService;
import com.yiyiglobal.dp.util.ValidateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value="/v1/home")
public class HomeController extends BaseController{

    @Autowired
    IHomeService  homeService;
    @Autowired
    IUserService userService;

    @ApiOperation(value="获取首页数据", notes="")
    @RequestMapping(value="/", method=RequestMethod.GET)
    public Map<String, Object> getHome(@RequestParam(required = false) String accessToken,
                                       @RequestParam(required = false) Integer cityId) throws Exception {
        Integer  userId =null;
        if(!ValidateUtil.isEmpty(accessToken)) {
            User user = userService.getUserByAccessToken(accessToken);
            if(user!=null){
                userId =user.getId();
            }
        }
        Map<String, Object>  map =  homeService.getHomeData(userId,cityId);
        return success(map);
    }


}
