package com.secondShop.productPhoto.model;

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

import com.secondShop.product.model.ProductVO;



public class ProductPhotoDAO implements ProductPhotoDAO_interface{
	
	private static final String INSERT_PHOTO = 
			"insert into product_photo(PHOTO_ID,PRODUCT_ID,PRODUCT_PHOTO)values('PIC'||lpad(to_char(PHOTO_SEQ.nextval),3,'0'),?,?)";
	private static final String UPDATE_PHOTO = "update product_photo set PRODUCT_NAME =?";
	private static final String DATELEE_PHOTO = "Delete product_photo where PHOTO_ID = ? "; 
	private static final String PRODUCT_PHOTO_LIST = 
			"SELECT * FROM product_photo WHERE product_id = ?";
	private static final String PRODUCT_PHOTO_FOR_ONE = 
			"SELECT * FROM (SELECT * FROM product_photo WHERE PRODUCT_ID=? ORDER BY photo_id DESC)WHERE ROWNUM = 1";
	private static final String FIND_ONE_BY_PHOTOID = 
			"SELECT * FROM product_photo WHERE photo_id = ?";
//	private static final String PRODUCT_PHOTO_FOR_ONE = 
//			"SELECT * FROM product_photo WHERE PRODUCT_ID='?' ORDER BY photo_id DESC";
	

//	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
//	private static final String USER = "CA103G2";
//	private static final String PASSWORD = "CA103G2";
//	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";


	static Context ctx = null;
	static DataSource ds = null;
	
	static {	
		try {
			ctx = new javax.naming.InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/CA103G2");	
		} catch (NamingException e) {
			e.printStackTrace();
		}		
	}
	
	
	@Override
	public List<ProductPhotoVO> ProductPhotoList(String productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductPhotoVO productPhotoVO = null;
		List<ProductPhotoVO> ProductPhotoList = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(PRODUCT_PHOTO_LIST);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				productPhotoVO = new ProductPhotoVO();
				productPhotoVO.setProductPhoto(rs.getBytes("product_photo"));
				productPhotoVO.setPhotoId(rs.getString("photo_id"));
				ProductPhotoList.add(productPhotoVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ProductPhotoList;
	}


	@Override
	public void deleteProductPhoto(String photoId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DATELEE_PHOTO);
			pstmt.setString(1, photoId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void insertProducPhoto(Connection con, ProductPhotoVO productPhotoVO) {
		PreparedStatement pstmt = null;	
			try {
		
				pstmt = con.prepareStatement(INSERT_PHOTO);
//				pstmt.setString(1, productPhotoVO);
				pstmt.setString(1, productPhotoVO.getProductId());
				pstmt.setBytes(2, productPhotoVO.getProductPhoto());
				pstmt.executeUpdate();
//				con.commit();
				System.out.println("新增圖片成功!");
				
			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally {
				
			}		
	}

	@Override
	public List<ProductPhotoVO> ProductPhotoforOne(String productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductPhotoVO productPhotoVO = null;
		List<ProductPhotoVO> productPhotolist = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(PRODUCT_PHOTO_FOR_ONE);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				productPhotoVO = new ProductPhotoVO();
				productPhotoVO.setPhotoId(rs.getString("PHOTO_ID"));
				productPhotoVO.setProductId(rs.getString("PRODUCT_ID"));
				productPhotoVO.setProductPhoto(rs.getBytes("product_photo"));
				productPhotolist.add(productPhotoVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return productPhotolist;
	}


	@Override
	public ProductPhotoVO findOneByPhotoId(String photoId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductPhotoVO productPhotoVO = new ProductPhotoVO();
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_ONE_BY_PHOTOID);
			pstmt.setString(1, photoId);
			rs = pstmt.executeQuery();
			
			rs.next();
			productPhotoVO.setPhotoId(rs.getString("PHOTO_ID"));
			productPhotoVO.setProductId(rs.getString("PRODUCT_ID"));
			productPhotoVO.setProductPhoto(rs.getBytes("product_photo"));
	

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return productPhotoVO;
	}

	




}
