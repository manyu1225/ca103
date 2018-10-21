package com.forPos_report.model;

import java.util.List;

public interface Forum_post_report_DAO_interface {
	public void insert(Forum_post_report_VO forPost_rep_VO);
	public void update(Forum_post_report_VO forPost_rep_VO);
	public void delete(Integer forPost_ID, String mem_ID);
	public Forum_post_report_VO findByPrimaryKey(Integer forPost_ID, String mem_ID);
	public List<Forum_post_report_VO> getALL();
	
	//************************************************
	public List<Forum_post_report_VO> getByStatus(String forPost_rep_states);
	public void closePos(Integer forPost_ID, Integer forPost_rep_states);
	//***************************************************************

}
