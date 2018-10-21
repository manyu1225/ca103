package com.routecollection.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class RouteCollectionVO implements Serializable  {
	private String mem_id;
	private String rot_id;
	private Timestamp rotColl_time;
	
	public RouteCollectionVO() {}
	
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
	public Timestamp getRotColl_time() {
		return rotColl_time;
	}
	public void setRotColl_time(Timestamp rotColl_time) {
		this.rotColl_time = rotColl_time;
	}
	
}
