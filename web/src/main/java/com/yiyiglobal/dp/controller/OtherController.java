package com.yiyiglobal.dp.controller;

import com.yiyiglobal.dp.domain.Category;
import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.service.IOtherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/v1/other")
public class OtherController extends BaseController{
    @Autowired
    IOtherService otherService;

    @ApiOperation(value="搜索首页获取热门标签", notes="")
    @RequestMapping(value="/tags/hot", method=RequestMethod.GET)
    public Map<String, Object> getHotTags() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list=otherService.getHotTags();
        map.put("tags",list);
        return success(map);
    }
}
