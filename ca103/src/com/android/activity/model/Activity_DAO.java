package com.android.activity.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class Activity_DAO implements Activity_DAO_interface {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String FIND_IMG_BY_PK = "SELECT act_pic FROM book WHERE act_ID = ?";

	
		private static final String GET_ALL_STMT = 
			"SELECT act_ID, act_name, act_sdate,act_edate, act_regis_date, act_info, act_holder, cusName, cusMail, rot_id, act_href,act_href2, act_pic" + 
			",emp_ID, act_explo, act_click, act_region_north, act_region_center, act_region_south, act_region_east, act_region_off, act_state FROM ACTIVITY order by act_ID desc";
		
		private static final String GET_ONE_STMT = 
				"SELECT act_ID, act_name, act_sdate, act_edate, act_regis_date, act_info, act_holder, cusName, cusMail, rot_id, act_href,act_href2, act_pic" + 
						",emp_ID, act_explo, act_click, act_region_north, act_region_center, act_region_south, act_region_east, act_region_off, act_state FROM ACTIVITY where act_ID=?";

		private static final String FINDIMGBYACTID = "SELECT act_pic FROM ACTIVITY WHERE act_ID = ?";

	
		
		//依照開始活動期排序活動
		private static final String GET_LATEST_ACT= 
		"SELECT * FROM ACTIVITY ORDER BY act_sdate DESC ";
				
		
		private static final String FIND_PIC_BY_ID = "SELECT * FROM ACTIVITY WHERE act_ID= ?";

		
		
		
		private static final String FIND_ACT_PIC = "SELECT  ACT_PIC FROM ACTIVITY WHERE ACT_ID=?"; 

		
		@Override
		public byte[] getImage(Integer act_ID) {
			byte[] picture = null;
			Activity_VO activity_VO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				activity_VO= new Activity_VO();
				con = ds.getConnection();
				pstmt = con.prepareStatement(FIND_ACT_PIC);
		

				pstmt.setInt(1,act_ID );
				
				rs = pstmt.executeQuery();

				if (rs.next()) {
					picture = rs.getBytes(1);
					System.out.println(String.valueOf(picture));
				}
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
			return picture;
		}

		

	
	
		
		@Override
		public Activity_VO findByPrimaryKey(Integer act_ID) {
			Activity_VO activity_VO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ONE_STMT);

				pstmt.setInt(1, act_ID);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					activity_VO = new Activity_VO();
					activity_VO.setAct_ID(rs.getInt("act_ID"));
					activity_VO.setAct_name(rs.getString("act_name"));
					activity_VO.setAct_sdate(rs.getDate("act_sdate"));
					activity_VO.setAct_edate(rs.getDate("act_edate"));
					activity_VO.setAct_regis_date(rs.getDate("act_regis_date"));
					activity_VO.setRot_id(rs.getString("rot_id"));
					activity_VO.setAct_pic(rs.getBytes("act_pic"));
					activity_VO.setEmp_id(rs.getString("emp_ID"));
					activity_VO.setAct_explo(rs.getInt("act_explo"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_north"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_center"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_south"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_east"));
//					activity_VO.setAct_region_off(rs.getInt("Act_region_off"));
				
				}

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
			return activity_VO;
		}
		
		
		
		@Override
		public Activity_VO findPicByPK2(Integer act_ID) {
			Activity_VO activity_VO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(FIND_ACT_PIC);

				pstmt.setInt(1, act_ID);

				rs = pstmt.executeQuery();
					while(rs.next()) {
						
						activity_VO = new Activity_VO();
						activity_VO.setAct_ID(rs.getInt("act_ID"));
						activity_VO.setAct_pic(rs.getBytes("act_pic"));

					}
					
				

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
			return activity_VO;
		}
		
		
		
		
		@Override
		public List<Activity_VO> findPicByPK(Integer act_ID) {
			
			List<Activity_VO> list = new ArrayList<Activity_VO>();
			Activity_VO activity_VO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(FIND_PIC_BY_ID);

				pstmt.setInt(1, act_ID);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					activity_VO = new Activity_VO();
					activity_VO.setAct_ID(rs.getInt("act_ID"));
					activity_VO.setAct_pic(rs.getBytes("act_pic"));
					list.add(activity_VO);
					
				}

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
		
		
		
		
		@Override
		public List<Activity_VO> getAll() {
			List<Activity_VO> list = new ArrayList<Activity_VO>();
			Activity_VO activity_VO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_ALL_STMT);
				rs = pstmt.executeQuery();

				while (rs.next()) {					// empVO �]�٬� Domain objects
					activity_VO = new Activity_VO();
					activity_VO.setAct_ID(rs.getInt("act_ID"));
					activity_VO.setAct_name(rs.getString("act_name"));
					activity_VO.setAct_sdate(rs.getDate("act_sdate"));
					activity_VO.setAct_edate(rs.getDate("act_edate"));
					activity_VO.setAct_regis_date(rs.getDate("act_regis_date"));
					activity_VO.setAct_info(rs.getString("act_info"));
				
//					activity_VO.setCusName(rs.getString("CusName"));
//					activity_VO.setCusMail(rs.getString("CusMail"));
					activity_VO.setRot_id(rs.getString("rot_ID"));
				
//					activity_VO.setAct_pic(rs.getBytes("act_pic"));
					activity_VO.setEmp_id(rs.getString("emp_ID"));
					
					
//					activity_VO.setAct_region_center(rs.getInt("Act_region_north"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_center"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_south"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_east"));
//					activity_VO.setAct_region_off(rs.getInt("Act_region_off"));
					
					list.add(activity_VO);
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
		public List<Activity_VO> getLatestAct() {
			List<Activity_VO> list = new ArrayList<Activity_VO>();
			Activity_VO activity_VO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_LATEST_ACT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					activity_VO = new Activity_VO();
					activity_VO.setAct_ID(rs.getInt("act_ID"));
					activity_VO.setAct_name(rs.getString("act_name"));
					activity_VO.setAct_sdate(rs.getDate("act_sdate"));
					activity_VO.setAct_edate(rs.getDate("act_edate"));
					activity_VO.setAct_regis_date(rs.getDate("act_regis_date"));
					activity_VO.setAct_info(rs.getString("act_info"));
//					activity_VO.setAct_holder(rs.getString("act_holder"));
//					activity_VO.setCusName(rs.getString("CusName"));
//					activity_VO.setCusMail(rs.getString("CusMail"));
					activity_VO.setRot_id(rs.getString("rot_ID"));
					activity_VO.setAct_pic(rs.getBytes("act_pic"));
					activity_VO.setEmp_id(rs.getString("emp_ID"));
//					activity_VO.setAct_explo(rs.getInt("act_explo"));
//					activity_VO.setAct_click(rs.getInt("act_click"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_north"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_center"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_south"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_east"));
//					activity_VO.setAct_region_off(rs.getInt("Act_region_off"));
					list.add(activity_VO);
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


		
		
	
	
		
		
	}
		


