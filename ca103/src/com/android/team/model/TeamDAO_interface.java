package com.android.team.model;

import java.util.List;

import com.android.mem.model.MemVO;


public interface TeamDAO_interface {
//	public TeamVO getByStatus (int status);
//	private static final String GETONEBYSTATUS = "select * from GP where GP_STATUS = 0 ";
	public List<TeamVO> getByTeamId(String gp_id);
//	private static final String GETALLBYPK = "select * from GP where GP_ID=?";
	public List<TeamVO> getByMemId (String mem_id);
//private static final String GETONEBYMEM = "select * from GP where MEM_ID = ?"; 
	public List<TeamVO>  getAll();
//	private static final String GETALLTEAM= "select * from GP";
	public byte[] getImage(String gp_id);
	
//	public TeamVO getBygp_id (String gp_id);

	
}
