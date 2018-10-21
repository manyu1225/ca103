package com.activity.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class Activity_Service {

	Activity_DAO_interface dao;

	public Activity_Service() {
		dao = new Activity_DAO();

	}

	public Activity_VO addAct(String act_name, Date act_sdate, Date act_edate, Date act_regis_date, String act_info,
							String act_holder, String cusName, String cusMail ,String rot_id, String act_href, String act_href2, byte[] act_pic,String emp_id,
							Integer act_state) 
	{

		Activity_VO activity_VO = new Activity_VO();

		activity_VO.setAct_name(act_name);
		activity_VO.setAct_sdate(act_sdate);
		activity_VO.setAct_edate(act_edate);
		activity_VO.setAct_regis_date(act_regis_date);
		activity_VO.setAct_info(act_info);
		activity_VO.setAct_holder(act_holder);
		activity_VO.setAct_name(act_name);
		activity_VO.setCusName(cusName);
		activity_VO.setCusMail(cusMail);
		activity_VO.setRot_id(rot_id);
		activity_VO.setAct_href(act_href);
		activity_VO.setAct_href2(act_href2);
		activity_VO.setAct_pic(act_pic);
		activity_VO.setEmp_id(emp_id);
		activity_VO.setAct_state(act_state);
		dao.insert(activity_VO);

		return activity_VO;

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
	
	public List<Activity_VO> getAllAct(Map<String, String[]> map){
		
		return dao.getAll(map);
	}
	
	
	
	
	//取得活動瀏覽人數
	public Activity_VO updateActView(Integer act_ID) {
		
		Activity_VO activity_VO = new Activity_VO();
		activity_VO.setAct_ID(act_ID);
		dao.updateActView(activity_VO);
		
		return activity_VO;
	}
	
	
//	================================
	
	
	public List<Activity_VO> getPopAct() {

		return dao.getPopAct();
		
		
	}
	
	
	public List<Activity_VO> getLatestAct(){
//		System.out.println("dao.getLatestAct()="+dao.getLatestAct().get(1));
		return dao.getLatestAct();
		
	}

}
