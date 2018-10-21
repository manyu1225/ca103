package com.mem.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/Mem_cart_photoReader")
public class Mem_cart_photoReader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	 
	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("image/gif");// 設定回應資料的形式
		ServletOutputStream out = res.getOutputStream();// 傳送到瀏覽器上面
		// PrintWriter out = res.getWriter();

		try {
			Statement stmt = con.createStatement();
			String mem_id = req.getParameter("mem_id").trim();//怕使用者輸入空白，所以加trim辨識
			ResultSet rs = stmt.executeQuery("SELECT MEM_CART_PHOTO FROM MEMBER WHERE MEM_ID='" + mem_id + "' ");
//			ResultSet rs = stmt.executeQuery("SELECT MEM_CART_PHOTO FROM MEMBER WHERE MEM_AC='mem_ac' ");
//			ResultSet rs = stmt.executeQuery("SELECT PICTURE FROM EMP_PHOTO WHERE EMPNO= '漂亮' ");

			if (rs.next()) {
				System.out.println("圖片2載入成功");
				BufferedInputStream in = new BufferedInputStream(rs.getBinaryStream("MEM_CART_PHOTO"));
				byte[] buf = new byte[4 * 1024]; // 4K buffer
				int len;
				while ((len = in.read(buf)) != -1) {// 傳入byte陣列來讀取資料，一組一組的倒入out裡面
					out.write(buf, 0, len);// 最後透過ServletOutputStream將東西傳送到瀏覽器上面
				}
				in.close();
				
			} else {
				//res.sendError(HttpServletResponse.SC_NOT_FOUND);
				InputStream in =getServletContext().getResourceAsStream("/sources/img/Member/defult_cart.jpg");
				byte[] b = new byte[in.available()];
				in.read(b);
				out.write(b);
				in.close();
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			InputStream in =getServletContext().getResourceAsStream("/sources/img/Member/defult_cart.jpg");
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
			
		}
	}

	public void init() throws ServletException {

		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
			con = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void destroy() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, res);
	}

}
