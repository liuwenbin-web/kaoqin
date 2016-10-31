package com.mapbar.kaoqin.bean;

import java.io.Serializable;
import java.util.Date;

public class UserVisit implements Serializable,Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private Date loginTime;
	private String ip;
	private String userAgent;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public int compareTo(Object o) {
		if (this.loginTime.after(((UserVisit)o).loginTime)){
			return -1;
		}else{
			return 1;
		}
	}
}
