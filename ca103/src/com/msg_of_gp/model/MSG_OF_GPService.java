package com.msg_of_gp.model;

import java.util.List;

import com.gp.model.GPVO;

public class MSG_OF_GPService {
	private MSG_OF_GPDAO_interface dao;
	public MSG_OF_GPService() {
		dao = new MSG_OF_GPJNDIDAO();
	}
	
	public List<MSG_OF_GPVO> findMessages(GPVO gpVO){
		return dao.findMessages(gpVO);
	};
	
	public void newMessage(MSG_OF_GPVO msgVO) {
		dao.newMessage(msgVO);
	}
	public void deleteMessage(MSG_OF_GPVO msgVO) {
		dao.deleteMessage(msgVO);
	}
	
	public List<MSG_OF_GPVO> findMessagesForEmp(GPVO gpVO){
		return dao.findMessagesForEmp(gpVO);
	};
}
