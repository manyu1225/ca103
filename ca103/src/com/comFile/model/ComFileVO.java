package com.comFile.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Timestamp;

public class ComFileVO implements Serializable {
	private String cuf_id;
	private String com_id;
	private String mem_id;
	private String comPo_id;
	private String cuf;
	private Timestamp cuf_time;
	private String album;
	
	public String getCuf_id() {
		return cuf_id;
	}
	public void setCuf_id(String cuf_id) {
		this.cuf_id = cuf_id;
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
	public String getComPo_id() {
		return comPo_id;
	}
	public void setComPo_id(String comPo_id) {
		this.comPo_id = comPo_id;
	}
	public String getCuf() {
		return cuf;
	}
	public void setCuf(String cuf) {
		this.cuf = cuf;
	}
	public Timestamp getCuf_time() {
		return cuf_time;
	}
	public void setCuf_time(Timestamp cuf_time) {
		this.cuf_time = cuf_time;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	
}
