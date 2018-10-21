package com.reply_of_msg.model;

import java.util.List;

import com.msg_of_gp.model.MSG_OF_GPVO;

public interface Reply_of_MSGDAO_interface {
	public List<Reply_of_MSGVO> findReplies(MSG_OF_GPVO msgVO);
	public List<Reply_of_MSGVO> findRepliesForEmp(MSG_OF_GPVO msgVO);
	public void newReply(Reply_of_MSGVO replyVO);
	public void deleteReply(Reply_of_MSGVO replyVO);
}
