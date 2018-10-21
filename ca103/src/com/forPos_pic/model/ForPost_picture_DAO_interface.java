package com.forPos_pic.model;

import java.sql.Connection;
import java.util.List;

import com.activity.model.Activity_VO;

public interface ForPost_picture_DAO_interface  {
	
	public void insert(ForPost_picture_VO forPos_pic_VO);
	public void insert(ForPost_picture_VO forPos_pic_VO, Connection con);
	public void update(ForPost_picture_VO forPos_pic_VO );
	public void delete(Integer forPostPic_ID);
	public ForPost_picture_VO findByPrimaryKey(Integer forPostPic_ID);
	public List<ForPost_picture_VO> getAll();
	public List<ForPost_picture_VO> findAllPicByPK(Integer act_ID);
	public ForPost_picture_VO findOnePicByPK(Integer forPostPic_ID);

	

}
