package com.forPos_rat.model;

public class ForPost_rat_Service {
	private ForPost_rat_DAO_interface dao;
	
	public ForPost_rat_Service() {
		
		dao = new ForPost_rat_DAO();
	}
	
	
	public ForPost_rat_VO addForPosRat(Integer forPost_ID, String mem_id, Integer pos_rat_ID, Integer neg_rat_ID ) {
		ForPost_rat_VO forPost_rat_VO = new ForPost_rat_VO();
		forPost_rat_VO.setForPost_ID(forPost_ID);
		forPost_rat_VO.setMem_ID(mem_id);
		forPost_rat_VO.setPos_rat_ID(pos_rat_ID);
		forPost_rat_VO.setNeg_rat_ID(neg_rat_ID);
		dao.insert(forPost_rat_VO);
		return forPost_rat_VO;
	}
	
	
	public void deleteforPosRat(Integer forPost_rat_ID) {
		dao.delete(forPost_rat_ID);
	}
	
	
	
	

}
