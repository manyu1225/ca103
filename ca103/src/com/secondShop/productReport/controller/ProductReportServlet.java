package com.secondShop.productReport.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.secondShop.productReport.model.ProductReportService;
import com.secondShop.productReport.model.ProductReportVO;

/**
 * Servlet implementation class productReportServlet
 */
@WebServlet("/ProductReportServlet")
public class ProductReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		String requestURL = req.getParameter("requestURL");
		String action = req.getParameter("action");
		if("Report".equals(action)) {
			JSONObject obj = new JSONObject();
			System.out.println("我來新增檢舉");
			String productId = req.getParameter("productId");
			String memId = req.getParameter("memId");
			String reportdetailed = req.getParameter("Reportdetailed");
			if(reportdetailed==null) {
				try {
					obj.put("error", "檢舉理由請勿空白且不能小於五個字");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else {
				if(reportdetailed.trim().length()<5) {
					try {
						obj.put("error", "檢舉理由請勿空白且不能小於五個字");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					ProductReportVO porductReportVO = new ProductReportVO();
					porductReportVO.setProductId(productId);
					porductReportVO.setMemId(memId);
					porductReportVO.setReportDetailed(reportdetailed);
					ProductReportService porductReportService = new ProductReportService();
					porductReportService.insertProductReport(porductReportVO);
					System.out.println("我來剪剪囉");
				}
			}
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
			
		}
		
		if("reverseReport".equals(action)) {
			System.out.println("我來處理檢局");
			String reportId = req.getParameter("reportId");
			ProductReportService productReportSercice = new ProductReportService();
			ProductReportVO porductReportVO = new ProductReportVO();
			porductReportVO.setReportId(reportId);
			porductReportVO.setReportStatus(2);//無效檢
			productReportSercice.updateProductReport(porductReportVO);
			res.sendRedirect(req.getContextPath()+requestURL);
			
		}
		
		if("getReport".equals(action)) {
			System.out.println("我來處理檢局");
			String reportId = req.getParameter("reportId");
			String productId = req.getParameter("productId");
			String memId = req.getParameter("memId"); //備用
			ProductReportService productReportSercice = new ProductReportService();
			ProductReportVO porductReportVO = new ProductReportVO();
			porductReportVO.setReportId(reportId);
			porductReportVO.setReportStatus(1);//有效檢
			porductReportVO.setProductId(productId);
			porductReportVO.setMemId(memId);
			productReportSercice.updateProductReport(porductReportVO);
			res.sendRedirect(req.getContextPath()+requestURL);
			
		}
	}

}
