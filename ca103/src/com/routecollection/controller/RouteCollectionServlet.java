package com.routecollection.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

@WebServlet("/RouteCollection")
public class RouteCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RouteCollectionServlet() {
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
		
		//	收藏路線
		if("insertRoute".equals(action)) {
			//	接收參數
			String rot_id = request.getParameter("rot_id");
			String goWhere = (String)request.getParameter("goWhere");
			
			String sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			java.sql.Timestamp rotColl_time = java.sql.Timestamp.valueOf(sdate);
			
			//	接收tokens
			String reqUUID = request.getParameter("uuid");
			String sessUUID = (String) session.getAttribute("uuid");
			session.removeAttribute("uuid");
			
			if(reqUUID.equals(sessUUID)){
				//	開始新增
				RouteCollectionService rotCollSvc = new RouteCollectionService();
				rotCollSvc.addCollection(mem_id, rot_id,rotColl_time);
				
				//	取得原頁面的資料以利於操作完停留此頁面
				RouteService rotSvc = new RouteService();
				RouteVO routeVO = rotSvc.getRouteDetailed(rot_id);
				
				//	開始查詢會員取得暱稱
				MemService memSvc = new MemService();
				List<MemVO> memList = memSvc.getAll();
				
				//	查詢路線的留言資料,並且轉交給路線詳細資料頁面
				RouteMessageService routeMessageSvc = new RouteMessageService();
				List<RouteMessageVO> listRouteMessage = routeMessageSvc.getByRouteId(routeVO.getRot_id());
				
				//	顯示此路線是否有被登入的該會員收藏,以便於在路線下方的收藏按鈕的顯示與隱藏
			    Integer collOrNot = rotCollSvc.collOrNot(rot_id, mem_id);
			    
				//	顯示此路線是否有被登入的該會員檢舉,以便於在路線下方的檢舉按鈕的顯示與隱藏
			    RouteReportService routeReportSvc = new RouteReportService();
			    Integer rotRepOrNot = routeReportSvc.rotRepOrNot(rot_id, mem_id);
			    
				//	顯示此則留言是否有被登入的該會員檢舉,以便於在留言內部的檢舉按鈕的顯示與隱藏
				RouteMessageReportService routeMessageReportService = new RouteMessageReportService();
				List<RouteMessageReportVO> listRouteMessageReportVO = routeMessageReportService.rotMessRepOrNot(mem_id);
				
				//	開始轉交資料到RoutePage.jsp
				session.setAttribute("routeVO", routeVO); 
				session.setAttribute("memList", memList); 
				session.setAttribute("listRouteMessage",listRouteMessage);
				session.setAttribute("collOrNot", collOrNot);
				session.setAttribute("rotRepOrNot", rotRepOrNot);
				session.setAttribute("listRouteMessageReportVO", listRouteMessageReportVO);
				request.setAttribute("goWhere", goWhere);
				String url = "/front-end/Route/RoutePage.jsp";
				RequestDispatcher successView = request.getRequestDispatcher(url);
				successView.forward(request, response);
			}else{
				request.getRequestDispatcher("/front-end/Route/RoutePage.jsp").forward(request, response);
			}
		}
		
		//	收藏路線的清單列表
		if("getRouteCollectionList".equals(action)) {
			
			//	接收參數,建立空的sql,用以串接前端送進來的查詢字串
			//	接收參數,建利空的sqlColse,用以串接後端送進來的查詢封存路線字串
			StringBuilder sql = new StringBuilder();
			StringBuilder sqlColse = new StringBuilder();
			
			//	接收參數,取得排行條件
			String order = request.getParameter("order");
			String route_game = request.getParameter("route_game");
			
			//	條件限制的SQL字串串接
			if(request.getParameter("hard") != null) {
				sql.append(" AND ROT_HARD" + request.getParameter("hard"));
				sql.append(" AND ROT_DIS" + request.getParameter("dis"));
				sql.append(" AND ROT_SLOPE_AVE" + request.getParameter("slope"));
				sql.append(" AND (ROT_LOC_START like '%" + request.getParameter("rot_loc") + "%'" + 
							" OR ROT_LOC_END like '%" + request.getParameter("rot_loc") + "%')");
			}
			
			//	模糊比對的SQL字串串接,串接完後送至DAO取得路線清單
			if(request.getParameter("text") != null) {
				if(!"".equals(request.getParameter("text").trim())) {
					sql.append(" AND (ROT_NAME like '%" + request.getParameter("text") + 
					"%' OR ROT_DESCRIBE like '%" + request.getParameter("text") +
					"%' OR ROT_LOC_START_DES like '%" + request.getParameter("text") + 
					"%' OR ROT_LOC_END_DES like '%" + request.getParameter("text") + "%')");
				}
			}
			
			//	排行條件：全部路線
			if("route".equals(order)) {
				sql.append(" order by rot_name desc");
			}
			
			//	賽事路線的條件
			if("route_game".equals(route_game)) {
				sql.append(" AND (ROT_NAME like '%賽事%' OR ROT_NAME like '%活動%')");
			}
			
			//	搜尋區塊：非賽事路線
			if("route_not_game".equals(route_game)) {
				sql.append(" AND ROT_NAME not like '%賽事%' AND ROT_NAME not like '%活動%'");
			}
			
			//	排行條件：熱門度
			if("route_popu".equals(order)) {
				sql.append(" order by rot_popu desc");
			}
			
			//	排行條件：難度
			if("route_hard".equals(order)) {
				sql.append(" order by rot_hard desc");
			}
			
			//	排行條件：距離
			if("route_dis".equals(order)) {
				sql.append(" order by rot_dis desc");
			}
			
			//	排行條件：坡度
			if("route_slope_ave".equals(order)) {
				sql.append(" order by rot_slope_up desc");
			}
			
			//	開始查詢,根據request.getParameter("route_personal")判斷是否取出「收藏的私人路線」還是「收藏的公開路線」
			RouteCollectionService rotCollSvc = new RouteCollectionService();
			List<RouteVO> routeCollectionList;
			if("route_personal".equals(request.getParameter("route_personal"))) {
				routeCollectionList = rotCollSvc.findRouteCollectionList(mem_id,"= 0 " + sql.toString());
				System.out.println("= 0 " + sql.toString());
			}else {
				routeCollectionList = rotCollSvc.findRouteCollectionList(mem_id,"!= 2 " + sql.toString());
				System.out.println("!= 2 " + sql.toString());
			}
			
			//	開始查詢會員取得暱稱
			MemService memSvc = new MemService();
			List<MemVO> memList = memSvc.getAll();
			
			//	開始查詢路線留言數量
			RouteMessageService rotMessSvc = new RouteMessageService();
			Map<String, Integer> routeMessageCountMap = rotMessSvc.getRouteMessageCount();
			
			//	開始轉交,唯一一個方式是把資訊都丟進session裡面的!!因為要配合分頁的取值,所以用session省去資料要在頁面之間傳遞的麻煩
			session.setAttribute("routeCollectionList", routeCollectionList); 
			session.setAttribute("memList", memList); 
			session.setAttribute("routeMessageCountMap", routeMessageCountMap); 
			//	下面四個屬性是為了保存條件查詢時所送進來的「條件」,再把這些條件送回去jsp
			session.setAttribute("hard", request.getParameter("hard"));
			session.setAttribute("dis", request.getParameter("dis"));
			session.setAttribute("slope", request.getParameter("slope"));
			session.setAttribute("rot_loc", request.getParameter("rot_loc"));
			session.setAttribute("text", request.getParameter("text"));
			//	下面這個屬性是為了保存排名條件時所送進來的「條件」,再把這些條件送回去jsp
			session.setAttribute("route_order", request.getParameter("order"));
			session.setAttribute("oldSql", sql.toString());
			session.setAttribute("route_game", request.getParameter("route_game"));
			session.setAttribute("route_personal", request.getParameter("route_personal"));
			
			String url = "/front-end/Route/RouteCollectionListPage.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
			//	轉交RouteCollectionListPage.jsp秀出路線清單
		}
		
		//	刪除收藏路線列表的路線
		if("deleteCollectionRoute".equals(action)) {
			//	接收參數
			String rot_id = request.getParameter("rot_id");
			String goWhere = (String)request.getParameter("goWhere");
			
			//	開始刪除
			RouteCollectionService rotCollSvc = new RouteCollectionService();
			rotCollSvc.deleteCollectionRoute(rot_id,mem_id);
			
			//	取得原頁面的資料以利於操作完停留此頁面
			RouteService rotSvc = new RouteService();
			RouteVO routeVO = rotSvc.getRouteDetailed(rot_id);
			
			//	開始查詢會員取得暱稱
			MemService memSvc = new MemService();
			List<MemVO> memList = memSvc.getAll();
			
			//	查詢路線的留言資料,並且轉交給路線詳細資料頁面
			RouteMessageService routeMessageSvc = new RouteMessageService();
			List<RouteMessageVO> listRouteMessage = routeMessageSvc.getByRouteId(routeVO.getRot_id());
			
			//	顯示此路線是否有被登入的該會員收藏,以便於在路線下方的收藏按鈕的顯示與隱藏
		    Integer collOrNot = rotCollSvc.collOrNot(rot_id, mem_id);
		    
			//	顯示此路線是否有被登入的該會員檢舉,以便於在路線下方的檢舉按鈕的顯示與隱藏
		    RouteReportService routeReportSvc = new RouteReportService();
		    Integer rotRepOrNot = routeReportSvc.rotRepOrNot(rot_id, mem_id);
		    
			//	顯示此則留言是否有被登入的該會員檢舉,以便於在留言內部的檢舉按鈕的顯示與隱藏
			RouteMessageReportService routeMessageReportService = new RouteMessageReportService();
			List<RouteMessageReportVO> listRouteMessageReportVO = routeMessageReportService.rotMessRepOrNot(mem_id);
			
			//	開始轉交資料到RoutePage.jsp
			session.setAttribute("routeVO", routeVO); 
			session.setAttribute("memList", memList); 
			session.setAttribute("listRouteMessage",listRouteMessage);
			session.setAttribute("collOrNot", collOrNot);
			session.setAttribute("rotRepOrNot", rotRepOrNot);
			session.setAttribute("listRouteMessageReportVO", listRouteMessageReportVO); 
			request.setAttribute("goWhere", goWhere);
			String url = "/front-end/Route/RoutePage.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
	}
}
