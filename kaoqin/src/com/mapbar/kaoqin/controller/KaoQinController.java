package com.mapbar.kaoqin.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.mapbar.kaoqin.bean.DownLoadBean;
import com.mapbar.kaoqin.bean.KaoQin;
import com.mapbar.kaoqin.bean.OrderInfo;
import com.mapbar.kaoqin.bean.TongjiUser;
import com.mapbar.kaoqin.util.Constant;
import com.mapbar.kaoqin.util.DownLoadSave;
import com.mapbar.kaoqin.util.KaoQinSave;
import com.mapbar.kaoqin.util.KaoQinUtil;
import com.mapbar.kaoqin.util.NumUtil;
import com.mapbar.kaoqin.util.TongjiUserSave;
import com.mapbar.kaoqin.util.UpSave;
import com.mapbar.kaoqin.util.UserHisSave;
import com.mapbar.kaoqin.util.UserSave;

@Controller
@RequestMapping(value="")
public class KaoQinController {
	
	@RequestMapping(value="/kaoqin")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String dateStr = request.getParameter("date");
		String userId = request.getParameter("userId");
		String pwd =request.getParameter("pwd");
		ModelAndView mav = new ModelAndView();
		if(null == dateStr || "".equals(dateStr) || null == userId || "".equals(userId) || null == pwd || "".equals(pwd)){
			mav.setViewName("user");
			return mav;
		}
		//验证用户密码
		Map<String, String> upMap = UpSave.fileToObject();
		if(null == upMap){
			mav.setViewName("user");
			return mav;
		}
		String pwdCache = upMap.get(userId);
		if(null == pwdCache){
			mav.setViewName("user");
			return mav;
		}
		if(!pwdCache.equals(pwd)){
			mav.setViewName("user");
			return mav;
		}
		mav.addObject("pwd", pwd);
		//获取用户信息
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
		//正式删除--开始
		//baseInfo.setIsFirstUse("1");
		//正式删除--结束
		mav.addObject("baseInfo", baseInfo);
		mav.addObject("jiaBanDanCount", Constant.jiaBanDanCount);
		mav.addObject("tiaoXiuDanCount", Constant.tiaoXiuDanCount);
		List<UserHistory> userHistories = UserHisSave.fileToObject(userId);
		//三个月之后才会出现历史记录
		if(null == userHistories){
			mav.addObject("showHistory", "false");
		}else{
			if(userHistories.size() > 2){
				mav.addObject("showHistory", "true");
			}else{
				mav.addObject("showHistory", "false");
			}
		}
		return mav;
	}
	@RequestMapping(value="/userHistoryAjax")
	public void userHistoryAjax(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userId");
		//正式删除--开始
		//userId = "23007";
		//正式删除--结束
		List<UserHistory> userHistories = UserHisSave.fileToObject(userId);
		if(userHistories == null){
			out.write("没有该用户");
			out.flush();
			out.close();
			return;
		}
		String jiabanHours = "";
		String tiaoxiuHours = "";
		String wangdakaCounts = "";
		String dates = "";
		Collections.sort(userHistories);
		for (UserHistory userHistory : userHistories) {
			jiabanHours += userHistory.getJiabanHour() + ",";
			tiaoxiuHours += userHistory.getTiaoxiuHour() + ",";
			wangdakaCounts += userHistory.getWangdakaCount() + ",";
			dates += userHistory.getDate().replaceAll("-", "年") + "月,";
		}
		jiabanHours = jiabanHours.substring(0,jiabanHours.length() - 1);
		tiaoxiuHours = tiaoxiuHours.substring(0,tiaoxiuHours.length() - 1);
		wangdakaCounts = wangdakaCounts.substring(0,wangdakaCounts.length() - 1);
		dates = dates.substring(0,dates.length() - 1);
		out.write(jiabanHours+"|"+tiaoxiuHours+"|"+wangdakaCounts+"|"+dates);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/tongjiUserAjax")
	public void tongjiUserAjax(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		if(null == userId || "".equals(userId)){
			return;
		}
		//添加统计信息
		Map<String, BaseInfo> userMap = UserSave.fileToObject();
		if(null == userMap){
			return;
		}
		BaseInfo baseInfo = userMap.get(userId);
		if(null == baseInfo){
			return;
		}
		String date = baseInfo.getDate().replaceAll("年", "-").replaceAll("月", "");
		Map<String, TongjiUser> tongjiUsers = TongjiUserSave.fileToObject(date);
		if(null == tongjiUsers){
			tongjiUsers = new HashMap<String, TongjiUser>();
		}
		TongjiUser tongjiUser = tongjiUsers.get(userId);
		if(null == tongjiUser){
			tongjiUser = new TongjiUser();
			tongjiUser.setCount(1);
			tongjiUser.setLastUpdateTime(new Date());
			tongjiUser.setUserId(userId);
			tongjiUser.setUserName(baseInfo.getUserName());
		}else{
			tongjiUser = tongjiUsers.get(userId);
			tongjiUser.setCount(tongjiUser.getCount() + 1);
			tongjiUser.setLastUpdateTime(new Date());
		}
		tongjiUsers.put(userId, tongjiUser);
		//保存
		TongjiUserSave.objectToFile(tongjiUsers, date);
	}
	
	@RequestMapping(value="zhengli")
	public void zhengli(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String userId = request.getParameter("userId");
		String pwd = request.getParameter("pwd");
		PrintWriter out = response.getWriter();
		String date = request.getParameter("date");
		String holidays = request.getParameter("holidays");
		//判断用户名密码
		if(null == pwd || "".equals(pwd)){
			out.write("密码为空");
			out.flush();
			out.close();
			return;
		}
		Map<String, String> upMap = UpSave.fileToObject();
		if(null == upMap){
			out.write("查询不到密码记录");
			out.flush();
			out.close();
			return;
		}
		String pwdCache = upMap.get(userId);
		if(null == pwdCache){
			out.write("查询不到密码记录");
			out.flush();
			out.close();
			return;
		}
		if(!pwd.equals(pwdCache)){
			out.write("密码错误");
			out.flush();
			out.close();
			return;
		}
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
		String pwd = request.getParameter("pwd");
		//判断用户名密码
		if(null == pwd || "".equals(pwd)){
			return;
		}
		Map<String, String> upMap = UpSave.fileToObject();
		if(null == upMap){
			return;
		}
		String pwdCache = upMap.get(userId);
		if(null == pwdCache){
			return;
		}
		if(!pwd.equals(pwdCache)){
			return;
		}
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
		String type = "";
		String yearMonth = null;
		double jiabanHour = 0;
		double tiaoxiuHour = 0;
		int wangdakaCount = 0;
		List<UserHistory> userHistories = UserHisSave.fileToObject(userId);
		if(null == userHistories){
			userHistories = new ArrayList<UserHistory>();
		}
		UserHistory userHistory = new UserHistory();
		for (String kq : kaoqinStrs) {
			dsp = kq.split(" ");
			type = dsp[0];
			docBeans = dataMap.get(type);
			if(null == docBeans){
				docBeans = new ArrayList<DocBean>();
			}
			dateSplit = dsp[1].split("-");
			if(null == yearMonth){
				yearMonth = dateSplit[0]+"-"+dateSplit[1];
			}
			if("t".equals(type)){
				tiaoxiuHour += Double.parseDouble(dsp[4]);
			}else if("j".equals(type)){
				jiabanHour += Double.parseDouble(dsp[4]);
			}else if("w".equals(type)){
				wangdakaCount ++;
			}
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
		userHistory.setDate(yearMonth);
		userHistory.setInsertTime(new Date());
		userHistory.setJiabanHour(NumUtil.formatDouble(jiabanHour));
		userHistory.setTiaoxiuHour(NumUtil.formatDouble(tiaoxiuHour));
		userHistory.setWangdakaCount(wangdakaCount + "");
		//正式删除--开始
		//userHistory.setDate("2017-1");
		//正式删除--结束
		if(!userHistories.contains(userHistory)){
			userHistories.add(userHistory);
		}
		UserHisSave.objectToFile(userHistories, userId);
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
			DownLoadBean downLoadBean = DownLoadSave.fileToObject();
			if(downLoadBean == null){
				downLoadBean = new DownLoadBean();
				downLoadBean.setDownLoadJiaban(0);
				downLoadBean.setDownLoadTiaoxiu(0);
				downLoadBean.setPrintJiaban(0);
				downLoadBean.setPrintTiaoxiu(0);
			}
			downLoadBean.setDownLoadJiaban(downLoadBean.getDownLoadJiaban() + 1);
			DownLoadSave.objectToFile(downLoadBean);
		}else if(fileName.equals("tiaoxiu")){
			DownLoadBean downLoadBean = DownLoadSave.fileToObject();
			if(downLoadBean == null){
				downLoadBean = new DownLoadBean();
				downLoadBean.setDownLoadJiaban(0);
				downLoadBean.setDownLoadTiaoxiu(0);
				downLoadBean.setPrintJiaban(0);
				downLoadBean.setPrintTiaoxiu(0);
			}
			downLoadBean.setDownLoadTiaoxiu(downLoadBean.getDownLoadTiaoxiu() + 1);
			DownLoadSave.objectToFile(downLoadBean);
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
