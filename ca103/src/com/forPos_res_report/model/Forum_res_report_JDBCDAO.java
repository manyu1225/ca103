package com.forPos_res_report.model;

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


public class Forum_res_report_JDBCDAO implements Forum_res_report_DAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO Forum_res_report (for_res_ID,mem_ID,forRes_rep_state,forRes_rep_reason,forRes_rep_time) VALUES (forum_response_seq.NEXTVAL, SP01.mem_seq, ?, ?, ?, ?, current_timestamp)";
	private static final String GET_ALL_STMT = "SELECT forRes_ID,mem_ID,ForRes_REP_state,forRes_rep_reason,to_char(forRes_Rep_time,'DD.MM.YYYY HH24:MI:SSFF3') forRes_Rep_time FROM Forum_res_report order by forRes_rep_time";
	private static final String GET_ONE_STMT = "SELECT forRes_ID,mem_ID,ForRes_REP_state,forRes_rep_reason,forRes_rep_reason,to_char(forRes_Rep_time,'DD.MM.YYYY HH24:MI:SSFF3') forRes_Rep_time FROM Forum_res_report where forRes_ID = ?, mem_ID = ?";
	private static final String DELETE = "DELETE FROM FORUM_POST_REPORT where forRes_ID = ?, mem_ID = ? ";
	private static final String UPDATE = "UPDATE FORUM_POST_REPORT set forRes_rep_time, forRes_rep_state = ?,forRes_rep_reason = ? where forRes_ID = ?, mem_ID = ?";

	@Override
	public void insert(Forum_res_report_VO forRes_rep_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, forRes_rep_VO.getForRes_ID());
			pstmt.setString(2, forRes_rep_VO.getMem_ID());
			pstmt.setInt(3, forRes_rep_VO.getForRes_rep_state());
			pstmt.setInt(4, forRes_rep_VO.getForRes_rep_state());
			pstmt.setString(5, forRes_rep_VO.getForRes_rep_reason());
			pstmt.setTimestamp(6, forRes_rep_VO.getForRes_req_time());

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
	public void update(Forum_res_report_VO forRes_rep_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, forRes_rep_VO.getForRes_rep_state());
			pstmt.setInt(2, forRes_rep_VO.getForRes_rep_state());
			pstmt.setString(3, forRes_rep_VO.getForRes_rep_reason());
			pstmt.setTimestamp(4, forRes_rep_VO.getForRes_req_time());
			pstmt.setInt(5, forRes_rep_VO.getForRes_ID());
			pstmt.setString(6, forRes_rep_VO.getMem_ID());

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
	public void delete(Integer forRes_ID, String mem_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, forRes_ID);
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
	public Forum_res_report_VO findByPrimaryKey(Integer forRes_ID, String mem_ID) {
		Forum_res_report_VO forRes_rep_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
	public List<Forum_res_report_VO> getResRepAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Forum_res_report_VO> getByStatus(String forPos_rep_state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeRes(Integer forRes_ID, Integer forRes_rep_state) {
		// TODO Auto-generated method stub
		
	}

}
