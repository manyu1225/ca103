package com.android.emp.model;

import java.io.Serializable;

public class EmpMemVO  implements Serializable{
	private String emp_id;
	private String emp_accound;
	private String emp_password;
	private String emp_firstname;
	private String emp_lastname;
	private Integer emp_pr;
	private String emp_ad;
	private String emp_email;
	
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_accound() {
		return emp_accound;
	}
	public void setEmp_accound(String emp_accound) {
		this.emp_accound = emp_accound;
	}
	public String getEmp_password() {
		return emp_password;
	}
	public void setEmp_password(String emp_password) {
		this.emp_password = emp_password;
	}
	public String getEmp_firstname() {
		return emp_firstname;
	}
	public void setEmp_firstname(String emp_firstname) {
		this.emp_firstname = emp_firstname;
	}
	public String getEmp_lastname() {
		return emp_lastname;
	}
	public void setEmp_lastname(String emp_lastname) {
		this.emp_lastname = emp_lastname;
	}
	public Integer getEmp_pr() {
		return emp_pr;
	}
	public void setEmp_pr(Integer emp_pr) {
		this.emp_pr = emp_pr;
	}
	public String getEmp_ad() {
		return emp_ad;
	}
	public void setEmp_ad(String emp_ad) {
		this.emp_ad = emp_ad;
	}
	public String getEmp_email() {
		return emp_email;
	}
	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}
	
	
	
}
