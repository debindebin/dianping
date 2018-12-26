package com.yiyiglobal.dp.domain;

import java.io.Serializable;
import java.util.Date;

public class ReviewReply implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer reviewId;

    private String replyContent;

    private Date creTime;

    private Integer type;

    private Integer delFlag;

    private Integer supportFlag;

    private Integer unsupportFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent == null ? null : replyContent.trim();
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getSupportFlag() {
        return supportFlag;
    }

    public void setSupportFlag(Integer supportFlag) {
        this.supportFlag = supportFlag;
    }

    public Integer getUnsupportFlag() {
        return unsupportFlag;
    }

    public void setUnsupportFlag(Integer unsupportFlag) {
        this.unsupportFlag = unsupportFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", reviewId=").append(reviewId);
        sb.append(", replyContent=").append(replyContent);
        sb.append(", creTime=").append(creTime);
        sb.append(", type=").append(type);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", supportFlag=").append(supportFlag);
        sb.append(", unsupportFlag=").append(unsupportFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}