package com.com.model;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemVO;

public interface ComDAO_interface {
	public ComVO insert(ComVO ComVO);
	public void update(ComVO ComVO);
	public void updateIntroduction(ComVO ComVO);
	public void updateAnnouncement(ComVO ComVO);
//	public void lockCom(String Com_id);
	public void updatePostCount(ComVO ComVO);
	public void plusMemCount(ComVO ComVO);
	public void minusMemCount(ComVO ComVO);
	public ComVO getComContent(String com_id);
	public List<ComVO> getComList(String str);
	

}
