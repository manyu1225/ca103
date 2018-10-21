package com.comRes.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
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

import com.comRes.model.ComResService;
import com.comRes.model.ComResVO;
import com.mem.model.MemVO;
import com.reply_of_msg.model.Reply_of_MSGService;
import com.reply_of_msg.model.Reply_of_MSGVO;

@WebServlet("/ComResServlet")
@MultipartConfig
public class ComResServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ComResServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();
		
		//	 新增社群回文
		if ("insertComRes".equals(action)) {
			
			/*************************** 1.接收請求參數 *************************/
			String com_id = req.getParameter("com_id");
			String comPo_id = req.getParameter("comPo_id");
			String mem_id = ((MemVO) session.getAttribute("memVO")).getMem_id();
			String cr_content = req.getParameter("cr_content");
			
			String cr_file = null;
			try {
				// 	圖片改成Base64上傳資料庫
				Part cr_file_part = req.getPart("cr_file");
				if (cr_file_part != null) {
					InputStream cr_file_part_in = cr_file_part.getInputStream();
					byte[] buffer = new byte[cr_file_part_in.available()];
					cr_file_part_in.read(buffer);
					cr_file_part_in.close();
					Base64.Encoder encoder = Base64.getEncoder();
					cr_file = encoder.encodeToString(buffer);
				}
				
			} catch (Exception e) {
				e.printStackTrace(System.err);
			}
			
			String sdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			java.sql.Timestamp cr_time = java.sql.Timestamp.valueOf(sdate);
			
			ComResVO comResVO = null;
			
			/*************************** 2.開始新增資料 ***************************************/
			ComResService comResSvc = new ComResService();
			comResVO = comResSvc.addComRes(comPo_id, mem_id, cr_content, cr_file, cr_time);
			
			
			//	判斷cr_content與cr_file的資料是否有進來
			if ((cr_content != null) && (cr_file.equals(""))) {
	
				System.out.println("f1");	//	有內容
			} else if ((cr_file != null) && ("".equals(cr_content))) {
				// 	有準備上傳圖片時，呼叫此方法改變狀態(是否有上傳圖片)
				comResSvc.crHaveFile(comResVO.getComRes_id());
	
				System.out.println("f2");	//	有圖片
			} else if ((cr_file != null) && (cr_content != null)) {
				// 	有準備上傳圖片時，呼叫此方法改變狀態(是否有上傳圖片)
				comResSvc.crHaveFile(comResVO.getComRes_id());
	
				System.out.println("f3");	//	有內容與圖片
			}
			
			
			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			
			
			String url = "/com/ComServlet.do?action=getComContent&com_id=";
			res.sendRedirect(req.getContextPath() + url + com_id);	
			

		
		}


	
	}

}
