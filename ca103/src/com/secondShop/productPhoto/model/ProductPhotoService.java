package com.secondShop.productPhoto.model;

import java.util.List;


public class ProductPhotoService {
	private ProductPhotoDAO_interface dao;
	
	public ProductPhotoService(){
		dao = new ProductPhotoDAO();
	}
	
	public List<ProductPhotoVO> ProductPhotoList(String productId){	
		
		return dao.ProductPhotoList(productId);
	}
	
	public List<ProductPhotoVO> ProductPhotoforOne(String productId){	
		
		return dao.ProductPhotoforOne(productId);
	}
	
	public void deleteProductPhoto(String photoId) {
		dao.deleteProductPhoto(photoId);
	}
	
	public ProductPhotoVO findOneByPhotoId(String photoId) {
		return dao.findOneByPhotoId(photoId);
	}

}
