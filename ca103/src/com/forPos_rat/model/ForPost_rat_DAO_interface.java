package com.forPos_rat.model;

import java.util.List;

public interface ForPost_rat_DAO_interface {

	public void insert(ForPost_rat_VO forPost_rat_VO);
	public void update(ForPost_rat_VO forPost_rat_VO);
	public void delete(Integer forPost_rat_ID);
	public ForPost_rat_VO findByPrimaryKey(Integer forPost_rat_ID);
	public List<ForPost_rat_VO> getAll();
	
}
