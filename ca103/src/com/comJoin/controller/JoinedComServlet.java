package com.comJoin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.com.model.*;
import com.comJoin.model.*;
import com.mem.model.*;

@WebServlet("/JoinedComServlet")
public class JoinedComServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JoinedComServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		String mem_id = ((MemVO) session.getAttribute("memVO")).getMem_id();
		
		if("joinCom".equals(action)) {
			
			/****************************1.接收請求參數***************************************/
			String com_id = req.getParameter("com_id");
			Integer pm_setting = 0;		//	申請者須通過審核，預設為0
			Integer available = 0;
			System.out.println("com_id "+com_id);
			System.out.println("mem_id "+mem_id);
			/*************************** 2.開始新增資料 ***************************************/
			//	加入社群，等待審核
			JoinedComService joinedComSvc = new JoinedComService();
			joinedComSvc.joinCom(com_id, mem_id, pm_setting, available);
			
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			
			String url = "/com/ComServlet.do?action=getComContent&com_id=";
			RequestDispatcher successView = req.getRequestDispatcher(url + com_id); // 新增成功後轉交ComPage.jsp
			successView.forward(req, res);
			
		}
		
		if("joinCom_OK".equals(action)) {
			
			String com_id = req.getParameter("com_id");
			mem_id = req.getParameter("mem_id");
			Integer pm_setting = 1;		//	通過審核，預設為1
			Integer available = 0;
			
			//	加入社群，通過審核
			JoinedComService joinedComSvc = new JoinedComService();
			joinedComSvc.joinCom_OK(com_id, mem_id, pm_setting, available);
			
			//	更新社群人數紀錄(+1)
			ComService comSvc = new ComService();
			comSvc.plusMemCount(com_id);
			
			//	Send the Success view
			String url = "/com/ComServlet.do?action=getComContent&com_id=";
			res.sendRedirect(req.getContextPath() + url + com_id);
			
		}
		
		if("exitCom".equals(action)) {
			
			String com_id = req.getParameter("com_id");
			
			//	退出社群
			JoinedComService joinedComSvc = new JoinedComService();
			joinedComSvc.exitCom(com_id, mem_id);
			
			//	更新社群人數紀錄(-1)
			ComService comSvc = new ComService();
			comSvc.minusMemCount(com_id);
			
			//	Send the Success view
			String url = "/com/ComServlet.do?action=getComList";
			res.sendRedirect(req.getContextPath() + url);
			
			
		}
		
		if("getJoinedComList".equals(action)) {
			/*************************** 1.接收請求參數 *************************/
			JoinedComService joinedComSvc = new JoinedComService();
			List<ComVO> joinedComList;
			System.out.println(mem_id);
			/*************************** 開始查詢資料 ****************************************/
			joinedComList = joinedComSvc.getJoinedComList(mem_id);
			
			//*************************** 查詢完成,準備轉交(Send the Success view) *************/
			session.setAttribute("joinedComList", joinedComList);
			
			//	Send the Success view
			String url = "/front-end/com/ListAllCom.jsp";
			res.sendRedirect(req.getContextPath() + url);
			
		}
		
//		if("getComMemberList".equals(action)) {
//			/*************************** 1.接收請求參數 *************************/
////			String com_id = null;
//			
//			String com_id = req.getParameter("com_id");
//			
//			/*************************** 開始查詢資料 ****************************************/
//			JoinedComService joinedComSvc = new JoinedComService();
//			List<MemVO>  comMemberList = joinedComSvc.getComMemberList(com_id);
//			
//			//*************************** 查詢完成,準備轉交(Send the Success view) *************/
//			req.setAttribute("comMemberList", comMemberList);
//			
//			//	Send the Success view
//			String url = "/front-end/com/ComMemberList.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交ComPage.jsp
//			successView.forward(req, res);
//			
//		}
	}

	
}
