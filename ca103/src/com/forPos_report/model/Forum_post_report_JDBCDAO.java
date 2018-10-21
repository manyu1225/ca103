package com.forPos_report.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Forum_post_report_JDBCDAO implements Forum_post_report_DAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO FORUM_POST_REPORT (forPost_ID, mem_ID, FORPOST_REP_TIME,FORPOS_REP_REASON) VALUES (?, ?, current_timestamp, ?)";
	private static final String GET_ALL_STMT = "SELECT forPost_ID,mem_ID,forPos_rep_state,forPost_reason FROM FORUM_POST_REPORT order by forPos_rep_time";
	private static final String GET_ONE_STMT = "SELECT forPost_ID,mem_ID,forPos_rep_state,forPost_reason FROM FORUM_POST_REPORT where forPost_ID = ?, mem_ID = ?";
	private static final String DELETE = "DELETE FROM FORUM_POST_REPORT where forPost_ID = ?, mem_ID = ? = ?";
	private static final String UPDATE = "UPDATE FORUM_POST_REPORT set forPos_rep_state = ?,forPost_reason = ? where forPost_ID = ?, mem_ID = ?";

	@Override
	public void insert(Forum_post_report_VO forPost_rep_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, forPost_rep_VO.getForPost_ID());
			pstmt.setString(2, forPost_rep_VO.getMem_ID());
			pstmt.setString(3, forPost_rep_VO.getForPost_rep_reason());

			pstmt.executeUpdate();
			
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setTimestamp(1, forPost_rep_VO.getForPos_rep_time());
			pstmt.setInt(2, forPost_rep_VO.getForPos_rep_state());
			pstmt.setString(3, forPost_rep_VO.getForPost_rep_reason());
			pstmt.setInt(6, forPost_rep_VO.getForPost_ID());
			pstmt.setString(7, forPost_rep_VO.getMem_ID());

			pstmt.executeUpdate();

		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, forPost_ID);
			pstmt.setString(2, mem_ID);

			pstmt.executeUpdate();

		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

	@Override
	public List<Forum_post_report_VO> getALL() {
		List<Forum_post_report_VO> list = new ArrayList<Forum_post_report_VO>();
		Forum_post_report_VO forPost_rep_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO �]�٬� Domain objects
				forPost_rep_VO = new Forum_post_report_VO();
				forPost_rep_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPost_rep_VO.setMem_ID(rs.getString("mem_ID"));
				forPost_rep_VO.setForPos_rep_time(rs.getTimestamp("forPos_rep_time"));
				forPost_rep_VO.setForPos_rep_state(rs.getInt("forPos_rep_state"));
				forPost_rep_VO.setForPost_rep_reason(rs.getString("forPos_rep_reason"));
				list.add(forPost_rep_VO); // Store the row in the list
			}


		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public List<Forum_post_report_VO> getByStatus(String forPost_rep_states) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closePos(Integer forPost_ID, Integer forPost_rep_states) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		
		Forum_post_report_JDBCDAO dao = new Forum_post_report_JDBCDAO();
		Forum_post_report_VO forPost_rep_VO = new Forum_post_report_VO();
		forPost_rep_VO.setForPost_ID(15);
		forPost_rep_VO.setMem_ID("M000085");
		forPost_rep_VO.setForPost_rep_reason("騷擾");
		dao.insert(forPost_rep_VO);
		System.out.println("新增成功");
		
	}

}
