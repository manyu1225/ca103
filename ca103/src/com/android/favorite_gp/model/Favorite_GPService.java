package com.android.favorite_gp.model;

import java.util.List;

import com.android.mem.model.MemVO;
import com.android.team.model.TeamVO;


public class Favorite_GPService {
	
	private Favorite_GPDAO_interface dao;
	
	public Favorite_GPService() {
		dao = new Favorite_GPJDBCDAO();
	}
	
	
	
//	
	public List<TeamVO> searchFavGP(String mem_id){
		MemVO memVO = new MemVO();
		memVO.setMem_id(mem_id);
		return dao.searchFavGP(memVO);
	}
	
	
	
	
	
	public void addFavGP(Favorite_GPVO favGPVO) {
		dao.addFavGP(favGPVO);
	}
	
	
	public void deleteFav_GP(Favorite_GPVO favGPVO) {
		dao.deleteFav_GP(favGPVO);
	}
	
	
	
	public List<TeamVO> searchPubfGP(MemVO memVO){
		return dao.searchPubfGP(memVO);
	}
	
	public boolean isFavGP(MemVO memVO,String gp_id){

		List<TeamVO> fgpList = dao.searchFavGP(memVO);
		for(TeamVO fGPVO : fgpList) {
			if(fGPVO.getGp_id().equals(gp_id)) {
				return true;
			}
		}
		
		return false;
	}
}
