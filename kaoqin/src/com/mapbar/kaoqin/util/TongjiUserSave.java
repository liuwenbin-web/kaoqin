package com.mapbar.kaoqin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.mapbar.kaoqin.bean.TongjiUser;

public class TongjiUserSave {
	public static void objectToFile(Map<String, TongjiUser> tongjiUsers,String date) {
		try {
			if(date.length() == 6){
				date = date.replaceAll("-", "-0");
			}
			File file = new File(Constant.dataPath + "tongji/" + date + ".ser");
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(Constant.dataPath + "tongji/" + date + ".ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(tongjiUsers);
			os.close();
			fs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static TreeMap<String, Map<String, TongjiUser>> getLast(){
		TreeMap<String, Map<String, TongjiUser>> map = new TreeMap<String, Map<String, TongjiUser>>();
		File file = new File(Constant.dataPath + "tongji/");
		if(!file.exists()){
			file.mkdirs();
		}
		File[] files = file.listFiles();
		int begin = 0;
		int total = files.length;
		if(total > 12){
			begin = total - 12;
		}
		String date = null;
		File f = null;
		for(int i=begin;i<total;i++){
			f = files[i];
			if(f.getName().equals(".DS_Store")){
				continue;
			}
			date = f.getName().replaceAll(".ser", "");
			map.put(date, fileNameToObject(f.getAbsolutePath()));
		}
		return map;
	}
	
	public static Map<String, TongjiUser> fileToObject(String date) {
		if(date.length() == 6){
			date = date.replaceAll("-", "-0");
		}
		if(!new File(Constant.dataPath + "tongji/" + date + ".ser").exists()){
			return null;
		}
		try {
			FileInputStream fs = new FileInputStream(Constant.dataPath + "tongji/" + date + ".ser");
			ObjectInputStream oi = new ObjectInputStream(fs);
			Map<String, TongjiUser> tongjiUsers = (Map<String, TongjiUser>) oi.readObject();
			oi.close();
			fs.close();
			return tongjiUsers;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	public static List<String> getDates(){
		List<String> dates = new ArrayList<String>();
		File file = new File(Constant.dataPath + "tongji/");
		if(!file.exists()){
			file.mkdirs();
		}
		File[] files = file.listFiles();
		String fileName = null;
		for (File f : files) {
			fileName = f.getName();
			if(fileName.equals(".DS_Store")){
				continue;
			}
			dates.add(fileName.replaceAll(".ser", ""));
		}
		return dates;
	}
	
	private static Map<String, TongjiUser> fileNameToObject(String fileName) {
		try {
			FileInputStream fs = new FileInputStream(fileName);
			ObjectInputStream oi = new ObjectInputStream(fs);
			Map<String, TongjiUser> tongjiUsers = (Map<String, TongjiUser>) oi.readObject();
			oi.close();
			fs.close();
			return tongjiUsers;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
