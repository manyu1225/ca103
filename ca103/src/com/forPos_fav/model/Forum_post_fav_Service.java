package com.forPos_fav.model;

import java.util.List;

public class Forum_post_fav_Service {
	
	Forum_post_fav_DAO_interface dao;
	
    public Forum_post_fav_Service() {
    	dao = new Forum_post_fav_DAO();
    }
	
    
    
	public Forum_post_fav_VO addFavPos(Integer forPost_ID, String mem_ID) {
		Forum_post_fav_VO fav_VO = new Forum_post_fav_VO();
		fav_VO.setForPost_ID(forPost_ID);
		fav_VO.setMem_ID(mem_ID);
		dao.insert(fav_VO);
		
		return fav_VO;
		
	}
	
	
	public Forum_post_fav_VO getOnePosFav(Integer forPost_ID, String mem_ID) {
		
		return dao.findByPrimaryKey(forPost_ID, mem_ID);
		
	}
	
	
public void deletePosFav(Integer forPost_ID, String mem_ID) {
		
		 dao.delete(forPost_ID, mem_ID);
		
	}



	
	public List<Forum_post_fav_VO> getAllPosFav(String mem_id) {
		
		return dao.getAll(mem_id);
		
	}
	
}
