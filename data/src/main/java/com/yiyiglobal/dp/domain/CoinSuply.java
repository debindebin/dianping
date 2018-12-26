package com.yiyiglobal.dp.domain;

import java.math.BigDecimal;
import java.util.Date;

public class CoinSuply {
    private Integer id;

    private Integer num;

    private Date suplyDate;

    private Date creTime;

    private BigDecimal tenContributionValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getSuplyDate() {
        return suplyDate;
    }

    public void setSuplyDate(Date suplyDate) {
        this.suplyDate = suplyDate;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public BigDecimal getTenContributionValue() {
        return tenContributionValue;
    }

    public void setTenContributionValue(BigDecimal tenContributionValue) {
        this.tenContributionValue = tenContributionValue;
    }
}