package com.route.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import com.android.util.ImageUtil;
import com.forPos_report.model.Forum_post_report_VO;

public class photoChange {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "Team2";
		String passwd = "Team2";
		
			Connection con = null;
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement("select rot_photo, rot_id from ROUTE ");
				ResultSet rs = pstmt.executeQuery();
				
				int i = 0;
				while(rs.next()) {
					i++;
	//				進資料庫前把圖片縮小,節省網路流量
					byte []  rot_photo_init = Base64.getDecoder().decode(rs.getString("rot_photo"));
					byte [] rot_photo_ing = ImageUtil.shrink(rot_photo_init, 300);
					Base64.Encoder encoder = Base64.getEncoder();
					String rot_photo_end = encoder.encodeToString(rot_photo_ing);
					System.out.println(i + "轉好圖片準備放進去DB");
					con = change(rot_photo_end, rs.getString("ROT_ID"), con);
					System.out.println(i + "已經放進去了");
				}
				
				
			}catch(ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
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
	
	
	public static Connection change(String rot_id, String rot_photo, Connection con) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String userid = "CA103G2";
		String passwd = "CA103G2";
		
			PreparedStatement pstmt = null;

			try {

				Class.forName(driver);
				con = DriverManager.getConnection(url, userid, passwd);
				pstmt = con.prepareStatement("UPDATE ROUTE SET ROT_photo=? where ROT_ID=?");
				pstmt.setString(1, rot_photo);
				pstmt.setString(2, rot_id);
				
				pstmt.executeUpdate();
				return con;
			}catch(ClassNotFoundException e) {
				throw new RuntimeException("Couldn't load database driver. "
						+ e.getMessage());
			} catch (SQLException se) {
				throw new RuntimeException("A database error occured. " + se.getMessage());
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
						return con;
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}

		
	}

}
