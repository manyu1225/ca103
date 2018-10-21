package com.forPos_fav.model;

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


public class Forum_post_fav_DAO implements Forum_post_fav_DAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT_STMT = "INSERT INTO FORUM_POST_FAV (forPost_ID,mem_ID,FORPOST_FAV_TIME) VALUES (?, ?, current_TIMESTAMP)";
	private static final String GET_ALL_STMT = "SELECT forPost_ID,mem_ID,FORPOST_FAV_TIME FROM FORUM_POST_FAV where MEM_ID=? order by FORPOST_FAV_TIME desc";
	private static final String GET_ONE_STMT = "SELECT forPost_ID, mem_ID, forPost_fav_time FROM FORUM_POST_FAV where forPost_ID=? and mem_ID = ?";
	private static final String DELETE = "DELETE FROM FORUM_POST_FAV where forPost_ID = ? and mem_ID= ? ";

	// UPDATE

	@Override
	public void insert(Forum_post_fav_VO forPost_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, forPost_VO.getForPost_ID());
			pstmt.setString(2, forPost_VO.getMem_ID());


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

	// @Override
	// public void update(Forum_post_fav_VO FORPOST_VO) {
	// // TODO Auto-generated method stub
	//
	// }

	// Multiple PK
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
	public Forum_post_fav_VO findByPrimaryKey(Integer forPost_ID, String mem_ID) {
		Forum_post_fav_VO forPost_VO = null;
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
				forPost_VO = new Forum_post_fav_VO();
				forPost_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPost_VO.setMem_ID(rs.getString("mem_ID"));
				forPost_VO.setForPost_fav_time(rs.getTimestamp("forPost_fav_time"));

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return forPost_VO;
	}

	@Override
	public List<Forum_post_fav_VO> getAll(String mem_id) {
		List<Forum_post_fav_VO> list = new ArrayList<Forum_post_fav_VO>();
		Forum_post_fav_VO forPost_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			

			while (rs.next()) {
				forPost_VO = new Forum_post_fav_VO();
				forPost_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPost_VO.setMem_ID(rs.getString("mem_ID"));
				forPost_VO.setForPost_fav_time(rs.getTimestamp("forPost_fav_time"));
				list.add(forPost_VO);  
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
