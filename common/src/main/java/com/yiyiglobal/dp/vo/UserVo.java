package com.yiyiglobal.dp.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVo {
    private Integer id;

    private String nickname;

    private String profileImageUrl;

    private String countryMobileCode;

    private String mobile;

    private String email;

    private Integer sex;

    private Date birthday;

    private String description;

    private String country;

    private String province;

    private String city;

    private Integer followNum;

    private Integer followingNum;

    private Integer clickNum;

    private Date creTime;

    private Integer contributionNum;

    private String inviteCode;

    private String selfInviteCode;

    private Integer inviteNum;

    private BigDecimal tokenRemain;

    private BigDecimal tokenSum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCountryMobileCode() {
        return countryMobileCode;
    }

    public void setCountryMobileCode(String countryMobileCode) {
        this.countryMobileCode = countryMobileCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getFollowNum() {
        return followNum;
    }

    public void setFollowNum(Integer followNum) {
        this.followNum = followNum;
    }

    public Integer getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(Integer followingNum) {
        this.followingNum = followingNum;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Date getCreTime() {
        return creTime;
    }

    public void setCreTime(Date creTime) {
        this.creTime = creTime;
    }

    public Integer getContributionNum() {
        return contributionNum;
    }

    public void setContributionNum(Integer contributionNum) {
        this.contributionNum = contributionNum;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getSelfInviteCode() {
        return selfInviteCode;
    }

    public void setSelfInviteCode(String selfInviteCode) {
        this.selfInviteCode = selfInviteCode;
    }

    public Integer getInviteNum() {
        return inviteNum;
    }

    public void setInviteNum(Integer inviteNum) {
        this.inviteNum = inviteNum;
    }

    public BigDecimal getTokenRemain() {
        return tokenRemain;
    }

    public void setTokenRemain(BigDecimal tokenRemain) {
        this.tokenRemain = tokenRemain;
    }

    public BigDecimal getTokenSum() {
        return tokenSum;
    }

    public void setTokenSum(BigDecimal tokenSum) {
        this.tokenSum = tokenSum;
    }
}