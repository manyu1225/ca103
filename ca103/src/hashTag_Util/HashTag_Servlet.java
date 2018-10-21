package hashTag_Util;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import antlr.collections.List;

public class HashTag_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

	
	String action = req.getParameter("action");
	
	if("get_One_Tag".equals(action)) {
		System.out.println("成功進入標籤取得頁面");
		Map<String, String> errorMsgs = new LinkedHashMap<String, String>();

		
		try {
			
			
			LinkedList<Integer> listPostByTag = new LinkedList<Integer>();
			
			//String forPost_ID = req.getParameter("forPost_ID");
			String tag = req.getParameter("tag");
			
			HashTag_Service hashTagSvc = new HashTag_Service();
			
			for(String forPost_ID:hashTagSvc.getPostsByTag(tag)) {
				
				listPostByTag.add(new Integer(forPost_ID));
			}
			
			req.setAttribute("listPostByTag", listPostByTag);

			RequestDispatcher successView = req.getRequestDispatcher("/front-end/Forum/hashTag/HashTag_display.jsp");
			System.out.println("2");

			successView.forward(req, res);
			
			
			
		}catch(RuntimeException e) {
			errorMsgs.put("Exception",e.getMessage());
			RequestDispatcher failureView = req .getRequestDispatcher("/emp/HashTag_display.jsp");
			failureView.forward(req, res);
		}
		
		
		
			}
		}
	}
