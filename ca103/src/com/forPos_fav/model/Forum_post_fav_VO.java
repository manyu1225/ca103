package com.forPos_fav.model;

import java.sql.Timestamp;

public class Forum_post_fav_VO implements java.io.Serializable {

	private Integer forPost_ID;
	private String mem_ID;
	private Timestamp forPost_fav_time;

	public Forum_post_fav_VO() {

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

	public Timestamp getForPost_fav_time() {
		return forPost_fav_time;
	}

	public void setForPost_fav_time(Timestamp forPost_fav_time) {
		this.forPost_fav_time = forPost_fav_time;
	}

}
