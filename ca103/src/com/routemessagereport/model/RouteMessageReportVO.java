package com.routemessagereport.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.routemessage.model.RouteMessageVO;

public class RouteMessageReportVO implements Serializable  {
	private String mem_id;
	private Integer rotMes_id;
	private Timestamp rotMesRep_time;
	private String rotMesRep_cont;
	private Integer rotMesRep_status;
	private RouteMessageVO routeMessageVO;
	
	public RouteMessageReportVO() {}
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Integer getRotMes_id() {
		return rotMes_id;
	}
	public void setRotMes_id(Integer rotMes_id) {
		this.rotMes_id = rotMes_id;
	}
	public Timestamp getRotMesRep_time() {
		return rotMesRep_time;
	}
	public void setRotMesRep_time(Timestamp rotMesRep_time) {
		this.rotMesRep_time = rotMesRep_time;
	}
	public String getRotMesRep_cont() {
		return rotMesRep_cont;
	}
	public void setRotMesRep_cont(String rotMesRep_cont) {
		this.rotMesRep_cont = rotMesRep_cont;
	}
	public Integer getRotMesRep_status() {
		return rotMesRep_status;
	}
	public void setRotMesRep_status(Integer rotMesRep_status) {
		this.rotMesRep_status = rotMesRep_status;
	}

	public RouteMessageVO getRouteMessageVO() {
		return routeMessageVO;
	}

	public void setRouteMessageVO(RouteMessageVO routeMessageVO) {
		this.routeMessageVO = routeMessageVO;
	}
	
}
