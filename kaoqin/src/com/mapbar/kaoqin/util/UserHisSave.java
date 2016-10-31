package com.mapbar.kaoqin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.mapbar.kaoqin.controller.UserHistory;

public class UserHisSave {
	public static void objectToFile(List<UserHistory> userHistorys,String userId) {
		int totalSize = userHistorys.size();
		if(totalSize > 12){
			List<UserHistory> ul = new ArrayList<UserHistory>();
			int begin = totalSize - 12;
			for(int i = begin;i < totalSize;i++){
				ul.add(userHistorys.get(i));
			}
			userHistorys = ul;
		}
		try {
			File file = new File(Constant.dataPath + "userHistory/" + userId + ".ser");
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(Constant.dataPath + "userHistory/" + userId + ".ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(userHistorys);
			os.close();
			fs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static List<UserHistory> fileToObject(String userId) {
		if(!new File(Constant.dataPath + "userHistory/" + userId + ".ser").exists()){
			return null;
		}
		try {
			FileInputStream fs = new FileInputStream(Constant.dataPath + "userHistory/" + userId + ".ser");
			ObjectInputStream oi = new ObjectInputStream(fs);
			List<UserHistory> userHistorys = (List<UserHistory>) oi.readObject();
			oi.close();
			fs.close();
			return userHistorys;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
