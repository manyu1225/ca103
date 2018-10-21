package com.android.routecollection.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.android.favorite_gp.model.Favorite_GPService;
import com.android.route.model.RouteService;
import com.android.route.model.RouteVO;
import com.android.routecollection.model.RouteCollectionService;
import com.android.routecollection.model.RouteCollectionVO;
import com.android.team.model.TeamVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;


public class Android_RouteCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	private void writeText(HttpServletResponse res, String outText) throws IOException {
		res.setContentType(CONTENT_TYPE);
		PrintWriter out = res.getWriter();
		out.print(outText);
		out.close();
		System.out.println("outText: " + outText);
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
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
		String mem_id = jsonObject.get("mem_id").getAsString();

		System.out.println(action+"=action");
		System.out.println(mem_id+"=mem_id");
		
	//=======================================================================	
		List <RouteVO> routevolist = new ArrayList<>();
		RouteCollectionService routeCollectionRoute = new RouteCollectionService();
//		String favorite = "true";

	//點選愛心收藏路線	給兩個FK 
		if("saveloveroute".equals(action)&& mem_id!=null) {
			String rot_id = jsonObject.get("rot_id").getAsString();
			System.out.println(rot_id+"=rot_id");
			try {
				
				routeCollectionRoute.addCollection(
						mem_id, rot_id 
						,new Timestamp(System.currentTimeMillis()));//return  List<TeamVO>		
//				writeText(res, gson.toJson(routevolist));
			}catch (Exception e) {
				System.out.println(e.getMessage());
//				favorite = "false";
			}	
			
		}else if("getMyroutebymem".equals(action) && mem_id!=null) {
			
			routevolist	= routeCollectionRoute.servicefindRouteCollection(mem_id);
			System.out.println(String.valueOf(routevolist));
			writeText(res, gson.toJson(routevolist));
//			刪除收藏的路線	
			
		}else if ("delete_loveroute".equals(action)&& mem_id!=null) {
			String rot_id = jsonObject.get("rot_id").getAsString();
			routeCollectionRoute.deleteCollectionRoute(rot_id, mem_id);
			System.out.println(rot_id+"=delete_loveroute");

			writeText(res, gson.toJson(routevolist));
			//我得收藏列表取出資料庫的內容資料

		}
	}
	
}

