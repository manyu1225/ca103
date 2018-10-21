package com.android.activity.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.android.activity.model.Activity_Service;
import com.android.activity.model.Activity_VO;
import com.android.route.model.RouteService;
import com.android.route.model.RouteVO;
import com.android.team.model.TeamVO;
import com.android.util.ImageUtil;
//import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Android_Activity_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		Activity_Service activity_ServiceDAO = new Activity_Service();
//		List<Activity_VO> activity_VOlist = activity_ServiceDAO.getAllAct();
//		writeText(res, new Gson().toJson(activity_VOlist));
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Activity_Service activity_ServiceDAO = new Activity_Service();

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		BufferedReader br = req.getReader();
		StringBuilder jsonIn = new StringBuilder();
		String line = null;

		while ((line = br.readLine()) != null) {
			jsonIn.append(line);
			System.out.println("input: " + jsonIn);

		}
		br.close();


		JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
		String action = jsonObject.get("action").getAsString();
//===============================================================		
		if ("getAll".equals(action)) {//首頁得到全部的資料
			List<Activity_VO> activity_VOlist = null;
			try {
				activity_VOlist=	activity_ServiceDAO.getAllAct();
			writeText(res, gson.toJson(activity_VOlist));
		

			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("gson.toJson(Activity_VO)::"
				+gson.toJson(activity_VOlist));
			}

//	
		}else if ("getImage".equals(action)) {
			String act_ID = jsonObject.get("act_ID").getAsString();
			Integer intact_ID	= Integer.valueOf(act_ID);
			System.out.println("gson.toJson(image)::"
					+gson.toJson(act_ID));
					
			OutputStream os = res.getOutputStream();
			int imageSize = jsonObject.get("imageSize").getAsInt();
			byte[] image = activity_ServiceDAO.servicegetImage(intact_ID);
			if (image != null) {
				// 縮圖 in server side
				image = ImageUtil.shrink(image, imageSize);
				res.setContentType("image/jpeg");
				res.setContentLength(image.length);
			}
			os.write(image);
//
		} else 
			{
			writeText(res, "");
		}
//		
	
	
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
