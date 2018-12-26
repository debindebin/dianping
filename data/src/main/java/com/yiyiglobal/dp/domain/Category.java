package com.yiyiglobal.dp.domain;

import java.io.Serializable;

public class Category implements Serializable {
    private Integer id;

    private String categoryName;

    private String imageUrl;

    private Integer pid;

    private Integer weight;

    private Integer delFlag;

    private Integer homeWeight;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getHomeWeight() {
        return homeWeight;
    }

    public void setHomeWeight(Integer homeWeight) {
        this.homeWeight = homeWeight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", categoryName=").append(categoryName);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", pid=").append(pid);
        sb.append(", weight=").append(weight);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", homeWeight=").append(homeWeight);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}