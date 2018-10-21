package com.comJoin.model;

import java.io.Serializable;

public class JoinedComVO implements Serializable {
	private String com_id;
	private String mem_id;
	private Integer pm_setting;
	private Integer available;
	
	public String getCom_id() {
		return com_id;
	}
	public void setCom_id(String com_id) {
		this.com_id = com_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public Integer getPm_setting() {
		return pm_setting;
	}
	public void setPm_setting(Integer pm_setting) {
		this.pm_setting = pm_setting;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	
}
