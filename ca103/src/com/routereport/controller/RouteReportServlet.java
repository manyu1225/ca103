package com.routereport.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mem.model.MemVO;
import com.route.model.RouteService;
import com.route.model.RouteVO;
import com.routemessage.model.RouteMessageService;
import com.routemessage.model.RouteMessageVO;
import com.routemessagereport.model.RouteMessageReportService;
import com.routemessagereport.model.RouteMessageReportVO;
import com.routereport.model.RouteReportService;
import com.routereport.model.RouteReportVO;

@WebServlet("/RouteReportServlet")
public class RouteReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RouteReportServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		
			
		//	新增路線檢舉
		if("insertRouteReport".equals(action)) {
			//	確認session裡面有無memVO,沒有就導向首頁
			HttpSession session = request.getSession();
			String mem_id = null;
			try {
				mem_id = ((MemVO)session.getAttribute("memVO")).getMem_id();
			}catch (Exception e) {
				String url = "/front-end/mem/Login.jsp";
				response.sendRedirect(request.getContextPath() + url);
				return;
			}
			
			//	接收參數
			String rot_id = request.getParameter("rot_id");
			String rotRep_cont = request.getParameter("rotRep_cont");
			String sdate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
			java.sql.Timestamp rotRep_time = java.sql.Timestamp.valueOf(sdate);
			String goWhere = (String)request.getParameter("goWhere");
			
			//	開始新增檢舉
			RouteReportService routeReportSvc = new RouteReportService();
			routeReportSvc.insertRouteReport(mem_id,rot_id,rotRep_cont,rotRep_time);
			
			//	重導非轉交
			String url = "/Route/RouteServlet.do?action=getRouteDetailed&rot_id=";
			response.sendRedirect(request.getContextPath() + url + rot_id + "&goWhere=" + goWhere);
			
			//	開始轉交,會造成「檢舉後立刻重整頁面,檢舉會不斷增加」的狀況,所以改用重導的方式去避免非預期性的重複檢舉
			//	取得來源該頁的路徑資訊,一起轉交回RoutePage.jsp
//			RouteService routeSvc = new RouteService();
//			RouteVO routeVO = routeSvc.getRouteDetailed(rot_id);
//			request.setAttribute("routeVO", routeVO);
//			String url = "/front-end/Route/RoutePage.jsp";
//			RequestDispatcher successView = request.getRequestDispatcher(url);
//			successView.forward(request, response);
		}
		
		
		//	路線檢舉處理
		if("confirm".equals(action)) {
			String mem_id = request.getParameter("mem_id");
			String rot_id = request.getParameter("rot_id");
			Integer rotRep_status = new Integer(request.getParameter("rotRep_status"));
			String goWhere = request.getParameter("goWhere");
			System.out.println("goWhere = " + goWhere);
			
			//	改變留言狀態與送出站內通知信
			RouteReportService rotRepSvc = new RouteReportService();
			RouteService rotSvc = new RouteService();
			
			if(rotRep_status == 1) {
				rotSvc.closeRoute(rot_id);
			}
			rotRepSvc.closeRot(rot_id,rotRep_status);

			//	轉交回審核檢舉頁面
			String url = "/back-end/Route/RouteReportPage.jsp";
			request.setAttribute("goWhere", goWhere);
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		//	取得檢舉清單
		if("getRotRepList".equals(action)) {
			String rotRep_status =request.getParameter("rotRep_status");
			String goWhere = request.getParameter("goWhere");
			
			//	查詢不同條件的檢舉清單
			RouteReportService rotRepSvc = new RouteReportService();
			List<RouteReportVO> rotRepList= rotRepSvc.getByStatus(rotRep_status);

			//	查詢被檢舉的路線清單
			RouteService rotSvc = new RouteService();
			List<RouteVO> rotList = rotSvc.getRouteList(" OR ROT_STATUS = 2 OR ROT_STATUS = 0");
			
			
			//	轉交回審核檢舉頁面
			String url = "/back-end/Route/RouteReportPage.jsp";
			request.setAttribute("rotRepList", rotRepList);
			request.setAttribute("rotList", rotList);
			request.setAttribute("rotRep_status", rotRep_status);
			request.setAttribute("goWhere", goWhere);
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
	}

}
