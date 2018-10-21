package com.routemessage.controller;

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

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.route.model.RouteService;
import com.route.model.RouteVO;
import com.routecollection.model.RouteCollectionService;
import com.routemessage.model.RouteMessageService;
import com.routemessage.model.RouteMessageVO;
import com.routemessagereport.model.RouteMessageReportService;
import com.routemessagereport.model.RouteMessageReportVO;
import com.routereport.model.RouteReportService;

@WebServlet("/RouteMessageServlet")
public class RouteMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RouteMessageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		
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
		
		
		//	新增路線留言
		if("insertMessage".equals(action)) {
			
			//	接收參數
			String rot_id = request.getParameter("rot_id");
			String rotMes_cont = request.getParameter("rotMes_cont").replaceAll("\r|\n", "");
			String sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			java.sql.Timestamp rotMes_time = java.sql.Timestamp.valueOf(sdate);
			String goWhere = request.getParameter("goWhere");
			
			//	取得留言的錨點位置
			int pageIndex = 0;
			if(request.getParameter("pageIndex") != null) {
				pageIndex = new Integer(request.getParameter("pageIndex"));
			}
			
			//	開始新增
			RouteMessageService rotMesSvc = new RouteMessageService();
			rotMesSvc.inserRouteMessage(mem_id,rot_id,rotMes_cont,rotMes_time);
			
			//	更新ROUTE表格內的留言數量
			RouteService rotSvc = new RouteService();
			rotSvc.update_messageCount(rot_id);
			
			//	重導非轉交
			String url = "/Route/RouteServlet.do?action=getRouteDetailed&rot_id=";
			response.sendRedirect(request.getContextPath() + url + rot_id + "&goWhere=" + goWhere + "&pageIndex=" + pageIndex);

			
			
			
			//	開始轉交 - 轉交會造成「留言後立刻重整頁面,留言會不斷增加」的狀況,所以改用重導的方式去避免非預期性的重複留言
			//	取得來源該頁的路徑資訊,一起轉交回RoutePage.jsp
//			RouteService routeSvc = new RouteService();
//			RouteVO routeVO = routeSvc.getRouteDetailed(rot_id);
//			request.setAttribute("routeVO", routeVO);
//			String url = "/front-end/Route/RoutePage.jsp";
//			RequestDispatcher successView = request.getRequestDispatcher(url);
//			successView.forward(request, response);
			
			//	測試轉交...
//			RouteService routeSvc = new RouteService();
//			RouteVO routeVO = routeSvc.getRouteDetailed(rot_id);
//			request.setAttribute("rot_id", rot_id);
//			request.setAttribute("action", "getRouteDetailed");
//			String url = "/Route/RouteServlet.do";
//			RequestDispatcher successView = request.getRequestDispatcher(url);
//			successView.forward(request, response);
			
		}
		
		if("getByRouteId".equals(action)) {
			//	目前不會進來servlet
		}
		
		//	刪除留言
		if("closeRouteMessage".equals(action)) {
			
			//	接收參數
			Integer rotMes_id = new Integer(request.getParameter("rotMes_id"));
			String rot_id = request.getParameter("rot_id");
			String front = request.getParameter("front");
			
			//	取得刪除留言的錨點位置
			int pageIndex = 0;
			if(request.getParameter("pageIndex") != null) {
				pageIndex = new Integer(request.getParameter("pageIndex"));
			}
			String goWhere = request.getParameter("goWhere");
			
			//	開始刪除
			RouteMessageService rotMesSvc = new RouteMessageService();
			rotMesSvc.closeRouteMessage(rotMes_id);
			
			//	重導非轉交,若有取得front字串則為前台送來的請求,返回前台
			String url = null;
			if("front".equals(front)) {
				url = "/Route/RouteServlet.do?action=getRouteDetailed&goWhere=" + goWhere + "&rot_id=" + rot_id +"&pageIndex=" + pageIndex;
			}else {
				url = "/back-end/Route/RoutePage.jsp";
			}
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
			
			
			
			//	開始轉交,先把rot_id丟給RouteService處理,取得該路線的詳細資訊,再丟給jsp顯示
//			RouteService rotSvc = new RouteService();
//			RouteVO routeVO = rotSvc.getRouteDetailed(rot_id);
//			request.setAttribute("routeVO", routeVO);
		}
		
		//	修改留言
		if("updateRouteMessage".equals(action)) {
			
			//	接收參數
			Integer rotMes_id = new Integer(request.getParameter("rotMes_id"));
			String rot_id = request.getParameter("rot_id");
			String rotMes_cont = request.getParameter("rotMes_cont").replaceAll("\r|\n", "");
			//	下面的參數是為了修改留言的定位使用
			Integer whichPage = new Integer(request.getParameter("whichPage"));
			Integer pageIndex = new Integer(request.getParameter("pageIndex"));
			String goWhere = request.getParameter("goWhere");
			
			//	開始修改
			RouteMessageService rotMesSvc = new RouteMessageService();
			rotMesSvc.updateRouteMessage(rotMes_id, rotMes_cont);
			
			//	開始轉交,先把rot_id丟給RouteService處理,取得該路線的詳細資訊,再丟給jsp顯示
			RouteService rotSvc = new RouteService();
			RouteVO routeVO = rotSvc.getRouteDetailed(rot_id);
			
			//	開始查詢會員取得暱稱
			MemService memSvc = new MemService();
			List<MemVO> memList = memSvc.getAll();
			
			//	查詢路線的留言資料,並且轉交給路線詳細資料頁面
			RouteMessageService routeMessageSvc = new RouteMessageService();
			List<RouteMessageVO> listRouteMessage = routeMessageSvc.getByRouteId(rot_id);
			
			//	顯示此路線是否有被登入的該會員收藏,以便於在路線下方的收藏按鈕的顯示與隱藏
			RouteCollectionService rotCollSvc = new RouteCollectionService();
		    Integer collOrNot = rotCollSvc.collOrNot(rot_id, mem_id);
		    
			//	顯示此路線是否有被登入的該會員檢舉,以便於在路線下方的檢舉按鈕的顯示與隱藏
		    RouteReportService routeReportSvc = new RouteReportService();
		    Integer rotRepOrNot = routeReportSvc.rotRepOrNot(rot_id, mem_id);
		    
			//	顯示此則留言是否有被登入的該會員檢舉,以便於在留言內部的檢舉按鈕的顯示與隱藏
			RouteMessageReportService routeMessageReportService = new RouteMessageReportService();
			List<RouteMessageReportVO> listRouteMessageReportVO = routeMessageReportService.rotMessRepOrNot(mem_id);
					
			
			session.setAttribute("routeVO", routeVO);
			session.setAttribute("memList", memList); 
			session.setAttribute("listRouteMessage",listRouteMessage);
			session.setAttribute("collOrNot", collOrNot);
			session.setAttribute("rotRepOrNot", rotRepOrNot);
			session.setAttribute("listRouteMessageReportVO", listRouteMessageReportVO);
			//	把定位需要的參數轉送出去
			request.setAttribute("whichPage", whichPage);
			request.setAttribute("pageIndex", pageIndex);
			request.setAttribute("goWhere", goWhere);
			String url = "/front-end/Route/RoutePage.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
	}

}
