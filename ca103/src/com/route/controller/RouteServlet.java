package com.route.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.emp.model.EmpVO;
import com.mem.model.*;
import com.route.model.*;
import com.routecollection.model.*;
import com.routemessage.model.*;
import com.routemessagereport.model.RouteMessageReportService;
import com.routemessagereport.model.RouteMessageReportVO;
import com.routereport.model.RouteReportService;

@WebServlet("/RouteServlet")
@MultipartConfig
public class RouteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RouteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	ListPage.jsp頁面中路線的代表圖片
		if("getPhoto".equals(request.getParameter("action"))){
			String rot_id = request.getParameter("rot_id");
			RouteService routeSvc = new RouteService();
			routeSvc.getRot_Photo(rot_id, response);
		}else {
			doPost(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String action = request.getParameter("action");
		
		//	這是取出員工ID,測試後台用
		HttpSession session = request.getSession();
		String emp_id = (String)session.getAttribute("emp_id");
		
		//	確認session裡面有無memVO,沒有就導向首頁
		String mem_id = null;
		if(session.getAttribute("memVO") != null) {
			mem_id = ((MemVO)session.getAttribute("memVO")).getMem_id();
		}
		
		//	路線清單+綜合查詢處理-----------------------------------------------------------------------------
		if("getRouteList".equals(action)) {
			
			//	判斷是前台還是後台送來的要求,決定要送往前台或是後台
			String url;
			if("updataRoute".equals(request.getParameter("back_manager"))) {
				if(session.getAttribute("empVO") == null) {
					url = "/back-end/emp/EmpLogin.jsp";
					response.sendRedirect(request.getContextPath() + url);
					return;
				}else if("路線管理員".equals(((EmpVO)session.getAttribute("empVO")).getEmp_pr()) 
						|| "超級管理員".equals(((EmpVO)session.getAttribute("empVO")).getEmp_pr())){
					url = "/back-end/Route/ListPage.jsp";
				}else {
					url = "/BackHomePage.jsp";
					response.sendRedirect(request.getContextPath() + url);
					return;
				}
				
			}else {
				url = "/front-end/Route/ListPage.jsp";
			}
					
			
			
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
			}
			//	條件限制的SQL字串串接
			if(request.getParameter("rot_loc") != null) {
				sql.append(" AND (ROT_LOC_START like '%" + request.getParameter("rot_loc") + "%'" + 
						" OR ROT_LOC_END like '%" + request.getParameter("rot_loc") + "%')");
			}
			
			//	模糊比對的SQL字串串接,串接完後送至DAO取得路線清單
			//	模糊比對項目：路線名稱、路線描述、起點描述、終點描述、起點位置、終點位置
			if(request.getParameter("text") != null) {
				if(!"".equals(request.getParameter("text").trim())) {
					sql.append(" AND (ROT_NAME like '%" + request.getParameter("text") + 
					"%' OR ROT_DESCRIBE like '%" + request.getParameter("text") +
					"%' OR ROT_LOC_START_DES like '%" + request.getParameter("text") + 
					"%' OR ROT_LOC_END_DES like '%" + request.getParameter("text") + 
					"%' OR rot_loc_start like '%" + request.getParameter("text") + 
					"%' OR rot_loc_end like '%" + request.getParameter("text") + 
					"%')");
				}
			}
			
			//	搜尋區塊：全部路線
			if("route".equals(order)) {
				sql.append(" order by rot_name desc");
			}
			
			//	搜尋區塊：賽事路線
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
			
			//	管理員功能------------------------取得被封存的路線清單
			if("route_close".equals(order)) {
				sqlColse.append(" order by rot_id desc");
			}
			
			//	開始查詢路線清單
			RouteService rotSvc = new RouteService();
			List<RouteVO> routeList;
			
			System.out.println("SELECT * FROM ROUTE WHERE ROT_STATUS=1 " + sql.toString());
			
			//	sqlColse空字串代表查詢非封存路線,sqlColse有值代表查詢封存路線
			if(sqlColse.toString().equals("")) {
				routeList = rotSvc.getRouteList(sql.toString());
			}else {
				routeList = rotSvc.getRouteCloseList(sqlColse.toString());
			}
			
			//	開始查詢會員取得暱稱
			MemService memSvc = new MemService();
			List<MemVO> memList = memSvc.getAll();
			
			//	開始查詢路線留言數量
			RouteMessageService rotMessSvc = new RouteMessageService();
			Map<String, Integer> routeMessageCountMap = rotMessSvc.getRouteMessageCount();
				
			//	開始轉交,唯一一個方式是把資訊都丟進session裡面的!!因為要配合分頁的取值,所以用session省去資料要在頁面之間傳遞的麻煩
			session.setAttribute("routeList", routeList); 
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
			System.out.println("route_game = " + route_game);
			
			System.out.println("request.getParameter(\"back_manager\") = " + request.getParameter("back_manager"));
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
			//	轉交ListRoute.jsp秀出路線清單
		}
		
		//	路線詳細資訊處理-----------------------------------------------------------------------------
		if("getRouteDetailed".equals(action)) {
			//	接收參數
			String rot_id = request.getParameter("rot_id");
			String goWhere = (String)request.getParameter("goWhere");
			
			//	開始查詢路線詳細資料
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
		    
		    //	列出登入的會員檢舉過的留言編號,比對該路線的留言編號是否有檢舉過,以便於留言下方的檢舉按鈕的顯示與隱藏
		    RouteMessageReportService routeMessageReportService = new RouteMessageReportService();
		    List listRouteMessageReport = routeMessageReportService.rotMessRepOrNot(mem_id);
		    
			//	取得listRouteMessage的大小確認要從第幾個索引值取物件
			int  pageIndex = 0;
			if(request.getParameter("pageIndex") != null) {
				//	listRouteMessage.size() % 5 == 1  條件是為了確認輸入的留言是不是每頁的第一筆,如果是換頁數要多加一
				//	listRouteMessage.size() != 1 條件式為了排除「第一筆輸入的資料」,避免第一頁的第一筆資料造成換頁數多加一
				if(listRouteMessage.size() % 5 == 1 && listRouteMessage.size() != 1) {
					pageIndex = new Integer(request.getParameter("pageIndex")) + 5;
				}else {
					pageIndex = new Integer(request.getParameter("pageIndex"));
				}
			}
			
			
			//	開始轉交
			session.setAttribute("routeVO", routeVO);
			//	10/6原為session,後改為request
			session.setAttribute("memList", memList); 
			session.setAttribute("listRouteMessage",listRouteMessage);
			session.setAttribute("collOrNot", collOrNot);
			session.setAttribute("rotRepOrNot", rotRepOrNot);
			session.setAttribute("listRouteMessageReport", listRouteMessageReport); 
			//	下面兩個屬性是為了錨點
			request.setAttribute("goWhere", goWhere);
			request.setAttribute("pageIndex", pageIndex);
			String url;
			
			//	判斷是前台還是後台送來的要求,決定要送往前台或是後台
			//	送往後台,轉交後台的UpdataPage.jsp修改路線資料
			if(session.getAttribute("empVO") != null) {
				if("updataRoute".equals(request.getParameter("back_manager")) || 
					"路線管理員".equals(((EmpVO)session.getAttribute("empVO")).getEmp_pr())  || 
					"超級管理員".equals(((EmpVO)session.getAttribute("empVO")).getEmp_pr())) {
					url = "/back-end/Route/RoutePage.jsp";
					System.out.println("後台-修改路線");
					RequestDispatcher successView = request.getRequestDispatcher(url);
					successView.forward(request, response);
					return;
				}else {
					url = "/BackHomePage.jsp";
					response.sendRedirect(request.getContextPath() + url);
					return;
				}
			}
			
			//	送往前端,轉交UpdataPage.jsp修改路線資料
			if("updataRoute".equals(request.getParameter("front_manager"))) {
				url = "/front-end/Route/UpdataPage.jsp";
				System.out.println("前台-修改路線");
			//	轉交RoutePage.jsp秀出詳細路線資訊
			}else {
				url = "/front-end/Route/RoutePage.jsp";
				System.out.println("前台-查詢路線");
				//	計算一次路線熱門度
				rotSvc.updataRoute_back(rot_id, routeVO.getRot_popu()+1);
			}
			
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
			
		}
		
		//	新增路線處理-----------------------------------------------------------------------------
		if("insertRoute".equals(action)) {
			//	若非會員則導向首頁
			if(session.getAttribute("memVO") == null) {
				String url = "/front-end/mem/Login.jsp";
				response.sendRedirect(request.getContextPath() + url);
				return;
			}
			
			//	接收參數-輸入錯誤處理
			String rot_name,rot_describe,rot_loc_start_des,rot_loc_end_des, rot_loc_start, rot_loc_end;
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			//	路線命名若有填寫,則使用填寫的字串,若無填寫則使用預設字串「未命名」
			if("".equals(request.getParameter("rot_name").trim())) {
				rot_name = "未命名";
			}else {
				rot_name = request.getParameter("rot_name").trim();
			}
			
			//	路線描述若有填寫,則使用填寫的字串,若無填寫則使用預設字串「未命名」
			if("".equals(request.getParameter("rot_describe").trim())) {
				rot_describe = "未描述";
			}else {
				rot_describe = request.getParameter("rot_describe").trim();
			}
			
			//	路線起點描述若有填寫,則使用填寫的字串,若無填寫則使用預設字串「未命名」
			if("".equals(request.getParameter("rot_loc_start_des").trim())) {
				rot_loc_start_des = "未描述";
			}else {
				rot_loc_start_des = request.getParameter("rot_loc_start_des").trim();
			}
			
			//	路線終點描述若有填寫,則使用填寫的字串,若無填寫則使用預設字串「未命名」
			if("".equals(request.getParameter("rot_loc_end_des").trim())) {
				rot_loc_end_des = "未描述";
			}else {
				rot_loc_end_des = request.getParameter("rot_loc_end_des").trim();
			}
			
			//	路線起點若有選擇,則使用選擇的縣市,若無選擇則使用預設字串「-」
			if("0".equals(request.getParameter("rot_loc_start"))) {
				rot_loc_start = " - ";
			}else {
				rot_loc_start = request.getParameter("rot_loc_start");
			}
			
			//	路線終點描述若有選擇,則使用選擇的縣市,若無選擇則使用預設字串「-」
			if("0".equals(request.getParameter("rot_loc_end"))) {
				rot_loc_end = " - ";
			}else {
				rot_loc_end = request.getParameter("rot_loc_end");
			}
			
			String rot_start = null, rot_end = null, rot_gps = null;
			Double rot_hard = null, rot_dis = null, rot_height_up = null, rot_height_down = null, rot_height_ave = null, 
					rot_slope_up = null, rot_slope_down = null, rot_slope_ave = null, rot_photo_loc = null;
			Integer rot_status = null, rot_popu = null;
			try{
			rot_hard = new Double(request.getParameter("rot_hard"));
			rot_dis= new Double(request.getParameter("rot_dis"));
			rot_height_up = new Double(request.getParameter("rot_height_up"));
			rot_height_down = new Double(request.getParameter("rot_height_down"));
			rot_height_ave = new Double(request.getParameter("rot_height_ave"));
			rot_slope_up = new Double(request.getParameter("rot_slope_up"));
			rot_slope_down = new Double(request.getParameter("rot_slope_down"));
			rot_slope_ave = new Double(request.getParameter("rot_slope_ave"));
			rot_status = new Integer(request.getParameter("rot_status"));
			
			//	rot_popu在創建路線時內建從0開始
			rot_popu = new Integer(0);
			rot_photo_loc = new Double(request.getParameter("rot_photo_loc"));
			rot_start = request.getParameter("rot_start");
			rot_end = request.getParameter("rot_end");
			rot_gps = request.getParameter("rot_gps");
			}catch(NumberFormatException nfe){
				nfe.printStackTrace(System.err);
				errorMsgs.add("QQ");
			}
			
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = request.getRequestDispatcher("/front-end/Route/InsertPage.jsp");
				failureView.forward(request, response);
				return;
			}
			
			String rot_photo = null;
			try {
				//	圖片改成Base64上傳資料庫
				Part rot_photo_part = request.getPart("rot_photo");
				InputStream rot_photo_part_in = rot_photo_part.getInputStream();
				byte[] buffer = new byte[rot_photo_part_in.available()];
				rot_photo_part_in.read(buffer);
				rot_photo_part_in.close();
				Base64.Encoder encoder = Base64.getEncoder();
				rot_photo = encoder.encodeToString(buffer);
			} catch(Exception e) {
				e.printStackTrace(System.err);
			}
			
			//	開始新增路線
			RouteService rotSvc = new RouteService();
			RouteVO routeVO = rotSvc.addRoute(mem_id, rot_name, rot_describe, rot_loc_start, rot_loc_end, 
					rot_hard, rot_dis, rot_height_up, rot_height_down, rot_height_ave, rot_slope_up, 
					rot_slope_down, rot_slope_ave, rot_loc_start_des, rot_loc_end_des, rot_status, rot_photo, 
					rot_popu, rot_photo_loc, rot_start, rot_end, rot_gps);
			
			//	開始新增到收藏路線
			String sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			java.sql.Timestamp rotColl_time = java.sql.Timestamp.valueOf(sdate);
			RouteCollectionService rotCollSvc = new RouteCollectionService();
			rotCollSvc.addCollection(mem_id, routeVO.getRot_id(), rotColl_time);
			
			//	開始轉交RoutePage.jsp秀出詳細路線資訊,會造成request重複遞交,所以棄用此方法
//			request.setAttribute("routeVO", routeVO);
//			String url = "/front-end/Route/RoutePage.jsp";
//			RequestDispatcher successView = request.getRequestDispatcher(url);
//			successView.forward(request, response);
			
			
			//	重導非轉交,避免重新整理造成的request重複遞交
			String url = "/Route/RouteServlet.do?action=getRouteDetailed&rot_id=";
			response.sendRedirect(request.getContextPath() + url + routeVO.getRot_id());
		}
		
		//	刪除路線-實際上是封存不顯示,把路線代表的rot_status修正成為數字2即可--------------------------------------------------
		if("closeRoute".equals(action)) {
			
			//	接收參數
			String rot_id = request.getParameter("rot_id");
			String front = request.getParameter("front");
			
			//	開始刪除(封存)路線
			RouteService rotSvc = new RouteService();
			rotSvc.closeRoute(rot_id);
			
			//	開始轉交
			String url = null;
			if("front".equals(front)) {
				//	當front的值等於front時代表從前台送進來的要求,轉交至該會員的收藏路線列表頁面
				RouteCollectionService rotCollSvc = new RouteCollectionService();
				List<RouteVO> routeCollectionList = rotCollSvc.findRouteCollectionList(mem_id, "= 0 ");
				session.setAttribute("route_personal", "route_personal");
				session.setAttribute("routeCollectionList", routeCollectionList);
				url = "/front-end/Route/RouteCollectionListPage.jsp";
			}else {
				//	轉交RoutePage.jsp回到路線清單頁面
				url = "/Route/RouteServlet.do?action=getRouteList&back_manager=updataRoute";
			}
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		//	修改路線----------------------------------------------------------------------------------------
		if("updataRoute".equals(action)) {
			
			//	接收參數
			String rot_name,rot_describe,rot_loc_start_des,rot_loc_end_des, rot_loc_start, rot_loc_end;
			String rot_id = request.getParameter("rot_id");
			String rot_mem_id = request.getParameter("mem_id");
			
			//	路線命名若有填寫,則使用填寫的字串,若無填寫則使用預設字串「未命名」
			if("".equals(request.getParameter("rot_name").trim())) {
				rot_name = "未命名";
			}else {
				rot_name = request.getParameter("rot_name").trim();
			}
			
			//	路線描述若有填寫,則使用填寫的字串,若無填寫則使用預設字串「未命名」
			if("".equals(request.getParameter("rot_describe").trim())) {
				rot_describe = "未描述";
			}else {
				rot_describe = request.getParameter("rot_describe").trim();
			}
			
			//	路線起點描述若有填寫,則使用填寫的字串,若無填寫則使用預設字串「未命名」
			if("".equals(request.getParameter("rot_loc_start_des").trim())) {
				rot_loc_start_des = "未描述";
			}else {
				rot_loc_start_des = request.getParameter("rot_loc_start_des").trim();
			}
			
			//	路線終點描述若有填寫,則使用填寫的字串,若無填寫則使用預設字串「未命名」
			if("".equals(request.getParameter("rot_loc_end_des").trim())) {
				rot_loc_end_des = "未描述";
			}else {
				rot_loc_end_des = request.getParameter("rot_loc_end_des").trim();
			}
			
			//	路線起點若有選擇,則使用選擇的縣市,若無選擇則使用預設字串「-」
			if("0".equals(request.getParameter("rot_loc_start"))) {
				rot_loc_start = " - ";
			}else {
				rot_loc_start = request.getParameter("rot_loc_start");
			}
			
			//	路線終點描述若有選擇,則使用選擇的縣市,若無選擇則使用預設字串「-」
			if("0".equals(request.getParameter("rot_loc_end"))) {
				rot_loc_end = " - ";
			}else {
				rot_loc_end = request.getParameter("rot_loc_end");
			}
			
			//	路線狀態,若是忘記選擇的話就為公開路線
			Integer rot_status = 1;
			if(null!=request.getParameter("rot_status")) {
				rot_status = new Integer(request.getParameter("rot_status"));
			}
			
			//	圖片的位置
			Double rot_photo_loc = new Double(request.getParameter("rot_photo_loc"));
			
			Double rot_hard = null, rot_dis = null, rot_height_up = null, rot_height_down = null, rot_height_ave = null, 
					rot_slope_up = null, rot_slope_down = null, rot_slope_ave = null;
			Integer rot_popu = null;
			
			if("updataRoute".equals(request.getParameter("back_manager"))) {
				rot_hard = new Double(request.getParameter("rot_hard"));
				rot_dis= new Double(request.getParameter("rot_dis"));
				rot_height_up = new Double(request.getParameter("rot_height_up"));
				rot_height_down = new Double(request.getParameter("rot_height_down"));
				rot_height_ave = new Double(request.getParameter("rot_height_ave"));
				rot_slope_up = new Double(request.getParameter("rot_slope_up"));
				rot_slope_down = new Double(request.getParameter("rot_slope_down"));
				rot_slope_ave = new Double(request.getParameter("rot_slope_ave"));
				rot_popu = new Integer(request.getParameter("rot_popu"));
			}
			
			//	圖片改成Base64上傳資料庫
			//	修改時若無重新上傳圖片,則抓取舊的圖片的Base64資料再重新設定回去
			String rot_photo = null;
			if("new_rot_photo".equals(request.getParameter("old_rot_photo"))) {
				Part rot_photo_part = request.getPart("rot_photo");
				InputStream rot_photo_part_in = rot_photo_part.getInputStream();
				byte[] buffer = new byte[rot_photo_part_in.available()];
				rot_photo_part_in.read(buffer);
				rot_photo_part_in.close();
				Base64.Encoder encoder = Base64.getEncoder();
				rot_photo = encoder.encodeToString(buffer);
			}else {
				rot_photo = request.getParameter("old_rot_photo");
			}

			//	開始修改路線
			RouteService rotSvc = new RouteService();
			//	後台送進來的修改路線要求
			if("updataRoute".equals(request.getParameter("back_manager"))) {
				rotSvc.updataRoute_back(rot_id,  rot_mem_id, rot_name, rot_describe, rot_loc_start,
						 rot_loc_end, rot_hard, rot_dis, rot_height_up, rot_height_down,
						 rot_height_ave, rot_slope_up, rot_slope_down, rot_slope_ave, rot_loc_start_des,
						 rot_loc_end_des, rot_status, rot_photo, rot_popu, rot_photo_loc);
			//	前台送進來的修改路線要求
			}else {
				rotSvc.updataRoute_front(rot_id, rot_name, rot_describe, rot_loc_start, rot_loc_end,
						rot_loc_start_des, rot_loc_end_des, rot_status, rot_photo, rot_photo_loc);
			}
			//	重新讀取修改後的路線資料
			RouteVO routeVO = rotSvc.getRouteDetailed(rot_id);
			
			//	開始轉交
			request.setAttribute("routeVO", routeVO);
			//	back_manager的值是updataRoute,來源為後台;反之來源為前台
			String url =null;
			if("updataRoute".equals(request.getParameter("back_manager"))) {
				url = "/back-end/Route/ListPage.jsp";
			}else {
				url = "/front-end/Route/RoutePage.jsp";
			}
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
		
		//	進入「建立路線」的頁面-只需要審核是否為會員即可--------------------------------------------------
		if("insideRouteBuilding".equals(action)) {
			
			//	若非會員則導向首頁
			if(session.getAttribute("memVO") == null) {
				String url = "/front-end/mem/Login.jsp";
				response.sendRedirect(request.getContextPath() + url);
				return;
			}
			
			
			//	開始轉交InsertPage.jsp回到建立路線清單頁面
			String url = "/front-end/Route/InsertPage.jsp";
			RequestDispatcher successView = request.getRequestDispatcher(url);
			successView.forward(request, response);
		}
	}

}
