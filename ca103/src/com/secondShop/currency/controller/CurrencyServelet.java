package com.secondShop.currency.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.secondShop.currency.model.CurrencyService;
import com.secondShop.currency.model.CurrencyVO;
import com.secondShop.currencyCheackout.model.CurrencyCheackoutVO;


/**
 * Servlet implementation class CourncySelevt
 */
@WebServlet("/CurrencyServelt")
public class CurrencyServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CurrencyServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");

		String requestURL = req.getParameter("requestURL");
		String action = req.getParameter("action");
		
		if("addCurrency".equals(action)) {//front-end/secondShop/Currency.jsp"; 
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			String memId = req.getParameter("memId").trim();
			String currencyBalanceSt = req.getParameter("currencyBalance");
			Integer currencyBalance = null;
			try {
				if(currencyBalanceSt == null) { 									//先檢查價格String 是否未輸入
					errorMsgs.add("請輸入儲值金額");															
				}else{                                                          //String 有輸入資料 	=>空白鍵 或是 國字 或是 0
					if(currencyBalanceSt.trim().length() == 0) {
						errorMsgs.add("請輸入儲值金額");							//String 資料是空白鍵 就放錯誤訊息
					}else {														//String 資料不是空白鍵 => 國字 或是 0
						try {
							currencyBalance = Integer.parseInt(currencyBalanceSt);   //String非空值/非null 就轉型Integer存回實際變數裡
							if(currencyBalance<100) {								//String成功轉為數值,最後再檢查必須大於0
								errorMsgs.add("儲值金額至少大於100元");					//檢查商品價格必須大於0
							}
						} catch (NumberFormatException e) {
							errorMsgs.add("請輸入儲值金額請輸入數字");								//價格String如果不是數字,會被這裡catch住
						}
					}
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				CurrencyVO currencyVO = new CurrencyVO();
				currencyVO.setCurrencyBalance(currencyBalance);
				currencyVO.setCurrencyDetail("儲值");
				currencyVO.setMemId(memId);
				CurrencyService currencysSvc = new CurrencyService();
				currencysSvc.insertCurrency(currencyVO);
				//新增成功後重導
				res.sendRedirect(req.getContextPath()+"/front-end/secondShop/Currency.jsp");				
		}catch(Exception e) {
			errorMsgs.add(e.getMessage());
			req.setAttribute("currencyBalance", currencyBalance);
			RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
			System.out.println("85"+requestURL);
			failureView.forward(req, res);
		}
	}
		
//		===================
		if("outCurrency".equals(action)) {//front-end/secondShop/Currencyout.jsp
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			String currencyBalanceSt  = req.getParameter("currencyBalance");
			Integer memCurrecyALLTotal = Integer.parseInt(req.getParameter("memCurrecyALLTotal"));
			System.out.println(memCurrecyALLTotal);
			Integer currencyBalance = null;
			String memId = req.getParameter("memId").trim();
			try {
				if(currencyBalanceSt == null) { 									//先檢查價格String 是否未輸入
					errorMsgs.add("請輸入提領金額");															
				}else{                                                          //String 有輸入資料 	=>空白鍵 或是 國字 或是 0
					if(currencyBalanceSt.trim().length() == 0) {
						errorMsgs.add("請輸入提領金額");							//String 資料是空白鍵 就放錯誤訊息
					}else {														//String 資料不是空白鍵 => 國字 或是 0
						try {
							currencyBalance = Integer.parseInt(currencyBalanceSt);   //String非空值/非null 就轉型Integer存回實際變數裡
							if(currencyBalance<1) {								//String成功轉為數值,最後再檢查必須大於0
								errorMsgs.add("提領金額至少大於1元");					//檢查商品價格必須大於0
							}
						} catch (NumberFormatException e) {
							errorMsgs.add("請輸入提領金額，請輸入數字");								//價格String如果不是數字,會被這裡catch住
						}
					if(currencyBalance>memCurrecyALLTotal) {
							errorMsgs.add("提款金額不能大於目前可用餘額");
						}
					}
				}
				if(!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				
				CurrencyVO currencyVO = new CurrencyVO();
				currencyVO.setCurrencyBalance(currencyBalance*-1);//提領為負數
				currencyVO.setCurrencyDetail("審核提領");
				currencyVO.setMemId(memId);
				CurrencyService currencysSvc = new CurrencyService();
				CurrencyCheackoutVO currencyCheackoutVO = new CurrencyCheackoutVO();
				currencyCheackoutVO.setMemId(memId);
				currencyCheackoutVO.setCheackoutBalance((currencyVO.getCurrencyBalance()));
				currencyCheackoutVO.setCheackoutStatus(currencyVO.getCurrencyStatus());
				currencysSvc.insertCurrency(currencyVO, currencyCheackoutVO);
				res.sendRedirect(req.getContextPath()+"/front-end/secondShop/Currency.jsp");		
		}catch(Exception e) {
			errorMsgs.add(e.getMessage());
			req.setAttribute("currencyBalance", currencyBalance);
			RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
			failureView.forward(req, res);
		}
		
		}

	}
	
	
}
