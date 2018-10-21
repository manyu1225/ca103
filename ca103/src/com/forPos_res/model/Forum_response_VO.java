package com.forPos_res.model;

import java.sql.Timestamp;

public class Forum_response_VO implements java.io.Serializable {
	
	private Integer forRes_ID;
	private Integer forPost_ID;
	private String mem_ID;
	private String forRes_content;
	private Timestamp forRes_time;
	private Integer forRes_rating;
	
	
	
	public Integer getForRes_ID() {
		return forRes_ID;
	}
	public void setForRes_ID(Integer forRes_ID) {
		this.forRes_ID = forRes_ID;
	}
	public Integer getForPost_ID() {
		return forPost_ID;
	}
	public void setForPost_ID(Integer forPost_ID) {
		this.forPost_ID = forPost_ID;
	}
	public String getMem_ID() {
		return mem_ID;
	}
	public void setMem_ID(String mem_ID) {
		this.mem_ID = mem_ID;
	}
	public String getForRes_content() {
		return forRes_content;
	}
	public void setForRes_content(String forRes_content) {
		this.forRes_content = forRes_content;
	}
	public Timestamp getForRes_time() {
		return forRes_time;
	}
	public void setForRes_time(Timestamp forRes_time) {
		this.forRes_time = forRes_time;
	}
	public Integer getForRes_rating() {
		return forRes_rating;
	}
	public void setForRes_rating(Integer forRes_rating) {
		this.forRes_rating = forRes_rating;
	}
	



	
	 
	
	

}
