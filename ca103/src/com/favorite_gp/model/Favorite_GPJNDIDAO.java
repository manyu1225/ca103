package com.favorite_gp.model;

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

import com.gp.model.GPVO;
import com.mem.model.MemVO;



public class Favorite_GPJNDIDAO implements Favorite_GPDAO_interface{
	
	private static final String INSERT_STMT = "insert into FAVORITE_GP(MEM_ID,GP_ID,FAV_TIMESTAMP) values (?,?,?)";
	private static final String DELETE_FAV_GP = "DELETE FROM FAVORITE_GP WHERE MEM_ID = ? and GP_ID = ?";
	private static final String FIND_FAV_GP = "select GP.* from GP join FAVORITE_GP on GP.GP_ID = FAVORITE_GP.GP_ID where FAVORITE_GP.MEM_ID = ? AND GP_STATUS!=10 ORDER BY gp_status,gp_date";
	private static final String FIND_PUBLIC_FAV_GP = "select GP.* from GP join FAVORITE_GP on GP.GP_ID = FAVORITE_GP.GP_ID where FAVORITE_GP.MEM_ID = ?  AND gp_status=0 AND pub_set=0";
	
	
	
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
	public List<GPVO> searchPubfGP(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<GPVO> list = new ArrayList<>();
		GPVO gpVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_PUBLIC_FAV_GP);
			pstmt.setString(1, memVO.getMem_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
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
	
	@Override
	public List<GPVO> searchFavGP(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<GPVO> list = new ArrayList<>();
		GPVO gpVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_FAV_GP);
			pstmt.setString(1, memVO.getMem_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
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

	@Override
	public void addFavGP(Favorite_GPVO favGPVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, favGPVO.getMem_id());
			pstmt.setString(2, favGPVO.getGp_id());
			pstmt.setTimestamp(3, favGPVO.getFav_timestamp());
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
	public void deleteFav_GP(Favorite_GPVO favGPVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_FAV_GP);

			pstmt.setString(1, favGPVO.getMem_id());
			pstmt.setString(2, favGPVO.getGp_id());
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
	
	private void setgpVO(GPVO gpVO,ResultSet rs) throws SQLException {
		gpVO.setGp_id(rs.getString(1));
		gpVO.setMem_id(rs.getString(2));
		gpVO.setRot_id(rs.getString(3));
		gpVO.setCom_id(rs.getString(4));
		gpVO.setCre_time(rs.getTimestamp(5));
		gpVO.setGp_title(rs.getString(6));
		gpVO.setGp_date(rs.getDate(7));
		gpVO.setGp_hour(rs.getInt(8));
		gpVO.setGp_time(rs.getInt(9));
		gpVO.setGp_desc(rs.getString(10));
		gpVO.setGp_content_edit(rs.getString(11));
		gpVO.setGp_content(rs.getString(12));
		gpVO.setGp_content_photo(rs.getString(13));
		gpVO.setGp_photo(rs.getBytes(14));
		gpVO.setPub_set(rs.getInt(15));
		gpVO.setMin_num(rs.getInt(16));
		gpVO.setMax_num(rs.getInt(17));
		gpVO.setSign_up_DD(rs.getDate(18));
		gpVO.setGp_status(rs.getInt(19));
		gpVO.setTeamadded_QR(rs.getString(20));
	}
	
	
	
}
