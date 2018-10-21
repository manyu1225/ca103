package com.routereport.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class RouteReportVO implements Serializable  {
	
	private String mem_id;
	private String rot_id;
	private Timestamp rotRep_time;
	private String rotRep_cont;
	private Integer rotRep_status;
	
	public RouteReportVO() {}
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getRot_id() {
		return rot_id;
	}
	public void setRot_id(String rot_id) {
		this.rot_id = rot_id;
	}
	public Timestamp getRotRep_time() {
		return rotRep_time;
	}
	public void setRotRep_time(Timestamp rotRep_time) {
		this.rotRep_time = rotRep_time;
	}
	public String getRotRep_cont() {
		return rotRep_cont;
	}
	public void setRotRep_cont(String rotRep_cont) {
		this.rotRep_cont = rotRep_cont;
	}
	public Integer getRotRep_status() {
		return rotRep_status;
	}
	public void setRotRep_status(Integer rotRep_status) {
		this.rotRep_status = rotRep_status;
	}
	
}
