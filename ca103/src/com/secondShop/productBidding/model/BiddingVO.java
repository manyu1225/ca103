package com.secondShop.productBidding.model;


import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import com.secondShop.product.model.ProductVO;

public class BiddingVO implements java.io.Serializable{
	private String biddingId;
	private String productId;
	private String memId;
	private Integer biddingPrice;
	private Timestamp biddingDate;
	private ProductVO productVO ;
	


	public ProductVO getProductVO() {
		return productVO;
	}
	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}
	public BiddingVO(String biddingId, String productId, String memId, Integer biddingPrice, Timestamp biddingDate,
			ProductVO productVO) {
		super();
		this.biddingId = biddingId;
		this.productId = productId;
		this.memId = memId;
		this.biddingPrice = biddingPrice;
		this.biddingDate = biddingDate;
		this.productVO = productVO;
	}
	public BiddingVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBiddingId() {
		return biddingId;
	}
	public void setBiddingId(String biddingId) {
		this.biddingId = biddingId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public Integer getBiddingPrice() {
		return biddingPrice;
	}
	public void setBiddingPrice(Integer biddingPrice) {
		this.biddingPrice = biddingPrice;
	}
	public Timestamp getBiddingDate() {
		return biddingDate;
	}
	public void setBiddingDate(Timestamp biddingDate) {
		this.biddingDate = biddingDate;
	}
	@Override
	public String toString() {
		return "BiddingVO [biddingId=" + biddingId + ", productId=" + productId + ", memId=" + memId + ", biddingPrice="
				+ biddingPrice + ", biddingDate=" + biddingDate + "]";
	}
	
	

}
