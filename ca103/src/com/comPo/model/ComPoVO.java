package com.comPo.model;

import java.io.Serializable;
import java.sql.Clob;
import java.sql.Timestamp;

public class ComPoVO implements Serializable {
	private String comPo_id;
	private String com_id;
	private String mem_id;
	private String cp_content;
	private Timestamp cp_time;
	private Integer cpr_count;
	private Integer cpp_count;
	private Integer cp_status;
	private Integer cp_hf;
	
	public String getComPo_id() {
		return comPo_id;
	}
	public void setComPo_id(String comPo_id) {
		this.comPo_id = comPo_id;
	}
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
	public String getCp_content() {
		return cp_content;
	}
	public void setCp_content(String cp_content) {
		this.cp_content = cp_content;
	}
	public Timestamp getCp_time() {
		return cp_time;
	}
	public void setCp_time(Timestamp cp_time) {
		this.cp_time = cp_time;
	}
	public Integer getCpr_count() {
		return cpr_count;
	}
	public void setCpr_count(Integer cpr_count) {
		this.cpr_count = cpr_count;
	}
	public Integer getCpp_count() {
		return cpp_count;
	}
	public void setCpp_count(Integer cpp_count) {
		this.cpp_count = cpp_count;
	}
	public Integer getCp_status() {
		return cp_status;
	}
	public void setCp_status(Integer cp_status) {
		this.cp_status = cp_status;
	}
	public Integer getCp_hf() {
		return cp_hf;
	}
	public void setCp_hf(Integer cp_hf) {
		this.cp_hf = cp_hf;
	}
	
}
