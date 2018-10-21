package com.comPo.model;

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

import com.com.model.ComVO;

public class ComPoDAO implements ComPoDAO_interface {

	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT_COMPO = "INSERT INTO COMMUNITY_POST(" + 
			" COMPO_ID,COM_ID,MEM_ID,CP_CONTENT,CP_TIME,CPR_COUNT,CPP_COUNT,CP_STATUS,CP_HF)" + 
			" VALUES ('P'||LPAD(to_char(COMMUNITY_seq.NEXTVAL), 6, '0'),?,?,?,?,?,?,?,?)";
	private static final String GET_BY_COMID = 
			" SELECT * FROM COMMUNITY_POST WHERE COM_ID=? ORDER BY COMPO_ID DESC";
	private static final String COMPOST_HAVEFILE = 
			"UPDATE COMMUNITY_POST SET CP_HF=1 WHERE COMPO_ID=?";
	
	
	
	@Override
	public String insert(ComPoVO comPoVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String[] cols = { "comPo_id" };
		String comPo_id;
		
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_COMPO, cols);
			
			pstmt.setString(1,comPoVO.getCom_id());
			pstmt.setString(2,comPoVO.getMem_id());
			pstmt.setString(3,comPoVO.getCp_content());
			pstmt.setTimestamp(4,comPoVO.getCp_time());
			pstmt.setInt(5,0);	//	這邊直接給預設值為0
			pstmt.setInt(6,0);	//	這邊直接給預設值為0
			pstmt.setInt(7,0);	//	這邊直接給預設值為0
			pstmt.setInt(8,0);	//	這邊直接給預設值為0
			
			pstmt.executeUpdate();
				
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			comPo_id = rs.getString(1);
			
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
		return comPo_id;
	}

	@Override
	public void update(ComPoVO comPoVO) {

	}

	@Override
	public void delete(ComPoVO comPoVO) {

	}

	@Override
	public List<ComPoVO> getAll() {

		return null;
	}

	@Override
	public List<ComPoVO> getByComId(String com_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		List<ComPoVO> comPoList = new ArrayList<ComPoVO>();
		ComPoVO comPoVO = null;
		
		try {
			con = ds.getConnection();			
			pstmt = con.prepareStatement(GET_BY_COMID);			
			pstmt.setString(1, com_id);			
			ResultSet rs = pstmt.executeQuery();
			//	先不拿貼文狀態
			while(rs.next()) {
				comPoVO = new ComPoVO();
				comPoVO.setComPo_id(rs.getString("comPo_id"));
				comPoVO.setCom_id(rs.getString("com_id"));
				comPoVO.setMem_id(rs.getString("mem_id"));
				comPoVO.setCp_content(rs.getString("cp_content"));
				comPoVO.setCp_time(Timestamp.valueOf(rs.getString("cp_time")));
				comPoVO.setCpr_count(rs.getInt("cpr_count"));
				comPoVO.setCpp_count(rs.getInt("cpp_count"));	
				comPoVO.setCp_hf(rs.getInt("cp_hf"));
				comPoList.add(comPoVO);
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
		return comPoList;
	}

	
	@Override
	public List<ComPoVO> getByComPoId(String comPo_id) {
		
		return null;
	}
	
	@Override
	public void cpHaveFile(String comPo_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(COMPOST_HAVEFILE);
			pstmt.setString(1,comPo_id);
			
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
