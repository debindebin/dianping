package com.yiyiglobal.dp.controller;

import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.domain.ResReview;
import com.yiyiglobal.dp.domain.ReviewReply;
import com.yiyiglobal.dp.domain.User;
import com.yiyiglobal.dp.dto.review.ReviewForListDto;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.service.IReviewService;
import com.yiyiglobal.dp.service.IUserService;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;
import com.yiyiglobal.dp.util.ValidateUtil;
import com.yiyiglobal.dp.vo.review.ReplyForSuppotVo;
import com.yiyiglobal.dp.vo.review.ReviewForCreVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/v1/review")
public class ReviewController extends BaseController{

    @Autowired
    IUserService userService;
    @Autowired
    IReviewService  reviewService;

    @ApiOperation(value="发布商户点评", notes="")
    @RequestMapping(value="/create", method=RequestMethod.POST,consumes= "application/json;charset=utf-8")
    public Map<String, Object> create(@Valid @RequestBody ReviewForCreVo reviewVo, @RequestParam String accessToken) throws Exception {
        ResReview  review =new ResReview();
        BeanUtils.copyProperties(reviewVo,review);
        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken)) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }
        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }
        review.setUserId(user.getId());
        Integer result = reviewService.addReview(review,reviewVo);
        if(result>0){
            Map<String, Object>  map = new HashMap<>();
            map.put("reviewId",result);
            return success(map);
        }else{
            throw new BusinessException(ResultEnum.SYSTEM_ERROR);
        }
    }

    @ApiOperation(value="支持点评/不支持点评", notes="")
    @RequestMapping(value="/support", method=RequestMethod.POST)
    public Map<String, Object> support(@Valid @RequestBody ReplyForSuppotVo replyVo,@RequestParam String accessToken) throws Exception {
        ReviewReply reply =new ReviewReply();
        BeanUtils.copyProperties(replyVo,reply);
        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken)) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }
        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }
        reply.setUserId(user.getId());
        if(reviewService.support(reply)){
            return success();
        }else {
            throw new BusinessException(ResultEnum.SYSTEM_ERROR);
        }

    }

    @ApiOperation(value="我发布的点评", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "offset", value = "起始页", required = true, dataType  = "Int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页幅", required = true, dataType = "Int",paramType = "query")
    })
    @RequestMapping(value="/my", method=RequestMethod.GET)
    public Map<String, Object> my(@Valid @ApiIgnore PageEntity<ResReview> pageEntity, @RequestParam String accessToken) throws Exception {
        //缺少必选参数
        if(ValidateUtil.isEmpty(accessToken)) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }
        User user = userService.getUserByAccessToken(accessToken);
        //授权不合法或者已过期
        if(user == null){
            throw new BusinessException(ResultEnum.ACCESSTOKEN_INVALID);
        }
        ResReview review =new ResReview();
        review.setUserId(user.getId());
        pageEntity.setParams(review);
        PageResult<ReviewForListDto>  result = reviewService.getResReviewList(pageEntity,user.getId());
        Map<String, Object>  map = new HashMap<>();
        map.put("data",result);
        return success(map);
    }

    @ApiOperation(value="某个资源的点评", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "offset", value = "起始页", required = true, dataType  = "Int",paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页幅", required = true, dataType = "Int",paramType = "query")
    })
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public Map<String, Object> list(@Valid @ApiIgnore PageEntity<ResReview> pageEntity,
                                    @NotNull(message = "resId必须非空") @Min(value = 0,message = "resId必须大于0") @RequestParam Integer resId,
                                    @RequestParam(required = false) String accessToken) throws Exception {
        Integer loginUserId =0;
        if(!ValidateUtil.isEmpty(accessToken)) {
            User user = userService.getUserByAccessToken(accessToken);
            //授权不合法或者已过期
            if(user != null){
                loginUserId = user.getId();
            }
        }
        ResReview review =new ResReview();
        review.setResId(resId);
        pageEntity.setParams(review);
        PageResult<ReviewForListDto>  result = reviewService.getResReviewList(pageEntity,loginUserId);
        Map<String, Object>  map = new HashMap<>();
        map.put("data",result);
        return success(map);
    }
}
