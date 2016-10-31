package com.mapbar.kaoqin.bean;


public class KaoQin {
	private String date;
	private String startTime;
	private String endTime;
	private String isHoliday;
	private String color;
	private String holidayName;
	private int inTenMin;
	
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getIsHoliday() {
		return isHoliday;
	}
	public void setIsHoliday(String isHoliday) {
		this.isHoliday = isHoliday;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getInTenMin() {
		return inTenMin;
	}
	public void setInTenMin(int inTenMin) {
		this.inTenMin = inTenMin;
	}
	@Override
	public int hashCode() {
		return date.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.date.equals(((KaoQin)obj).date);
	}
}
