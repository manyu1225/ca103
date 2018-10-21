package com.util.product;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.Part;

public class PorductTool {

	public static void main(String[]args) {
		Date date = new Date();                        // util.Date 物件拿到當前時間
		Long milSecFromd19700101 = date.getTime();      // date當前時間.getTime() =>  拿到當前時間的 從1970/1/1 起的毫秒數
		Timestamp sqlTimeStamp_2 = new Timestamp(milSecFromd19700101);  // 從1970/1/1 起的毫秒數轉換成 timeStamp
		System.out.println(sqlTimeStamp_2);
	} 
	
	
	public byte[] getbyte(Part photo) throws IOException {
		InputStream in = photo.getInputStream();
		byte[] productPhoto = new byte[in.available()];
		in.read(productPhoto);
		in.close();
		return productPhoto;
	}
	
	public Timestamp getCurrentTime() { //取得當下時間
		 Date date = new Date();                        // util.Date 物件拿到當前時間
		 Long milSecFromd19700101 = date.getTime();      // date當前時間.getTime() =>  拿到當前時間的 從1970/1/1 起的毫秒數
		 Timestamp sqlTimeStamp_2 = new Timestamp(milSecFromd19700101);  // 從1970/1/1 起的毫秒數轉換成 timeStamp
		 System.out.println(sqlTimeStamp_2);
		 
		return sqlTimeStamp_2;
		
	}

}
