package com.forPos_res_report.model;

import java.util.List;

public interface Forum_res_report_DAO_interface {
	public void insert(Forum_res_report_VO forRes_rep_VO);
	public void update(Forum_res_report_VO forRes_rep_VO);
	public void delete(Integer forRes_ID, String mem_ID);
	public Forum_res_report_VO findByPrimaryKey(Integer forRes_ID, String mem_ID);
	public List<Forum_res_report_VO> getAll();
	//********************************************
	public List<Forum_res_report_VO> getResRepAll();
	public List<Forum_res_report_VO> getByStatus(String forPos_rep_state);
	public void closeRes(Integer forRes_ID, Integer forRes_rep_state);
}
