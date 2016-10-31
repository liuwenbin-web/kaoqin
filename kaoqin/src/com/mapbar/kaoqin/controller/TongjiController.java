package com.mapbar.kaoqin.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.jcraft.jsch.MAC;
import com.mapbar.kaoqin.bean.BaseInfo;
import com.mapbar.kaoqin.bean.TongjiUser;
import com.mapbar.kaoqin.bean.UserVisit;
import com.mapbar.kaoqin.util.DownLoadSave;
import com.mapbar.kaoqin.util.TongjiUserSave;
import com.mapbar.kaoqin.util.UserHisSave;
import com.mapbar.kaoqin.util.UserSave;
import com.mapbar.kaoqin.util.UserVisitSave;

@Controller
@RequestMapping(value="/ECE9CEABECEA894FF272E6D96A59BDEF")
public class TongjiController {
	
	@RequestMapping(value="")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("ECE9CEABECEA894FF272E6D96A59BDEF");
		String userId = request.getParameter("userId");
		Map<String, BaseInfo> bMap = UserSave.fileToObject();
		if(null == userId || "".equals(userId) || !"23007".equals(userId)){
			mav.setViewName("user");
			return mav;
		}
		BaseInfo baseInfo = bMap.get(userId);
		if(baseInfo == null){
			mav.setViewName("user");
			return mav;
		}
		String date = baseInfo.getDate().replace("年", "-").replaceAll("月", "");
		if(date.length() == 6){
			date = date.replaceAll("-", "-0");
		}
		mav.addObject("date",date);
		Map<String, String> idToNameMap = new TreeMap<String, String>();
		//Map<String, TongjiUser> tongjiUserMap = TongjiUserSave.fileToObject(date);
		//mav.addObject("tongjiUserMap",tongjiUserMap);
		mav.addObject("hisDates", TongjiUserSave.getDates());
		Map<String, Map<String, TongjiUser>> userHisMap = TongjiUserSave.getLast();
		mav.addObject("userHisMap", userHisMap);
		//获取历史用户数和总访问数
		String userCount = "[";
		String visitCount = "[";
		String dates = "[";
		Set<String> dateKeys = userHisMap.keySet();
		Map<String, TongjiUser> userMap = null;
		long currentTotal = 0;
		Set<String> tongjiUsers = null;
		TongjiUser tongjiUser = null; 
		for (String dateKey : dateKeys) {
			currentTotal = 0;
			dates += "'"+dateKey+"',";
			userMap = userHisMap.get(dateKey);
			userCount += userMap.size() + ",";
			tongjiUsers = userMap.keySet();
			for (String tongjiUserKey : tongjiUsers) {
				tongjiUser = userMap.get(tongjiUserKey);
				currentTotal += tongjiUser.getCount();
				idToNameMap.put(tongjiUser.getUserId(), tongjiUser.getUserName());
			}
			visitCount += currentTotal + ",";
		}
		userCount = userCount.substring(0,userCount.length() - 1)+"]";
		visitCount = visitCount.substring(0,visitCount.length() - 1)+"]";
		dates = dates.substring(0,dates.length() - 1)+"]";
		mav.addObject("downLoad", DownLoadSave.fileToObject());
		mav.addObject("userCount", userCount);
		mav.addObject("visitCount", visitCount);
		mav.addObject("dates", dates);
		mav.addObject("idToNameMap",idToNameMap);
		return mav;
	}
	
	@RequestMapping("/getVisitTableAjax")
	public void getVisitTableAjax(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String date = request.getParameter("date");
		Map<String, TongjiUser> map = TongjiUserSave.fileToObject(date);
		if(null == map){
			out.write(date + "数据不存在");
			out.flush();
			out.close();
			return;
		}
		out.write(new Gson().toJson(map));
		out.flush();
		out.close();
	}
	
	@RequestMapping("/history")
	public void getHistory(HttpServletRequest request,HttpServletResponse response) throws Exception{
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
	
	@RequestMapping(value="/getUserVisitAjax")
	public void getUserVisitAjax(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		List<UserVisit> userVisits = UserVisitSave.fileToObject();
		if(null == userVisits){
			userVisits = new ArrayList<UserVisit>();
		}
		Collections.sort(userVisits);
		out.write(new Gson().toJson(userVisits));
		out.flush();
		out.close();
	}
}
