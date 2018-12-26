package com.yiyiglobal.dp.controller;


import com.yiyiglobal.dp.domain.Category;
import com.yiyiglobal.dp.service.ICategoryService;
import com.yiyiglobal.dp.util.ValidateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/v1/category")
public class CategoryController extends BaseController{
    @Autowired
    ICategoryService categoryService;

    @ApiOperation(value="获取一级分类列表", notes="")
    @RequestMapping(value="/top", method=RequestMethod.GET)
    public Map<String, Object> top() throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        List<Category> list=categoryService.getTopCategory();
        map.put("top",list);
        return success(map);
    }

    @ApiOperation(value="根据一级分类的id获取子分类的列表", notes="")
    @ApiImplicitParam(name = "id", value = "一级分类ID", required = true,paramType = "query",dataType = "Integer")
    @RequestMapping(value="sons", method=RequestMethod.GET)
    public Map<String, Object> sons(@RequestParam(value = "id") Integer id)  throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Category> list=categoryService.getSonCategory(Integer.valueOf(id));
        map.put("son",list);
        return success(map);
    }


}
