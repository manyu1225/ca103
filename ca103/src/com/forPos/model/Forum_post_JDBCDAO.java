package com.forPos.model;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.forPos_pic.model.ForPost_picture_VO;

public class Forum_post_JDBCDAO implements Forum_post_DAO_interface {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO FORUM_POST"
			+ "(FORPOST_ID, MEM_ID, FORCLASS_ID, FORPOST_TIME,FORPOST_VIEW,FORPOST_CONTENT,FORPOST_THEME,FORPOST_STATE)"
			+ "values(FORUM_POST_SEQ.NEXTVAL, ?, ?, current_TIMESTAMP, forPos_view.NEXTVAL, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT FORPOST_ID, MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, "
			+ "FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE FROM FORUM_POST ORDER BY FORPOST_ID";
	
	private static final String GET_ONE_POS_BY_MEM =
			"SELECT FORPOST_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE "
			+ "FROM FORUM_POST WHERE MEM_ID=? order by FORPOST_ID";
	
//	private static final String GET_NEW_POST = 
//			"SELECT  MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE "
//					+ "FROM FORUM_POST WHERE FORPOST_ID=? ";

	private static final String GET_ONE_STMT = "SELECT FORPOST_ID, MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, "
			+ "FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE FROM FORUM_POST WHERE FORPOST_ID = ?";
	
	private static final String GET_VIEW_NUM = "UPDATE FORUM_POST SET FORPOST_VIEW= (FORPOST_VIEW+1) WHERE FORPOST_ID=?";

	private static final String GET_ClOB_PIC = "SELECT FORPOST_ID, MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, "
			+ "FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE FROM FORUM_POST WHERE FORPOST_ID = ?";

	private static final String DELETE = "DELETE FROM FORUM_POST WHERE FORPOST_ID = ?";

	private static final String UPDATE = "UPDATE FORUM_POST SET  FORCLASS_ID = ? , FORPOST_TIME=current_TIMESTAMP , "
			 + "FORPOST_CONTENT = ?, FORPOST_THEME = ? ,FORPOST_STATE = ? where FORPOST_ID = ?";
	
	
	

	@Override
	public void insert(Forum_post_VO forPostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, forPostVO.getMem_ID());
			pstmt.setString(2, forPostVO.getForClass_ID());
			pstmt.setString(3, forPostVO.getForPost_content());
			pstmt.setString(4, forPostVO.getForPost_theme());
			pstmt.setInt(5, forPostVO.getForPost_state());

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {

			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (pstmt != null) {

				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public void update(Forum_post_VO forPostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
//			pstmt.setString(1, forPostVO.getMem_ID());
			pstmt.setString(1, forPostVO.getForClass_ID());
			pstmt.setTimestamp(2, forPostVO.getForPost_time());
			pstmt.setString(3, forPostVO.getForPost_content());
			pstmt.setString(4, forPostVO.getForPost_theme());
			pstmt.setInt(5, forPostVO.getForPost_state());
			pstmt.setInt(6, forPostVO.getForPost_ID());

			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			
			e.getMessage();
			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public void delete(Integer forPost_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, forPost_ID);

			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public Forum_post_VO findByPrimaryKey(Integer forPost_id) {
		Forum_post_VO forum_post_vo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, forPost_id);
			rs = pstmt.executeQuery();
		
			
			while (rs.next()) {
				forum_post_vo = new Forum_post_VO();
				forum_post_vo.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_vo.setMem_ID(rs.getString("mem_ID"));
				forum_post_vo.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_vo.setForPost_time(rs.getTimestamp("forPost_time"));
				forum_post_vo.setForPost_view(rs.getInt("forPost_view"));
				forum_post_vo.setForPost_content(rs.getString("forPost_content"));
				forum_post_vo.setForPost_content(rs.getString("forPost_content"));
//				try {
//					forum_post_vo.setForPost_content(readString(rs.getString("forPost_content")));
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
				
				
				forum_post_vo.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_vo.setForPost_state(rs.getInt("forPost_state"));

			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return forum_post_vo;

	}
	
	
	@Override
	public List<Forum_post_VO> findPostByMem_ID(String mem_ID) {
		List<Forum_post_VO> list = new ArrayList<Forum_post_VO>();
		Forum_post_VO forum_post_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_POS_BY_MEM);
			pstmt.setString(1, mem_ID);
			rs = pstmt.executeQuery();
		
			
			while (rs.next()) {
				forum_post_vo = new Forum_post_VO();
				forum_post_vo.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_vo.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_vo.setForPost_time(rs.getTimestamp("forPost_time"));
				forum_post_vo.setForPost_view(rs.getInt("forPost_view"));
				forum_post_vo.setForPost_content(rs.getString("forPost_content"));
				
				
				
				forum_post_vo.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_vo.setForPost_state(rs.getInt("forPost_state"));
				list.add(forum_post_vo);

			}
			
		}catch(ClassNotFoundException e) {
			
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}
	

	@Override
	public List<Forum_post_VO> getAll() {
		List<Forum_post_VO> list = new ArrayList<Forum_post_VO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Forum_post_VO forum_post_VO = new Forum_post_VO();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				forum_post_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_VO.setMem_ID(rs.getString("mem_ID"));
				forum_post_VO.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_VO.setForPost_time(rs.getTimestamp("FORPOST_TIME"));
				forum_post_VO.setForPost_view(rs.getInt("forPost_view"));
				forum_post_VO.setForPost_content(rs.getString("forPost_content"));
				forum_post_VO.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_VO.setForPost_state(rs.getInt("forPost_state"));
				list.add(forum_post_VO);

			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}

		return list;
	}
	
	
	@Override
	public void updateView(Forum_post_VO forPostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			
//			 forPostVO = new Forum_post_VO();
			pstmt = con.prepareStatement(GET_VIEW_NUM);
//			pstmt.setInt(1, forPostVO.getForPost_view());
			pstmt.setInt(1, forPostVO.getForPost_ID());


			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			throw new RuntimeException("Could'nt load the driver" + e.getMessage());

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}

	}
	
	
	
	public Forum_post_VO findClobPic(Integer forPost_id) {
		
		
		Forum_post_VO forum_post_vo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, forPost_id);
			rs = pstmt.executeQuery();
		
			
			while (rs.next()) {
				forum_post_vo = new Forum_post_VO();
				forum_post_vo.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_vo.setMem_ID(rs.getString("mem_ID"));
				forum_post_vo.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_vo.setForPost_time(rs.getTimestamp("forPost_time"));
				forum_post_vo.setForPost_view(rs.getInt("forPost_view"));
				forum_post_vo.setForPost_content(rs.getString("forPost_content"));
				forum_post_vo.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_vo.setForPost_state(rs.getInt("forPost_state"));

			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return forum_post_vo;

	}
	
//	
////	@Override
////	public List<Forum_post_VO> findNewestPost(Integer forPost_ID) {
////		// TODO Auto-generated method stub
////		return null;
////	}
////	
	
	
	@Override
	public List<Forum_post_VO> getReportAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeforPos(Integer forPost_ID, Integer forPos_rep_state) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	////////////////////////////測試/////////////////////////////////////////////
	
	
	

	public static void main(String[] args) {

		
		
		
		Forum_post_VO forum_post_VO = new Forum_post_VO();
		Forum_post_JDBCDAO dao = new Forum_post_JDBCDAO();

//		******新增******
		
//		String content  = 
//				"2014 年 9 月，在一場籃球練習中摔斷了右側膝蓋髕骨韌帶，當下感覺膝蓋被鐵鎚重重敲了一下。我倒在地上發覺膝蓋骨移位，"
//				+ "想說應該去醫院推回去就好了。沒想到隔幾天照 MRI，醫生說髕骨韌帶斷了，當下我真的是眼前一片白，以為我這輩子都要瘸了。"
//				+ "將近一年的時間只能坐在家裡，看見外面大晴天會覺得很憂鬱，發現原來能走路是那麼幸福的事。最痛苦的是當孩子看著癱在沙發上的我說："
//				+ "「把拔，我們週末為什麼不能出門？」當下真的是心如刀割。      ";
//		forum_post_VO.setMem_ID("M000030");
//		forum_post_VO.setForPost_theme("yyyyyyy");
//		forum_post_VO.setForPost_content(content);
//		forum_post_VO.setForPost_state(1);
//		forum_post_VO.setForClass_ID("C003");
//		
//		dao.insert(forum_post_VO);
//		System.out.println("新增成功");

//		****************查一筆*******************
//
		 forum_post_VO = dao.findByPrimaryKey(5);
//		System.out.println("類別: " + forum_post_VO.getForClass_ID());
//			System.out.println("主題:" + forum_post_VO.getForPost_theme());
			System.out.println("觀看人數:" + forum_post_VO.getForPost_view());
		 
////		try {
//			System.out.println("文章內容:" + forum_post_VO.getForPost_content());
////		} catch (IOException e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		
//		System.out.println("文章內容:" + forum_post_VO.getForPost_content());
//		
//		
//		System.out.println("貼文發表時間:" + forum_post_VO.getForPost_time());
//		System.out.println("===========================================================================");
//		
		
//		************UPDATE***************
//		Forum_post_VO forum_post_VO2 = new Forum_post_VO();
//		forum_post_VO2.setForPost_ID(5);
//		forum_post_VO2.setMem_ID("M000090");
//		forum_post_VO2.setForClass_ID("C002");
////		forum_post_VO2.setForPost_view(forPost_view);
//		Timestamp time = new Timestamp(new java.util.Date().getTime());
//
//		forum_post_VO2.setForPost_time(time);
//		forum_post_VO2.setForPost_theme("修改假標題");
//		forum_post_VO2.setForPost_content("修改假內容");
//		forum_post_VO2.setForPost_state(new Integer(1));
//		dao.update(forum_post_VO2);
//		System.out.println("修改成功~!!!");
//		
		
		
//	 ***************查詢會員文章*****************	
//		List<Forum_post_VO> list =  dao.findPostByMem_ID("M000030");
//		
//		for(Forum_post_VO forum_post_VO : list) {
//			System.out.println("貼文發表時間: " + forum_post_VO.getForPost_time());
//			System.out.println("文章編號: " + forum_post_VO.getForPost_ID());
//			System.out.println("類別: " + forum_post_VO.getForClass_ID());
//			System.out.println("主題:" + forum_post_VO.getForPost_theme());
//			System.out.println("文章內容: " + forum_post_VO.getForPost_content());
//			System.out.println("===========================================================================");
//			
//		}
		
		
//*******************************刪除*************************
//		
//		dao.delete(25);
//		System.out.println("刪除成功!!!");
//		
//	
//		
//		
//	}
	
//	public static String readString(String str) throws IOException {
//		
//		
//		String str2 = str.substring(str.indexOf("base64") + 8, str.indexOf("data-filename")-2);
//		
//		System.out.println(str2);
//		
//		
//		return str2;
		
		
//***********************************手動新增觀看人數******************************
		
		
		forum_post_VO.setForPost_ID(5);
		dao.updateView(forum_post_VO);
		System.out.println("瀏覽人數增加~~~~!!!!");
		
		
		
		
		
	}

	@Override
	public Forum_post_VO getPostInState(Integer forPost_ID, String mem_ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Forum_post_VO> getAllByState(Integer forPost_state) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Forum_post_VO> getViewByDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertAndphoto(Forum_post_VO forPostVO, ForPost_picture_VO forPost_pictureVO) {
		// TODO Auto-generated method stub
		
	}





}
