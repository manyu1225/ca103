package com.com.controller;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.com.model.*;
import com.comFile.model.ComFileService;
import com.comJoin.model.JoinedComService;
import com.comJoin.model.JoinedComVO;
import com.comPo.model.ComPoService;
import com.comPo.model.ComPoVO;
import com.comRes.model.ComResService;
import com.comRes.model.ComResVO;
import com.mem.model.MemVO;
import com.route.model.RouteService;

@WebServlet("/ComServlet")
@MultipartConfig
public class ComServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ComServlet() {
		super();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		if ("getFile".equals(req.getParameter("action"))) {
			String cuf_id = req.getParameter("cuf_id");
			
			ComFileService comFileSvc = new ComFileService();
			comFileSvc.getCom_File(cuf_id, res);
			
		} else if("getResFile".equals(req.getParameter("action"))){
			String comRes_id = req.getParameter("comRes_id");
			
			ComResService comResSvc = new ComResService();
			comResSvc.getCom_Res_File(comRes_id, res);
			
		} else {
			doPost(req, res);
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
		
		if ("insertCom".equals(action)) { // 來自addCom.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				MemVO memVO = (MemVO)session.getAttribute("memVO");
				String mem_id = memVO.getMem_id();
				//宣告session物件，並強轉型成字串
				//從中取得來自"/com/addCom.jsp"的"mem_id"
				
				String com_name = req.getParameter("com_name");	
				String com_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (com_name == null || com_name.trim().length() == 0) {
					errorMsgs.add("社群名稱: 請勿空白");
				} else if (!com_name.trim().matches(com_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("社群名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
				}

				byte[] cover_image = null;
				Integer privacy = new Integer(req.getParameter("privacy"));				
				String announcement = null;
				String introduction = null;
//				String sdate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
//				Date create_time = Date.valueOf(sdate);		//	由datebase抓取系統時間
				Date create_time = null;
				Integer com_status = null;
				Integer post_count = null;
				Integer mem_count = null;
				
				ComVO comVO = new ComVO();
				comVO.setMem_id(mem_id);
				comVO.setCom_name(com_name);
				comVO.setCover_image(cover_image);
				comVO.setPrivacy(privacy);
				comVO.setAnnouncement(announcement);
				comVO.setIntroduction(introduction);
				comVO.setCreate_time(create_time);
				comVO.setCom_status(com_status);
				comVO.setCom_status(post_count);
				comVO.setMem_count(mem_count);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("comVO", comVO); // 含有輸入格式錯誤的comVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/com/ComPage.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				ComService comSvc = new ComService();
				comVO = comSvc.addCom(mem_id, com_name, cover_image, privacy, announcement, introduction,
						create_time, com_status, mem_count);
				
				Integer pm_setting = 2, available = 0;	//	版主預設為2
				JoinedComService joinedComSvc = new JoinedComService();
				joinedComSvc.joinCom(comVO.getCom_id(), mem_id, pm_setting, available);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("comVO", comVO);
				
				String url = "/com/ComServlet.do?action=getComContent&com_id=";
				RequestDispatcher successView = req.getRequestDispatcher(url+comVO.getCom_id()); // 新增成功後轉交ComPage.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/com/ComPage.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getComList".equals(action)) { // 來自comHomePage.jsp的請求
			/*************************** 開始查詢資料 ****************************************/
				
			//	接收參數,建立空的sql,用以串接前端送進來的查詢字串
			//	接收參數,建利空的sqlColse,用以串接後端送進來的查詢封存路線字串
			StringBuilder sql = new StringBuilder();
			StringBuilder sqlColse = new StringBuilder();
					
			//	接收參數,取得排序條件
			String order = req.getParameter("order");
			
			//	模糊比對的SQL字串串接,串接完後送至DAO取得社群清單
			if(req.getParameter("text") != null) {
				if(!"".equals(req.getParameter("text").trim())) {
					sql.append(" WHERE (COM_NAME like '%" + req.getParameter("text") + 
					"%' OR ANNOUNCEMENT like '%" + req.getParameter("text") + "%')");
				}
			}
			
			//	排行條件：全部路線 (其實是按 orderBy com_id asc 來排序)
			if("com_id".equals(order)) {
				sql.append(" order by com_id asc");
			}
			
			//	排行條件：社群人數
			if("mem_count".equals(order)) {
				sql.append(" order by mem_count desc nulls last");
			}
			
			//	排行條件：今日貼文數
			if("post_count".equals(order)) {
				sql.append(" order by post_count desc nulls last");
			}
			
			//	排行條件：今日貼文數
			if("create_time".equals(order)) {
				sql.append(" order by com_id desc");
			}
					
			//	開始查詢
			ComService comSvc = new ComService();
			List<ComVO> comList;
			comList = comSvc.getComList(sql.toString());

			
			

			//*************************** 查詢完成,準備轉交(Send the Success view) *************/
			//	開始轉交,唯一一個方式是把資訊都丟進session裡面的!!因為要配合分頁的取值,所以用session省去資料要在頁面之間傳遞的麻煩
			session.setAttribute("comList", comList);		
			//	下面這個屬性是為了保存模糊搜尋時所送進來的「條件」，再把這些些條件送回 ListAllCom.jsp
			session.setAttribute("text", req.getParameter("text"));
			//	下面這個屬性是為了保存排序選項所送進來的「條件」，再把這些些條件送回 ListAllCom.jsp
			session.setAttribute("com_order", req.getParameter("order"));
			session.setAttribute("oldSql", sql.toString());
			
		
			// Send the Success view
//			String url = "/front-end/com/ListAllCom.jsp";	//	舊的路徑，現在都先查詢comList再查詢joinedComList，之後再轉交回ComPage.jsp
			
			if(((MemVO)session.getAttribute("memVO")) == null) {
				String url = "/front-end/com/ListAllCom.jsp";
				res.sendRedirect(req.getContextPath() + url);
			}else {
				String url = "/com/JoinedComServlet.do?action=getJoinedComList";
				res.sendRedirect(req.getContextPath() + url);
			}
			
		}

		if ("getComContent".equals(action)) { // 來自ListAllCom.jsp的請求

			/*************************** 1.接收請求參數 *****************************************/
			String com_id = req.getParameter("com_id");

			
			/*************************** 2.開始查詢資料 *****************************************/
			//	取得社群的基本資料
			ComService comSvc = new ComService();
			ComVO comVO = comSvc.getComContent(com_id);
	
			//	取得該社群的貼文詳細資料
			ComPoService comPoSvc = new ComPoService();
			List<ComPoVO> comPoList = comPoSvc.getByComId(com_id);
			
			//	取得該社群的所有貼文編號
			//	先將comPoList中的comPoVO取出，再把comPoVO中的comPo_id取出來，依順序加到comPo_idList中	
			List<String> comPo_idList = new ArrayList<String>();
			for(int i = 0; i < comPoList.size(); i++) {
				String comPo_id = ((ComPoVO)comPoList.get(i)).getComPo_id();
				comPo_idList.add(comPo_id);
			}
			
			//	取得該社群的所有圖片編號(同時依照貼文編號包在一起)
			//	先將comPo_idList中的comPo_id取出，通過getCuf_id方法取得該次comPo_id中所有的圖片編號(是一個list)，再依順序加到cuf_idList中
			List<List<String>> cuf_idsList = new ArrayList<List<String>>();
			ComFileService comFileSvc = new ComFileService();
			for(int i = 0; i < comPo_idList.size(); i++) {
				cuf_idsList.add(comFileSvc.getCuf_ids(comPo_idList.get(i)));
			}
			
			List<List<ComResVO>> comResVOsList = new ArrayList<List<ComResVO>>();
			ComResService comResSvc = new ComResService();
			for(int i = 0; i < comPo_idList.size(); i++) {
				comResVOsList.add(comResSvc.getComResVOs(comPo_idList.get(i)));
			}
			
			
			
			//	取得該社群所有貼文的會員編號
			List<MemVO> poMemVOList = new ArrayList<MemVO>();
			JoinedComService joinedComSvc = new JoinedComService();
			for(int i = 0; i < comPoList.size(); i++) {
				poMemVOList.add(joinedComSvc.getMemVO(comPo_idList.get(i)));
			}

			//	取得社群最新的3張圖片，放置在右邊
			List<String> siedeFileList = new ArrayList<String>();
			siedeFileList = comFileSvc.getSideFile(com_id);
			
			//	取得社群的所有會員
			List<MemVO>  comMemberList = joinedComSvc.getComMemberList(com_id);
			List<String> comMem_idList = new ArrayList<String>();
			for(int i = 0; i < comMemberList.size();i++) {
				comMem_idList.add(comMemberList.get(i).getMem_id());
			}
			
			
			//	取得單一加入會員的狀態，用以檢視權限設定
			String mem_id = ((MemVO)session.getAttribute("memVO")).getMem_id();
			JoinedComVO joinedComVO = joinedComSvc.getOneForCheck(com_id, mem_id);
			
			//	取得等待版主批准加入的列表
			List<JoinedComVO> waitForCheckList;
			waitForCheckList = joinedComSvc.getWaitForCheckList(com_id);
			

			//	
			List<String> allFileList;
			allFileList = comFileSvc.getAllFile(com_id);
			
			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("comVO",comVO); // req或session都一樣，先用req
			req.setAttribute("com_id",com_id);
			req.setAttribute("comPoList",comPoList);
			req.setAttribute("post_count",comPoList.size());
			req.setAttribute("comPo_idList",comPo_idList);
			req.setAttribute("cuf_idsList",cuf_idsList);
			req.setAttribute("comResVOsList",comResVOsList);
			req.setAttribute("comMemberList", comMemberList);
			req.setAttribute("poMemVOList", poMemVOList);
			req.setAttribute("siedeFileList", siedeFileList);
			req.setAttribute("comMem_idList", comMem_idList);
			req.setAttribute("waitForCheckList", waitForCheckList);
			req.setAttribute("allFileList", allFileList);
			
			String url = "/front-end/com/ComPage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交ComPage.jsp
			successView.forward(req, res);

		}
		
		if ("updateCom".equals(action)) { // 來自update_com_input.jsp的請求
			
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				//	接收參數
				String mem_id = req.getParameter("mem_id");
				String com_id = req.getParameter("com_id");
				String com_name, announcement, introduction;
				
				//	社群名稱若有填寫,則使用填寫的字串,若無填寫則使用預設字串「未命名」
				if("".equals(req.getParameter("update_com_name").trim())) {
					com_name = "未命名";
				}else {
					com_name = req.getParameter("update_com_name").trim();
				}
				
				//	圖片上傳
				byte[] cover_image = null;
				if("update_cover_image".equals(req.getParameter("old_cover_image"))) {
					Part cover_image_part = req.getPart("update_cover_image");
					InputStream cover_image_part_in = cover_image_part.getInputStream();
					cover_image = new byte[cover_image_part_in.available()];
					cover_image_part_in.read(cover_image);
					cover_image_part_in.close();
				}else {
					cover_image = req.getParameter("cover_image").getBytes();
				}
				
				//	取得社群隱私設定
				Integer privacy = new Integer(req.getParameter("update_privacy"));
				
				//	社群公告若有填寫,則使用填寫的字串,若無填寫則使用預設字串「社群相關公告。」
				if("".equals(req.getParameter("update_announcement").trim())) {
					announcement = "社群相關公告。";
				}else {
					announcement = req.getParameter("update_announcement").trim();
				}
				
				//	社群簡介若有填寫,則使用填寫的字串,若無填寫則使用預設字串「告訴其他人這個社群的成立宗旨。」
				if("".equals(req.getParameter("update_introduction").trim())) {
					introduction = "告訴其他人這個社群的成立宗旨。";
				}else {
					introduction = req.getParameter("update_introduction").trim();
				}
				
				
				
					

				
				/***************************2.開始修改資料*****************************************/
				ComService comSvc = new ComService();
				comSvc.updateCom(com_id, mem_id, com_name, cover_image, privacy, announcement, introduction);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/				
				String url="/com/ComServlet.do?action=getComContent&com_id=";
				RequestDispatcher successView = req.getRequestDispatcher(url+com_id);   // 修改成功後,轉交回送出修改的來源網頁
				
				successView.forward(req, res);
				
		}

		if ("updateIntroduction".equals(action)) { // 來自update_com_input.jsp的請求
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String com_id = req.getParameter("com_id");
				String introduction = req.getParameter("updateIntroduction");;

				/***************************2.開始修改資料*****************************************/
				ComService comSvc = new ComService();
				comSvc.updateIntroduction(com_id,introduction);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/				
				String url="/com/ComServlet.do?action=getComContent&com_id=";
				RequestDispatcher successView = req.getRequestDispatcher(url+com_id);   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);

		}
		
		if ("updateAnnouncement".equals(action)) { // 來自update_com_input.jsp的請求
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String com_id = req.getParameter("com_id");
				String announcement = req.getParameter("updateAnnouncement");;
	
				/***************************2.開始修改資料*****************************************/
				ComService comSvc = new ComService();
				comSvc.updateAnnouncement(com_id,announcement);

				/***************************3.修改完成,準備轉交(Send the Success view)*************/				
				String url="/com/ComServlet.do?action=getComContent&com_id=";
				RequestDispatcher successView = req.getRequestDispatcher(url+com_id);   // 修改成功後,轉交回送出修改的來源網頁
				successView.forward(req, res);

		}
		
	}
}
