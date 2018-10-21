package com.android.mem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.android.activity.model.Activity_VO;
import com.android.mem.model.MemService;
import com.android.mem.model.MemVO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Android_MemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
//1.---GSON解碼手機傳來的 資料成為jsonobject 在和 SERVELET 的String =  getparameter(action) 做比對	
		Gson gson = new Gson();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		BufferedReader br = req.getReader();
		while ((line = br.readLine()) != null) {
			jsonIn.append(line); //jsonIn.....jsonIn(line)......
		}
		br.close();
		System.out.println("andorid  input: " + jsonIn);	
		//using json's fromjson method to  jsonobject  (jsonin string become a  jsonobject) 
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		System.out.println("1.jsonObject="+jsonObject);
		//get action's value and getas a String  
		String action = jsonObject.get("action").getAsString();
		System.out.println("2.action"+action);
//2.satart with a check with android's sending----------------
	
		MemService memberDao = new MemService();
		if ("isMember".equals(action)) {
			
			System.out.println("\"isMember\".equals(action)");	

			String mem_ac = jsonObject.get("mem_ac").getAsString();
			System.out.println("3.mem_ac"+ mem_ac );	

			String password = jsonObject.get("password").getAsString();
			System.out.println("4.password"+ password );	

			System.out.println("5." + memberDao.findisMember("mem_ac","password") );
			//conncetion with DAO(sql) to ckeck return boolean
			writeText(res,	String.valueOf(memberDao.findisMember(mem_ac, password)));
	}else if("findByPrimarKey".equals(action)) {
		MemVO memVO = null;

		String mem_ac = jsonObject.get("mem_ac").getAsString();
		System.out.println("\"mem_ac\".equals(action)");	
		try {
			memVO =	memberDao.findByPrimarKey(mem_ac);
			System.out.println(String.valueOf(memVO));	


		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("gson.toJson(memVO)::"
			+gson.toJson(memVO));
		}

		writeText(res, gson.toJson(memVO));
	
	}
		
	}
	

	private void writeText(HttpServletResponse res, String outText) throws IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);

	}
	
	

}
;