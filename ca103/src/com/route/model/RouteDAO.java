package com.route.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

//	取得收藏路線清單的SQL語法還不確定
public  class RouteDAO implements RouteDAO_interface{

	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_ROUTE = 
		"INSERT INTO ROUTE (" + 
		"ROT_ID, MEM_ID, ROT_NAME, ROT_DESCRIBE, ROT_LOC_START, ROT_LOC_END, " + 
		"ROT_HARD, ROT_DIS, ROT_HEIGHT_UP, ROT_HEIGHT_DOWN, ROT_HEIGHT_AVE, " + 
		"ROT_SLOPE_UP, ROT_SLOPE_DOWN, ROT_SLOPE_AVE, ROT_LOC_START_DES, ROT_LOC_END_DES, " + 
		"ROT_STATUS, ROT_PHOTO, ROT_POPU, ROT_PHOTO_LOC, ROT_START, ROT_END, ROT_GPS, ROT_MES_COUNT)" + 
		"VALUES ('R'||LPAD(to_char(rot_seq.NEXTVAL), 6, '0'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_ROUTE_FRONT = 
		"UPDATE ROUTE SET ROT_NAME=?, ROT_DESCRIBE=?, ROT_LOC_START=?, ROT_LOC_END=?, " + 
		" ROT_LOC_START_DES=?, ROT_LOC_END_DES=?, ROT_STATUS=?, ROT_PHOTO=?, ROT_PHOTO_LOC=? where ROT_ID=?";
	private static final String UPDATE_ROUTE_BACK = 
		"UPDATE ROUTE SET MEM_ID=?, ROT_NAME=?, ROT_DESCRIBE=?, ROT_LOC_START=?, ROT_LOC_END=?, " + 
		"ROT_HARD=?, ROT_DIS=?, ROT_HEIGHT_UP=?, ROT_HEIGHT_DOWN=?, ROT_HEIGHT_AVE=?, " + 
		"ROT_SLOPE_UP=?, ROT_SLOPE_DOWN=?, ROT_SLOPE_AVE=?, ROT_LOC_START_DES=?, ROT_LOC_END_DES=?, " + 
		"ROT_STATUS=?, ROT_PHOTO=?, ROT_POPU=?, ROT_PHOTO_LOC=? where ROT_ID=?";
	private static final String UPDATE_ROUTE_POPU = 
		"UPDATE ROUTE SET ROT_POPU=? where ROT_ID=?";
	private static final String UPDATE_ROT_MES_COUNT = 
		"UPDATE ROUTE SET ROT_MES_COUNT=? where ROT_ID=?";
	private static final String DELETE_ROUTE = 
		"DELETE FROM ROUTE WHERE ROT_ID=?";
	private static final String CLOSE_ROUTE = 
		"UPDATE ROUTE SET ROT_STATUS=2 WHERE ROT_ID=?";
	private static final String GETROUTELIST =  
		"SELECT * FROM ROUTE WHERE ROT_STATUS=1";
	private static final String GETROUTECLOSELIST =  
		"SELECT * FROM ROUTE WHERE ROT_STATUS=2";
	private static final String GETROUTEDETAILED = 
		"SELECT * FROM ROUTE WHERE ROT_ID=?";
	private static final String GETROUTEPHOTO = 
		"SELECT rot_photo FROM ROUTE WHERE ROT_ID=?";
	

	@Override
	public String insert(RouteVO routeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] cols = { "rot_id" };
		String rot_id;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_ROUTE,cols);
			
			pstmt.setString( 1, routeVO.getMem_id());
			pstmt.setString( 2, routeVO.getRot_name());
			pstmt.setString( 3, routeVO.getRot_describe());
			pstmt.setString( 4, routeVO.getRot_loc_start());
			pstmt.setString( 5, routeVO.getRot_loc_end());
			pstmt.setDouble( 6, routeVO.getRot_hard());
			pstmt.setDouble( 7, routeVO.getRot_dis());
			pstmt.setDouble( 8, routeVO.getRot_height_up());
			pstmt.setDouble( 9, routeVO.getRot_height_down());
			pstmt.setDouble(10, routeVO.getRot_height_ave());
			pstmt.setDouble(11, routeVO.getRot_slope_up());
			pstmt.setDouble(12, routeVO.getRot_slope_down());
			pstmt.setDouble(13, routeVO.getRot_slope_ave());
			pstmt.setString(14, routeVO.getRot_loc_start_des());
			pstmt.setString(15, routeVO.getRot_loc_end_des());
			pstmt.setInt   (16, routeVO.getRot_status());
			pstmt.setString(17, routeVO.getRot_photo());
			pstmt.setInt   (18, routeVO.getRot_popu());
			pstmt.setDouble(19, routeVO.getRot_photo_loc());
			pstmt.setString(20, routeVO.getRot_start());
			pstmt.setString(21, routeVO.getRot_end());
			pstmt.setString(22, routeVO.getRot_gps());
			pstmt.setInt   (23, 0);

			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			rot_id = rs.getString(1);
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
		return rot_id;
	}

	@Override
	public void update_front(RouteVO routeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(UPDATE_ROUTE_FRONT);
			pstmt.setString( 1, routeVO.getRot_name());
			pstmt.setString( 2, routeVO.getRot_describe());
			pstmt.setString( 3, routeVO.getRot_loc_start());
			pstmt.setString( 4, routeVO.getRot_loc_end());
			pstmt.setString( 5, routeVO.getRot_loc_start_des());
			pstmt.setString( 6, routeVO.getRot_loc_end_des());
			pstmt.setInt   ( 7, routeVO.getRot_status());
			pstmt.setString( 8, routeVO.getRot_photo());
			pstmt.setDouble( 9, routeVO.getRot_photo_loc());
			pstmt.setString(10, routeVO.getRot_id());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
	}
	
	@Override
	public void update_back(RouteVO routeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ROUTE_BACK);
			
			pstmt.setString( 1, routeVO.getMem_id());
			pstmt.setString( 2, routeVO.getRot_name());
			pstmt.setString( 3, routeVO.getRot_describe());
			pstmt.setString( 4, routeVO.getRot_loc_start());
			pstmt.setString( 5, routeVO.getRot_loc_end());
			pstmt.setDouble( 6, routeVO.getRot_hard());
			pstmt.setDouble( 7, routeVO.getRot_dis());
			pstmt.setDouble( 8, routeVO.getRot_height_up());
			pstmt.setDouble( 9, routeVO.getRot_height_down());
			pstmt.setDouble(10, routeVO.getRot_height_ave());
			pstmt.setDouble(11, routeVO.getRot_slope_up());
			pstmt.setDouble(12, routeVO.getRot_slope_down());
			pstmt.setDouble(13, routeVO.getRot_slope_ave());
			pstmt.setString(14, routeVO.getRot_loc_start_des());
			pstmt.setString(15, routeVO.getRot_loc_end_des());
			pstmt.setInt   (16, routeVO.getRot_status());
			pstmt.setString(17, routeVO.getRot_photo());
			pstmt.setInt   (18, routeVO.getRot_popu());
			pstmt.setDouble(19, routeVO.getRot_photo_loc());
			pstmt.setString(20, routeVO.getRot_id());
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
	}
	
	@Override
	public void update_back(String rot_id, Integer rot_popu) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ROUTE_POPU);
			pstmt.setInt   ( 1, rot_popu);
			pstmt.setString( 2, rot_id);
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
	}
	
	@Override
	public void update_messageCount(String rot_id) {
		Connection con = null;
		PreparedStatement pstmtSelect = null;
		PreparedStatement pstmtUpdate = null;
		
		try {
			con = ds.getConnection();
			pstmtSelect = con.prepareStatement(GETROUTEDETAILED);
			pstmtSelect.setString( 1, rot_id);
			ResultSet rs = pstmtSelect.executeQuery();
			rs.next();
			pstmtUpdate = con.prepareStatement(UPDATE_ROT_MES_COUNT);
			pstmtUpdate.setInt   ( 1, rs.getInt("rot_mes_count")+1);
			pstmtUpdate.setString( 2, rot_id);
			pstmtUpdate.executeUpdate();
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmtUpdate != null) {
				try {pstmtUpdate.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (pstmtSelect != null) {
				try {pstmtSelect.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
	}
	
	@Override
	public void delete(RouteVO routeVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_ROUTE);
			
			pstmt.setString(1, routeVO.getRot_id());
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
	}
	
	@Override
	public void closeRoute(String rot_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CLOSE_ROUTE);
			
			pstmt.setString(1, rot_id);
			pstmt.executeUpdate();
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
	}

	@Override
	public RouteVO getRouteDetailed(String rot_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RouteVO routeVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETROUTEDETAILED);
			pstmt.setString(1, rot_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeVO = new RouteVO();
				routeVO.setRot_id(rs.getString("rot_id"));
				routeVO.setMem_id(rs.getString("mem_id"));
				routeVO.setRot_name(rs.getString("rot_name"));
				routeVO.setRot_describe(rs.getString("rot_describe"));
				routeVO.setRot_loc_start(rs.getString("rot_loc_start"));
				routeVO.setRot_loc_end(rs.getString("rot_loc_end"));
				routeVO.setRot_hard(rs.getDouble("rot_hard"));
				routeVO.setRot_dis(rs.getDouble("rot_dis"));
				routeVO.setRot_height_up(rs.getDouble("rot_height_up"));
				routeVO.setRot_height_down(rs.getDouble("rot_height_down"));
				routeVO.setRot_height_ave(rs.getDouble("rot_height_ave"));
				routeVO.setRot_slope_up(rs.getDouble("rot_slope_up"));
				routeVO.setRot_slope_down(rs.getDouble("rot_slope_down"));
				routeVO.setRot_slope_ave(rs.getDouble("rot_slope_ave"));
				routeVO.setRot_loc_start_des(rs.getString("rot_loc_start_des"));
				routeVO.setRot_loc_end_des(rs.getString("rot_loc_end_des"));
				routeVO.setRot_status(rs.getInt("rot_status"));
				routeVO.setRot_photo(rs.getString("rot_photo"));
				routeVO.setRot_popu(rs.getInt("rot_popu"));
				routeVO.setRot_photo_loc(rs.getDouble("rot_photo_loc"));
				routeVO.setRot_start(rs.getString("rot_start"));
				routeVO.setRot_end(rs.getString("rot_end"));
				routeVO.setRot_gps(rs.getString("rot_gps"));
			}
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
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
		return routeVO;
	}

	@Override
	public List<RouteVO> getRouteList(String str) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<RouteVO> list = new ArrayList();
		RouteVO routeVO = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GETROUTELIST+str);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				routeVO = new RouteVO();
				routeVO.setMem_id(rs.getString("mem_id"));
				routeVO.setRot_name(rs.getString("rot_name"));
				routeVO.setRot_describe(rs.getString("rot_describe"));
				routeVO.setRot_hard(rs.getDouble("rot_hard"));
				routeVO.setRot_dis(rs.getDouble("rot_dis"));
				routeVO.setRot_slope_ave(rs.getDouble("rot_slope_ave"));
				routeVO.setRot_photo(rs.getString("rot_photo"));
				routeVO.setRot_popu(rs.getInt("rot_popu"));
				routeVO.setRot_id(rs.getString("rot_id"));
				routeVO.setRot_photo_loc(rs.getDouble("rot_photo_loc"));
				routeVO.setRot_slope_up(rs.getDouble("rot_slope_up"));
				list.add(routeVO);
			}
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
		return list;
	}

	
	@Override
	public List<RouteVO> getRouteCloseList(String str) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<RouteVO> list = new ArrayList();
		RouteVO routeVO = null;
		
		try {
			con = ds.getConnection();
			System.out.println("sql = " + GETROUTECLOSELIST+str);
			pstmt = con.prepareStatement(GETROUTECLOSELIST+str);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeVO = new RouteVO();
				routeVO.setMem_id(rs.getString("mem_id"));
				routeVO.setRot_name(rs.getString("rot_name"));
				routeVO.setRot_describe(rs.getString("rot_describe"));
				routeVO.setRot_hard(rs.getDouble("rot_hard"));
				routeVO.setRot_dis(rs.getDouble("rot_dis"));
				routeVO.setRot_slope_ave(rs.getDouble("rot_slope_ave"));
				routeVO.setRot_photo(rs.getString("rot_photo"));
				routeVO.setRot_popu(rs.getInt("rot_popu"));
				routeVO.setRot_id(rs.getString("rot_id"));
				routeVO.setRot_photo_loc(rs.getDouble("rot_photo_loc"));
				routeVO.setRot_slope_up(rs.getDouble("rot_slope_up"));
				list.add(routeVO);
			}
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
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
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETROUTEPHOTO);
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
}
