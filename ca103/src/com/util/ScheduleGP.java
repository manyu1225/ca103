package com.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gp.model.GPService;
import com.gp.model.GPVO;
import com.sun.jmx.snmp.Timestamp;

public class ScheduleGP extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private String time;
	private Timer expiryTimer = new Timer();
	private Timer overTimer = new Timer();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath()).append(time);
		
	}
	
	public void init() throws ServletException {
		super.init();
		TimerTask expiryTask = new TimerTask() {
			public void run() {
				GPService gpSrc = new GPService();
				List<GPVO> gpList = gpSrc.getAllGP();
				for(GPVO gpVO:gpList) {
					if(gpVO.getSign_up_DD().getTime() <= System.currentTimeMillis()) {
						gpVO.setGp_status(1);
						gpSrc.updateStatus(gpVO);
						System.out.println("已到期：" + gpVO.getGp_id());
					}
				}
			}
		};
		TimerTask overTask = new TimerTask() {
			public void run() {
				GPService gpSrc = new GPService();
				List<GPVO> gpList = gpSrc.getAllGP();
				for(GPVO gpVO:gpList) {
					if(gpVO.getGp_date().getTime() + (long)1000*60*60*gpVO.getGp_hour() <= System.currentTimeMillis()) {
						gpVO.setGp_status(2);
						gpSrc.updateStatus(gpVO);
						System.out.println("已結束：" + gpVO.getGp_id());
					}
				}
			}
		};
//		t.scheduleAtFixedRate(task, new GregorianCalendar(2018, Calendar.JULY, 21, 21, 0, 0).getTime(), 1*20*1000);
		expiryTimer.scheduleAtFixedRate(expiryTask, 5*2000, 10*60*1000);
		overTimer.scheduleAtFixedRate(overTask, 5*2000, 10*60*1000);
	}
	
	public void destroy() {
		expiryTimer.cancel();
		super.destroy();
	
	}
}
