package com.android.emp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.android.emp.model.EmpMemService;
import com.android.emp.model.EmpMemVO;
import com.android.team.model.TeamVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;



/**
 * Servlet implementation class MemServlet
 */

public class Android_EmpMemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		EmpMemService memberDao = new EmpMemService();
		List<EmpMemVO>  empMemVOVOlist	= memberDao.getAll();
		writeText(res, new Gson().toJson(empMemVOVOlist));

//		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		
		Gson gson = new Gson();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		System.out.println("input: " + jsonIn);	
	
		EmpMemService memberDao = new EmpMemService();
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
			
		String action = jsonObject.get("action").getAsString();
//----------------------------------------------------------------------------------
		System.out.println("2.action"+action);
		
		if ("isMember".equals(action)) {
			
			String userId = jsonObject.get("userId").getAsString();
			System.out.println("3.userId"+ userId );	

			String password = jsonObject.get("password").getAsString();
			System.out.println("4.password"+ password );	

			System.out.println("5." + memberDao.getOneEmp("userId") );
			
			EmpMemVO member = memberDao.getOneEmp("password");
			EmpMemVO memberuserId = memberDao.getOneEmp("userId");
			writeText(res, member == null ? "" : gson.toJson(userId));
			writeText(res, memberuserId == null ? "" : gson.toJson(password));

//			writeText(res,	String.valueOf(memberDao.getOneEmp("userId").getEmp_id()));
//			writeText(res,	String.valueOf(memberDao.getOneEmp("password").getEmp_password()));
			}
		
	}
	

	private void writeText(HttpServletResponse res, String outText) 
			throws IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);

	}
	
}

