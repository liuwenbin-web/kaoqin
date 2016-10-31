package com.mapbar.kaoqin.bean;

import java.io.Serializable;
import java.util.Date;

public class BaseInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private String date;
	private String isFirstUse;
	
	private int startWorkTime;
	private int endWorkTime;
	private int allowLateTime;
	private int overWorkTime;
	private int overWorkLineTime;
	private int halfBeginTime;
	private int halfEndTime;
	
	private String halfBeginTimeStr;
	private String halfEndTimeStr;
	private String overWorkLineTimeStr;
	private String startWorkTimeStr;
	private String endWorkTimeStr;
	private String path;
	private Date updateTime;
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIsFirstUse() {
		return isFirstUse;
	}
	public void setIsFirstUse(String isFirstUse) {
		this.isFirstUse = isFirstUse;
	}
	public int getStartWorkTime() {
		return startWorkTime;
	}
	public void setStartWorkTime(int startWorkTime) {
		this.startWorkTime = startWorkTime;
	}
	public int getEndWorkTime() {
		return endWorkTime;
	}
	public void setEndWorkTime(int endWorkTime) {
		this.endWorkTime = endWorkTime;
	}
	public int getAllowLateTime() {
		return allowLateTime;
	}
	public void setAllowLateTime(int allowLateTime) {
		this.allowLateTime = allowLateTime;
	}
	public int getOverWorkTime() {
		return overWorkTime;
	}
	public void setOverWorkTime(int overWorkTime) {
		this.overWorkTime = overWorkTime;
	}
	public int getOverWorkLineTime() {
		return overWorkLineTime;
	}
	public void setOverWorkLineTime(int overWorkLineTime) {
		this.overWorkLineTime = overWorkLineTime;
	}
	public int getHalfBeginTime() {
		return halfBeginTime;
	}
	public void setHalfBeginTime(int halfBeginTime) {
		this.halfBeginTime = halfBeginTime;
	}
	public int getHalfEndTime() {
		return halfEndTime;
	}
	public void setHalfEndTime(int halfEndTime) {
		this.halfEndTime = halfEndTime;
	}
	public String getHalfBeginTimeStr() {
		return halfBeginTimeStr;
	}
	public void setHalfBeginTimeStr(String halfBeginTimeStr) {
		this.halfBeginTimeStr = halfBeginTimeStr;
	}
	public String getHalfEndTimeStr() {
		return halfEndTimeStr;
	}
	public void setHalfEndTimeStr(String halfEndTimeStr) {
		this.halfEndTimeStr = halfEndTimeStr;
	}
	public String getOverWorkLineTimeStr() {
		return overWorkLineTimeStr;
	}
	public void setOverWorkLineTimeStr(String overWorkLineTimeStr) {
		this.overWorkLineTimeStr = overWorkLineTimeStr;
	}
	public String getStartWorkTimeStr() {
		return startWorkTimeStr;
	}
	public void setStartWorkTimeStr(String startWorkTimeStr) {
		this.startWorkTimeStr = startWorkTimeStr;
	}
	public String getEndWorkTimeStr() {
		return endWorkTimeStr;
	}
	public void setEndWorkTimeStr(String endWorkTimeStr) {
		this.endWorkTimeStr = endWorkTimeStr;
	}
}
