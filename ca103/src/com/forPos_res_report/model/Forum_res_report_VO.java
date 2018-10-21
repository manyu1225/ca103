package com.forPos_res_report.model;

import java.sql.Timestamp;

public class Forum_res_report_VO {

	private Integer forRes_ID;
	private String mem_ID;
	private Integer forRes_rep_state;
	private String forRes_rep_reason;
	private Timestamp forRes_req_time;

	public Integer getForRes_ID() {
		return forRes_ID;
	}

	public void setForRes_ID(Integer forRes_ID) {
		this.forRes_ID = forRes_ID;
	}

	public String getMem_ID() {
		return mem_ID;
	}

	public void setMem_ID(String mem_ID) {
		this.mem_ID = mem_ID;
	}

	public Integer getForRes_rep_state() {
		return forRes_rep_state;
	}

	public void setForRes_rep_state(Integer forRes_rep_state) {
		this.forRes_rep_state = forRes_rep_state;
	}

	public String getForRes_rep_reason() {
		return forRes_rep_reason;
	}

	public void setForRes_rep_reason(String forRes_rep_reason) {
		this.forRes_rep_reason = forRes_rep_reason;
	}

	public Timestamp getForRes_req_time() {
		return forRes_req_time;
	}

	public void setForRes_req_time(Timestamp forRes_req_time) {
		this.forRes_req_time = forRes_req_time;
	}

}
