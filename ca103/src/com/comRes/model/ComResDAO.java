package com.comRes.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.com.model.ComVO;


public class ComResDAO implements ComResDAO_interface{

private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static final String INSERT = 
		"INSERT INTO COMMUNITY_RESPONSE(COMRES_ID, COMPO_ID, MEM_ID, CR_CONTENT, CR_FILE, CR_TIME, CRP_COUNT, CR_STATUS, CR_HF)"
		 + "VALUES ('R'||LPAD(to_char(COMMUNITY_seq.NEXTVAL), 6, '0'),?,?,?,?,?,?,?,?)";
	private static final String GET_COMRESVOS = 
			"SELECT * FROM COMMUNITY_RESPONSE WHERE COMPO_ID=? ORDER BY COMRES_ID ASC";
	private static final String GET_COM_RES_FILE = 
		"SELECT CR_FILE FROM COMMUNITY_RESPONSE  WHERE COMRES_ID=?";
	private static final String COMRES_HAVEFILE = 
		"UPDATE COMMUNITY_RESPONSE SET CR_HF=1 WHERE COMRES_ID=?";
	
	@Override
	public String insert(ComResVO comResVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
		String[] cols = {"comRes_id"};
		String comRes_id;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT, cols);
			
			pstmt.setString(1, comResVO.getComPo_id());
			pstmt.setString(2, comResVO.getMem_id());
			pstmt.setString(3, comResVO.getCr_content());
			pstmt.setString(4, comResVO.getCr_file());
			pstmt.setTimestamp(5, comResVO.getCr_time());
			pstmt.setInt(6, 0);		//	這邊直接給預設值為0
			pstmt.setInt(7, 0);		//	這邊直接給預設值為0
			pstmt.setInt(8, 0);		//	這邊直接給預設值為0
			
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			comRes_id = rs.getString(1);
			
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
		return comRes_id;
	}
	
	@Override
	public List<ComResVO> getComResVOs(String comPo_id) {
		
		ComResVO comResVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ComResVO> comResVOsList = new ArrayList<ComResVO>();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COMRESVOS);
			
			
			pstmt.setString(1, comPo_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				comResVO = new ComResVO();
				comResVO.setComRes_id(rs.getString("comRes_id"));
				comResVO.setComPo_id(rs.getString("comPo_id"));
				comResVO.setMem_id(rs.getString("mem_id"));			
				comResVO.setCr_content(rs.getString("cr_content"));
				comResVO.setCr_time(rs.getTimestamp("cr_time"));
				comResVO.setCrp_count(rs.getInt("crp_count"));
				comResVO.setCr_status(rs.getInt("cr_status"));
				comResVO.setCr_file(rs.getString("cr_file"));
				comResVO.setCr_hf(rs.getInt("cr_hf"));
				
				comResVOsList.add(comResVO);
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
		return comResVOsList;
	}

	@Override
	public void getCom_Res_File(String comRes_id, HttpServletResponse res) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ServletOutputStream out = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COM_RES_FILE);
			out = res.getOutputStream();
			
			pstmt.setString(1, comRes_id);
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
	public void crHaveFile(String comRes_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(COMRES_HAVEFILE);
			pstmt.setString(1,comRes_id);
			
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
	
}
	
		
	
	

