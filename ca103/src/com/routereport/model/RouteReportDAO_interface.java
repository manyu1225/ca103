package com.routereport.model;

import java.util.List;

import com.routemessagereport.model.RouteMessageReportVO;

public interface RouteReportDAO_interface {
	public void insert(RouteReportVO routeReportVO);
	public void update(RouteReportVO routeReportVO);
	public void delete(RouteReportVO routeReportVO);
	public void closeRot(String rot_id,Integer rotRep_status);
	public List<RouteReportVO> getAll();
	public List<RouteReportVO> getByStatus(String rotRep_status);
	public int rotRepOrNot(RouteReportVO routeReportVO);
}
