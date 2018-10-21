package com.secondShop.productDelivery.model;

import java.util.List;

public interface DeliveryDAO_Interface {
	public String insertAddress(DeliveryVO deliveryVO); //新增地址
	
	public List<DeliveryVO> allAddress(String men_id); //某會員全部地址
	
	public String deleteAddress(String deliveryId);
	
	public void updateAddress(DeliveryVO deliveryVO);//修改地址

}
