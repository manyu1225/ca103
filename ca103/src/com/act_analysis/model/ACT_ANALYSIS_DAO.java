package com.act_analysis.model;

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


public class ACT_ANALYSIS_DAO implements ACT_ANALYSIS_DAO_interface  {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_STMT = "INSERT INTO ACT_ANALYSIS (act_analysis_ID, act_ID) VALUES (act_analysis_ID.NEXTVAL, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT act_analysis_ID , act_ID FROM ACT_ANALYSIS order by act_analysis_ID = ?";
	private static final String GET_ONE_STMT = "SELECT act_analysis_ID , act_ID FROM ACT_ANALYSIS where act_analysis_ID = ?";
	
	private static final String DELETE = "DELETE FROM ACT_ANALYSIS where act_analysis_ID = ?";
	
	private static final String UPDATE = "UPDATE ACT_ANALYSIS act_ID set  where act_analysis_ID = ?";

	@Override
	public void insert(ACT_ANALYSIS_VO act_ANALYSIS_VO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, act_ANALYSIS_VO.getAct_analysis_ID());
			pstmt.setInt(2, act_ANALYSIS_VO.getAct_ID());
			

			pstmt.executeUpdate();

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
	public void delete(Integer act_analysis_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, act_analysis_ID);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void update(ACT_ANALYSIS_VO act_ANALYSIS_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;


		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, act_ANALYSIS_VO.getAct_ID());
			pstmt.setInt(2, act_ANALYSIS_VO.getAct_analysis_ID());
		


			pstmt.executeUpdate();

			// Handle any driver errors
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
	public ACT_ANALYSIS_VO findPrimaryKey(Integer act_analysis_ID) {
		ACT_ANALYSIS_VO act_ANALYSIS_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			act_ANALYSIS_VO = new ACT_ANALYSIS_VO();
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, act_analysis_ID);

			rs = pstmt.executeQuery();


			while (rs.next()) {
				act_ANALYSIS_VO = new ACT_ANALYSIS_VO();
				act_ANALYSIS_VO.setAct_analysis_ID(rs.getInt("getAct_analysis_ID"));
				act_ANALYSIS_VO.setAct_analysis_ID(rs.getInt("act_ID"));
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return act_ANALYSIS_VO;
	}

	@Override
	public List<ACT_ANALYSIS_VO> getAll() {
		List<ACT_ANALYSIS_VO> list = new ArrayList<ACT_ANALYSIS_VO>();
		ACT_ANALYSIS_VO act_ANALYSIS_VO  = null;
	
	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				act_ANALYSIS_VO = new ACT_ANALYSIS_VO();
				act_ANALYSIS_VO.setAct_analysis_ID(rs.getInt("act_analysis_ID"));
				act_ANALYSIS_VO.setAct_ID(rs.getInt("act_ID"));
				list.add(act_ANALYSIS_VO); // Store the row in the list
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
}
