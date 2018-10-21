package com.forPos_pic.model;

import java.sql.Connection;
import java.util.List;

import com.activity.model.Activity_VO;

public class ForPost_pic_Service {
	
	private ForPost_picture_DAO_interface dao;

	public ForPost_pic_Service() {
		dao = new ForPost_picture_DAO();

	}
	
	
	public ForPost_picture_VO addPosPic(Integer forPostPic, Integer forPost_ID)

	{
		ForPost_picture_VO forPost_picture_VO = new ForPost_picture_VO();
		forPost_picture_VO.setForPostPic_ID(forPostPic);
		forPost_picture_VO.setForPost_ID(forPost_ID);
//		
		dao.insert(forPost_picture_VO);
		
		return forPost_picture_VO;

	}
	
	
	
	
//	public List<ForPost_picture_VO> getListPic(Integer forPsotPic_ID) {
//
//		return dao.findPicByPK(forPost_ID);
//
//	}

	

	
	public ForPost_picture_VO findByPrimaryKey(Integer forPost_ID) {

		return dao.findByPrimaryKey(forPost_ID);

	}
	
	///////////////////////////////////////////////////
	public void insert(ForPost_picture_VO forPos_pic_VO, Connection con) {
		
		 dao.insert(forPos_pic_VO, con);
	}
	
	
	
	
//	把圖片放在列表
	public List<ForPost_picture_VO> getListPic() {

		return dao.getAll();

	}
	
	
	
	
	
	
	
	

}
