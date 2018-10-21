package com.secondShop.productFavorite.model;

import java.util.List;

public interface ProductFavoriteDAO_interface {
	
	public List<ProductFavoriteVO> allFavorite(String memId); //某會員的收藏
	public void insertFavorite(ProductFavoriteVO productFavoriteVO); //新增一筆收藏
	public void deleteFavorite(ProductFavoriteVO productFavoriteVO);
	public ProductFavoriteVO findOneByPK(String memId ,String productId);

}
