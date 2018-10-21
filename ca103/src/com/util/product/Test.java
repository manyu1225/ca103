package com.util.product;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

public class Test {
public static void main(String []args) {
	                     
	Date date = new Date();                        // util.Date 物件拿到當前時間
	long AD_FINISH_MILSECOND =date.getTime()+(1000*60); //測試廣告結束時間的變數 預5分鐘秒 5M*1S*1MM
	Timestamp sqlTimeStamp_2 = new Timestamp(AD_FINISH_MILSECOND);
	System.out.println("下架時間是"+sqlTimeStamp_2);
	
	
	 Long milSecFromd19700101 = new Date().getTime();      // date當前時間.getTime() =>  拿到當前時間的 從1970/1/1 起的毫秒數
	 Long afterTimeAndRunWork = milSecFromd19700101+ (5*1000);  //5秒後
	 Date dateAfterTimeAndRunWork = new Date(afterTimeAndRunWork);
	 System.out.println("main running~ : " + new Date());
	 
	 Timer timer = new Timer();
//	 timer.schedule(new work(), 21*24*60*60*1000);
	 timer.schedule(new work(), dateAfterTimeAndRunWork);
	} 
 
}

 class work extends TimerTask{
	 
	@Override
	public void run() {
		 System.out.println("This is Task"); 
         System.out.println("tsak running~ : "+ new Date());        
		
	}
}
