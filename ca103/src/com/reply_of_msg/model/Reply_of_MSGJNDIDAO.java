package com.reply_of_msg.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.msg_of_gp.model.MSG_OF_GPVO;

public class Reply_of_MSGJNDIDAO implements Reply_of_MSGDAO_interface{
	public static final String FIND_REPLY = "select * from REPLY_OF_MSG where MSG_ID = ? AND REPLY_STATUS = 0";
	public static final String ADD_REPLY = "insert into REPLY_OF_MSG(REPLY_ID,MEM_ID,MSG_ID,REPLY_CONTENT,REPLY_TIME,REPLY_STATUS)" + 
			"values ('REP'||LPAD(to_char(REPLY_OF_MSG_seq.NEXTVAL),4,'0'),?,?,?,?,?)";
	public static final String DELETE_REPLY = "UPDATE REPLY_OF_MSG set REPLY_STATUS=1 where REPLY_ID = ?";
	
	public static final String FIND_REPLY_FOR_EMP = "select * from REPLY_OF_MSG where MSG_ID = ?";
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Reply_of_MSGVO> findRepliesForEmp(MSG_OF_GPVO msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<Reply_of_MSGVO> list = new ArrayList<>();
		Reply_of_MSGVO replyVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_REPLY_FOR_EMP);
			pstmt.setString(1, msgVO.getMsg_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				replyVO = new Reply_of_MSGVO(rs.getString("REPLY_ID"), rs.getString("MSG_ID"), rs.getString("MEM_ID"), 
						rs.getString("REPLY_CONTENT"), rs.getTimestamp("REPLY_TIME"), rs.getInt("REPLY_STATUS"));
				list.add(replyVO);
			}
		}catch (SQLException se) {
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
	public List<Reply_of_MSGVO> findReplies(MSG_OF_GPVO msgVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<Reply_of_MSGVO> list = new ArrayList<>();
		Reply_of_MSGVO replyVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_REPLY);
			pstmt.setString(1, msgVO.getMsg_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				replyVO = new Reply_of_MSGVO(rs.getString("REPLY_ID"), rs.getString("MSG_ID"), rs.getString("MEM_ID"), 
						rs.getString("REPLY_CONTENT"), rs.getTimestamp("REPLY_TIME"), rs.getInt("REPLY_STATUS"));
				list.add(replyVO);
			}
		}catch (SQLException se) {
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADD_REPLY);
			pstmt.setString(1, replyVO.getMem_id());
			pstmt.setString(2, replyVO.getMsg_id());
			pstmt.setString(3, replyVO.getReply_content());
			pstmt.setTimestamp(4, replyVO.getReply_time());
			pstmt.setInt(5, replyVO.getReply_status());
			pstmt.executeUpdate();
		}catch (SQLException se) {
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_REPLY);
			pstmt.setString(1, replyVO.getReply_id());
			pstmt.executeUpdate();
		}catch (SQLException se) {
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
}
