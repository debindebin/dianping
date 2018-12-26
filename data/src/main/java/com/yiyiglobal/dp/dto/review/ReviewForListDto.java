package com.yiyiglobal.dp.dto.review;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yiyiglobal.dp.domain.Material;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewForListDto implements Serializable{

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

    private String nickname;

    private Integer  replyType;

    private String profileImageUrl;

    private List<String> pictureList;

    private List<Material>  videoList;

    private String resName;

    private String defaultPic;

    private static final long serialVersionUID = 1L;


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
        this.reviewContent = reviewContent;
    }

    public List<String> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
    }

    public List<Material> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Material> videoList) {
        this.videoList = videoList;
    }

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(String defaultPic) {
        this.defaultPic = defaultPic;
    }
}