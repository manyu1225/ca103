package com.comJoin.model;

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

import com.com.model.*;
import com.mem.model.*;

public class JoinedComDAO implements JoinedComDAO_interface{

	//	一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT = 
			"INSERT INTO JOINED_COMMUNITY (COM_ID, MEM_ID, PM_SETTING, AVAILABLE) VALUES (?,?,?,?)";
	private static final String GET_ONE_STATUS = 
			"SELECT * FROM JOINED_COMMUNITY  WHERE COM_ID=? AND MEM_ID=?";
	private static final String GET_WAIT_FOR_CHECK = 
			"SELECT * FROM JOINED_COMMUNITY  WHERE COM_ID=? AND PM_SETTING='0'";
	private static final String INSERT_OK = 
			"UPDATE JOINED_COMMUNITY SET PM_SETTING=1 WHERE COM_ID=? AND MEM_ID=?";
	private static final String EXIT_COM =
			"DELETE FROM JOINED_COMMUNITY WHERE COM_ID=? AND MEM_ID=?";
	private static final String GET_JOINED_COM_LIST = 
			"SELECT C.*,JC.PM_SETTING FROM COMMUNITY C JOIN JOINED_COMMUNITY JC ON C.COM_ID=JC.COM_ID WHERE JC.MEM_ID=? AND PM_SETTING=1";
	private static final String GET_CREATED_COM_LIST = 
			"SELECT C.*,JC.PM_SETTING FROM COMMUNITY C JOIN JOINED_COMMUNITY JC ON C.COM_ID=JC.COM_ID WHERE JC.MEM_ID=? AND PM_SETTING=2";
	private static final String GET_COM_MEMBER_LIST = 
			"SELECT M.* FROM MEMBER M JOIN JOINED_COMMUNITY JC ON JC.MEM_ID=M.MEM_ID WHERE COM_ID=? AND (PM_SETTING=1 OR PM_SETTING=2)";
	private static final String FIND_NUMBER_OF_COM = 
			"SELECT COUNT(*) FROM MEMBER M JOIN JOINED_COMMUNITY JC ON JC.MEM_ID=M.MEM_ID WHERE COM_ID=?";
	private static final String GET_POMEMVO = 
			"SELECT CP.COM_ID,CP.MEM_ID, M.MEM_AC, M.MEM_NICKNAME, M.MEM_PHOTO FROM COMMUNITY_POST CP  JOIN MEMBER M ON CP.MEM_ID=M.MEM_ID WHERE CP.COMPO_ID=? ORDER BY CP.COMPO_ID DESC";

	
	@Override
	public void add(JoinedComVO joinedComVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setString(1, joinedComVO.getCom_id());
			pstmt.setString(2, joinedComVO.getMem_id());
			pstmt.setInt (3, joinedComVO.getPm_setting());		//	一般社員預設為1，版主預設為2
			pstmt.setInt (4, joinedComVO.getAvailable());		//	預設為正常
			
			pstmt.executeUpdate();
			
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
	
	}
	
	@Override
	public JoinedComVO getOneForCheck(JoinedComVO joinedComVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STATUS);
			
			pstmt.setString(1, joinedComVO.getCom_id());
			pstmt.setString(2, joinedComVO.getMem_id());
			rs = pstmt.executeQuery();
			
			rs.next(); 
				
			joinedComVO = new JoinedComVO();
			joinedComVO.setCom_id(rs.getString("com_id"));
			joinedComVO.setMem_id(rs.getString("mem_id"));
			joinedComVO.setPm_setting(rs.getInt("pm_setting"));			
			joinedComVO.setAvailable(rs.getInt("available"));
			
			// Handle any SQL errors
		} catch (SQLException se) {
			se.getMessage();
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
		return joinedComVO;
	}
	
	@Override
	public List<JoinedComVO> getWaitForCheckList(String com_id) {
		
		List<JoinedComVO> getWaitForCheckList = new ArrayList<JoinedComVO>();
		JoinedComVO joinedComVO;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_WAIT_FOR_CHECK);
			pstmt.setString(1, com_id);
										
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				
				joinedComVO = new JoinedComVO();
				joinedComVO.setCom_id(rs.getString("com_id"));
				joinedComVO.setMem_id(rs.getString("mem_id"));
				joinedComVO.setPm_setting(rs.getInt("pm_setting"));			
				joinedComVO.setAvailable(rs.getInt("available"));
				
				getWaitForCheckList.add(joinedComVO);
			}
			
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
		return getWaitForCheckList;
	}
	
	@Override
	public void add_OK(JoinedComVO joinedComVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_OK);
			pstmt.setString(1, joinedComVO.getCom_id());
			pstmt.setString(2, joinedComVO.getMem_id());
										
			pstmt.executeUpdate();
			
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
	
	}

	@Override
	public void exitCom(JoinedComVO joinedComVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(EXIT_COM);
			
			pstmt.setString(1, joinedComVO.getCom_id());
			pstmt.setString(2, joinedComVO.getMem_id());
			
			pstmt.executeUpdate();
			
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
	
	}
	
	@Override
	public List<ComVO> getJoinedComList(String mem_id) {
		List<ComVO> list = new ArrayList<ComVO>();
		ComVO comVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_JOINED_COM_LIST);
			
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
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
	
	@Override
	public List<ComVO> getCreatedComList(String mem_id) {
		List<ComVO> list = new ArrayList<ComVO>();
		ComVO comVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_CREATED_COM_LIST);
			
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
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
	
	@Override
	public List<MemVO> getComMemberList(String com_id) {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COM_MEMBER_LIST);
			
			pstmt.setString(1, com_id);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				memVO = new MemVO();
				
				memVO.setMem_id(rs.getString("mem_id"));
				memVO.setMem_ac(rs.getString("mem_ac"));
				memVO.setMem_firstname(rs.getString("mem_firstname"));
				memVO.setMem_lastname(rs.getString("mem_lastname"));
				memVO.setMem_photo(rs.getBytes("mem_photo"));
				memVO.setMem_nickname(rs.getString("mem_nickname"));
//				memVO.setMem_aboutme(rs.getString("mem_aboutme"));		//也許會用到	
				
				list.add(memVO); 
				
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

	@Override
	public int findNumberOfCom(String com_id) {
		int numberOfCom = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COM_MEMBER_LIST);
			
			pstmt.setString(1, com_id);
			rs = pstmt.executeQuery();
			
			rs.next();
			numberOfCom = rs.getInt(1);
			
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
		return numberOfCom;
	}

	@Override
	public MemVO getMemVO(String comPo_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemVO memVO = new MemVO();
		
		 
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_POMEMVO);
			
			pstmt.setString(1, comPo_id);
			rs = pstmt.executeQuery();
			rs.next();
			
			memVO.setMem_id(rs.getString("com_id"));
			memVO.setMem_id(rs.getString("mem_id"));
			memVO.setMem_ac(rs.getString("mem_ac"));
			memVO.setMem_nickname(rs.getString("mem_nickname"));
			memVO.setMem_photo(rs.getBytes("mem_photo"));
				
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
			if (rs != null) {
				try {rs.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
			
		}
		return memVO;
	}
	
}
