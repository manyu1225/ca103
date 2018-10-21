package com.secondShop.productBidding.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.secondShop.productBidding.model.BiddingService;
import com.secondShop.productBidding.model.BiddingVO;

@WebServlet("/BiddingSerlet")
public class BiddingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public BiddingServlet() {
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");

		String action = req.getParameter("action");
		if ("addBiddingPice".equals(action)) {
			JSONObject JSONObject = new JSONObject();
			String productId = req.getParameter("productId");
			String memIdBuy = req.getParameter("memIdBuy");
//			Integer biddingPrice = Integer.parseInt(req.getParameter("topBiddingPrice"));// 不能小於目前最高出價
			Integer productBiddingPrice = Integer.parseInt(req.getParameter("productBiddingPrice"));
			System.out.println("我來競標");
			BiddingService biddingService = new BiddingService();
			BiddingVO biddingVOforTop = new BiddingVO();
			biddingVOforTop = biddingService.biddingBenefitMem(productId);
			Integer BiddingPriceforTop = null;
			if(biddingVOforTop==null) {
				BiddingPriceforTop=productBiddingPrice;
			}else {
				BiddingPriceforTop = biddingVOforTop.getBiddingPrice();
			}
			Integer productPrice = Integer.parseInt(req.getParameter("productPrice"));// 若大於商品總價導向直接購買畫面;
			String callPriceSt = req.getParameter("callPrice");
			Integer callPrice = null;
			if(callPriceSt == null) {
				try {
					JSONObject.put("error", "請輸入出價金額");
					JSONObject.put("return", "wrong");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					callPrice = Integer.parseInt(callPriceSt);
					if (callPrice <= BiddingPriceforTop) {
						try {
							JSONObject.put("error", "出價金額不能低於目前最高出價");
							JSONObject.put("return", "wrong");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					} else if (callPrice >= productPrice) {
						try {
							JSONObject.put("error", "出價金額等於直購金額請按直接購買");
							JSONObject.put("return", "wrong");

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
					BiddingVO biddingVO = new BiddingVO();
					biddingVO.setMemId(memIdBuy);
					biddingVO.setBiddingPrice(callPrice);
					biddingVO.setProductId(productId);
					BiddingService biddingSvc = new BiddingService();
					biddingVO = biddingSvc.insertBiddingPrice(biddingVO);
					try {
						JSONObject.put("BiddingPrice",biddingVO.getBiddingPrice());
						JSONObject.put("return", "bingo");

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					
				} catch (NumberFormatException e) {
					try {
						JSONObject.put("error", "請輸入出價金額請輸入數字");
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
			}
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(JSONObject.toString());
			out.flush();
			out.close();
		}
		
	}

}
