package com.android.team.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.android.team.model.TeamService;
import com.android.team.model.TeamVO;
import com.android.util.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Android_TeamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		 TeamService teamSvc = new TeamService();
		List<TeamVO>  teamVOlist = teamSvc.serviceGetAll();
		writeText(response, new Gson().toJson(teamVOlist));
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		 TeamService teamSvc = new TeamService();
		 
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		BufferedReader br = request.getReader();
		
		StringBuilder jsonIn = new StringBuilder();
		String line = null;

		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
			System.out.println("input: " + jsonIn);
		}
			br.close();

			JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
			System.out.println("jsonObject: " + jsonObject);

			String action = jsonObject.get("action").getAsString();


//------------------------------------------------------------------------
//where thid getall to android have to do  Type listType = new TypeToken<List<Book>>() {}
			//使用TypeToken进行转化to list<>XX = new Gson().fromJson(jsonString,type);
			if ("getAll".equals(action)) {//首頁得到全部的資料
				List<TeamVO> teamVOlist = null;
				try {
				teamVOlist= teamSvc.serviceGetAll();
				writeText(response, gson.toJson(teamVOlist));
			

				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("gson.toJson(teamVOlist)::"
					+gson.toJson(teamVOlist));
				}
			} else if ("getByMemId".equals(action)) {
				String mem_id = jsonObject.get("mem_id").getAsString();
				System.out.println("input mem_id: " + mem_id);

				List<TeamVO> teamVOlist = null;
				try {
				//登入後得到我的揪團清單	
				teamVOlist = 
						teamSvc.serviceGetAllByMemId(mem_id);
				writeText(response, gson.toJson(teamVOlist));
		
				}catch(Exception e) {
					e.printStackTrace();
					System.out.println("gson.toJson(teamVOlist):getMyteambymem:"
					+gson.toJson(teamVOlist));
				}

			// 圖片請求
			}else if ("getImage".equals(action)) {
				String gp_id = jsonObject.get("gp_id").getAsString();
System.out.println(gp_id);
				OutputStream os = response.getOutputStream();
				int imageSize = jsonObject.get("imageSize").getAsInt();
				byte[] image = teamSvc.servicegetImage(gp_id);
				if (image != null) {
					// 縮圖 in server side
					image = ImageUtil.shrink(image, imageSize);
					response.setContentType("image/jpeg");
					response.setContentLength(image.length);
				}
				os.write(image);
				System.out.println("gson.toJson(image)::"
						+gson.toJson(image));
			} else if("getBygp_id".equals(action) ) {
				List<TeamVO> teamVOlist = null;
			
				String gp_id = jsonObject.get("gp_id").getAsString();
				teamVOlist 	= teamSvc.serviceGetOneByTeamId(gp_id);
				System.out.println(String.valueOf(teamVOlist));
				
				writeText(response, gson.toJson(teamVOlist));
			
				}else {
				writeText(response, "");
			}
			

	}
//--------------------------------------------------------------------
private void writeText(HttpServletResponse response, String outText) throws IOException {
	response.setContentType(CONTENT_TYPE);
	PrintWriter out = response.getWriter();
	out.print(outText+""
			+ "\n");
	out.close();
	System.out.println("outText: " + outText);
	}

}
