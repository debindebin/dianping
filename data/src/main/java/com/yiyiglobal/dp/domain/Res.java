package com.yiyiglobal.dp.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Res implements Serializable {
    private Integer id;

    private String resName;

    private Integer categoryId;

    private Integer reviewNum;

    private BigDecimal reviewAvgScore;

    private BigDecimal avgPrice;

    private String mobiles;

    private String addr;

    private String addrNode;

    private String district;

    private String subDistrict;

    private Double latitude;

    private Double longitude;

    private Date creTime;

    private Integer delFlag;

    private Integer visibleFlag;

    private Integer clickNum;

    private Integer  size;

    private String defaultPic;

    public Res() {
    }

    public Res(String resName, Integer categoryId) {
        this.resName = resName;
        this.categoryId = categoryId;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getReviewNum() {
        return reviewNum;
    }

    public void setReviewNum(Integer reviewNum) {
        this.reviewNum = reviewNum;
    }

    public BigDecimal getReviewAvgScore() {
        return reviewAvgScore;
    }

    public void setReviewAvgScore(BigDecimal reviewAvgScore) {
        this.reviewAvgScore = reviewAvgScore;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles == null ? null : mobiles.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getAddrNode() {
        return addrNode;
    }

    public void setAddrNode(String addrNode) {
        this.addrNode = addrNode == null ? null : addrNode.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict == null ? null : subDistrict.trim();
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

    public Integer getVisibleFlag() {
        return visibleFlag;
    }

    public void setVisibleFlag(Integer visibleFlag) {
        this.visibleFlag = visibleFlag;
    }

    public Integer getClickNum() {
        return clickNum;
    }

    public void setClickNum(Integer clickNum) {
        this.clickNum = clickNum;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(String defaultPic) {
        this.defaultPic = defaultPic;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", resName=").append(resName);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", reviewNum=").append(reviewNum);
        sb.append(", reviewAvgScore=").append(reviewAvgScore);
        sb.append(", avgPrice=").append(avgPrice);
        sb.append(", mobiles=").append(mobiles);
        sb.append(", addr=").append(addr);
        sb.append(", addrNode=").append(addrNode);
        sb.append(", district=").append(district);
        sb.append(", subDistrict=").append(subDistrict);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", creTime=").append(creTime);
        sb.append(", delFlag=").append(delFlag);
        sb.append(", visibleFlag=").append(visibleFlag);
        sb.append(", clickNum=").append(clickNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}