package com.reported_gp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.gp.model.GPVO;

public class Reported_GPJDBCDAO implements Reported_GPDAO_interface{

	private static final String INSERT_STMT = "insert into REPORTED_GP(MEM_ID,GP_ID,REP_TIME,REP_DETAIL,REP_STATUS) values (?,?,?,?,?)";
	private static final String GET_STMT = "select * from REPORTED_GP ";
	private static final String UPDATE_STMT = "update REPORTED_GP set REP_STATUS = ? where MEM_ID = ? AND GP_ID = ?";
	
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "CA103G2";
	
	public static void main(String[] args) {
		Reported_GPJDBCDAO dao = new Reported_GPJDBCDAO();
		
////		插入揪團檢舉
//		Reported_GPVO repGPVO= new Reported_GPVO("M000001", "G000004", new Timestamp(System.currentTimeMillis()), "幹你媽草植百", 0);
//		dao.addRepGP(repGPVO);
		
////		查所有尚未處理檢舉的揪團
//		List<Reported_GPVO> list = dao.getAllRepGP(false);
//		for(Reported_GPVO repGOVO : list) {
//			System.out.println(repGOVO.getGp_id());
//		}
		
////		更新狀態
//		Reported_GPVO repGPVO= new Reported_GPVO("M000001", "G000004", new Timestamp(System.currentTimeMillis()), "幹你媽草植百", 0);
//		dao.updateRepGP(repGPVO, 1);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public void addRepGP(Reported_GPVO repVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, repVO.getMem_id());
			pstmt.setString(2, repVO.getGp_id());
			pstmt.setTimestamp(3, repVO.getRep_time());
			pstmt.setString(4, repVO.getRep_detail());
			pstmt.setInt(5, repVO.getRep_status());
			pstmt.executeUpdate();
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public void updateRepGP(Reported_GPVO repVO, int status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);
			pstmt.setInt(1, status);
			pstmt.setString(2, repVO.getMem_id());
			pstmt.setString(3, repVO.getGp_id());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
	public List<Reported_GPVO> getAllRepGP(boolean hasDisposed) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<Reported_GPVO> list = new ArrayList<>();
		Reported_GPVO repGPVO = null;
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
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
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
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
		return list;
	}

}
