package com.reply_of_msg.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemVO;
import com.msg_of_gp.model.MSG_OF_GPService;
import com.msg_of_gp.model.MSG_OF_GPVO;
import com.reply_of_msg.model.Reply_of_MSGService;
import com.reply_of_msg.model.Reply_of_MSGVO;

public class Reply_of_MSGServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("NEW_REPLY".equals(action)) {
			
			Reply_of_MSGVO replyVO = new Reply_of_MSGVO(null, req.getParameter("msg_id"),((MemVO) (req.getSession().getAttribute("memVO"))).getMem_id(),
					req.getParameter("reply_content"), new Timestamp(System.currentTimeMillis()), 0);

			Reply_of_MSGService replySrc = new Reply_of_MSGService();
			replySrc.newReply(replyVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" + req.getParameter("gp_id") + "&action=GP_SEARCH&tab=2#tabpage");
		}
		if ("DEL_REPLY".equals(action)) {
			Reply_of_MSGVO replyVO = new Reply_of_MSGVO();
			replyVO.setReply_id(req.getParameter("reply_id"));
			Reply_of_MSGService replySrc = new Reply_of_MSGService();
			replySrc.deleteReply(replyVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" + req.getParameter("gp_id") + "&action=GP_SEARCH&tab=2#tabpage");
		}
		
	}
}
