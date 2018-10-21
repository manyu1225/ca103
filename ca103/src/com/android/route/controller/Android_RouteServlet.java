package com.android.route.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.android.route.model.RouteService;
import com.android.route.model.RouteVO;
import com.android.util.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Android_RouteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENT_TYPE = "text/html; charset=UTF-8";

   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RouteService routeSvc = new RouteService();
		 List<RouteVO>  routeVOlist = routeSvc.serviceGetAll();
		writeText(response, new Gson().toJson(routeVOlist));
//	 doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RouteService routeSvc = new RouteService();
			Gson gson = new Gson();
			BufferedReader br = request.getReader();
			StringBuilder jsonIn = new StringBuilder();
			String line = null;

			while ((line = br.readLine()) != null) {
				jsonIn.append(line);
	System.out.println("input routeSvc: " + jsonIn);
			}				
			br.close();

				JsonObject jsonObject = gson.fromJson(jsonIn.toString(), JsonObject.class);
				
				String action = jsonObject.get("action").getAsString();
				System.out.println("action="+action);
//where thid getall to android have to do  Type listType = new TypeToken<List<Book>>() {}
	//使用TypeToken进行转化to list<>XX = new Gson().fromJson(jsonString,type);
	//=======================================================================
				if ("getAll".equals(action)) {
					
						List<RouteVO>  routeVOlist = null;
					try {
						routeVOlist= routeSvc.serviceGetAll();//<<<<<<<<<
					
//						   for(int i=10;i<routeVOlist.size();i++){
//   System.out.println("dead"+routeVOlist.get(i));
//
//							   routeVOlist.remove(i);
System.out.println("routeVOlist"+String.valueOf(routeVOlist));
//						   }
//System.out.println(String.valueOf(routeVOlist));
						   writeText(response, gson.toJson(routeVOlist));//<<<<<string>

						}catch(Exception e) {
							e.printStackTrace();
							System.out.println("gson.toJson(teamVOlist)::"
							+gson.toJson(routeVOlist));
							
							
						}	
				
				} else if ("getImage".equals(action)) {
					String rot_id = jsonObject.get("rot_id").getAsString();
	System.out.println("gson.toJson(image)::"
					+gson.toJson(rot_id));
					
					OutputStream os = response.getOutputStream();
					int imageSize = jsonObject.get("imageSize").getAsInt();
					
					byte[] image = routeSvc.servicegetImage(rot_id);
					if (image != null) {
						// 縮圖 in server side
						image = ImageUtil.shrink(image, 150);
						response.setContentType("image/jpeg");
						response.setContentLength(image.length);
					}
					os.write(image);
					System.out.println("gson.toJson(image)::"
							+gson.toJson(image));
							
				
									
				}else if ("getByRotid".equals(action)) {
					
					RouteVO  routeVO = routeSvc.servicegetRouteDetailed("rot_id");
					writeText(response, gson.toJson(routeVO));
			

				} else if ("getByMemId".equals(action)) {
					
					List<RouteVO>  routeVOlist = routeSvc.servicegetByMemid("mem_id");
					writeText(response, gson.toJson(routeVOlist));
					///gson.toJson return String to android that included image base64(string)
					

				} else if ("getPhoto".equals(action)) {	
					OutputStream os = response.getOutputStream();
					int imageSize = jsonObject.get("imageSize").getAsInt();

					 routeSvc.getRot_Photo("gp_id",response);

					
	// 圖片請求				
					 
					 
					 
					 
					 
					 
					 
					 
					 
					 
				
				} else {
					writeText(response, "");
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
