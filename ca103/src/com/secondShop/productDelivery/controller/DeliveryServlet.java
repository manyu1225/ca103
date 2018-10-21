package com.secondShop.productDelivery.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.secondShop.productDelivery.model.DeliveryService;
import com.secondShop.productDelivery.model.DeliveryVO;

//@WebServlet("/DeliveryServlet2")
public class DeliveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeliveryServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");
		String action = req.getParameter("action");
		
		if ("deliveryAdd".equals(action)) {
			String yesOrNo = "yes";
			JSONObject obj = new JSONObject();
			String deliveryName = req.getParameter("deliveryName");
			String deliveryAddress = req.getParameter("deliveryAddress");
			String memId = req.getParameter("memId");
			String deliveryPhone = req.getParameter("deliveryPhone");
			String phoneType = "^[0-9]*$";
			if (deliveryAddress == null) {
				System.out.println(1+deliveryAddress);
				try {
					obj.put("Address", "請輸入輸地址，地址不能為空");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				yesOrNo = "no";
			} else {
				if (deliveryAddress.trim().length() == 0) {
					System.out.println(2+deliveryAddress);
					try {
						obj.put("Address", "請輸入輸地址，地址不能為空");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					yesOrNo = "no";
				}
			}
			if (deliveryName == null) {
				try {
					obj.put("Name", "請輸入輸入名字，名字不能為空");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				yesOrNo = "no";
			} else {
				if (deliveryName.trim().length() == 0) {
					try {
						obj.put("Name", "請輸入輸入名字，名字不能為空");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					yesOrNo = "no";
				}
			}
			if (deliveryPhone == null) {
				try {
					obj.put("Phone", "請輸入輸入電話，電話不能為空");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				yesOrNo = "no";
			} else {
				if (deliveryPhone.trim().length() == 0) {
					try {
						obj.put("Phone", "請輸入輸入電話，電話不能為空");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					yesOrNo = "no";
				} else {
					if(!deliveryPhone.trim().matches(phoneType)) {
						try {
							obj.put("Phone", "請輸入輸入電話，請輸入數字");
						} catch (JSONException e) {
							e.printStackTrace();
						}
						yesOrNo = "no";
					}
				}
			}
			try {
				obj.put("yesOrNo", yesOrNo);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			System.out.println("obj.toString():"+obj.toString());
			if ("yes".equals(yesOrNo)) {
				DeliveryService deliverySvc = new DeliveryService();
				DeliveryVO deliveryVO = new DeliveryVO();
				deliveryVO.setDeliveryAddress(deliveryAddress);
				deliveryVO.setDeliveryName(deliveryName);
				deliveryVO.setDeliveryPhone(deliveryPhone);
				deliveryVO.setMemId(memId);
				String deliveryId = deliverySvc.insertAddress(deliveryVO);
				System.out.println("HIHI");
				try {
					obj.put("Done", "新增成功");
					obj.put("deliveryAddress", deliveryAddress);
					obj.put("deliveryName", deliveryName);
					obj.put("deliveryPhone", deliveryPhone);
					obj.put("deliveryId", deliveryId);
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();

		}
		if("deliveryDelet".equals(action)) {
			Enumeration<String> emm = req.getParameterNames(); //拿所有req中的keys (JsonArray內容都已被獨立出來）
			List<String> deliveryIdList = new ArrayList<>();
			JSONArray objarray = new JSONArray();
			JSONObject obj = null;
			
			while(emm.hasMoreElements()) {
				String key = (String) emm.nextElement();
				if(  !("action".equals(key)) ) {
					
					deliveryIdList.add(req.getParameter(key));
				}
			}
			DeliveryService deliverySvc = new DeliveryService();
			for(String deliveryId:deliveryIdList) {
				obj = new JSONObject();
				try {
					obj.put("deliveryId",deliveryId);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				objarray.put(obj);
				deliverySvc.deleteAddress(deliveryId);
			}

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			out.write(objarray.toString());
			out.flush();
			out.close();
		}

	}

}
