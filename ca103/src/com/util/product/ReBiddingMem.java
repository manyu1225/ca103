package com.util.product;

import java.util.TimerTask;

import com.secondShop.product.model.ProductService;

public class ReBiddingMem extends TimerTask{
	String productId;
	public ReBiddingMem() {
		super();
	}

	public ReBiddingMem(String productId) {
		super();
		this.productId = productId;
	}
	
	@Override
	public void run() {
		new ProductService().removeBIddingMem(productId);
		System.out.println("我來移除棄標買家");
		//我來移除棄標買家
	}
	
	

}
