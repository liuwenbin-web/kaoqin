package com.mapbar.kaoqin.controller;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mapbar.kaoqin.bean.BaseInfo;
import com.mapbar.kaoqin.util.Constant;
import com.mapbar.kaoqin.util.HolidayUtil;
import com.mapbar.kaoqin.util.ReadExcel;

@Controller
@RequestMapping(value = "")
public class UserController {

	@RequestMapping(value = "")
	public ModelAndView user(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user");
		return mav;
	}

	@RequestMapping(value = "getBaseInfo")
	public @ResponseBody BaseInfo getBaseInfo(HttpServletRequest request,HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		if(null == fileName || "".equals(fileName)){
			return null;
		}
		System.out.println(Constant.uploadPath+fileName);
		if(!new File(Constant.uploadPath+fileName).exists()){
			return null;
		}
		BaseInfo baseInfo = ReadExcel.getBaseInfo(Constant.uploadPath+fileName);
		return baseInfo;
	}
	
	@RequestMapping(value = "getHoliday")
	public void getHoliday(HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String yearStr = request.getParameter("year");
		String monthStr = request.getParameter("month");
		if(null == yearStr || "".equals(yearStr)){
			out.write("");
			out.flush();
			out.close();
			return;
		}
		if(null == monthStr || "".equals(monthStr)){
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
		if(0 == year || 0 == month){
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
	public void uploadPhoto(@RequestParam MultipartFile kqFile,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("in");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			if (kqFile != null && !kqFile.isEmpty()) {
				String originalFilename = kqFile.getOriginalFilename();
				if(null != originalFilename && originalFilename.endsWith(".xls")){
					String newFileName = originalFilename.replaceAll(".xls", "_"+System.currentTimeMillis()+".xls");
					FileUtils.copyInputStreamToFile(kqFile.getInputStream(),new File(Constant.uploadPath, newFileName));
					boolean isKq = ReadExcel.isKaoqinFile(Constant.uploadPath+newFileName);
					if(!isKq){
						new File(Constant.uploadPath+newFileName).delete();
						out.write("不是标准的考勤文件，请重新上传");
						out.flush();
						out.close();
					}else{
						out.write("上传成功:"+newFileName);
					}
				}else{
					out.write("请上传考勤excel文件");
					out.flush();
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}