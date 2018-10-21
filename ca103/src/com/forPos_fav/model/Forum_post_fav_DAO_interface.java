package com.forPos_fav.model;

import java.util.List;

public interface Forum_post_fav_DAO_interface {
	public void insert(Forum_post_fav_VO forPost_VO);
//	public void update(Forum_post_fav_VO FORPOST_VO);
	public void delete(Integer forPost_ID, String mem_ID);
	public Forum_post_fav_VO findByPrimaryKey(Integer forPost_ID, String mem_ID);
	public List<Forum_post_fav_VO> getAll(String mem_id);

}
