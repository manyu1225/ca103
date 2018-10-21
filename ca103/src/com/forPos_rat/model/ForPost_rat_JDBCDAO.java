package com.forPos_rat.model;

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


public class ForPost_rat_JDBCDAO implements ForPost_rat_DAO_interface {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G2";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
		"INSERT INTO FORPOST_RAT (forPost_rat_ID,forPost_ID,mem_ID,pos_rate_ID,neg_rat_ID) VALUES (forPost_rat_seq.NEXTVAL, FORUM_POST_SEQ.NEXTVAL, mem_SEQ.NEXTVAL, ?, ?)";
	private static final String GET_ALL_STMT = 
		"SELECT forPost_rat_ID,forPost_ID,mem_ID,pos_rate_ID,neg_rat_ID FROM FORPOST_RAT order by forPost_rat_ID";
	private static final String GET_ONE_STMT = 
		"SELECT forPost_rat_ID,forPost_ID,mem_ID,pos_rate_ID,neg_rat_ID FROM FORPOST_RAT where forPost_rat_ID = ?";
	private static final String DELETE = 
		"DELETE FROM FORPOST_RAT where forPost_rat_ID = ?";
	private static final String UPDATE = 
		"UPDATE FORPOST_RAT set forPost_ID = ?, mem_ID = ?, pos_rate_ID = ?, neg_rat_ID = ? where forPost_rat_ID = ?";
	@Override
	public void insert(ForPost_rat_VO forPost_rat_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, forPost_rat_VO.getForPost_rat_ID());
			pstmt.setInt(2, forPost_rat_VO.getForPost_ID());
			pstmt.setString(3, forPost_rat_VO.getMem_ID());
			pstmt.setInt(4, forPost_rat_VO.getPos_rat_ID());
			pstmt.setInt(5, forPost_rat_VO.getNeg_rat_ID());

			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());

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
	public void update(ForPost_rat_VO forPost_rat_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, forPost_rat_VO.getForPost_ID());
			pstmt.setString(2, forPost_rat_VO.getMem_ID());
			pstmt.setInt(3, forPost_rat_VO.getPos_rat_ID());
			pstmt.setInt(4, forPost_rat_VO.getNeg_rat_ID());
			pstmt.setInt(5, forPost_rat_VO.getForPost_rat_ID());


			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public void delete(Integer forPost_rat_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, forPost_rat_ID);

			pstmt.executeUpdate();


		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		
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
	};

	@Override
	public ForPost_rat_VO findByPrimaryKey(Integer forPost_rat_ID) {
		ForPost_rat_VO forPost_rat_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {


			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, forPost_rat_ID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				forPost_rat_VO = new ForPost_rat_VO();
				forPost_rat_VO.setForPost_ID(rs.getInt("forPost_rat_ID"));
				forPost_rat_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPost_rat_VO.setMem_ID(rs.getString("mem_ID"));
				forPost_rat_VO.setPos_rat_ID(rs.getInt("pos_rat_ID"));
				forPost_rat_VO.setNeg_rat_ID(rs.getInt("neg_rat_ID"));
			}
			
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());

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
		return forPost_rat_VO;	}
	@Override
	public List<ForPost_rat_VO> getAll() {
		List<ForPost_rat_VO> list = new ArrayList<ForPost_rat_VO>();
		ForPost_rat_VO forPost_rat_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				forPost_rat_VO = new ForPost_rat_VO();
				forPost_rat_VO.setForPost_ID(rs.getInt("forPost_rat_ID"));
				forPost_rat_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPost_rat_VO.setMem_ID(rs.getString("mem_ID"));
				forPost_rat_VO.setPos_rat_ID(rs.getInt("pos_rat_ID"));
				forPost_rat_VO.setNeg_rat_ID(rs.getInt("neg_rat_ID"));
				list.add(forPost_rat_VO); // Store the row in the list
			}
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
