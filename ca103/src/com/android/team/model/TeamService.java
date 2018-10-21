package com.android.team.model;

import java.util.List;

public class TeamService {
	
	private TeamDAO_interface teamDAO = null;
	
	public TeamService() {
		
		teamDAO = new TeamDAO();	
	}
	
	public List<TeamVO>  serviceGetOneByTeamId(String gp_id) {
		
		return  teamDAO.getByTeamId(gp_id);
	}
	
	public List<TeamVO> serviceGetAll() {
		return teamDAO.getAll();
	}
	
	public List<TeamVO> serviceGetAllByMemId (String mem_id) {
		return teamDAO.getByMemId(mem_id);
	}
	

	
	public byte[]  servicegetImage(String gp_id) {
		
		return teamDAO.getImage(gp_id);
	}
	
//	public TeamVO serviceGetAllBygp_id (String gp_id) {
//		return teamDAO.getBygp_id(gp_id);
//	}
}
