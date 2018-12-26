package com.yiyiglobal.dp.domain;

import java.io.Serializable;
import java.util.Date;

public class InviteContribution implements Serializable {
    private Integer id;

    private Integer fromUserId;

    private Integer toUserId;

    private Integer lastVal;

    private Integer sumValue;

    private Date contributeDate;

    private Date creTime;

    public InviteContribution() {
    }

    public InviteContribution(Integer fromUserId, Integer toUserId, Integer lastVal, Integer sumValue, Date contributeDate, Date creTime) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.lastVal = lastVal;
        this.sumValue = sumValue;
        this.contributeDate = contributeDate;
        this.creTime = creTime;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getLastVal() {
        return lastVal;
    }

    public void setLastVal(Integer lastVal) {
        this.lastVal = lastVal;
    }

    public Integer getSumValue() {
        return sumValue;
    }

    public void setSumValue(Integer sumValue) {
        this.sumValue = sumValue;
    }

    public Date getContributeDate() {
        return contributeDate;
    }

    public void setContributeDate(Date contributeDate) {
        this.contributeDate = contributeDate;
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
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", lastVal=").append(lastVal);
        sb.append(", sumValue=").append(sumValue);
        sb.append(", contributeDate=").append(contributeDate);
        sb.append(", creTime=").append(creTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}