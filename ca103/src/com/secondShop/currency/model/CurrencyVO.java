package com.secondShop.currency.model;

import java.sql.Timestamp;

public class CurrencyVO implements java.io.Serializable{
	private String memId;
	private String currencyId;
	private Integer currencyBalance;
	private Timestamp currencyChangedate;
	private Integer currencyStatus;
	private String currencyDetail;
	
	
	public CurrencyVO() {
		super();
	}
	public CurrencyVO(String memId, String currencyId, Integer currencyBalance, Timestamp currencyChangedate,
			Integer currencyStatus, String currencyDetail) {
		super();
		this.memId = memId;
		this.currencyId = currencyId;
		this.currencyBalance = currencyBalance;
		this.currencyChangedate = currencyChangedate;
		this.currencyStatus = currencyStatus;
		this.currencyDetail = currencyDetail;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public Integer getCurrencyBalance() {
		return currencyBalance;
	}
	public void setCurrencyBalance(Integer currencyBalance) {
		this.currencyBalance = currencyBalance;
	}
	public Timestamp getCurrencyChangedate() {
		return currencyChangedate;
	}
	public void setCurrencyChangedate(Timestamp currencyChangedate) {
		this.currencyChangedate = currencyChangedate;
	}
	public Integer getCurrencyStatus() {
		return currencyStatus;
	}
	public void setCurrencyStatus(Integer currencyStatus) {
		this.currencyStatus = currencyStatus;
	}
	public String getCurrencyDetail() {
		return currencyDetail;
	}
	public void setCurrencyDetail(String currencyDetail) {
		this.currencyDetail = currencyDetail;
	}
	
	


}
