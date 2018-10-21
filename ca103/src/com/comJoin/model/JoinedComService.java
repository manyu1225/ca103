package com.comJoin.model;

import java.util.List;

import com.com.model.*;
import com.mem.model.*;

public class JoinedComService {
	JoinedComDAO_interface dao;
	
	public JoinedComService() {
		dao = new JoinedComDAO();
	}
	
	public void joinCom(String com_id, String mem_id, Integer pm_setting, Integer available) {
		
		JoinedComVO joinedComVO = new JoinedComVO();
		
		joinedComVO.setCom_id(com_id);
		joinedComVO.setMem_id(mem_id);
		joinedComVO.setPm_setting(pm_setting);
		joinedComVO.setAvailable(available);
		
		dao.add(joinedComVO);
		
	}
	
	public JoinedComVO getOneForCheck(String com_id, String mem_id) {
		
		JoinedComVO joinedComVO = new JoinedComVO();
		joinedComVO.setCom_id(com_id);
		joinedComVO.setMem_id(mem_id);
		
		return dao.getOneForCheck(joinedComVO);
	}
	
	public List<JoinedComVO> getWaitForCheckList(String com_id) {
		return dao.getWaitForCheckList(com_id);
	}
	
	public void joinCom_OK(String com_id, String mem_id, Integer pm_setting, Integer available) {
		
		JoinedComVO joinedComVO = new JoinedComVO();
		
		joinedComVO.setCom_id(com_id);
		joinedComVO.setMem_id(mem_id);
		joinedComVO.setPm_setting(pm_setting);
		joinedComVO.setAvailable(available);
		
		dao.add_OK(joinedComVO);
		
	}
	
	public void exitCom(String com_id, String mem_id) {
		
		JoinedComVO joinedComVO = new JoinedComVO();
		
		joinedComVO.setCom_id(com_id);
		joinedComVO.setMem_id(mem_id);
		
		dao.exitCom(joinedComVO);
		
	}

	public List<ComVO> getJoinedComList(String mem_id) {
		return dao.getJoinedComList(mem_id);
	}
	
	public List<ComVO> getCreatedComList(String mem_id) {
		return dao.getCreatedComList(mem_id);
	}
	
	public List<MemVO> getComMemberList(String mem_id) {
		return dao.getComMemberList(mem_id);
	}
	
	public MemVO getMemVO(String comPo_id){
		return dao.getMemVO(comPo_id);
	}
}
