package com.act_analysis.model;

import java.util.List;

public interface ACT_ANALYSIS_DAO_interface {
	public void insert(ACT_ANALYSIS_VO act_ANALYSIS_VO);
	public void delete(Integer act_analysis_ID );
	public void update(ACT_ANALYSIS_VO act_ANALYSIS_VO);
	public ACT_ANALYSIS_VO findPrimaryKey(Integer act_analysis_ID);
	public List<ACT_ANALYSIS_VO> getAll();
}
