package com.comFile.model;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.IdentityHashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class ComFileDAO implements ComFileDAO_interface {

private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_FILE = 
			"INSERT INTO COMMUNITY_UPLOAD_File (cuf_id, com_id, mem_id, comPo_id,cuf, cuf_time, album) VALUES ('F'||LPAD(to_char(COMMUNITY_UPLOAD_FILE_seq.NEXTVAL), 6, '0'), ?, ?, ?, ?, ?, ?)";
	private static final String GET_CUF_IDS = 
			"SELECT CUF_ID, CF.COMPO_ID, CUF FROM COMMUNITY_POST CP JOIN COMMUNITY_UPLOAD_FILE CF ON CP.COMPO_ID=CF.COMPO_ID WHERE CP.COMPO_ID=? ORDER BY CP.COMPO_ID DESC";
	private static final String GET_COM_FILE =
			"SELECT CUF FROM COMMUNITY_POST CP JOIN COMMUNITY_UPLOAD_FILE CF ON CP.COMPO_ID=CF.COMPO_ID WHERE CF.CUF_ID =? ";
	private static final String GET_SIDE_FILE =
			"SELECT CUF_ID FROM COMMUNITY_UPLOAD_FILE WHERE COM_ID=? AND ROWNUM <=3 ORDER BY CUF_ID DESC";
	private static final String GET_ALL_FILE =
			"SELECT CUF_ID FROM COMMUNITY_UPLOAD_FILE WHERE COM_ID=? ORDER BY CUF_ID DESC";
	
	@Override
	public String insert(ComFileVO comFileVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] cols = { "cuf_id" };
		String cuf_id;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_FILE, cols);
			
			pstmt.setString( 1, comFileVO.getCom_id());
			pstmt.setString( 2, comFileVO.getMem_id());
			pstmt.setString( 3, comFileVO.getComPo_id());
			pstmt.setString( 4, comFileVO.getCuf());
			pstmt.setTimestamp( 5, comFileVO.getCuf_time());
			pstmt.setString( 6, comFileVO.getAlbum());
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			cuf_id = rs.getString(1);
			
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
		return cuf_id;
	}

	@Override
	public List<String> getCuf_ids(String comPo_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> cuf_idsList = new ArrayList<String>();
		
		 
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_CUF_IDS);
			
			
			pstmt.setString(1, comPo_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cuf_idsList.add(rs.getString(1));
			}
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
			if (rs != null) {
				try {rs.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
			
		}
		return cuf_idsList;
	}
	
	@Override
	public void getCom_File(String cuf_id, HttpServletResponse res) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ServletOutputStream out = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COM_FILE);
			out = res.getOutputStream();
			
			pstmt.setString(1, cuf_id);
			rs = pstmt.executeQuery();
			rs.next();
			
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] buffer = decoder.decode(rs.getString(1));
			out.write(buffer);
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}catch (IOException ioe) {
			throw new RuntimeException("A IOException error occured. " + ioe.getMessage());
		} finally {
			if (out != null) {
				try {out.close();} catch (IOException ioe) {ioe.printStackTrace(System.err);}
			}
			if (rs != null) {
				try {rs.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
	}
	
	@Override
	public List<String> getSideFile(String com_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> siedeFileList = new ArrayList<String>();
		
		 
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SIDE_FILE);
			
			pstmt.setString(1, com_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
			siedeFileList.add(rs.getString(1));
			}
			
			
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
			if (rs != null) {
				try {rs.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
			
		}
		return siedeFileList;
	}

	@Override
	public List<String> getAllFile(String com_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<String> allFileList = new ArrayList<String>();
		
		 
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_FILE);
			
			pstmt.setString(1, com_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				allFileList.add(rs.getString(1));
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		}finally {
			if (rs != null) {
				try {rs.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (pstmt != null) {
				try {pstmt.close();} catch (SQLException se) {se.printStackTrace(System.err);}
			}
			if (con != null) {
				try {con.close();} catch (Exception e) {e.printStackTrace(System.err);}
			}
		}
		return allFileList;
	}
	
}
