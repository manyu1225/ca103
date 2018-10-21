package com.secondShop.product.browserRec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.secondShop.product.model.ProductService;
import com.secondShop.product.model.ProductVO;

import redis.clients.jedis.Jedis;

public class BrowserRec {
	static Jedis jedis = null;
	
	static {
		jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		System.out.println(jedis.ping());
	}
	
	public List<String> insterBrowserRec(String memid ,String porductId) {
		List<String> productIdList = jedis.lrange(memid, 0, 9);
		if(productIdList.size()==0) {
			jedis.lpush(memid, porductId);
		}else {
			int i = 1;
			Boolean checkHaveRec = true;
			
			for(String st : productIdList) { // 1,2,3
				if(st.equals(porductId)) {
					checkHaveRec = false;
				}
			}
			
			if(checkHaveRec) {
				jedis.lpush(memid, porductId);
			}
		}
		
		
//		jedis.rpush(memid, porductId);  //list
//	    List<String> list = jedis.lrange(memid, 0, jedis.llen(memid) - 1);  //list
//		jedis.sadd(memid, porductId);  // set
//		Set<String> smembers = jedis.smembers(memid);
//		System.out.println("25-smembers.size()"+ smembers.size());
//	    for (String set :smembers)
//	    	System.out.println("我進來resa"+set);
	    jedis.close();
	    	
		return null;
	}
	
	public void delBrowserRec(String memid) {
	    jedis.del(memid);
	    jedis.close();
	}
	
	public List<ProductVO> browserRecList(String memid){
		List<ProductVO> productListForBrowser = new ArrayList<>();
	    ProductService productService = new ProductService();
	    ProductVO productVO =null;

		List<String> productIdList = jedis.lrange(memid, 0, 9);
		for(String productKey :productIdList) {
	    	productVO = new ProductVO();
	    	productVO = productService.findProductByPK(productKey);
			productListForBrowser.add(productVO);
		}
		return productListForBrowser;
	}
	
	
	public static void main(String[]args) {
//		jedis.rpush("key1", "a12");
//		jedis.rpush("key1", "a11");
//		jedis.rpush("key1", "a10");
//		jedis.rpush("key1", "a9");
//		jedis.rpush("key1", "a8");
//		jedis.rpush("key1", "a7");
//		jedis.rpush("key1", "a6");
//		jedis.rpush("key1", "aa5");
//		jedis.rpush("key1", "a4");
//		jedis.rpush("key1", "a3");
//		jedis.rpush("key1", "a2");
//		jedis.rpush("key1", "a1");		
//		List<String> productIdList = jedis.lrange("key1", 0, 9);
//		System.out.println(productIdList.size());
//		for(String st : productIdList) {
//			System.out.println(st);
//		}

		List<String> productIdList = jedis.lrange("M000903", 0, 9);
		System.out.println("size:"+productIdList.size());
//		for(String productKey :productIdList) {
//			System.out.println(productKey);
//		}
//		jedis.del("M000903");
	}
	
	
}
