package com.friends.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class FriendsJDBCDAO implements FriendsDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String user = "CA103G2";
//	String pw = "CA103G2";

	String INSERT_STMT = "INSERT INTO FRIENDS(SELF_ID,OTHER_ID,FR_STATUS) VALUES(?,?,?)";
	String DELETE_STMT = "DELETE FROM FRIENDS WHERE SELF_ID=? AND OTHER_ID=?";
	String GET_ONE_STMT = "SELECT * FROM FRIENDS WHERE OTHER_ID=? AND SELF_ID=?";
	String GET_ALL_STMT = "SELECT * FROM FRIENDS WHERE OTHER_ID=?";
	String UPDATE_STATUS_STMT = "UPDATE FRIENDS SET FR_STATUS=? WHERE SELF_ID = ? AND OTHER_ID= ?";//傳物件進來更新
	String GET_NICKNAME_JOIN = "SELECT M.MEM_NICKNAME,M.MEM_AC,F.SELF_ID,F.OTHER_ID,F.FR_STATUS FROM MEMBER M RIGHT JOIN FRIENDS F ON F.self_ID = M.MEM_ID WHERE F.OTHER_ID =?";

	@Override
	public void insert(FriendsVO friendsVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			System.out.println("11");
//			Class.forName(driver);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			pstmt.setString(1, friendsVO.getSelf_id());
			System.out.println(friendsVO.getSelf_id());
			pstmt.setString(2, friendsVO.getOther_id());
			System.out.println(friendsVO.getOther_id());
			pstmt.setInt(3, friendsVO.getFr_status());
			System.out.println(friendsVO.getFr_status());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public void update(FriendsVO friendsVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
//		FriendsVO frVO = new FriendsVO();
		FriendsJDBCDAO dao = new FriendsJDBCDAO();
		
		
		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STATUS_STMT);
			
			pstmt.setInt(1, friendsVO.getFr_status());
			pstmt.setString(2, friendsVO.getSelf_id());
			pstmt.setString(3, friendsVO.getOther_id());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(FriendsVO friendsVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);
			pstmt.setString(1, friendsVO.getSelf_id());
			pstmt.setString(2, friendsVO.getOther_id());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public FriendsVO findByPrimarKey(FriendsVO friendsVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		FriendsVO frVO = null;
		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, friendsVO.getOther_id());
			pstmt.setString(2, friendsVO.getSelf_id());
			System.out.println("^^報了活該in setString^^");
			rs = pstmt.executeQuery();
			System.out.println("^^報了活該^^");
			if(rs.next()) {
				frVO = new FriendsVO();
				frVO.setSelf_id(rs.getString("self_id"));
				frVO.setFr_status(new Integer(rs.getInt("fr_status")));
				frVO.setOther_id(rs.getString("other_id"));
				
			}
			
		}  catch (SQLException e) {
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
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
		return frVO;
	}

	@Override
	public List<FriendsVO> getAll(String other_id) {
		//要抓登入者ID，所以不能用主健
		List<FriendsVO> list = new ArrayList<FriendsVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_NICKNAME_JOIN);
			pstmt.setString(1, other_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("好友getAll有無進入");
				FriendsVO frVO = new FriendsVO();
				frVO.setSelf_nickname(new String(rs.getString("mem_nickname")));
				frVO.setSelf_ac(new String(rs.getString("mem_ac")));
				frVO.setSelf_id(rs.getString("self_id"));
				frVO.setOther_id(rs.getString("other_id"));
				frVO.setFr_status(new Integer(rs.getInt("fr_status")));
//				frVO.setFr_no(rs.getString("fr_no"));
				list.add(frVO);
			}

		}  catch (SQLException e) {
//			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
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
	public FriendsVO findOtherNicknameById(String other_id) {
		return null;
	}

	
	public static void main(String[] args) {
//		List<FriendsVO> list = new ArrayList<FriendsVO>();
//		FriendsJDBCDAO dao = new FriendsJDBCDAO();
//		list = dao.getAll("M000021");
//		System.out.println(list.size());
//		for(FriendsVO frVO:list) {
//			System.out.println(frVO.getSelf_ac());
//			System.out.println(frVO.getOther_id());
//			System.out.println(frVO.getSelf_id());
//			System.out.println(frVO.getSelf_nickname());
//			System.out.println(frVO.getFr_status());
//			System.out.println(frVO.getFr_no());
//			
//		}
//		dao.update("FR000002");
	}


}
