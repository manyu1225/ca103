package com.activity.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Activity_JDBCDAO implements Activity_DAO_interface {
	
	
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "CA103G2";
		String passwd = "123456";
	
	
	
			private static final String INSERT_STMT = 
				"INSERT INTO ACTIVITY (act_ID, act_name, act_sdate, act_edate, act_regis_date, act_info, act_holder,CusName, CusMail, rot_id, act_href, act_href2, act_pic, emp_id,  act_state)" + 
						"VALUES (ACTIVITY_SEQ.NEXTVAL, ?, ?, ?, ?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			private static final String GET_ALL_STMT = 
				"SELECT act_ID, act_name, act_sdate, act_edate, act_regis_date, act_info, act_holder, cusName, cusMail, rot_id, act_href,act_href2, act_pic" + 
				",emp_ID, act_explo, act_click, act_region_north, act_region_center, act_region_south, act_region_east, act_region_off, act_state FROM ACTIVITY order by act_ID desc";
			private static final String GET_ONE_STMT = 
					"SELECT act_ID, act_name, act_sdate, act_edate, act_regis_date, act_info, act_holder, cusName, cusMail, rot_id, act_href,act_href2, act_pic" + 
							",emp_ID, act_explo, act_click, act_region_north, act_region_center, act_region_south, act_region_east, act_region_off, act_state FROM ACTIVITY where act_ID=?";
			private static final String DELETE = 
				"DELETE FROM ACTIVITY where act_ID = ?";
			private static final String UPDATE = 
				"UPDATE ACTIVITY set act_name = ? , act_date = ?, act_regis_date = ?, act_info = ?, act_holder = ?, CusName = ?, CustMail = ?, rot_id = ? ,act_href =  ?, act_href2 =  ?, act_pic = ?," + 
				"emp_id = ?, act_explo = ?, act_click = ?, act_region_north =? , act_region_center = ?, act_region_south = ?, act_region_east = ?, act_region_off = ? where act_ID = ?";
			private static final String FIND_PIC_BY_ID = "SELECT * FROM ACTIVITY WHERE act_ID= ?";

			
			private static final String FIND_ACT_POS_PIC = "SELECT ACT_ID, ACT_PIC FROM ACTIVITY WHERE ACT_ID=?"; 
			//依照開始活動期排序活動
			private static final String GET_LATEST_ACT= 
			"SELECT * FROM ACTIVITY ORDER BY act_sdate DESC ";
					
		
		
		@Override
		public void insert(Activity_VO activity_VO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);

				pstmt = con.prepareStatement(INSERT_STMT);
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
//				pstmt.setInt(14, activity_VO.getAct_explo());
//				pstmt.setInt(15,  activity_VO.getAct_click());
//				pstmt.setInt(16, activity_VO.getAct_region_north());
//				pstmt.setInt(17, activity_VO.getAct_region_center());
//				pstmt.setInt(18, activity_VO.getAct_region_south());
//				pstmt.setInt(19, activity_VO.getAct_region_east());
//				pstmt.setInt(20, activity_VO.getAct_region_off());
				pstmt.setInt(14, activity_VO.getAct_state());



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
		public void update(Activity_VO activity_VO) {
			Connection con = null;
			PreparedStatement pstmt = null;


			try {
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
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
		public void delete(Integer act_ID) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);				pstmt = con.prepareStatement(DELETE);

				pstmt.setInt(1, act_ID);

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
		public Activity_VO findByPrimaryKey(Integer act_ID) {
			Activity_VO activity_VO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);				
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
			return activity_VO;
		}
		@Override
		public List<Activity_VO> getAll() {
			List<Activity_VO> list = new ArrayList<Activity_VO>();
			Activity_VO activity_VO = null ;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);				
				pstmt = con.prepareStatement(GET_ALL_STMT);
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
//					activity_VO.setAct_explo(rs.getInt("act_explo"));
//					activity_VO.setAct_click(rs.getInt("act_click"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_north"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_center"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_south"));
//					activity_VO.setAct_region_center(rs.getInt("Act_region_east"));
//					activity_VO.setAct_region_off(rs.getInt("Act_region_off"));
					activity_VO.setAct_state(rs.getInt("act_state"));
					list.add(activity_VO);
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
			return list;
		}
		
		
		
		
		
//		新增活動
		
		public static void main(String[] args) {
			
			Activity_JDBCDAO dao = new Activity_JDBCDAO();
//			Activity_VO activity_VO = new Activity_VO();
			
//			*******　insert　****************
//			
//			activity_VO.setAct_name("輪壩xxxxx西濱");
//			activity_VO.setAct_sdate(Date.valueOf("2018-10-08"));
//			activity_VO.setAct_edate(Date.valueOf("2018-10-09"));
//			activity_VO.setAct_regis_date(Date.valueOf("2018-09-08"));
//			activity_VO.setAct_info("一、活動目的：為響應政府『運動i台灣計畫』，持續發展自行車運動，藉本活動結合戶外旅遊及運動，呈現於大台\r\n" + 
//					"\r\n" + 
//					"北及桃竹苗地區連結之西濱公路，以最環保的自行車騎乘方式，引領參加民眾以體能及心靈感受台灣西北部海\r\n" + 
//					"\r\n" + 
//					"岸線之美，並透過活動進行中，體驗挑戰自我、挑戰極限之精神。 \r\n" + 
//					"\r\n" + 
//					"二、指導單位：邀請中\r\n" + 
//					"\r\n" + 
//					"三、主辦單位：中華民國自行車協會、中華國際休閒運動交流協會\r\n" + 
//					"\r\n" + 
//					"四、協辦單位：邀請中 \r\n" + 
//					"\r\n" + 
//					"五、活動日期：107 年3月18日 (星期日) \r\n" + 
//					"\r\n" + 
//					"七、集合地點：新北市八里區廖添丁廟(訊塘廣場)、苗栗縣媽靈宮 \r\n" + 
//					"\r\n" + 
//					"八、活動項目： ");
//			activity_VO.setAct_holder("xxxxxsxxxxxx自行車協會");
//			activity_VO.setCusName("林小姐");
//			activity_VO.setCusMail("jennifer@gmail.com");
//			activity_VO.setRot_id("R000022");
//			activity_VO.setAct_href("http://taiwanbike.org/index.php/435-2018-2");
//			activity_VO.setAct_href2("http://taiwanbike.org/index.php/435-2018-2");
//			activity_VO.setAct_pic(null);
//			activity_VO.setEmp_id("E0008"); 
//			
//			activity_VO.setAct_state(1);
//			
//			
//			dao.insert(activity_VO);
//			
//			System.out.println("新增成功");
////			
			
//			******* 查全部活動 *******
			List<Activity_VO> list = dao.getLatestAct();
			System.out.println(dao);


			for(Activity_VO activity_VO:list) {

				System.out.println("【客戶姓名】: " + activity_VO.getCusName());
//				System.out.println("【客戶連絡信箱:】 " + activity_VO.getCusMail());
//				System.out.println("【活動名稱:】 " + activity_VO.getAct_name());
//				System.out.println("【主辦單位】: " + activity_VO.getAct_holder());
//				System.out.println("【活動資訊】: " + activity_VO.getAct_info());
//				System.out.println("【活動連結】: " + activity_VO.getAct_href());
//				System.out.println("【報名簡章】: " + activity_VO.getEmp_id());
//				System.out.println("【活動路線】: " + activity_VO.getRot_id());
//				System.out.println("【活動報名日期】: " + activity_VO.getAct_regis_date());
				System.out.println("【活動開始日期】: " + activity_VO.getAct_sdate());
				System.out.println("】活動結束日期】: " + activity_VO.getAct_edate());
				System.out.println("=============================================================");
			}
			
//			******* 查活動 *******

			
//			activity_VO = dao.findByPrimaryKey(7);
//			System.out.println("【客戶姓名】: " + activity_VO.getCusName());
//			System.out.println("【客戶連絡信箱:】 " + activity_VO.getCusMail());
//			System.out.println("【活動名稱:】 " + activity_VO.getAct_name());
//			System.out.println("【主辦單位】: " + activity_VO.getAct_holder());
//			System.out.println("【活動資訊】: " + activity_VO.getAct_info());
//			System.out.println("【活動連結】: " + activity_VO.getAct_href());
//			System.out.println("【報名簡章】: " + activity_VO.getEmp_id());
//			System.out.println("【活動路線】: " + activity_VO.getRot_id());
//			System.out.println("【活動報名日期】: " + activity_VO.getAct_regis_date());
//			System.out.println("【活動開始日期】: " + activity_VO.getAct_sdate());
//			System.out.println("【活動結束日期】: " + activity_VO.getAct_edate());
//			System.out.println("=============================================================");
//			
			
////			
//			activity_VO = dao.findPicByPK2(100);
//			System.out.println(activity_VO);
////			System.out.println(activity_VO.getAct_pic());
			
			
		}
		
		
@Override
public List<Activity_VO> findPicByPK(Integer act_ID) {
	List<Activity_VO> list = new ArrayList<Activity_VO>();
	Activity_VO activity_VO = null;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	try {
		Class.forName(driver);
		con = DriverManager.getConnection(url, userid, passwd);	

		pstmt = con.prepareStatement(FIND_PIC_BY_ID);

		pstmt.setInt(1, act_ID);

		rs = pstmt.executeQuery();

		while (rs.next()) {
			activity_VO = new Activity_VO();
			activity_VO.setAct_ID(rs.getInt("act_ID"));
			activity_VO.setAct_pic(rs.getBytes("act_pic"));
			list.add(activity_VO);
			
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
		@Override
		public Activity_VO findPicByPK2(Integer act_ID) {
			Activity_VO activity_VO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
		
			try {
		
				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);			
				pstmt = con.prepareStatement(FIND_ACT_POS_PIC);
		
				pstmt.setInt(1, act_ID);

				rs = pstmt.executeQuery();

		while(rs.next()) {
			activity_VO = new Activity_VO();

			activity_VO.setAct_ID(rs.getInt("act_ID"));
			System.out.println("555555");

			activity_VO.setAct_pic(rs.getBytes("act_pic"));
		}
					
			}catch(ClassNotFoundException e) {
				
				throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		
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
		public List<Activity_VO> getAll(Map<String, String[]> map) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public void updateActView(Activity_VO activity_VO) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public List<Activity_VO> getPopAct() {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		@Override
		public List<Activity_VO> getLatestAct() {
			List<Activity_VO> list = new ArrayList<Activity_VO>();
			Activity_VO activity_VO = null;

			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);			
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
				
				
			}catch(ClassNotFoundException e) {
				
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		
				
			

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
				


