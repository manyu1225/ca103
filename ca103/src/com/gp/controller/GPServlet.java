package com.gp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mem.model.MemVO;
import com.route.model.RouteVO;
import com.gp.model.GPService;
import com.gp.model.GPVO;
import com.joined_gp.model.JoinedGPService;
import com.joined_gp.model.JoinedGPVO;
@MultipartConfig
public class GPServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
//		Enumeration arr = req.getParameterNames();
//		while(arr.hasMoreElements()) {
//			System.out.println(arr.nextElement());
//		}
		
		
		if ("GPs_FIND".equals(action)) {
			
			StringBuilder searchStr = new StringBuilder();
			
//			關鍵字查詢
			if(!"".equals(req.getParameter("search"))) {
				String[] arrKw = req.getParameter("search").trim().split(" ");
				for(int i=0;i<arrKw.length;i++) {
					searchStr.append(" AND (RegExp_like(GP.GP_TITLE,'" + arrKw[i] + "') OR RegExp_like(GP.GP_DESC,'" + arrKw[i] + "') OR RegExp_like(GP.GP_CONTENT_EDIT,'" + arrKw[i] + "')) ");
				}
			}
			

			searchStr.append(" AND ROT_HARD" + req.getParameter("hard"));
			searchStr.append(" AND ROT_DIS" + req.getParameter("dis"));
			searchStr.append(" AND ROT_SLOPE_AVE" + req.getParameter("slope"));
			searchStr.append(" AND (ROT_LOC_START like '%" + req.getParameter("rot_loc") + "%'" + 
						" OR ROT_LOC_END like '%" + req.getParameter("rot_loc") + "%')");
			
			if(!"0".equals(req.getParameter("limitNum"))) {
				searchStr.append(" AND MAX_NUM<=" + req.getParameter("limitNum") + " AND MAX_NUM>=" + (Integer.valueOf(req.getParameter("limitNum"))-10));
			}
			
			if(!"".equals(req.getParameter("dateStart")) && !"".equals(req.getParameter("dateEnd"))) {
//				searchStr.append(" AND GP_DATE>=" + Date.valueOf(req.getParameter("dateStart")) + " AND GP_DATE<=" + Date.valueOf(req.getParameter("dateEnd")));
				searchStr.append(" AND GP_DATE>=TO_DATE('" + Date.valueOf(req.getParameter("dateStart")) + "', 'yyyy-mm-dd')");
				searchStr.append(" AND GP_DATE<=TO_DATE('" + Date.valueOf(req.getParameter("dateEnd")) + "', 'yyyy-mm-dd')");
			}
			String order =req.getParameter("order");
			if("0".equals(order)) {
				searchStr.append(" order by GP.CRE_TIME desc");
			}else if("1".equals(order)){
				searchStr.append(" order by GP.CRE_TIME");
			}else if("2".equals(order)) {
				searchStr.append(" order by ROT_HARD");
			}else if("3".equals(order)) {
				searchStr.append(" order by ROT_HARD desc");
			}else if("4".equals(order)) {
				searchStr.append(" order by ROT_DIS");
			}else if("5".equals(order)) {
				searchStr.append(" order by ROT_DIS desc");
			}else if("6".equals(order)) {
				searchStr.append(" order by ROT_SLOPE_AVE");
			}else if("7".equals(order)) {
				searchStr.append(" order by ROT_SLOPE_AVE desc");
			}else if("8".equals(order)) {
				searchStr.append(" order by GP_DATE");
			}else if("9".equals(order)) {
				searchStr.append(" order by GP_DATE desc");
			}
			
			GPService gpSrc = new GPService();
			List<GPVO> gpList = gpSrc.searchByCoondition(searchStr.toString());
			req.setAttribute("gpList",gpList);
			req.getSession().setAttribute("gpList",gpList);
			
//			保存搜尋
			req.setAttribute("hard", req.getParameter("hard"));
			req.setAttribute("dis", req.getParameter("dis"));
			req.setAttribute("slope", req.getParameter("slope"));
			req.setAttribute("rot_loc", req.getParameter("rot_loc"));
			req.setAttribute("search", req.getParameter("search"));
			req.setAttribute("limitNum", req.getParameter("limitNum"));
			req.setAttribute("dateStart", req.getParameter("dateStart"));
			req.setAttribute("dateEnd", req.getParameter("dateEnd"));
			req.setAttribute("order", req.getParameter("order"));
			
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/gp/GP_Browsing.jsp?hasSearched=true");
			successView.forward(req, res);
		}
		
		
		if ("GP_CREATING".equals(action)) {
			
			
			Set<Integer> errorIndex = new HashSet<Integer>();
			
			StringBuilder exceptionMessage = new StringBuilder();
			req.setAttribute("errorIndex", errorIndex);
			req.setAttribute("exceptionMessage", exceptionMessage);
			try {
				
				
				String rot_id = req.getParameter("ROUTE");
				if(rot_id == null || rot_id.trim().length() == 0) {
					errorIndex.add(1);
				}
				
				
				String gp_title = req.getParameter("GP_TITLE");
				if(gp_title == null || gp_title.trim().length() == 0) {//或是字數量太大
//	System.out.println("我抓到錯誤了1");
					errorIndex.add(2);
				}
				
				Date gp_date = null;
				try {
					gp_date = Date.valueOf(req.getParameter("GP_DATE"));
				}catch (IllegalArgumentException e) {
					gp_date = new Date(System.currentTimeMillis() + (long)24*3600*1000);
					errorIndex.add(3);
					System.out.println("我來到這邊");
				}
				
				int gp_time;
				try {
					gp_time = Integer.valueOf(req.getParameter("GP_TIME"));
				}catch (NumberFormatException e) {
					gp_time = 9;
					errorIndex.add(3);
				}
				
				Date sign_up_dd = null;
				try {
					sign_up_dd = Date.valueOf(req.getParameter("SIGN_UP_DD"));
				}catch (IllegalArgumentException e) {
					sign_up_dd = new Date(System.currentTimeMillis() + (long)24*3600*1000);
					errorIndex.add(4);
				}
				
				int min_num;
				try {
					min_num = Integer.valueOf(req.getParameter("MIN_NUM"));
				}catch (NumberFormatException e) {
					min_num = 0;
					errorIndex.add(4);
				}
				
				int max_num;
				try {
					max_num = Integer.valueOf(req.getParameter("MAX_NUM"));
				}catch (NumberFormatException e) {
					max_num = 20;
					errorIndex.add(4);
				}System.out.println("我到ㄉ了這邊嗎");
				
				String gp_desc = req.getParameter("GP_DESC");
				if(gp_desc == null || gp_desc.trim().length() == 0) {//或是字數量太大
					errorIndex.add(5);
					System.out.println("我進來了");
				}
				
				GPVO gpVO = null;
				
				gpVO = new GPVO(null,((MemVO) req.getSession().getAttribute("memVO")).getMem_id(), rot_id, null, new Timestamp(System.currentTimeMillis()), gp_title,gp_date
					, Integer.valueOf(req.getParameter("GP_HOUR")), gp_time, gp_desc
					, req.getParameter("GP_CONTENT_EDIT"),req.getParameter("GP_CONTENT"),req.getParameter("GP_CONTENT_PHOTO"), getPictureByteArray(req.getPart("GP_PHOTO").getInputStream()), Integer.valueOf(req.getParameter("PUB_SET"))
					, min_num, max_num, sign_up_dd, 0, "/ff");
				
//	System.out.println("撐的到這嗎");
				if(!errorIndex.isEmpty()) {
//	System.out.println(errorIndex + "我抓到自己抓的錯誤了");
					
//					路線名另外處理
					
					req.setAttribute("routeName", req.getParameter("routeName"));
					req.setAttribute("gpVOcre", gpVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp/GP_Creating.jsp");
					failureView.forward(req, res);
					return;
				}
				
				GPService gpSrc = new GPService();
//	System.out.println("error時我不會出現");
				gpSrc.addGP(gpVO);
				//成功時跳轉至???管理的揪團
				res.sendRedirect(req.getContextPath() + "/front-end/gp/GP_Browsing.jsp");
				
			}catch (Exception e) {
				errorIndex.add(0);
				//內部抓到的錯誤也會經過這裡
				exceptionMessage.append(e.getMessage());//這個字串裝Exception
//	System.out.println("exceptionMessage：" + exceptionMessage);
				//失敗時回去
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp/GP_Creating.jsp");
				failureView.forward(req, res);
				return;
			}
			
			
		}
		
		if("GP_SEARCH".equals(action)) {
			String gp_id = req.getParameter("gp_id");
			GPService gpSrc = new GPService();
			GPVO gpVO = new GPVO();
			gpVO.setGp_id(gp_id);
			gpVO = gpSrc.searchGPVO(gpVO);
			req.setAttribute("gpVO",gpVO);
			req.setAttribute("gpVOupdate", gpVO);
			if(req.getParameter("isEmp")!=null && "true".equals(req.getParameter("isEmp"))) {
				req.setAttribute("isEmp", true);
			}else {
				req.setAttribute("isEmp", false);
			}
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/gp/GP_Viewing.jsp"); // 將查詢結果轉交至Viewing揪團畫面
			successView.forward(req, res);
		}
		
		if("GP_UPDATE".equals(action)) {
			String gp_id = req.getParameter("gp_id");
			
			Set<Integer> errorIndex = new HashSet<Integer>();
			StringBuilder exceptionMessage = new StringBuilder();
			req.setAttribute("errorIndex", errorIndex);
			req.setAttribute("exceptionMessage", exceptionMessage);
			
			try {
				String gp_title = req.getParameter("GP_TITLE");
				if(gp_title == null || gp_title.trim().length() == 0) {
					errorIndex.add(2);
				}
				
				int gp_time;
				try {
					gp_time = Integer.valueOf(req.getParameter("GP_TIME"));
				}catch (NumberFormatException e) {
					gp_time = 9;
					errorIndex.add(3);
				}
				
				Date sign_up_dd = null;
				try {
					sign_up_dd = Date.valueOf(req.getParameter("SIGN_UP_DD"));
				}catch (IllegalArgumentException e) {
					sign_up_dd = new Date(System.currentTimeMillis());
					errorIndex.add(4);
				}
				
				int min_num;
				try {
					min_num = Integer.valueOf(req.getParameter("MIN_NUM"));
				}catch (NumberFormatException e) {
					min_num = 0;
					errorIndex.add(4);
				}
				
				int max_num;
				try {
					max_num = Integer.valueOf(req.getParameter("MAX_NUM"));
				}catch (NumberFormatException e) {
					max_num = 20;
					errorIndex.add(4);
				}
				
				String gp_desc = req.getParameter("GP_DESC");
				if(gp_desc == null || gp_desc.trim().length() == 0) {//或是字數量太大
					errorIndex.add(5);
				}
				byte[] gp_photo = getPictureByteArray(req.getPart("GP_PHOTO").getInputStream());
				
				
				GPVO gpVO = new GPVO();
				gpVO.setGp_id(gp_id);
				gpVO.setGp_title(gp_title);
				gpVO.setGp_hour(Integer.valueOf(req.getParameter("GP_HOUR")));
				gpVO.setGp_time(gp_time);
				gpVO.setSign_up_DD(sign_up_dd);
				gpVO.setMin_num(min_num);
				gpVO.setMax_num(max_num);
				gpVO.setGp_desc(gp_desc);
				gpVO.setGp_photo(gp_photo);
				gpVO.setPub_set(Integer.valueOf(req.getParameter("PUB_SET")));
				
				if(!errorIndex.isEmpty()) {
//System.out.println(errorIndex + "我抓到自己抓的錯誤了");
					
					GPService gpSrc = new GPService();
					GPVO gpVO2 = new GPVO();
					gpVO2.setGp_id(gp_id);
					gpVO2 = gpSrc.searchGPVO(gpVO);
					req.setAttribute("gpVO",gpVO2);
					
					
					req.setAttribute("gpVOupdate", gpVO); // 含有輸入格式錯誤的empVO物件,也存入req
					req.setAttribute("showLB", "true");
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/gp/GP_Viewing.jsp");//還要讓LightBox顯現
					failureView.forward(req, res);
					return;
				}
				
				//更新成功
				GPService gpSrc = new GPService();
//				如果沒圖片就用之前的圖片
				byte[] prPhoto = gpSrc.searchGPVO(gpVO).getGp_photo();
				if(gp_photo == null) {
					gpVO.setGp_photo(prPhoto);
				}
				
				gpSrc.updateGPInfo(gpVO);
				gpVO = gpSrc.searchGPVO(gpVO);
				res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" + gpVO.getGp_id() + "&action=GP_SEARCH");
				
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
		//主辦者取消揪團
		if("GP_CANCEL".equals(action)) {
			GPVO gpVO = new GPVO();
			gpVO.setGp_id((String)req.getParameter("gp_id"));
			gpVO.setGp_status(3);
			GPService gpSrc = new GPService();
			gpSrc.updateStatus(gpVO);
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/gp/GP_List.jsp?listPage=3");
			successView.forward(req, res);
		}
		
		if("GP_VIEWING_CANCEL".equals(action)) {
			GPVO gpVO = new GPVO();
			gpVO.setGp_id((String)req.getParameter("gp_id"));
			gpVO.setGp_status(3);
			GPService gpSrc = new GPService();
			gpSrc.updateStatus(gpVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" +  req.getParameter("gp_id") + "&action=GP_SEARCH");
		}
		
		//主辦者刪除紀錄
		if("GP_DELETE".equals(action)) {
			GPVO gpVO = new GPVO();
			gpVO.setGp_id((String)req.getParameter("gp_id"));
			gpVO.setGp_status(4);
			GPService gpSrc = new GPService();
			gpSrc.updateStatus(gpVO);
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/gp/GP_List.jsp?listPage=3");
			successView.forward(req, res);
		}
		
		
		
		
		if("GP_CONTENT_UPDATE".equals(action)) {
			GPVO gpVO = new GPVO();
			gpVO.setGp_content_edit(req.getParameter("GP_CONTENT_EDIT"));
			gpVO.setGp_content(req.getParameter("GP_CONTENT"));
			gpVO.setGp_content_photo(req.getParameter("GP_CONTENT_PHOTO"));
			gpVO.setGp_id(req.getParameter("gp_id"));
			GPService gpSrc = new GPService();
			gpSrc.updateContent(gpVO);
			res.sendRedirect(req.getContextPath() + "/gp.do?gp_id=" +  req.getParameter("gp_id") + "&action=GP_SEARCH#tabpage");
		}
		
		if("GP_SEAL".equals(action)) {
			GPVO gpVO = new GPVO();
			gpVO.setGp_id(req.getParameter("gp_id"));
			gpVO.setGp_status(10);
			GPService gpSrc = new GPService();
			gpSrc.updateStatus(gpVO);
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/gp/GP_Manager.jsp?currentPage=" + req.getParameter("currentPage") + "&tab=" + req.getParameter("tab"));
			successView.forward(req, res);
		}
		
		
	}
	//轉圖片InputStream To Byte[]
	public byte[] getPictureByteArray(InputStream in) {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
		byte[] in_b = null;
		try {
			byte[] buff = new byte[in.available()];//buff用于存放循环读取的临时数据
			int rc = in.read(buff);
			swapStream.write(buff, 0, rc);
			in_b = swapStream.toByteArray(); //in_b为转换之后的结果
		} catch (Exception e) {
//			e.printStackTrace();
			in_b = null;
		}
		return in_b;
	}
	
	//自製List差集
	public List<GPVO> retainAll(List<GPVO> list1,List<GPVO> list2) {
		List<GPVO> listRetain = new ArrayList<>();
		for(GPVO gpVO1 : list1) {
			for(GPVO gpVO2 : list2) {
				if(gpVO1.getGp_id().equals(gpVO2.getGp_id())) {
					listRetain.add(gpVO1);
				}
			}
		}
		return listRetain;
		
	}
	
	
}
