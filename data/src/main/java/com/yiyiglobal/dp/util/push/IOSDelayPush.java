package com.yiyiglobal.dp.util.push;

import java.io.Serializable;

public class IOSDelayPush implements Serializable {

	private Integer id;
	private Integer notificationId;
	private String deviceToken;
	private String content;
	private Integer type;
	private Integer userId;
	private String redirectAddr;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRedirectAddr() {
		return redirectAddr;
	}
	public void setRedirectAddr(String redirectAddr) {
		this.redirectAddr = redirectAddr;
	}
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	
}
