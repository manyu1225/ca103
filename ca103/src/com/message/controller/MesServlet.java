package com.message.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import com.friends.model.FriendsVO;
import com.friends.model.FriendsService;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.message.model.MesService;
import com.message.model.MesVO;

public class MesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html ;charset=utf-8");
		String action = req.getParameter("action");
		System.out.println("確認有無進來MesServlet:  "+action);
		String sendMes = req.getParameter("sendMes");
		
		if ("sendMes".equals(sendMes)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			Map<String, String> okMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("okMsgs", okMsgs);
			okMsgs.put("sendOk", "信件傳送成功");
			
			String receive_ac = req.getParameter("receive_ac");
			String login_ac = req.getParameter("login_ac");
			String chat_title = req.getParameter("chat_title");
			String chat_text = req.getParameter("chat_text");
			MemService memSvc = new MemService();
			MemVO getReceive_id = memSvc.findByPrimarKey(receive_ac);
			MemVO getLogin_id = memSvc.findByPrimarKey(login_ac);
			String receive_id = getReceive_id.getMem_id();
			String login_id = getLogin_id.getMem_id();
			
			
			if (receive_ac == null || receive_ac.trim().length() == 0) {
				errorMsgs.add("收件者請勿空白");
			}

			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/mem/MemMesOut.jsp");
				failureView.forward(req, res);
				return;
			}

			MesVO mesVO = new MesVO();
			mesVO.setLogin_id(login_id);
			mesVO.setReceive_id(receive_id);
			mesVO.setChat_title(chat_title);
			mesVO.setChat_text(chat_text);
			mesVO.setLogin_nickname(getLogin_id.getMem_nickname());

			try {
				MesService mesSvc = new MesService();
				mesSvc.insert(mesVO);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			req.setAttribute("mesVO", mesVO);
			//session斷掉的同時，也不能看站內信，存session好像也沒甚麼關係
			
			String url = "/front-end/mem/MemberMes.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);

		}

		if ("getMesText".equals(action)) {
			MesVO mesVO = new MesVO();
			
			String chat_no = req.getParameter("chat_no");
			System.out.println("讀取信件編號："+chat_no);
			MesService mesSvc = new MesService();
			mesVO = mesSvc.findNickName(chat_no);
			req.setAttribute("mesVO", mesVO);
			System.out.println("測試讀取信件內容： "+mesVO.getChat_text());
			System.out.println("測試讀取信件標題： "+mesVO.getChat_title());
			System.out.println("測試讀取寄件者 ： "+mesVO.getLogin_nickname());
			String url = "/front-end/mem/MemMesRead.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		
		if("sendInvite".equals(action)) {
			Map<String, String> okMsg = new LinkedHashMap<String, String>();
			req.setAttribute("okMsg", okMsg);
			

			MemVO memVO = new MemVO();
			MesVO mesVO = new MesVO();
			HttpSession session = req.getSession();
//			memVO = (MemVO)session.getAttribute("memVO");
			String other_id = req.getParameter("other_id");//邀請對象的ID
			String self_id = req.getParameter("self_id");//登入者的ID
			System.out.println("邀請對象的ID: "+other_id);
			System.out.println("登入者的ID: "+self_id);
			MemService memSvc = new MemService();
			MemVO viewMem = new MemVO();
			memVO = memSvc.findMemById(self_id);
			viewMem = memSvc.findMemById(other_id);
			req.setAttribute("mem_id", viewMem);
			
			MesService mesSvc = new MesService();
			FriendsService frSvc = new FriendsService();
			FriendsVO friendsVO = new FriendsVO();
			String title = memVO.getMem_nickname()+"("+memVO.getMem_id()+")"+"發送了好友邀請給你";
			String text = "您好：有人向您提出的好友邀請，請至好友清單確認是否要成為好友。";
			String inviteOk = "已送出好友邀請";
			//mesVO:發送信件
		
			try {
				
				friendsVO.setSelf_id(self_id);
				System.out.println("有無抓到session的ID: "+friendsVO.getSelf_id());
				friendsVO.setOther_id(other_id);
//				frSvc.findByPrimarKey(friendsVO);
//				System.out.println("辨識邀請狀態： "+friendsVO.getFr_status());
				if(frSvc.findByPrimarKey(friendsVO)==null) {
					System.out.println("接收邀請的id: "+friendsVO.getOther_id());
					friendsVO.setFr_status(0);
					
					frSvc.insert(friendsVO);
					
					mesVO.setLogin_id("M999999");
					mesVO.setReceive_id(other_id);
					mesVO.setChat_text(text);
					mesVO.setChat_title(title);
					mesVO.setLogin_nickname("系統管理員");
					mesSvc.insert(mesVO);//寄信給被邀請的人
					//friendsVO:設置好友狀態
					okMsg.put("inviteOk", "發送邀請成功");
					String url = "/front-end/mem/MemberView.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					
				}else {
					
					okMsg.put("inviteFail", "請勿重複發送邀請或你已被封鎖");
					String url = "/front-end/mem/MemberView.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					
				}
			}catch(Exception e) {
				e.printStackTrace();
				okMsg.put("imvaiteFail", "請勿重複發送邀請");
				String url = "/front-end/mem/MemberView.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			}
			
			
			
			//創建一個系統用帳號，編號：M999999 暱稱：系統管理員 ok
		}
		
		if("mesDelete".equals(action)) {
			Map<String, String> delMsg = new LinkedHashMap<String, String>();
			delMsg.put("deleteOk", "信件已刪除");
			req.setAttribute("delMsg", delMsg);
			String deleteNo = req.getParameter("deleteNo");
			System.out.println("刪除信件編號： "+deleteNo);
			delMsg.put("chat_no", deleteNo);
			delMsg.put("string", "編號");
			MesService mesSvc = new MesService();
			mesSvc.delete(deleteNo);
			
			String url = "/front-end/mem/MemberMes.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		}
		if("reMail".equals(action)) {
			Map<String, String> reMsg = new LinkedHashMap<String, String>();
			String reMailAc =req.getParameter("reMailAc");
			String reMailTitle = req.getParameter("reMailTitle");
			reMsg.put("re", "Re:");
			reMsg.put("reMailAc", reMailAc);
			reMsg.put("reMailTitle", reMailTitle);
			System.out.println("回覆對象的帳號： "+reMailAc);
			req.setAttribute("reMsg", reMsg);
			
			String url = "/front-end/mem/MemMesOut.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
		}
		
		

	}

}
