package com.android.route.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.android.util.ImageUtil;








public class RouteDAO  implements RouteDAO_interface {
	
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "CA103G2";
	
	 
	private static final String GETONEBYMEM = "SELECT * FROM ROUTE WHERE MEM_ID = ?"; 
	private static final String GETALLBYPK =  "SELECT * FROM ROUTE WHERE ROT_ID = ? ";
	
	private static final String GETALLROUTE= "SELECT * FROM ROUTE";
	
	private static final String FINDIMGBYROTID = "SELECT rot_photo FROM ROUTE WHERE ROT_ID = ?";
	
	
	private static final String GETROUTEPHOTO = 
			"SELECT rot_photo FROM ROUTE WHERE ROT_ID=?";
	
	
	@Override
	public RouteVO getByRotid(String rot_id) {
		RouteVO routeVO = new RouteVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALLBYPK);
			pstmt.setString(1, rot_id );
			rs = pstmt.executeQuery();
			while (rs.next()) {
				routeVO = new RouteVO();
				routeVO.setRot_id(rs.getString("rot_id"));
				routeVO.setMem_id(rs.getString("mem_id"));
				routeVO.setRot_name(rs.getString("rot_name"));
				routeVO.setRot_describe(rs.getString("rot_describe"));
				routeVO.setRot_loc_start(rs.getString("rot_loc_start"));
				routeVO.setRot_loc_end(rs.getString("rot_loc_end"));
//				routeVO.setRot_hard(rs.getDouble("rot_hard"));
//				routeVO.setRot_dis(rs.getDouble("rot_dis"));
//				routeVO.setRot_height_up(rs.getDouble("rot_height_up"));
//				routeVO.setRot_height_down(rs.getDouble("rot_height_down"));
//				routeVO.setRot_height_ave(rs.getDouble("rot_height_ave"));
//				routeVO.setRot_slope_up(rs.getDouble("rot_slope_up"));
//				routeVO.setRot_slope_down(rs.getDouble("rot_slope_down"));
//				routeVO.setRot_slope_ave(rs.getDouble("rot_slope_ave"));
				routeVO.setRot_loc_start_des(rs.getString("rot_loc_start_des"));
				routeVO.setRot_loc_end_des(rs.getString("rot_loc_end_des"));
//				routeVO.setRot_status(rs.getInt("rot_status"));
				routeVO.setRot_photo(rs.getString("rot_photo"));
//				routeVO.setRot_popu(rs.getInt("rot_popu"));
//				routeVO.setRot_photo_loc(rs.getDouble("rot_photo_loc"));
//				routeVO.setRot_start(rs.getString("rot_start"));
//				routeVO.setRot_end(rs.getString("rot_end"));
//				routeVO.setRot_gps(rs.getString("rot_gps"));
			}	} catch (SQLException se) {
						throw new RuntimeException("A database error occured. " + se.getMessage());
						// Clean up JDBC resources
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
		return routeVO;
	}
	
	@Override
	public List<RouteVO> getByMemId(String mem_id) {
		RouteVO routeVO = new RouteVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		List<RouteVO> list = new ArrayList<>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETONEBYMEM);
			pstmt.setString(1, mem_id );
			rs = pstmt.executeQuery();
			while (rs.next()) {
				routeVO = new RouteVO();
				routeVO.setRot_id(rs.getString("rot_id"));
				routeVO.setMem_id(rs.getString("mem_id"));
				routeVO.setRot_name(rs.getString("rot_name"));
				routeVO.setRot_describe(rs.getString("rot_describe"));
				routeVO.setRot_loc_start(rs.getString("rot_loc_start"));
				routeVO.setRot_loc_end(rs.getString("rot_loc_end"));
//				routeVO.setRot_hard(rs.getDouble("rot_hard"));
//				routeVO.setRot_dis(rs.getDouble("rot_dis"));
//				routeVO.setRot_height_up(rs.getDouble("rot_height_up"));
//				routeVO.setRot_height_down(rs.getDouble("rot_height_down"));
//				routeVO.setRot_height_ave(rs.getDouble("rot_height_ave"));
//				routeVO.setRot_slope_up(rs.getDouble("rot_slope_up"));
//				routeVO.setRot_slope_down(rs.getDouble("rot_slope_down"));
//				routeVO.setRot_slope_ave(rs.getDouble("rot_slope_ave"));
				routeVO.setRot_loc_start_des(rs.getString("rot_loc_start_des"));
				routeVO.setRot_loc_end_des(rs.getString("rot_loc_end_des"));
//				routeVO.setRot_status(rs.getInt("rot_status"));
				routeVO.setRot_photo(rs.getString("rot_photo"));
//				routeVO.setRot_popu(rs.getInt("rot_popu"));
//				routeVO.setRot_photo_loc(rs.getDouble("rot_photo_loc"));
//				routeVO.setRot_start(rs.getString("rot_start"));
//				routeVO.setRot_end(rs.getString("rot_end"));
//				routeVO.setRot_gps(rs.getString("rot_gps"));
			}
			} catch (SQLException se) {
						throw new RuntimeException("A database error occured. " + se.getMessage());
						// Clean up JDBC resources
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
	public List<RouteVO> getAll() {
		List<RouteVO> list = new ArrayList<RouteVO>();	
	
		Connection con = null;
		RouteVO routeVO = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALLROUTE);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeVO = new RouteVO();
//				String strbase64= rs.getString("rot_photo");
////		        byte []  decodedString = Base64.getDecoder().decode(strbase64);
//				byte[] image = Base64.getMimeDecoder().decode(strbase64);
//
//		        
//		        byte [] shrink = ImageUtil.shrink(image, 50);
//		        Base64.Encoder encoder = Base64.getEncoder();
//				String rot_photo = encoder.encodeToString(shrink);

				routeVO.setRot_id(rs.getString("rot_id"));
				routeVO.setMem_id(rs.getString("mem_id"));
				routeVO.setRot_name(rs.getString("rot_name"));
				routeVO.setRot_describe(rs.getString("rot_describe"));
//				routeVO.setRot_loc_start(rs.getString("rot_loc_start"));
//				routeVO.setRot_loc_end(rs.getString("rot_loc_end"));
//				routeVO.setRot_hard(rs.getDouble("rot_hard"));
//				routeVO.setRot_dis(rs.getDouble("rot_dis"));
//				routeVO.setRot_height_up(rs.getDouble("rot_height_up"));
//				routeVO.setRot_height_down(rs.getDouble("rot_height_down"));
//				routeVO.setRot_height_ave(rs.getDouble("rot_height_ave"));
//				routeVO.setRot_slope_up(rs.getDouble("rot_slope_up"));
//				routeVO.setRot_slope_down(rs.getDouble("rot_slope_down"));
//				routeVO.setRot_slope_ave(rs.getDouble("rot_slope_ave"));
//				routeVO.setRot_loc_start_des(rs.getString("rot_loc_start_des"));
//				routeVO.setRot_loc_end_des(rs.getString("rot_loc_end_des"));
//				routeVO.setRot_status(rs.getInt("rot_status"));
//				routeVO.setRot_photo(rot_photo);
//				routeVO.setRot_popu(rs.getInt("rot_popu"));
//				routeVO.setRot_photo_loc(rs.getDouble("rot_photo_loc"));
//				routeVO.setRot_start(rs.getString("rot_start"));
//				routeVO.setRot_end(rs.getString("rot_end"));
//				routeVO.setRot_gps(rs.getString("rot_gps"));
				list.add(routeVO);
			}	
			
		} catch (ClassNotFoundException e) {
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
	
public void getRot_Photo(String rot_id, HttpServletResponse response) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ServletOutputStream out = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETROUTEPHOTO);
			rs = pstmt.executeQuery();
			out = response.getOutputStream();
			
			pstmt.setString(1, rot_id);
			rs = pstmt.executeQuery();
			boolean boo=rs.next();
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] buffer = decoder.decode(rs.getString(1));
			
			out.write(buffer);
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}catch (IOException ioe) {
			throw new RuntimeException("A IOException error occured. " + ioe.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {out.close();} catch (IOException ioe) {ioe.printStackTrace(System.err);}
			}
			if (rs != null) {
				try {rs.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
	}
	@Override
	public byte[] getImage(String rot_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] buffer = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FINDIMGBYROTID);
			pstmt.setString(1, rot_id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
		
				
				Base64.Decoder decoder = Base64.getDecoder();
				
				 buffer = decoder.decode(rs.getString(1));
					System.out.println("===================");

			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return buffer;
	}
}
