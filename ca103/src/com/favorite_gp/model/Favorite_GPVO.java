package com.favorite_gp.model;

import java.sql.Timestamp;

public class Favorite_GPVO {
	private String mem_id;
	private String gp_id;
	private Timestamp fav_timestamp;
	
	
	
	public Favorite_GPVO() {
	
	}
	public Favorite_GPVO(String mem_id, String gp_id, Timestamp fav_timestamp) {
		super();
		this.mem_id = mem_id;
		this.gp_id = gp_id;
		this.fav_timestamp = fav_timestamp;
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
	public Timestamp getFav_timestamp() {
		return fav_timestamp;
	}
	public void setFav_timestamp(Timestamp fav_timestamp) {
		this.fav_timestamp = fav_timestamp;
	}
	
}
