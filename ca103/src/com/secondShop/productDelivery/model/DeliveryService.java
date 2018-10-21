package com.secondShop.productDelivery.model;

import java.util.List;

public class DeliveryService {
	private DeliveryDAO_Interface dao;
	
	public DeliveryService() {
		dao = new DeliveryDAO();
	}

	public List<DeliveryVO> allAddress(String men_id){
		return dao.allAddress(men_id);	
	}
	public String insertAddress(DeliveryVO deliveryVO) {
		return dao.insertAddress(deliveryVO);
		
	}
	
	public String deleteAddress(String deliveryId) {
		return dao.deleteAddress(deliveryId);
		
	}
}
