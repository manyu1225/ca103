package com.forPos.model;

import java.sql.Timestamp;
import java.util.List;

import com.forPos_pic.model.ForPost_picture_VO;

public interface Forum_post_DAO_interface {
	
	public void insert(Forum_post_VO forPostVO);
	public void insertAndphoto(Forum_post_VO forPostVO , ForPost_picture_VO forPost_pictureVO);
	public void update(Forum_post_VO forPostVO); 
	public void updateView(Forum_post_VO forPostVO);
	public Forum_post_VO getPostInState(Integer forPost_ID, String mem_ID);

	
	public void delete(Integer forPost_ID); 
	public Forum_post_VO findByPrimaryKey(Integer forPost_id); 
	public List<Forum_post_VO> findPostByMem_ID(String mem_id);
//	public List<Forum_post_VO> findNewestPost(Integer forPost_ID);
	public List<Forum_post_VO>getAll();
	public List<Forum_post_VO>getAllByState(Integer forPost_state);
	
	
	public List<Forum_post_VO>getReportAll();
	
	public void closeforPos(Integer forPost_ID, Integer forPos_rep_state);
	public List <Forum_post_VO> getViewByDate(String date);
}
