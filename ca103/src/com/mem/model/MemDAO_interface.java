package com.mem.model;

import java.util.*;

public interface MemDAO_interface {
	public void insert(MemVO memVO);
	public void update(MemVO memVO);
	public void updateByMaster(MemVO memVO);
	public void delete(String mem_id);
	public MemVO findByPrimarKey(String mem_ac);
	public MemVO findMemById(String mem_id);
//	public MemVO findByAccount(String mem_ac);
	public List<MemVO> getAll();
	public void status_set(Integer mem_status,String mem_ac);
	
}
