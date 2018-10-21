package com.comRes.model;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class ComResService {
	
	private ComResDAO_interface dao;
	
	public ComResService() {
		dao = new ComResDAO();
	}
	
	public ComResVO addComRes(String comPo_id, String mem_id, String cr_content, String cr_file, Timestamp cr_time) {
		
		ComResVO comResVO = new ComResVO();
		comResVO.setComPo_id(comPo_id);
		comResVO.setMem_id(mem_id);
		comResVO.setCr_content(cr_content);
		comResVO.setCr_file(cr_file);
		comResVO.setCr_time(cr_time);
		
		String comRes_id = dao.insert(comResVO);
		comResVO.setComRes_id(comRes_id);
		
		return comResVO;
		
	}
	
	public List<ComResVO> getComResVOs(String comPo_id) {
		return dao.getComResVOs(comPo_id);
	}
	
	public void getCom_Res_File(String comRes_id, HttpServletResponse res) {
		dao.getCom_Res_File(comRes_id, res);
	}
	
	public void crHaveFile(String comRes_id) {
		dao.crHaveFile(comRes_id);
	}
	
}
