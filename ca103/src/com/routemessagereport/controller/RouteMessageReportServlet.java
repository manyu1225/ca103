package com.routemessagereport.controller;

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

import com.emp.model.EmpVO;
import com.mem.model.MemVO;
import com.routemessage.model.RouteMessageService;
import com.routemessage.model.RouteMessageVO;
import com.routemessagereport.model.RouteMessageReportService;
import com.routemessagereport.model.RouteMessageReportVO;

@WebServlet("/RouteMessageReportServlet")
public class RouteMessageReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RouteMessageReportServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
	
		if("insertMessageReport".equals(action)) {
			//	接收參數
			String rot_id = request.getParameter("rot_id");
			Integer rotMes_id = new Integer(request.getParameter("rotMes_id"));
			String rotMesRep_cont = request.getParameter("rotMesRep_cont");
			String sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			java.sql.Timestamp rotMesRep_time = java.sql.Timestamp.valueOf(sdate);
			String mem_id = ((MemVO)session.getAttribute("memVO")).getMem_id();
			
			//	取得刪除留言的錨點位置
			int pageIndex = 0;
			if(request.getParameter("pageIndex") != null) {
				pageIndex = new Integer(request.getParameter("pageIndex"));
			}
			String goWhere = request.getParameter("goWhere");
					
			//	開始新增
			RouteMessageReportService rotMesRepSvc = new RouteMessageReportService();
			rotMesRepSvc.InsertRouteMessageReport(mem_id,rot_id,rotMes_id,rotMesRep_cont,rotMesRep_time);
			
			//	重導非轉交
			String url = "/Route/RouteServlet.do?action=getRouteDetailed&goWhere=" + goWhere + "&rot_id=" + rot_id + "&pageIndex=" + pageIndex;
			response.sendRedirect(request.getContextPath() + url);
			
			//	開始轉交,會造成「檢舉後立刻重整頁面,檢舉會不斷增加」的狀況,所以改用重導的方式去避免非預期性的重複檢舉
			//	取得來源該頁的路徑資訊,一起轉交回RoutePage.jsp
//			RouteService routeSvc = new RouteService();
//			RouteVO routeVO = routeSvc.getRouteDetailed(rot_id);
//			request.setAttribute("routeVO", routeVO);
//			String url = "/front-end/Route/RoutePage.jsp";
//			RequestDispatcher successView = request.getRequestDispatcher(url);
//			successView.forward(request, response);
		}
		
		if("confirm".equals(action)) {
			//	接收參數
			Integer rotMes_id = new Integer(request.getParameter("rotMes_id"));
			Integer rotMesRep_status = new Integer(request.getParameter("rotMesRep_status"));
			String goWhere = request.getParameter("goWhere");
			
			//	改變留言狀態
			RouteMessageReportService rotMesRepSvc = new RouteMessageReportService();
			RouteMessageService rotMesSvc = new RouteMessageService();
			
			if(rotMesRep_status == 1) {
				rotMesSvc.closeRouteMessage(rotMes_id);
			}
			rotMesRepSvc.closeMes(rotMes_id,rotMesRep_status);

			//	轉交回審核檢舉頁面
			String url = "/back-end/Route/RouteReportPage.jsp";
			request.setAttribute("goWhere", goWhere);
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		if("getRotMessRepList".equals(action)) {
			
			//	接收參數
			String rotMesRep_status = request.getParameter("rotMesRep_status");
			String goWhere = request.getParameter("goWhere");
			
			//	查詢不同條件的檢舉清單
			RouteMessageReportService rotMesRepSvc = new RouteMessageReportService();
			List<RouteMessageReportVO> rotMessRepList = rotMesRepSvc.getByStatus(rotMesRep_status);

			//	查詢被檢舉的留言清單
			RouteMessageService rotMessSvc = new RouteMessageService();
			List<RouteMessageVO> rotMessList = rotMessSvc.getAll();
			
			
			//	判斷是前台還是後台送來的要求,決定要送往前台或是後台
			//	送往後台,轉交後台的UpdataPage.jsp修改路線資料
			String url = null;
			if(session.getAttribute("empVO") != null) {
				if("updataRoute".equals(request.getParameter("back_manager")) || 
					"路線管理員".equals(((EmpVO)session.getAttribute("empVO")).getEmp_pr())  || 
					"超級管理員".equals(((EmpVO)session.getAttribute("empVO")).getEmp_pr())) {
					//	轉交回審核檢舉頁面
					request.setAttribute("rotMessRepList", rotMessRepList);
					request.setAttribute("rotMessList", rotMessList);
					request.setAttribute("rotMesRep_status", rotMesRep_status);
					request.setAttribute("goWhere", goWhere);
					url = "/back-end/Route/RouteReportPage.jsp";
					RequestDispatcher successView = request.getRequestDispatcher(url);
					successView.forward(request, response);
					System.out.println("Emp_pr = " + ((EmpVO)session.getAttribute("empVO")).getEmp_pr());
					return;
				}else {
					url = "/BackHomePage.jsp";
					System.out.println("Emp_pr = " + ((EmpVO)session.getAttribute("empVO")).getEmp_pr());
					response.sendRedirect(request.getContextPath() + url);
					return;
				}
			}
			System.out.println("Emp_pr = back" );
			url = "/back-end/emp/EmpLogin.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
			
		}
	}

}
