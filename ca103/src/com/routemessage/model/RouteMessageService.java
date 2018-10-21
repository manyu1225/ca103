package com.routemessage.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class RouteMessageService {
	RouteMessageDAO_interface dao;
	
	public RouteMessageService() {
		dao = new RouteMessageDAO();
	}
	
	public void inserRouteMessage(String mem_id,String rot_id,String rotMes_cont,Timestamp rotMes_time) {
		RouteMessageVO routeMessageVO = new RouteMessageVO();
		routeMessageVO.setMem_id(mem_id);
		routeMessageVO.setRot_id(rot_id);
		routeMessageVO.setRotMes_cont(rotMes_cont);
		routeMessageVO.setRotMes_time(rotMes_time);
		dao.insert(routeMessageVO);
	}
	
	public List<RouteMessageVO> getByRouteId(String rot_id){
		return dao.getByRouteId(rot_id);
	}
	
	public void closeRouteMessage(Integer rotMes_id) {
		dao.close(rotMes_id);
	}
	
	public void updateRouteMessage(Integer rotMes_id, String rotMes_cont) {
		RouteMessageVO routeMessageVO = new RouteMessageVO();
		routeMessageVO.setRotMes_id(rotMes_id);
		routeMessageVO.setRotMes_cont(rotMes_cont);
		dao.update(routeMessageVO);
	}
	
	public List<RouteMessageVO> getAll(){
		return dao.getAll();
	}
	
	public List<RouteMessageVO> getByStatus(String rotMesRep_status){
		return dao.getByStatus(rotMesRep_status);
	}
	
	public Map<String, Integer> getRouteMessageCount(){
		return dao.getRouteMessageCount();
	}
}
