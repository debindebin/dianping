package com.yiyiglobal.dp.domain;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    private Integer redirectType;

    private String redirectAddr;

    private Integer delFlag;

    private Date creTime;

    private Integer type;

    private Integer  readed;

    public Notification() {
    }

    public Notification(Integer userId, String title, String content, Integer redirectType, String redirectAddr, Integer type,Date creTime,Integer delFlag) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.redirectType = redirectType;
        this.redirectAddr = redirectAddr;
        this.type = type;
        this.creTime = creTime;
        this.delFlag = delFlag;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getReaded() {
        return readed;
    }

    public void setReaded(Integer readed) {
        this.readed = readed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", title=").append(title);
        sb.append(", content=").append(content);
        sb.append(", redirectType=").append(redirectType);
        sb.append(", redirectAddr=").append(redirectAddr);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", creTime=").append(creTime);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}