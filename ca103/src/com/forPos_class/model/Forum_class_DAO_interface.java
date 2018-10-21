package com.forPos_class.model;

import java.util.List;

public interface Forum_class_DAO_interface {
	
	public void insert(Forum_class_VO forClass_VO);
	public void delete(Integer forClass_ID);
	public void update(Forum_class_VO forClass_VO);
	public Forum_class_VO findByPrimaryKey(String forClass_ID);
	public List<Forum_class_VO> getAll();
	

}
