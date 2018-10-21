package com.secondShop.product.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import com.util.product.FinishTimeAD;
import com.util.product.FinishTimeBidding;
import com.util.product.GetTimeDay;
import com.util.product.sms.Send;


public class ProductService {
	private ProductDAO_Interface dao;
	Date date = new Date();                        // util.Date 物件拿到當前時間
	long AD_FINISH_MILSECOND=date.getTime()+(2*60*1000); //測試廣告結束時間的變數 預2分鐘秒 5M*1S*1MM
	Timestamp sqlTimeStamp_2 = new Timestamp(AD_FINISH_MILSECOND);
	public ProductService() {
		dao = new ProductDAO();
	}

	public ProductVO insertProductAndPhoto(String memIdSale, String productName, String productType,
			String productDetail, String productStatus, String productSaleType, Timestamp productAd,
			Integer productPrice, List<byte[]> produtPhotoList) {// 新直購商品--測試ＯＫ
		ProductVO productVO = new ProductVO();
		productVO.setMemIdSale(memIdSale);
		productVO.setProductName(productName);
		productVO.setProductType(productType);
		productVO.setProductDetail(productDetail);
		productVO.setProductStatus(productStatus);
		productVO.setProductSaleType(productSaleType);
		productVO.setProductAd(productAd);
		productVO.setProductPrice(productPrice);
		String productId = dao.insertProductAndPhoto(productVO, produtPhotoList);
		
		if(productAd!=null) { //表示是剛購買廣告要進來追蹤時間下架廣告
			Timer timerAD = new Timer();
//			timerAD.schedule(new FinishTimeAD(productId),productVO.getProductAd());//傳入購買廣告幾天後下架的時間
			timerAD.schedule(new FinishTimeAD(productId),sqlTimeStamp_2);//展示用

		}
		return productVO;
	}

	public ProductVO insertProductAndPhoto_Bidding(String memIdSale, String productName, String productType,
			String productDetail, String productStatus, String productSaleType, Timestamp productAd,
			Integer productPrice, Timestamp productEndBidding, Integer productBiddingPrice,
			List<byte[]> produtPhotoList) {// 新增競標商品與圖片

		ProductVO productVO = new ProductVO();
		productVO.setMemIdSale(memIdSale);
		productVO.setProductName(productName);
		productVO.setProductType(productType);
		productVO.setProductDetail(productDetail);
		productVO.setProductStatus(productStatus);
		productVO.setProductSaleType(productSaleType);
		productVO.setProductAd(productAd);
		productVO.setProductPrice(productPrice);
		productVO.setProductBiddingPrice(productBiddingPrice);
		productVO.setProductEndBidding(sqlTimeStamp_2);
		System.out.println(sqlTimeStamp_2);
		String productId = dao.insertProductAndPhoto_Bidding(productVO, produtPhotoList);
		//新增競標成功後追蹤到期時間要下架並更新購買人
		Timer timerBidding = new Timer();
//		timerBidding.schedule(new FinishTimeBidding(productId),productVO.getProductEndBidding());
		timerBidding.schedule(new FinishTimeBidding(productId),sqlTimeStamp_2);//展示用五分鐘
		if(productAd!=null) { //表示是剛購買廣告要進來追蹤時間下架廣告
			Timer timerAD = new Timer();
//			timerAD.schedule(new FinishTimeAD(productId),sqlTimeStamp_2);//展示用五分鐘
			timerAD.schedule(new FinishTimeAD(productId),productVO.getProductAd());//傳入購買廣告幾天後下架的時間
		}
		return productVO;
	}

	public ProductVO updateProduct(String productName,String productDetail,Integer productPrice,
			String productType,String productStatus,String productId,List<byte[]> produtPhotoList) {
		ProductVO productVO = new ProductVO();
		productVO.setProductName(productName);
		productVO.setProductType(productType);
		productVO.setProductDetail(productDetail);
		productVO.setProductStatus(productStatus);
		productVO.setProductPrice(productPrice);
		productVO.setProductId(productId);
		dao.updateProduct(productVO, produtPhotoList);
		return productVO;
	}
	// 更改狀態檢舉下架或關閉賣場
	public void updateProductDataStatus(Integer producDataStatus, String productId) {
		dao.updateProductDataStatus(producDataStatus, productId);
	}

	public List<ProductVO> findSalesListByMemId(String memIdSale, Integer producDataStatus) {
		return dao.findSalesListByMemId(memIdSale, producDataStatus);

	}

	public List<ProductVO> findBuyListByMemId(String memIdBuy) {
		return dao.findBuyListByMemId(memIdBuy);
	}

	public List<ProductVO> findProductListbyDataStatus(Integer productDataStatus) {
		return dao.findProductListbyDataStatus(productDataStatus);
	}

	public List<ProductVO> findAllProduct() {
		return dao.findAllProduct();
	}

	public ProductVO findProductByPK(String productId) {
		return dao.findProductByPK(productId);
	}

	public List<ProductVO> productTypeSaleTypeList(String productType, String productSaleType) {
		return dao.productTypeSaleTypeList(productType, productSaleType);
	}

	
	public void updateProductAd(Timestamp productAd,String productId) {//下架廣告用排程呼叫
		dao.updateProductAd(productAd, productId);
	
	}
	
	public void updateProductBuyMem (ProductVO productVO) {
		dao.updateProductBuyMem(productVO);
		//購買商品後發訊息
		String smgMssagforbuy =
		"親愛的自轉車會員您好你購買的商品我們會通知賣家盡快幫您出貨，請耐心等候";
		String smgMssagforSale =
		"親愛的自轉車會員您的商品已售出，請至賣家中心查看訂單明細。";
		String[] forBuy = {productVO.getDeliveryPhone()};
		String[] forSale = {"0952355727"};
		Send se = new Send();
		se.sendMessage(forBuy, smgMssagforbuy);
		se.sendMessage(forSale, smgMssagforSale);



	}
	
	public void prductBiddingFinnish(String memIdBuy, String productId,Integer productBiddingWinPrice) {//排成呼叫下架競標商品
		dao.prductBiddingFinnish(memIdBuy, productId,productBiddingWinPrice);
	}
	
	public ProductVO buyProductAd(String productId ,String memIdSale, String productName) {
		ProductVO productVO = new ProductVO();
		productVO.setProductId(productId);
		productVO.setMemIdSale(memIdSale);
		productVO.setProductName(productName);
//		productVO.setProductAd(sqlTimeStamp_2); 展示用五分鐘
		productVO.setProductAd(new GetTimeDay().getTimeDay(3)); //預設三天
		Timer timer = new Timer();
		timer.schedule(new FinishTimeAD(productId),productVO.getProductAd());//傳入購買廣告幾天後下架的時間
		dao.buyProductAd(productVO);
		return productVO;
	}

	public void addRating (String productRating,Integer productScore,String productId) {
		dao.addRating(productRating, productScore, productId);
	}
	public Integer memSaleProductTotal(String memIdSale) {//我的銷售商品數量
		return dao.memSaleProductTotal(memIdSale);	
	}
	public void updateBiddingBuyMemAddr(String deliveryAddress,String deliveryName,String deliveryPhone,String productId) {
		ProductVO productVO = new ProductVO();
		productVO.setDeliveryAddress(deliveryAddress);
		productVO.setDeliveryName(deliveryName);
		productVO.setDeliveryPhone(deliveryPhone);
		productVO.setProductId(productId);
		dao.updateBiddingBuyMemAddr(productVO);
	}
	public Integer memRatingAvg (String memId) {
		return dao.memRatingAvg(memId);
	}//我的評分平均
	public Integer memRatingCount(String memId) {
		return dao.memRatingCount(memId);
	}//我擁有幾筆評價
	public List<ProductVO> findProductName(java.util.Map<String, String[]> map){
		return dao.findProductName(map);
	}
	
	public void clickProductDone(Integer productDataStatus,String productId, String productName , Integer productPrice ,String memIdSale) {
		ProductVO productVO =new ProductVO();
		productVO.setProductId(productId);
		productVO.setProductName(productName);
		productVO.setProductDataStatus(productDataStatus);
		productVO.setProductPrice(productPrice);
		productVO.setMemIdSale(memIdSale);
		dao.clickProductDone(productVO);
	
	}
	public void removeBIddingMem (String productId) {
		dao.removeBIddingMem(productId);
	}
	public List<ProductVO> findExpiredAD(){
		return dao.findExpiredAD();
	}
	public List<ProductVO> findExpiredBidding() {
		return dao.findExpiredBidding();
	}
}
