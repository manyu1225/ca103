package com.message.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mem.model.MemJDBCDAO;
import com.mem.model.MemVO;

public class MesJDBCDAO implements MesDAO_interface {

//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String userid = "CA103G2";
//	String passwd = "123456";
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	String INSERT_STMT = "INSERT INTO MESSAGE_RECORD(CHAT_NO,LOGIN_ID,RECEIVE_ID,CHAT_TEXT,CHAT_DATE,CHAT_TITLE,LOGIN_NICKNAME) VALUES('S'||LPAD(to_char(MES_SEQ.NEXTVAL),5,'0'),?,?,?,?,?,?)";
	String DELETE_STMT = "DELETE  FROM MESSAGE_RECORD WHERE CHAT_NO=?";
	String UPDATE_STMT = "UPDATE MESSAGE_RECORD SET CHAT_TEXT=?,CHAT_DATE =? CHAT_TITLE=? WHERE LOGIN_ID = ?";
	String GET_SNEDER_STMT = "SELECT M.MEM_NICKNAME,M.MEM_AC,R.CHAT_NO,R.LOGIN_ID,R.RECEIVE_ID,R.CHAT_TEXT,R.CHAT_DATE,R.CHAT_TITLE from MEMBER M right join MESSAGE_RECORD R on R.LOGIN_ID = M.MEM_ID WHERE R.RECEIVE_ID =? ";
	String GET_SNEDER_STMT2 = "SELECT CHAT_NO,RECEIVE_ID,LOGIN_ID,CHAT_TITLE,CHAT_DATE,LOGIN_NICKNAME,CHAT_TEXT FROM MESSAGE_RECORD WHERE CHAT_NO=?";
	String GET_MEMBER_NICKNAME_STMT = "SELECT M.MEM_NICKNAME,M.MEM_AC,R.CHAT_NO,R.LOGIN_ID,R.RECEIVE_ID,R.CHAT_TEXT,R.CHAT_DATE,R.CHAT_TITLE from MEMBER M right join MESSAGE_RECORD R on R.LOGIN_ID = M.MEM_ID WHERE R.CHAT_NO =? ";


	@Override
	public void insert(MesVO mesVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, userid, passwd);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);

			pstmt.setString(1, mesVO.getLogin_id());
			pstmt.setString(2, mesVO.getReceive_id());
			pstmt.setString(3, mesVO.getChat_text());
			java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
			String chatDay = new SimpleDateFormat("yyyy-MM-dd").format(sqlTimestamp);
			pstmt.setString(4, chatDay);
			pstmt.setString(5, mesVO.getChat_title());
			pstmt.setString(6, mesVO.getLogin_nickname());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void update(MesVO mesVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, userid, passwd);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STMT);
			pstmt.setString(1, mesVO.getChat_text());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void delete(String chat_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, userid, passwd);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			pstmt.setString(1,chat_no);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public MesVO findByPrimarKey(String chat_no) {
		MesVO mesVO = new MesVO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, userid, passwd);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_SNEDER_STMT2);
			pstmt.setString(1, chat_no);
			rs = pstmt.executeQuery();

			rs.next();
			mesVO.setChat_no(rs.getString("chat_no"));
			mesVO.setReceive_id(rs.getString("receive_id"));
			mesVO.setLogin_id(rs.getString("login_id"));
			mesVO.setChat_date(rs.getString("chat_date"));
			mesVO.setChat_title(rs.getString("chat_title"));
			mesVO.setChat_text(rs.getString("chat_text"));
			mesVO.setLogin_nickname(rs.getString("login_nickname"));
		} catch (SQLException e) {
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return mesVO;
	}

	@Override
	public List<MesVO> getAll(String receive_id) {
		List<MesVO> list = new ArrayList<MesVO>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, userid, passwd);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_SNEDER_STMT);
			pstmt.setString(1, receive_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				MesVO mesVO = new MesVO();
				mesVO.setLogin_nickname(rs.getString("mem_nickname"));
				mesVO.setLogin_ac(rs.getString("mem_ac"));
				mesVO.setChat_no(rs.getString("chat_no"));
				mesVO.setLogin_id(rs.getString("login_id"));
				mesVO.setReceive_id(rs.getString("receive_id"));
				mesVO.setChat_text(rs.getString("chat_text"));
				mesVO.setChat_date(rs.getString("chat_date"));
				mesVO.setChat_title(rs.getString("chat_title"));
				list.add(mesVO);
			}
			System.out.println("站內信物件長度" + list.size());

		} catch (SQLException e) {
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

		return list;
	}

	@Override
	public MesVO findNickName(String chat_no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MesVO mesVO = new MesVO();

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, userid, passwd);
//			pstmt.getConnection().prepareStatement(GET_MEMBER_NICKNAME_STMT);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_MEMBER_NICKNAME_STMT);
			pstmt.setString(1, chat_no);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				mesVO.setLogin_nickname(rs.getString("mem_nickname"));
				mesVO.setLogin_ac(rs.getString("mem_ac"));
				mesVO.setChat_no(rs.getString("chat_no"));
				mesVO.setLogin_id(rs.getString("login_id"));
				mesVO.setReceive_id(rs.getString("receive_id"));
				mesVO.setChat_text(rs.getString("chat_text"));
				mesVO.setChat_date(rs.getString("chat_date"));
				mesVO.setChat_title(rs.getString("chat_title"));
				System.out.println("有進來join查詢嗎?");
			}

		} catch (SQLException e) {
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return mesVO;
	}

	public static void main(String[] args) {
//		MesVO mesVO = new MesVO();
//		MesJDBCDAO dao = new MesJDBCDAO();
//		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
//		String chatDay = new SimpleDateFormat("yyyy-MM-dd").format(sqlTimestamp);
//		mesVO.setChat_date(chatDay);
//		mesVO.setChat_text("測試1");
//		mesVO.setChat_title("假的");
//		mesVO.setLogin_id("M000021");
//		mesVO.setReceive_id("M000022");
//		dao.insert(mesVO);
//		String a1 = "M000001";
//		mesVO = dao.findByPrimarKey(a1);
//		System.out.println(mesVO.getRECIVE_ID());
//		List<MesVO> list = new ArrayList<MesVO>();
//		list = dao.getAll(a1);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i).getChat_text());
//			System.out.println(list.get(i).getChat_title());
//			System.out.println(list.get(i).getRECIVE_ID());
//		}

	}

}
