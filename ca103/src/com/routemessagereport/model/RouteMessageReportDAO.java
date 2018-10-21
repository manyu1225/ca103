package com.routemessagereport.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.routemessage.model.RouteMessageVO;

public class RouteMessageReportDAO implements RouteMessageReportDAO_interface {
	
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT = 
			"INSERT INTO ROUTEMESSAGEREPORT( " + 
			" MEM_ID,ROTMES_ID,ROTMESREP_TIME,ROTMESREP_CONT,ROTMESREP_STATUS) " + 
			" VALUES (?,?,?,?,?)";
	private static final String ROTMESSREPORNOT = 
			"SELECT ROTMES_ID FROM ROUTEMESSAGEREPORT WHERE MEM_ID=?";
	private static final String GETALL = 
			"SELECT * FROM ROUTEMESSAGEREPORT WHERE ROTMESREP_STATUS=0 ORDER BY ROTMESREP_TIME DESC";
	private static final String CLOSEORNOT = 
			"UPDATE ROUTEMESSAGEREPORT SET ROTMESREP_STATUS=? WHERE ROTMES_ID=?";
	private static final String GETBYSTATUS = 
			"SELECT * FROM ROUTEMESSAGEREPORT WHERE ";
	
	
	@Override
	public void insert(RouteMessageReportVO routeMessageReportVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1,routeMessageReportVO.getMem_id());
			pstmt.setInt(2,routeMessageReportVO.getRotMes_id());
			pstmt.setTimestamp(3,routeMessageReportVO.getRotMesRep_time());
			pstmt.setString(4,routeMessageReportVO.getRotMesRep_cont());
			pstmt.setInt(5,0);
			
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
	public List<RouteMessageReportVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		RouteMessageReportVO rotMessRepVO = null;
		List<RouteMessageReportVO> rotMessRepList = new ArrayList();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				rotMessRepVO = new RouteMessageReportVO();
				
				rotMessRepVO.setMem_id(rs.getString("mem_id"));
				rotMessRepVO.setRotMes_id(rs.getInt("rotMes_id"));
				rotMessRepVO.setRotMesRep_time(Timestamp.valueOf(rs.getString("rotMesRep_time")));
				rotMessRepVO.setRotMesRep_cont(rs.getString("rotMesRep_cont"));
				rotMessRepVO.setRotMesRep_status(rs.getInt("rotMesRep_status"));
				
				rotMessRepList.add(rotMessRepVO);
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
		return rotMessRepList;
	}

	@Override
	public List<RouteMessageReportVO> getByStatus(String rotMesRep_status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		RouteMessageReportVO rotMessRepVO = null;
		List<RouteMessageReportVO> rotMessRepList = new ArrayList();
		
		try {
			con = ds.getConnection();
			if("0".equals(rotMesRep_status)) {
				pstmt = con.prepareStatement(GETBYSTATUS + " ROTMESREP_STATUS = 0 ORDER BY ROTMESREP_TIME DESC");
			} else{
				pstmt = con.prepareStatement(GETBYSTATUS + " ROTMESREP_STATUS != 0 ORDER BY ROTMESREP_TIME DESC");
			}
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				rotMessRepVO = new RouteMessageReportVO();
				
				rotMessRepVO.setMem_id(rs.getString("mem_id"));
				rotMessRepVO.setRotMes_id(rs.getInt("rotMes_id"));
				rotMessRepVO.setRotMesRep_time(Timestamp.valueOf(rs.getString("rotMesRep_time")));
				rotMessRepVO.setRotMesRep_cont(rs.getString("rotMesRep_cont"));
				rotMessRepVO.setRotMesRep_status(rs.getInt("rotMesRep_status"));
				
				rotMessRepList.add(rotMessRepVO);
			}
			
			while((rotMessRepList.size()%10) > 0){
				rotMessRepVO = new RouteMessageReportVO();
				rotMessRepVO.setMem_id("");
				rotMessRepList.add(rotMessRepVO);
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
		return rotMessRepList;
	}

	@Override
	public List rotMessRepOrNot(String mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List list=new ArrayList();
		RouteMessageReportVO routeMessageReportVO;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ROTMESSREPORNOT);
			
			pstmt.setString(1,mem_id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				routeMessageReportVO = new RouteMessageReportVO();
				int rotMes_id = rs.getInt(1);
				list.add(rotMes_id);
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

	@Override
	public void closeMesOrNot(Integer rotMes_id, Integer rotMesRep_status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CLOSEORNOT);
			
			pstmt.setInt(1,rotMesRep_status);
			pstmt.setInt(2,rotMes_id);
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
	public void update(RouteMessageReportVO routeMessageReportVO) {
		
	}
	
	@Override
	public void delete(RouteMessageReportVO routeMessageReportVO) {
		
	}
}
