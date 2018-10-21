package com.gp.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.mem.model.MemVO;
import com.route.model.RouteVO;

public class GPService {
	private GPDAO_interface dao;
	public GPService() {
		dao = new GPJNDIDAO();
	}
	
	public List<GPVO> getAllGP(){
		return dao.getAllGP();
	}
	
	public void addGP(GPVO gpVO) {
		
		dao.add(gpVO);
	}
	
	public GPVO searchGPVO(GPVO gpVO) {
		return dao.searchGPVO(gpVO);
	}
	
	public void updateGPInfo(GPVO gpVO) {
		dao.updateinfo(gpVO);
	}
	public List<GPVO> searchCreGP(String mem_id){
		MemVO memVO = new MemVO();
		memVO.setMem_id(mem_id);
		return dao.searchCreGP(memVO);
	}
	
	public void updateStatus(GPVO gpVO) {
		dao.updateStatus(gpVO);
	}
	
	public List<GPVO> searchByKw(String...arrKw) {
		return dao.searchByKw(arrKw);
	}
	
	public List<GPVO> search(int maxNum) {
		return dao.search(maxNum);
	}
	
	public List<GPVO> search(Date dateStart, Date dateEnd) {
		return dao.search(dateStart, dateEnd);
	}
	
	public List<GPVO> search(RouteVO rotVO) {
		return dao.search(rotVO);
	}
	
	public List<GPVO> searchByCoondition(String searchStr) {
		return dao.searchByCoondition(searchStr);
	}
	public void updateContent(GPVO gpVO) {
		dao.updateContent(gpVO);
	}
	
	public List<GPVO> forGP_Manager(String str){
		return dao.forGP_Manager(str);
	}
	
	
	public GPVO searchGPVOById(String gp_id) {
		GPVO gpVO = new GPVO();
		gpVO.setGp_id(gp_id);
		return dao.searchGPVO(gpVO);
	}
	
	
	
	
	
}
