package com.emp_per.controller;


import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.emp.model.*;
import com.emp_per.model.*;
import com.permission.model.*;

@MultipartConfig
public class EmpPerServlet extends HttpServlet {

	public EmpPerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if("listOneEmpPre".equals(action)) {
			String employee_id = req.getParameter("emp_id");
			System.out.println(employee_id);
			
			PerDAOJDBC perDao = new PerDAOJDBC();
			List<PerVO> list = new ArrayList<PerVO>();
			list = perDao.findPerNameByEmp(employee_id);
			System.out.println("是否為空?"+list.isEmpty());
			req.setAttribute("emp_id", employee_id);
			req.setAttribute("empPer", list);
			String url = "/back-end/per/PermissionAdd.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
			
		}
		
		
	}

}
