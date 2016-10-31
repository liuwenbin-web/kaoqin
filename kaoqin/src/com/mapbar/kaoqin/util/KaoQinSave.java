package com.mapbar.kaoqin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

import com.mapbar.kaoqin.bean.DocBean;

public class KaoQinSave {
	//姓名=（类型 =（考勤列表））
	public static void objectToFile(Map<String, Map<String, List<DocBean>>> map) {
		try {
			File file = new File(Constant.dataPath + "kaoqin.ser");
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(Constant.dataPath + "kaoqin.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(map);
			os.close();
			fs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static Map<String, Map<String, List<DocBean>>> fileToObject() {
		try {
			FileInputStream fs = new FileInputStream(Constant.dataPath + "kaoqin.ser");
			ObjectInputStream oi = new ObjectInputStream(fs);
			Map<String, Map<String, List<DocBean>>> map = (Map<String, Map<String, List<DocBean>>>) oi.readObject();
			oi.close();
			fs.close();
			return map;
		} catch (Exception ex) {
		}
		return null;
	}
}
