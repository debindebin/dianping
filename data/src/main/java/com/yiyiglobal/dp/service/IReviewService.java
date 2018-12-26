package com.yiyiglobal.dp.service;


import com.yiyiglobal.dp.domain.Res;
import com.yiyiglobal.dp.domain.ResReview;
import com.yiyiglobal.dp.domain.ReviewReply;
import com.yiyiglobal.dp.dto.review.ReviewForListDto;
import com.yiyiglobal.dp.util.PageEntity;
import com.yiyiglobal.dp.util.PageResult;
import com.yiyiglobal.dp.vo.review.ReviewForCreVo;

import java.util.List;

public interface IReviewService {

    Integer  addReview(ResReview  review, ReviewForCreVo reviewVo);

    PageResult<ReviewForListDto> getResReviewList(PageEntity<ResReview> pageEntity,Integer loginUserId);

    boolean support(ReviewReply reply);

}
