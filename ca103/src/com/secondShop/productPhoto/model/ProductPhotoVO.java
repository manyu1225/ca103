package com.secondShop.productPhoto.model;



public class ProductPhotoVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String photoId;
	private String productId;
	private byte[] productPhoto;
	
	
	
	public ProductPhotoVO() {
		super();
	}
	
	public ProductPhotoVO(String photoId, String productId, byte[] productPhoto) {
		super();
		this.photoId = photoId;
		this.productId = productId;
		this.productPhoto = productPhoto;
	}
	
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public byte[] getProductPhoto() {
		return productPhoto;
	}
	public void setProductPhoto(byte[] productPhoto) {
		this.productPhoto = productPhoto;
	}

	@Override
	public String toString() {
		return "ProductPhotoVO [photoId=" + photoId + ", productId=" + productId +  "]";
	}
	
	
}
