package com.comPo.model;

import java.sql.Timestamp;
import java.util.List;

public class ComPoService {
	private ComPoDAO_interface dao;
	
	public ComPoService() {
		dao = new ComPoDAO();
	}
	
	public ComPoVO addComPo(String com_id,String mem_id,String cp_content, Timestamp cp_time) {
		
		ComPoVO comPoVO = new ComPoVO();
		comPoVO.setCom_id(com_id);
		comPoVO.setMem_id(mem_id);
		comPoVO.setCp_content(cp_content);
		comPoVO.setCp_time(cp_time);
		
		String comPo_id = dao.insert(comPoVO);
		comPoVO.setComPo_id(comPo_id);
		
		return comPoVO;
	}
	
	public List<ComPoVO> getByComId(String com_id){
		return dao.getByComId(com_id);
	}
	
	public void cpHaveFile(String comPo_id) {
		dao.cpHaveFile(comPo_id);
	}
}
