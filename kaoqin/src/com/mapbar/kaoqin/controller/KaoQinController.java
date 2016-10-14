package com.mapbar.kaoqin.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.mapbar.kaoqin.bean.BaseInfo;
import com.mapbar.kaoqin.bean.DocBean;
import com.mapbar.kaoqin.bean.KaoQin;
import com.mapbar.kaoqin.bean.OrderInfo;
import com.mapbar.kaoqin.util.Constant;
import com.mapbar.kaoqin.util.KaoQinSave;
import com.mapbar.kaoqin.util.KaoQinUtil;
import com.mapbar.kaoqin.util.UserSave;

@Controller
@RequestMapping(value="")
public class KaoQinController {
	
	@RequestMapping(value="/kaoqin")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String dateStr = request.getParameter("date");
		String userId = request.getParameter("userId");
		ModelAndView mav = new ModelAndView();
		if(null == dateStr || "".equals(dateStr) || null == userId || "".equals(userId)){
			mav.setViewName("user");
			return mav;
		}
		Map<String, BaseInfo> bMap = UserSave.fileToObject();
		BaseInfo baseInfo = bMap.get(userId);
		if(null == bMap || null == baseInfo){
			mav.setViewName("user");
			return mav;
		}
		mav.setViewName("home");
		mav.addObject("kaoqinList", KaoQinUtil.get(dateStr,baseInfo));
		mav.addObject("userId", userId);
		mav.addObject("date", dateStr);
		mav.addObject("jiaBanDanCount", Constant.jiaBanDanCount);
		mav.addObject("tiaoXiuDanCount", Constant.tiaoXiuDanCount);
		return mav;
	}
	
	@RequestMapping(value="zhengli")
	public void zhengli(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		PrintWriter out = response.getWriter();
		String date = request.getParameter("date");
		String holidays = request.getParameter("holidays");
		//Thread.sleep(700);
		Map<String, BaseInfo> bMap = UserSave.fileToObject();
		BaseInfo baseInfo = bMap.get(userId);
		if(null == baseInfo){
			out.write("没有对应的用户信息");
			out.flush();
			out.close();
			return;
		}
		List<KaoQin> kaoqinList = KaoQinUtil.get(holidays,baseInfo);
		KaoQin kaoQin = null;
		for (KaoQin k : kaoqinList) {
			if(k.getDate().equals(date)){
				kaoQin = k;
				break;
			}
		}
		if(null == kaoQin){
			out.write("没有考勤记录");
			out.flush();
			out.close();
			return;
		}
		//判断是否是休息日
		String[] dateArr = date.split("-");
		List<OrderInfo> orderInfos = new ArrayList<OrderInfo>();
		OrderInfo jiaban = null;
		List<OrderInfo> tiaoxius = null;
		if(holidays.contains("/"+dateArr[2]+"/")){
			//是休息日，所有的都算加班
			jiaban = KaoQinUtil.getJiaBan(date, kaoQin.getStartTime(), kaoQin.getEndTime(), true,baseInfo);
			if(null != jiaban){
				orderInfos.add(jiaban);
			}
		}else{
			//工作日
			jiaban = KaoQinUtil.getJiaBan(date, kaoQin.getStartTime(), kaoQin.getEndTime(), false,baseInfo);
			if(null != jiaban){
				orderInfos.add(jiaban);
			}
			tiaoxius = KaoQinUtil.getTiaoxiu(date, kaoQin.getStartTime(), kaoQin.getEndTime(),baseInfo);
			if(null != tiaoxius){
				orderInfos.addAll(tiaoxius);
			}
		}
		if(orderInfos.size() == 0){
			out.write("没有考勤记录");
		}else{
			out.write(new Gson().toJson(orderInfos));
		}
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="saveData")
	public void saveData(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String kaoqinStr = request.getParameter("kaoqinStr");
		String userId  = request.getParameter("userId");
		if(null == kaoqinStr || "".equals(kaoqinStr)){
			return;
		}
		if(null == userId || "".equals(userId)){
			return;
		}
		kaoqinStr = kaoqinStr.substring(0,kaoqinStr.length() - 1);
		String[] kaoqinStrs = kaoqinStr.split("\\|");
		Map<String, List<DocBean>> dataMap = new HashMap<String, List<DocBean>>();
		String[] dsp = null;
		List<DocBean> docBeans = null;
		DocBean docBean = null;
		String[] dateSplit = null;
		String[] startTimeSplit = null;
		String[] endTimeSplit = null;
		for (String kq : kaoqinStrs) {
			dsp = kq.split(" ");
			docBeans = dataMap.get(dsp[0]);
			if(null == docBeans){
				docBeans = new ArrayList<DocBean>();
			}
			dateSplit = dsp[1].split("-");
			startTimeSplit = dsp[2].split(":");
			endTimeSplit = dsp[3].split(":");
			docBean = new DocBean();
			docBean.setDay(dateSplit[2]);
			docBean.setEndTimeMin(endTimeSplit[1]);
			docBean.setEndTimeHour(endTimeSplit[0]);
			docBean.setInterval(dsp[4]);
			docBean.setMonth(dateSplit[1]);
			docBean.setStartTimeHour(startTimeSplit[0]);
			docBean.setStartTimeMin(startTimeSplit[1]);
			docBean.setType(dsp[0]);
			docBean.setYear(dateSplit[0]);
			docBeans.add(docBean);
			dataMap.put(dsp[0], docBeans);
		}
		
		Map<String, Map<String, List<DocBean>>> userMap = KaoQinSave.fileToObject();
		if(null == userMap){
			userMap = new HashMap<String, Map<String,List<DocBean>>>();
		}
		userMap.put(userId, dataMap);
		KaoQinSave.objectToFile(userMap);
	}
	
	@RequestMapping(value="download")
	public void download(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String fileName = request.getParameter("fileName");
		String path = null;
		if(null == fileName){
			return;
		}
		if(fileName.equals("jiaban")){
			path = Constant.dataPath + Constant.jiabanFileName;
		}else if(fileName.equals("tiaoxiu")){
			path = Constant.dataPath + Constant.tiaoxiuFileName;
		}else{
			return;
		}
		File file = new File(path);
		if (file == null || !file.exists()) {
			return;
		}
		OutputStream out = null;
		try {
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename="+ file.getName());
			out = response.getOutputStream();
			out.write(FileUtils.readFileToByteArray(file));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
