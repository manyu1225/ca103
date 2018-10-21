package com.secondShop.currencyCheackout.model;


import java.sql.Timestamp;

public class CurrencyCheackoutVO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memId;
	private String currencyId;
	private String cheackoutId;
	private Integer cheackoutBalance;
	private Timestamp cheackoutDate;
	private Integer cheackoutStatus;
	public CurrencyCheackoutVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CurrencyCheackoutVO(String memId, String currencyId, String cheackoutId, Integer cheackoutBalance,
			Timestamp cheackoutDate, Integer cheackoutStatus) {
		super();
		this.memId = memId;
		this.currencyId = currencyId;
		this.cheackoutId = cheackoutId;
		this.cheackoutBalance = cheackoutBalance;
		this.cheackoutDate = cheackoutDate;
		this.cheackoutStatus = cheackoutStatus;
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
	public String getCheackoutId() {
		return cheackoutId;
	}
	public void setCheackoutId(String cheackoutId) {
		this.cheackoutId = cheackoutId;
	}
	public Integer getCheackoutBalance() {
		return cheackoutBalance;
	}
	public void setCheackoutBalance(Integer cheackoutBalance) {
		this.cheackoutBalance = cheackoutBalance;
	}
	public Timestamp getCheackoutDate() {
		return cheackoutDate;
	}
	public void setCheackoutDate(Timestamp cheackoutDate) {
		this.cheackoutDate = cheackoutDate;
	}
	public Integer getCheackoutStatus() {
		return cheackoutStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setCheackoutStatus(Integer cheackoutStatus) {
		this.cheackoutStatus = cheackoutStatus;
	}
	@Override
	public String toString() {
		return "CurrencyCheackoutVO [memId=" + memId + ", currencyId=" + currencyId + ", cheackoutId=" + cheackoutId
				+ ", cheackoutBalance=" + cheackoutBalance + ", cheackoutDate=" + cheackoutDate + ", cheackoutStatus="
				+ cheackoutStatus + "]";
	}
	
	
	
}
