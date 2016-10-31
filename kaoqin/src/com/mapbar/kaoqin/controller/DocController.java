package com.mapbar.kaoqin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mapbar.kaoqin.bean.BaseInfo;
import com.mapbar.kaoqin.bean.DocBean;
import com.mapbar.kaoqin.util.Constant;
import com.mapbar.kaoqin.util.KaoQinSave;
import com.mapbar.kaoqin.util.KaoQinUtil;
import com.mapbar.kaoqin.util.UserSave;

@Controller
@RequestMapping(value="/doc")
public class DocController {

	@RequestMapping(value="/tiaoxiu")
	public ModelAndView jiaban(HttpServletResponse response,HttpServletRequest request){
		String userId = request.getParameter("u");
		ModelAndView mav = new ModelAndView();
		if(null == userId || "".equals(userId)){
			mav.setViewName("forward:/download?fileName=tiaoxiu");
			return mav;
		}
		Map<String, Map<String, List<DocBean>>> map = KaoQinSave.fileToObject();
		if(null == map){
			mav.setViewName("forward:/download?fileName=tiaoxiu");
			return mav;
		}
		Map<String, List<DocBean>> kaoqinMap = map.get(userId);
		if(null == kaoqinMap){
			mav.setViewName("forward:/download?fileName=tiaoxiu");
			return mav;
		}
		List<DocBean> tiaoxius = kaoqinMap.get("t");
		if(null == tiaoxius){
			mav.setViewName("forward:/download?fileName=tiaoxiu");
			return mav;
		}
		Map<String, BaseInfo> userMap = UserSave.fileToObject();
		if(null == userMap){
			mav.setViewName("forward:/download?fileName=tiaoxiu");
			return mav;
		}
		BaseInfo baseInfo = userMap.get(userId);
		if(null == baseInfo){
			mav.setViewName("forward:/download?fileName=tiaoxiu");
			return mav;
		}
		mav.addObject("userName",baseInfo.getUserName());
		mav.setViewName("tiaoxiu");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		mav.addObject("date", format.format(new Date()));
		mav.addObject("department", "互联网技术部");
		//根据调休的条数判断需要打印多少页
		//添加忘打卡的记录
		//tiaoxius = new ArrayList<DocBean>();
		List<DocBean> wangdakas = kaoqinMap.get("w");
		if(null != wangdakas && 0 != wangdakas.size()){
			int wangdakaSize = wangdakas.size();
			for(int i = 0;i<wangdakaSize;i++){
				tiaoxius.add(new DocBean());
			}
			mav.addObject("hasWangdaka", "1");
		}
		//正式删除--开始
		//tiaoxius.addAll(tiaoxius);
		//tiaoxius.addAll(tiaoxius);
		//tiaoxius.addAll(tiaoxius);
		
		//正式删除--结束
		int yushu = tiaoxius.size() % Constant.tiaoXiuDanCount;
		if(yushu != 0){
			int remind = Constant.tiaoXiuDanCount - yushu;
			for(int i  = 0;i<remind;i++){
				tiaoxius.add(new DocBean());
			}
		}
		int page = tiaoxius.size() / Constant.tiaoXiuDanCount;
		List<List<DocBean>> pages = new ArrayList<List<DocBean>>();
		List<DocBean> docBeans = null;
		Map<Integer, String> totalHourMap = new HashMap<Integer, String>();
		for(int i = 1;i <= page;i++){
			docBeans = tiaoxius.subList((i-1) * Constant.tiaoXiuDanCount, i * Constant.tiaoXiuDanCount);
			pages.add(docBeans);
			totalHourMap.put(i, KaoQinUtil.getTotalHour(docBeans));
		}
		mav.addObject("pages", pages);
		mav.addObject("totalHourMap", totalHourMap);
		mav.addObject("wangdakaList", wangdakas);
		return mav;
	}
}
