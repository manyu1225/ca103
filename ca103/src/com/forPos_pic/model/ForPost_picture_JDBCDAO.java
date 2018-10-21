package com.forPos_pic.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;





public class ForPost_picture_JDBCDAO implements ForPost_picture_DAO_interface {


	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "G2";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO FORPOST_PICTURE (FORPOSTPIC_ID,FORPOST_ID,FORPOSTPIC) VALUES (FORPOST_PICTURE_SEQ.NEXTVAL, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT forPostPic_ID,forPost_ID,forPostPic FROM FORPOST_PICTURE order by forPostPic_ID";
	private static final String GET_ONE_STMT = "SELECT forPostPic_ID,forPost_ID,forPostPic FROM FORPOST_PICTURE where forPostPic_ID = ?";
	private static final String DELETE = "DELETE FROM FORPOST_PICTURE where forPostPic_ID = ?";
	private static final String UPDATE = "UPDATE FORPOST_PICTURE set forPost_ID=?, forPostPic=? where forPostPic_ID = ?";

	@Override
	public void insert(ForPost_picture_VO forPos_pic_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;
//		String picName = "focus.jpg";

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

		
			pstmt.setInt(1, forPos_pic_VO.getForPost_ID());			
			pstmt.setBytes(2, forPos_pic_VO.getForPostPic());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void update(ForPost_picture_VO forPos_pic_VO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setInt(1, forPos_pic_VO.getForPost_ID());
			pstmt.setBytes(2, forPos_pic_VO.getForPostPic());
			pstmt.setInt(3, forPos_pic_VO.getForPostPic_ID());

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
	public void delete(Integer forPsotPic_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, forPsotPic_ID);
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
	public ForPost_picture_VO findByPrimaryKey(Integer forPsotPic_ID) {
		ForPost_picture_VO forPos_pic_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, forPsotPic_ID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo == Domain objects
				forPos_pic_VO = new ForPost_picture_VO();
				forPos_pic_VO.setForPostPic_ID(rs.getInt("forPsotPic_ID"));
				forPos_pic_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPos_pic_VO.setForPostPic(rs.getBytes("forPostPic"));;
				
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
		return forPos_pic_VO;
	}

	@Override
	public List<ForPost_picture_VO> getAll() {
		List<ForPost_picture_VO> list = new ArrayList<ForPost_picture_VO>();
		ForPost_picture_VO forPos_pic_VO = null;

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
				forPos_pic_VO = new ForPost_picture_VO();
				forPos_pic_VO.setForPostPic_ID(rs.getInt("forPostPic_ID"));
				forPos_pic_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPos_pic_VO.setForPostPic(rs.getBytes("forPostPic"));
				list.add(forPos_pic_VO);
				
			}

		}catch (ClassNotFoundException e) {
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

	
	
//	testify.....
	public static void main(String args[]) {

//		----------------------------�s�W-------------------------------
		
		
		
		ForPost_picture_VO forPos_pic_VO = new ForPost_picture_VO();
		ForPost_picture_JDBCDAO forPost_picture_JDBCDAO = new ForPost_picture_JDBCDAO();
	
	
		
		byte[] pic = null;
		try {
			
			for(int num=1;num <35;num++) {
				
				pic = getPictureByteArray("WebContent/爬圖/"+ num +".jpg");
				
				forPos_pic_VO.setForPost_ID(10);
				forPos_pic_VO.setForPostPic(pic);

//				forPos_pic_VO.setForPostPic(getPictureByteArray("/WebContent/images/focus.jpg"));
				forPost_picture_JDBCDAO.insert(forPos_pic_VO);
				System.out.println("圖片新增成功");

			} 
			
			
			

	}catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


//	

	System.out.println("圖片新增成功");
		
	}
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);//**source
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];//
		int i;
		while ((i = fis.read(buffer)) != -1) {//true
			baos.write(buffer, 0, i);// 
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	@Override
	public List<ForPost_picture_VO> findAllPicByPK(Integer act_ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ForPost_picture_VO findOnePicByPK(Integer forPostPic_ID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(ForPost_picture_VO forPos_pic_VO, Connection con) {
		// TODO Auto-generated method stub
		
	}
}
