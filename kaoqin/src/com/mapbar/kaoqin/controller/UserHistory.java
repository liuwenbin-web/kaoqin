package com.mapbar.kaoqin.controller;

import java.io.Serializable;
import java.util.Date;

public class UserHistory implements Serializable,Comparable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String jiabanHour;
	private String tiaoxiuHour;
	private String wangdakaCount;
	private Date insertTime;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getJiabanHour() {
		return jiabanHour;
	}
	public void setJiabanHour(String jiabanHour) {
		this.jiabanHour = jiabanHour;
	}
	public String getTiaoxiuHour() {
		return tiaoxiuHour;
	}
	public void setTiaoxiuHour(String tiaoxiuHour) {
		this.tiaoxiuHour = tiaoxiuHour;
	}
	public String getWangdakaCount() {
		return wangdakaCount;
	}
	public void setWangdakaCount(String wangdakaCount) {
		this.wangdakaCount = wangdakaCount;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.date.equals(((UserHistory)obj).date);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.hashCode();
	}
	public int compareTo(Object o) {
		return this.date.compareTo(((UserHistory)o).date);
	}
}
