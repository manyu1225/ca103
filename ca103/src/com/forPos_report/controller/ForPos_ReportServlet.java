package com.forPos_report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.forPos.model.*;
import com.forPos_report.model.*;

public class ForPos_ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ForPos_ReportServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");

		// **************************************************
		if ("getForPos_RepList".equals(action)) {
			// 接收參數
			String forPos_rep_state = request.getParameter("forPos_rep_state");
			String goWhere = request.getParameter("goWhere");

			// 查詢不同條件的檢舉清單
			Forum_post_report_service forPosRepSvc = new Forum_post_report_service();
			List<Forum_post_report_VO> forPosRepList = forPosRepSvc.getByStatus(forPos_rep_state);

			// 查詢被檢舉的留言清單
			Forum_post_Service fotPosSvc = new Forum_post_Service();
			List<Forum_post_VO> forPosList = fotPosSvc.getAllForPos();

			// 轉交回審核檢舉頁面
			String url = "/back-end/forPos_report/forPos_ReportPage.jsp";
			request.setAttribute("forPosRepList", forPosRepList);
			request.setAttribute("forPosList", forPosList);
			request.setAttribute("forPos_rep_state", forPos_rep_state);
			request.setAttribute("goWhere", goWhere);
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}

		// ******************************************************************************************
		if ("confirm".equals(action)) {
			
			System.out.println("貼文檢舉狀態--確認");
			
			// 接收參數
			String mem_id = request.getParameter("mem_id");
			Integer forPost_ID = new Integer(request.getParameter("forPost_ID"));
			Integer forPos_rep_state = new Integer(request.getParameter("forPos_rep_state"));
			String goWhere = request.getParameter("goWhere");

			// 改變留言(貼文)狀態與送出站內通知信
			Forum_post_report_service forPosRepSvc = new Forum_post_report_service();
			Forum_post_Service forPosSvc = new Forum_post_Service();

			if (forPos_rep_state == 1) {
				forPosSvc.closeforPos(forPost_ID, forPos_rep_state);
			}
			forPosRepSvc.close(forPost_ID, forPos_rep_state);

			// 轉交回審核檢舉頁面
			String url = "/back-end/forPos_report/forPos_ReportPage.jsp";
			request.setAttribute("goWhere", goWhere);
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		
		
		
		

		if ("addReport".equals(action)) {
//			String mem_id = request.getParameter("mem_id");

			String mem_id = request.getParameter("mem_id");
			Integer forPost_ID = new Integer(request.getParameter("forPost_ID"));
			String forPos_rep_reason = request.getParameter("forPos_rep_reason");
			JSONObject obj = new JSONObject();

			if (forPos_rep_reason == null) {
				try {
					obj.put("error", "檢舉理由請勿空白且不能小於五個字");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				if (forPos_rep_reason.trim().length() < 5) {

					try {
						obj.put("error", "檢舉理由請勿空白且不能小於五個字");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					
					Forum_post_report_VO forum_post_report_VO = new Forum_post_report_VO();
					forum_post_report_VO.setForPost_ID(forPost_ID);
					;
//					forum_post_VO.setMemId(memId);
					forum_post_report_VO.setMem_ID(mem_id);
					forum_post_report_VO.setForPost_rep_reason(forPos_rep_reason);
					Forum_post_report_service forPosRepSvc = new Forum_post_report_service();
					System.out.println("mem_id***" + mem_id);
					System.out.println("forPost_ID***" + forPost_ID);
					System.out.println("forPos_rep_reason***" + forPos_rep_reason);
					
					

					forPosRepSvc.insertForumPostReport(forPost_ID, mem_id, forPos_rep_reason);
					
//					
//			 request.setAttribute("forum_post_report_VO", forum_post_report_VO);
//			 RequestDispatcher successView =request.getRequestDispatcher("/back-end/report/forPos_ReportPage.jsp");
//			 successView.forward(request, response);
					System.out.println("***檢舉成功**");

				}
			}

			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
			
		}
	}
}
