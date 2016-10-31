package com.mapbar.kaoqin.bean;

import java.io.Serializable;

public class DocBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String year;
	private String month;
	private String day;
	private String startTimeMin;
	private String startTimeHour;
	private String endTimeMin;
	private String endTimeHour;
	private String interval;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getStartTimeMin() {
		return startTimeMin;
	}
	public void setStartTimeMin(String startTimeMin) {
		this.startTimeMin = startTimeMin;
	}
	public String getEndTimeMin() {
		return endTimeMin;
	}
	public void setEndTimeMin(String endTimeMin) {
		this.endTimeMin = endTimeMin;
	}
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getStartTimeHour() {
		return startTimeHour;
	}
	public void setStartTimeHour(String startTimeHour) {
		this.startTimeHour = startTimeHour;
	}
	public String getEndTimeHour() {
		return endTimeHour;
	}
	public void setEndTimeHour(String endTimeHour) {
		this.endTimeHour = endTimeHour;
	}
	
}
