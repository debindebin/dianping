package com.yiyiglobal.dp.controller;


import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.domain.IncomeDetail;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.service.IUserService;
import com.yiyiglobal.dp.service.IWalletService;
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
@RequestMapping(value="/v1/wallet")
public class WalletController extends BaseController{

    @Autowired
    IWalletService   walletService;

    @Autowired
    IUserService userService;

    @ApiOperation(value="获取代币收入排行总榜", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "offset", value = "起始页", required = true, dataType  = "Int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页幅", required = true, dataType = "Int",paramType = "query")
    })
    @RequestMapping(value="/token/ranks", method=RequestMethod.GET)
    public Map<String, Object> getTokenRanks(@Valid @ApiIgnore  PageEntity pageEntity) throws Exception {
        Map<String, Object>  map = walletService.getTokenRanks(pageEntity);
        return success(map);
    }

    @ApiOperation(value="代币的账单明细", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "offset", value = "起始页", required = true, dataType  = "Int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页幅", required = true, dataType = "Int",paramType = "query")
    })
    @RequestMapping(value="/token/my-bill", method=RequestMethod.GET)
    public Map<String, Object> getMyTokenBills(@Valid @ApiIgnore  PageEntity<IncomeDetail> pageEntity,@RequestParam String accessToken) throws Exception {
        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken)) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }
        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }
        IncomeDetail detail = new IncomeDetail();
        detail.setUserId(user.getId());
        detail.setIncomeType(Cons.IncomeDetailType.token);
        pageEntity.setParams(detail);
        PageResult<IncomeDetail> result= walletService.getMyIncomeDetailList(pageEntity);
        Map<String, Object>  map = new HashMap<>();
        map.put("data",result);
        return success(map);
    }

    @ApiOperation(value="获取贡献值的账单明细", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "offset", value = "起始页", required = true, dataType  = "Int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页幅", required = true, dataType = "Int",paramType = "query")
    })
    @RequestMapping(value="/contribution/my-bill", method=RequestMethod.GET)
    public Map<String, Object> getMyContributionBills(@Valid @ApiIgnore PageEntity<IncomeDetail> pageEntity, @RequestParam String accessToken) throws Exception {
        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken)) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }
        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }
        IncomeDetail detail = new IncomeDetail();
        detail.setUserId(user.getId());
        detail.setIncomeType(Cons.IncomeDetailType.contribution);
        pageEntity.setParams(detail);
        PageResult<IncomeDetail> result= walletService.getMyIncomeDetailList(pageEntity);
        Map<String, Object>  map = new HashMap<>();
        map.put("data",result);
        return success(map);
    }


}
