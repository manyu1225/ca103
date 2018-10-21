package com.secondShop.productPhoto.model;

import java.sql.Connection;
import java.util.List;

public interface ProductPhotoDAO_interface {
	public void deleteProductPhoto(String photoId);//刪除某圖片 透過商品編號 
	public List<ProductPhotoVO> ProductPhotoList(String productId);//顯示某商品的圖片 透過商品編號 
	
//	public byte[] ProductPhotoforOne(String productId);//找一張
	public List<ProductPhotoVO> ProductPhotoforOne(String productId);//找一張
	
	public void insertProducPhoto(Connection con, ProductPhotoVO productPhotoVO);//新增圖片
	
	public ProductPhotoVO findOneByPhotoId(String photoId);
}
