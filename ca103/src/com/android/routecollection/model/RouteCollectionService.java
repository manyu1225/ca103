package com.android.routecollection.model;

import java.util.List;

import com.android.mem.model.MemVO;
import com.android.route.model.RouteVO;

import java.sql.Timestamp;

public class RouteCollectionService {
	
	private RouteCollectionDAO_interface dao= new RouteCollectionDAO();
	
	public RouteCollectionService() {
		dao = new RouteCollectionDAO();
	}
	
	//	新增收藏的路線
	public void addCollection(String mem_id, String rot_id, Timestamp rotColl_time) {
		
		RouteCollectionVO routeCollectionVO = new RouteCollectionVO();
		routeCollectionVO.setMem_id(mem_id);
		routeCollectionVO.setRot_id(rot_id);
		routeCollectionVO.setRotColl_time(rotColl_time);
		
		dao.insert(routeCollectionVO);
	}
	
	
	//	刪除收藏的路線
	public void deleteCollectionRoute(String rot_id, String mem_id){
		
		RouteCollectionVO routeCollectionVO = new RouteCollectionVO();
		routeCollectionVO.setMem_id(mem_id);
		routeCollectionVO.setRot_id(rot_id);
		dao.delete(routeCollectionVO);
	}
	
	
	
	//	查詢收藏的路線
	public List<RouteVO> findRouteCollectionList(String mem_id,String sql){
		return dao.findRouteCollectionList(mem_id, sql);
	}
	
	public List<RouteVO> servicefindRouteCollection(String mem_id){
		MemVO memVO = new MemVO();
		memVO.setMem_id(mem_id);
		return dao.findRouteCollection(memVO);
	}
	
	
	//	以ROT_ID判斷是否要顯示新增收藏的按鈕
	public int collOrNot(String rot_id, String mem_id) {
		RouteCollectionVO routeCollectionVO = new RouteCollectionVO();
		routeCollectionVO.setMem_id(mem_id);
		routeCollectionVO.setRot_id(rot_id);
		return dao.collOrNot(routeCollectionVO);
	}
}
