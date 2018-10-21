package com.forPos_res.model;

import java.util.List;

public class Forum_response_Service {
	
	private Forum_response_DAO_interface dao;

	public Forum_response_Service() {
		 dao = new Forum_response_DAO();
	}
	
	Forum_response_VO forRes_VO = new Forum_response_VO();
	
	public void addForRes(Integer forPost_ID, String mem_ID, String forRes_content, Integer forRes_rating ) {
		forRes_VO.setForPost_ID(forPost_ID);
		forRes_VO.setMem_ID(mem_ID);
		forRes_VO.setForRes_content(forRes_content);
		forRes_VO.setForRes_rating(forRes_rating);
		
		dao.insert(forRes_VO);
		
		
	}
	
	
	public void deleteForRes(Integer forRes_ID) {
		
		dao.delete(forRes_ID);
		
	}
	
	public Forum_response_VO updateForRes(String forRes_content) {
		forRes_VO.setForRes_content(forRes_content);
		dao.update(forRes_VO);
		return forRes_VO;
	}
	
	
	
	public Forum_response_VO getOneForRes_by_ID(Integer forRes_ID) {
				
		
		return dao.findByPrimaryKey(forRes_ID);
		
		
	}
	
	
	
	public List<Forum_response_VO> getAllForRes() {
		
		return dao.getAll();
		 
		
		
	}
	
	public List<Forum_response_VO>getResByPost_ID(Integer forPost_ID){
		
		return dao.findByPost_ID(forPost_ID);
		
		
		
	}
	
	public void closeforRes(int forRes_ID, int forRes_rep_state) {
		dao.closeforRes(forRes_ID, forRes_rep_state);
	}
	

	
	
	

}
