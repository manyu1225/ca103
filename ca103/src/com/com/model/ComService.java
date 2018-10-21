package com.com.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemVO;

public class ComService {

	private ComDAO_interface dao;

	public ComService() {
		dao = new ComDAO();
	}

	public ComVO addCom(String mem_id, String com_name, byte[] cover_image, Integer privacy, String announcement,
			String introduction, Date create_time, Integer com_status, Integer mem_count) {
		
		ComVO comVO = new ComVO();

		comVO.setMem_id(mem_id);
		comVO.setCom_name(com_name);
//		comVO.setCover_image(cover_image);		//晚點由update處設定
		comVO.setPrivacy(privacy);
//		comVO.setAnnouncement(announcement);	//晚點由update處設定
//		comVO.setIntroduction(introduction);	//晚點由update處設定
//		comVO.setCom_status(com_status);		//com_status已由datebase預設為0
		
		comVO = dao.insert(comVO);	
		//準備insert進database的comVO，
		//內含由user輸入的資料與自身會員編號
		
		
		
		return comVO;	
		//回傳資料與準備insert進database的comVO一致，不是完整的comVO
		//完整的comVO是由getComList和findByPrimaryKey，從database撈出來的
	}
	
	public ComVO updateCom(String com_id, String mem_id, String com_name, byte[] cover_image, Integer privacy,
			String announcement, String introduction) {

		ComVO comVO = new ComVO();

		comVO.setCom_id(com_id);
		comVO.setCom_name(com_name);
		comVO.setCover_image(cover_image);
		comVO.setPrivacy(privacy);
		comVO.setAnnouncement(announcement);
		comVO.setIntroduction(introduction);

		dao.update(comVO);

		return comVO;
	}
	
	public void updateIntroduction(String com_id, String introduction) {
		
		ComVO comVO = new ComVO();
		comVO.setCom_id(com_id);
		comVO.setIntroduction(introduction);

		dao.updateIntroduction(comVO);

	}
	
	public void updateAnnouncement(String com_id, String announcement) {
		
		ComVO comVO = new ComVO();
		comVO.setCom_id(com_id);
		comVO.setAnnouncement(announcement);

		dao.updateAnnouncement(comVO);

	}
	
	public ComVO updatePostCount(String com_id) {

		ComVO comVO = new ComVO();

		comVO.setCom_id(com_id);
		dao.updatePostCount(comVO);

		return comVO;
	}
	
	public ComVO plusMemCount(String com_id) {

		ComVO comVO = new ComVO();

		comVO.setCom_id(com_id);
		dao.plusMemCount(comVO);

		return comVO;
	}
	
	public ComVO minusMemCount(String com_id) {

		ComVO comVO = new ComVO();

		comVO.setCom_id(com_id);
		dao.minusMemCount(comVO);

		return comVO;
	}

	public ComVO getComContent(String com_id) {
		return dao.getComContent(com_id);
	}

	public List<ComVO> getComList(String sql) {
		return dao.getComList(sql);
	}
	
}
