package com.mapbar.kaoqin.util;

public class DateUtil {
	public static int getIntVal(String time){
		return Integer.parseInt(time.replaceAll(":", ""));
	}
}
