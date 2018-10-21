package com.android.activity.model;

import java.io.Serializable;
import java.sql.Date;


public class Activity_VO implements Serializable{
	
	private Integer act_ID;
	private String act_name;
	private Date act_sdate;
	private Date act_edate;
	private Date act_regis_date;
	private String act_info;
	private String act_holder;
	private String cusName;
	private String cusMail;
	private String rot_id;
	private String act_href;
	private String act_href2;
	private byte[] act_pic;
	private String emp_id;
	private Integer act_explo;
	private Integer act_click;
	private Integer act_region_north;
	private Integer act_region_center;
	private Integer act_region_south;
	private Integer act_region_east;
	private Integer act_region_off;
	private Integer act_state;
	
	public Integer getAct_ID() {
		return act_ID;
	}

	public void setAct_ID(Integer act_ID) {
		this.act_ID = act_ID;
	}

	public String getAct_name() {
		return act_name;
	}

	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}

	public Date getAct_sdate() {
		return act_sdate;
	}

	public void setAct_sdate(Date act_sdate) {
		this.act_sdate = act_sdate;
	}
	
	public Date getAct_edate() {
		return act_edate;
	}

	public void setAct_edate(Date act_edate) {
		this.act_edate = act_edate;
	}

	public Date getAct_regis_date() {
		return act_regis_date;
	}

	public void setAct_regis_date(Date act_regis_date) {
		this.act_regis_date = act_regis_date;
	}

	public String getAct_info() {
		return act_info;
	}

	public void setAct_info(String act_info) {
		this.act_info = act_info;
	}

	public String getAct_holder() {
		return act_holder;
	}

	public void setAct_holder(String act_holder) {
		this.act_holder = act_holder;
	}
	
	

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getCusMail() {
		return cusMail;
	}

	public void setCusMail(String cusMail) {
		this.cusMail = cusMail;
	}

	public String getRot_id() {
		return rot_id;
	}

	public void setRot_id(String rot_id) {
		this.rot_id = rot_id;
	}

	public String getAct_href() {
		return act_href;
	}

	public void setAct_href(String act_href) {
		this.act_href = act_href;
	}
	
	
	
	public String getAct_href2() {
		return act_href2;
	}

	public void setAct_href2(String act_href2) {
		this.act_href2 = act_href2;
	}

	public byte[] getAct_pic() {
		return act_pic;
	}

	public void setAct_pic(byte[] act_pic) {
		this.act_pic = act_pic;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public Integer getAct_explo() {
		return act_explo;
	}

	public void setAct_explo(Integer act_explo) {
		this.act_explo = act_explo;
	}

	public Integer getAct_click() {
		return act_click;
	}

	public void setAct_click(Integer act_click) {
		this.act_click = act_click;
	}

	public Integer getAct_region_north() {
		return act_region_north;
	}

	public void setAct_region_north(Integer act_region_north) {
		this.act_region_north = act_region_north;
	}

	public Integer getAct_region_center() {
		return act_region_center;
	}

	public void setAct_region_center(Integer act_region_center) {
		this.act_region_center = act_region_center;
	}

	public Integer getAct_region_south() {
		return act_region_south;
	}

	public void setAct_region_south(Integer act_region_south) {
		this.act_region_south = act_region_south;
	}

	public Integer getAct_region_east() {
		return act_region_east;
	}

	public void setAct_region_off(Integer act_region_east) {
		this.act_region_east = act_region_east;
	}
	
	
	public Integer getAct_region_off() {
		return act_region_off;
	}

	public void setAct_region_east(Integer act_region_off) {
		this.act_region_off = act_region_off;
	}

	public Integer getAct_state() {
		return act_state;
	}

	public void setAct_state(Integer act_state) {
		this.act_state = act_state;
	}

	
	
}
