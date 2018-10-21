package com.comRes.model;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface ComResDAO_interface {
	
	public String insert(ComResVO comResVO);
	public List<ComResVO> getComResVOs(String comPo_id);
	public void getCom_Res_File(String comRes_id, HttpServletResponse res);	//	印出回文的圖片
	public void crHaveFile(String comRes_id);
	
}
