package com.util.product.sms;

import java.util.Date;
import java.util.TimerTask;

import com.secondShop.product.model.ProductService;

public class NotiftyMessage  extends TimerTask{
	Send se = new Send();
	String[] phone;
	String message ;
	public NotiftyMessage() {
		super();
	}
	public NotiftyMessage(String[]phone, String message) {
		super();
		this.phone = phone;
		this.message = message;
	}
	@Override
	public void run() {
	        se.sendMessage(phone , message);
	        System.out.println("我的內容"+message);
	        System.out.println("我的電話"+phone);
	        System.out.println("我要發簡訊囉"+new Date());
		
	}
}
