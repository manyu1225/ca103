package com.secondShop.productFavorite.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.query.Query;


import hibernate.util.HibernateUtil;

public class ProductFavoriteDAO2  implements ProductFavoriteDAO_interface{
	private static final String FAVORITE_INSERT = "insert into product_favorite values (?,?)";
	private static final String FAVORITE_DATELEE = "Delete product_favorite where MEM_ID =? and PRODUCT_ID = ? ";
	private static final String FAVORITE_ALL = "Select * from product_favorite where MEM_ID = ?";
	private static final String FAVORITE_ONE = "Select * from product_favorite where MEM_ID =? and PRODUCT_ID = ?";
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
	public void insertFavorite(ProductFavoriteVO productFavoriteVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FAVORITE_INSERT);
			pstmt.setString(1, productFavoriteVO.getMemId());
			pstmt.setString(2, productFavoriteVO.getProductId());
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
	public List<ProductFavoriteVO> allFavorite(String memId) {
		List<ProductFavoriteVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<ProductFavoriteVO> query = session.createQuery("from ProductFavoriteVO where memId=?0 ",ProductFavoriteVO.class);
			query.setParameter(0, memId);
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
	public void deleteFavorite(ProductFavoriteVO productFavoriteVO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			session.get(ProductFavoriteVO.class, productFavoriteVO);
			session.delete(productFavoriteVO);
			session.getTransaction().commit();	
		}catch(RuntimeException ex) {
			session.getTransaction().rollback();
		}
				
	}

	@Override
	public ProductFavoriteVO findOneByPK(String memId, String productId) {
		ProductFavoriteVO productFavoriteVO = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<ProductFavoriteVO> query = session.createQuery("from ProductFavoriteVO where memId=?0 and productId=?1",ProductFavoriteVO.class);
			query.setParameter(0, memId);
			query.setParameter(1, productId);
			productFavoriteVO = query.getSingleResult();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			System.out.print(ex);
			session.getTransaction().rollback();
			throw ex;
		}
	
		return productFavoriteVO;
	}

	
}
