package com.activity.model;

import java.util.List;
import java.util.Map;

public interface Activity_DAO_interface {
	
	public void insert(Activity_VO activity_VO);
	public void update(Activity_VO activity_VO);
	public void delete(Integer act_ID);
	public Activity_VO findByPrimaryKey(Integer act_ID);
	public List<Activity_VO> getAll();
	public List<Activity_VO> findPicByPK(Integer act_ID);
	public Activity_VO findPicByPK2(Integer act_ID);
//********複合查詢************
	public List<Activity_VO> getAll(Map<String, String[]> map);
	//更新點擊人數
	public void updateActView(Activity_VO activity_VO);
	//查詢熱門活動
	public List<Activity_VO> getPopAct();
	//查詢最新活動
	public List<Activity_VO> getLatestAct();
//	public List<Activity_VO> findByaAllCondition();

		
		

}
