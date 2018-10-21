package com.secondShop.currency.model;

import java.util.List;

import com.secondShop.currencyCheackout.model.CurrencyCheackoutVO;

public class CurrencyService {
	private CurrencyDAO_interface dao;
	
	public CurrencyService() {
		dao = new CurrencyDAO();
	}
	
	public CurrencyVO insertCurrency(CurrencyVO currecyVO) {
		
		return dao.insertCurrency(currecyVO)	;
	}
//	String memId ,Integer currencyBalance,String currencyDetail,
	public Integer memCurrecyALLTotal(String memId) {//目前持有
		return dao.memCurrecyALLTotal(memId);
		
	}
	public Integer memCurrecyTotal(String memId) {//目前可用餘額
		return dao.memCurrecyTotal(memId);
		
	}
	public List<CurrencyVO> curencyListMem(String memId){
		return dao.curencyListMem(memId);
		
	}
	public void insertCurrency(CurrencyVO currecyVO, CurrencyCheackoutVO currencyCheackoutVO) { 
		dao.insertCurrency(currecyVO, currencyCheackoutVO);
	}
}
