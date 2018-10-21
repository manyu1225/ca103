package com.android.route.model;

import java.util.List;

import javax.servlet.http.HttpServletResponse;



public class RouteService {
		public void getRot_Photo(String rot_id, HttpServletResponse response) {
		dao.getRot_Photo(rot_id, response);
	}
	
private RouteDAO_interface dao;
	
	public RouteService() {
		dao = new RouteDAO();
	}
	
	public RouteVO  servicegetRouteDetailed(String rot_id) {
		return dao.getByRotid(rot_id);	
	}

//	傳入SQL是取得全部的路線清單、傳入MEM_ID是取得會員收藏的路線清單
	public List<RouteVO>  servicegetByMemid(String mem_id) {
		return dao.getByMemId(mem_id);
	}
	
	public List<RouteVO> serviceGetAll(){
		return dao.getAll();
		
	}
	

	public byte[] servicegetImage(String rot_id) {
		return dao.getImage(rot_id);

	}
}
