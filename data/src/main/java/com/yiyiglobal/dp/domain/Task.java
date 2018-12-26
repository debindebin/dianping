package com.yiyiglobal.dp.domain;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private Integer id;

    private String taskName;

    private String imageUrl;

    private String description;

    private Integer rewardNum;

    private Integer delFlag;

    private Date creTime;

    private Integer openStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getRewardNum() {
        return rewardNum;
    }

    public void setRewardNum(Integer rewardNum) {
        this.rewardNum = rewardNum;
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

    public Integer getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(Integer openStatus) {
        this.openStatus = openStatus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", taskName=").append(taskName);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", description=").append(description);
        sb.append(", rewardNum=").append(rewardNum);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", creTime=").append(creTime);
        sb.append(", openStatus=").append(openStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}