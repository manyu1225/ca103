package com.msg_of_gp.model;

import java.sql.Timestamp;

public class MSG_OF_GPVO {
	
	public MSG_OF_GPVO() {

	}
	public MSG_OF_GPVO(String msg_id, String gp_id, String mem_id, String msg_content, Timestamp msg_time,
			Integer msg_status) {
		super();
		this.msg_id = msg_id;
		this.gp_id = gp_id;
		this.mem_id = mem_id;
		this.msg_content = msg_content;
		this.msg_time = msg_time;
		this.msg_status = msg_status;
	}
	private String msg_id;
	private String gp_id;
	private String mem_id;
	private String msg_content;
	private Timestamp msg_time;
	private Integer msg_status;
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getGp_id() {
		return gp_id;
	}
	public void setGp_id(String gp_id) {
		this.gp_id = gp_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public Timestamp getMsg_time() {
		return msg_time;
	}
	public void setMsg_time(Timestamp msg_time) {
		this.msg_time = msg_time;
	}
	public Integer getMsg_status() {
		return msg_status;
	}
	public void setMsg_status(Integer msg_status) {
		this.msg_status = msg_status;
	}
	
}
