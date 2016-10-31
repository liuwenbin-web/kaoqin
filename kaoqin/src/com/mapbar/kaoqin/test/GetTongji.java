package com.mapbar.kaoqin.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.mapbar.kaoqin.bean.TongjiUser;
import com.mapbar.kaoqin.util.TongjiUserSave;

public class GetTongji {
	public static void main2(String[] args) {
		//System.out.println(new Gson().toJson(TongjiUserSave.fileToObject("2016-09")));
		Map<String, Map<String, TongjiUser>> userHisMap = TongjiUserSave.getLast();
		System.out.println(userHisMap);
	}
	public static void main(String[] args) {
		String date = "";
		int type = 0;
		Map<String, TongjiUser> map = null;
		TongjiUser tongjiUser = null;
		for(int i = 1;i<13;i++){
			map = new HashMap<String, TongjiUser>();
			date = "2016-"+i;
			type = new Random().nextInt(5)+5;
			System.out.println(type);
			for(int j = 0;j<type;j++){
				tongjiUser = new TongjiUser();
				tongjiUser.setCount(new Random().nextInt(20)+1);
				tongjiUser.setLastUpdateTime(new Date());
				tongjiUser.setUserId(j+"");
				tongjiUser.setUserName("A"+j);
				map.put(j+"", tongjiUser);
			}
			TongjiUserSave.objectToFile(map, date);
		}
		Map<String, Map<String, TongjiUser>> userHisMap = TongjiUserSave.getLast();
		System.out.println(userHisMap);
	}
}
