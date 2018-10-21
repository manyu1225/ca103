package com.secondShop.productReport.model;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.SQLException;

import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.secondShop.product.model.ProductService;

import hibernate.util.HibernateUtil;

public class ProductReportJNDI implements ProductReportDAO_Interface{
	
	private static final String INSERT_REPORT = 
			"insert into product_report values('REP'||lpad(to_char(product_seq.nextval),3,'0'),?,?,current_TIMESTAMP,?,?)";
	private static final String ALL_REPORT = "Select * from product_report";

	private static final String UPDATE_STATUS = "update product_report set REPORT_STATUS = ? where REPORT_ID = ?";
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
	public void insertProductReport(ProductReportVO productReportVO) {

		Connection con = null;
		PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_REPORT);
				pstmt.setString(1, productReportVO.getMemId());
				pstmt.setString(2, productReportVO.getProductId());
				pstmt.setInt(3,0);
				pstmt.setString(4, productReportVO.getReportDetailed());
				pstmt.executeUpdate();
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
	}
	
	@Override
	public List<ProductReportVO> allProductReport( ) {
		List<ProductReportVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<ProductReportVO> query = session.createQuery("from ProductReportVO ORDER BY reportId DES",ProductReportVO.class);
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
	public List<ProductReportVO> statusProductReport(Integer reportStatus) {
		List<ProductReportVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<ProductReportVO> query = session.createQuery("from ProductReportVO where reportStatus=?0 order by reportId",ProductReportVO.class);
			query.setParameter(0, reportStatus);
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
	public void updateProductReport(ProductReportVO productReportVO) { //審核成功 狀1 去該販售商品會員的表格增加被檢舉次數1 檢舉成功商品改成狀態5
		Connection con = null;
		PreparedStatement pstmt = null;
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(UPDATE_STATUS);
					con.setAutoCommit(false);
					pstmt.setInt(1, productReportVO.getReportStatus());
					pstmt.setString(2, productReportVO.getReportId());
					pstmt.executeUpdate();
					System.out.println("我來檢局DAO");
					ProductService ProductService = new ProductService();
					if(productReportVO.getReportStatus()==1) {
						ProductService.updateProductDataStatus(5, productReportVO.getProductId());
					}				
					con.commit();
				} catch (SQLException e) {
					if(con!=null) {
						try {
							con.rollback();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
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




}
