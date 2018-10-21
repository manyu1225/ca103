package com.forPos_res.model;

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


public class Forum_response_DAO implements Forum_response_DAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO FORUM_RESPONSE (forRes_ID, forPost_ID, mem_ID, forRes_content, forRes_time, forRes_rating, FORRES_STATE) VALUES (forum_response_seq.NEXTVAL, ?, ?, ?, current_timestamp, ?, 1)";
	private static final String GET_ALL_STMT = "SELECT forRes_ID, forPost_ID, mem_ID,  forRes_content, forRes_time, forRes_rating FROM FORUM_RESPONSE order by forRes_time desc ";
	private static final String GET_ONE_STMT = "SELECT forRes_ID, forPost_ID,mem_ID,  forRes_content, forRes_time, forRes_rating,  FROM FORUM_RESPONSE where forRes_ID = ?";
	private static final String GET_RES_BY_POS_ID = "SELECT forRes_ID, forPost_ID ,mem_ID,  forRes_content, forRes_time, forRes_rating  FROM FORUM_RESPONSE where forPost_ID  = ? order by forRes_time desc";
	private static final String DELETE = "DELETE FROM FORUM_RESPONSE where forRes_ID = ?";
	private static final String UPDATE = "UPDATE FORUM_RESPONSE set forRes_content = ?, forRes_time = current_timestamp where forRes_ID = ?";
	//*************************************************
	private static final String CLOSE = "UPDATE FORUM_RESPONSE SET FORRES_STATE = ? WHERE forRes_ID = ?";
	private static final String GET_ALL = "SELECT * FROM FORUM_RESPONSE order by FORRES_TIME desc ";
	
	
	@Override
	public void insert(Forum_response_VO forRes_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, forRes_VO.getForPost_ID());
			pstmt.setString(2, forRes_VO.getMem_ID());
			pstmt.setString(3, forRes_VO.getForRes_content());
			pstmt.setInt(4, forRes_VO.getForRes_rating());

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
	public void update(Forum_response_VO forRes_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, forRes_VO.getForRes_content());
			pstmt.setInt(2, forRes_VO.getForRes_ID());


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
	public void delete(Integer forRes_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, forRes_ID);

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
	public Forum_response_VO findByPrimaryKey(Integer forRes_ID) {
		Forum_response_VO forRes_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, forRes_ID);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				forRes_VO = new Forum_response_VO();
				forRes_VO.setForRes_ID(rs.getInt("forRes_ID"));
				forRes_VO.setMem_ID(rs.getString("forPost_ID"));
				forRes_VO.setForRes_time(rs.getTimestamp("forRes_time"));
				forRes_VO.setForRes_rating(rs.getInt("forRes_rating"));

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
		return forRes_VO;
	}
	
	
	
	@Override
	public List<Forum_response_VO> findByPost_ID(Integer forPost_ID) {
		
		List<Forum_response_VO> list = new ArrayList<Forum_response_VO>();
		Forum_response_VO forRes_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_RES_BY_POS_ID);

			pstmt.setInt(1, forPost_ID);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				forRes_VO = new Forum_response_VO();
				forRes_VO.setForRes_ID(rs.getInt("forRes_ID"));
				forRes_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forRes_VO.setMem_ID(rs.getString("mem_ID"));
				forRes_VO.setForRes_content(rs.getString("forRes_content"));
				forRes_VO.setForRes_time(rs.getTimestamp("forRes_time"));
				forRes_VO.setForRes_rating(rs.getInt("forRes_rating"));
				list.add(forRes_VO);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public List<Forum_response_VO> getAll() {
		List<Forum_response_VO> list = new ArrayList<Forum_response_VO>();
		Forum_response_VO forRes_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				forRes_VO = new Forum_response_VO();
				forRes_VO.setForRes_ID(rs.getInt("forRes_ID"));
				forRes_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forRes_VO.setMem_ID(rs.getString("mem_ID"));
				forRes_VO.setForRes_content(rs.getString("forRes_content"));
				forRes_VO.setForRes_time(rs.getTimestamp("forRes_time"));
				forRes_VO.setForRes_rating(rs.getInt("forRes_rating"));
				list.add(forRes_VO);
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
	
//**************************************************************************
	@Override
	public void closeforRes(Integer forRes_ID, Integer forRes_rep_state) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CLOSE);
			pstmt.setInt(1, forRes_rep_state);
			pstmt.setInt(2, forRes_ID);
			pstmt.executeUpdate();

			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
			if (con != null) {try {con.close();} catch (SQLException e) {e.printStackTrace();}}
		}
	}

}
