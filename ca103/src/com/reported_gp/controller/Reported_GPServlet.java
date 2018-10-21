package com.reported_gp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friends.model.FriendsService;
import com.friends.model.FriendsVO;
import com.gp.model.GPService;
import com.gp.model.GPVO;
import com.mem.model.MemVO;
import com.message.model.MesService;
import com.message.model.MesVO;
import com.reported_gp.model.Reported_GPService;
import com.reported_gp.model.Reported_GPVO;

public class Reported_GPServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if("GP_REPORTED".equals(action)) {
			String mem_id = ((MemVO) (req.getSession().getAttribute("memVO"))).getMem_id();
			String reported = "true";
			Reported_GPService repSrc = new Reported_GPService();
			Reported_GPVO repVO = new Reported_GPVO(mem_id, req.getParameter("gp_id"), new Timestamp(System.currentTimeMillis()), req.getParameter("rep_detail"), 0);
			try {
				repSrc.addRepGP(repVO);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				reported = "false";
			}
			
			PrintWriter out = res.getWriter();
			out.write(reported);
		}

		if("REPSUCCESS".equals(action)) {
			
//			更改揪團狀態為被檢舉成功
			GPService gpSrc = new GPService();
			GPVO gpVO = new GPVO();
			gpVO.setGp_id(req.getParameter("gp_id"));
			gpVO.setGp_status(10);
			gpSrc.updateStatus(gpVO);
			
			//寄信給被檢舉者
			GPVO gpVO2 = gpSrc.searchGPVOById(req.getParameter("gp_id"));
			try {
				
				MesService mesSvc = new MesService();
				MesVO mesVO = new MesVO();
				mesVO.setLogin_id("M999999");
				mesVO.setReceive_id(gpVO2.getMem_id());
				mesVO.setChat_text("你的揪團 " + gpVO2.getGp_title() + " 因經營不當，已 被封鎖");
				mesVO.setChat_title("系統訊息");
				mesVO.setLogin_nickname("系統管理員");
				mesSvc.insert(mesVO);
			}catch(Exception e) {
				System.out.println("系統發送出現錯誤");
			}
//			更改檢舉狀態
			Reported_GPService repSrc = new Reported_GPService();
			Reported_GPVO repVO = new Reported_GPVO(req.getParameter("mem_id"),req.getParameter("gp_id"),null,null,null);
			repSrc.updateRepGP(repVO, 1);
			res.sendRedirect(req.getContextPath() + "/back-end/gp/GP_Report.jsp?tab_list=tab1");
		}
		
		if("REPFAIL".equals(action)) {
			System.out.println("近來駁回");
			Reported_GPService repSrc = new Reported_GPService();
			Reported_GPVO repVO = new Reported_GPVO(req.getParameter("mem_id"),req.getParameter("gp_id"),null,null,null);
			repSrc.updateRepGP(repVO, 2);
			res.sendRedirect(req.getContextPath() + "/back-end/gp/GP_Report.jsp?tab_list=tab1");
		}
	}
}
