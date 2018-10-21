package com.android.activity.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Activity_Service {

	Activity_DAO_interface dao;

	public Activity_Service() {
		dao = new Activity_DAO();

	}

	

	public Activity_VO getOneAct(Integer act_ID) {

		return dao.findByPrimaryKey(act_ID);

	}
	
	
	public List<Activity_VO> getListPic(Integer act_ID) {

		return dao.findPicByPK(act_ID);

	}
	
	
	
	public Activity_VO getPosPic(Integer act_ID) {

		return dao.findPicByPK2(act_ID);

	}
	

	public List<Activity_VO> getAllAct() {

		return dao.getAll();

	}

	//SHRINK
	public byte[] servicegetImage(Integer act_ID) {
		return dao.getImage(act_ID);

	}
	
	
	public List<Activity_VO> getLatestAct(){
		return dao.getLatestAct();
		
	}

}
