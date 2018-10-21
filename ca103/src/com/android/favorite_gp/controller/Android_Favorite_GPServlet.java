package com.android.favorite_gp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.android.favorite_gp.model.Favorite_GPService;
import com.android.favorite_gp.model.Favorite_GPVO;
import com.android.team.model.TeamVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Android_Favorite_GPServlet  extends HttpServlet{
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	private void writeText(HttpServletResponse res, String outText) throws IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);
	}
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
		

//取得手機的揪團id使用add方法將喜愛的揪團加入 action=
		System.out.println(action+"=action");
		
		String mem_id = jsonObject.get("mem_id").getAsString();
		System.out.println(mem_id+"=mem_id");
//================================================
		List <TeamVO> teamvolist = new ArrayList<>();
		Favorite_GPService favorite_GPDAO = new Favorite_GPService();
		String favorite = "true";
//登入後取得此會員喜歡的團					
		if("getMyteambymem".equals(action) && mem_id!=null) {
			teamvolist= favorite_GPDAO.searchFavGP(mem_id);//return  List<TeamVO>
				System.out.println(String.valueOf(teamvolist));
				writeText(res, gson.toJson(teamvolist));

		}else if("addloveteam".equals(action)&& mem_id!=null){
					String gp_id = jsonObject.get("gp_id").getAsString();
					System.out.println(gp_id+"=add_gp_id");
			try {
			
				Favorite_GPVO favGPVO = 
						new Favorite_GPVO(mem_id, gp_id, 
								new Timestamp(System.currentTimeMillis()));
					favorite_GPDAO.addFavGP(favGPVO);
			}catch (Exception e) {
						System.out.println(e.getMessage());
				favorite = "false";
				}
			
		}else if("delete_loveteam".equals(action)&& mem_id!=null) {
						String gp_id = jsonObject.get("gp_id").getAsString();
			System.out.println(gp_id+"= delete_gp_id");
			try {
				Favorite_GPVO favGPVO = 
						new Favorite_GPVO(mem_id, gp_id,new Timestamp(System.currentTimeMillis()));
				favorite_GPDAO.deleteFav_GP(favGPVO);
			}catch (Exception e) {
					System.out.println(e.getMessage());		
			}
		}
		
		

	}

}
