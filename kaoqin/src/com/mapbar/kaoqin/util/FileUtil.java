package com.mapbar.kaoqin.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileUtil {
	public static void write(String filePath,String content) throws Exception{
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath),true));
		bufferedWriter.write(content+"\r\n");
		bufferedWriter.close();
	}
}