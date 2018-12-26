package com.yiyiglobal.dp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;

/**
 * Created by zhangdebin on 2018/4/28.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HomeIncomeDto {

    private BigDecimal tokenSum;

    private BigDecimal tokenRemain;

    private BigDecimal yesterdayTokenSum;

    private BigDecimal todayContributionSum;

    private BigDecimal yesterdayRate;



    public BigDecimal getTokenSum() {
        return tokenSum;
    }

    public void setTokenSum(BigDecimal tokenSum) {
        this.tokenSum = tokenSum;
    }

    public BigDecimal getYesterdayTokenSum() {
        return yesterdayTokenSum;
    }

    public void setYesterdayTokenSum(BigDecimal yesterdayTokenSum) {
        this.yesterdayTokenSum = yesterdayTokenSum;
    }

    public BigDecimal getTokenRemain() {
        return tokenRemain;
    }

    public void setTokenRemain(BigDecimal tokenRemain) {
        this.tokenRemain = tokenRemain;
    }

    public BigDecimal getTodayContributionSum() {
        return todayContributionSum;
    }

    public void setTodayContributionSum(BigDecimal todayContributionSum) {
        this.todayContributionSum = todayContributionSum;
    }

    public BigDecimal getYesterdayRate() {
        return yesterdayRate;
    }

    public void setYesterdayRate(BigDecimal yesterdayRate) {
        this.yesterdayRate = yesterdayRate;
    }
}
