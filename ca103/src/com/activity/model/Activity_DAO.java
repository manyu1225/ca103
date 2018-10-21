package com.activity.model;

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

import jdbc.util.CompositeQuery.jdbcUtil_CompositeQuery_Activity;


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
	
	
	
		private static final String INSERT_STMT = 
			"INSERT INTO ACTIVITY (act_ID, act_name, act_sdate, act_edate, act_regis_date, act_info, act_holder,CusName, CusMail, rot_id, act_href, act_href2, act_pic, emp_id,  act_state, act_click)" + 
					"VALUES (ACTIVITY_SEQ.NEXTVAL, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?,0)";
		private static final String GET_ALL_STMT = 
			"SELECT act_ID, act_name, act_sdate,act_edate, act_regis_date, act_info, act_holder, cusName, cusMail, rot_id, act_href,act_href2, act_pic" + 
			",emp_ID, act_explo, act_click, act_region_north, act_region_center, act_region_south, act_region_east, act_region_off, act_state FROM ACTIVITY order by act_ID desc";
		private static final String GET_ONE_STMT = 
				"SELECT act_ID, act_name, act_sdate, act_edate, act_regis_date, act_info, act_holder, cusName, cusMail, rot_id, act_href,act_href2, act_pic" + 
						",emp_ID, act_explo, act_click, act_region_north, act_region_center, act_region_south, act_region_east, act_region_off, act_state FROM ACTIVITY where act_ID=?";
		//累計活動觀看人數=======
		private static final String ACT_CLICK_NUM =  
				"UPDATE ACTIVITY SET ACT_CLICK=(ACT_CLICK + 1) WHERE ACT_ID = ?";

		//依照觀看數排序熱門活動排序
		private static final String GET_POPULAR_ACT= 
		"SELECT * FROM ACTIVITY ORDER BY ACT_CLICK DESC ";
		
		//依照開始活動期排序活動
		private static final String GET_LATEST_ACT= 
		"SELECT * FROM ACTIVITY ORDER BY act_sdate DESC ";
				
		
		private static final String FIND_PIC_BY_ID = "SELECT * FROM ACTIVITY WHERE act_ID= ?";

		
		private static final String FIND_ACT_POS_PIC = "SELECT act_ID, ACT_PIC FROM ACTIVITY WHERE ACT_ID=?"; 
		
		
		private static final String DELETE = 
			"DELETE FROM ACTIVITY where act_ID = ?";
		private static final String UPDATE = 
			"UPDATE ACTIVITY set act_name = ? , act_date = ?, act_regis_date = ?, act_info = ?, act_holder = ?, CusName = ?, CustMail = ?, rot_id = ? ,act_href =  ?, act_href2 =  ?, act_pic = ?," + 
			"emp_id = ?, act_explo = ?, act_click = ?, act_region_north =? , act_region_center = ?, act_region_south = ?, act_region_east = ?, act_region_off = ? where act_ID = ?";		
		
		private static final String ACT_VIEW_NUM="UPDATE ACTIVITY set ";
		
		private static final String FIND_BY_ALL_CONDITION="SELECT *　FROM ACTIVITY";

		
		
		
		
		
		@Override
		public void insert(Activity_VO activity_VO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);

				pstmt.setString(1, activity_VO.getAct_name());
				pstmt.setDate(2, activity_VO.getAct_sdate());
				pstmt.setDate(3, activity_VO.getAct_edate());
				pstmt.setDate(4, activity_VO.getAct_regis_date());
				pstmt.setString(5, activity_VO.getAct_info());
				pstmt.setString(6, activity_VO.getAct_holder());
				pstmt.setString(7, activity_VO.getCusName());
				pstmt.setString(8, activity_VO.getCusMail());
				pstmt.setString(9, activity_VO.getRot_id());
				pstmt.setString(10, activity_VO.getAct_href());
				pstmt.setString(11, activity_VO.getAct_href2());
				pstmt.setBytes(12, activity_VO.getAct_pic());
				pstmt.setString(13, activity_VO.getEmp_id());
				pstmt.setInt(14, activity_VO.getAct_state());



				pstmt.executeUpdate();


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
		public void update(Activity_VO activity_VO) {
			Connection con = null;
			PreparedStatement pstmt = null;


			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE);
				pstmt.setString(1, activity_VO.getAct_name());
				pstmt.setDate(2, activity_VO.getAct_edate());
				pstmt.setDate(3, activity_VO.getAct_sdate());
				pstmt.setDate(4, activity_VO.getAct_regis_date());
				pstmt.setString(5, activity_VO.getAct_info());
				pstmt.setString(6, activity_VO.getAct_holder());
				pstmt.setString(7, activity_VO.getCusName());
				pstmt.setString(8, activity_VO.getCusMail());
				pstmt.setString(9, activity_VO.getRot_id());
				pstmt.setString(10, activity_VO.getAct_href());
				pstmt.setString(11, activity_VO.getAct_href2());
				pstmt.setBytes(12, activity_VO.getAct_pic());
				pstmt.setString(13, activity_VO.getEmp_id());
				pstmt.setInt(14, activity_VO.getAct_explo());
				pstmt.setInt(15,  activity_VO.getAct_click());
				pstmt.setInt(16, activity_VO.getAct_region_north());
				pstmt.setInt(17, activity_VO.getAct_region_center());
				pstmt.setInt(18, activity_VO.getAct_region_south());
				pstmt.setInt(19, activity_VO.getAct_region_east());
				pstmt.setInt(20, activity_VO.getAct_region_off());
				pstmt.setInt(21, activity_VO.getAct_state());

				
				
				pstmt.setInt(16, activity_VO.getAct_ID());


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
		public void delete(Integer act_ID) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, act_ID);

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
					activity_VO.setAct_info(rs.getString("act_info"));
					activity_VO.setAct_holder(rs.getString("act_holder"));
					activity_VO.setCusName(rs.getString("CusName"));
					activity_VO.setCusMail(rs.getString("CusMail"));
					activity_VO.setRot_id(rs.getString("rot_id"));
					activity_VO.setAct_href(rs.getString("act_href"));
					activity_VO.setAct_href2(rs.getString("act_href2"));
					activity_VO.setAct_pic(rs.getBytes("act_pic"));
					activity_VO.setEmp_id(rs.getString("emp_ID"));
					activity_VO.setAct_explo(rs.getInt("act_explo"));
					activity_VO.setAct_click(rs.getInt("act_click"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_north"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_center"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_south"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_east"));
					activity_VO.setAct_region_off(rs.getInt("Act_region_off"));
					activity_VO.setAct_state(rs.getInt("act_state"));
				
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
				pstmt = con.prepareStatement(FIND_ACT_POS_PIC);

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

				while (rs.next()) {
					// empVO �]�٬� Domain objects
					activity_VO = new Activity_VO();
					activity_VO.setAct_ID(rs.getInt("act_ID"));
					activity_VO.setAct_name(rs.getString("act_name"));
					activity_VO.setAct_sdate(rs.getDate("act_sdate"));
					activity_VO.setAct_edate(rs.getDate("act_edate"));
					activity_VO.setAct_regis_date(rs.getDate("act_regis_date"));
					activity_VO.setAct_info(rs.getString("act_info"));
					activity_VO.setAct_holder(rs.getString("act_holder"));
					activity_VO.setCusName(rs.getString("CusName"));
					activity_VO.setCusMail(rs.getString("CusMail"));
					activity_VO.setRot_id(rs.getString("rot_ID"));
					activity_VO.setAct_href(rs.getString("act_href"));
					activity_VO.setAct_href2(rs.getString("act_href2"));
					activity_VO.setAct_pic(rs.getBytes("act_pic"));
					activity_VO.setEmp_id(rs.getString("emp_ID"));
					activity_VO.setAct_explo(rs.getInt("act_explo"));
					activity_VO.setAct_click(rs.getInt("act_click"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_north"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_center"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_south"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_east"));
					activity_VO.setAct_region_off(rs.getInt("Act_region_off"));
					activity_VO.setAct_state(rs.getInt("act_state"));
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
		public List<Activity_VO> getAll(Map<String, String[]> map) {
			List<Activity_VO> list = new ArrayList<Activity_VO>();
			Activity_VO activity_VO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				String finalSQL = "select * from activity"
						+ jdbcUtil_CompositeQuery_Activity.get_WhereCondition(map)
						+ " order by act_ID desc";
				pstmt = con.prepareStatement(finalSQL);
				System.out.println("●●finalSQL(by DAO) = " + finalSQL);
				rs = pstmt.executeQuery();
				

				while (rs.next()) {
					activity_VO = new Activity_VO();
					activity_VO.setAct_ID(rs.getInt("act_ID"));
					activity_VO.setAct_name(rs.getString("act_name"));
					activity_VO.setAct_sdate(rs.getDate("act_sdate"));
					activity_VO.setAct_edate(rs.getDate("act_edate"));
					activity_VO.setAct_regis_date(rs.getDate("act_regis_date"));
					activity_VO.setAct_info(rs.getString("act_info"));
					activity_VO.setAct_holder(rs.getString("act_holder"));
					activity_VO.setCusName(rs.getString("CusName"));
					activity_VO.setCusMail(rs.getString("CusMail"));
					activity_VO.setRot_id(rs.getString("rot_ID"));
					activity_VO.setAct_href(rs.getString("act_href"));
					activity_VO.setAct_href2(rs.getString("act_href2"));
					activity_VO.setAct_pic(rs.getBytes("act_pic"));
					activity_VO.setEmp_id(rs.getString("emp_ID"));
					activity_VO.setAct_explo(rs.getInt("act_explo"));
					activity_VO.setAct_click(rs.getInt("act_click"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_north"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_center"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_south"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_east"));
					activity_VO.setAct_region_off(rs.getInt("Act_region_off"));
					activity_VO.setAct_state(rs.getInt("act_state"));
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
		public void updateActView(Activity_VO activity_VO) {

			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(ACT_CLICK_NUM);
				pstmt.setInt(1, activity_VO.getAct_ID());


				pstmt.executeUpdate();

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
					activity_VO.setAct_holder(rs.getString("act_holder"));
					activity_VO.setCusName(rs.getString("CusName"));
					activity_VO.setCusMail(rs.getString("CusMail"));
					activity_VO.setRot_id(rs.getString("rot_ID"));
					activity_VO.setAct_href(rs.getString("act_href"));
					activity_VO.setAct_href2(rs.getString("act_href2"));
					activity_VO.setAct_pic(rs.getBytes("act_pic"));
					activity_VO.setEmp_id(rs.getString("emp_ID"));
					activity_VO.setAct_explo(rs.getInt("act_explo"));
					activity_VO.setAct_click(rs.getInt("act_click"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_north"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_center"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_south"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_east"));
					activity_VO.setAct_region_off(rs.getInt("Act_region_off"));
					activity_VO.setAct_state(rs.getInt("act_state"));
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
		
		
		
		
		
		
		
//		==
		
		@Override
		public List<Activity_VO> getPopAct() {
			List<Activity_VO> list = new ArrayList<Activity_VO>();
			Activity_VO activity_VO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				con = ds.getConnection();
				pstmt = con.prepareStatement(GET_POPULAR_ACT);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					activity_VO = new Activity_VO();
					activity_VO.setAct_ID(rs.getInt("act_ID"));
					activity_VO.setAct_name(rs.getString("act_name"));
					activity_VO.setAct_sdate(rs.getDate("act_sdate"));
					activity_VO.setAct_edate(rs.getDate("act_edate"));
					activity_VO.setAct_regis_date(rs.getDate("act_regis_date"));
					activity_VO.setAct_info(rs.getString("act_info"));
					activity_VO.setAct_holder(rs.getString("act_holder"));
					activity_VO.setCusName(rs.getString("CusName"));
					activity_VO.setCusMail(rs.getString("CusMail"));
					activity_VO.setRot_id(rs.getString("rot_ID"));
					activity_VO.setAct_href(rs.getString("act_href"));
					activity_VO.setAct_href2(rs.getString("act_href2"));
					activity_VO.setAct_pic(rs.getBytes("act_pic"));
					activity_VO.setEmp_id(rs.getString("emp_ID"));
					activity_VO.setAct_explo(rs.getInt("act_explo"));
					activity_VO.setAct_click(rs.getInt("act_click"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_north"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_center"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_south"));
					activity_VO.setAct_region_center(rs.getInt("Act_region_east"));
					activity_VO.setAct_region_off(rs.getInt("Act_region_off"));
					activity_VO.setAct_state(rs.getInt("act_state"));
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
		


