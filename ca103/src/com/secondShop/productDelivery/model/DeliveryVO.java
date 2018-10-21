package com.secondShop.productDelivery.model;

public class DeliveryVO {
	private String deliveryId;
	private String memId;
	private String deliveryName;
	private String deliveryAddress;
	private String deliveryPhone;
	public DeliveryVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeliveryVO(String deliveryId, String memId, String deliveryName, String deliveryAddress,
			String deliveryPhone) {
		super();
		this.deliveryId = deliveryId;
		this.memId = memId;
		this.deliveryName = deliveryName;
		this.deliveryAddress = deliveryAddress;
		this.deliveryPhone = deliveryPhone;
	}
	public String getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
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
	
	
	

}
