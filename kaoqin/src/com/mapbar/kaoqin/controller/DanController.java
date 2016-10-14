package com.mapbar.kaoqin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mapbar.kaoqin.bean.BaseInfo;
import com.mapbar.kaoqin.bean.DocBean;
import com.mapbar.kaoqin.util.KaoQinSave;
import com.mapbar.kaoqin.util.UserSave;

@Controller
@RequestMapping(value="/doc")
public class DanController {

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
		mav.addObject("list", tiaoxius.subList(0, 10));
		return mav;
	}
}
