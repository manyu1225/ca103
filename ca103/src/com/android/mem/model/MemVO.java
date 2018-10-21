package com.android.mem.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

public class MemVO  implements Serializable{
	private String mem_id;
	private String mem_ac;
	private String mem_password;
	private String mem_firstname;
	private String mem_lastname;
	private String mem_tel;
	private String mem_phone;
	private String mem_email;
	private Integer mem_status;
	private byte[] mem_photo;
	private byte[] mem_cart_photo;
	private String mem_cart_type;
	private String mem_nickname;
	private Date mem_birthday;
	private Integer mem_rot_badtimes =0;
	private Integer mem_act_badtimes =0;
	private Integer mem_gru_badtimes =0;
	private Integer mem_post_badtimes=0;
	private Integer mem_sale_badtimes=0;
	private String mem_aboutme;
	
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public String getMem_ac() {
		return mem_ac;
	}
	public void setMem_ac(String mem_ac) {
		this.mem_ac = mem_ac;
	}
	public String getMem_password() {
		return mem_password;
	}
	public void setMem_password(String mem_password) {
		this.mem_password = mem_password;
	}
	public String getMem_firstname() {
		return mem_firstname;
	}
	public void setMem_firstname(String mem_firstname) {
		this.mem_firstname = mem_firstname;
	}
	public String getMem_lastname() {
		return mem_lastname;
	}
	public void setMem_lastname(String mem_lastname) {
		this.mem_lastname = mem_lastname;
	}
	public String getMem_tel() {
		return mem_tel;
	}
	public void setMem_tel(String mem_tel) {
		this.mem_tel = mem_tel;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public Integer getMem_status() {
		return mem_status;
	}
	public void setMem_status(Integer mem_status) {
		this.mem_status = mem_status;
	}
	public byte[] getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(byte[] mem_photo) {
		this.mem_photo = mem_photo;
	}
	public byte[] getMem_cart_photo() {
		return mem_cart_photo;
	}
	public void setMem_cart_photo(byte[] mem_cart_photo) {
		this.mem_cart_photo = mem_cart_photo;
	}
	public String getMem_cart_type() {
		return mem_cart_type;
	}
	public void setMem_cart_type(String mem_cart_type) {
		this.mem_cart_type = mem_cart_type;
	}
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public String getMem_aboutme() {
		return mem_aboutme;
	}
	public void setMem_aboutme(String mem_aboutme) {
		this.mem_aboutme = mem_aboutme;
	}
	public Date getMem_birthday() {
		return mem_birthday;
	}
	public void setMem_birthday(Date mem_birthday) {
		this.mem_birthday = mem_birthday;
	}
	public Integer getMem_rot_badtimes() {
		return mem_rot_badtimes;
	}
	public void setMem_rot_badtimes(Integer mem_rot_badtimes) {
		this.mem_rot_badtimes = mem_rot_badtimes;
	}
	public Integer getMem_act_badtimes() {
		return mem_act_badtimes;
	}
	public void setMem_act_badtimes(Integer mem_act_badtimes) {
		this.mem_act_badtimes = mem_act_badtimes;
	}
	public Integer getMem_gru_badtimes() {
		return mem_gru_badtimes;
	}
	public void setMem_gru_badtimes(Integer mem_gru_badtimes) {
		this.mem_gru_badtimes = mem_gru_badtimes;
	}
	public Integer getMem_post_badtimes() {
		return mem_post_badtimes;
	}
	public void setMem_post_badtimes(Integer mem_post_badtimes) {
		this.mem_post_badtimes = mem_post_badtimes;
	}
	public Integer getMem_sale_badtimes() {
		return mem_sale_badtimes;
	}
	public void setMem_sale_badtimes(Integer mem_sale_badtimes) {
		this.mem_sale_badtimes = mem_sale_badtimes;
	}
	
	
	
	
	
	
	

	
	
	
}
