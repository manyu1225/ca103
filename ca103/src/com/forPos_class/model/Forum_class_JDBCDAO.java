package com.forPos_class.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class Forum_class_JDBCDAO implements Forum_class_DAO_interface  {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G2";
	String passwd = "123456";
	
	private static final String INSERT_STMT = 
			"INSERT INTO forum_class (forclass_ID, forClass_name) VALUES (emp2_seq.NEXTVAL, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT forclass_ID,forClass_name FROM forum_class order by forclass_ID";
		private static final String GET_ONE_STMT = 
			"SELECT forclass_ID,forClass_name FROM forum_class where forclass_ID = ?";
		private static final String DELETE = 
			"DELETE FROM forum_class where forclass_ID = ?";
		private static final String UPDATE = 
			"UPDATE forum_class set forclass_ID=?, forClass_name=?";
		@Override
		public void insert(Forum_class_VO forClass_VO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, forClass_VO.getForClass_ID());
				pstmt.setString(2, forClass_VO.getForClass_name());

				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
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
		public void delete(Integer forClass_ID) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, forClass_ID);

				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
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
		public void update(Forum_class_VO forClass_VO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(UPDATE);

				pstmt.setString(1, forClass_VO.getForClass_ID());
				pstmt.setString(2, forClass_VO.getForClass_name());
   
				pstmt.executeUpdate();

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
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
		public Forum_class_VO findByPrimaryKey(String forClass_ID) {
			Forum_class_VO forClass_VO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setString(1, forClass_ID);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVo 也稱為 Domain objects
					forClass_VO = new Forum_class_VO();
					forClass_VO.setForClass_ID(rs.getString("forClass_ID"));;
					forClass_VO.setForClass_name(rs.getString("forClass_name"));;
					
				}

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
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
			return forClass_VO;
		}
		@Override
		public List<Forum_class_VO> getAll() {
			List<Forum_class_VO> list = new ArrayList<Forum_class_VO>();
			Forum_class_VO forClass_VO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					// empVO 也稱為 Domain objects
					forClass_VO = new Forum_class_VO();
					forClass_VO.setForClass_ID(rs.getString("forClass_ID"));;
					forClass_VO.setForClass_name(rs.getString("forClass_name"));;
					
					list.add(forClass_VO); 
				}

				// Handle any driver errors
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
				// Handle any SQL errors
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
			return list;
		}
		
		
		public static void main(String[] args) {
			
			
			Forum_class_JDBCDAO dao = new Forum_class_JDBCDAO();
			
			
			
////			*******查全部********
//			List<Forum_class_VO> list = dao.getAll();
//			
//			for (Forum_class_VO forClass : list) {
//				System.out.println("類別編號: " + forClass.getForClass_ID() + ",");
//				System.out.println("類別名稱: " + forClass.getForClass_name() + ",");
//				
//				System.out.println("==================================================");
//			}
			
//			*******查一筆******
			
			Forum_class_VO forClass_VO = dao.findByPrimaryKey("C001");
			
			System.out.println("文章類別編號: " + forClass_VO.getForClass_ID());
			System.out.println("文章類別名稱: " + forClass_VO.getForClass_name());
			
			
			
			
			

			
			
			
			
		}


}
