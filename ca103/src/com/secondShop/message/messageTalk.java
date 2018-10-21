package com.secondShop.message;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/MessageTalk")
public class messageTalk {	
	
	private static final Set<Session> allSessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session userSession) throws IOException {
		allSessions.add(userSession);
		System.out.println(userSession.getId() + ": 已連線");
	}
	
	@OnMessage
	public void onMessage(Session userSession, String whoSedmessage) {  
		System.out.println("whoSedmessage received: " + whoSedmessage);		//{"動作":"聊天","say":"Li","listren":"kevin","內容":"hihihi"}
		for (Session session : allSessions) {
			if (session.isOpen())
				session.getAsyncRemote().sendText(whoSedmessage);
		}
	
	}
	
	@OnError
	public void onError(Session userSession, Throwable e){
		e.printStackTrace();
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		allSessions.remove(userSession);
//		System.out.println(userSession.getId() + ": Disconnected: " + Integer.toString(reason.getCloseCode().getCode()));
	}


}
