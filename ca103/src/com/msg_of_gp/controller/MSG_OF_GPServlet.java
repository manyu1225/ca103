package com.msg_of_gp.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemVO;
import com.msg_of_gp.model.MSG_OF_GPService;
import com.msg_of_gp.model.MSG_OF_GPVO;

public class MSG_OF_GPServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//新增留言，還沒做空字串驗證功能
		if ("NEW_MSG".equals(action)) {
			MSG_OF_GPVO msgVO = new MSG_OF_GPVO(null, req.getParameter("gp_id"), ((MemVO) (req.getSession().getAttribute("memVO"))).getMem_id(), req.getParameter("msg_content"),
					new Timestamp(System.currentTimeMillis()), 0);
			MSG_OF_GPService msgSrc = new MSG_OF_GPService();
			msgSrc.newMessage(msgVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" + msgVO.getGp_id() + "&action=GP_SEARCH&tab=2#tabpage");
			
		}
		
		if ("DEL_MSG".equals(action)) {
			MSG_OF_GPVO msgVO = new MSG_OF_GPVO();
			msgVO.setMsg_id(req.getParameter("msg_id"));
			MSG_OF_GPService msgSrc = new MSG_OF_GPService();
			msgSrc.deleteMessage(msgVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" + req.getParameter("gp_id") + "&action=GP_SEARCH&tab=2#tabpage");
		}
	}
}
