package com.mapbar.kaoqin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HolidayUtil {
	public static String get(int year,int month) {
		String date = year + "-" +month;
		Map<String, String> holidayMap = HolidaySave.fileToObject();
		if(null == holidayMap){
			holidayMap = new HashMap<String, String>();
		}
		if(!holidayMap.containsKey(date)){
			holidayMap = new HashMap<String, String>();
		}else{
			return holidayMap.get(date);
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year); // 2010年
		c.set(Calendar.MONTH, month - 1);
		int allDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String dateStr = "";
		String yearMonth = "";
		if(month < 10){
			yearMonth = year + "0" + month;
		}else{
			yearMonth = year + "" + month;
		}
		for(int i = 1;i <= allDays;i++){
			dateStr += year;
			if(month < 10){
				dateStr += "0";
			}
			dateStr += month;
			if(i < 10){
				dateStr += "0";
			}
			dateStr += i;
			dateStr += ",";
		}
		dateStr = dateStr.substring(0,dateStr.length()-1);
		String httpUrl = "http://apis.baidu.com/xiaogg/holiday/holiday";
		String httpArg = "d="+dateStr;
		String jsonResult = request(httpUrl, httpArg);
		String result = "/";
		if(jsonResult.startsWith("{")){
			jsonResult = jsonResult.trim();
			jsonResult = jsonResult.substring(1,jsonResult.length() - 1);
			String[] jsons = jsonResult.split(",");
			int day = 0;
			for (String json : jsons) {
				if(json.endsWith("\"0\"")){
					continue;
				}
				json = json.substring(1,json.lastIndexOf("\""));
				day = Integer.parseInt(json.replaceAll(yearMonth, ""));
				result += day + "/";
			}
		}
		holidayMap.put(date, result);
		HolidaySave.objectToFile(holidayMap);
		return result;
	}
	
	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;
	
	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // 填入apikey到HTTP header
	        connection.setRequestProperty("apikey",  "c755b2d2bf029c032206cc8a3df24a3e");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
}