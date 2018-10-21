package com.comRes.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;

public class ComResVO implements Serializable {
	private String comRes_id;
	private String comPo_id;
	private String mem_id;
	private String cr_content;
	private String cr_file;
	private Timestamp cr_time;
	private Integer crp_count;
	private Integer cr_status;
	private Integer cr_hf;
	
	public String getComRes_id() {
		return comRes_id;
	}
	public void setComRes_id(String comRes_id) {
		this.comRes_id = comRes_id;
	}
	public String getComPo_id() {
		return comPo_id;
	}
	public void setComPo_id(String comPo_id) {
		this.comPo_id = comPo_id;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getCr_content() {
		return cr_content;
	}
	public void setCr_content(String cr_content) {
		this.cr_content = cr_content;
	}
	public String getCr_file() {
		return cr_file;
	}
	public void setCr_file(String cr_file) {
		this.cr_file = cr_file;
	}
	public Timestamp getCr_time() {
		return cr_time;
	}
	public void setCr_time(Timestamp cr_time) {
		this.cr_time = cr_time;
	}
	public Integer getCrp_count() {
		return crp_count;
	}
	public void setCrp_count(Integer crp_count) {
		this.crp_count = crp_count;
	}
	public Integer getCr_status() {
		return cr_status;
	}
	public void setCr_status(Integer cr_status) {
		this.cr_status = cr_status;
	}
	public Integer getCr_hf() {
		return cr_hf;
	}
	public void setCr_hf(Integer cr_hf) {
		this.cr_hf = cr_hf;
	}
	
}
