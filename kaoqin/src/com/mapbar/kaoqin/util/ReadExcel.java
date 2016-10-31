package com.mapbar.kaoqin.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.mapbar.kaoqin.bean.BaseInfo;
 
public class ReadExcel {
	public static List<String> readXml(String fileName){
		List<String> result = new ArrayList<String>();
		try {
			InputStream input = new FileInputStream(fileName);	//建立输入流
			Workbook wb  = null;
			//根据文件格式(2003或者2007)来初始化
			wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);		//获得第一个表单
			Iterator<Row> rows = sheet.rowIterator();	//获得第一个表单的迭代器
			String value = "";
			while (rows.hasNext()) {
				Row row = rows.next();	//获得行数据
				Iterator<Cell> cells = row.cellIterator();	//获得第一行的迭代器
				while (cells.hasNext()) {
					Cell cell = cells.next();
					value = cell.getStringCellValue();
					if(value.contains(":")){
						result.add(value);
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		//正式删除--开始
//		result = new ArrayList<String>();
//		int year = 2016;
//		int month = 9;
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.YEAR, year); // 2010年
//		c.set(Calendar.MONTH, month - 1);
//		int allDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
//		for(int day = 1;day<=allDays-1;day++){
//			if("/3/4/10/11/15/16/17/24/25/".contains("/"+day+"/")){
//				continue;
//			}
//			if(day == 12){
//				result.add(year+"-"+month+"-"+day+" 9:05");
//				result.add(year+"-"+month+"-"+day+" 18:55");
//			}else{
//				//result.add(year+"-"+month+"-"+day+" 8:55");
//				result.add(year+"-"+month+"-"+day+" 9:05");
//				result.add(year+"-"+month+"-"+day+" 18:55");
//			}
//		}
		//result.add("2016-9-13 15:25");
		//result.add("2016-9-13 18:05");
		//正式删除--结束
		return result;
	}
	
	public static boolean isKaoqinFile(String fileName){
		try {
			InputStream input = new FileInputStream(fileName);	//建立输入流
			Workbook wb  = null;
			//根据文件格式(2003或者2007)来初始化
			wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);		//获得第一个表单
			Iterator<Row> rows = sheet.rowIterator();	//获得第一个表单的迭代器
			String value = "";
			if (rows.hasNext()) {
				Row row = rows.next();	//获得行数据
				Iterator<Cell> cells = row.cellIterator();	//获得第一行的迭代器
				if (cells.hasNext()) {
					Cell cell = cells.next();
					value = cell.getStringCellValue();
					if("考勤号码".equals(value)){
						return true;
					}else{
						return false;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static BaseInfo getBaseInfo(String fileName){
		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setPath(fileName);
		String userId = "";
		try {
			InputStream input = new FileInputStream(fileName);	//建立输入流
			Workbook wb  = null;
			//根据文件格式(2003或者2007)来初始化
			wb = new HSSFWorkbook(input);
			Sheet sheet = wb.getSheetAt(0);		//获得第一个表单
			Row row = sheet.getRow(1);
			userId = row.getCell(0).getStringCellValue();
			baseInfo.setUserId(userId);
			baseInfo.setUserName(row.getCell(2).getStringCellValue());
			String date = row.getCell(3).getStringCellValue();
			date = date.substring(0,date.lastIndexOf("-")).replaceAll("-", "年")+"月";
			baseInfo.setDate(date);
			//判断工作开始时间和结束时间
			Iterator<Row> rows = sheet.rowIterator();	//获得第一个表单的迭代器
			String value = "";
			if (rows.hasNext()) {
				row = rows.next();	//获得行数据
			}
			/*String dateString = "";
			int tenCount = 0;
			int nightCount = 0;
			while (rows.hasNext()) {
				row = rows.next();	//获得行数据
				try {
					dateString = row.getCell(3).getStringCellValue().split(" ")[1];
					if(dateString.startsWith("8:")){
						nightCount ++;
					}
					if(dateString.startsWith("9:")){
						tenCount ++;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			if(tenCount > nightCount){
				baseInfo.setStartWorkTimeStr("10:00");
				baseInfo.setEndWorkTimeStr("19:00");
			}else{
				baseInfo.setStartWorkTimeStr("9:00");
				baseInfo.setEndWorkTimeStr("18:00");
			}
			KaoQinUtil.getWorkConfig(baseInfo);*/
			baseInfo.setStartWorkTimeStr("9:00");
			baseInfo.setEndWorkTimeStr("18:00");
			baseInfo.setUpdateTime(new Date());
			KaoQinUtil.getWorkConfig(baseInfo);
			//添加用户
			Map<String, BaseInfo> bMap = UserSave.fileToObject();
			if(null == bMap){
				baseInfo.setIsFirstUse("1");
				bMap = new HashMap<String, BaseInfo>();
			}
			if(null == bMap.get(userId)){
				baseInfo.setIsFirstUse("1");
			}else{
				baseInfo.setIsFirstUse("0");
			}
			bMap.put(userId, baseInfo);
			UserSave.objectToFile(bMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return baseInfo;
	}
}