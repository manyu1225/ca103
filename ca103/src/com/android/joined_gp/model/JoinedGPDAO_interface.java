package com.android.joined_gp.model;

import java.util.List;

import com.android.mem.model.MemVO;
import com.android.team.model.TeamVO;



public interface JoinedGPDAO_interface {
	public void add(JoinedGPVO jgpVO);//���鈭箸��頞喳��
	public void delete(JoinedGPVO jgpVO);
	public void updateChk(JoinedGPVO jgpVO);//霈������������(��)
	public void updateStatus(JoinedGPVO jgpVO);//霈�����(�憓�宏�蝞∠�)
	public int findNumberOfGP(TeamVO gpVO);
	public List<MemVO> SearchMemberByGP(TeamVO gpVO);
		public JoinedGPVO SearchjGPVOByPK(MemVO memVO,TeamVO gpVO);
		public List<TeamVO> SearchPublicJGP(MemVO memberVO);//�靘������


		
public List<TeamVO> SearchJoinedGPByMember(MemVO memberVO);//�銝�������y����迂
	

}