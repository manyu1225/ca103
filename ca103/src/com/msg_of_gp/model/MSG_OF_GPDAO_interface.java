package com.msg_of_gp.model;

import java.util.List;

import com.gp.model.GPVO;

public interface MSG_OF_GPDAO_interface {
	public List<MSG_OF_GPVO> findMessages(GPVO gpVO);
	public void newMessage(MSG_OF_GPVO msgVO);
	public void deleteMessage(MSG_OF_GPVO msgVO);
	public List<MSG_OF_GPVO> findMessagesForEmp(GPVO gpVO);
	
}
