package com.secondShop.currency.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.secondShop.currencyCheackout.model.CurrencyCheackoutDAO1;
import com.secondShop.currencyCheackout.model.CurrencyCheackoutVO;

public class CurrencyDAO implements CurrencyDAO_interface{
	private static final String CURRENCY_INSERT=
			"insert into currency values(?,'CUY'||lpad(to_char(currency_seq.nextval),3,'0'),?,current_TIMESTAMP,?,?)";
	private static final String CURRENCY_UPDATESTATUS = "update currency set CURRENCY_STATUS = 2, currency_detail = '提領成功' where CURRENCY_ID = ?";
	private static final String CURRENCY_MEMLIST = "select * from currency where MEM_ID = ? order by CURRENCY_CHARGEDATE DESC";
	private static final String CURRENCY_ALLLIST = "select * from currency";
	private static final String CURRENCY_MEM_TOTAL="SELECT SUM(CURRENCY_BALANCE)AS CURRENCYTOTA FROM currency where MEM_ID=? and CURRENCY_STATUS != 3";
	private static final String CURRENCY_MEM_ALL_TOTAL="SELECT SUM(CURRENCY_BALANCE)AS CURRENCYTOTA FROM currency where MEM_ID=?";
	static Context ctx = null;
	static DataSource ds = null;
	
	static {	
			try {
				ctx = new javax.naming.InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");	
			} catch (NamingException e) {
				e.printStackTrace();
			}		
	}
	@Override
	public CurrencyVO insertCurrency(CurrencyVO currecyVO) {//儲值
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CURRENCY_INSERT);
			con.setAutoCommit(false);
			pstmt.setString(1, currecyVO.getMemId());
			pstmt.setInt(2, currecyVO.getCurrencyBalance());
			pstmt.setInt(3, 1);
			pstmt.setString(4, currecyVO.getCurrencyDetail());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
		return currecyVO;
	}

	@Override
	public void updateCurrecy(Connection con,String currencyId) {//提領成功更改狀態2提領   0 -消費 + 1 加值 2-提領成功 3審核提領
		PreparedStatement pstmt = null;
			try {

				pstmt = con.prepareStatement(CURRENCY_UPDATESTATUS);
				pstmt.setString(1, currencyId);
				int i = pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
	}

	@Override
	public List<CurrencyVO> curencyListMem(String memId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CurrencyVO currencyVO = null;
		List<CurrencyVO> curencyListMem = new ArrayList<>();
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(CURRENCY_MEMLIST);
				pstmt.setString(1, memId);
				rs = pstmt.executeQuery();
				while(rs.next()){
					currencyVO = new CurrencyVO();
					currencyVO.setCurrencyId(rs.getString("CURRENCY_ID"));
					currencyVO.setCurrencyBalance(rs.getInt("CURRENCY_BALANCE"));
					currencyVO.setCurrencyChangedate(rs.getTimestamp("currency_chargedate"));
					currencyVO.setCurrencyStatus(rs.getInt("currency_status"));
					currencyVO.setCurrencyDetail(rs.getString("currency_detail"));
					curencyListMem.add(currencyVO);
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
		return curencyListMem;
	}

	@Override
	public List<CurrencyVO> CurrecyList(CurrencyVO currecyVO) {//沒有
		// TODO Auto-generated method stub
		return null;
	}
	
	void a() {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public Integer memCurrecyTotal(String memId) { //目前可用餘額 測試ＯＫ
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer memCurrecyTotal = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CURRENCY_MEM_TOTAL);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			rs.next();
			memCurrecyTotal = rs.getInt("CURRENCYTOTA");
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
		return memCurrecyTotal;	

	}
	


	@Override
	public Integer memCurrecyALLTotal(String memId) { //測試ＯＫ
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer memCurrecyTotal = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CURRENCY_MEM_ALL_TOTAL);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			rs.next();
			memCurrecyTotal = rs.getInt("CURRENCYTOTA");				
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
		return memCurrecyTotal;	
	}
	


	@Override
	public void insertCurrency(Connection con, CurrencyVO currecyVO) { //買商品 買廣告
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(CURRENCY_INSERT);
			
			pstmt.setString(1, currecyVO.getMemId());
			pstmt.setInt(2,currecyVO.getCurrencyBalance());	
			pstmt.setInt(3,currecyVO.getCurrencyStatus());
			pstmt.setString(4,currecyVO.getCurrencyDetail());
			pstmt.executeUpdate();
		} catch (Exception e) {
			try {
				if(con!=null) {
					con.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		CurrencyJDBCDAO currencyJDBCDAO = new CurrencyJDBCDAO();
//		Integer ii = currencyJDBCDAO.memCurrecyTotal("M000001");
//		Integer i2 = currencyJDBCDAO.memCurrecyALLTotal("M000001");
//		System.out.println("自轉目前餘額（含以提領未審核）"+ii);
//		System.out.println("自轉實際可用餘額"+i2);
//	}

	@Override
	public void insertCurrency(CurrencyVO currecyVO, CurrencyCheackoutVO currencyCheackoutVO) { //提領
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			String next_currencyId = null;
			String cols[] = { "CURRENCY_ID" };
			pstmt = con.prepareStatement(CURRENCY_INSERT,cols);
			con.setAutoCommit(false);
			pstmt.setString(1, currecyVO.getMemId());
			pstmt.setInt(2, currecyVO.getCurrencyBalance());
			pstmt.setInt(3, 3);
			pstmt.setString(4, currecyVO.getCurrencyDetail());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();// 取得對應的自增主鍵值
			rs.next();
			next_currencyId = rs.getString(1);//把自增主鍵值存入next_orderid	
			System.out.println("自增主鍵值 = " + next_currencyId + "(新增提領取道到");
			CurrencyCheackoutDAO1 currencyCheackoutDAOJNDI  = new CurrencyCheackoutDAO1();
			currencyCheackoutVO.setMemId(currecyVO.getMemId());
			currencyCheackoutVO.setCheackoutBalance(currecyVO.getCurrencyBalance());
			currencyCheackoutVO.setCurrencyId(next_currencyId);
//			currencyCheackoutVO.setCheackoutStatus(0);//提領審核中
			currencyCheackoutDAOJNDI.insertCurrencyCheackout(con,currencyCheackoutVO);
			con.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

}


