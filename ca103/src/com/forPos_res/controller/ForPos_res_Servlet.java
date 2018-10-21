package com.forPos_res.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.forPos.model.Forum_post_Service;
import com.forPos.model.Forum_post_VO;
import com.forPos_res.model.Forum_response_Service;
import com.forPos_res.model.Forum_response_VO;

public class ForPos_res_Servlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		System.out.println("888888");
		
		
		String action = req.getParameter("action");
		
		
		
		if ("addOneForRes".equals(action)) {
//			try {
			
			
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
//			String forRes_content_reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]$";
			
			//登入會員資料
//			HttpSession sessionCli =  req.getSession();
//			String cli = (String)sessionCli.getAttribute("cli");

			String mem_id = req.getParameter("mem_id");
			System.out.println("mem_iddddddd=" + mem_id);
			
			String forRes_content = req.getParameter("forRes_content");
			
			System.out.println("forRes_content:" + forRes_content);
			
			
			if(forRes_content.trim().length() == 0|| forRes_content == null) {
				
				errorMsgs.put("content","不要讓你的發言機會留白!!!");
			}
			
//			else if(!forRes_content.trim().matches(forRes_content_reg)) {
//				
//				errorMsgs.put("content", "標題請填寫正確格式，包括的數字、中文、英文");
//			}
			
			System.out.println("eorrorrrrrr" + errorMsgs);
			Integer forPost_ID = new Integer(req.getParameter("forPost_ID"));

			Forum_post_VO forPosVO = new Forum_post_VO();
			Forum_post_Service forPosSvc = new Forum_post_Service();
			forPosVO = forPosSvc.getOneForPos(forPost_ID);
			 req.setAttribute("forPosVO", forPosVO);

			

			
			if(!errorMsgs.isEmpty()) {
				System.out.println("準備煮焦 錯誤訊息囉");
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Forum/forPos/myPost.jsp");
				failureView.forward(req, res);
				return;
				
				}
			System.out.println("errorMsgsssss" + errorMsgs);
				
			
			System.out.println("forPost_ID+++:" + forPost_ID);
			
			

				//包裝此文章
			
//			req.setAttribute("forPosVO", forPosVO);
			 
			Forum_response_VO forResVO = new Forum_response_VO(); 
			forResVO.setForRes_content(forRes_content);
			
			
//			Send the error Message to front page
		
			System.out.println("成功進入錯誤驗證程序");
			
			//			Add new respense
//			 System.out.println("1111111111111111111");

			System.out.println("forPost_ID:" + forPost_ID);
			System.out.println("mem_id:" + mem_id);
			System.out.println("forRes_content:" + forRes_content);

			Forum_response_Service forResSvc = new Forum_response_Service();
			 forResSvc.addForRes(forPost_ID, mem_id, forRes_content, 1);
				System.out.println("forResVO"  + forResVO);
System.out.println("哈哈哈" + forPosVO);
			 req.setAttribute("forResVO", forResVO);
//			 req.setAttribute("forPosVO", forPosVO);
////			 

//			 
			 
////			 轉交
			 RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/forPos/myPost.jsp");
			 successView.forward(req, res);
//			 String url = req.getContextPath() + "/forPos/myPost.jsp"; 
//			 res.sendRedirect(url);

			

//			} catch (Exception e) {
//
//				errorMsgs.put("Exception",  e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/forPos/myPost.jsp");
//				failureView.forward(req, res);
//
//			}

		}
		
		
//		if("getResp".equals(action)) {
//			
//			Integer forPost_ID = new Integer(req.getParameter("forPost_ID"));
//			Forum_response_Service  forResSvc = new Forum_response_Service();
//			List<Forum_response_VO> list = forPosSvc.getResByPost_ID(forPost_ID);
//			
//			
//			
//		}
		
		
		

	}

}
