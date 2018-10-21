package com.secondShop.productBidding.model;

import java.util.List;

public interface BiddingDAO_Interface {
	
	public BiddingVO insertBiddingPrice(BiddingVO biddingVO); //新增競標出價
	
	public List<BiddingVO> productBiddinglist(String productId); //某產品的競標紀錄
	
	public List<BiddingVO> allBiddingprice(String memId); //某會員參與過的競標
	
	public BiddingVO biddingBenefitMem (String productId) ;
}
