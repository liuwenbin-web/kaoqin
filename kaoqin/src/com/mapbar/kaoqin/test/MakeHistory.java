package com.mapbar.kaoqin.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.mapbar.kaoqin.controller.UserHistory;
import com.mapbar.kaoqin.util.NumUtil;
import com.mapbar.kaoqin.util.UserHisSave;

public class MakeHistory {
	public static void main(String[] args) {
		List<UserHistory> userHistories = new ArrayList<UserHistory>();
		UserHistory userHistory = null;
		for(int i = 1;i < 13;i++){
			userHistory = new UserHistory();
			userHistory.setDate("2016-"+i);
			userHistory.setInsertTime(new Date());
			userHistory.setJiabanHour(NumUtil.formatDouble(0.5 * i));
			userHistory.setTiaoxiuHour(NumUtil.formatDouble(20 - 0.5 * i));
			userHistory.setWangdakaCount(i + "");
			userHistories.add(userHistory);
		}
		UserHisSave.objectToFile(userHistories, "23007");
		userHistories = UserHisSave.fileToObject("23007");
		System.out.println(new Gson().toJson(userHistories));
	}
}
