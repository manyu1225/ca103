package com.android.favorite_gp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.batch.Main;

import com.android.mem.model.MemVO;
import com.android.team.model.TeamVO;




public class Favorite_GPJDBCDAO implements Favorite_GPDAO_interface{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "CA103G2";
	
	private static final String FIND_PUBLIC_FAV_GP = "select GP.* from GP join FAVORITE_GP on GP.GP_ID = FAVORITE_GP.GP_ID where FAVORITE_GP.MEM_ID = ?  AND gp_status=0 AND pub_set=0";
	
	private static final String INSERT_STMT = "insert into FAVORITE_GP(MEM_ID,GP_ID,FAV_TIMESTAMP) values (?,?,?)";
	@Override
	public void addFavGP(Favorite_GPVO favGPVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);
			pstmt.setString(1, favGPVO.getMem_id());
			pstmt.setString(2, favGPVO.getGp_id());
			pstmt.setTimestamp(3, favGPVO.getFav_timestamp());
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
//====================================





//=========================================	
	private static final String FIND_FAV_GP = 
			"select GP.* from GP join FAVORITE_GP on GP.GP_ID = FAVORITE_GP.GP_ID "
			+ "where FAVORITE_GP.MEM_ID = ?"
			+ " AND GP_STATUS!=10 ORDER BY gp_status,gp_date";
	@Override
	public List<TeamVO> searchFavGP(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<TeamVO> list = new ArrayList<>();
		TeamVO teamVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_FAV_GP);
			pstmt.setString(1, memVO.getMem_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				teamVO = new TeamVO();
				setgpVO(teamVO,rs);
				list.add(teamVO);
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

	
	
	
	
	
	
	@Override
	public List<TeamVO> searchPubfGP(MemVO memVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;;
		List<TeamVO> list = new ArrayList<>();
		TeamVO teamVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_PUBLIC_FAV_GP);
			pstmt.setString(1, memVO.getMem_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				teamVO = new TeamVO();
				setgpVO(teamVO,rs);
				list.add(teamVO);
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
	
	//刪除收藏的團
private static final String DELETE_FAV_GP =
"DELETE FROM FAVORITE_GP WHERE MEM_ID = ? and GP_ID = ?";
		
	@Override
	public void deleteFav_GP(Favorite_GPVO favGPVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE_FAV_GP);

			pstmt.setString(1, favGPVO.getMem_id());
			pstmt.setString(2, favGPVO.getGp_id());
			pstmt.executeUpdate();
			
			// Handle any driver errors
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
	
	private void setgpVO(TeamVO gpVO,ResultSet rs) throws SQLException {
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
