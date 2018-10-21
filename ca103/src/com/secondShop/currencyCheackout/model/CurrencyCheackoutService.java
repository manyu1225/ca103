package com.secondShop.currencyCheackout.model;

import java.util.List;

public class CurrencyCheackoutService {
	private CurrencyCheackoutDAO_Interface dao;
	
	public CurrencyCheackoutService() {
		dao = new CurrencyCheackoutDAO();
	}
	
	public CurrencyCheackoutVO insertCurrencyCheackout(CurrencyCheackoutVO currencyCheackoutVO) {
		currencyCheackoutVO.setMemId(currencyCheackoutVO.getMemId());
		currencyCheackoutVO.setCheackoutBalance(currencyCheackoutVO.getCheackoutBalance());
		return currencyCheackoutVO;
	}
	public void updateCurrencyCheackout(String currencyId){
		dao.updateCurrencyCheackout(currencyId);
	}//提領成功 去自幣幣明細修改狀態
	public List<CurrencyCheackoutVO> currencyCheackoutList(Integer cheackoutStatus){
		return dao.currencyCheackoutList(cheackoutStatus);
		
	}//可以查看處理完的提領或申請中的提領
	public List<CurrencyCheackoutVO> currencyCheackoutList(){
		return dao.currencyCheackoutList();
		
	}
	

}
