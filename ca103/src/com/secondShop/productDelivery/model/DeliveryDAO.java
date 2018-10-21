package com.secondShop.productDelivery.model;

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

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mysql.fabric.xmlrpc.base.Array;

import hibernate.util.HibernateUtil;
import sun.rmi.server.DeserializationChecker;


public class DeliveryDAO implements DeliveryDAO_Interface{
	private static final String ADDRESS_UPDATE = "update PRODUCT_Delivery Delivery_name = ? Delivery_address =? Delivery_phone = ? where Delivery_ID = ?" ;
	private static final String ADDRESS_INSERT = "insert into PRODUCT_Delivery values (('ADD'||lpad(to_char(DELIVERY_SEQ.nextval),3,'0')),?,?,?,?)";

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
	public String insertAddress(DeliveryVO deliveryVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
		String next_DeliveryId = null;
			try {
				con = ds.getConnection();
				String key[] = { "delivery_Id" };		
				pstmt = con.prepareStatement(ADDRESS_INSERT,key);
				System.out.println(deliveryVO.getMemId());
				System.out.println(deliveryVO.getDeliveryName());
				System.out.println(deliveryVO);
				System.out.println(deliveryVO.getDeliveryPhone());
				pstmt.setString(1,deliveryVO.getMemId());
				pstmt.setString(2,deliveryVO.getDeliveryName());
				pstmt.setString(3,deliveryVO.getDeliveryAddress());
				pstmt.setString(4, deliveryVO.getDeliveryPhone());
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();// 取得對應的自增主鍵值
				rs.next();
				next_DeliveryId = rs.getString(1);//把自增主鍵值存入next_orderid
			} catch (Exception e) {
				System.out.println("新增地址錯誤");
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
			return next_DeliveryId;
	}

	@Override
	public List<DeliveryVO> allAddress(String men_id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List<DeliveryVO> deliveryList = new ArrayList<>();
		try{
			session.beginTransaction();
			Query<DeliveryVO> query = session.createQuery("from DeliveryVO where memId=?0",DeliveryVO.class);
			query.setParameter(0, men_id);
			deliveryList = query.getResultList();
			session.getTransaction().commit();
			
		}catch(RuntimeException ex) {
			session.getTransaction().rollback();
		}
	
		return deliveryList;
	}

	@Override
	public String deleteAddress(String deliveryId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			DeliveryVO deliveryVO = session.get(DeliveryVO.class, deliveryId);
			session.delete(deliveryVO);
			session.getTransaction().commit();	
		}catch(RuntimeException ex) {
			session.getTransaction().rollback();
		}
				return deliveryId;
			
					
	}

	@Override
	public void updateAddress(DeliveryVO deliveryVO) {
		Connection con = null;
		PreparedStatement pstmt = null;	
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(ADDRESS_UPDATE);
				pstmt.setString(1,deliveryVO.getMemId());
				pstmt.setString(2,deliveryVO.getDeliveryName());
				pstmt.setString(3,deliveryVO.getDeliveryAddress());
				pstmt.setString(4,deliveryVO.getDeliveryId());
				pstmt.executeUpdate();
			} catch (Exception e) {
				System.out.println("更新地址錯誤");
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
