package com.mapbar.kaoqin.main;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mapbar.kaoqin.util.FileUtil;
import com.mapbar.kaoqin.util.KaoQinUtil;
import com.mapbar.kaoqin.util.ReadExcel;

public class Main {
	private static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws Exception {
		String path = "/Users/liuwenbin/Desktop/刘文斌-考勤.xls";
		List<String> result = ReadExcel.readXml(path);
		Map<String, List<String>> map = KaoQinUtil.dealResult(result); 
		// 获取一个月一共的天数
		int year = 0;
		int month = 0;
		String single = result.get(0);
		month = Integer.parseInt(single.substring(single.indexOf("-") + 1,single.lastIndexOf("-")));
		year = Integer.parseInt(single.substring(0,single.indexOf("-")));
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year); // 2010年
		c.set(Calendar.MONTH, month - 1);
		int allDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String dateString = null;
		Date date = null;
		String content = "";
		List<String> times = null;
		String begin = "";
		String end = "";
		for(int day = 1;day <= allDays;day ++){
			content = "";
			begin = "";
			end = "";
			dateString = year+"-"+month+"-"+day;
			date = format1.parse(dateString);
			if(date.getDay() == 6){
				content += "[星期六] ";
			}else if(date.getDay() == 0){
				content += "[星期天] ";
			}
			if(map.containsKey(dateString)){
				times = map.get(dateString);
				if(times.size() == 1){
					content += times.get(0)+" 可能忘记打卡";
				}else{
					begin = times.get(0);
					end = times.get(times.size() - 1);
					content += begin+" -- "+end.replaceAll(dateString + " ", "");
				}
			}else{
				content += dateString + " 无记录";
			}
			System.out.println(content);
			FileUtil.write("/Users/liuwenbin/Desktop/考勤文件整理.txt", content);
		}
	}
}
