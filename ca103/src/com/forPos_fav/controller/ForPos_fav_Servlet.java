package com.forPos_fav.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.forPos.model.Forum_post_Service;
import com.forPos.model.Forum_post_VO;
import com.forPos_fav.model.Forum_post_fav_Service;
import com.forPos_fav.model.Forum_post_fav_VO;
import com.mem.model.MemVO;

/**
 * Servlet implementation class forPos_fav_Servlet
 */

public class ForPos_fav_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	   


		String action = req.getParameter("action");
		System.out.println("-----------------");
		System.out.println("進入fav controller");

		if ("addFavorite".equals(action)) {
			List<String> errorMsgs = new ArrayList<String>();

			Forum_post_fav_VO fav_VO = new Forum_post_fav_VO();
//				Forum_post_VO forPosVO = new Forum_post_VO();

			try {
				Integer forPost_ID = new Integer(req.getParameter("forPost_ID"));
				String mem_id = req.getParameter("mem_id");////////////////////////////////模擬登入/////用ajax接
				System.out.println("進入新增fav controller2");
				System.out.println("進入新增fav controller2 forPost_ID" + forPost_ID);

//				****2****
				Forum_post_fav_Service forPosFavSvc = new Forum_post_fav_Service();

				fav_VO = forPosFavSvc.addFavPos(forPost_ID, mem_id);
				System.out.println("進入fav controller3");
				req.setAttribute("fav_VO", fav_VO);
//				*****準備轉交****
//				RequestDispatcher successView = req.getRequestDispatcher("/forPos/myPost.jsp");
//				successView.forward(req, res);

				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write("{\"新增\":\"收藏\"}");
				out.flush();
				out.close();
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/forPos/myPost.jsp");
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("新增收藏紀錄失敗:" + e.getMessage());

//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/forPos/myPost.jsp");
//				failureView.forward(req, res);
//				
			}

		}

		if ("deleteFavorite".equals(action)) {
			List<String> errorMsgs = new ArrayList<String>();

			try {
				Integer forPost_ID = new Integer(req.getParameter("forPost_ID"));
				String mem_id = req.getParameter("mem_id");////////////////////////////////模擬登入
														/////////////////////模擬登入

				System.out.println("進入刪除fav controller2 forPost_ID" + forPost_ID);
				System.out.println("進入刪除fav controller2 mem_id---" + mem_id);
				
				
//			****2****
				Forum_post_fav_Service forPosFavSvc = new Forum_post_fav_Service();

				forPosFavSvc.deletePosFav(forPost_ID, mem_id);

//			*****準備轉交****
//			RequestDispatcher successView = req.getRequestDispatcher("/front-end/forPos/myPost.jsp");
//			successView.forward(req, res);

				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				out.write("{\"取消\":\"收藏\"}");
				out.flush();
				out.close();

//			*********如果已加入過收藏*********

			} catch (Exception e) {
				errorMsgs.add("新增收藏紀錄失敗:" + e.getMessage());

				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Forum/forPos/myPost.jsp");
				failureView.forward(req, res);
//			
			}

		}
		
		
		
		if("get_One_FavPost".equals(action)) {
			
			Integer forPost_ID = new Integer(req.getParameter("forPost_ID").trim());
			Forum_post_fav_Service favPosSvc = new Forum_post_fav_Service();
			 System.out.println("forPost_ID-----" + forPost_ID);
			
			HttpSession session = req.getSession(); //會員
			 MemVO memVO = (MemVO)session.getAttribute("memVO");
			
			 Forum_post_fav_VO fav_VO = favPosSvc.getOnePosFav(forPost_ID, memVO.getMem_id());
			 System.out.println("fav_VO-----" + fav_VO);

			 
			 req.setAttribute("fav_VO", fav_VO);
			 RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/forPos/myPost.jsp");
			 System.out.println("forum_xxxxxxt_VO-----" + fav_VO);

			 successView.forward(req, res);
		}
		
		

	}

}
