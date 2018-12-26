package com.yiyiglobal.dp.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {
    private Integer id;

    private String nickname;

    private String profileImageUrl;

    private String password;

    private String salt;

    private String countryMobileCode;

    private String mobile;

    private String email;

    private Integer sex;

    private Date birthday;

    private String description;

    private String qqId;

    private String weiboId;

    private String wechatUnionId;

    private String wechatOpenId;

    private String wechatMpOpenId;

    private String country;

    private String province;

    private String city;

    private Double latitude;

    private Double longitude;

    private Date locationTime;

    private Integer deviceType;

    private String deviceClientId;

    private Date loginTime;

    private Integer followNum;

    private Integer followingNum;

    private Integer isTestUser;

    private Integer clickNum;

    private Integer isAllowMutilogin;

    private String regDeviceId;

    private Integer activateFlag;

    private Date creTime;

    private Integer contributionNum;

    private String inviteCode;

    private String selfInviteCode;

    private Integer inviteNum;

    private BigDecimal tokenRemain;

    private BigDecimal tokenSum;

    private String bundleIdentifier;

    private String clientId;

    private static final long serialVersionUID = 1L;

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
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl == null ? null : profileImageUrl.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getCountryMobileCode() {
        return countryMobileCode;
    }

    public void setCountryMobileCode(String countryMobileCode) {
        this.countryMobileCode = countryMobileCode == null ? null : countryMobileCode.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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
        this.description = description == null ? null : description.trim();
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId == null ? null : qqId.trim();
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId == null ? null : weiboId.trim();
    }

    public String getWechatUnionId() {
        return wechatUnionId;
    }

    public void setWechatUnionId(String wechatUnionId) {
        this.wechatUnionId = wechatUnionId == null ? null : wechatUnionId.trim();
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId == null ? null : wechatOpenId.trim();
    }

    public String getWechatMpOpenId() {
        return wechatMpOpenId;
    }

    public void setWechatMpOpenId(String wechatMpOpenId) {
        this.wechatMpOpenId = wechatMpOpenId == null ? null : wechatMpOpenId.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(Date locationTime) {
        this.locationTime = locationTime;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceClientId() {
        return deviceClientId;
    }

    public void setDeviceClientId(String deviceClientId) {
        this.deviceClientId = deviceClientId == null ? null : deviceClientId.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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

    public Integer getIsTestUser() {
        return isTestUser;
    }

    public void setIsTestUser(Integer isTestUser) {
        this.isTestUser = isTestUser;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getIsAllowMutilogin() {
        return isAllowMutilogin;
    }

    public void setIsAllowMutilogin(Integer isAllowMutilogin) {
        this.isAllowMutilogin = isAllowMutilogin;
    }

    public String getRegDeviceId() {
        return regDeviceId;
    }

    public void setRegDeviceId(String regDeviceId) {
        this.regDeviceId = regDeviceId == null ? null : regDeviceId.trim();
    }

    public Integer getActivateFlag() {
        return activateFlag;
    }

    public void setActivateFlag(Integer activateFlag) {
        this.activateFlag = activateFlag;
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
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    public String getSelfInviteCode() {
        return selfInviteCode;
    }

    public void setSelfInviteCode(String selfInviteCode) {
        this.selfInviteCode = selfInviteCode == null ? null : selfInviteCode.trim();
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

    public String getBundleIdentifier() {
        return bundleIdentifier;
    }

    public void setBundleIdentifier(String bundleIdentifier) {
        this.bundleIdentifier = bundleIdentifier;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", nickname=").append(nickname);
        sb.append(", profileImageUrl=").append(profileImageUrl);
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", countryMobileCode=").append(countryMobileCode);
        sb.append(", mobile=").append(mobile);
        sb.append(", email=").append(email);
        sb.append(", sex=").append(sex);
        sb.append(", birthday=").append(birthday);
        sb.append(", description=").append(description);
        sb.append(", qqId=").append(qqId);
        sb.append(", weiboId=").append(weiboId);
        sb.append(", wechatUnionId=").append(wechatUnionId);
        sb.append(", wechatOpenId=").append(wechatOpenId);
        sb.append(", wechatMpOpenId=").append(wechatMpOpenId);
        sb.append(", country=").append(country);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", locationTime=").append(locationTime);
        sb.append(", deviceType=").append(deviceType);
        sb.append(", deviceClientId=").append(deviceClientId);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", followNum=").append(followNum);
        sb.append(", followingNum=").append(followingNum);
        sb.append(", isTestUser=").append(isTestUser);
        sb.append(", clickNum=").append(clickNum);
        sb.append(", isAllowMutilogin=").append(isAllowMutilogin);
        sb.append(", regDeviceId=").append(regDeviceId);
        sb.append(", activateFlag=").append(activateFlag);
        sb.append(", creTime=").append(creTime);
        sb.append(", contributionNum=").append(contributionNum);
        sb.append(", inviteCode=").append(inviteCode);
        sb.append(", selfInviteCode=").append(selfInviteCode);
        sb.append(", inviteNum=").append(inviteNum);
        sb.append(", tokenRemain=").append(tokenRemain);
        sb.append(", tokenSum=").append(tokenSum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}