package com.joined_gp.model;

import java.util.List;

import com.gp.model.GPVO;
import com.mem.model.MemVO;

public class JoinedGPService {
	private JoinedGPDAO_interface dao;
	
	public JoinedGPService() {
		dao = new JoinedGPJNDIDAO();
	}
	
	public List<GPVO> SearchJoinedGPByMember(String mem_id){
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
	
	public int findNumberOfGP(GPVO gpVO) {
		return dao.findNumberOfGP(gpVO);
	}
	
	public List<GPVO> SearchPublicJGP(MemVO memberVO){
		return dao.SearchPublicJGP(memberVO);
	}
	
	public boolean isJoined(MemVO memberVO,String gp_id){
		List<GPVO> jgpList = dao.SearchJoinedGPByMember(memberVO);
		for(GPVO jgpVO : jgpList) {
			if(jgpVO.getGp_id().equals(gp_id)) {
				return true;
			}
		}
		return false;
	}
	
	public List<MemVO> SearchMemberByGP(GPVO gpVO){
		return dao.SearchMemberByGP(gpVO);
	}
	
	public JoinedGPVO SearchjGPVOByPK(MemVO memVO,GPVO gpVO) {
		return dao.SearchjGPVOByPK(memVO, gpVO);
	}
	
	public void updateStatus(JoinedGPVO jGPVO) {
		dao.updateStatus(jGPVO);
	}
}
