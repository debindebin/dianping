package com.yiyiglobal.dp.vo.review;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class ReplyForSuppotVo implements Serializable {

    @NotNull(message = "评论ID为空")
    @Min(value = 0,message = "评论ID要大于0")
    private Integer reviewId;

    @NotNull(message = "支持类型为空")
    @Min(value = 0,message = "支持类型为0至2")
    @Max(value = 2,message = "支持类型为0至2")
    private Integer type;

    private static final long serialVersionUID = 1L;

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}