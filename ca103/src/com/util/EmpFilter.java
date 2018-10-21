package com.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpVO;

public class EmpFilter implements Filter {

	private FilterConfig config;
	
	public void init(FilterConfig fConfig) throws ServletException {
		this.config = config;
	}

	public void destroy() {
		config = null;
	} 
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		EmpVO empVO = new EmpVO();
		
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		empVO = (EmpVO)session.getAttribute("empVO");
		System.out.println("抓到的參數: "+action);
		
		if(empVO != null) {
			System.out.println("登入者權限： "+empVO.getEmp_pr());
		}
		
		if(empVO == null) {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/back-end/emp/EmpLogin.jsp");
			return;
		}
		
		else if("empList".equals(action)&&"超級管理員".equals(empVO.getEmp_pr())) {
			session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/back-end/emp/EmpList.jsp");
			chain.doFilter(request, response);
			return;
		}else if("empAdd".equals(action)&&"超級管理員".equals(empVO.getEmp_pr())) {
			session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/back-end/emp/EmpAdd.jsp");
			chain.doFilter(request, response);
			return;
		}
		else if("routeReportPage".equals(action)&& ("超級管理員".equals(empVO.getEmp_pr())||"路線管理員".equals(empVO.getEmp_pr())) ) {
			session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/back-end/Route/RouteReportPage.jsp");
			chain.doFilter(request, response);
			return;
		}
		else if("getRouteList".equals(action)&& ("超級管理員".equals(empVO.getEmp_pr())||"路線管理員".equals(empVO.getEmp_pr())) ) {
			session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/back-end/Route/RouteReportPage.jsp");
			chain.doFilter(request, response);
			return;
		}
		else if("currencyCheackout".equals(action)&& ("超級管理員".equals(empVO.getEmp_pr())||"拍賣管理員".equals(empVO.getEmp_pr())) ) {
			session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/back-end/secondShop/CurrencyCheackout.jsp");
			chain.doFilter(request, response);
			return;
		}
		else if("productReport".equals(action)&& ("超級管理員".equals(empVO.getEmp_pr())||"拍賣管理員".equals(empVO.getEmp_pr())) ) {
			session.setAttribute("location", req.getRequestURI());
			//res.sendRedirect(req.getContextPath() + "/back-end/secondShop/ProductReport.jsp");
			chain.doFilter(request, response);
			return;
		}
		else if("addActivity".equals(action)&& ("超級管理員".equals(empVO.getEmp_pr())||"文字管理員".equals(empVO.getEmp_pr())) ) {
			session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/back-end/empAct/addActivity.jsp");
			chain.doFilter(request, response);
			return;
		}else if("GP_Manager".equals(action)&& ("超級管理員".equals(empVO.getEmp_pr())||"揪團管理員".equals(empVO.getEmp_pr())) ) {
				session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/back-end/empAct/addActivity.jsp");
				chain.doFilter(request, response);
				return;
		}else if("GP_Report".equals(action)&& ("超級管理員".equals(empVO.getEmp_pr())||"揪團管理員".equals(empVO.getEmp_pr())) ) {
				session.setAttribute("location", req.getRequestURI());
//			res.sendRedirect(req.getContextPath() + "/back-end/empAct/addActivity.jsp");
				chain.doFilter(request, response);
				return;
		}
		else {
			String errorMsg ="你無權進入此頁面";
			request.setAttribute("errorMsg", errorMsg);
			String url = "/BackHomePage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			return;
		}
	}
}
