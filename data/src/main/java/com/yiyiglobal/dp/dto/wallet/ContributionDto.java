package com.yiyiglobal.dp.dto.wallet;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;

/**
 * Created by zhangdebin on 2018/4/28.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ContributionDto {

    private Integer  userId;

    private BigDecimal contributionSum;

    private  Integer  pUserId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getContributionSum() {
        return contributionSum;
    }

    public void setContributionSum(BigDecimal contributionSum) {
        this.contributionSum = contributionSum;
    }

    public Integer getpUserId() {
        return pUserId;
    }

    public void setpUserId(Integer pUserId) {
        this.pUserId = pUserId;
    }
}
