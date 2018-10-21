package com.reported_gp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Reported_GPJNDIDAO implements Reported_GPDAO_interface{

	private static final String INSERT_STMT = "insert into REPORTED_GP(MEM_ID,GP_ID,REP_TIME,REP_DETAIL,REP_STATUS) values (?,?,?,?,?)";
	private static final String GET_STMT = "select * from REPORTED_GP ";
	private static final String UPDATE_STMT = "update REPORTED_GP set REP_STATUS = ? where MEM_ID = ? AND GP_ID = ?";
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@Override
	public void addRepGP(Reported_GPVO repVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, repVO.getMem_id());
			pstmt.setString(2, repVO.getGp_id());
			pstmt.setTimestamp(3, repVO.getRep_time());
			pstmt.setString(4, repVO.getRep_detail());
			pstmt.setInt(5, repVO.getRep_status());
			pstmt.executeUpdate();
		}catch (SQLException se) {
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
	public void updateRepGP(Reported_GPVO repVO, int status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, status);
			pstmt.setString(2, repVO.getMem_id());
			pstmt.setString(3, repVO.getGp_id());
			pstmt.executeUpdate();
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
	public List<Reported_GPVO> getAllRepGP(boolean hasDisposed) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<Reported_GPVO> list = new ArrayList<>();
		Reported_GPVO repGPVO = null;
		try {
			
			con = ds.getConnection();
			if(hasDisposed) {
				pstmt = con.prepareStatement(GET_STMT + " where rep_status != 0");
			}else{
				pstmt = con.prepareStatement(GET_STMT + " where rep_status = 0");
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				repGPVO = new Reported_GPVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4), rs.getInt(5));
				list.add(repGPVO);
			}
		}catch (SQLException se) {
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
	
}
