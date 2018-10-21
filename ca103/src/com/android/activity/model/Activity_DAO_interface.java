package com.android.activity.model;

import java.util.List;
import java.util.Map;

public interface Activity_DAO_interface {
	

	public Activity_VO findByPrimaryKey(Integer act_ID);
	public List<Activity_VO> getAll();
	
	public List<Activity_VO> findPicByPK(Integer act_ID);
	
	public Activity_VO findPicByPK2(Integer act_ID);
	
	//查詢最新活動
	public List<Activity_VO> getLatestAct();
		
	byte[] getImage(Integer act_ID);


}
