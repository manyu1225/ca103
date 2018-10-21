package com.android.mem.model;

import java.util.*;

public interface MemDAO_interface {
	public void insert(MemVO memVO);
	public void update(MemVO memVO);
	public void delete(String mem_id);
	public MemVO findByPrimarKey(String mem_ac);
//	public MemVO findByAccount(String mem_ac);
	public List<MemVO> getAll();
	
	boolean isMember(String mem_ac, String password);

}
