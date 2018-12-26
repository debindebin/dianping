package com.yiyiglobal.dp.domain;

import java.io.Serializable;
import java.util.Date;

public class Advertisement implements Serializable {
    private Integer id;

    private String imageUrl;

    private Integer redirectType;

    private String redirectAddr;

    private Integer weight;

    private String note;

    private Date effectTime;

    private Integer type;

    private Integer clickNum;

    private Integer delFlag;

    private Date creTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(Integer redirectType) {
        this.redirectType = redirectType;
    }

    public String getRedirectAddr() {
        return redirectAddr;
    }

    public void setRedirectAddr(String redirectAddr) {
        this.redirectAddr = redirectAddr == null ? null : redirectAddr.trim();
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
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
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", redirectType=").append(redirectType);
        sb.append(", redirectAddr=").append(redirectAddr);
        sb.append(", weight=").append(weight);
        sb.append(", note=").append(note);
        sb.append(", effectTime=").append(effectTime);
        sb.append(", type=").append(type);
        sb.append(", clickNum=").append(clickNum);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", creTime=").append(creTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}