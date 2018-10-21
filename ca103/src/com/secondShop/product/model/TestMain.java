package com.secondShop.product.model;

import java.util.List;

import com.secondShop.productBidding.model.BiddingDAOJNDI;
import com.secondShop.productBidding.model.BiddingVO;

public class TestMain {
	public static void main(String []args) {
		
		BiddingDAOJNDI biddingDAO = new BiddingDAOJNDI();
		BiddingVO biddingVO =new BiddingVO();
		biddingVO = biddingDAO.biddingBenefitMem("PRD005");
		System.out.println(biddingVO.toString());
		System.out.println(biddingVO.getBiddingId());
		System.out.println(biddingVO.getBiddingDate());
		System.out.println(biddingVO.toString());
		//新增商品
//		ProductDAO_JDBC productDAO = new ProductDAO_JDBC();	
//		ProductVO productVO = new ProductVO();
//		
//		List<ProductVO> findSalesListByMemId = productDAO.findSalesListByMemId("M000001", 1);
//		System.out.println(findSalesListByMemId.size());
//		productVO.setMemIdSale("M000001");
//		productVO.setProductName("FUSIN-F101 新騎生活");
//		productVO.setProductType("單車");
//		productVO.setProductDetail("高質感平價都市穿梭小折\n" + "汽車級亮粉烤漆\n" + "高級跑車型舒適座墊");
//		productVO.setProductPrice(2099);
//		productVO.setProductStatus("全新");
//		productVO.setProductBiddingPrice(0);
//		productVO.setProductSaleType("直購");
//		productVO.setProductEndBidding(null);
//		productVO.setProductAd(null);
//		productVO.setProductDataStatus(1);	
//		productDAO.insertProduct(productVO);
//		System.out.println("GOOD");
//		//更新商品
//		productVO.setProductName("FUSIN-F101");
//		productVO.setProductDetail("高質感平價都市穿梭小折");
//		productVO.setProductPrice(8000);
//		productVO.setProductStatus("全新");
//		productVO.setProductBiddingPrice(0);
//		productVO.setProductType("配件");
//		productVO.setProductEndBidding(null);
//		productVO.setProductId("PRD007");
//		productDAO.updateProduct(productVO);
//		更新狀態
//		productDAO.updateProductDataStatus(1,"PRD006");
//		購買商品新增購買人資料 更新商品狀態為售出
//		productDAO.updateProductBuyMem("M000004",2,"PRD002");
//		System.out.println("DOWN");
		
		
	
//		找出資料庫全部商品
//		List<ProductVO> list  = productDAO.findProductListbyDataStatus(1);
//		for(ProductVO productlist : list) {
//			System.out.println("商品名稱："+productlist.getProductName());
////			System.out.println("商品價格："+productlist.getProductPrice());
//			System.out.println("商品銷售種類："+productlist.getProductSaleType());
//			System.out.println("商品更新時間："+productlist.getProductUpdateDate());
//			System.out.println("競標時間："+productlist.getProductEndBidding());
//			System.out.println("廣告時間："+productlist.getProductAd());
//			System.out.println("===========================================");
//			System.out.println(productlist);
//		};
		
//依狀態查詢商品 網頁畫面要呈顯商品列表用
//		List<ProductVO> listStatus  = productDAO.productList(2);
//		for(ProductVO productlist : listStatus) {
//			System.out.println("商品名稱："+productlist.getProductName());
//			System.out.println("商品價格："+productlist.getProductPrice());
//			System.out.println("商品銷售種類："+productlist.getProductSaleType());
//			System.out.println("商品更新時間："+productlist.getProductUpdateDate());
//
//		};
//賣家清單 可分上架下架關閉賣場等等狀態
//		List<ProductVO> mySalaMenList  = productDAO.mySalaMenList("M000002",1);
//		for(ProductVO productlist : mySalaMenList) {
//			System.out.println("會員編號："+productlist.getMemIdSale());
//			System.out.println("商品名稱："+productlist.getProductName());
//			System.out.println("商品價格："+productlist.getProductPrice());
//			System.out.println("商品銷售種類："+productlist.getProductSaleType());
//			System.out.println("商品更新時間："+productlist.getProductUpdateDate());
//
//		};
		
//		List<ProductVO> myBuyMenList  = productDAO.myBuyMenList("M000003");
//		for(ProductVO productlist : myBuyMenList) {
//			System.out.println("敗家清單");
//			System.out.println("會員編號："+productlist.getMemIdBuy());
//			System.out.println("商品名稱："+productlist.getProductName());
//			System.out.println("商品價格："+productlist.getProductPrice());
//			System.out.println("商品銷售種類："+productlist.getProductSaleType());
//			System.out.println("商品更新時間："+productlist.getProductUpdateDate());
//
//		};
	}

}
