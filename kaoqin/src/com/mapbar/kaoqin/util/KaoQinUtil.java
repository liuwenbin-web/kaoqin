package com.mapbar.kaoqin.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mapbar.kaoqin.bean.BaseInfo;
import com.mapbar.kaoqin.bean.KaoQin;
import com.mapbar.kaoqin.bean.OrderInfo;

public class KaoQinUtil {
	
	private static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	
	public static List<KaoQin> get(String holidays,BaseInfo baseInfo) throws Exception{
		List<KaoQin> kaoqinList = new ArrayList<KaoQin>();
		List<String> result = ReadExcel.readXml(baseInfo.getPath());
		Map<String, List<String>> map = dealResult(result); 
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
		List<String> times = null;
		String begin = "";
		String end = "";
		KaoQin kaoQin = null;
		int allowLate = 0;
		for(int day = 1;day <= allDays;day ++){
			kaoQin = new KaoQin();
			begin = "";
			end = "";
			dateString = year+"-"+month+"-"+day;
			kaoQin.setDate(dateString);
			date = format1.parse(dateString);
			if(holidays.contains("/"+day+"/")){
				kaoQin.setIsHoliday("true");
				kaoQin.setColor("节假日");
				kaoQin.setHolidayName("节假日");
			}else{
				kaoQin.setIsHoliday("false");
			}
			if(date.getDay() == 6){
				kaoQin.setHolidayName("星期六");
			}else if(date.getDay() == 0){
				kaoQin.setHolidayName("星期天");
			}
			if(map.containsKey(dateString)){
				times = map.get(dateString);
				begin = times.get(0);
				end = times.get(times.size() - 1);
				kaoQin.setStartTime(begin.replaceAll(dateString + " ", ""));
				kaoQin.setEndTime(end.replaceAll(dateString + " ", ""));
				if("true".equals(kaoQin.getIsHoliday())){
					kaoQin.setColor("加班");
					kaoQin.setIsHoliday("false");
				}else if(kaoQin.getStartTime().equals(kaoQin.getEndTime())){
					kaoQin.setColor("忘打卡");
				}else{
					//判断考勤类型，确定颜色
					String lateType = getLateType(kaoQin.getStartTime(), kaoQin.getEndTime(),baseInfo);
					if("in".equals(lateType)){
						allowLate ++;
						if(allowLate <= 3){
							kaoQin.setColor("正常");
						}else{
							kaoQin.setColor("调休");
						}
					}else if("normal".equals(lateType)){
						kaoQin.setColor("正常");
					}else if("out".equals(lateType)){
						kaoQin.setColor("调休");
					}else if("over".equals(lateType)){
						kaoQin.setColor("加班");
					}
				}
			}else{
				if("false".equals(kaoQin.getIsHoliday())){
					kaoQin.setColor("调休");
					kaoQin.setStartTime("");
					kaoQin.setEndTime("");
				}
			}
			kaoqinList.add(kaoQin);
		}
		return kaoqinList;
	}
	
	public static Map<String, List<String>> dealResult(List<String> result){
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String date = null;
		List<String> timeList = null;
		for (String r : result) {
			date = r.split(" ")[0];
			if(!map.containsKey(date)){
				timeList = new ArrayList<String>();
			}else{
				timeList = map.get(date);
			}
			timeList.add(r);
			map.put(date, timeList);
		}
		return map;
	}
	
	private static String getLateType(String startTime,String endTime,BaseInfo baseInfo){
		int s = Integer.parseInt(startTime.replaceAll(":", ""));
		int e = Integer.parseInt(endTime.replaceAll(":", ""));
		if(s > baseInfo.getStartWorkTime() && s <= baseInfo.getAllowLateTime()){
			return "in";
		}
		if(s > baseInfo.getAllowLateTime()){
			return "out";
		}
		if(e < baseInfo.getEndWorkTime()){
			return "out";
		}
		if(e >= baseInfo.getOverWorkTime()){
			return "over";
		}
		return "normal";
	}
	
//	public static void main(String[] args) throws Exception {
//		System.out.println(new Gson().toJson(get("/3/4/15/16/17/10/11/24/25/")));
//	}
	
	public static String getAfterHalfTime(String time){
		if(time.endsWith("00") || time.endsWith("30")){
			return time;
		}
		String[] times = time.split(":");
		int hour = Integer.parseInt(times[0]);
		int min = Integer.parseInt(times[1]);
		if(min < 30){
			min = 30;
		}
		if(min > 30){
			min = 0;
			hour ++;
		}
		return hour+":"+NumUtil.buWei(min);
	}
	
	public static String getBeforeHalfTime(String time){
		if(time.endsWith("00") || time.endsWith("30")){
			return time;
		}
		String[] times = time.split(":");
		int hour = Integer.parseInt(times[0]);
		int min = Integer.parseInt(times[1]);
		if(min < 30){
			min = 0;
		}
		if(min > 30){
			min = 30;
		}
		return hour+":"+NumUtil.buWei(min);
	}
	
	public static String getInterval(String begin,String end,double removeHour){
		int endTime = Integer.parseInt(end.replaceAll(":", ""));
		int startTime = Integer.parseInt(begin.replaceAll(":", ""));
		double interVal = (endTime - startTime) / Double.parseDouble("100") - removeHour;
		String inter = NumUtil.formatDouble(interVal).replaceAll(".7", ".5").replaceAll(".3", ".5");
		return inter;
	}
	
	public static void main(String[] args) {
		System.out.println(getInterval("9:00", "18:00", 1));
	}
	
	/**
	 * 获取加班
	 * @param startTime
	 * @param endTime
	 * @param isHoliday
	 */
	public static OrderInfo getJiaBan(String date,String startTime,String endTime,boolean isHoliday,BaseInfo baseInfo){
		if(null == startTime || "".equals(startTime) || null == endTime || "".equals(endTime)){
			return null;
		}
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setDate(date);
		orderInfo.setType("jiaban");
		if(isHoliday){
			startTime = getAfterHalfTime(startTime);
			endTime = getBeforeHalfTime(endTime);
			orderInfo.setInterval(getInterval(startTime, endTime, 0));
			orderInfo.setStartTime(startTime);
			orderInfo.setEndTime(endTime);
		}else{
			//结束时间没有超过19:30
			if(DateUtil.getIntVal(endTime) < baseInfo.getOverWorkTime()){
				return null;
			}
			//超过19:30了，算加班
			orderInfo.setStartTime(baseInfo.getOverWorkLineTimeStr());
			orderInfo.setEndTime(getBeforeHalfTime(endTime));
			orderInfo.setInterval(getInterval(orderInfo.getStartTime(), orderInfo.getEndTime(), 0));
		}
		return orderInfo;
	}
	
	/**
	 * 获取调休
	 * @param startTime
	 * @param endTime
	 * @param isHoliday
	 */
	public static List<OrderInfo> getTiaoxiu(String date,String startTime,String endTime,BaseInfo baseInfo){
		List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
		OrderInfo orderInfo = null;
		String et = null;
		String st = null;
		if(null == startTime || "".equals(startTime) || null == endTime || "".equals(endTime)){
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = baseInfo.getStartWorkTimeStr();
			et = baseInfo.getEndWorkTimeStr();
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 1));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
			return orderInfos;
		}
		if(startTime.equals(endTime)){
			//忘记打卡
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("wangdaka");
			
			st = startTime;
			et = endTime;
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 1));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
			return orderInfos;
		}
		int sti = DateUtil.getIntVal(startTime);
		int eti = DateUtil.getIntVal(endTime);
		//开始和结束时间都在12点之后
		if(sti > baseInfo.getHalfBeginTime() && eti > baseInfo.getHalfBeginTime() || eti <= baseInfo.getStartWorkTime()){
			System.out.println("开始和结束时间都在12点之后");
			//整上午调休
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = baseInfo.getStartWorkTimeStr();
			et = baseInfo.getHalfBeginTimeStr();
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
		}
		//计算上午的调休
		//开始时间在9点以后(但是在12点之前)，并且结束时间在12点以后的情况
		if(sti > baseInfo.getStartWorkTime() && sti <= baseInfo.getHalfBeginTime() && eti > baseInfo.getHalfBeginTime()){
			System.out.println("开始时间在9点以后(但是在12点之前)，并且结束时间在12点以后的情况");
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = baseInfo.getStartWorkTimeStr();
			et = getAfterHalfTime(startTime);
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
		}
		//开始时间在9点以后(但是在12点之前)，并且结束时间在12点之前的情况
		if(sti > baseInfo.getStartWorkTime() && sti <= baseInfo.getHalfBeginTime() && eti <= baseInfo.getHalfBeginTime()){
			System.out.println("开始时间在9点以后(但是在12点之前)，并且结束时间在12点之前的情况");
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = baseInfo.getStartWorkTimeStr();
			et = getAfterHalfTime(startTime);
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
			//时间分两段
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = getBeforeHalfTime(endTime);
			et = baseInfo.getHalfBeginTimeStr();
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
		}
		//开始时间在9点之前，并且结束时间在12点之前（在9点之后）的情况
		if(sti <= baseInfo.getStartWorkTime() && eti < baseInfo.getHalfBeginTime() && eti > baseInfo.getStartWorkTime()){
			System.out.println("开始时间在9点之前，并且结束时间在12点之前的情况");
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = getBeforeHalfTime(endTime);
			et = baseInfo.getHalfBeginTimeStr();
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
		}
		//下午的调休
		//开始时间在13点之前，结束时间在18点之前（但是在13点之后）的情况。
		if(sti < baseInfo.getHalfEndTime() && eti < baseInfo.getEndWorkTime() && eti > baseInfo.getHalfEndTime()){
			System.out.println("开始时间在13点之前，结束时间在18点之前（但是在13点之后）的情况。");
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = getBeforeHalfTime(endTime);
			et = baseInfo.getEndWorkTimeStr();
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
		}
		//开始时间在13点之后（在18点之前），结束时间在18点之后的情况。
		if(sti > baseInfo.getHalfEndTime() && eti > baseInfo.getEndWorkTime() && sti < baseInfo.getEndWorkTime()){
			System.out.println("开始时间在13点之后，结束时间在18点之后的情况。");
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = baseInfo.getHalfEndTimeStr();
			et = getAfterHalfTime(startTime);
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
		}
		//开始时间在13点以后(但是在18点之前)，并且结束时间在18点之前的情况
		if(sti >= baseInfo.getHalfEndTime() && sti <= baseInfo.getEndWorkTime() && eti <= baseInfo.getEndWorkTime()){
			System.out.println("开始时间在13点以后(但是在18点之前)，并且结束时间在18点之前的情况");
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = baseInfo.getHalfEndTimeStr();
			et = getAfterHalfTime(startTime);
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
			//时间分两段
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = getBeforeHalfTime(endTime);
			et = baseInfo.getEndWorkTimeStr();
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
		}
		//开始和结束时间都在13点之前
		if((sti < baseInfo.getHalfEndTime() && eti <= baseInfo.getHalfEndTime()) || sti >= baseInfo.getEndWorkTime()){
			System.out.println("开始和结束时间都在13点之前");
			//整下午调休
			orderInfo = new OrderInfo();
			orderInfo.setDate(date);
			orderInfo.setType("tiaoxiu");
			
			st = baseInfo.getHalfEndTimeStr();
			et = baseInfo.getEndWorkTimeStr();
			orderInfo.setStartTime(st);
			orderInfo.setEndTime(et);
			orderInfo.setInterval(getInterval(st, et, 0));
			if(!"0.0".equals(orderInfo.getInterval())){
				orderInfos.add(orderInfo);
			}
		}
		if(orderInfos.size() == 0){
			return null;
		}
		//合并日期
		if(orderInfos.size() == 2){
			OrderInfo firstOrderInfo = orderInfos.get(0);
			OrderInfo secondOrderInfo = orderInfos.get(1);
			if(firstOrderInfo.getEndTime().equals(baseInfo.getHalfBeginTimeStr()) && secondOrderInfo.getStartTime().equals(baseInfo.getHalfEndTimeStr())){
				orderInfos = new ArrayList<OrderInfo>();
				OrderInfo oInfo = new OrderInfo();
				oInfo.setDate(date);
				oInfo.setEndTime(secondOrderInfo.getEndTime());
				oInfo.setStartTime(firstOrderInfo.getStartTime());
				oInfo.setInterval(getInterval(oInfo.getStartTime(), oInfo.getEndTime(), 1));
				oInfo.setType("tiaoxiu");
				orderInfos.add(oInfo);
			}
		}
		return orderInfos;
	}
	
	public static void getWorkConfig(BaseInfo baseInfo){
		if(baseInfo.getStartWorkTimeStr().equals("9:00")){
			//9点上班
			baseInfo.setStartWorkTime(900);
			baseInfo.setEndWorkTime(1800);
			baseInfo.setAllowLateTime(910);
			baseInfo.setOverWorkTime(1930);
			baseInfo.setOverWorkLineTime(1830);
			baseInfo.setHalfBeginTime(1200);
			baseInfo.setHalfEndTime(1300);
			baseInfo.setHalfBeginTimeStr("12:00");
			baseInfo.setHalfEndTimeStr("13:00");
			baseInfo.setOverWorkLineTimeStr("18:30");
			baseInfo.setStartWorkTimeStr("9:00");
			baseInfo.setEndWorkTimeStr("18:00");
		}else{
			//10点上班
			baseInfo.setStartWorkTime(1000);
			baseInfo.setEndWorkTime(1900);
			baseInfo.setAllowLateTime(1010);
			baseInfo.setOverWorkTime(1930);
			baseInfo.setOverWorkLineTime(1830);
			baseInfo.setHalfBeginTime(1200);
			baseInfo.setHalfEndTime(1300);
			baseInfo.setHalfBeginTimeStr("12:00");
			baseInfo.setHalfEndTimeStr("13:00");
			baseInfo.setOverWorkLineTimeStr("18:30");
			baseInfo.setStartWorkTimeStr("10:00");
			baseInfo.setEndWorkTimeStr("19:00");
		}
	}
}
