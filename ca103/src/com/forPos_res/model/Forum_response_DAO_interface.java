package com.forPos_res.model;

import java.util.List;

public interface Forum_response_DAO_interface {
	public void insert(Forum_response_VO forRes_VO );
	public void update(Forum_response_VO forRes_VO);
	public void delete(Integer forRes_ID);
	public Forum_response_VO findByPrimaryKey(Integer forRes_ID);
	public List<Forum_response_VO> findByPost_ID(Integer forPost_ID);

	public List<Forum_response_VO> getAll();
	public void closeforRes(Integer forRes_ID, Integer forRes_rep_state);

}
