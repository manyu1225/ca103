package com.android.joined_gp.model;

import java.util.List;

import com.android.mem.model.MemVO;
import com.android.team.model.TeamVO;



public class JoinedGPService {
	private JoinedGPDAO_interface dao;
	
	public JoinedGPService() {
		dao = new JoinedGPJDBCDAO();
	}
	
	public List<TeamVO> SearchJoinedGPByMember(String mem_id){
		MemVO memVO = new MemVO();
		memVO.setMem_id(mem_id);
		return dao.SearchJoinedGPByMember(memVO);
	}
	
	
	
	
	
	
	
	
	
	
	public void joinGP(JoinedGPVO jGPVO) {
		dao.add(jGPVO);
	}
	public void delete(JoinedGPVO jgpVO) {
		dao.delete(jgpVO);
	}
	
	public int findNumberOfGP(TeamVO gpVO) {
		return dao.findNumberOfGP(gpVO);
	}
	
	
	
	public boolean isJoined(MemVO memberVO,String gp_id){
		List<TeamVO> jgpList = dao.SearchJoinedGPByMember(memberVO);
		for(TeamVO jgpVO : jgpList) {
			if(jgpVO.getGp_id().equals(gp_id)) {
				return true;
			}
		}
		return false;
	}
	
	public List<MemVO> SearchMemberByGP(TeamVO gpVO){
		return dao.SearchMemberByGP(gpVO);
	}
	
	public JoinedGPVO SearchjGPVOByPK(MemVO memVO,TeamVO gpVO) {
		return dao.SearchjGPVOByPK(memVO, gpVO);
	}
	
	public void updateStatus(JoinedGPVO jGPVO) {
		dao.updateStatus(jGPVO);
	}
}
