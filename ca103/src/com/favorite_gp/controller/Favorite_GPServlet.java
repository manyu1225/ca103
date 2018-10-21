package com.favorite_gp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.favorite_gp.model.*;
import com.joined_gp.model.JoinedGPService;
import com.joined_gp.model.JoinedGPVO;
import com.mem.model.MemVO;

public class Favorite_GPServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		String mem_id = ((MemVO) (req.getSession().getAttribute("memVO"))).getMem_id();
		
		
		if("GP_FAVORITE".equals(action)) {
			String favorite = "true";
			Favorite_GPVO favGPVO = new Favorite_GPVO(((MemVO) (req.getSession().getAttribute("memVO"))).getMem_id(),
					req.getParameter("gp_id"),  new Timestamp(System.currentTimeMillis()));
			
			Favorite_GPService favgpSrc = new Favorite_GPService();
			try {
				favgpSrc.addFavGP(favGPVO);
			}catch (Exception e) {
//					����������
				System.out.println(e.getMessage());
				favorite = "false";
			}
			PrintWriter out = res.getWriter();
			out.write(favorite);
		}
		
		if("GP_FAV_DEL".equals(action)) {
			Favorite_GPVO favGPVO = new Favorite_GPVO(((MemVO) (req.getSession().getAttribute("memVO"))).getMem_id(),
					req.getParameter("gp_id"),  new Timestamp(System.currentTimeMillis()));
			Favorite_GPService favgpSrc = new Favorite_GPService();
			favgpSrc.deleteFav_GP(favGPVO);
			
//			res.sendRedirect(req.getContextPath() + "/front-end/gp/GP_Browsing.jsp");
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp/GP_List.jsp?listPage=2");
			failureView.forward(req, res);
			
		}
		if("GP_VIEWING_FAV".equals(action)) {
			Favorite_GPVO favGPVO = new Favorite_GPVO(mem_id,req.getParameter("gp_id"),  new Timestamp(System.currentTimeMillis()));
			Favorite_GPService favgpSrc = new Favorite_GPService();
			favgpSrc.addFavGP(favGPVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" +  req.getParameter("gp_id") + "&action=GP_SEARCH");
		}
		if("GP_VIEWING_UNFAV".equals(action)) {
			Favorite_GPVO favGPVO = new Favorite_GPVO(mem_id,req.getParameter("gp_id"),  new Timestamp(System.currentTimeMillis()));
			Favorite_GPService favgpSrc = new Favorite_GPService();
			favgpSrc.deleteFav_GP(favGPVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" +  req.getParameter("gp_id") + "&action=GP_SEARCH");
		}
		
	}
}

