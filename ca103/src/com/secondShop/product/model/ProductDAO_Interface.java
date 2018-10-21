package com.secondShop.product.model;
import java.sql.Timestamp;
import java.util.List;

import com.secondShop.currency.model.CurrencyDAO;
import com.sun.javafx.collections.MappingChange.Map;

public interface ProductDAO_Interface {
	public void insertProduct(ProductVO productVO);
	public void updateProduct(ProductVO productVO,List<byte[]> produtPhotoList);//修改商品 
	public void updateProductAd(Timestamp timeAd, String productId); //購買廣告 ,時間到移除廣告，排程器比對
	
	public void buyProductAd(ProductVO productVO) ;
	public void updateProductBuyMem (ProductVO productVO);//購買商品新增購買人資料 更新商品狀態為售出

	public void updateBiddingWinMemDelivery (ProductVO productVO);
	public List<ProductVO> productTypeSaleTypeList(String productType ,String productSaleType);
	public ProductVO findProductByPK(String productId);
	public List<ProductVO> findProductListbyDataStatus(Integer productDataStatus);
	public List<ProductVO> findBuyListByMemId(String memIdBuy);
	//競標結束後新增購買人資料 發送通知後再新增地址
	public ProductVO prductBiddingFinnish(String memIdBuy, String productId ,Integer productBiddingWinPrice) ;
	public void updateBiddingBuyMemAddr(ProductVO productVO);//要刪除
	//新增直購商品和圖片 如購買廣告會去新增一筆自転幣消費明細
	public List<ProductVO> findAllProduct();
	public List<ProductVO> findSalesListByMemId(String memIdSale ,Integer producDataStatus);
	public void updateProductDataStatus (Integer producDataStatus,String productId);//修改某商品狀態
	public String insertProductAndPhoto(ProductVO productVO,List<byte[]> produtPhotoList );
	public String insertProductAndPhoto_Bidding(ProductVO productVO, List<byte[]> produtPhotoList); //新增競標商品與圖片
	public Integer memSaleProductTotal(String memIdSale);
	public Integer memRatingAvg (String memId); //我的評分平均
	public Integer memRatingCount(String memId);//我擁有幾筆評價
	public void addRating (String rating,Integer score,String productId);
	public List<ProductVO> findProductName (java.util.Map<String, String[]> map);
	public void clickProductDone(ProductVO productVO);
	public void removeBIddingMem (String productId);
	public List<ProductVO> findExpiredBidding ();//檢查時否有沒下架到競標商品
	public List<ProductVO> findExpiredAD ();
	
	
}
