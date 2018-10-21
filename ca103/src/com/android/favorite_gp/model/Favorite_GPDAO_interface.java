package com.android.favorite_gp.model;


import java.util.List;

import com.android.mem.model.MemVO;
import com.android.team.model.TeamVO;




public interface Favorite_GPDAO_interface {
	

	public List<TeamVO> searchFavGP(MemVO memVO);
	
	public void addFavGP(Favorite_GPVO favGPVO);
	
	//
	public void deleteFav_GP(Favorite_GPVO favGPVO);
	
	public List<TeamVO> searchPubfGP(MemVO memVO);
	

	
}
