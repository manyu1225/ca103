package com.util.product;

import java.sql.Timestamp;
import java.util.Date;

public class GetTimeDay {
	public Timestamp getTimeDay(int DayRange) {//取得Ｘ天
		 Date date = new Date();                        // util.Date 物件拿到當前時間
		 Long milSecFromd19700101 = date.getTime();      // date當前時間.getTime() =>  拿到當前時間的 從1970/1/1 起的毫秒數
		 Long DayTime = milSecFromd19700101 + DayRange*24*60*60*1000; //增加一天的時間 1d * 24h * 60m * 60s * 1000ms
		//  java.sql.Date sqlDate = new java.sql.Date(milSecFromd19700101); //sql.Date  只有記錄到Date(日期)會缺少時分秒,所以依情況選用   
//		 Timestamp sqlTimeStamp = new Timestamp(milSecFromd19700101); // sql.Timestamp  拿到當前毫秒時間轉換的 timeStamp
		 Timestamp sqlTimeStamp_2 = new Timestamp(DayTime);  // sql.Timestamp  拿到加一天毫秒時間轉換的 timeStamp
		 
		return sqlTimeStamp_2;
	}
}
