package com.reply_of_msg.model;

import java.sql.Timestamp;

public class Reply_of_MSGVO {
	
	public Reply_of_MSGVO() {

	}
	public Reply_of_MSGVO(String reply_id, String msg_id, String mem_id, String reply_content, Timestamp reply_time,
			Integer reply_status) {
		super();
		this.reply_id = reply_id;
		this.msg_id = msg_id;
		this.mem_id = mem_id;
		this.reply_content = reply_content;
		this.reply_time = reply_time;
		this.reply_status = reply_status;
	}
	private String reply_id;
	private String msg_id;
	private String mem_id;
	private String reply_content;
	private Timestamp reply_time;
	private Integer reply_status;
	
	
	public String getReply_id() {
		return reply_id;
	}
	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public Timestamp getReply_time() {
		return reply_time;
	}
	public void setReply_time(Timestamp reply_time) {
		this.reply_time = reply_time;
	}
	public Integer getReply_status() {
		return reply_status;
	}
	public void setReply_status(Integer reply_status) {
		this.reply_status = reply_status;
	}
	
	
	
}
