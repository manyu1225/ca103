package com.route.model;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.android.util.ImageUtil;

public class RouteService {
	
	private RouteDAO_interface dao= new RouteDAO();
	
	public RouteService() {
		dao = new RouteDAO();
	}
	
	public RouteVO addRoute(String mem_id,String rot_name,String rot_describe,String rot_loc_start,
			String rot_loc_end,Double rot_hard,Double rot_dis,Double rot_height_up,Double rot_height_down,
			Double rot_height_ave,Double rot_slope_up,Double rot_slope_down,Double rot_slope_ave,String rot_loc_start_des,
			String rot_loc_end_des,Integer rot_status,String rot_photo,Integer rot_popu,Double rot_photo_loc,String rot_start,
			String rot_end,String rot_gps) {
		
		RouteVO routeVO = new RouteVO();
		
		routeVO.setMem_id(mem_id);
		routeVO.setRot_name(rot_name);
		routeVO.setRot_describe(rot_describe);
		routeVO.setRot_loc_start(rot_loc_start);
		routeVO.setRot_loc_end(rot_loc_end);
		routeVO.setRot_hard(rot_hard);
		routeVO.setRot_dis(rot_dis);
		routeVO.setRot_height_up(rot_height_up);
		routeVO.setRot_height_down(rot_height_down);
		routeVO.setRot_height_ave(rot_height_ave);
		routeVO.setRot_slope_up(rot_slope_up);
		routeVO.setRot_slope_down(rot_slope_down);
		routeVO.setRot_slope_ave(rot_slope_ave);
		routeVO.setRot_loc_start_des(rot_loc_start_des);
		routeVO.setRot_loc_end_des(rot_loc_end_des);
		routeVO.setRot_status(rot_status);
		
		//	進資料庫前把圖片縮小,節省網路流量
		byte []  rot_photo_init = Base64.getDecoder().decode(rot_photo);
		byte [] rot_photo_ing = ImageUtil.shrink(rot_photo_init, 300);
		Base64.Encoder encoder = Base64.getEncoder();
		String rot_photo_end = encoder.encodeToString(rot_photo_ing);

		routeVO.setRot_photo(rot_photo_end);
		routeVO.setRot_popu(rot_popu);
		routeVO.setRot_photo_loc(rot_photo_loc);
		routeVO.setRot_start(rot_start);
		routeVO.setRot_end(rot_end);
		routeVO.setRot_gps(rot_gps);
		
		String rot_id = dao.insert(routeVO);
		
		routeVO.setRot_id(rot_id);
		
		return routeVO;
	}
	
	public void getRot_Photo(String rot_id, HttpServletResponse response) {
		dao.getRot_Photo(rot_id, response);
	}
	
	public RouteVO getRouteDetailed(String rot_id) {
		return dao.getRouteDetailed(rot_id);
	}
	
	//	傳入SQL是取得全部的路線清單、傳入MEM_ID是取得會員收藏的路線清單
	public List<RouteVO> getRouteList(String sql) {
		return dao.getRouteList(sql);
	}
	
	public List<RouteVO> getRouteCloseList(String sqlColse) {
		return dao.getRouteCloseList(sqlColse);
	}
	
	public void closeRoute(String rot_id) {
		dao.closeRoute(rot_id);
	}
	
	public void updataRoute_front(String rot_id, String rot_name, String rot_describe, 
			String rot_loc_start, String rot_loc_end, String rot_loc_start_des,
			String rot_loc_end_des, Integer rot_status, String rot_photo, Double rot_photo_loc) {
		
		RouteVO routeVO = new RouteVO();
		
		routeVO.setRot_id(rot_id);
		routeVO.setRot_name(rot_name);
		routeVO.setRot_describe(rot_describe);
		routeVO.setRot_loc_start(rot_loc_start);
		routeVO.setRot_loc_end(rot_loc_end);
		routeVO.setRot_loc_start_des(rot_loc_start_des);
		routeVO.setRot_loc_end_des(rot_loc_end_des);
		routeVO.setRot_status(rot_status);
		
		//	進資料庫前把圖片縮小,節省網路流量
		byte []  rot_photo_init = Base64.getDecoder().decode(rot_photo);
		byte [] rot_photo_ing = ImageUtil.shrink(rot_photo_init, 300);
		Base64.Encoder encoder = Base64.getEncoder();
		String rot_photo_end = encoder.encodeToString(rot_photo_ing);
		
		routeVO.setRot_photo(rot_photo_end);
		routeVO.setRot_photo_loc(rot_photo_loc);
		dao.update_front(routeVO);
	}
	
	public void updataRoute_back(String rot_id, String mem_id,String rot_name,String rot_describe,String rot_loc_start,
			String rot_loc_end,Double rot_hard,Double rot_dis,Double rot_height_up,Double rot_height_down,
			Double rot_height_ave,Double rot_slope_up,Double rot_slope_down,Double rot_slope_ave,String rot_loc_start_des,
			String rot_loc_end_des,Integer rot_status,String rot_photo,Integer rot_popu,Double rot_photo_loc) {
		
		RouteVO routeVO = new RouteVO();
		
		routeVO.setRot_id(rot_id);
		routeVO.setMem_id(mem_id);
		routeVO.setRot_name(rot_name);
		routeVO.setRot_describe(rot_describe);
		routeVO.setRot_loc_start(rot_loc_start);
		routeVO.setRot_loc_end(rot_loc_end);
		routeVO.setRot_hard(rot_hard);
		routeVO.setRot_dis(rot_dis);
		routeVO.setRot_height_up(rot_height_up);
		routeVO.setRot_height_down(rot_height_down);
		routeVO.setRot_height_ave(rot_height_ave);
		routeVO.setRot_slope_up(rot_slope_up);
		routeVO.setRot_slope_down(rot_slope_down);
		routeVO.setRot_slope_ave(rot_slope_ave);
		routeVO.setRot_loc_start_des(rot_loc_start_des);
		routeVO.setRot_loc_end_des(rot_loc_end_des);
		routeVO.setRot_status(rot_status);
		
		//	進資料庫前把圖片縮小,節省網路流量
		byte []  rot_photo_init = Base64.getDecoder().decode(rot_photo);
		byte [] rot_photo_ing = ImageUtil.shrink(rot_photo_init, 300);
		Base64.Encoder encoder = Base64.getEncoder();
		String rot_photo_end = encoder.encodeToString(rot_photo_ing);
			
		routeVO.setRot_photo(rot_photo_end);
		routeVO.setRot_popu(rot_popu);
		routeVO.setRot_photo_loc(rot_photo_loc);
		dao.update_back(routeVO);
	}
	
	public void updataRoute_back(String rot_id,Integer rot_popu) {
		dao.update_back(rot_id,rot_popu);
	}
	
	public void update_messageCount(String rot_id) {
		dao.update_messageCount(rot_id);
	}
}