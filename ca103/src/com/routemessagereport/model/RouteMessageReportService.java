package com.routemessagereport.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class RouteMessageReportService {
	RouteMessageReportDAO_interface dao;
	
	public RouteMessageReportService() {
		dao = new RouteMessageReportDAO();
	}
	
	public void InsertRouteMessageReport(String mem_id,String rot_id,Integer rotMes_id,String rotMesRep_cont,Timestamp rotMesRep_time) {
		RouteMessageReportVO routeMessageReportVO = new RouteMessageReportVO();
		routeMessageReportVO.setMem_id(mem_id);
		routeMessageReportVO.setRotMes_id(rotMes_id);
		routeMessageReportVO.setRotMesRep_cont(rotMesRep_cont);
		routeMessageReportVO.setRotMesRep_time(rotMesRep_time);
		dao.insert(routeMessageReportVO);
	}
	
	public List<RouteMessageReportVO> rotMessRepOrNot(String mem_id) {
		return dao.rotMessRepOrNot(mem_id);
	}
	
	public List<RouteMessageReportVO> getAll() {
		return dao.getAll();
	}
	
	public void closeMes(int rotMes_id,int rotMesRep_status) {
		dao.closeMesOrNot(rotMes_id,rotMesRep_status);
	}
	
	public List<RouteMessageReportVO> getByStatus(String rotMesRep_status) {
		return dao.getByStatus(rotMesRep_status);
	}
}
