package com.royal.entity;

import java.sql.Timestamp;

public class SessionDTO {
	private String thirdSession;
	private String sessionKey;
	private String openid;
	private Timestamp lastLoginTime;
	
	public String getThirdSession() {
		return thirdSession;
	}
	public void setThirdSession(String thirdSession) {
		this.thirdSession = thirdSession;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	@Override
	public String toString() {
		return "SessionDTO [thirdSession=" + thirdSession + ", sessionKey=" + sessionKey + ", openid=" + openid
				+ ", lastLoginTime=" + lastLoginTime + "]";
	}
}
