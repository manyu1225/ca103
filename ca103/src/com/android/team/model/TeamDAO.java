package com.android.team.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.android.util.ImageUtil;


public class TeamDAO implements TeamDAO_interface {
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "CA103G2";
//	搜尋某個
//	private static final String GETONEBYSTATUS = "select * from GP where GP_STATUS = 0 ";
//	查詢所有的揪團
	private static final String GETONEBYMEM = "select * from GP where MEM_ID = ?"; 
	
	private static final String GETALLBYPK = "select * from GP where GP_ID = ?";
	
	private static final String GETALLTEAM= "select * from GP";

	
	private static final String FINDIMGBYISBN = "select gp_photo from GP where GP_ID = ?";
	
//INSERT INTO "CA103G2"."GP" (GP_ID, GP_PHOTO) VALUES ('gp001', empty_blob())
	//UPDATE "CA103G2"."GP" SET GP_PHOTO=? WHERE ROWID=:sqldevrowid AND ORA_ROWSCN=:sqldevrowscn

//	給揪團的ID查詢所有揪團
	
	@Override
	public List<TeamVO> getByTeamId(String gp_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		List<TeamVO> teamVOlist= new ArrayList<TeamVO>();
		TeamVO teamVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALLBYPK);
			pstmt.setString(1, gp_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				teamVO = new TeamVO();
				teamVO.setGp_id(rs.getString(1));
				teamVO.setMem_id(rs.getString(2));
				teamVO.setRot_id(rs.getString(3));
//				teamVO.setCom_id(rs.getString(4));
//				teamVO.setCre_time(rs.getTimestamp(5));
				teamVO.setGp_title(rs.getString(6));
				teamVO.setGp_date(rs.getDate(7));
				teamVO.setGp_hour(rs.getInt(8));
				teamVO.setGp_time(rs.getInt(9));
				teamVO.setGp_desc(rs.getString(10));
				teamVO.setGp_content_edit(rs.getString(11));

				teamVO.setGp_photo(rs.getBytes(14));			
			
				teamVOlist.add(teamVO);
				System.out.println("=================");

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
		return teamVOlist;
	}
	
	//我登入後可以得到我的TeamVO清單
	@Override
	public List<TeamVO> getByMemId(String mem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		List<TeamVO> teamVOlist= new ArrayList<TeamVO>();
		TeamVO teamVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETONEBYMEM);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				teamVO = new TeamVO();
				teamVO.setGp_id(rs.getString(1));
				teamVO.setMem_id(rs.getString(2));
				teamVO.setRot_id(rs.getString(3));
//				teamVO.setCom_id(rs.getString(4));
//				teamVO.setCre_time(rs.getTimestamp(5));
				teamVO.setGp_title(rs.getString(6));
				teamVO.setGp_date(rs.getDate(7));
//				teamVO.setGp_hour(rs.getInt(8));
				teamVO.setGp_time(rs.getInt(9));
				teamVO.setGp_desc(rs.getString(10));
				teamVO.setGp_content_edit(rs.getString(11));
//				teamVO.setGp_content(rs.getString(12));
//				teamVO.setGp_content_photo(rs.getString(13));
				teamVO.setGp_photo(rs.getBytes(14));
//				teamVO.setPub_set(rs.getInt(15));
//				teamVO.setMin_num(rs.getInt(16));
//				teamVO.setMax_num(rs.getInt(17));
//				teamVO.setSign_up_DD(rs.getDate(18));
//				teamVO.setGp_status(rs.getInt(19));
//				teamVO.setTeamadded_QR(rs.getString(20));	
				teamVOlist.add(teamVO);
				System.out.println("=================");

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
		return teamVOlist;
	}
	@Override
	public byte[] getImage(String gp_id) {
		byte[] gp_photo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FINDIMGBYISBN);
			pstmt.setString(1, gp_id );

			rs = pstmt.executeQuery();
			while (rs.next()) {
				gp_photo = rs.getBytes(1);
				System.out.println(gp_photo+"===================");

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
		return gp_photo;
	}	
//--可以看到全部的清單--------------------------------
	@Override
	public List<TeamVO> getAll() {
		List<TeamVO> teamVOlist= new ArrayList<TeamVO>();
		TeamVO teamVO = null;
		Connection con = null;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
	
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GETALLTEAM);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				teamVO = new TeamVO();
				
		        byte [] shrink = ImageUtil.shrink(rs.getBytes(14), 50);
		 
				
				teamVO.setGp_id(rs.getString(1));
				teamVO.setMem_id(rs.getString(2));
				teamVO.setRot_id(rs.getString(3));
//				teamVO.setCom_id(rs.getString(4));
//				teamVO.setCre_time(rs.getTimestamp(5));
				teamVO.setGp_title(rs.getString(6));
				teamVO.setGp_date(rs.getDate(7));
//				teamVO.setGp_hour(rs.getInt(8));
				teamVO.setGp_time(rs.getInt(9));
				teamVO.setGp_desc(rs.getString(10));
				teamVO.setGp_content_edit(rs.getString(11));

//				teamVO.setGp_content(rs.getString(12));
//				teamVO.setGp_content_photo(rs.getString(13));
				
				teamVO.setGp_photo(shrink);
	
//				teamVO.setGp_status(rs.getInt(19));
//				teamVO.setTeamadded_QR(rs.getString(20));	
				teamVOlist.add(teamVO);
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
		return teamVOlist;

	}
//---------------------------------------------------------------------
	private void setteamVO(TeamVO teamVO,ResultSet rs) throws SQLException {
		System.out.println("============================");
		teamVO.setGp_id(rs.getString(1));
		teamVO.setMem_id(rs.getString(2));
		teamVO.setRot_id(rs.getString(3));
		teamVO.setCom_id(rs.getString(4));
		teamVO.setCre_time(rs.getTimestamp(5));
		teamVO.setGp_title(rs.getString(6));
		teamVO.setGp_date(rs.getDate(7));
		teamVO.setGp_hour(rs.getInt(8));
		teamVO.setGp_time(rs.getInt(9));
//		teamVO.setGp_desc(rs.getString(10));
//		teamVO.setGp_content(rs.getString(11));
//		teamVO.setGp_photo(rs.getBytes(12));
//		teamVO.setPub_set(rs.getInt(13));
//		teamVO.setMin_num(rs.getInt(14));
//		teamVO.setMax_num(rs.getInt(15));
//		teamVO.setSign_up_DD(rs.getDate(16));
//		teamVO.setGp_status(rs.getInt(17));
//		teamVO.setTeamadded_QR(rs.getString(18));
	}
//---------------------------------------------------------------------------
	public static byte[] getPictureByteArray_photo(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

}
	
	
	


	
	
	
