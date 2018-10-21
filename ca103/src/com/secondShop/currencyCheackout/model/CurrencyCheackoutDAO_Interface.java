package com.secondShop.currencyCheackout.model;

import java.sql.Connection;
import java.util.List;

import com.secondShop.currency.model.CurrencyVO;

public interface CurrencyCheackoutDAO_Interface {
	public void insertCurrencyCheackout(CurrencyCheackoutVO currencyCheackoutVO);
	public void updateCurrencyCheackout(String currencyId);//提領成功 去自幣幣明細修改狀態
	public List<CurrencyCheackoutVO> currencyCheackoutList(Integer cheackoutStatus);//可以查看處理完的提領或申請中的提領
	public List<CurrencyCheackoutVO> currencyCheackoutList();
	public void insertCurrencyCheackout(Connection con,CurrencyCheackoutVO currencyCheackoutVO);//新一筆提領
}