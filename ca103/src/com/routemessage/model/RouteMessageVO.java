package com.routemessage.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class RouteMessageVO implements Serializable  {
	
	private Integer rotMes_id;
	private String mem_id;
	private String rot_id;
	private String rotMes_cont;
	private Integer rotMes_status;
	private Timestamp rotMes_time;
	
	public RouteMessageVO() {}
	
	public Integer getRotMes_id() {
		return rotMes_id;
	}
	public void setRotMes_id(Integer rotMes_id) {
		this.rotMes_id = rotMes_id;
	}
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
	public String getRotMes_cont() {
		return rotMes_cont;
	}
	public void setRotMes_cont(String rotMes_cont) {
		this.rotMes_cont = rotMes_cont;
	}
	public Integer getRotMes_status() {
		return rotMes_status;
	}
	public void setRotMes_status(Integer rotMes_status) {
		this.rotMes_status = rotMes_status;
	}
	public Timestamp getRotMes_time() {
		return rotMes_time;
	}
	public void setRotMes_time(Timestamp rotMes_time) {
		this.rotMes_time = rotMes_time;
	}
	
}
