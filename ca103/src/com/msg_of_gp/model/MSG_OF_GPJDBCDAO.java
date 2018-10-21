package com.msg_of_gp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gp.model.GPVO;

public class MSG_OF_GPJDBCDAO implements MSG_OF_GPDAO_interface{
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "CA103G2";
	
	public static final String FIND_MSG = "select * from MSG_OF_GP where GP_ID = ? AND msg_status = 0";
	public static final String ADD_MSG = "insert into MSG_OF_GP(MSG_ID,MEM_ID,GP_ID,MSG_CONTENT,MSG_TIME,MSG_STATUS)" + 
			"values ('MSG'||to_char(LPAD(to_char(MSG_OF_GP_seq.NEXTVAL),4,'0')),?,?,?,?,?)";
	public static final String DELETE_MSG = "UPDATE MSG_OF_GP set MSG_STATUS=1 where MSG_ID=?";
	
	
	public static void main(String[] args) {
		MSG_OF_GPJDBCDAO dao = new MSG_OF_GPJDBCDAO();
		
////		查看某揪團的訊息
//		List<MSG_OF_GPVO> list = new ArrayList<>();
//		GPVO gpVO = new GPVO();
//		gpVO.setGp_id("G000001");
//		list = dao.findMessages(gpVO);
//		for(MSG_OF_GPVO msgVO:list) {
//			System.out.println(msgVO.getMsg_id());
//			System.out.println(msgVO.getMem_id());
//			System.out.println(msgVO.getMsg_content());
//		}
		
		
////		在揪團留言
//		MSG_OF_GPVO msgVO = new MSG_OF_GPVO(null, "G000001", "M000002", "哈锪我又來了", new Timestamp(System.currentTimeMillis()), 1);
//		dao.newMessage(msgVO);
//		將留言狀態改為1封存
		MSG_OF_GPVO msgVO = new MSG_OF_GPVO();
		msgVO.setMsg_id("MSG0001");
		dao.deleteMessage(msgVO);
		
	}

	@Override
	public List<MSG_OF_GPVO> findMessages(GPVO gpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<MSG_OF_GPVO> list = new ArrayList<>();
		MSG_OF_GPVO msgVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_MSG);
			pstmt.setString(1, gpVO.getGp_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				msgVO = new MSG_OF_GPVO();
				msgVO.setMsg_id(rs.getString("MSG_ID"));
				msgVO.setMem_id(rs.getString("MEM_ID"));
				msgVO.setGp_id(rs.getString("GP_ID"));
				msgVO.setMsg_content(rs.getString("MSG_CONTENT"));
				msgVO.setMsg_time(rs.getTimestamp("MSG_TIME"));
				msgVO.setMsg_status(rs.getInt("MSG_STATUS"));
				list.add(msgVO);
			}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void newMessage(MSG_OF_GPVO msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(ADD_MSG);
			pstmt.setString(1, msgVO.getMem_id());
			pstmt.setString(2, msgVO.getGp_id());
			pstmt.setString(3, msgVO.getMsg_content());
			pstmt.setTimestamp(4, msgVO.getMsg_time());
			pstmt.setInt(5, msgVO.getMsg_status());
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void deleteMessage(MSG_OF_GPVO msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_MSG);
			pstmt.setString(1, msgVO.getMsg_id());
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<MSG_OF_GPVO> findMessagesForEmp(GPVO gpVO) {
		// TODO Auto-generated method stub
		return null;
	}
}
