package com.forPos.model;

import java.sql.Timestamp;

public class Forum_post_VO implements java.io.Serializable {

	private Integer forPost_ID;
	private String mem_ID;
	private String forClass_ID;
	private Timestamp forPost_time;
	private Integer forPost_view;
	private String forPost_content;
	private String forPost_theme;
	private Integer forPost_state;

	public Forum_post_VO() {
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

	public String getForClass_ID() {
		return forClass_ID;
	}

	public void setForClass_ID(String forClass_ID) {
		this.forClass_ID = forClass_ID;
	}

	public Timestamp getForPost_time() {
		return forPost_time;
	}

	public void setForPost_time(Timestamp forPost_time) {
		this.forPost_time = forPost_time;
	}

	public Integer getForPost_view() {
		return forPost_view;
	}

	public void setForPost_view(Integer forPost_view) {
		this.forPost_view = forPost_view;
	}

	public String getForPost_content() {
		return forPost_content;
	}

	public void setForPost_content(String forPost_content) {
		this.forPost_content = forPost_content;
	}

	public String getForPost_theme() {
		return forPost_theme;
	}

	public void setForPost_theme(String forPost_theme) {
		this.forPost_theme = forPost_theme;
	}

	public Integer getForPost_state() {
		return forPost_state;
	}

	public void setForPost_state(Integer forPost_state) {
		this.forPost_state = forPost_state;
	}

	@Override
	public String toString() {
		return "Forum_post_VO [forPost_ID=" + forPost_ID + ", mem_ID=" + mem_ID + ", forClass_ID=" + forClass_ID
				+ ", forPost_time=" + forPost_time + ", forPost_view=" + forPost_view + ", forPost_content="
				+ forPost_content + ", forPost_theme=" + forPost_theme + ", forPost_state=" + forPost_state + "]";
	}

}
