package com.mapbar.kaoqin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.mapbar.kaoqin.bean.DownLoadBean;

public class DownLoadSave {
	public static void objectToFile(DownLoadBean dlb) {
		try {
			File file = new File(Constant.dataPath + "download.ser");
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			FileOutputStream fs = new FileOutputStream(Constant.dataPath + "download.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(dlb);
			os.close();
			fs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static DownLoadBean fileToObject() {
		try {
			FileInputStream fs = new FileInputStream(Constant.dataPath + "download.ser");
			ObjectInputStream oi = new ObjectInputStream(fs);
			DownLoadBean dlb = (DownLoadBean) oi.readObject();
			oi.close();
			fs.close();
			return dlb;
		} catch (Exception ex) {
		}
		return null;
	}
}
