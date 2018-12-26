package com.yiyiglobal.dp.domain;

import java.io.Serializable;
import java.util.Date;

public class Material implements Serializable {
    private Integer id;

    private String materialUrl;

    private String materialNote;

    private Integer relatedType;

    private Integer relatedId;

    private Date creTime;

    private Integer delFlag;

    private Integer materialType;

    private Integer weight;

    private String  persistentId;

    private Integer duration;

    private static final long serialVersionUID = 1L;

    public Material() {
    }

    public Material(Integer relatedType, Integer relatedId, Date creTime, Integer materialType) {
        this.relatedType = relatedType;
        this.relatedId = relatedId;
        this.creTime = creTime;
        this.materialType = materialType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl == null ? null : materialUrl.trim();
    }

    public String getMaterialNote() {
        return materialNote;
    }

    public void setMaterialNote(String materialNote) {
        this.materialNote = materialNote == null ? null : materialNote.trim();
    }

    public Integer getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(Integer relatedType) {
        this.relatedType = relatedType;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getMaterialType() {
        return materialType;
    }

    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPersistentId() {
        return persistentId;
    }

    public void setPersistentId(String persistentId) {
        this.persistentId = persistentId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", materialUrl=").append(materialUrl);
        sb.append(", materialNote=").append(materialNote);
        sb.append(", relatedType=").append(relatedType);
        sb.append(", relatedId=").append(relatedId);
        sb.append(", creTime=").append(creTime);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", materialType=").append(materialType);
        sb.append(", weight=").append(weight);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}