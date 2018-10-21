package com.secondShop.productBidding.model;

import java.util.List;

public class BiddingService {
	private BiddingDAO_Interface dao;
	
	public BiddingService() {
		dao = new BiddingDAO();
	}
	
	public BiddingVO biddingBenefitMem (String productId) {
		return dao.biddingBenefitMem(productId);
	}
	
	public BiddingVO insertBiddingPrice(BiddingVO biddingVO) {	
		return dao.insertBiddingPrice(biddingVO);
	} //新增競標出價
	
	public List<BiddingVO> productBiddinglist(String productId){
		
		return dao.productBiddinglist(productId);
		
	} //某產品的競標紀錄
	
	public List<BiddingVO> allBiddingprice(String memId){	
		return dao.allBiddingprice(memId);
		
	}//某會員參與過的競標

}
