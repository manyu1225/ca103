package com.android.joined_gp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.android.joined_gp.model.JoinedGPService;
import com.android.team.model.TeamVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;



public class Android_Joined_GPServlet extends HttpServlet{
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;
		
		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
		}
		
		System.out.println("input: " + jsonIn);
		
		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
		
		System.out.println(action+"=action");
	
		String mem_id = jsonObject.get("mem_id").getAsString();//mem_id ==use-id
		System.out.println(mem_id+"=mem_id");

		//-=====================================================
		List <TeamVO> teamvolist = new ArrayList<>();
	
			if("FIND_JGP".equals(action) && mem_id!=null) {
				JoinedGPService jgpSrc = new JoinedGPService();
				teamvolist= jgpSrc.SearchJoinedGPByMember(mem_id);
				
				//return  List<TeamVO>
					System.out.println(String.valueOf(teamvolist));
			
			
			writeText(res, gson.toJson(teamvolist));
			
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




