package com.forPos.model;

import java.util.List;

import com.forPos_pic.model.ForPost_picture_VO;


public class Forum_post_Service {

	private Forum_post_DAO_interface dao;

	public Forum_post_Service() {

		dao = new Forum_post_DAO();
	}



	public Forum_post_VO addForPos( String forClass_ID, String mem_ID, 
			String forPost_content, String forPost_theme, Integer forPost_state)

	{
		Forum_post_VO forum_post_VO = new Forum_post_VO();
		forum_post_VO.setForClass_ID(forClass_ID);
		forum_post_VO.setMem_ID(mem_ID);
		forum_post_VO.setForPost_content(forPost_content);
		forum_post_VO.setForPost_theme(forPost_theme);
		forum_post_VO.setForPost_state(forPost_state);
		dao.insert(forum_post_VO);
		
		return forum_post_VO;

	}
	
	
	public Forum_post_VO updateForPos(Integer forPos_ID, String forClass_ID ,String forPost_content, 
				String forPost_theme, Integer forPost_state ) {
		
		Forum_post_VO forum_post_VO = new Forum_post_VO();
		forum_post_VO.setForPost_ID(forPos_ID);
		forum_post_VO.setForClass_ID(forClass_ID);
		forum_post_VO.setForPost_content(forPost_content);
		forum_post_VO.setForPost_theme(forPost_theme);
		forum_post_VO.setForPost_state(forPost_state);
//		forum_post_VO.setMem_ID(mem_ID);
		dao.update(forum_post_VO);
		return forum_post_VO;
		
		
	}
	
	
	

	public void deleteForPos(Integer forPost_ID) {
		dao.delete(forPost_ID);

	}

	public Forum_post_VO getOneForPos(Integer forPost_ID) {

		return dao.findByPrimaryKey(forPost_ID);

	}

	public List<Forum_post_VO> getAllForPos() {

		return dao.getAll();

	}
	
	
	
	public List<Forum_post_VO> getAllForPosByMem_id(String mem_id) {

		return dao.findPostByMem_ID(mem_id);

	}
	
	
	//更新文章狀態 給特定權限的會員
	
		public List<Forum_post_VO> getAllByState(Integer forPost_state) {
			
			return dao.getAllByState(forPost_state);

		}
		
	
	
	
	
	
	
	
	public Forum_post_VO getClobPic(Integer forPost_ID) {
		
		return dao.findByPrimaryKey(forPost_ID);
		
	}
	
	public Forum_post_VO updateViewNum(Integer forPost_ID) {
		
		Forum_post_VO forum_post_VO = new Forum_post_VO();
//		forum_post_VO.setForPost_view(forPost_view);
		forum_post_VO.setForPost_ID(forPost_ID);
		dao.updateView(forum_post_VO);
		return forum_post_VO;
		
	}
	
	
	public void closeforPos(int forPost_ID, int forPos_rep_state) {
		dao.closeforPos(forPost_ID, forPos_rep_state);
	}
	
	public List <Forum_post_VO> getViewByDate(String date){
		return dao.getViewByDate(date);
	}
	
	
	public void insertAndphoto(Forum_post_VO forPostVO, ForPost_picture_VO forPost_pictureVO) {
		dao.insertAndphoto(forPostVO, forPost_pictureVO);
	}
	
	

	
	
//	
//	public List<Forum_post_VO>getAllForPos_state(){
//		
//		return dao.
//		
//		
//		
//		
//	}

}
