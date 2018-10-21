package com.forPos_fav.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class Forum_post_fav_JDBCDAO implements Forum_post_fav_DAO_interface {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "CA103G2";
		String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO FORUM_POST_FAV (forPost_ID,mem_ID,FORPOST_FAV_TIME) VALUES (?, ?, current_TIMESTAMP)";
	private static final String GET_ALL_STMT = "SELECT forPost_ID,mem_ID,FORPOST_FAV_TIME FROM FORUM_POST_FAV where mem_id=? order by FORPOST_FAV_TIME desc";
	private static final String GET_ONE_STMT = "SELECT forPost_ID, mem_ID, forPost_fav_time FROM FORUM_POST_FAV where forPost_ID=? and mem_ID = ?";
	private static final String DELETE = "DELETE FROM FORUM_POST_FAV where forPost_ID = ? and mem_ID= ? ";

	// UPDATE

	@Override
	public void insert(Forum_post_fav_VO forPost_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setInt(1, forPost_VO.getForPost_ID());
			pstmt.setString(2, forPost_VO.getMem_ID());


			pstmt.executeUpdate();
			
			
		}catch(ClassNotFoundException e){
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
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		Forum_post_fav_VO fav_VO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				fav_VO = new Forum_post_fav_VO();
				fav_VO.setForPost_ID(rs.getInt("forPost_ID"));
				fav_VO.setMem_ID(rs.getString("mem_ID"));
				fav_VO.setForPost_fav_time(rs.getTimestamp("forPost_fav_time"));
				list.add(fav_VO); 
			}
			
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	
	
//	******************************////  Testify   \\\\***********************************
	
	
	
	public static void main(String[] args) {
		
		
		Forum_post_fav_JDBCDAO dao = new Forum_post_fav_JDBCDAO();
//		Forum_post_fav_VO fav_VO = new Forum_post_fav_VO(); 
		
//		fav_VO.setForPost_ID(17);
//		fav_VO.setMem_ID("M000040");
//		
//		dao.insert(fav_VO);
//		
//		System.out.println("新增收藏成功");
		
		
		//取消收藏
		
		
	
//		dao.delete(17, "M000040");
//		
//		System.out.println("取消收藏成功");

		
		
		
		List<Forum_post_fav_VO> list = dao.getAll("M000904");
		
		System.out.println("list" + list);
		for(Forum_post_fav_VO fav_VO:list) {
			
			System.out.println("收藏會員編號：" +  fav_VO.getMem_ID());
			System.out.println("收藏文章：" + fav_VO.getForPost_ID());
			System.out.println("收藏日期：" + fav_VO.getForPost_fav_time());

			System.out.println("====================================");
			
		}
		
		
//		fav_VO = dao.findByPrimaryKey(14, "M000110");
//		System.out.println("收藏文章編號： " + fav_VO.getForPost_ID());
//		System.out.println("收藏會員編號： " + fav_VO.getMem_ID());
//		System.out.println("收藏時間： " + fav_VO.getForPost_fav_time());
//		
//		System.out.println("查詢單筆成功");
//		
		
		
		
	}

	
}
