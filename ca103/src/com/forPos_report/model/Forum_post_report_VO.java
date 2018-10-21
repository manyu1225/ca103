package com.forPos_report.model;

import java.sql.Timestamp;

public class Forum_post_report_VO implements java.io.Serializable {

	private Integer forPost_ID;
	private String mem_ID;
	private Timestamp forPos_rep_time;
	private Integer forPos_rep_state;
	private String forPos_rep_reason;

	public Forum_post_report_VO() {

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

	public Timestamp getForPos_rep_time() {
		return forPos_rep_time;
	}

	public void setForPos_rep_time(Timestamp forPos_rep_time) {
		this.forPos_rep_time = forPos_rep_time;
	}

	public Integer getForPos_rep_state() {
		return forPos_rep_state;
	}

	public void setForPos_rep_state(Integer forPos_rep_state) {
		this.forPos_rep_state = forPos_rep_state;
	}

	public String getForPost_rep_reason() {
		return forPos_rep_reason;
	}

	public void setForPost_rep_reason(String forPos_rep_reason) {
		this.forPos_rep_reason = forPos_rep_reason;
	}

}
