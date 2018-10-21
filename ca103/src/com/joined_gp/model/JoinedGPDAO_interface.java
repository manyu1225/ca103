package com.joined_gp.model;

import java.util.List;

import com.gp.model.GPVO;
import com.mem.model.MemVO;

public interface JoinedGPDAO_interface {
	public void add(JoinedGPVO jgpVO);//先判斷人數是否足夠
	public void delete(JoinedGPVO jgpVO);
	public void updateChk(JoinedGPVO jgpVO);//變更報到時間、報到狀態(報到)
	public void updateStatus(JoinedGPVO jgpVO);//變更狀態(新增、移除管理員)
	public int findNumberOfGP(GPVO gpVO);
	public List<MemVO> SearchMemberByGP(GPVO gpVO);
	public JoinedGPVO SearchjGPVOByPK(MemVO memVO,GPVO gpVO);
	public List<GPVO> SearchJoinedGPByMember(MemVO memberVO);//查一個會員的揪團by會員名稱
	public List<GPVO> SearchPublicJGP(MemVO memberVO);//用來判斷是否加入用
}