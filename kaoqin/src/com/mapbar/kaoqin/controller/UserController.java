package com.mapbar.kaoqin.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.mapbar.kaoqin.bean.BaseInfo;
import com.mapbar.kaoqin.bean.UserVisit;
import com.mapbar.kaoqin.util.Constant;
import com.mapbar.kaoqin.util.HolidayUtil;
import com.mapbar.kaoqin.util.IpUtil;
import com.mapbar.kaoqin.util.ReadExcel;
import com.mapbar.kaoqin.util.UpSave;
import com.mapbar.kaoqin.util.UserVisitSave;

@Controller
@RequestMapping(value = "")
public class UserController {

	private static Map<String, String> fileMap = new ConcurrentHashMap<String, String>();
	@RequestMapping(value = "")
	public ModelAndView user(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user");
		return mav;
	}

	@RequestMapping(value = "getBaseInfo")
	public @ResponseBody BaseInfo getBaseInfo(HttpServletRequest request,HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		if (null == fileName || "".equals(fileName)) {
			return null;
		}
		if (!new File(Constant.uploadPath + fileName).exists()) {
			return null;
		}
		BaseInfo baseInfo = ReadExcel.getBaseInfo(Constant.uploadPath + fileName);
		return baseInfo;
	}

	@RequestMapping(value="getUp")
	public void getUp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userId");
		//获取用户的密码。看看是否存在
		Map<String, String> upMap = UpSave.fileToObject();
		if(null == upMap){
			out.write("first");
			out.flush();
			out.close();
			return;
		}
		if(null == userId || "".equals(userId)){
			out.write("noUser");
			out.flush();
			out.close();
			return;
		}
		String pwd = upMap.get(userId);
		if(null == pwd){
			out.write("first");
			out.flush();
			out.close();
			return;
		}
		out.write("exist");
		out.flush();
		out.close();
		return;
	}
	
	@RequestMapping(value="setUp")
	public void setUp(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String userId = request.getParameter("userId");
		String pwd = request.getParameter("pwd");
		if(null == userId || "".equals(userId)){
			out.write("用户不存在，请刷新页面重试");
			out.flush();
			out.close();
			return;
		}
		if(null == pwd || "".equals(pwd)){
			out.write("密码为空，请重新填写");
			out.flush();
			out.close();
			return;
		}
		Map<String, String> upMap = UpSave.fileToObject();
		if(null == upMap){
			upMap = new HashMap<String, String>();
		}
		String pwdCache = upMap.get(userId);
		if(null == pwdCache){
			upMap.put(userId, pwd);
			//保存
			UpSave.objectToFile(upMap);
			out.write("success");
			out.flush();
			out.close();
			return;
		}else{
			//验证密码是否正确
			if(pwd.endsWith(pwdCache)){
				out.write("success");
				out.flush();
				out.close();
			}else{
				out.write("密码错误");
				out.flush();
				out.close();
			}
		}
	}
	
	@RequestMapping(value = "getHoliday")
	public void getHoliday(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String yearStr = request.getParameter("year");
		String monthStr = request.getParameter("month");
		if (null == yearStr || "".equals(yearStr)) {
			out.write("");
			out.flush();
			out.close();
			return;
		}
		if (null == monthStr || "".equals(monthStr)) {
			out.write("");
			out.flush();
			out.close();
			return;
		}
		int year = 0;
		int month = 0;
		try {
			year = Integer.parseInt(yearStr);
			month = Integer.parseInt(monthStr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (0 == year || 0 == month) {
			out.write("");
			out.flush();
			out.close();
			return;
		}
		out.write(HolidayUtil.get(year, month));
		out.flush();
		out.close();
	}

	@RequestMapping(value = "uploadFileAjax")
	public void uploadFileAjax(@RequestParam MultipartFile kqFile,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if (kqFile != null && !kqFile.isEmpty()) {
				String originalFilename = kqFile.getOriginalFilename();
				if (null != originalFilename
						&& originalFilename.endsWith(".xls")) {
					String newFileName = originalFilename.replaceAll(".xls",
							"_" + System.currentTimeMillis() + ".xls");
					FileUtils.copyInputStreamToFile(kqFile.getInputStream(),
							new File(Constant.uploadPath, newFileName));
					boolean isKq = ReadExcel.isKaoqinFile(Constant.uploadPath
							+ newFileName);
					if (!isKq) {
						new File(Constant.uploadPath + newFileName).delete();
						out.write("不是标准的考勤文件，请重新上传");
						out.flush();
						out.close();
					} else {
						out.write("上传成功:" + newFileName);
					}
				} else {
					out.write("请上传考勤excel文件");
					out.flush();
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "uploadKaoQinAjax")
	public void uploadKaoQinAjax(HttpServletRequest request,HttpServletResponse response) throws Exception {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					String newFileName = file.getOriginalFilename().replaceAll(".xls","_drag" + System.currentTimeMillis() + ".xls");
					String path = Constant.uploadPath + newFileName;
					file.transferTo(new File(path));
					fileMap.put(file.getOriginalFilename(), newFileName);
				}
			}
		}
	}
	
	@RequestMapping(value = "getDragRealNameAjax")
	public void getDragRealNameAjax(HttpServletRequest request,HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String fileName = request.getParameter("fileName");
		if(null != fileName && !"".equals(fileName)){
			out.write(fileMap.get(fileName));
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping(value="setUserVisitAjax")
	public void setUserVisitAjax(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		List<UserVisit> userVisits = UserVisitSave.fileToObject();
		if(null == userVisits){
			userVisits = new ArrayList<UserVisit>();
		}
		UserVisit userVisit = new UserVisit();
		userVisit.setIp(IpUtil.getIpAddress(request));
		userVisit.setLoginTime(new Date());
		userVisit.setUserAgent(request.getHeader("User-Agent"));
		userVisit.setUserId(userId);
		userVisit.setUserName(userName);
		userVisits.add(userVisit);
		UserVisitSave.objectToFile(userVisits);
	}
}