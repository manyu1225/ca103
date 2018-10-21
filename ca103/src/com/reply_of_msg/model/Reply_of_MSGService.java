package com.reply_of_msg.model;

import java.util.List;

import com.msg_of_gp.model.MSG_OF_GPVO;

public class Reply_of_MSGService {
	private Reply_of_MSGDAO_interface dao;
	
	public Reply_of_MSGService() {
		dao = new Reply_of_MSGJNDIDAO();
	}
	
	public List<Reply_of_MSGVO> findReplies(MSG_OF_GPVO msgVO){
		return dao.findReplies(msgVO);
	}
	
	public List<Reply_of_MSGVO> findRepliesForEmp(MSG_OF_GPVO msgVO){
		return dao.findRepliesForEmp(msgVO);
	}
	
	public void newReply(Reply_of_MSGVO replyVO) {
		dao.newReply(replyVO);
	}
	
	public void deleteReply(Reply_of_MSGVO replyVO) {
		dao.deleteReply(replyVO);
	}
	
}
