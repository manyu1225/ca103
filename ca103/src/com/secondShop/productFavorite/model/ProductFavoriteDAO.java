package com.secondShop.productFavorite.model;

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

import com.secondShop.productPhoto.model.ProductPhotoVO;

public class ProductFavoriteDAO  implements ProductFavoriteDAO_interface{
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
	public List<ProductFavoriteVO> allFavorite(String men_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductFavoriteVO productFavoriteVO = null;
		List<ProductFavoriteVO> productFavoriteVOList = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FAVORITE_ALL);
			pstmt.setString(1, men_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
			productFavoriteVO = new ProductFavoriteVO();
			productFavoriteVO.setMemId(rs.getString("MEM_ID"));
			productFavoriteVO.setProductId(rs.getString("PRODUCT_ID"));
			productFavoriteVOList.add(productFavoriteVO);
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

		
		return productFavoriteVOList;
	}

	@Override
	public void deleteFavorite(ProductFavoriteVO productFavoriteVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(FAVORITE_DATELEE);
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
	public ProductFavoriteVO findOneByPK(String men_id, String productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductFavoriteVO productFavoriteVO = null;			
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FAVORITE_ONE);
			pstmt.setString(1, men_id);
			pstmt.setString(2, productId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				productFavoriteVO = new ProductFavoriteVO();
				productFavoriteVO.setMemId(rs.getString(1));
				productFavoriteVO.setProductId(rs.getString(2));
			}	
		} catch (SQLException e) {
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
		
		
		
		return productFavoriteVO;
	}

	
}
