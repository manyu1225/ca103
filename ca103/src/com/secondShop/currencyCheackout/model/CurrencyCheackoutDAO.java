package com.secondShop.currencyCheackout.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.secondShop.currency.model.CurrencyDAO;

import hibernate.util.HibernateUtil;

public class CurrencyCheackoutDAO implements CurrencyCheackoutDAO_Interface{

	private static final String GET_ALL = 
			"from CurrencyCheackoutVO order by cheackoutId";
	private static final String GET_ALL_BY_STATUS = 
			"from CurrencyCheackoutVO where cheackoutStatus=? order by cheackoutId";
	
	private static final String CURRENCYCHEACKOU = 
	"insert into currency_cheackout (MEM_ID,CHEACKOUT_ID,CHEACKOUT_BALANCE,CHEACKOUT_DATE,CHEACKOUT_STATUS,currency_id)" 
           + "VALUES (?,'CHK'||lpad(to_char(cheackout_seq.nextval),3,'0'),?,current_TIMESTAMP,0,?)";
	private static final String UPDATE_CURRENCYCHEACKOU = "update currency_cheackout set CHEACKOUT_STATUS =1 where currency_id = ?";
	
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
	public void insertCurrencyCheackout(CurrencyCheackoutVO currencyCheackoutVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.saveOrUpdate(currencyCheackoutVO);
			session.getTransaction().commit();
		}catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}

	}

	@Override
	public void updateCurrencyCheackout(String currencyId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		CurrencyDAO currencyDAO = new CurrencyDAO();
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_CURRENCYCHEACKOU);
				con.setAutoCommit(false);
				pstmt.setString(1,currencyId);
				pstmt.executeUpdate();
				currencyDAO.updateCurrecy(con,currencyId);
				System.out.println("提領成功");
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
	public List<CurrencyCheackoutVO> currencyCheackoutList(Integer cheackoutStatus) {
		List<CurrencyCheackoutVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<CurrencyCheackoutVO> query = session.createQuery("from CurrencyCheackoutVO where cheackoutStatus=?0 order by cheackoutId",CurrencyCheackoutVO.class);
			query.setParameter(0, cheackoutStatus);
			list = query.getResultList();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			System.out.print(ex);
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}

	@Override
	public List<CurrencyCheackoutVO> currencyCheackoutList() {
		List<CurrencyCheackoutVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<CurrencyCheackoutVO> query = session.createQuery(GET_ALL, CurrencyCheackoutVO.class);
			list = query.getResultList();
			session.getTransaction().commit();
			System.out.println("116 currencyCheackoutList : ");
		} catch (RuntimeException ex) {
			throw ex;
		}
		return list;
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
