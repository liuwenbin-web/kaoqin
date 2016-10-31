package com.mapbar.kaoqin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.mapbar.kaoqin.bean.BaseInfo;

public class UserSave {

	public static void objectToFile(Map<String, BaseInfo> map) {
		try {
			File file = new File(Constant.dataPath + "user.ser");
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(Constant.dataPath + "user.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(map);
			os.close();
			fs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Map<String, BaseInfo> fileToObject() {
		try {
			FileInputStream fs = new FileInputStream(Constant.dataPath + "user.ser");
			ObjectInputStream oi = new ObjectInputStream(fs);
			Map<String, BaseInfo> map = (Map<String, BaseInfo>) oi.readObject();
			oi.close();
			fs.close();
			return map;
		} catch (Exception ex) {
		}
		return null;
	}
}
