package com.forPos_res_report.model;

import java.sql.Timestamp;
import java.util.List;

import com.forPos_report.model.Forum_post_report_DAO;
import com.forPos_report.model.Forum_post_report_DAO_interface;
import com.forPos_report.model.Forum_post_report_VO;

public class Forum_res_report_service {
	
Forum_res_report_DAO_interface dao;
	
	public Forum_res_report_service() {
		dao = new Forum_res_report_DAO();
	}
	
	public void insertForRestReport(Integer forRes_ID, String mem_ID, String forRes_rep_reason) {
		Forum_res_report_VO forRes_rep_VO = new Forum_res_report_VO();
		forRes_rep_VO.setForRes_ID(forRes_ID);
		forRes_rep_VO.setMem_ID(mem_ID);
		forRes_rep_VO.setForRes_rep_reason(forRes_rep_reason);
		dao.insert(forRes_rep_VO);

	}
	
	
	public List<Forum_res_report_VO> getAll() {
		return dao.getAll();
	}
	
	
	public List<Forum_res_report_VO> getResRepAll() {
		return dao.getResRepAll();
	}
	
	public List<Forum_res_report_VO> getByStatus(String forRes_Rep_State) {
		return dao.getByStatus(forRes_Rep_State);
	}
	
	//************************************************************************
	public void close(Integer forRes_ID, Integer forRes_rep_state) {
		dao.closeRes(forRes_ID, forRes_rep_state);
	}
}