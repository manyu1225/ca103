package com.util.product;

import java.util.Date;
import java.util.TimerTask;

import com.secondShop.product.model.ProductService;

public class FinishTimeAD  extends TimerTask{
	String productId;
	public FinishTimeAD() {
		super();
	}
	public FinishTimeAD(String productId) {
		super();
		this.productId = productId;
		System.out.println("我要賣廣告");
	}
	@Override
	public void run() {
		new ProductService().updateProductAd(null, productId);
		System.out.println("我來下架廣告囉");
		
	}
}
