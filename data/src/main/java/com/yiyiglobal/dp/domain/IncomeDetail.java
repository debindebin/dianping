package com.yiyiglobal.dp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class IncomeDetail implements Serializable {
    private Integer id;

    private Integer userId;

    private String title;

    private BigDecimal incomeNum;

    private Integer incomeType;

    private Integer taskId;

    private String adminNote;

    private Date creTime;

    private static final long serialVersionUID = 1L;

    public IncomeDetail() {
    }

    public IncomeDetail(Integer userId, Integer incomeType, Integer taskId) {
        this.userId = userId;
        this.incomeType = incomeType;
        this.taskId = taskId;
    }

    public IncomeDetail(Integer userId, String title, BigDecimal incomeNum, Integer incomeType, Integer taskId, Date creTime) {
        this.userId = userId;
        this.title = title;
        this.incomeNum = incomeNum;
        this.incomeType = incomeType;
        this.taskId = taskId;
        this.creTime = creTime;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public BigDecimal getIncomeNum() {
        return incomeNum;
    }

    public void setIncomeNum(BigDecimal incomeNum) {
        this.incomeNum = incomeNum;
    }

    public Integer getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(Integer incomeType) {
        this.incomeType = incomeType;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getAdminNote() {
        return adminNote;
    }

    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote == null ? null : adminNote.trim();
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
        sb.append(", title=").append(title);
        sb.append(", incomeNum=").append(incomeNum);
        sb.append(", incomeType=").append(incomeType);
        sb.append(", taskId=").append(taskId);
        sb.append(", adminNote=").append(adminNote);
        sb.append(", creTime=").append(creTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}