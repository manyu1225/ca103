package com.joined_gp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.gp.model.GPService;
import com.joined_gp.model.JoinedGPService;
import com.joined_gp.model.JoinedGPVO;
import com.mem.model.MemVO;
import com.message.model.MesService;
import com.message.model.MesVO;

public class Joined_GPServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String mem_id = ((MemVO) (req.getSession().getAttribute("memVO"))).getMem_id();
		
		
		if("GP_JOIN".equals(action)) {
			String joined = "true";
			JoinedGPVO jGPVO = new JoinedGPVO(mem_id, req.getParameter("gp_id"),new Timestamp(System.currentTimeMillis()), 0, null, 0);
			
			JoinedGPService jgpSrc = new JoinedGPService();
			try {
				jgpSrc.joinGP(jGPVO);
			}catch (Exception e) {
//				重複加入揪團了
				System.out.println(e.getMessage());
				joined = "false";
			}
			PrintWriter out = res.getWriter();
			out.write(joined);
		}
		
		if("GP_VIEWING_JOIN".equals(action)) {
			JoinedGPVO jGPVO = new JoinedGPVO(mem_id, req.getParameter("gp_id"),new Timestamp(System.currentTimeMillis()), 0, null, 0);
			JoinedGPService jgpSrc = new JoinedGPService();
			jgpSrc.joinGP(jGPVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" +  req.getParameter("gp_id") + "&action=GP_SEARCH");
		}
		if("GP_VIEWING_UNJOIN".equals(action)) {
			JoinedGPVO jGPVO = new JoinedGPVO(mem_id, req.getParameter("gp_id"),null, 0, null, 0);
			JoinedGPService jgpSrc = new JoinedGPService();
			jgpSrc.delete(jGPVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" +  req.getParameter("gp_id") + "&action=GP_SEARCH");
		}
		
		
		if("GP_JOINED_DEL".equals(action)) {
			JoinedGPVO jGPVO = new JoinedGPVO(mem_id, req.getParameter("gp_id"),null, 0, null, 0);
			JoinedGPService jgpSrc = new JoinedGPService();
			jgpSrc.delete(jGPVO);
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/gp/GP_List.jsp?listPage=1");
			successView.forward(req, res);
		}
		
		if("GP_JOINED_KICK".equals(action)) {
			JoinedGPVO jGPVO = new JoinedGPVO(req.getParameter("mem_id"), req.getParameter("gp_id"),null, 0, null, 0);
			JoinedGPService jgpSrc = new JoinedGPService();
			jgpSrc.delete(jGPVO);
			
			try {
				GPService gpSrc = new GPService();
				MesService mesSvc = new MesService();
				MesVO mesVO = new MesVO();
				mesVO.setLogin_id("M999999");
				mesVO.setReceive_id(req.getParameter("mem_id"));
				mesVO.setChat_text("你已被踢出揪團： " + gpSrc.searchGPVOById(req.getParameter("gp_id")).getGp_title());
				mesVO.setChat_title("系統訊息");
				mesVO.setLogin_nickname("系統管理員");
				mesSvc.insert(mesVO);
			}catch(Exception e) {
				System.out.println("系統發送出現錯誤");
			}
			
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" +  req.getParameter("gp_id") + "&action=GP_SEARCH&tab=3#tabpage");
		}
		
		if("SET_PMSN".equals(action)) {
			JoinedGPVO jGPVO = new JoinedGPVO(req.getParameter("mem_id"), req.getParameter("gp_id"), null, Integer.valueOf(req.getParameter("status")), null, null);
			JoinedGPService jgpSrc = new JoinedGPService();
			jgpSrc.updateStatus(jGPVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" +  req.getParameter("gp_id") + "&action=GP_SEARCH&tab=3#tabpage");
		}
		
	}
}




