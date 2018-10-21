package com.reported_gp.model;

import java.sql.Timestamp;

public class Reported_GPVO {
	
	private String mem_id;
	private String gp_id;
	private Timestamp rep_time; 
	private String rep_detail;
	private Integer rep_status;
	
	
	public Reported_GPVO() {
		
	}
	
	public Reported_GPVO(String mem_id, String gp_id, Timestamp rep_time, String rep_detail, Integer rep_status) {
		super();
		this.mem_id = mem_id;
		this.gp_id = gp_id;
		this.rep_time = rep_time;
		this.rep_detail = rep_detail;
		this.rep_status = rep_status;
	}
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getGp_id() {
		return gp_id;
	}
	public void setGp_id(String gp_id) {
		this.gp_id = gp_id;
	}
	public Timestamp getRep_time() {
		return rep_time;
	}
	public void setRep_time(Timestamp rep_time) {
		this.rep_time = rep_time;
	}
	public String getRep_detail() {
		return rep_detail;
	}
	public void setRep_detail(String rep_detail) {
		this.rep_detail = rep_detail;
	}
	public Integer getRep_status() {
		return rep_status;
	}
	public void setRep_status(Integer rep_status) {
		this.rep_status = rep_status;
	}
	
	
	
	
}
