package com.comPo.model;

import java.util.List;


public interface ComPoDAO_interface {
	
	public String insert(ComPoVO comPoVO);
	public void update(ComPoVO comPoVO);
	public void delete(ComPoVO comPoVO);
	public List<ComPoVO> getAll();
	public List<ComPoVO> getByComId(String com_id);
	public List<ComPoVO> getByComPoId(String comPo_id);
	public void cpHaveFile(String comPo_id);
	
}
