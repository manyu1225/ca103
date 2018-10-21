package com.secondShop.currencyCheackout.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.secondShop.currency.model.CurrencyDAO;
import com.secondShop.currency.model.CurrencyVO;


public class CurrencyCheackoutDAO1 implements CurrencyCheackoutDAO_Interface{
	
	private static final String CURRENCYCHEACKOU = 
	"insert into currency_cheackout (MEM_ID,CHEACKOUT_ID,CHEACKOUT_BALANCE,CHEACKOUT_DATE,CHEACKOUT_STATUS,currency_id)" 
           + "VALUES (?,'CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),?,current_TIMESTAMP,0,?)";
	private static final String UPDATE_CURRENCYCHEACKOU = "update currency_cheackout set CHEACKOUT_STATUS =1 where currency_id = ?";
	private static final String SELECT_ALL= "SELECT * FROM currency_cheackout";//不分狀態
	private static final String SELECT_STATUS = "SELECT * FROM currency_cheackout wherer CHEACKOUT_STATUS = ?"; 
	static Context ctx = null;
	static DataSource ds = null;
	
//	static {	
//			try {
//				ctx = new javax.naming.InitialContext();
//				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");	
//			} catch (NamingException e) {
//				e.printStackTrace();
//			}		
//	}
	
	@Override
	public void insertCurrencyCheackout(CurrencyCheackoutVO currencyCheackoutVO) { 
		Connection con = null;
		PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(CURRENCYCHEACKOU);
				con.setAutoCommit(false);
				pstmt.setString(1,currencyCheackoutVO.getMemId());
				pstmt.setInt(2,currencyCheackoutVO.getCheackoutBalance());
				pstmt.executeUpdate();
				con.commit();//我要送了
			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally {
				if(con!=null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		


	}

	@Override
	public void updateCurrencyCheackout(String currencyId) {//提領成功
		Connection con = null;
		PreparedStatement pstmt = null;
		CurrencyDAO currencyDAO = new CurrencyDAO();
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_CURRENCYCHEACKOU);
				con.setAutoCommit(false);
				pstmt.setString(1,currencyId);
				currencyDAO.updateCurrecy(con,currencyId);
				con.commit();//我要送了
			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally {
				if(con!=null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}		
	}

	@Override
	public List<CurrencyCheackoutVO> currencyCheackoutList(Integer cheackoutStatus) { //依狀態查詢
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CurrencyCheackoutVO currencyCheackoutVO = null;
		List<CurrencyCheackoutVO> currencyCheackoutList = new ArrayList<>();
	
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(SELECT_STATUS);
				pstmt.setInt(1, cheackoutStatus);
				rs = pstmt.executeQuery();//查詢資料列	
				while(rs.next()) {
					currencyCheackoutVO = new CurrencyCheackoutVO();
					currencyCheackoutVO.setMemId(rs.getString(1));
					currencyCheackoutVO.setCheackoutId(rs.getString(3));
					currencyCheackoutVO.setCurrencyId(rs.getString(2));
					currencyCheackoutVO.setCheackoutBalance(rs.getInt(4));
					currencyCheackoutVO.setCheackoutDate(rs.getTimestamp(5));
					currencyCheackoutVO.setCheackoutStatus(rs.getInt(6));
					currencyCheackoutList.add(currencyCheackoutVO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(con!=null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		
		return currencyCheackoutList;
	}

	@Override
	public List<CurrencyCheackoutVO> currencyCheackoutList() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CurrencyCheackoutVO currencyCheackoutVO = null;
		List<CurrencyCheackoutVO> currencyCheackoutList = new ArrayList<>();
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(SELECT_ALL);
				rs = pstmt.executeQuery();//查詢資料列	
				while(rs.next()) {
					currencyCheackoutVO = new CurrencyCheackoutVO();
					currencyCheackoutVO.setMemId(rs.getString(1));
					currencyCheackoutVO.setCheackoutId(rs.getString(3));
					currencyCheackoutVO.setCurrencyId(rs.getString(2));
					currencyCheackoutVO.setCheackoutBalance(rs.getInt(4));
					currencyCheackoutVO.setCheackoutDate(rs.getTimestamp(5));
					currencyCheackoutVO.setCheackoutStatus(rs.getInt(6));
					currencyCheackoutList.add(currencyCheackoutVO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(con!=null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		return currencyCheackoutList;
	}

	@Override
	public void insertCurrencyCheackout(Connection con, CurrencyCheackoutVO currencyCheackoutVO) {
		PreparedStatement pstmt = null;
			try {
				pstmt = con.prepareStatement(CURRENCYCHEACKOU);
				pstmt.setString(1,currencyCheackoutVO.getMemId());	
				pstmt.setInt(2,currencyCheackoutVO.getCheackoutBalance());
				pstmt.setString(3, currencyCheackoutVO.getCurrencyId());
				pstmt.executeUpdate();
			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}	
	}

}
