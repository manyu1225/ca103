package com.forPos_report.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.forPos_report.model.*;

public class Forum_post_report_DAO implements Forum_post_report_DAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO FORUM_POST_REPORT (forPost_ID, mem_ID, FORPOST_REP_TIME, FORPOST_REP_STATE, FORPOS_REP_REASON) VALUES (?, ?, current_timestamp, 0, ?)";
	private static final String GET_ALL_STMT = "SELECT forPost_ID,mem_ID,forPos_rep_state,forPost_reason FROM FORUM_POST_REPORT order by forPos_rep_time";
	private static final String GET_ONE_STMT = "SELECT forPost_ID,mem_ID,forPos_rep_state,forPost_reason FROM FORUM_POST_REPORT where forPost_ID = ?, mem_ID = ?";
	private static final String DELETE = "DELETE FROM FORUM_POST_REPORT where forPost_ID = ?, mem_ID = ? = ?";
	private static final String UPDATE = "UPDATE FORUM_POST_REPORT set forPos_rep_state = ?,forPost_reason = ? where forPost_ID = ?, mem_ID = ?";
	//****************************************
	private static final String GETBYSTATUS = 
			"SELECT * FROM FORUM_POST_REPORT WHERE ";
	private static final String GETALL = 
			"SELECT * FROM FORUM_POST_REPORT WHERE FORPOST_REP_STATE=0 order by forPost_rep_time";
	private static final String CLOSEPOST = 
			"UPDATE FORUM_POST_REPORT set forPost_rep_state = ?where forPost_ID = ?";
	//******************************************************

	@Override
	public void insert(Forum_post_report_VO forPost_rep_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, forPost_rep_VO.getForPost_ID());
			pstmt.setString(2, forPost_rep_VO.getMem_ID());
			pstmt.setString(3, forPost_rep_VO.getForPost_rep_reason());


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
	public void update(Forum_post_report_VO forPost_rep_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, forPost_rep_VO.getForPos_rep_time());
			pstmt.setInt(2, forPost_rep_VO.getForPos_rep_state());
			pstmt.setString(3, forPost_rep_VO.getForPost_rep_reason());
			pstmt.setInt(6, forPost_rep_VO.getForPost_ID());
			pstmt.setString(7, forPost_rep_VO.getMem_ID());

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
	public void delete(Integer forPost_ID, String mem_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, forPost_ID);
			pstmt.setString(2, mem_ID);

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
	public Forum_post_report_VO findByPrimaryKey(Integer forPost_ID, String mem_ID) {
		Forum_post_report_VO forPost_rep_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, forPost_ID);
			pstmt.setString(2, mem_ID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				forPost_rep_VO = new Forum_post_report_VO();
				forPost_rep_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPost_rep_VO.setMem_ID(rs.getString("mem_ID"));
				forPost_rep_VO.setForPos_rep_time(rs.getTimestamp("forPos_req_time"));
				forPost_rep_VO.setForPos_rep_state(rs.getInt("forPos_rep_state"));
				forPost_rep_VO.setForPost_rep_reason(rs.getString("forPos_rep-reason"));

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
		return forPost_rep_VO;
	}
	
//**********************************************************************************************
	@Override
	public List<Forum_post_report_VO> getALL() {
		List<Forum_post_report_VO> list = new ArrayList<Forum_post_report_VO>();
		Forum_post_report_VO forPost_rep_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				forPost_rep_VO = new Forum_post_report_VO();
				forPost_rep_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPost_rep_VO.setMem_ID(rs.getString("mem_ID"));
				forPost_rep_VO.setForPos_rep_time(rs.getTimestamp("FORPOST_REP_TIME"));
				forPost_rep_VO.setForPos_rep_state(rs.getInt("FORPOST_REP_STATE"));
				forPost_rep_VO.setForPost_rep_reason(rs.getString("forPos_rep_reason"));
				list.add(forPost_rep_VO); // Store the row in the list
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
		return list;
	}

	//********************************************************************************************
	@Override
	public List<Forum_post_report_VO> getByStatus(String forPost_rep_states) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Forum_post_report_VO forPosRep_VO = null;
		List<Forum_post_report_VO> forPosRepList = new ArrayList();
		
		try {
			con = ds.getConnection();
			if("0".equals(forPost_rep_states)) {
				pstmt = con.prepareStatement(GETBYSTATUS + " FORPOST_REP_STATE = 0 ORDER BY forPost_rep_time DESC");
			} else{
				pstmt = con.prepareStatement(GETBYSTATUS + " FORPOST_REP_STATE != 0 ORDER BY forPost_rep_time DESC");
			}
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				forPosRep_VO = new Forum_post_report_VO();
				
				forPosRep_VO.setMem_ID(rs.getString("mem_id"));
				forPosRep_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPosRep_VO.setForPost_rep_reason(rs.getString("forPos_rep_reason"));
				forPosRep_VO.setForPos_rep_time(null);
				forPosRep_VO.setForPos_rep_state(rs.getInt("forPost_rep_state"));
				
				forPosRepList.add(forPosRep_VO);
			}
			
			while((forPosRepList.size()%10) > 0){
				forPosRep_VO = new Forum_post_report_VO();
				forPosRep_VO.setMem_ID("");
				forPosRepList.add(forPosRep_VO);
			}
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
		return forPosRepList;
	}

	
	//***************************************************************************************
	@Override
	public void closePos(Integer forPost_ID, Integer forPost_rep_states) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(CLOSEPOST);

			pstmt.setInt(1, forPost_rep_states);
			pstmt.setInt(2, forPost_ID);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}}
			if (con != null) {try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}}
		}
		
	}
	
	
	
}
