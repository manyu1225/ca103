package com.gp.model;
import java.sql.Date;
import java.sql.Timestamp;


public class GPVO implements java.io.Serializable {
	
	public GPVO() {
		
	}



	public GPVO(String gp_id, String mem_id, String rot_id, String com_id, Timestamp cre_time, String gp_title,
			Date gp_date, Integer gp_hour, Integer gp_time, String gp_desc, String gp_content_edit, String gp_content,
			String gp_content_photo, byte[] gp_photo, Integer pub_set, Integer min_num, Integer max_num,
			Date sign_up_DD, Integer gp_status, String teamadded_QR) {
		super();
		this.gp_id = gp_id;
		this.mem_id = mem_id;
		this.rot_id = rot_id;
		this.com_id = com_id;
		this.cre_time = cre_time;
		this.gp_title = gp_title;
		this.gp_date = gp_date;
		this.gp_hour = gp_hour;
		this.gp_time = gp_time;
		this.gp_desc = gp_desc;
		this.gp_content_edit = gp_content_edit;
		this.gp_content = gp_content;
		this.gp_content_photo = gp_content_photo;
		this.gp_photo = gp_photo;
		this.pub_set = pub_set;
		this.min_num = min_num;
		this.max_num = max_num;
		this.sign_up_DD = sign_up_DD;
		this.gp_status = gp_status;
		this.teamadded_QR = teamadded_QR;
	}

	private String gp_id;
	private String mem_id;
	private String rot_id;
	private String com_id;
	private Timestamp cre_time;
	private String gp_title;
	private Date gp_date;
	private Integer gp_hour;
	private Integer gp_time;
	private String gp_desc;
	private String gp_content_edit;
	private String gp_content;
	private String gp_content_photo;
	private byte[] gp_photo;
	private Integer pub_set;
	private Integer min_num;
	private Integer max_num;
	private Date sign_up_DD;
	private Integer gp_status;
	private String teamadded_QR;
	
	
	
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
	public String getRot_id() {
		return rot_id;
	}
	public void setRot_id(String rot_id) {
		this.rot_id = rot_id;
	}
	public String getCom_id() {
		return com_id;
	}
	public void setCom_id(String com_id) {
		this.com_id = com_id;
	}
	public Timestamp getCre_time() {
		return cre_time;
	}
	public void setCre_time(Timestamp cre_time) {
		this.cre_time = cre_time;
	}
	public String getGp_title() {
		return gp_title;
	}
	public void setGp_title(String gp_title) {
		this.gp_title = gp_title;
	}
	public Date getGp_date() {
		return gp_date;
	}
	public void setGp_date(Date gp_date) {
		this.gp_date = gp_date;
	}
	public Integer getGp_hour() {
		return gp_hour;
	}
	public void setGp_hour(Integer gp_hour) {
		this.gp_hour = gp_hour;
	}
	public Integer getGp_time() {
		return gp_time;
	}
	public void setGp_time(Integer gp_time) {
		this.gp_time = gp_time;
	}
	public String getGp_desc() {
		return gp_desc;
	}
	public void setGp_desc(String gp_desc) {
		this.gp_desc = gp_desc;
	}
	public String getGp_content_edit() {
		return gp_content_edit;
	}
	public void setGp_content_edit(String gp_content_edit) {
		this.gp_content_edit = gp_content_edit;
	}
	public String getGp_content() {
		return gp_content;
	}
	public void setGp_content(String gp_content) {
		this.gp_content = gp_content;
	}
	public String getGp_content_photo() {
		return gp_content_photo;
	}

	public void setGp_content_photo(String gp_content_photo) {
		this.gp_content_photo = gp_content_photo;
	}
	public byte[] getGp_photo() {
		return gp_photo;
	}
	public void setGp_photo(byte[] gp_photo) {
		this.gp_photo = gp_photo;
	}
	public Integer getPub_set() {
		return pub_set;
	}
	public void setPub_set(Integer pub_set) {
		this.pub_set = pub_set;
	}
	public Integer getMin_num() {
		return min_num;
	}
	public void setMin_num(Integer min_num) {
		this.min_num = min_num;
	}
	public Integer getMax_num() {
		return max_num;
	}
	public void setMax_num(Integer max_num) {
		this.max_num = max_num;
	}
	public Date getSign_up_DD() {
		return sign_up_DD;
	}
	public void setSign_up_DD(Date sign_up_DD) {
		this.sign_up_DD = sign_up_DD;
	}
	public Integer getGp_status() {
		return gp_status;
	}
	public void setGp_status(Integer gp_status) {
		this.gp_status = gp_status;
	}
	public String getTeamadded_QR() {
		return teamadded_QR;
	}
	public void setTeamadded_QR(String teamadded_QR) {
		this.teamadded_QR = teamadded_QR;
	}


	
}
