package com.comJoin.model;

import java.util.List;

import com.com.model.ComVO;
import com.mem.model.MemVO;


public interface JoinedComDAO_interface {
	public void add(JoinedComVO joinedComVO);			//	加入社群，等待審核
	public JoinedComVO getOneForCheck(JoinedComVO joinedComVO);	//	取得會員資料，準備做審核
	public List<JoinedComVO> getWaitForCheckList(String com_id);
	public void add_OK(JoinedComVO joinedComVO);		//	加入社群，通過審核
	public void exitCom(JoinedComVO joinedComVO);		//	退出社群
	public List<ComVO> getJoinedComList(String mem_id);	//	查一個會員加入的社群
	public List<ComVO> getCreatedComList(String mem_id);	//	查一個會員加入的社群
	public List<MemVO> getComMemberList(String com_id);	//	查一個社群所加入的會員
	public int findNumberOfCom(String com_id);			//	查社群人數
	public MemVO getMemVO(String comPo_id);
	
}
