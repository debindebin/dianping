package com.yiyiglobal.dp.controller;

import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.service.IResService;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;
import com.yiyiglobal.dp.vo.res.ResForListVo;
import com.yiyiglobal.dp.vo.res.ResForSearchVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping(value="/v1/res")
public class ResController extends BaseController{

    @Autowired
    IResService  resService;


    @ApiOperation(value="根据分类、排序和关键词获取商家列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "offset", value = "起始页", required = true, dataType  = "Int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页幅", required = true, dataType = "Int",paramType = "query"),
            @ApiImplicitParam(name = "categoryId", value = "分类", dataType = "Int",paramType = "query"),
            @ApiImplicitParam(name = "firstCategoryId", value = "一级分类", dataType = "Int",paramType = "query"),
            @ApiImplicitParam(name = "resName", value = "资源名称", dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "cityId", value = "城市ID", dataType = "Int",paramType = "query"),
            @ApiImplicitParam(name = "areaId", value = "区ID", dataType = "Int",paramType = "query"),
            @ApiImplicitParam(name = "regionId", value = "商圈ID", dataType = "Int",paramType = "query"),
            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "Double",paramType = "query"),
            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "Double",paramType = "query"),
            @ApiImplicitParam(name = "orderColumn", value = "排序字段", dataType = "String",paramType = "query")
    })
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public Map<String, Object> list(@ApiIgnore @Valid PageEntity<ResForSearchVo> pageEntity, @ApiIgnore ResForSearchVo res) throws Exception {
        pageEntity.setParams(res);
        PageResult<ResForListVo>  result = resService.getResList(pageEntity);
        Map<String, Object>  map = new HashMap<>();
        map.put("data",result);
        return success(map);
    }

    @ApiOperation(value="获取资源详情", notes="")
    @RequestMapping(value="/detail", method=RequestMethod.GET)
    public Map<String, Object> detail(@RequestParam(value = "id") Integer id ) throws Exception {
        if(id==null){
            throw new BusinessException(ResultEnum.PARAM_INVALID);
        }
        Res  res = resService.getResDetail(id);
        Map<String, Object>  map = new HashMap<>();
        map.put("data",res);
        return success(map);
    }

    @ApiOperation(value="获取联系方式", notes="")
    @RequestMapping(value="/contacts", method=RequestMethod.GET)
    public Map<String, Object> contacts(@NotNull(message = "id必须非空") @Min(value = 0,message = "id必须大于0")@RequestParam(value = "id")Integer id) throws Exception {
        List<String> list = resService.getContacts(id);
        Map<String, Object>  map = new HashMap<>();
        map.put("data",list);
        return success(map);
    }


}
