package com.android.routecollection.model;

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

import com.android.mem.model.MemVO;
import com.android.route.model.RouteVO;
import com.android.team.model.TeamVO;



public class RouteCollectionDAO implements RouteCollectionDAO_interface{
	
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	
	private static final String DELETE_COLLECTION = 
			"delete from routecollection where mem_id=? AND rot_id=?";
	
	
	private static final String COLLORNOT = 
			"SELECT ROT_ID FROM ROUTECOLLECTION WHERE MEM_ID=? AND ROT_ID=?";
	
//我要得˙
	private static final String GET_COLLECTION_LIST = 
			"SELECT * FROM ROUTECOLLECTION , ROUTE WHERE " + 
			" ROUTECOLLECTION.ROT_ID = ROUTE.ROT_ID AND ROUTECOLLECTION.MEM_ID = ? " + 
			" AND ROUTE.ROT_STATUS != 2 ";
	
	@Override
	public List<RouteVO> findRouteCollection(MemVO memVO) {
		List<RouteVO> list = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		RouteVO routeVO = null;
		try {
				con = ds.getConnection();
	
			pstmt = con.prepareStatement(GET_COLLECTION_LIST );
			pstmt.setString(1,memVO.getMem_id());
			ResultSet rs = pstmt.executeQuery();
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
				list.add(routeVO);
			}
		
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
		return list;
	}
		
		
		
		
		

	@Override
	public List<RouteVO> findRouteCollectionList(String mem_id, String sql) {
		List<RouteVO> list = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		RouteVO routeVO = null;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GET_COLLECTION_LIST + sql);
			pstmt.setString(1,mem_id);
			ResultSet rs = pstmt.executeQuery();
			
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
				list.add(routeVO);
			}
			
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
		return list;
	}
	
	
	private static final String INSERT_COLLECTION = 
			"insert into ROUTECOLLECTION (MEM_ID,ROT_ID,ROTCOLL_TIME) VALUES (?,?,?)";
	@Override
	public void insert(RouteCollectionVO routeCollectionVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_COLLECTION);
			
			pstmt.setString(1, routeCollectionVO.getMem_id());
			pstmt.setString(2, routeCollectionVO.getRot_id());
			pstmt.setTimestamp(3, routeCollectionVO.getRotColl_time());
			
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
	public void update(RouteCollectionVO routeCollectionVO) {
		
	}

	@Override
	public void delete(RouteCollectionVO routeCollectionVO) {
		Connection con=null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_COLLECTION);
			
			pstmt.setString(1, routeCollectionVO.getMem_id());
			pstmt.setString(2, routeCollectionVO.getRot_id());
			
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
	public int collOrNot(RouteCollectionVO routeCollectionVO) {
		Connection con=null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(COLLORNOT);
			
			pstmt.setString(1, routeCollectionVO.getMem_id());
			pstmt.setString(2, routeCollectionVO.getRot_id());
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return 1;
			}else {
				return 0;
			}
			
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


	

}
