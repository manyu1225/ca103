package com.routemessagereport.model;

import java.util.List;

public interface RouteMessageReportDAO_interface {
	public void insert(RouteMessageReportVO routeMessageReportVO);
	public void update(RouteMessageReportVO routeMessageReportVO);
	public void delete(RouteMessageReportVO routeMessageReportVO);
	public void closeMesOrNot(Integer rotMes_id, Integer rotMesRep_status);
	public List<RouteMessageReportVO> getAll();
	public List<RouteMessageReportVO> getByStatus(String rotMesRep_status);
	public List rotMessRepOrNot(String mem_id);
}
