package com.secondShop.productFavorite.model;

import java.util.List;

public class ProductFavoriteService {
	private ProductFavoriteDAO_interface dao;
	
	public ProductFavoriteService(){
		dao = new ProductFavoriteDAO();
	}
	public void insertFavorite(String mem_id, String productId) {
		ProductFavoriteVO productFavoriteVO= new ProductFavoriteVO();
		productFavoriteVO.setMemId(mem_id);
		productFavoriteVO.setProductId(productId);
		dao.insertFavorite(productFavoriteVO);
	}//新增一筆收藏
	
	public List<ProductFavoriteVO> allFavorite(String men_id){
		return dao.allFavorite(men_id);
		
	} //某會員的收藏
	
	public void deleteFavorite(String mem_id, String productId) {
		ProductFavoriteVO productFavoriteVO= new ProductFavoriteVO();
		productFavoriteVO.setMemId(mem_id);
		productFavoriteVO.setProductId(productId);
		
		dao.deleteFavorite(productFavoriteVO);
	}
	public ProductFavoriteVO findOneByPK(String men_id, String productId) {
		return dao.findOneByPK(men_id, productId);
	}
}
