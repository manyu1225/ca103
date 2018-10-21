package com.com.model;

import java.io.Serializable;
import java.sql.Date;



public class ComVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String com_id;
	private String mem_id;
	private String com_name;
	private byte[] cover_image;
	private Integer privacy;
	private String announcement;
	private String introduction;
	private Date create_time;
	private Integer com_status;
	private Integer post_count;
	private Integer mem_count;
	
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
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public byte[] getCover_image() {
		return cover_image;
	}
	public void setCover_image(byte[] cover_image) {
		this.cover_image = cover_image;
	}
	public Integer getPrivacy() {
		return privacy;
	}
	public void setPrivacy(Integer privacy) {
		this.privacy = privacy;
	}
	public String getAnnouncement() {
		return announcement;
	}
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Integer getCom_status() {
		return com_status;
	}
	public void setCom_status(Integer com_status) {
		this.com_status = com_status;
	}
	public Integer getPost_count() {
		return post_count;
	}
	public void setPost_count(Integer post_count) {
		this.post_count = post_count;
	}	
	public Integer getMem_count() {
		return mem_count;
	}
	public void setMem_count(Integer mem_count) {
		this.mem_count = mem_count;
	}	
}
