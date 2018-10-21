package com.secondShop.productFavorite.model;

import com.secondShop.product.model.ProductVO;

public class ProductFavoriteVO implements java.io.Serializable{
	private String memId;
	private String productId;
	private ProductVO productVO ;

	
	
	public ProductVO getProductVO() {
		return productVO;
	}

	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}

	
	public ProductFavoriteVO(String memId, String productId, ProductVO productVO) {
		super();
		this.memId = memId;
		this.productId = productId;
		this.productVO = productVO;
	}

	public ProductFavoriteVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	


}
