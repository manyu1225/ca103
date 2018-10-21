package com.util;


import java.util.Timer;
import java.util.TimerTask;

import com.gp.model.GPService;
import com.gp.model.GPVO;

public class GP_End_Date {
	
	private GPVO gpVO;
	private Timer t = new Timer();
	
	//揪團揪團的task
	TimerTask task = new TimerTask() {
		public void run() {
			gpVO.setGp_status(2);//2為結束的揪團
			GPService gpSrc = new GPService();
			gpSrc.updateStatus(gpVO);
			System.out.println("結束揪團");
			t.cancel();
		}
	};

	public void endSet() {
		t.schedule(task, gpVO.getGp_date().getTime() + (long) gpVO.getGp_hour()*3600*1000);
	}
	
	public GP_End_Date(GPVO gpVO) {
		this.gpVO = gpVO;
	}

}
