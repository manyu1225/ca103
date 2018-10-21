package com.forPos_res_report.model;

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

import com.forPos_report.model.Forum_post_report_VO;


public class Forum_res_report_DAO implements Forum_res_report_DAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO Forum_res_report (for_res_ID,mem_ID, forRes_rep_time, forRes_rep_state, forRes_rep_reason) VALUES (?, ?, current_timestamp, 1, ?)";
	private static final String GET_ALL_STMT = "SELECT forRes_ID,mem_ID,ForRes_REP_state,forRes_rep_reason,to_char(forRes_Rep_time,'DD.MM.YYYY HH24:MI:SSFF3') forRes_Rep_time FROM Forum_res_report order by forRes_rep_time";
	private static final String GET_ONE_STMT = "SELECT forRes_ID,mem_ID,ForRes_REP_state,forRes_rep_reason,forRes_rep_reason,to_char(forRes_Rep_time,'DD.MM.YYYY HH24:MI:SSFF3') forRes_Rep_time FROM Forum_res_report where forRes_ID = ?, mem_ID = ?";
	private static final String DELETE = "DELETE FROM FORUM_POST_REPORT where forRes_ID = ?, mem_ID = ? ";
	private static final String UPDATE = "UPDATE FORUM_POST_REPORT set forRes_rep_time, forRes_rep_state = ?,forRes_rep_reason = ? where forRes_ID = ?, mem_ID = ?";
	
	//****************************************
	private static final String GETBYSTATUS = 
			"SELECT * FROM Forum_res_report WHERE ";
	private static final String GETALL = 
			"SELECT * FROM Forum_res_report WHERE FORRES_REP_STATE=0 order by FORRES_REP_TIME";
	private static final String CLOSRES = 
			"UPDATE Forum_res_report set FORRES_REP_STATE = ?where FOR_RES_ID = ?";
	//******************************************************

	@Override
	public void insert(Forum_res_report_VO forRes_rep_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, forRes_rep_VO.getForRes_ID());
			pstmt.setString(2, forRes_rep_VO.getMem_ID());
//			pstmt.setInt(3, forRes_rep_VO.getForRes_rep_state());
			pstmt.setString(3, forRes_rep_VO.getForRes_rep_reason());

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
	public void update(Forum_res_report_VO forRes_rep_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, forRes_rep_VO.getForRes_rep_state());
			pstmt.setInt(2, forRes_rep_VO.getForRes_rep_state());
			pstmt.setString(3, forRes_rep_VO.getForRes_rep_reason());
			pstmt.setTimestamp(4, forRes_rep_VO.getForRes_req_time());
			pstmt.setInt(5, forRes_rep_VO.getForRes_ID());
			pstmt.setString(6, forRes_rep_VO.getMem_ID());

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
	public void delete(Integer forRes_ID, String mem_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, forRes_ID);
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
	public Forum_res_report_VO findByPrimaryKey(Integer forRes_ID, String mem_ID) {
		Forum_res_report_VO forRes_rep_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, forRes_ID);
			pstmt.setString(2, mem_ID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo �]�٬� Domain objects
				forRes_rep_VO = new Forum_res_report_VO();
				forRes_rep_VO.setForRes_ID(rs.getInt("forRes_ID"));
				forRes_rep_VO.setMem_ID(rs.getString("mem_ID"));
				forRes_rep_VO.setForRes_rep_state(rs.getInt("forRes+rep_state"));
				forRes_rep_VO.setForRes_rep_reason(rs.getString("forRes_rep_reason"));
				forRes_rep_VO.setForRes_req_time(rs.getTimestamp("Forres_rep_time"));
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
		return forRes_rep_VO;
	}

	@Override
	public List<Forum_res_report_VO> getAll() {
		List<Forum_res_report_VO> list = new ArrayList<Forum_res_report_VO>();
		Forum_res_report_VO forRes_rep_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				forRes_rep_VO = new Forum_res_report_VO();
				forRes_rep_VO.setForRes_ID(rs.getInt("forRes_ID"));
				forRes_rep_VO.setMem_ID(rs.getString("mem_ID"));
				forRes_rep_VO.setForRes_rep_state(rs.getInt("forRes_rep_state"));
				forRes_rep_VO.setForRes_rep_reason(rs.getString("forRes_rep_time"));
				list.add(forRes_rep_VO); 
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
	
	
//****************************************************************************
	@Override
	public List<Forum_res_report_VO> getResRepAll() {
		List<Forum_res_report_VO> list = new ArrayList<Forum_res_report_VO>();
		Forum_res_report_VO forRes_rep_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				forRes_rep_VO = new Forum_res_report_VO();
				System.out.println(rs.getInt("FOR_RES_ID"));
				forRes_rep_VO.setForRes_ID(rs.getInt("FOR_RES_ID"));
				System.out.println(rs.getString("MEM_ID"));
				forRes_rep_VO.setMem_ID(rs.getString("MEM_ID"));
				forRes_rep_VO.setForRes_rep_state(rs.getInt("FORRES_REP_STATE"));
				forRes_rep_VO.setForRes_rep_reason(rs.getString("FORRES_REP_REASON"));
				forRes_rep_VO.setForRes_req_time(rs.getTimestamp("FORRES_REP_TIME"));
				list.add(forRes_rep_VO); 
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs != null) {try {rs.close();} catch (SQLException se) {se.printStackTrace(System.err);}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}}
			if (con != null) {try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}}
		}
		return list;
	}
	
//*************************************************************************
	@Override
	public List<Forum_res_report_VO> getByStatus(String forRes_Rep_State) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Forum_res_report_VO forResRep_VO = null;
		List<Forum_res_report_VO> forResRepList = new ArrayList();
		
		try {
			con = ds.getConnection();
			if("0".equals(forRes_Rep_State)) {
				pstmt = con.prepareStatement(GETBYSTATUS + " FORRES_REP_STATE = 0 ORDER BY FORRES_REP_TIME DESC");
			} else{
				pstmt = con.prepareStatement(GETBYSTATUS + " FORRES_REP_STATE != 0 ORDER BY FORRES_REP_TIME DESC");
			}
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				forResRep_VO = new Forum_res_report_VO();
				
				forResRep_VO.setMem_ID(rs.getString("MEM_ID"));
				forResRep_VO.setForRes_ID(rs.getInt("FOR_RES_ID"));
				forResRep_VO.setForRes_rep_reason(rs.getString("FORRES_REP_REASON"));
				forResRep_VO.setForRes_req_time(null);
				forResRep_VO.setForRes_rep_state(rs.getInt("FORRES_REP_STATE"));
				
				forResRepList.add(forResRep_VO);
			}
			
			while((forResRepList.size()%10) > 0){
				forResRep_VO = new Forum_res_report_VO();
				forResRep_VO.setMem_ID("");
				forResRepList.add(forResRep_VO);
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
		return forResRepList;
	}
	
//*********************************************************************************
	@Override
	public void closeRes(Integer forRes_ID, Integer forRes_rep_state) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(CLOSRES);

			pstmt.setInt(1, forRes_rep_state);
			pstmt.setInt(2, forRes_ID);

			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}}
			if (con != null) {try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}}
		}
	}

}
