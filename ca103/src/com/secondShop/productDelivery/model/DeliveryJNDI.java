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

import com.mysql.fabric.xmlrpc.base.Array;

import sun.rmi.server.DeserializationChecker;


public class DeliveryJNDI implements DeliveryDAO_Interface{
	private static final String ADDRESS_INSERT = "insert into PRODUCT_Delivery values (('ADD'||lpad(to_char(DELIVERY_SEQ.nextval),3,'0')),?,?,?,?)";
	private static final String ADDRESS_UPDATE = "update PRODUCT_Delivery Delivery_name = ? Delivery_address =? Delivery_phone = ? where Delivery_ID = ?" ;
	private static final String ADDRESS_DATELEE = "Delete PRODUCT_Delivery where Delivery_ID = ? ";
	private static final String ADDRESS_ALL = "select * FROM PRODUCT_Delivery where MEM_ID = ?";
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
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DeliveryVO deliveryVO = null;
		List<DeliveryVO> deliveryList = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADDRESS_ALL);
			pstmt.setString(1, men_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				deliveryVO = new DeliveryVO();
				deliveryVO.setMemId(rs.getString("mem_id"));
				deliveryVO.setDeliveryId(rs.getString("delivery_Id"));
				deliveryVO.setDeliveryName(rs.getString("Delivery_name"));
				deliveryVO.setDeliveryAddress(rs.getString("Delivery_address"));
				deliveryVO.setDeliveryPhone(rs.getString("Delivery_phone"));
				deliveryList.add(deliveryVO);
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
	
		return deliveryList;
	}

	@Override
	public String deleteAddress(String deliveryId) {
		Connection con = null;
		PreparedStatement pstmt = null;	
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(ADDRESS_DATELEE);
					pstmt.setString(1, deliveryId);
					pstmt.executeUpdate();
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
