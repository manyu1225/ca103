package com.reply_of_msg.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.msg_of_gp.model.MSG_OF_GPVO;

public class Reply_of_MSGJDBCDAO implements Reply_of_MSGDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "CA103G2";
	
	public static final String FIND_REPLY = "select * from REPLY_OF_MSG where MSG_ID = ? AND reply_status = 0";
	public static final String ADD_REPLY = "insert into REPLY_OF_MSG(REPLY_ID,MEM_ID,MSG_ID,REPLY_CONTENT,REPLY_TIME,REPLY_STATUS)" + 
			"values ('REP'||LPAD(to_char(REPLY_OF_MSG_seq.NEXTVAL),4,'0'),?,?,?,?,?)";
	public static final String DELETE_REPLY = "UPDATE REPLY_OF_MSG set REPLY_STATUS=1 where REPLY_ID = ?";
	
	public static void main(String[] args) {
		Reply_of_MSGJDBCDAO dao = new Reply_of_MSGJDBCDAO();
		
////		查看某留言的回文
//		List<Reply_of_MSGVO> list = new ArrayList<>();
//		MSG_OF_GPVO msgVO = new MSG_OF_GPVO();
//		msgVO.setMsg_id("MSG0001");
//		list = dao.findReplies(msgVO);
//		for(Reply_of_MSGVO replyVO:list) {
//			System.out.println(replyVO.getReply_id());
//			System.out.println(replyVO.getMem_id());
//			System.out.println(replyVO.getReply_content());
//		}
		
////		新增一筆回文到留言
//		Reply_of_MSGVO replyVO = new Reply_of_MSGVO(null, "MSG0002", "M000002", "我吃火鍋你吃貨郭ㄉ屌", new Timestamp(System.currentTimeMillis()), 0);
//		dao.newReply(replyVO);
		
////		改變回文的狀態封存
//		Reply_of_MSGVO replyVO = new Reply_of_MSGVO();
//		replyVO.setReply_id("REP0002");
//		dao.deleteReply(replyVO);
		
	}

	@Override
	public List<Reply_of_MSGVO> findReplies(MSG_OF_GPVO msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<Reply_of_MSGVO> list = new ArrayList<>();
		Reply_of_MSGVO replyVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_REPLY);
			pstmt.setString(1, msgVO.getMsg_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				replyVO = new Reply_of_MSGVO(rs.getString("REPLY_ID"), rs.getString("MSG_ID"), rs.getString("MEM_ID"), 
						rs.getString("REPLY_CONTENT"), rs.getTimestamp("REPLY_TIME"), rs.getInt("REPLY_STATUS"));
				list.add(replyVO);
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
	public void newReply(Reply_of_MSGVO replyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(ADD_REPLY);
			pstmt.setString(1, replyVO.getMem_id());
			pstmt.setString(2, replyVO.getMsg_id());
			pstmt.setString(3, replyVO.getReply_content());
			pstmt.setTimestamp(4, replyVO.getReply_time());
			pstmt.setInt(5, replyVO.getReply_status());
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
	public void deleteReply(Reply_of_MSGVO replyVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_REPLY);
			pstmt.setString(1, replyVO.getReply_id());
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
	public List<Reply_of_MSGVO> findRepliesForEmp(MSG_OF_GPVO msgVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
