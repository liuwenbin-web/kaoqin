package com.mapbar.kaoqin.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.mapbar.kaoqin.bean.UserVisit;
import com.mapbar.kaoqin.util.UserVisitSave;

public class UserVisitTest {
	public static void main(String[] args) {
		List<UserVisit> userVisits = UserVisitSave.fileToObject();
		Collections.sort(userVisits);
		System.out.println(new Gson().toJson(userVisits));
	}
	public static void main5(String[] args) {
		String[] userIds = new String[]{"23007","23008","23009","23010","23011","23012"};
		String[] userNames = new String[]{"张毅","王浩","周鑫","宋万","程凯","李浩"};
		List<UserVisit> userVisits = new ArrayList<UserVisit>();
		for(int i = 0;i < 52;i++){
			UserVisit userVisit = new UserVisit();
			int count = new Random().nextInt(userIds.length);
			userVisit.setUserId(userIds[count]);
			userVisit.setUserName(userNames[count]);
			userVisit.setIp("127.0.0.1");
			userVisit.setLoginTime(new Date());
			userVisit.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:49.0) Gecko/20100101 Firefox/49.0");
			userVisits.add(userVisit);
			System.out.println(i);
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		UserVisitSave.objectToFile(userVisits);
	}
}
