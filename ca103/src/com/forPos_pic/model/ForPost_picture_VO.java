package com.forPos_pic.model;

import java.io.Serializable;

import oracle.sql.BLOB;

public class ForPost_picture_VO implements Serializable {

	private Integer forPostPic_ID;
	private Integer	forPost_ID;
	private byte[] forPostPic;
	
	public ForPost_picture_VO()  {
	}

	public Integer getForPostPic_ID() {
		return forPostPic_ID;
	}

	public void setForPostPic_ID(Integer forPostPic_ID) {
		this.forPostPic_ID = forPostPic_ID;
	}

	public Integer getForPost_ID() {
		return forPost_ID;
	}

	public void setForPost_ID(Integer forPost_ID) {
		this.forPost_ID = forPost_ID;
	}

	public byte[] getForPostPic() {
		return forPostPic;
	}

	public void setForPostPic(byte[] forPostPic) {
		this.forPostPic = forPostPic;
	}

	
	
}
