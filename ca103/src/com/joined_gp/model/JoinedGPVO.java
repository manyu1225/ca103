package com.joined_gp.model;

import java.sql.Timestamp;

public class JoinedGPVO {
	
	public JoinedGPVO() {
	}
	
	public JoinedGPVO(String mem_id, String gp_id, Timestamp join_time, Integer pmsn_setting, Timestamp chk_time,
			Integer chk_status) {
		super();
		this.mem_id = mem_id;
		this.gp_id = gp_id;
		this.join_time = join_time;
		this.pmsn_setting = pmsn_setting;
		this.chk_time = chk_time;
		this.chk_status = chk_status;
	}
	
	private String mem_id;
	private String gp_id;
	private Timestamp join_time;
	private Integer pmsn_setting;
	private Timestamp chk_time;
	private Integer chk_status;
	
	
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
	public Timestamp getJoin_time() {
		return join_time;
	}
	public void setJoin_time(Timestamp join_time) {
		this.join_time = join_time;
	}
	public Integer getPmsn_setting() {
		return pmsn_setting;
	}
	public void setPmsn_setting(Integer pmsn_setting) {
		this.pmsn_setting = pmsn_setting;
	}
	public Timestamp getChk_time() {
		return chk_time;
	}
	public void setChk_time(Timestamp chk_time) {
		this.chk_time = chk_time;
	}
	public Integer getChk_status() {
		return chk_status;
	}
	public void setChk_status(Integer chk_status) {
		this.chk_status = chk_status;
	}
}
