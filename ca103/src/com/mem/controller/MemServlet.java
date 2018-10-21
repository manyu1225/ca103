package com.mem.controller;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import com.mem.model.MailService;
import com.mem.model.MemJDBCDAO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.secondShop.product.browserRec.BrowserRec;

@MultipartConfig
public class MemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html ;charset=utf-8");
		String sign_up = req.getParameter("sign_up");
		String login = req.getParameter("login");
		String action = req.getParameter("action");

		if ("login".equals(login)) {
			String mem_ac = req.getParameter("mem_ac");
			String mem_password = req.getParameter("mem_password");

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			MemService memSvc = new MemService();
			MemVO CheckResult = null;

			if (mem_ac == null || mem_ac.trim().length() == 0) {
				errorMsgs.add("帳號請勿空白");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/Login.jsp");
				failureView.forward(req, res);
				return;
			}

			try {
				CheckResult = memSvc.findByPrimarKey(mem_ac);
			} catch (Exception e) {
				errorMsgs.add("帳號不存在");
			}

			if (mem_password == null || mem_password.trim().length() == 0) {
				errorMsgs.add("密碼請勿空白");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/Login.jsp");
				failureView.forward(req, res);
				return;
			}

			if (CheckResult.getMem_password() != null && (!mem_password.equals(CheckResult.getMem_password()))) {
				errorMsgs.add("密碼輸入錯誤");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/Login.jsp");
				failureView.forward(req, res);
				return;
			}

//			if(!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/Login.jsp");
//				failureView.forward(req, res);
//				return;
//			}

			MemVO memVO = new MemVO();
			memVO = memSvc.findByPrimarKey(mem_ac);

			System.out.println("帳號" + memVO.getMem_ac());
			System.out.println(memVO.getMem_id());
//			req.setAttribute("memVO", memVO);

			// ---------------開始送值-------------------
			HttpSession session = req.getSession(true);
			session.setAttribute("memVO", memVO);
			String url = "/front-end/mem/loginOK.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}

		String mem_check = req.getParameter("mem_check");

		if ("mem_check".equals(mem_check)) {
			List<String> errorMsgs = new LinkedList<String>();
			String mem_ac = req.getParameter("mem_ac");
			if (mem_ac == null) {// 如果找不到東西的話
				errorMsgs.add("驗證出現錯誤");
				String url = "/front-end/mem/MailCheckfaile.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
			MemService memSvc = new MemService();
			MemVO memVO = memSvc.findByPrimarKey(mem_ac);
			memSvc.status_set(1, memVO.getMem_ac());
			//
			req.setAttribute("memVO", memVO);
			String url = "/front-end/mem/MailCheckOK.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		/***** 註冊會員 *****/
		if ("sign_up".equals(sign_up)) {
			List<String> errorMsgs = new LinkedList<String>();

			req.setAttribute("errorMsgs", errorMsgs);

			// 帳號設定驗證
			String mem_ac = req.getParameter("mem_ac");
			String mem_acReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,20}$";
			if (mem_ac == null || mem_ac.trim().length() == 0) {
				errorMsgs.add("帳號請勿空白");
			} else if (!mem_ac.trim().matches(mem_acReg)) {
				errorMsgs.add("帳號請輸入英數大小寫、數字");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/register.jsp");
				failureView.forward(req, res);
				return;
			}

			// 輸入密碼驗證
			String mem_password = req.getParameter("mem_password");
			String mem_passwordRes = "^[(a-zA-Z0-9)]{8,16}$";

			if (mem_password == null || mem_password.trim().length() == 0) {
				errorMsgs.add("密碼請勿空白");
			} else if (!mem_password.trim().matches(mem_passwordRes)) {
				errorMsgs.add("請輸入英數大小寫、數字，最低長度8、最大長度16");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/register.jsp");
				failureView.forward(req, res);
				return;
			}

			// 確認密碼驗證
			String pwConfirm = req.getParameter("pwConfirm");
			System.out.println(pwConfirm);
			if (pwConfirm == null || pwConfirm.trim().length() == 0) {
				errorMsgs.add("請輸入確認密碼");
			} else if (!(pwConfirm).equals(mem_password)) {
				errorMsgs.add("請再次確認密碼是否輸入相同");
			}

			// 信箱驗證
			String mem_email = req.getParameter("mem_email");
			String mem_emailRes = "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$";
			if (mem_email == null || mem_email.trim().length() == 0) {
				errorMsgs.add("電子信箱請勿空白");
			} else if (!mem_email.trim().matches(mem_emailRes)) {
				errorMsgs.add("信箱輸入格式錯誤");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/register.jsp");
				failureView.forward(req, res);
				return;
			}

			// 姓驗證
			String mem_lastname = req.getParameter("mem_lastname");
			String mem_lastnameRes = "^[(\u4e00-\u9fa5)(a-zA-Z)]{1,10}$";
			if (mem_lastname == null || mem_lastname.trim().length() == 0) {
				errorMsgs.add("姓名請勿空白");
			} else if (!mem_lastname.trim().matches(mem_lastnameRes)) {
				errorMsgs.add("姓名請以中文字、英文輸入，字元最小1，最大100");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/register.jsp");
				failureView.forward(req, res);
				return;
			}
			// 名驗證
			String mem_firstname = req.getParameter("mem_firstname");
			String mem_firstnameRes = "^[(\u4e00-\u9fa5)(a-zA-Z)]{1,10}$";
			if (mem_firstname == null || mem_firstname.trim().length() == 0) {
				errorMsgs.add("名不能空白");
			} else if (!mem_firstname.trim().matches(mem_firstnameRes)) {
				errorMsgs.add("名請以中文字，英文輸入，字元最小1，最大100");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/register.jsp");
				failureView.forward(req, res);
				return;
			}

			// 匿名驗證
			String mem_nickname = req.getParameter("mem_nickname");
			String mem_nicknameRes = "^[(\u4e00-\u9fa5)(a-zA-Z)]{1,10}$";
			if (mem_nickname == null || mem_nickname.trim().length() == 0) {
				errorMsgs.add("暱稱請勿空白，這將是您網站的化名");
			} else if (!mem_nickname.trim().matches(mem_nicknameRes)) {
				errorMsgs.add("請以中文字，英文輸入，字元最小1，最大100");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/register.jsp");
				failureView.forward(req, res);
				return;
			}

			// 生日驗證
			java.sql.Date mem_birthday = null;
			try {
				mem_birthday = java.sql.Date.valueOf(req.getParameter("mem_birthday").trim());

			} catch (IllegalArgumentException e) {
				mem_birthday = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.add("請輸入日期");
			}

			// 手機驗證
			String mem_phone = req.getParameter("mem_phone");
			String mem_phoneRes = "^0[0-9]{9}$";
			if (mem_phone == null || mem_phone.trim().length() == 0) {
				errorMsgs.add("請輸入電話號碼");
			} else if (!mem_phone.trim().matches(mem_phoneRes)) {
				errorMsgs.add("請輸入台灣正確的手機號碼");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/register.jsp");
				failureView.forward(req, res);
				return;
			}
			// 市話驗證
			String mem_tel = req.getParameter("mem_tel");
			String mem_telRes = "^0[0-9]{12}$";
//				if(mem_tel == null || mem_phone.trim().length()== 0){
//					errorMsgs.add("請輸入電話號碼");
//				}else if(!mem_tel.trim().matches(mem_telRes)) {
//					errorMsgs.add("請輸入台灣正確的電話號碼，且用區碼開頭");
//				}if(!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/register.jsp");
//					failureView.forward(req, res);
//					return;
//				}

			Part cart_photo = req.getPart("mem_cart_photo");
			InputStream setMem_cart_photo = cart_photo.getInputStream();
			byte[] mem_cart_photo = new byte[setMem_cart_photo.available()];
			setMem_cart_photo.read(mem_cart_photo);
			setMem_cart_photo.close();

			Part photo = req.getPart("mem_photo");
			InputStream setMem_photo = photo.getInputStream();
			byte[] mem_photo = new byte[setMem_photo.available()];
			setMem_photo.read(mem_photo);
			setMem_photo.close();

			String mem_aboutme = req.getParameter("mem_aboutme");

			MemVO memVO = new MemVO();
			memVO.setMem_ac(mem_ac);
			memVO.setMem_password(mem_password);
			memVO.setMem_email(mem_email);
			memVO.setMem_lastname(mem_lastname);
			memVO.setMem_firstname(mem_firstname);
			memVO.setMem_nickname(mem_nickname);
			memVO.setMem_birthday(mem_birthday);
			memVO.setMem_phone(mem_phone);
			memVO.setMem_tel(mem_tel);
			memVO.setMem_cart_photo(mem_cart_photo);
			memVO.setMem_photo(mem_photo);

			// --------------開始轉交---------------------
			try {
				MemService memSvc = new MemService();
				memSvc.addMem(mem_ac, mem_password, mem_lastname, mem_firstname, mem_birthday, mem_phone, mem_tel,
						mem_photo, mem_cart_photo, mem_email, mem_aboutme, mem_nickname);
				memSvc.findByPrimarKey(mem_ac);
				MailService ms = new MailService();

				// ---------信箱驗證-----------
				String path = req.getContextPath();
				String messageText = "親愛的" + memVO.getMem_lastname() + memVO.getMem_firstname()
						+ "您好，請透過以下連結網址通過信箱認證：http://" + req.getServerName() + ":" + req.getServerPort() + path + "/front-end/mem/mem.do?action=goCheckMail&mem_ac="
						+ memVO.getMem_ac();
				ms.sendMail(memVO.getMem_email(), "自轉車-會員註冊認證信", messageText);

			} catch (Exception e) {
				e.printStackTrace();
			}
			String url = "/front-end/mem/registerOK.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		
		if("goCheckMail".equals(action)) {
			String mem_ac = req.getParameter("mem_ac");
			MemService memSvc = new MemService();
			MemVO memVO = new MemVO();
			memSvc.status_set(1, mem_ac);
			memVO = memSvc.findByPrimarKey(mem_ac);
			req.setAttribute("memVO", memVO);
			
			
			String url = "/front-end/mem/MailCheckOK.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		}
		/* 員工瀏覽清單進修改前的動作 */
		if ("update_mem_master".equals(action)) {
			String mem_id = req.getParameter("mem_id");
			System.out.println("有無抓到進到修改前的值?   " + mem_id);
			MemService memSvc = new MemService();
			MemVO memVO = new MemVO();

			memVO = memSvc.findMemById(mem_id);
			req.setAttribute("memVO", memVO);
			String url = "/back-end/mem/memMasterUpdate.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		/***** 接收會員數據，進行修改 ******/
		if ("update_one_mem_byMaster".equals(action)) {
			String mem_lastname = req.getParameter("mem_lastname");
			String mem_firstname = req.getParameter("mem_firstname");
			String mem_ac = req.getParameter("mem_ac");
			String mem_email = req.getParameter("mem_email");
			String mem_phone = req.getParameter("mem_phone");
			String mem_nickname = req.getParameter("mem_nickname");
			java.sql.Date mem_birthday = java.sql.Date.valueOf(req.getParameter("mem_birthday"));
			String get_rot_badtimes = req.getParameter("mem_rot_badtimes");
			String get_act_badtimes = req.getParameter("mem_act_badtimes");
			String get_gru_badtimes = req.getParameter("mem_gru_badtimes");
			String get_post_badtimes = req.getParameter("mem_post_badtimes");
			String get_sale_badtimes = req.getParameter("mem_sale_badtimes");

			MemVO memVO = new MemVO();

			memVO.setMem_lastname(mem_lastname);
			memVO.setMem_firstname(mem_firstname);
			memVO.setMem_ac(mem_ac);
			memVO.setMem_email(mem_email);
			memVO.setMem_phone(mem_phone);
			memVO.setMem_nickname(mem_nickname);
			memVO.setMem_birthday(mem_birthday);
			memVO.setMem_id(req.getParameter("mem_id"));
			System.out.println(req.getParameter("mem_id"));

			if ("啟用".equals(get_rot_badtimes)) {
				memVO.setMem_rot_badtimes(0);
				System.out.println("權限已啟用");
			} else {
				memVO.setMem_rot_badtimes(50);
				System.out.println("權限已停用");
			}

			if ("啟用".equals(get_act_badtimes)) {
				memVO.setMem_act_badtimes(0);
			} else {
				memVO.setMem_act_badtimes(50);
			}

			if ("啟用".equals(get_gru_badtimes)) {
				memVO.setMem_gru_badtimes(0);
			} else {
				memVO.setMem_gru_badtimes(50);
			}

			if ("啟用".equals(get_post_badtimes)) {
				memVO.setMem_post_badtimes(0);
			} else {
				memVO.setMem_post_badtimes(50);
			}

			if ("啟用".equals(get_sale_badtimes)) {
				memVO.setMem_sale_badtimes(0);
			} else {
				memVO.setMem_sale_badtimes(50);
			}

			MemService memSvc = new MemService();
			memSvc.updateByMaster(memVO);
			req.setAttribute("memVO", memVO);

			try {
				String url = "/back-end/per/OK.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				String url = "/back-end/per/faile.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}

		}
		// 接受會員修改參數
		if ("updateProfile".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String mem_id = req.getParameter("mem_id");
			String mem_lastname = req.getParameter("mem_lastname");
			String mem_lastnameRes = "^[(\u4e00-\u9fa5)(a-zA-Z)]{1,10}$";
			MemVO memVO = new MemVO();

			if (mem_lastname == null || mem_lastname.trim().length() == 0) {
				errorMsgs.put("mem_lastname", "姓氏請勿空白");
			} else if (!mem_lastname.trim().matches(mem_lastnameRes)) {
				errorMsgs.put("mem_lastname", "姓名請以中文字、英文輸入，字元最小1，最大100");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/MemberMain.jsp");
				failureView.forward(req, res);
				return;
			}

			String mem_firstname = req.getParameter("mem_firstname");
			String mem_firstnameRes = "^[(\u4e00-\u9fa5)(a-zA-Z)]{1,10}$";
			if (mem_firstname == null || mem_firstname.trim().length() == 0) {
				errorMsgs.put("mem_firstname", "名不能空白");
			} else if (!mem_firstname.trim().matches(mem_firstnameRes)) {
				errorMsgs.put("mem_firstname", "名請以中文字，英文輸入，字元最小1，最大100");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/MemberMain.jsp");
				failureView.forward(req, res);
				return;
			}

			String mem_nickname = req.getParameter("mem_nickname");
			String mem_nicknameRes = "^[(\u4e00-\u9fa5)(a-zA-Z)]{1,10}$";
			if (mem_nickname == null || mem_nickname.trim().length() == 0) {
				errorMsgs.put("mem_nickname", "暱稱請勿空白，這將是您網站的化名");
			} else if (!mem_nickname.trim().matches(mem_nicknameRes)) {
				errorMsgs.put("mem_nickname", "請以中文字，英文輸入，字元最小1，最大100");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/MemberMain.jsp");
				failureView.forward(req, res);
				return;
			}

			String mem_email = req.getParameter("mem_email");
			String mem_emailRes = "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$";
			if (mem_email == null || mem_email.trim().length() == 0) {
				errorMsgs.put("mem_email", "電子信箱請勿空白");
			} else if (!mem_email.trim().matches(mem_emailRes)) {
				errorMsgs.put("mem_email", "信箱輸入格式誤");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/MemberMain.jsp");
				failureView.forward(req, res);
				return;
			}
			String mem_phone = req.getParameter("mem_phone");
			String mem_phoneRes = "^0[0-9]{9}$";
			if (mem_phone == null || mem_phone.trim().length() == 0) {
				errorMsgs.put("mem_phone", "請輸入電話號碼");
			} else if (!mem_phone.trim().matches(mem_phoneRes)) {
				errorMsgs.put("mem_phone", "請輸入台灣正確的手機號碼");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/MemberMain.jsp");
				failureView.forward(req, res);
				return;
			}

			String mem_tel = req.getParameter("mem_tel");
			// String mem_birthday = req.getParameter("mem_birthday");
			java.sql.Date mem_birthday = null;
			try {
				mem_birthday = java.sql.Date.valueOf(req.getParameter("mem_birthday").trim());

			} catch (IllegalArgumentException e) {
				mem_birthday = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.put("mem_birthday", "請輸入日期");
			}

			byte[] mem_photo = null;
			try {
				ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
				Part photo = req.getPart("mem_photo");
				InputStream setMem_photo = photo.getInputStream();
//				mem_photo = new byte[setMem_photo.available()];
//				setMem_photo.read(mem_photo);
//				setMem_photo.close();
				mem_photo = new byte[setMem_photo.available()];// buff用于存放循环读取的临时数据
				int rc = setMem_photo.read(mem_photo);
				swapStream.write(mem_photo, 0, rc);
				mem_photo = swapStream.toByteArray();
			} catch (Exception e) {
				MemService memSvc = new MemService();
				mem_photo = memSvc.findMemById(mem_id).getMem_photo();
			}

			byte[] mem_cart_photo = null;
			try {
				ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
				Part cart_photo = req.getPart("mem_cart_photo");
				InputStream setMem_cart_photo = cart_photo.getInputStream();
//				mem_cart_photo = new byte[setMem_cart_photo.available()];
//				setMem_cart_photo.read(mem_cart_photo);
//				setMem_cart_photo.close();
				mem_cart_photo = new byte[setMem_cart_photo.available()];// buff用于存放循环读取的临时数据
				int rc = setMem_cart_photo.read(mem_cart_photo);
				swapStream.write(mem_cart_photo, 0, rc);
				mem_cart_photo = swapStream.toByteArray(); // in_b为转换之后的结果
			} catch (Exception e) {
				MemService memSvc = new MemService();
				mem_cart_photo = memSvc.findMemById(mem_id).getMem_cart_photo();
			}

			String mem_aboutme = req.getParameter("mem_aboutme");

			/**** 修改開始轉交 ****/
			memVO.setMem_lastname(mem_lastname);
			memVO.setMem_firstname(mem_firstname);
			memVO.setMem_tel(mem_tel);
			memVO.setMem_phone(mem_phone);
			memVO.setMem_email(mem_email);
			memVO.setMem_nickname(mem_nickname);
			memVO.setMem_birthday(mem_birthday);
			memVO.setMem_aboutme(mem_aboutme);
			memVO.setMem_photo(mem_photo);
			memVO.setMem_cart_photo(mem_cart_photo);
			memVO.setMem_id(mem_id);
			MemService memSvc = new MemService();
			memSvc.update(memVO);

			memVO = memSvc.findMemById(mem_id);

			HttpSession session = req.getSession(true);
			session.setAttribute("memVO", memVO);
			String url = "/front-end/mem/MemberMain.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		if ("goToMemberView".equals(action)) {

			String mem_id = req.getParameter("mem_id");
			System.out.println("查看會員的ID: " + mem_id);
			MemService memSvc = new MemService();
			MemVO memVO = new MemVO();

			memVO = memSvc.findMemById(mem_id);

			req.setAttribute("mem_id", memVO);

			String url = "/front-end/mem/MemberView.jsp";
			RequestDispatcher failureView = req.getRequestDispatcher(url);
			failureView.forward(req, res);
		}
		if("logout".equals(action)) {
			String mem_id = req.getParameter("mem_id");
			System.out.println("登出ID: "+mem_id);
			HttpSession session = req.getSession(true);
			session.invalidate();
			
//			BrowserRec br = new BrowserRec();
//			br.delBrowserRec(mem_id);
//			
			String url = "/HomePage.jsp";
			RequestDispatcher failureView = req.getRequestDispatcher(url);
			failureView.forward(req, res);
			
		}
	}

}
