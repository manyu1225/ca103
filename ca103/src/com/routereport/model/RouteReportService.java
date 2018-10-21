package com.routereport.model;

import java.sql.Timestamp;
import java.util.List;

import com.routemessagereport.model.RouteMessageReportVO;

public class RouteReportService {
	
	RouteReportDAO_interface dao;
	
	public RouteReportService() {
		dao = new RouteReportDAO();
	}
	
	public void insertRouteReport(String mem_id,String rot_id,String rotRep_cont,Timestamp rotRep_time) {
		RouteReportVO routeReportVO = new RouteReportVO();
		routeReportVO.setMem_id(mem_id);
		routeReportVO.setRot_id(rot_id);
		routeReportVO.setRotRep_cont(rotRep_cont);
		routeReportVO.setRotRep_status(1);
		routeReportVO.setRotRep_time(rotRep_time);
		dao.insert(routeReportVO);
	}
	
	public int rotRepOrNot(String rot_id, String mem_id) {
		RouteReportVO routeReportVO = new RouteReportVO();
		routeReportVO.setMem_id(mem_id);
		routeReportVO.setRot_id(rot_id);
		return dao.rotRepOrNot(routeReportVO);
	}
	
	public List<RouteReportVO> getAll() {
		return dao.getAll();
	}
	
	public List<RouteReportVO> getByStatus(String rotRep_status) {
		return dao.getByStatus(rotRep_status);
	}
	
	public void closeRot(String rot_id,Integer rotRep_status) {
		dao.closeRot(rot_id, rotRep_status);
	}
}
