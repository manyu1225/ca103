package com.favorite_gp.model;

import java.util.List;

import com.gp.model.GPVO;
import com.mem.model.MemVO;

public interface Favorite_GPDAO_interface {
	public List<GPVO> searchFavGP(MemVO memVO);
	public void addFavGP(Favorite_GPVO favGPVO);
	public void deleteFav_GP(Favorite_GPVO favGPVO);
	
	public List<GPVO> searchPubfGP(MemVO memVO);
}
