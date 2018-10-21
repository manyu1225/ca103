package com.secondShop.currencyCheackout.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.secondShop.currencyCheackout.model.CurrencyCheackoutService;
import com.secondShop.currencyCheackout.model.CurrencyCheackoutVO;

/**
 * Servlet implementation class currencyCheackoutServlet
 */
@WebServlet("/currencyCheackoutServlet")
public class currencyCheackoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public currencyCheackoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		String requestURL = req.getParameter("requestURL");
		String action = req.getParameter("action");
		if("currencyCheackoutDone".equals(action)) {
			System.out.println("我來審核體領");
			String currencyId = req.getParameter("currencyId");
			CurrencyCheackoutService CurrencyCheackoutService = new CurrencyCheackoutService();
			CurrencyCheackoutService.updateCurrencyCheackout(currencyId);
			System.out.println("requestURL:" + requestURL);
			res.sendRedirect(req.getContextPath()+requestURL);
		}
		
		
		
	}

}
