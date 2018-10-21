package com.forPos_report.model;

import java.sql.Timestamp;
import java.util.List;


public class Forum_post_report_service {
	Forum_post_report_DAO_interface dao;
	
	public Forum_post_report_service() {
		dao = new Forum_post_report_DAO();
	}
	
	public void insertForumPostReport(Integer forPost_ID, String mem_ID, String forPos_rep_reason) {
		Forum_post_report_VO forPosRep_VO = new Forum_post_report_VO();
		forPosRep_VO.setMem_ID(mem_ID);
		forPosRep_VO.setForPost_ID(forPost_ID);
		forPosRep_VO.setForPost_rep_reason(forPos_rep_reason);
		dao.insert(forPosRep_VO);
	}
	
	public List<Forum_post_report_VO> getAll() {
		return dao.getALL();
	}
	
	public List<Forum_post_report_VO> getByStatus(String forPos_rep_state) {
		return dao.getByStatus(forPos_rep_state);
	}
	
	public void close(Integer forPost_ID, Integer forPost_rep_states) {
		dao.closePos(forPost_ID, forPost_rep_states);
	}
}
