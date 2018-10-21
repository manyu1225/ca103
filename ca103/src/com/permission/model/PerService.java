package com.permission.model;

import java.util.List;

public class PerService {
	private PerService dao;
	
	public PerService() {
		
	}
	
	public void insert(PerVO perVO) {
		dao.insert(perVO);
	}
	
	public PerVO findByPrimarKey(String permission_id) {
		return dao.findByPrimarKey(permission_id);
	}
	
	public List<PerVO> getAll(){
		return dao.getAll();
	}
}
