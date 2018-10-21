package com.forPos_res_report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.forPos.model.Forum_post_Service;
import com.forPos.model.Forum_post_VO;
import com.forPos_report.model.Forum_post_report_VO;
import com.forPos_report.model.Forum_post_report_service;
import com.forPos_res.model.Forum_response_Service;
import com.forPos_res.model.Forum_response_VO;
import com.forPos_res_report.model.Forum_res_report_VO;
import com.forPos_res_report.model.Forum_res_report_service;

public class ForPos_Res_ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ForPos_Res_ReportServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		
		//**************************************************
		if("getForPos_Res_RepList".equals(action)) {
			//	接收參數
			String forRes_Rep_State = request.getParameter("forRes_Rep_State");
			String goWhere = request.getParameter("goWhere");
			
			//	查詢不同條件的檢舉清單
			Forum_res_report_service forResRepSvc = new Forum_res_report_service();
			List<Forum_res_report_VO> forResRepList = forResRepSvc.getByStatus(forRes_Rep_State);
			

			//	查詢被檢舉的留言清單
			Forum_response_Service fotResSvc = new Forum_response_Service();
			List<Forum_response_VO> forResList = fotResSvc.getAllForRes();
			
			
			//	轉交回審核檢舉頁面
			String url = "/report/forPos_ReportPage.jsp";
			request.setAttribute("forResRepList", forResRepList);
			request.setAttribute("forResList", forResList);
			request.setAttribute("forRes_Rep_State", forRes_Rep_State);
			request.setAttribute("goWhere", goWhere);
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		//******************************************************************************************
		if("confirm".equals(action)) {
			//	接收參數
			String mem_id = request.getParameter("mem_id");
			Integer forRes_ID = new Integer(request.getParameter("forRes_ID"));
			Integer forRes_rep_state = new Integer(request.getParameter("forRes_rep_state"));
			String goWhere = request.getParameter("goWhere");
			
			//	改變留言狀態與送出站內通知信
			Forum_res_report_service forResRepSvc = new Forum_res_report_service();
			Forum_response_Service forResSvc = new Forum_response_Service();
			
			if(forRes_rep_state == 1) {
				forResSvc.closeforRes(forRes_ID, forRes_rep_state);
			}
			forResRepSvc.close(forRes_ID,forRes_rep_state);

			//	轉交回審核檢舉頁面
			String url = "/report/forPos_ReportPage.jsp";
			request.setAttribute("goWhere", goWhere);
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		
		/////////////////////新增
		if ("addResReport".equals(action)) {
			System.out.println("留言檢舉控制器");
//			String mem_id = request.getParameter("mem_id");
			HttpSession session = request.getSession();
			String cli = (String)session.getAttribute("cli");
			Integer forRes_ID = new Integer(request.getParameter("forRes_ID"));
			String forRes_rep_reason = request.getParameter("forRes_rep_reason");
			JSONObject obj = new JSONObject();
			
			

			if (forRes_rep_reason == null) {
				try {
					obj.put("error", "檢舉理由請勿空白且不能小於五個字");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if (forRes_rep_reason.trim().length() < 5) {

					try {
						obj.put("error", "檢舉理由請勿空白且不能小於五個字");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					
					Forum_res_report_VO forum_res_report_VO = new Forum_res_report_VO();
					forum_res_report_VO.setForRes_ID(forRes_ID);
//					forum_post_VO.setMemId(memId);
					forum_res_report_VO.setMem_ID(cli);
					forum_res_report_VO.setForRes_rep_reason(forRes_rep_reason);
					Forum_res_report_service forResRepSvc = new Forum_res_report_service();
					System.out.println("cli***" + cli);
					System.out.println("forRes_ID***" + forRes_ID);
					System.out.println("forRes_rep_reason***" + forRes_rep_reason);
					
					forResRepSvc.insertForRestReport(forRes_ID, cli, forRes_rep_reason);
					
//					
//			 request.setAttribute("forum_post_report_VO", forum_post_report_VO);
//			 RequestDispatcher successView =request.getRequestDispatcher("/back-end/report/forPos_ReportPage.jsp");
//			 successView.forward(request, response);
					System.out.println("***檢舉回文控制器**");

				}
			}
			System.out.println("***檢舉回文控制器2**");

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
			
		}
		
	
	}

}
