package com.comFile.model;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface ComFileDAO_interface {
	
	public String insert(ComFileVO comFileVO);
	public List<String> getCuf_ids(String comPo_id);
	public void getCom_File(String cuf_id, HttpServletResponse res);	//	印出貼文的圖片
	public List<String> getSideFile(String com_id);
	public List<String> getAllFile(String com_id);

}
