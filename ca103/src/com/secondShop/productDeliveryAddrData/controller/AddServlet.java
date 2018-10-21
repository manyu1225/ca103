package com.secondShop.productDeliveryAddrData.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.secondShop.productDeliveryAddrData.model.AddressDAO;
import com.secondShop.productDeliveryAddrData.model.AreaVO;
import com.secondShop.productDeliveryAddrData.model.RoadVO;

/**
 * Servlet implementation class AddServelt
 */
@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String addrType = req.getParameter("addrType");
		System.out.println("39-addrType "+addrType);
		
		if("getArea".equals(addrType)) {
			String cityId = req.getParameter("cityId");
			System.out.println(cityId);
			
			JSONArray jSONArray = new JSONArray();  //[鳳山,obj,obj,obj,obj,obj]
			
			List<AreaVO> areaList = new AddressDAO().getArea(cityId);
			
			for(AreaVO areaVO :areaList) {
				
				JSONObject obj = new JSONObject();
				
				try {
					obj.put("areaId", areaVO.getAreaId());
					obj.put("areaName", areaVO.getAreaName() );
					obj.put("areaEngName", areaVO.getAreaEngName());
				} catch (JSONException e) {
					e.printStackTrace();
				}
	
				
				jSONArray.put(obj);
			}
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			
			out.write(jSONArray.toString());
			out.flush();
			out.close();
			
		}
		
		if("getRoad".equals(addrType)) {
			String areaId = req.getParameter("AreaId");
			System.out.println(areaId);

			JSONArray jSONArray = new JSONArray();
			List<RoadVO> roadList =new AddressDAO().getRoad(areaId);
			for(RoadVO roadVO: roadList) {
				JSONObject obj = new JSONObject();
				try {
					obj.put("roadId", roadVO.getRoadId());
					obj.put("roadName", roadVO.getRoadName());
					obj.put("roadEngName",roadVO.getRoadEngName());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				jSONArray.put(obj);
			}
			
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			
			out.write(jSONArray.toString());
			out.flush();
			out.close();
			
		}
		
		
		

		
		
		
	}

}
