package com.activity.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.activity.model.Activity_Service;
import com.activity.model.Activity_VO;
//import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.emp.model.EmpVO;

@MultipartConfig // (fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024,
// maxRequestSize = 5 * 5 * 1024 * 1024)
public class Activity_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		System.out.println("進入act控制器");
		Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
		String action = req.getParameter("action");
		String urlRegularEx = "^((http[s]?|ftp):\\/)?\\/?([^:\\/\\s]+)((\\/\\w+)*\\/)([\\w\\-\\.]+[^#?\\s]+)(.*)?(#[\\w\\-]+)?$";
		
		
		// 接收員工資料
		


		if ("addAct".equals(action)) {
			System.out.println("成功進入新增活動控制器");
			req.setAttribute("errorMsgs", errorMsgs);

			String cusName = req.getParameter("cusName");
			if (cusName.trim().length() == 0 || cusName == null) {

				errorMsgs.put("cusName", "客戶姓名不可空");

			}

			System.out.println("成功進入新增活動控制器2:" + cusName);

			String cusMail = req.getParameter("cusMail");
			if (cusMail.trim().length() == 0 || cusMail == null) {

				errorMsgs.put("cusMail", "客戶郵件不可空");
			}

//			System.out.println("成功進入新增活動控制器3");

			String act_name = req.getParameter("act_name");
			if (act_name.trim().length() == 0 || act_name == null) {

				errorMsgs.put("act_name", "活動名稱不可空");
			}

			String act_info = req.getParameter("act_info");
			if (act_info.trim().length() == 0 || act_info == null) {

				errorMsgs.put("act_info", "活動簡介不可空");
			}


			Part activityPhoto = req.getPart("activityPhoto");
			InputStream in = activityPhoto.getInputStream();
			byte[] activityPhotoB = new byte[in.available()];
			in.read(activityPhotoB);

			String act_holder = req.getParameter("act_holder");
			if (act_holder.trim().length() == 0 || act_holder == null) {

				errorMsgs.put("act_holder", "活動主辦單位不可空");
			}

			String act_href = req.getParameter("act_href");
			if (act_href.trim().length() == 0 || act_href == null) {

				errorMsgs.put("act_href", "請至少填寫填寫活動相關連結");
			}
			
		
			

			String act_href2 = req.getParameter("act_href2");
			if (act_href2.trim().length() == 0 || act_href2 == null) {

				act_href2 = "略";
			}
			System.out.println("act_href2act_href2" + act_href2);
			
//			if(!act_href2.matches(urlRegularEx)) {
//				errorMsgs.put("act_href", "請輸入正確的超連結格式");
//			}
			
			

			Date act_sDate = null;
			try {
				act_sDate = java.sql.Date.valueOf(req.getParameter("act_sDate").trim());
			} catch (IllegalArgumentException e) {
				act_sDate = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.put("act_sDate", "請輸入日期!");
			}

			Date act_eDate = null;
			try {
				act_eDate = java.sql.Date.valueOf(req.getParameter("act_eDate").trim());
			} catch (IllegalArgumentException e) {
				act_eDate = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.put("act_eDate", "請輸入日期!");
			}
			
			if(act_eDate.before(act_sDate)) {
				errorMsgs.put("act_eDate", "活動結束日期不可早於活動開始日期");

			}
			

			Date act_regis_date = null;
			try {
				act_regis_date = java.sql.Date.valueOf(req.getParameter("act_regis_date").trim());
			} catch (IllegalArgumentException e) {
				act_regis_date = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.put("act_regis_date", "請輸入日期!");
			}
			


			String rot_id = req.getParameter("rot_id");
			if (rot_id.trim().length() == 0 || rot_id == null) {

				errorMsgs.put("rot_id", "請選擇路線");
			}

			String emp_id = req.getParameter("emp_id");
			if (emp_id.trim().length() == 0 || emp_id == null || emp_id == "請選擇維護的員工") {

				errorMsgs.put("emp_id", "請選擇維護員工編號");
			}
			
			System.out.println("成功進入新增活動控制器10");


			Integer act_state = null;

			try {
				act_state = new Integer(req.getParameter("act_state"));

			} catch (NumberFormatException e) {
				errorMsgs.put("act_state", "你選擇活動張貼狀態");
			}

			Activity_VO activityVO = new Activity_VO();
//System.out.println("-------++++act_ID：" + activityVO.getAct_ID());

			activityVO.setCusName(cusName);
			activityVO.setCusMail(cusMail);
			activityVO.setAct_name(act_name);
			activityVO.setAct_info(act_info);
			activityVO.setAct_pic(activityPhotoB);
			activityVO.setAct_holder(act_holder);
			activityVO.setAct_href(act_href);
			activityVO.setAct_href2(act_href2);
			activityVO.setAct_sdate(act_sDate);
			activityVO.setAct_edate(act_eDate);
			activityVO.setAct_regis_date(act_regis_date);
			activityVO.setRot_id(rot_id);
			activityVO.setEmp_id(emp_id);
			activityVO.setAct_state(act_state);

			if (!errorMsgs.isEmpty()) {

				req.setAttribute("activityVO", activityVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/empAct/addActivity.jsp");
				failureView.forward(req, res);
				return;
			}

			System.out.println("-----");
			System.out.println("act_name: " + act_name);
			System.out.println("act_sDate: " + act_sDate);
			System.out.println("act_eDate: " + act_eDate);
			System.out.println("act_regis_date: " + act_regis_date);
			System.out.println("act_info: " + act_info);
			System.out.println("act_holder: " + act_holder);
			System.out.println("cusName: " + cusName);
			System.out.println("cusMail: " + cusMail);
			System.out.println("rot_id: " + rot_id);
			System.out.println("act_href: " + act_href);
			System.out.println("act_href2: " + act_href2);
			System.out.println("emp_id:  " + emp_id);
			System.out.println("act_state:  " + act_state);
			System.out.println("activityPhotoB:  " + activityPhotoB);
			Activity_Service actSvc = new Activity_Service();

			System.out.println("actSvcddd" + actSvc);

			activityVO = actSvc.addAct(act_name, act_sDate, act_eDate, act_regis_date, act_info, act_holder, cusName,
					cusMail, rot_id, act_href, act_href2, activityPhotoB, emp_id, act_state);
			System.out.println("成功!!!");
			req.setAttribute("activityVO", activityVO);
//			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/activity/activityList.jsp");
//			successView.forward(req, res);
			res.sendRedirect(req.getContextPath()+"/front-end/Forum/activity/activityList.jsp");

		}

		// 活動列表圖片
		if ("getListPic".equals(action)) {


			Integer act_ID = new Integer(req.getParameter("act_ID"));
			Activity_Service actSvc = new Activity_Service();

			ServletOutputStream out = res.getOutputStream();

//			System.out.println("act_ID====" + act_ID);

			List<Activity_VO> list = actSvc.getListPic(act_ID);
			Iterator<Activity_VO> it = list.iterator();
			Activity_VO activityVO = it.next();

			byte[] act_pic = activityVO.getAct_pic();


//			System.out.println("act_pic---" + act_pic);
			InputStream is = new ByteArrayInputStream(act_pic);
			BufferedInputStream bis = new BufferedInputStream(is);

			byte[] buff = new byte[bis.available()];
			is.read(buff);
			out.write(buff);

		}

		// 活動頁面圖片
		if ("getOnePic".equals(action)) {

			System.out.println("進入活動圖片貼文載入區");
			Integer act_ID = new Integer(req.getParameter("act_ID"));
//			System.out.println("--act_ID:" + act_ID);

			ServletOutputStream out = res.getOutputStream();
			Activity_Service actSvc = new Activity_Service();

			Activity_VO activityVO = actSvc.getPosPic(act_ID);
//			System.out.println("--activityVO:" + activityVO);

			byte[] act_pic = activityVO.getAct_pic();
//			System.out.println("進入活動圖片貼文載入區2");

			InputStream is = new ByteArrayInputStream(act_pic);
			BufferedInputStream bis = new BufferedInputStream(is);

//			System.out.println(":bis:" + bis);

			byte[] buff = new byte[bis.available()];
			bis.read(buff);
			out.write(buff);

		}

		if ("getOneAct_onPage".equals(action)) {

			Integer act_ID = new Integer(req.getParameter("act_ID"));
//			=============================================
			//接受舊觀看人數請求
			Integer act_click = new Integer(req.getParameter("act_click"));
//			================================================
			System.out.println("--act_ID--" + act_ID);
			System.out.println("---act_click-----" + act_click);
			Activity_VO activityVO = new Activity_VO();
			Activity_Service actSvc = new Activity_Service();
			
			
//			===========================================
//			新增點擊人數+1

			activityVO = actSvc.updateActView(act_ID);
			activityVO = actSvc.getOneAct(act_ID);

			
			System.out.println("---act_click22222222222222-----" + activityVO.getAct_click());

//			============================================
			req.setAttribute("activityVO", activityVO);
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/activity/activityPost.jsp");
			successView.forward(req, res);
			
			

		}

		// 修改活動資料(後台)

		if ("getOne_For_Update".equals(action)) {

		}

		
		
		
		
		//萬用查詢控制器
		
		if ("listActivity_byCompositeQuery".equals(action)) {
			System.out.println("進入萬用查詢控制器");
			HttpSession session = req.getSession();

			List<String> errorMsgs_2 = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs_2", errorMsgs_2);

			try {
				/***************************1.將輸入資料轉為Map**********************************/ 

				HttpSession session_2 = req.getSession();
				System.out.println("1");
				Map<String, String[]> map = (Map<String, String[]>) session_2.getAttribute("map");
				System.out.println("2");

				System.out.println("3");

				System.out.println("map:" + map);
				if (req.getParameter("whichPage") == null) {
					// 避免駭客入侵
					HashMap<String, String[]> map1 = new HashMap<String, String[]>(req.getParameterMap());
				
					session.setAttribute("map", map1);
					System.out.println("map1:" + map1);

					map = map1;

				}
				/***************************2.開始複合查詢***************************************/
				Activity_Service activiySvc = new Activity_Service();
				List<Activity_VO> list  = activiySvc.getAllAct(map);
				for(Activity_VO list2:list) {
					
					System.out.println("listttttttttttt" + list2);

					
				}
				
				/***************************3.準備轉交***************************************/
				req.setAttribute("listActivity_byCompositeQuery", list); //從資料取出list物件 存入request
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/activity/activityList.jsp");
				successView.forward(req, res);

				
				/***************************3.其他錯誤處理***************************************/

			} catch (Exception e) {
				errorMsgs_2.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Forum/activity/activityList.jsp");
				failureView.forward(req, res);
			}
			
		}
		
		
		

		if("getLatestAct".equals(action)) {
			System.out.println("進入最新活動");

			
			Activity_Service activitySvc = new Activity_Service();
			List<Activity_VO> list = activitySvc.getLatestAct();

			
			
			for(Activity_VO activity_VO:list) {
				
				System.out.println("最新活動列表" + activity_VO.getAct_name());

				
			}
			req.setAttribute("list", list);
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/activity/activity_order.jsp");
			System.out.println("進入最新活動1");

			successView.forward(req, res);
			
			
		}
		
		
		
		if("getPopAct".equals(action)) {
			System.out.println("進入熱門活動");
			
			Activity_Service activitySvc = new Activity_Service();
			List<Activity_VO> list = activitySvc.getPopAct();
			
			req.setAttribute("list", list);
			
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/activity/activity_order.jsp");
			successView.forward(req, res);
		}


	}

}
