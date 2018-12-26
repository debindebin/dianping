package com.yiyiglobal.dp.domain;

import java.io.Serializable;
import java.util.Date;

public class ResReview implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer resId;

    private Integer score;

    private String reviewContent;

    private Integer consumePrice;

    private Integer supportNum;

    private Integer unsupportNum;

    private Integer reviewNum;

    private Integer delFlag;

    private Date creTime;


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

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent == null ? null : reviewContent.trim();
    }

    public Integer getConsumePrice() {
        return consumePrice;
    }

    public void setConsumePrice(Integer consumePrice) {
        this.consumePrice = consumePrice;
    }

    public Integer getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Integer supportNum) {
        this.supportNum = supportNum;
    }

    public Integer getUnsupportNum() {
        return unsupportNum;
    }

    public void setUnsupportNum(Integer unsupportNum) {
        this.unsupportNum = unsupportNum;
    }

    public Integer getReviewNum() {
        return reviewNum;
    }

    public void setReviewNum(Integer reviewNum) {
        this.reviewNum = reviewNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", resId=").append(resId);
        sb.append(", score=").append(score);
        sb.append(", reviewContent=").append(reviewContent);
        sb.append(", consumePrice=").append(consumePrice);
        sb.append(", supportNum=").append(supportNum);
        sb.append(", unsupportNum=").append(unsupportNum);
        sb.append(", reviewNum=").append(reviewNum);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", creTime=").append(creTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}