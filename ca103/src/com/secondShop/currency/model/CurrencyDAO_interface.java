package com.secondShop.currency.model;

import java.sql.Connection;
import java.util.List;

import com.secondShop.currencyCheackout.model.CurrencyCheackoutVO;


public interface CurrencyDAO_interface {
	
	public CurrencyVO insertCurrency(CurrencyVO currecyVO); //儲值
	public void insertCurrency(CurrencyVO currecyVO ,CurrencyCheackoutVO currencyCheackoutVO); //提領新增到明細
	
	public void insertCurrency(Connection con ,CurrencyVO currecyVO);//新增商品時同時購買廣告
	
	public void updateCurrecy(Connection con,String currencyId); //找到自轉編號修改目前狀態 提領成功
	public List<CurrencyVO> curencyListMem (String memId);//查看某會員自轉記錄

	public List<CurrencyVO> CurrecyList(CurrencyVO currecyVO); //後台可以查看自轉全部詳細
	public Integer memCurrecyTotal (String memId);//目前可用餘額 排除
	public Integer memCurrecyALLTotal (String memId);//目前餘額
	

}
