package com.message.model;

import java.util.List;

public class MesService {
	private MesDAO_interface dao;
	
	public MesService(){
		dao = new MesJDBCDAO();
	}
	
	public void insert(MesVO mesVO) {
		dao.insert(mesVO);
	}
	public MesVO findByPrimarKey(String chat_no) {
		return dao.findByPrimarKey(chat_no);
	}
	public List<MesVO> getAll(String receive_id){
		return dao.getAll(receive_id);
	}
	public void delete(String chat_no) {
		dao.delete(chat_no);
	}
	public MesVO findNickName(String chat_no) {
		return dao.findNickName(chat_no);
	}
}
