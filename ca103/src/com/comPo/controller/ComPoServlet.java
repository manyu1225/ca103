package com.comPo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.com.model.*;
import com.mem.model.*;
import com.comFile.model.*;
import com.comPo.model.*;

@WebServlet("/ComPoServlet")
@MultipartConfig
public class ComPoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ComPoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");

		// 	新增社群貼文
		if ("insertComPost".equals(action)) {

			/*************************** 1.接收請求參數 *************************/
			HttpSession session = req.getSession();
			String mem_id = ((MemVO) session.getAttribute("memVO")).getMem_id();
			String com_id = req.getParameter("com_id");
			String cp_content = req.getParameter("cp_content");
			String sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			java.sql.Timestamp cp_time = java.sql.Timestamp.valueOf(sdate);

			String cuf_id = null;
			String comPo_id = null;

			String sdate_f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			java.sql.Timestamp cuf_time = java.sql.Timestamp.valueOf(sdate);
			String album = null;

			ComPoVO comPoVO = null;
			ComFileVO comFileVO = null;


			List<String> cuf_list = new ArrayList<String>();
			try {
				// 	圖片改成Base64上傳資料庫
				Collection<Part> parts = req.getParts();
				Iterator<Part> ItePart = parts.iterator();
				while (ItePart.hasNext()) {
					Part part = (Part) ItePart.next();
					if (part.getContentType() != null) {
						InputStream ItePart_in = part.getInputStream();
						byte[] buffer = new byte[ItePart_in.available()];
						ItePart_in.read(buffer);
						ItePart_in.close();
						Base64.Encoder encoder = Base64.getEncoder();
						cuf_list.add(encoder.encodeToString(buffer));

					}
				}
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			/*************************** 2.開始新增資料 ***************************************/
			//	新增貼文(沒有內容)，並同時綁定自增主鍵值，如此才能接到comPo_id的值，用來新增圖片
			ComPoService comPoSvc = new ComPoService();
			ComFileService comFileSvc = new ComFileService();
			comPoVO = comPoSvc.addComPo(com_id, mem_id, cp_content, cp_time);
			
			for (String cuf : cuf_list) {
				// 	判斷cp_content與cuf的資料是否有進來
				if ((cp_content != null) && (cuf.equals(""))) {

					System.out.println("f1");	//	有內容
				} else if ((cuf != null) && ("".equals(cp_content))) {
					// 	有準備上傳圖片時，呼叫此方法改變狀態(是否有上傳圖片)
					comPoSvc.cpHaveFile(comPoVO.getComPo_id());
					// 	新增圖片
					comFileVO = comFileSvc.addComFile(com_id, mem_id, comPoVO.getComPo_id(), cuf, cuf_time, album);

					System.out.println("f2");	//	有圖片
				} else if ((cuf != null) && (cp_content != null)) {
					// 	有準備上傳圖片時，呼叫此方法改變狀態(是否有上傳圖片)
					comPoSvc.cpHaveFile(comPoVO.getComPo_id());
					// 	新增圖片
					comFileVO = comFileSvc.addComFile(com_id, mem_id, comPoVO.getComPo_id(), cuf, cuf_time, album);

					System.out.println("f3");	//	有內容與圖片
				}
			}


			//	更新貼文數紀錄
			ComService comSvc = new ComService();
			comSvc.updatePostCount(com_id);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//			session.setAttribute("cuf_list", cuf_list);	//大概是用不到了

			String url = "/com/ComServlet.do?action=getComContent&com_id=";
			res.sendRedirect(req.getContextPath() + url + com_id);	
			
			
			// 	因為sendRedirect可以跨專案，所以要記得加專案路徑給它

//			String url = "/com/ComServlet.do?action=getComContent&com_id=";
//			RequestDispatcher successView = req.getRequestDispatcher(url + com_id);
//			successView.forward(req, res);
//			因為按F5會有重複發送request的問題，所以改用sendRedirect
		}
	}

}
