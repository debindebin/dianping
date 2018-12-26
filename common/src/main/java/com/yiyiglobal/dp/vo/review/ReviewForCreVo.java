package com.yiyiglobal.dp.vo.review;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class ReviewForCreVo implements Serializable{

    @NotNull(message = "被评资源ID为空")
    @Min(value = 0,message = "被评资源ID需大于0")
    private Integer resId;

    @NotNull(message = "评分为空")
    @Min(value = 0,message = "评分需大于0")
    private Integer score;

    private String reviewContent;

    private List<String> pictureList;

    private String videoUrl;

    private String  persistentId;

    private Integer duration;

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

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}