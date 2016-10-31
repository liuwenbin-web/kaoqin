package com.mapbar.kaoqin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.mapbar.kaoqin.bean.UserVisit;

public class UserVisitSave {

	public static void objectToFile(List<UserVisit> userVisits) {
		int begin = 0;
		int total = userVisits.size();
		if(total > 50){
			begin = total - 50;
			List<UserVisit> uvs = new ArrayList<UserVisit>();
			for(int i = begin;i<total;i++){
				uvs.add(userVisits.get(i));
			}
			userVisits = uvs;
		}
		try {
			File file = new File(Constant.dataPath + "userVisit.ser");
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(Constant.dataPath + "userVisit.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(userVisits);
			os.close();
			fs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static List<UserVisit> fileToObject() {
		try {
			FileInputStream fs = new FileInputStream(Constant.dataPath + "userVisit.ser");
			ObjectInputStream oi = new ObjectInputStream(fs);
			List<UserVisit> map = (List<UserVisit>) oi.readObject();
			oi.close();
			fs.close();
			return map;
		} catch (Exception ex) {
		}
		return null;
	}
}
