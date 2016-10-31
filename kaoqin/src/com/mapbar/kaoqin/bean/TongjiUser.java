package com.mapbar.kaoqin.bean;

import java.io.Serializable;
import java.util.Date;

public class TongjiUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private int count;
	private Date lastUpdateTime;
	
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	@Override
	public boolean equals(Object obj) {
		return this.userId.equals(((TongjiUser)obj).userId);
	}
	@Override
	public int hashCode() {
		return this.hashCode();
	}
}
