package com.com.model;

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

import com.mem.model.MemVO;


public class ComDAO implements ComDAO_interface {

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

	private static final String INSERT = "INSERT INTO community (com_id, mem_id,com_name,privacy,create_time,com_status,post_count,mem_count) VALUES ('C'||LPAD(to_char(COMMUNITY_seq.NEXTVAL), 6, '0'),?,?,?,TO_DATE(sysdate),?,?,?)";
	private static final String UPDATE = "UPDATE community set com_name=?, cover_image=?, privacy=?, announcement=?, introduction=? WHERE com_id = ?";
	private static final String UPDATE_INTRODUCTION = "UPDATE COMMUNITY SET INTRODUCTION=? WHERE COM_ID = ?";
	private static final String UPDATE_ANNOUNCEMENT = "UPDATE COMMUNITY SET ANNOUNCEMENT=? WHERE COM_ID = ?";
	private static final String UPDATE_POST_COUNT = "UPDATE COMMUNITY SET POST_COUNT=(SELECT COUNT(*) FROM COMMUNITY_POST WHERE COM_ID=?)+1 WHERE COM_ID=?";
	private static final String PLUS_MEM_COUNT = "UPDATE COMMUNITY SET MEM_COUNT=(SELECT COUNT(*) FROM JOINED_COMMUNITY WHERE COM_ID=?)+1 WHERE COM_ID=?";
	private static final String MINUS_MEM_COUNT = "UPDATE COMMUNITY SET MEM_COUNT=(SELECT COUNT(*) FROM JOINED_COMMUNITY WHERE COM_ID=?)-1 WHERE COM_ID=?";
	private static final String GET_COM_CONTENT = "SELECT * FROM COMMUNITY WHERE COM_ID = ?";
	private static final String GET_COM_LIST = "SELECT * FROM community";

	@Override
	public ComVO insert(ComVO comVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String[] cols = { "com_id" };
		String com_id;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT,cols);
			pstmt.setString(1, comVO.getMem_id());
			pstmt.setString(2, comVO.getCom_name());
			pstmt.setInt (3, comVO.getPrivacy());
			pstmt.setInt (4, 0);	//	這邊給預設值為0(正常的社團)
			pstmt.setInt (5, 0);	//	這邊給預設值為0(貼文數從0開始)
			pstmt.setInt (6, 1);	//	這邊給預設值為1(一個人，就是建立者)
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			
			com_id = rs.getString(1);
			Integer mem_count = 1;
			
			comVO.setCom_id(com_id);
			comVO.setMem_count(mem_count);
			System.out.println(comVO.getMem_count());
			//pstmt.setXxx,set的個數要對應"?"的個數
			//pstmt.setXxx欄位的時候，不能同時使用系統自增的方式，會有conflict
					
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return comVO;
	}

	@Override
	public void update(ComVO comVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, comVO.getCom_name());
			pstmt.setBytes(2, comVO.getCover_image());
			pstmt.setInt(3, comVO.getPrivacy());
			pstmt.setString(4, comVO.getAnnouncement());
			pstmt.setString(5, comVO.getIntroduction());

			pstmt.setString(6, comVO.getCom_id());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void updateIntroduction(ComVO comVO) {		
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_INTRODUCTION);

			pstmt.setString(1, comVO.getIntroduction());
			pstmt.setString(2, comVO.getCom_id());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void updateAnnouncement(ComVO comVO) {		
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ANNOUNCEMENT);

			pstmt.setString(1, comVO.getAnnouncement());
			pstmt.setString(2, comVO.getCom_id());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void updatePostCount(ComVO comVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_POST_COUNT);

			pstmt.setString(1, comVO.getCom_id());
			pstmt.setString(2, comVO.getCom_id());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void plusMemCount(ComVO comVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(PLUS_MEM_COUNT);

			pstmt.setString(1, comVO.getCom_id());
			pstmt.setString(2, comVO.getCom_id());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void minusMemCount(ComVO comVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(MINUS_MEM_COUNT);

			pstmt.setString(1, comVO.getCom_id());
			pstmt.setString(2, comVO.getCom_id());
			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public ComVO getComContent(String com_id) {

		ComVO comVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COM_CONTENT);
			pstmt.setString(1, com_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// comVO 也稱為 Domain objects
				comVO = new ComVO();
				comVO.setCom_id(rs.getString("com_id"));
				comVO.setMem_id(rs.getString("mem_id"));
				comVO.setCom_name(rs.getString("com_name"));			
				comVO.setCover_image(rs.getBytes("cover_image"));
				comVO.setPrivacy(rs.getInt("privacy"));
				comVO.setAnnouncement(rs.getString("announcement"));
				comVO.setIntroduction(rs.getString("introduction"));
				comVO.setCreate_time(rs.getDate("create_time"));
				comVO.setCom_status(rs.getInt("com_status"));
				comVO.setPost_count(rs.getInt("post_count"));
				comVO.setMem_count(rs.getInt("mem_count"));
				
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
		return comVO;
	}

	@Override
	public List<ComVO> getComList(String str) {
		
		List<ComVO> list = new ArrayList<ComVO>();
		ComVO comVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println(GET_COM_LIST+str);
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COM_LIST+str);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// CommunityVO 也稱為 Domain objects
				comVO = new ComVO();
				comVO.setCom_id(rs.getString("com_id"));
				comVO.setMem_id(rs.getString("mem_id"));
				comVO.setCom_name(rs.getString("com_name"));
				comVO.setCover_image(rs.getBytes("cover_image"));		//database的Blob欄位資料，可以直接使用getBytes來接收
				comVO.setPrivacy(rs.getInt("privacy"));
				comVO.setAnnouncement(rs.getString("announcement"));	//database的Clob欄位資料，可以直接使用getString來接收
				comVO.setIntroduction(rs.getString("introduction"));	//database的Clob欄位資料，可以直接使用getString來接收
				comVO.setCreate_time(rs.getDate("create_time"));
				comVO.setCom_status(rs.getInt("com_status"));
				comVO.setPost_count(rs.getInt("post_count"));
				comVO.setMem_count(rs.getInt("mem_count"));
				
				list.add(comVO); // Store the row in the list

			}	
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		}  finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
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
	
}
