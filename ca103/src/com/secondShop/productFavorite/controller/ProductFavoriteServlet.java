package com.secondShop.productFavorite.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.secondShop.productFavorite.model.ProductFavoriteService;

@WebServlet("/ProductFavoriteServlet")
public class ProductFavoriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ProductFavoriteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");

		String action = req.getParameter("action");
		
		if("addFavorite".equals(action)) {
			System.out.println("39: 來+收藏");
			String productId = req.getParameter("productId");
			String memId = req.getParameter("memId");
			
			new ProductFavoriteService().insertFavorite(memId,productId);
			JSONObject obj = new JSONObject();
			try {
				obj.put("productId", productId);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
		}
		if("deleteFavorite".equals(action)){
			System.out.println("55: 來-收藏");
			String productId = req.getParameter("productId");
			String memId = req.getParameter("memId");
			
			new ProductFavoriteService().deleteFavorite(memId, productId);
			JSONObject obj = new JSONObject();
			try {
				obj.put("productId", productId);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
		}
	}

}
