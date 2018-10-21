
package com.comFile.model;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.android.util.ImageUtil;
import com.comPo.model.ComPoVO;


public class ComFileService {
	
	private ComFileDAO_interface dao= new ComFileDAO();
	
	public ComFileService() {
		dao = new ComFileDAO();
	}
	
	public ComFileVO addComFile(String com_id,String mem_id, String comPo_id,  String cuf, Timestamp cuf_time, String album) {
	

		// 進資料庫前把圖片縮小,節省網路流量
		byte []  cuf_init = Base64.getDecoder().decode(cuf);
		byte [] cuf_ing = ImageUtil.shrink(cuf_init, 300);
		Base64.Encoder encoder = Base64.getEncoder();
		String cuf_end = encoder.encodeToString(cuf_ing);
		
		ComFileVO comFileVO = new ComFileVO();
		  
		comFileVO.setCom_id(com_id);
		comFileVO.setMem_id(mem_id);
		comFileVO.setComPo_id(comPo_id);
		comFileVO.setCuf(cuf_end);
		comFileVO.setCuf_time(cuf_time);
		comFileVO.setAlbum(album);
		
		String cuf_id = dao.insert(comFileVO);
		comFileVO.setCuf_id(cuf_id);
		
		return comFileVO;
	}
	
	public List<String> getCuf_ids(String comPo_id) {
		return dao.getCuf_ids(comPo_id);
	}
	
	public void getCom_File(String cuf_id, HttpServletResponse res) {
		dao.getCom_File(cuf_id, res);
	}
	
	public List<String> getSideFile(String com_id) {
		return dao.getSideFile(com_id);
	}
	
	public List<String> getAllFile(String com_id){
		return dao.getAllFile(com_id);
	}
	
}
