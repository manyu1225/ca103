package com.friends.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.friends.model.FriendsService;
import com.friends.model.FriendsVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;

public class FriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = req.getParameter("action");
		String cleanInvite = req.getParameter("cleanInvite");
		if("getConfrim_id".equals(action)) {
			String self_id = req.getParameter("self_id");//被按下確認的NO
			System.out.println("確認好友ID有無抓到："+self_id);
			String other_id = req.getParameter("other_id");
			System.out.println("好友清單登入者的ID： "+other_id);
			MemVO memVO = new MemVO();
			MemService memSvc = new MemService();
			memVO = memSvc.findMemById(self_id);
			
			//呼叫dao修改
			
			FriendsService frSvc = new  FriendsService();
			FriendsVO friendsVO = new FriendsVO();
			friendsVO.setOther_id(other_id);
			friendsVO.setSelf_id(self_id);
			friendsVO.setFr_status(1);
			frSvc.update(friendsVO);
			
			//一邊確認的同時，兩邊都變成好友
			
			FriendsVO friendsVO2 = new FriendsVO();
			friendsVO2.setOther_id(self_id);
			friendsVO2.setSelf_id(other_id);
			friendsVO2.setFr_status(1);
			frSvc.insert(friendsVO2);
			
			//開始轉交
			req.setAttribute("mem_id", memVO);
			String url = "/front-end/mem/FriendsList.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		
		}
		if("getBlock_fr".equals(action)) {
			//呼叫刪除
			String self_id = req.getParameter("self_id");
			String other_id = req.getParameter("other_id");
			System.out.println("確認想要解除好友的no: "+self_id);
			FriendsVO friendsVO = new FriendsVO();
			FriendsVO friendsVO2 = new FriendsVO();
			FriendsService frSvc = new  FriendsService();
			friendsVO.setOther_id(other_id);
			friendsVO.setSelf_id(self_id);
			friendsVO.setFr_status(3);
			System.out.println("controller的other_id: "+friendsVO.getOther_id());
			System.out.println("controller的self_id: "+friendsVO.getSelf_id());
			frSvc.update(friendsVO);
			friendsVO2.setOther_id(self_id);
			friendsVO2.setSelf_id(other_id);
			frSvc.delete(friendsVO2);
			
			String url = "/front-end/mem/FriendsListOK.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		}
		if("cleanInvite".equals(action)) {
			Map<String, String> okMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("okMsgs", okMsgs);
			String self_id = req.getParameter("self_id");
			System.out.println(self_id);
			String other_id = req.getParameter("other_id");
			System.out.println(other_id);
			FriendsVO friendsVO = new FriendsVO();
			FriendsService frSvc = new  FriendsService();
			friendsVO.setSelf_id(self_id);
			friendsVO.setOther_id(other_id);
			frSvc.delete(friendsVO);
			okMsgs.put("cleanOk","已清除邀請");
			String url = "/front-end/mem/FriendsList.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		}
		
		
	}

}
