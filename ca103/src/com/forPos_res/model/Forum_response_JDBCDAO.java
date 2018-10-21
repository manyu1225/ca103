package com.forPos_res.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Forum_response_JDBCDAO implements Forum_response_DAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G2";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO FORUM_RESPONSE (forRes_ID, forPost_ID, mem_ID, forRes_content, forRes_time, forRes_rating) VALUES (forum_response_seq.NEXTVAL, ?, ?, ?, current_timestamp, ?)";
	private static final String GET_ALL_STMT = "SELECT forRes_ID, forPost_ID, mem_ID,  forRes_content, forRes_time, forRes_rating FROM FORUM_RESPONSE order by forRes_ID";
	private static final String GET_ONE_STMT = "SELECT forRes_ID, forPost_ID, mem_ID,  forRes_content, forRes_time, forRes_rating FROM FORUM_RESPONSE where forRes_ID = ?";
	private static final String GET_RES_BY_POS_ID = "SELECT forRes_ID, forPost_ID ,mem_ID,  forRes_content, forRes_time, forRes_rating  FROM FORUM_RESPONSE where forPost_ID = ?";
	private static final String DELETE = "DELETE FROM FORUM_RESPONSE where forRes_ID = ?";
	private static final String UPDATE = "UPDATE FORUM_RESPONSE set forRes_content = ?, forRes_time = current_timestamp where forRes_ID = ?";

	@Override
	public void insert(Forum_response_VO forRes_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, forRes_VO.getForPost_ID());
			pstmt.setString(2, forRes_VO.getMem_ID());
			pstmt.setString(3, forRes_VO.getForRes_content());
			pstmt.setInt(4, forRes_VO.getForRes_rating());

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
	public void update(Forum_response_VO forRes_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, forRes_VO.getForRes_content());
			pstmt.setInt(2, forRes_VO.getForRes_ID());


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
	public void delete(Integer forRes_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, forRes_ID);

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
	public Forum_response_VO findByPrimaryKey(Integer forRes_ID) {
		Forum_response_VO forRes_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);			
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
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
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
	
	
	public static void main(String[] args) {
		
		Forum_response_JDBCDAO dao = new Forum_response_JDBCDAO();
//		Forum_response_VO forRes_VO = new Forum_response_VO();
		
////		新增一筆留言
//		
//		forRes_VO.setForPost_ID(9);
//		forRes_VO.setMem_ID("M000086");
//		forRes_VO.setForRes_content("--------二樓推");
//		forRes_VO.setForRes_rating(1);
//		dao.insert(forRes_VO);
//		System.out.println("新增成功");
		
//		udadate testify
		
//		forRes_VO.setForRes_ID(7);
//		forRes_VO.setForRes_content("修改回應測試");
//		dao.update(forRes_VO);
//		System.out.println("修改成功");
		
		
//		delete testify
		
//		dao.delete(8);
//		System.out.println("刪除成功");
//
//		List<Forum_response_VO>list = dao.getAll();
//////		查全部
//		for(Forum_response_VO forRes_VO : list) {
//			
//			System.out.println("留言編號: " + forRes_VO.getForRes_ID());
//			System.out.println("留言會員: " + forRes_VO.getMem_ID());
//			System.out.println("留言內容: " + forRes_VO.getForRes_content());
//			System.out.println("留言文章編號: " + forRes_VO.getForPost_ID());
//			System.out.println("留言時間: " + forRes_VO.getForRes_time());
//			System.out.println("=====================================");
//			
//			
//			
//			}
//		
		
		 List<Forum_response_VO> list = dao.findByPost_ID(117);
		 
		 
		 System.out.println(list);
		 
		 for(Forum_response_VO forRes_VO : list) {
			 System.out.println("留言編號: " + forRes_VO.getForRes_ID());
				System.out.println("留言會員: " + forRes_VO.getMem_ID());
				System.out.println("留言內容: " + forRes_VO.getForRes_content());
				System.out.println("留言文章編號: " + forRes_VO.getForPost_ID());
				System.out.println("留言時間: " + forRes_VO.getForRes_time());
				System.out.println("=====================================");
		 }
		 
//	
		
		
		
		
		
	}

	@Override
	public void closeforRes(Integer forRes_ID, Integer forRes_rep_state) {
		// TODO Auto-generated method stub
		
	}

}
