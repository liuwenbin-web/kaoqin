package com.mapbar.kaoqin.util;

import java.text.DecimalFormat;

public class NumUtil {
	public static String buWei(int num){
		if(num < 10){
			return "0"+num;
		}
		return "" + num;
	}
	
	public static String formatDouble(double d){
		DecimalFormat df = new DecimalFormat("######0.0");
		return df.format(d);
	}
}
