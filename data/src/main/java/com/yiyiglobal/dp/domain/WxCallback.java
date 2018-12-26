package com.yiyiglobal.dp.domain;

import java.io.Serializable;

public class WxCallback implements Serializable {
    private Integer id;

    private String toUserName;

    private String fromUserName;

    private Integer createTime;

    private String msgType;

    private String event;

    private String eventKey;

    private String msgId;

    private String picUrl;

    private String mediaId;

    private String format;

    private String recognition;

    private String thumbMediaId;

    private String scale;

    private String label;

    private String title;

    private String description;

    private String url;

    private String ticket;

    private String latitude;

    private String longitude;

    private String precision;

    private String status;

    private Integer totalCount;

    private Integer filterCount;

    private Integer sentCount;

    private Integer errorCount;

    private Integer expiredTime;

    private Integer failTime;

    private String failReason;

    private String uniqId;

    private String poiId;

    private String result;

    private String msg;

    private String lotteryId;

    private Integer money;

    private Integer bindTime;

    private Integer connectTime;

    private Integer expireTime;

    private String vendorId;

    private String shopId;

    private String deviceNo;

    private String keyStandard;

    private String keyStr;

    private String country;

    private String province;

    private String city;

    private Integer sex;

    private Integer scene;

    private String regionCode;

    private Integer reasonMsg;

    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName == null ? null : toUserName.trim();
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName == null ? null : fromUserName.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey == null ? null : eventKey.trim();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format == null ? null : format.trim();
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition == null ? null : recognition.trim();
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId == null ? null : thumbMediaId.trim();
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale == null ? null : scale.trim();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision == null ? null : precision.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(Integer filterCount) {
        this.filterCount = filterCount;
    }

    public Integer getSentCount() {
        return sentCount;
    }

    public void setSentCount(Integer sentCount) {
        this.sentCount = sentCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Integer expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getFailTime() {
        return failTime;
    }

    public void setFailTime(Integer failTime) {
        this.failTime = failTime;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public String getUniqId() {
        return uniqId;
    }

    public void setUniqId(String uniqId) {
        this.uniqId = uniqId == null ? null : uniqId.trim();
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId == null ? null : poiId.trim();
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId == null ? null : lotteryId.trim();
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getBindTime() {
        return bindTime;
    }

    public void setBindTime(Integer bindTime) {
        this.bindTime = bindTime;
    }

    public Integer getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Integer connectTime) {
        this.connectTime = connectTime;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId == null ? null : vendorId.trim();
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo == null ? null : deviceNo.trim();
    }

    public String getKeyStandard() {
        return keyStandard;
    }

    public void setKeyStandard(String keyStandard) {
        this.keyStandard = keyStandard == null ? null : keyStandard.trim();
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr == null ? null : keyStr.trim();
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getScene() {
        return scene;
    }

    public void setScene(Integer scene) {
        this.scene = scene;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode == null ? null : regionCode.trim();
    }

    public Integer getReasonMsg() {
        return reasonMsg;
    }

    public void setReasonMsg(Integer reasonMsg) {
        this.reasonMsg = reasonMsg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", toUserName=").append(toUserName);
        sb.append(", fromUserName=").append(fromUserName);
        sb.append(", createTime=").append(createTime);
        sb.append(", msgType=").append(msgType);
        sb.append(", event=").append(event);
        sb.append(", eventKey=").append(eventKey);
        sb.append(", msgId=").append(msgId);
        sb.append(", picUrl=").append(picUrl);
        sb.append(", mediaId=").append(mediaId);
        sb.append(", format=").append(format);
        sb.append(", recognition=").append(recognition);
        sb.append(", thumbMediaId=").append(thumbMediaId);
        sb.append(", scale=").append(scale);
        sb.append(", label=").append(label);
        sb.append(", title=").append(title);
        sb.append(", description=").append(description);
        sb.append(", url=").append(url);
        sb.append(", ticket=").append(ticket);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", precision=").append(precision);
        sb.append(", status=").append(status);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", filterCount=").append(filterCount);
        sb.append(", sentCount=").append(sentCount);
        sb.append(", errorCount=").append(errorCount);
        sb.append(", expiredTime=").append(expiredTime);
        sb.append(", failTime=").append(failTime);
        sb.append(", failReason=").append(failReason);
        sb.append(", uniqId=").append(uniqId);
        sb.append(", poiId=").append(poiId);
        sb.append(", result=").append(result);
        sb.append(", msg=").append(msg);
        sb.append(", lotteryId=").append(lotteryId);
        sb.append(", money=").append(money);
        sb.append(", bindTime=").append(bindTime);
        sb.append(", connectTime=").append(connectTime);
        sb.append(", expireTime=").append(expireTime);
        sb.append(", vendorId=").append(vendorId);
        sb.append(", shopId=").append(shopId);
        sb.append(", deviceNo=").append(deviceNo);
        sb.append(", keyStandard=").append(keyStandard);
        sb.append(", keyStr=").append(keyStr);
        sb.append(", country=").append(country);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", sex=").append(sex);
        sb.append(", scene=").append(scene);
        sb.append(", regionCode=").append(regionCode);
        sb.append(", reasonMsg=").append(reasonMsg);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}