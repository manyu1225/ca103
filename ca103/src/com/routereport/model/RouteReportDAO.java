package com.routereport.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.routemessagereport.model.RouteMessageReportVO;

public class RouteReportDAO implements RouteReportDAO_interface {

	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INNERROUTEREPORT = 
			"INSERT INTO ROUTEREPORT(MEM_ID,ROT_ID,ROTREP_TIME,ROTREP_CONT,ROTREP_STATUS) " + 
			" VALUES (?,?,?,?,?)";
	private static final String ROTREPORNOT = 
			"SELECT ROT_ID FROM ROUTEREPORT WHERE MEM_ID=? AND ROT_ID=?";
	private static final String GETALL = 
			"SELECT * FROM ROUTEREPORT WHERE ROTREP_STATUS=0 ORDER BY ROTREP_TIME DESC";
	private static final String GETBYSTATUS = 
			"SELECT * FROM ROUTEREPORT WHERE ";
	private static final String CLOSEROT = 
			"UPDATE ROUTEREPORT SET ROTREP_STATUS = ? WHERE ROT_ID = ?";
	
	
	@Override
	public void insert(RouteReportVO routeReportVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INNERROUTEREPORT);
			
			pstmt.setString(1,routeReportVO.getMem_id());
			pstmt.setString(2,routeReportVO.getRot_id());
			pstmt.setTimestamp(3,routeReportVO.getRotRep_time());
			pstmt.setString(4,routeReportVO.getRotRep_cont());
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
	public List<RouteReportVO> getAll() {
		Connection con=null;
		PreparedStatement pstmt = null;
		RouteReportVO rotRepVO = null;
		List<RouteReportVO> rotRepList = new ArrayList();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				rotRepVO = new RouteReportVO();
				
				rotRepVO.setMem_id(rs.getString("mem_id"));
				rotRepVO.setRot_id(rs.getString("rot_id"));
				rotRepVO.setRotRep_time(Timestamp.valueOf(rs.getString("rotRep_time")));
				rotRepVO.setRotRep_cont(rs.getString("rotRep_cont"));
				rotRepVO.setRotRep_status(rs.getInt("rotRep_status"));
				
				rotRepList.add(rotRepVO);
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
		return rotRepList;
	}

	@Override
	public List<RouteReportVO> getByStatus(String rotRep_status) {
		Connection con=null;
		PreparedStatement pstmt = null;
		RouteReportVO rotRepVO = null;
		List<RouteReportVO> rotRepList = new ArrayList();
		
		try {
			con = ds.getConnection();
			if("0".equals(rotRep_status)) {
				pstmt = con.prepareStatement(GETBYSTATUS + " ROTREP_STATUS = 0 ORDER BY ROTREP_TIME DESC");
			} else{
				pstmt = con.prepareStatement(GETBYSTATUS + " ROTREP_STATUS != 0 ORDER BY ROTREP_TIME DESC");
			}
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				rotRepVO = new RouteReportVO();
				
				rotRepVO.setMem_id(rs.getString("mem_id"));
				rotRepVO.setRot_id(rs.getString("rot_id"));
				rotRepVO.setRotRep_time(Timestamp.valueOf(rs.getString("rotRep_time")));
				rotRepVO.setRotRep_cont(rs.getString("rotRep_cont"));
				rotRepVO.setRotRep_status(rs.getInt("rotRep_status"));
				
				rotRepList.add(rotRepVO);
			}
			
			while((rotRepList.size()%10) > 0){
				rotRepVO = new RouteReportVO();
				rotRepVO.setMem_id("");
				rotRepList.add(rotRepVO);
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
		return rotRepList;
	}

	@Override
	public int rotRepOrNot(RouteReportVO routeReportVO) {
		Connection con=null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ROTREPORNOT);
			
			pstmt.setString(1, routeReportVO.getMem_id());
			pstmt.setString(2, routeReportVO.getRot_id());
			
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
	
	@Override
	public void closeRot(String rot_id, Integer rotRep_status) {
		
		Connection con=null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CLOSEROT);
			
			pstmt.setInt(1, rotRep_status);
			pstmt.setString(2, rot_id);
			
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
	public void update(RouteReportVO routeReportVO) {
		
	}
	
	@Override
	public void delete(RouteReportVO routeReportVO) {
		
	}

}
