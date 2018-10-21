package com.forPos.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
import com.forPos.model.Forum_post_Service;
import com.forPos.model.Forum_post_VO;
import com.forPos_pic.model.ForPost_pic_Service;
import com.forPos_pic.model.ForPost_picture_VO;
import com.forPos_res.model.Forum_response_Service;
import com.forPos_res.model.Forum_response_VO;

import hashTag_Util.HashTag_Service;
import redis.clients.jedis.Jedis;


@MultipartConfig
public class ForPos_Servlet extends HttpServlet {

	////////////////////////////此控制器包含「文章」及「分類標籤」的新刪修/////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////另，分類標籤的service另存在套件hashTag.Util///////////////////////////////////////
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		HttpSession session = req.getSession();

		String action = req.getParameter("action");
		String actionTag = req.getParameter("actionTag");

		
//		************************修改文章*********************************

		if ("getOne_For_Update".equals(action)) {
			System.out.println("34"+action);
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			try {
//		************************************1. 接收求參數****************************************************************
				Integer forPos_ID = new Integer(req.getParameter("forPos_ID"));
//				String meme_ID  = req.getParameter("meme_ID");

				// ***********************************2.
				// 開始查詢資料****************************************************************
				Forum_post_Service forPos_Svc = new Forum_post_Service();
				Forum_post_VO forum_post_VO = forPos_Svc.getOneForPos(forPos_ID);

				// ***********************************2. 查詢完成，準備轉交 send to the success
				// view****************************************************************

				req.setAttribute("forPosVO", forum_post_VO); // 取出資料庫物件 並存入req
				String url = "/front-end/Forum/forPos/update_forPost.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

//***********************************3. 其他可能的錯誤處理****************************************************************

			} catch (Exception e) {
				throw new ServletException(e);
			}

		}

		if ("update".equals(action)) {
			System.out.println("我來更新囉");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

//		try {

//			---------------------1. 接收請求參數，錯誤驗證------------------------------
			Integer forPost_ID = new Integer(req.getParameter("forPost_ID").trim());
			System.out.println("====" + forPost_ID);

			// 選擇文章狀態
			Integer forPos_state = new Integer(req.getParameter("state"));
			if (forPos_state == 0 || forPos_state.equals(0)) {
				errorMsgs.add("請選擇文章狀態");
			}

			String forPost_theme = req.getParameter("theme");
//		String forPsot_theme_reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)(\\w)]{1,30}$";

			if (forPost_theme == null || forPost_theme.trim().length() == 0 || forPost_theme.trim().equals("請輸入標題")) {
				errorMsgs.add("標題不能為空白字元");

			}

			String forPost_content = req.getParameter("content");
//			String forPsot_content_reg = "^[\\w]$";

			if (forPost_content == null || forPost_content.trim().length() == 0
					|| forPost_content.trim().equals("在自轉車留下您的足跡吧......")) {
				errorMsgs.add("寫點什麼吧!");
			}

//			文章類別
			String forClass_ID = req.getParameter("class_ID");

			String mem_id = req.getParameter("mem_id");

//			Timestamp time = new Timestamp(new java.util.Date().getTime());

			Integer view = new Integer(req.getParameter("view"));

//			System.out.println("time:" + time);



			Forum_post_VO forPosVO = new Forum_post_VO();
			forPosVO.setForPost_ID(forPost_ID);
			forPosVO.setMem_ID(mem_id);
			forPosVO.setForPost_view(view);
			forPosVO.setForPost_theme(forPost_theme);
			forPosVO.setForPost_content(forPost_content);
			forPosVO.setForClass_ID(forClass_ID);
			forPosVO.setForPost_state(forPos_state);
//		System.out.println("133/"+forPos_state);
//		System.out.println("133/"+forPost_ID);
//		***********錯誤轉交*********
			if (!errorMsgs.isEmpty()) {
				System.out.println("錯誤轉交");
				req.setAttribute("forPosVO", forPosVO);
				// 請求送出，包含錯誤訊息
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Forum/forPos/update_forPost.jsp");
				failureView.forward(req, res);
				return;
			}

//			***********************2.開始新增欲修改的資料******************************************************
			Forum_post_Service forPosSvc = new Forum_post_Service();
			forPosVO = forPosSvc.updateForPos(forPost_ID, forClass_ID, forPost_content, forPost_theme,
					forPos_state);

			System.out.println("forPosVO" + forPosVO);
			/******************** 3.修改完成,準備轉交(Send the Success view) ***********/
			req.setAttribute("forPosVO", forPosVO); // 將經過驗證的的資訊存入req
			String url = "/front-end/Forum/forPos/forPostManage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllForPos.jsp
			successView.forward(req, res);

			/*************************** 其他可能的錯誤處理 **********************************/
//		} catch (Exception e) {
//			errorMsgs.add("修改資料失敗:" + e.getMessage());
//			RequestDispatcher failureView = req.getRequestDispatcher("/forPos/forPostManage.jsp");
//			failureView.forward(req, res);
//		}

		}

		if ("insert".equals(action)) {                 
			
			System.out.println("成功進入新增頁面");
			
			
			

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {

//				---------------------1.錯誤驗證------------------------------
//			Timestamp time = new Timestamp(new java.util.Date().getTime());
				Integer state = new Integer(req.getParameter("state"));
				String forClass_ID = req.getParameter("class_ID");
				String forPost_theme = req.getParameter("theme");
//				String forPsot_theme_reg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{1,30}$";
				String mem_id = req.getParameter("mem_id");


				//封面圖片上傳處理
				
				Part forPostPhoto = req.getPart("forPostPhoto");
				
				InputStream in = forPostPhoto.getInputStream();
				byte[] forPostPhotoB = new byte[in.available()];
				in.read(forPostPhotoB);
				
				ForPost_picture_VO ForPost_picVO = new ForPost_picture_VO();
				ForPost_picVO.setForPostPic(forPostPhotoB);
				if (forPost_theme == null || forPost_theme.trim().length() == 0
						|| forPost_theme.trim().equals("請輸入標題")) {
					errorMsgs.add("標題不能為空白字元");

				} 

//			選擇文章狀態
				if (state == 0 || state.equals(0)) {
					errorMsgs.add("請選擇文章狀態");
				}

				String forPost_content = req.getParameter("content");
//				String forPsot_content_reg = "^[\\w]$";

				if (forPost_content == null || forPost_content.trim().length() == 0) {
					errorMsgs.add("寫點什麼吧!");
				}

//			
				System.out.println("成功進入錯誤驗證程序");

//				

//				Timestamp forPost_time = new Timestamp(req.getParameter("time"));

				Forum_post_VO forPosVO = new Forum_post_VO();
				forPosVO.setForPost_theme(forPost_theme);
				forPosVO.setForPost_content(forPost_content);
				forPosVO.setForClass_ID(forClass_ID);
				forPosVO.setForPost_state(state);
				forPosVO.setMem_ID(mem_id);
//			***********錯誤轉交*********
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("forPosVO", forPosVO);
					// 請求送出，包含錯誤訊息
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Forum/forPos/insert_forPost.jsp");

					failureView.forward(req, res);
//					res.sendRedirect("/forPos/insert_forPost.jsp");
					return;
				}

//				***********************2.開始新增資料******************************************************

				Forum_post_Service forPosSvc = new Forum_post_Service();
				forPosSvc.insertAndphoto(forPosVO, ForPost_picVO);
				req.setAttribute("forPosVO", forPosVO); // 將經過驗證的的資訊存入req
		
				/******************** 3.新增完成,準備轉交(Send the Success view) ***********/
				System.out.println("文章送出");

				String url = req.getContextPath()+"/front-end/Forum/forPos/forPostManage.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交forPostManage.jsp
//				successView.forward(req, res);
				res.sendRedirect(url);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("新增資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Forum/forPos/forPostManage.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer forPos_ID = new Integer(req.getParameter("forPos_ID"));
//				String url = req.getParameter("requestURL");


				/*************************** 2.開始刪除資料 ***************************************/
				Forum_post_Service forPosSvc = new Forum_post_Service();
				forPosSvc.deleteForPos(forPos_ID);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//					String url = "/forPos/forPostManage.jsp";
				Forum_post_VO forPosVO = new Forum_post_VO();
				req.setAttribute("forPosVO", forPosVO);
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/forPos/forPostManage.jsp");// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				System.out.println("禁入刪除controller");


				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Forum/forPos/forPostManage.jsp");
				failureView.forward(req, res);
			}
//				

		}
		
		
		
		if("getOnePos_onPage".equals(action)) {
			List<String> errorMsgs = new ArrayList<String>();
			
			try {
				
				
				System.out.println("進若獲取資料頁面");
//			***********************1. 接受請求參數********************//要確定都有接到
				Integer forPost_ID = new Integer(req.getParameter("forPost_ID"));
				String mem_id = req.getParameter("mem_id");
				System.out.println(forPost_ID);
				System.out.println(mem_id);
				System.out.println("接收文章載入請求成功");
				System.out.println("查詢文章forPos_ID=" + forPost_ID);

//				***********************2. 開始顯示整頁資料********************	
				
				Forum_post_Service forPosSvc = new Forum_post_Service();
				Forum_post_VO forum_post_VO = new Forum_post_VO();

				String view = req.getParameter("forPost_view");
				
				if(view=="") {
					res.sendRedirect(req.getContextPath()+"/front-end/Forum/errorPage/pageNotFound.jsp");
				}
				
				
				Integer forPost_view =  new Integer(view);
				
				
				System.out.println("觀看人數前" + forPost_view );
System.out.println("1");
//				點擊後觀看人數，取出該文章原來瀏覽人次並+1
				forum_post_VO = forPosSvc.updateViewNum(forPost_ID);

				System.out.println("觀看人數增加後並送進資料庫" + forPost_view );

				//觀看人數+1後轉交回原文章
				forum_post_VO =  forPosSvc.getOneForPos(forPost_ID);
				
//System.out.println("查看最新文章controller");
				req.setAttribute("forPosVO", forum_post_VO);
				
				
		/*************************** 3.準備轉交(Send the Success view) ***********/
				
				
//		****************************3-1文章狀態 1:公開 2:不公開 3:因檢舉)被下架***************************
				Integer forPost_state = new Integer(req.getParameter("forPost_state"));
				
//		********************************3-2 檢舉申請成功  該網頁貼文被下架(state==3) 轉交自訂頁面***************************************
				
				 if(forPost_state == 3) {
					 System.out.println("2");
					RequestDispatcher closeView = req.getRequestDispatcher("/front-end/Forum/errorPage/pageNotFound.jsp");
					closeView.forward(req, res);
					
				}else {
					//state==1 state==2 交給前頁(文章列表)做顯示判斷
					System.out.println("3");
					RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/forPos/myPost.jsp");
					successView.forward(req, res); 
					
				}
				
				
			}catch(Exception e) {
//				errorMsgs.add("文章載入失敗" + e.getMessage());
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Forum/forPos/myPost.jsp");
//				failureView.forward(req, res);
				e.printStackTrace();
			}
			
		}
		
		
		
//		****************************Jedis新增文章分類標籤*****************************************************************************

		
		if("addhashTag".equals(action)) {
			
//			Map<String, String> tagDisplay = new LinkedHashMap<String, String>();
			List<String> tagDisplay = new LinkedList<String>();
			Long tagNum = null;


			System.out.println("分類標籤近來囉");

//			HashTag_Service hashSvc = new HashTag_Service();
			Forum_post_VO forPosVO  = new Forum_post_VO();
			Forum_response_VO forResVO = new Forum_response_VO();
			
			Jedis jedis = new Jedis("localhost", 6379);
			jedis.auth("123456");
			
			
			
			//取得標籤請求
			String tag = req.getParameter("tag");
			
			//取得該標籤文章編號參數
			String forPost_ID$ = req.getParameter("forPost_ID");
			Integer forPost_ID = new Integer(req.getParameter("forPost_ID"));

			//取得留言編號
//			Integer forRes_ID = new Integer(req.getParameter("forRes_ID"));

			//新增標籤
			jedis.sadd("post:" + forPost_ID +  ":" + "tag" , tag);
			jedis.sadd("tag:" + tag + ":" + "post", forPost_ID$);
			
			//取得貼文名稱(key)：標籤名稱 物件
			for (String tagStr : jedis.smembers("post:" + forPost_ID +  ":" + "tag")) { 
				tagDisplay.add(tagStr.toString());
			}
			
			//取得標籤名稱(key)：貼文名稱 物件(為求擁有該標籤貼文的數量)
			tagNum = jedis.scard("tag:" + tag +  ":" + "post");
			System.out.println("tagNummmmmmmm=" + tagNum);
			
			
			//存入標籤名稱
			req.setAttribute("tagDisplay", tagDisplay);
			
			
			//存入該種類標籤數量
			req.setAttribute("tagNum", tagNum);

			
			
			
			//取得前頁文章物件
			Forum_post_Service forPosSvc = new Forum_post_Service();
			
			forPosVO = forPosSvc.getOneForPos(forPost_ID);
			//取得前頁留言物件
			Forum_response_Service forResSvc = new Forum_response_Service();
			List<Forum_response_VO> list = forResSvc.getResByPost_ID(forPost_ID);
			req.setAttribute("list", list);
			
			
			System.out.println("+++準備轉交標籤");
			//傳送文章物件
//			forPosVO.setForPost_ID(forPost_ID);
			req.setAttribute("forPosVO", forPosVO);
			
			//傳送留言物件
			req.setAttribute("forResVO", forResVO);
			
			
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/forPos/myPost.jsp");
			successView.forward(req, res);
//			res.sendRedirect(req.getContextPath()+"/front-eend/forPos/myPost.jsp");
			
//			tagDisplay.put();
			
//			jedis.sadd("post:3:tags", "Java", "Servlet", "JSP");
//			jedis.sadd("tag:Java:posts", "2", "1", "3");         //tag為Java 的文章編號
//			jedis.sadd("tag:Servlet:posts", "3", "2");
//			jedis.sadd("tag:JSP:posts", "3");
			
		}
		
		
		
//		****************************Jedis取得特定文章分類標籤*****************************************************************************

		
//		if("getHashTag".equals(actionTag)) {
//			
//			
//			//取得該標籤文章編號參數
//			String forPost_ID$ = req.getParameter("forPost_ID");
//			Integer forPost_ID = new Integer(req.getParameter("forPost_ID"));
//			
//			HashTag_Service hashTagSvc  = new HashTag_Service();
//			List<String> tagList = hashTagSvc.getPostTag(forPost_ID);
//			
//			
//			
//			Forum_post_VO forPosVO  = new Forum_post_VO();
////			Forum_response_VO forResVO = new Forum_response_VO();
//			
//			
//		
//			
//			//取得前頁文章物件
//			Forum_post_Service forPosSvc = new Forum_post_Service();
//			forPosVO = forPosSvc.getOneForPos(forPost_ID);
//			//取得前頁留言物件
//			Forum_response_Service forResSvc = new Forum_response_Service();
//			List<Forum_response_VO> list = forResSvc.getResByPost_ID(forPost_ID);
//			
//			
//			req.setAttribute("forPosVO", forPosVO);//文章
//			req.setAttribute("list", list);//留言
//			req.setAttribute("tagList", tagList); //關鍵
//			
//			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/forPos/myPost.jsp");
//			successView.forward(req, res);
//
//			
//			
//			
//		}
		
		
		
		if(action.equals("getOnePic")) {
//			Integer forPoat_ID = new Integer(req.getParameter("forPoat_ID"));
			ForPost_pic_Service forPicSvc = new ForPost_pic_Service();
			System.out.println("圖圖圖");

			ServletOutputStream out = res.getOutputStream();
			Integer forPost_ID = new Integer(req.getParameter("forPost_ID"));
//			System.out.println("act_ID====" + act_ID);

			ForPost_picture_VO forPost_picture_VO = forPicSvc.findByPrimaryKey(forPost_ID);
//			Iterator<ForPost_picture_VO> it = forPost_picture_VO.iterator();
//			ForPost_picture_VO forPost_picture_VO = it.next();

			byte[] act_pic = forPost_picture_VO.getForPostPic();


//			System.out.println("act_pic---" + act_pic);
			InputStream is = new ByteArrayInputStream(act_pic);
			BufferedInputStream bis = new BufferedInputStream(is);

			byte[] buff = new byte[bis.available()];
			is.read(buff);
			out.write(buff);
			
			
			
		}
		

	

	}

}
