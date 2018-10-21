package com.forPos_pic.model;

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

import com.activity.model.Activity_VO;




public class ForPost_picture_DAO implements ForPost_picture_DAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}


	private static final String INSERT_STMT = "INSERT INTO FORPOST_PICTURE (FORPOSTPIC_ID,FORPOST_ID,FORPOSTPIC) VALUES (FORPOST_PICTURE_SEQ.NEXTVAL, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT forPostPic_ID,forPost_ID,forPostPic FROM FORPOST_PICTURE order by forPostPic_ID";
	private static final String GET_ONE_STMT = "SELECT forPostPic_ID,forPost_ID,forPostPic FROM FORPOST_PICTURE where forPost_ID = ?";
	private static final String DELETE = "DELETE FROM FORPOST_PICTURE where forPostPic_ID = ?";
	private static final String UPDATE = "UPDATE FORPOST_PICTURE set forPost_ID=?, forPostPic=? where forPostPic_ID = ?";
	private static final String FIND_PIC_BY_ID = "SELECT * FROM FORPOST_PICTURE WHERE FORPOSTPIC_ID= ?";
	
	
	
	
	@Override
	public void insert(ForPost_picture_VO forPos_pic_VO) {
		Connection con = null;
		PreparedStatement pstmt = null;
//		String picName = "focus.jpg";

		try {
			con = ds.getConnection();
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, forPos_pic_VO.getForPost_ID());
			pstmt.setBytes(2, forPos_pic_VO.getForPostPic());
			pstmt.setInt(3, forPos_pic_VO.getForPostPic_ID());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public void delete(Integer forPostPic_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, forPostPic_ID);

			pstmt.executeUpdate();

			// Handle any driver errors
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
	public ForPost_picture_VO findByPrimaryKey(Integer forPost_ID) {
		ForPost_picture_VO forPos_pic_VO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, forPost_ID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo = Domain objects
				forPos_pic_VO = new ForPost_picture_VO();
				forPos_pic_VO.setForPostPic_ID(rs.getInt("forPostPic_ID"));
				forPos_pic_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPos_pic_VO.setForPostPic(rs.getBytes("forPostPic"));;
				
			}

			// Handle any driver errors
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

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVO 也稱為 Domain objects
				forPos_pic_VO = new ForPost_picture_VO();
				forPos_pic_VO.setForPostPic_ID(rs.getInt("forPostPic_ID"));
				forPos_pic_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forPos_pic_VO.setForPostPic(rs.getBytes("forPostPic"));
				list.add(forPos_pic_VO);
				
			}

			// Handle any driver errors
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
		PreparedStatement pstmt = null;
		try {
			System.out.println("你有進來嗎照片DAO");
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setInt(1, forPos_pic_VO.getForPost_ID());	
			System.out.println("你有進來嗎照片DAO編號:"+forPos_pic_VO.getForPost_ID());
			pstmt.setBytes(2, forPos_pic_VO.getForPostPic());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} 
	}

}
