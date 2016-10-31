package com.mapbar.kaoqin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class HolidaySave {

	public static void objectToFile(Map<String, String> map) {
		try {
			File file = new File(Constant.dataPath + "holiday.ser");
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(Constant.dataPath + "holiday.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(map);
			os.close();
			fs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Map<String, String> fileToObject() {
		try {
			FileInputStream fs = new FileInputStream(Constant.dataPath + "holiday.ser");
			ObjectInputStream oi = new ObjectInputStream(fs);
			Map<String, String> map = (Map<String, String>) oi.readObject();
			oi.close();
			fs.close();
			return map;
		} catch (Exception ex) {
		}
		return null;
	}
}
