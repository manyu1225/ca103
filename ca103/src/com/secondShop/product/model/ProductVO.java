package com.secondShop.product.model;

import java.sql.Timestamp;

public class ProductVO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private String productId;
	private String memIdSale;
	private String memIdBuy;
	private String productName;
	private String productType;
	private String productDetail;
	private Integer productPrice;
	private String productStatus;
	private Integer productScore;
	private String productRating;
	private Integer productBiddingPrice;
	private Timestamp productUpdateDate;
	private String productSaleType;
	private Timestamp productEndBidding;
	private Timestamp productAd;
	private Integer productDataStatus;
	private String deliveryAddress;
	private String deliveryPhone;
	private String deliveryName;
	private Integer productBiddingWinPrice;
	private byte[] bproductSnapshot;
	
	public ProductVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductVO(String productId, String memIdSale, String memIdBuy, String productName, String productType,
			String productDetail, Integer productPrice, String productStatus, Integer productScore,
			String productRating, Integer productBiddingPrice, Timestamp productUpdateDate, String productSaleType,
			Timestamp productEndBidding, Timestamp productAd, Integer productDataStatus, String deliveryAddress,
			String deliveryPhone, String deliveryName, Integer productBiddingWinPrice, byte[] bproductSnapshot) {
		super();
		this.productId = productId;
		this.memIdSale = memIdSale;
		this.memIdBuy = memIdBuy;
		this.productName = productName;
		this.productType = productType;
		this.productDetail = productDetail;
		this.productPrice = productPrice;
		this.productStatus = productStatus;
		this.productScore = productScore;
		this.productRating = productRating;
		this.productBiddingPrice = productBiddingPrice;
		this.productUpdateDate = productUpdateDate;
		this.productSaleType = productSaleType;
		this.productEndBidding = productEndBidding;
		this.productAd = productAd;
		this.productDataStatus = productDataStatus;
		this.deliveryAddress = deliveryAddress;
		this.deliveryPhone = deliveryPhone;
		this.deliveryName = deliveryName;
		this.productBiddingWinPrice = productBiddingWinPrice;
		this.bproductSnapshot = bproductSnapshot;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMemIdSale() {
		return memIdSale;
	}

	public void setMemIdSale(String memIdSale) {
		this.memIdSale = memIdSale;
	}

	public String getMemIdBuy() {
		return memIdBuy;
	}

	public void setMemIdBuy(String memIdBuy) {
		this.memIdBuy = memIdBuy;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

	public Integer getProductScore() {
		return productScore;
	}

	public void setProductScore(Integer productScore) {
		this.productScore = productScore;
	}

	public String getProductRating() {
		return productRating;
	}

	public void setProductRating(String productRating) {
		this.productRating = productRating;
	}

	public Integer getProductBiddingPrice() {
		return productBiddingPrice;
	}

	public void setProductBiddingPrice(Integer productBiddingPrice) {
		this.productBiddingPrice = productBiddingPrice;
	}

	public Timestamp getProductUpdateDate() {
		return productUpdateDate;
	}

	public void setProductUpdateDate(Timestamp productUpdateDate) {
		this.productUpdateDate = productUpdateDate;
	}

	public String getProductSaleType() {
		return productSaleType;
	}

	public void setProductSaleType(String productSaleType) {
		this.productSaleType = productSaleType;
	}

	public Timestamp getProductEndBidding() {
		return productEndBidding;
	}

	public void setProductEndBidding(Timestamp productEndBidding) {
		this.productEndBidding = productEndBidding;
	}

	public Timestamp getProductAd() {
		return productAd;
	}

	public void setProductAd(Timestamp productAd) {
		this.productAd = productAd;
	}

	public Integer getProductDataStatus() {
		return productDataStatus;
	}

	public void setProductDataStatus(Integer productDataStatus) {
		this.productDataStatus = productDataStatus;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getDeliveryPhone() {
		return deliveryPhone;
	}

	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public Integer getProductBiddingWinPrice() {
		return productBiddingWinPrice;
	}

	public void setProductBiddingWinPrice(Integer productBiddingWinPrice) {
		this.productBiddingWinPrice = productBiddingWinPrice;
	}

	public byte[] getBproductSnapshot() {
		return bproductSnapshot;
	}

	public void setBproductSnapshot(byte[] bproductSnapshot) {
		this.bproductSnapshot = bproductSnapshot;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ProductVO [productId=" + productId + ", memIdSale=" + memIdSale + ", memIdBuy=" + memIdBuy
				+ ", productName=" + productName + ", productType=" + productType + ", productDetail=" + productDetail
				+ ", productPrice=" + productPrice + ", productStatus=" + productStatus + ", productScore="
				+ productScore + ", productRating=" + productRating + ", productBiddingPrice=" + productBiddingPrice
				+ ", productUpdateDate=" + productUpdateDate + ", productSaleType=" + productSaleType
				+ ", productEndBidding=" + productEndBidding + ", productAd=" + productAd + ", productDataStatus="
				+ productDataStatus + ", deliveryAddress=" + deliveryAddress + ", deliveryPhone=" + deliveryPhone
				+ ", deliveryName=" + deliveryName + ", productBiddingWinPrice=" + productBiddingWinPrice + "]";
	}

	
		
	
}
