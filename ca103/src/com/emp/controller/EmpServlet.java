package com.emp.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.secondShop.product.browserRec.BrowserRec;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMessages;



public class EmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html ;charset=utf-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			errorMsgs.put("ok", "新增完成");
			try {
				String emp_lastname = req.getParameter("emp_lastname");
				String emp_lastnameRes = "^[(\\u4e00-\\u9fa5)(a-zA-Z)]{1,10}$";
//				if (emp_lastname == null || emp_lastname.trim().length() == 0) {
//					errorMsgs.put("lastname", "姓不可空白");
//				} else if (!emp_lastname.trim().matches(emp_lastnameRes)) {
//					errorMsgs.put("lastname", "姓名請以中文字、英文輸入，字元最小1，最大100");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/EmpAdd.jsp");
//					failureView.forward(req, res);
//					return;
//				}

				String emp_firstname = req.getParameter("emp_firstname");
				String emp_firstnameRes = "^[(\\u4e00-\\u9fa5)(a-zA-Z)]{1,10}$";
//				if (emp_firstname == null || emp_firstname.trim().length() == 0) {
//					errorMsgs.put("firstname", "名不可空白");
//				} else if (!emp_firstname.trim().matches(emp_firstnameRes)) {
//					errorMsgs.put("firstname", "名請以中文字，英文輸入，字元最小1，最大100");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/EmpAdd.jsp");
//					failureView.forward(req, res);
//					return;
//				}

				String emp_account = req.getParameter("emp_account");
				String emp_accountRes = "^[a-zA-Z0-9]{8,20}$";
//				if (emp_account == null || emp_account.trim().length() == 0) {
//					errorMsgs.put("account", "帳號不可以空白");
//				} else if (!emp_account.trim().matches(emp_accountRes)) {
//					errorMsgs.put("account", "帳號請輸入大小寫英文、數字，最低長度8");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/EmpAdd.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				String emp_password = req.getParameter("emp_password");
				String emp_passwordRes = "^[(a-zA-Z0-9)]{8,16}$";
//				if (emp_password == null || emp_password.trim().length() == 0) {
//					errorMsgs.put("password", "密碼不可以空白");
//				} else if (!emp_password.trim().matches(emp_passwordRes)) {
//					errorMsgs.put("password", "密碼請輸入大小寫英文、數字，最低長度8");
//				}
//				if (!errorMsgs.isEmpty()) {
//					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/EmpAdd.jsp");
//					failureView.forward(req, res);
//					return;
//				}
				String emp_pr = req.getParameter("emp_pr");

				String emp_ad = req.getParameter("emp_ad");
				// 地址驗證做清單好了
				String emp_email = req.getParameter("emp_email");
				String emp_emailRes = "^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$";
//				if (emp_email == null || emp_email.trim().length() == 0) {
//					errorMsgs.put("email", "電子信箱不可為空白");
//				} else if (!emp_email.trim().matches(emp_emailRes)) {
//					errorMsgs.put("email", "信箱輸入格式錯誤");
//				}
				// ---------驗證完畢------

				EmpVO empVO = new EmpVO();
				empVO.setEmp_account(emp_account);
				empVO.setEmp_password(emp_password);
				empVO.setEmp_firstname(emp_firstname);
				empVO.setEmp_lastname(emp_lastname);
				empVO.setEmp_pr(emp_pr);
				empVO.setEmp_ad(emp_ad);
				empVO.setEmp_email(emp_email);

				// --------開始接值-------
				try {
					EmpService empSvc = new EmpService();
					empSvc.addEmp(emp_account, emp_password, emp_firstname, emp_lastname, emp_pr, emp_ad, emp_email);

				} catch (Exception e) {
					e.printStackTrace();
				}
				String url = "/back-end/emp/EmpList.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("Exception", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/EmpAdd.jsp");
				failureView.forward(req, res);
			}
		}
		if ("login".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			String emp_account = req.getParameter("emp_account");
			System.out.println("請求的帳號:"+emp_account);
			String emp_password = req.getParameter("emp_password");
			System.out.println("請求的密碼 "+emp_password);
			EmpVO CheckResult = null;
			EmpService empSvc = new EmpService();
			EmpVO empVO = new EmpVO();

			empVO = empSvc.getOneEmp(emp_account);
//			System.out.println(empVO==null);
			System.out.println("抓的帳號:"+empVO.getEmp_account());
			
			
			if( (empVO.getEmp_account()) == null ) {
				errorMsgs.put("account", "帳號錯誤");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/EmpLogin.jsp");
				failureView.forward(req, res);
				return;
			}
			
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/EmpLogin.jsp");
//				failureView.forward(req, res);
//				return;
//			}
				
			else if (emp_account == null || emp_account.trim().length() == 0) {
				errorMsgs.put("account", "請輸入帳號");
			}
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/EmpLogin.jsp");
				failureView.forward(req, res);
				return;
			}
			
			
			if (emp_password == null || emp_password.trim().length() == 0) {
				errorMsgs.put("password", "請輸入密碼");
			}
			else if(empVO.getEmp_password()==null || (!empVO.getEmp_password().equals(emp_password))) {
				errorMsgs.put("password", "密碼錯誤");
			}
			
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/EmpLogin.jsp");
				failureView.forward(req, res);
				return;
			}
			
			HttpSession session = req.getSession(true);
			session.setAttribute("empVO", empVO);
			String url = "/BackHomePage.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}
		
		if("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);
			errorMsgs.add("修改完成");
			try {
				String emp_id = req.getParameter("emp_id");
				String emp_lastname = req.getParameter("emp_lastname");
				String emp_firstname = req.getParameter("emp_firstname");
				String emp_account = req.getParameter("emp_account");
				String emp_password = req.getParameter("emp_password");
				String emp_email = req.getParameter("emp_email");
				String emp_ad = req.getParameter("emp_ad");
				String emp_pr =req.getParameter("emp_pr");
				System.out.println("個人修改"+emp_id);
				System.out.println("個人修改"+emp_lastname);
				System.out.println("個人修改"+emp_firstname);
				System.out.println("個人修改"+emp_account);
				System.out.println("個人修改"+emp_password);
				System.out.println("個人修改"+emp_email);
				System.out.println("個人修改"+emp_ad);
				System.out.println("個人修改"+emp_pr);
				
				
				//-----------這邊做驗證--------------
				//-----------這邊做驗證--------------
				
				
				EmpVO empVO = new EmpVO();
				empVO.setEmp_id(emp_id);
				
				empVO.setEmp_lastname(emp_lastname);
				empVO.setEmp_firstname(emp_firstname);
				empVO.setEmp_account(emp_account);
				empVO.setEmp_password(emp_password);
				empVO.setEmp_email(emp_email);
				empVO.setEmp_ad(emp_ad);
				empVO.setEmp_pr(emp_pr);
				EmpService empSvc = new EmpService();
				
				empSvc.updateEmp(empVO);
				req.setAttribute("empVO",empVO);
				String url = "/BackHomePage.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
						
				
			}catch(Exception e) {
				String url = "/BackHomePage.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
			
		}
		/*權限使用者的登入確認*/
		
		if("percheck".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			HttpSession session = req.getSession(true);
			EmpVO empVO = new EmpVO();
			empVO = (EmpVO) session.getAttribute("empVO");
			System.out.println("驗證使用者："+empVO.getEmp_account());
			System.out.println("驗證權限："+empVO.getEmp_pr());
			
			
			if("超級管理員".equals(empVO.getEmp_pr())) {
				String url = "/back-end/EmpList.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}else {
				errorMsgs.put("errorPer","您無權限進入此頁面");
				System.out.println("發現錯誤身分，重導頁面");
				String url="/back-end/EmpIndex.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				return;
			}
			
		}
		
		
		/*超級使用者的更新*/
		if("update_one_emp_a".equals(action)) {
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String emp_id = req.getParameter("emp_id");
				String emp_lastname = req.getParameter("emp_lastname");
				String emp_firstname = req.getParameter("emp_firstname");
				String emp_email = req.getParameter("emp_email");
				String emp_pr = req.getParameter("emp_pr");
				String emp_ad = req.getParameter("emp_ad");
				
				EmpVO empVO = new EmpVO();
				EmpService empSvc = new EmpService();
				empVO = empSvc.updateEmpMaster(emp_firstname, emp_lastname, emp_ad, emp_email, emp_pr, emp_id);
				req.setAttribute("MasterUpdate", empVO);
				empVO = empSvc.findOneById(emp_id);
				HttpSession session = req.getSession();
				
				if(emp_id == empVO.getEmp_id()) {
					session.setAttribute("empVO", empVO);					
				}
				String url = "/back-end/emp/EmpList.jsp?action=empList";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		if("logout".equals(action)) {
			String emp_id = req.getParameter("emp_id");
			System.out.println("登出ID: "+emp_id);
			HttpSession session = req.getSession(true);
			session.getAttribute("empVO");
			session.removeAttribute("empVO");//移除session
			
			String url = "/BackHomePage.jsp";
			RequestDispatcher failureView = req.getRequestDispatcher(url);
			failureView.forward(req, res);
		}
		
		if("go_to_update_one_emp".equals(action)) {
			String emp_id = req.getParameter("emp_id");
			System.out.println("Test1");
			EmpVO empVO = new EmpVO();
			EmpService empSvc = new EmpService();
			empVO = empSvc.findOneById(emp_id);
			req.setAttribute("MasterUpdate", empVO);
			System.out.println("Test2");
			
			String url = "/back-end/per/EmpMasterUpdate.jsp";
			RequestDispatcher failureView = req.getRequestDispatcher(url);
			failureView.forward(req, res);
			
		}
		if("delete_emp".equals(action)) {
			String emp_id = req.getParameter("emp_id");
			EmpVO empVO = new EmpVO();
			EmpService empSvc = new EmpService();
			empSvc.deleteEmp(emp_id);
			//未完成
			
			
		}
		

	}

};