package com.routemessage.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class RouteMessageDAO implements RouteMessageDAO_interface{

	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERTROUTEMESSAGE = 
			"INSERT INTO ROUTEMESSAGE( " + 
			" ROTMES_ID,MEM_ID,ROT_ID,ROTMES_CONT,ROTMES_STATUS,ROTMES_TIME) " + 
			" VALUES (ROT_MES_SEQ.nextval,?,?,?,?,?)";
	private static final String GETBYROTID = 
			"SELECT * FROM ROUTEMESSAGE WHERE ROT_ID=? ORDER BY ROTMES_ID ASC";
	private static final String GETALL =
			"SELECT * FROM ROUTEMESSAGE";
	private static final String GETBYSTATUS = 
			"SELECT * FROM ROUTEMESSAGEREPORT WHERE ";
	private static final String CLOSEMESSAGE = 
			"UPDATE ROUTEMESSAGE SET ROTMES_STATUS=0 WHERE ROTMES_ID=?";
	private static final String UPDATEMESSAGE = 
			"UPDATE ROUTEMESSAGE SET ROTMES_CONT=? WHERE ROTMES_ID=?";
	private static final String COUNTMESSAGE = 
			"select ROT_ID ,count(*) from ROUTEMESSAGE where ROTMES_STATUS = 1 GROUP BY ROT_ID";
	
	
	@Override
	public void insert(RouteMessageVO routeMessageVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERTROUTEMESSAGE);
			pstmt.setString(1,routeMessageVO.getMem_id());
			pstmt.setString(2,routeMessageVO.getRot_id());
			pstmt.setString(3,routeMessageVO.getRotMes_cont());
			pstmt.setInt(4,1);
			pstmt.setTimestamp(5,routeMessageVO.getRotMes_time());
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
	public void close(Integer rotMes_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CLOSEMESSAGE);
			pstmt.setInt(1,rotMes_id);
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
	public List<RouteMessageVO> getByRouteId(String rot_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RouteMessageVO> listRouteMessage = new ArrayList();
		RouteMessageVO routeMessageVO;
		
		try {
			con = ds.getConnection();
			
			pstmt = con.prepareStatement(GETBYROTID);
			
			pstmt.setString(1, rot_id);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeMessageVO = new RouteMessageVO();
				routeMessageVO.setMem_id(rs.getString("mem_id"));
				routeMessageVO.setRot_id(rs.getString("rot_id"));
				routeMessageVO.setRotMes_cont(rs.getString("rotmes_cont"));
				routeMessageVO.setRotMes_time(rs.getTimestamp("rotmes_time"));
				routeMessageVO.setRotMes_id(rs.getInt("rotmes_id"));
				routeMessageVO.setRotMes_status(rs.getInt("rotmes_status"));
				listRouteMessage.add(routeMessageVO);
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
		return listRouteMessage;
	}

	@Override
	public void update(RouteMessageVO routeMessageVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATEMESSAGE);
			pstmt.setString(1,routeMessageVO.getRotMes_cont());
			pstmt.setInt(2,routeMessageVO.getRotMes_id());
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
	public List<RouteMessageVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<RouteMessageVO> listRouteMessage = new ArrayList();
		RouteMessageVO routeMessageVO;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeMessageVO = new RouteMessageVO();
				routeMessageVO.setMem_id(rs.getString("mem_id"));
				routeMessageVO.setRot_id(rs.getString("rot_id"));
				routeMessageVO.setRotMes_cont(rs.getString("rotmes_cont"));
				routeMessageVO.setRotMes_time(Timestamp.valueOf(rs.getString("rotmes_time")));
				routeMessageVO.setRotMes_id(rs.getInt("rotmes_id"));
				routeMessageVO.setRotMes_status(rs.getInt("rotmes_status"));
				listRouteMessage.add(routeMessageVO);
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
		return listRouteMessage;
	}
	
	@Override
	public Map<String, Integer> getRouteMessageCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		Map<String, Integer> routeMessageCountMap = new TreeMap();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(COUNTMESSAGE);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				routeMessageCountMap.put(rs.getString(1), rs.getInt(2));
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
		return routeMessageCountMap;
	}

	@Override
	public List<RouteMessageVO> getByStatus(String rotMesRep_status) {
		return null;
	}

	@Override
	public void delete(RouteMessageVO routeMessage) {
		
	}

}
