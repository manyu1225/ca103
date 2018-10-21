package com.util.product;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.secondShop.product.model.ProductService;
import com.secondShop.product.model.ProductVO;
import com.secondShop.productBidding.model.BiddingDAO;
import com.secondShop.productBidding.model.BiddingVO;
import com.util.product.sms.MailService;
import com.util.product.ReBiddingMem;

public class FinishTimeBidding extends TimerTask{
	String productId;
	public FinishTimeBidding() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FinishTimeBidding(String productId) {
		super();
		 this.productId =productId;
	}

	@Override
	public void run() {
		//要寫一個新方法只更新競標結束找到最高出價者 設定進該商品 方法傳入商品id 會員id更改狀態為售出
		//用訊息東西得標者請填寫收件地址及結帳,在我的敗家記錄中也可以查看到
		Date date = new Date();                        // util.Date 物件拿到當前時間
		long FINISH_MILSECOND=date.getTime()+(2*60*1000); //測試時間的變數 預2分鐘秒 5M*1S*1MM
		Timestamp sqlTimeStamp = new Timestamp(FINISH_MILSECOND);
		
		BiddingDAO biddingDAO =new BiddingDAO();
		BiddingVO biddingVO = new BiddingVO();
		biddingVO = biddingDAO.biddingBenefitMem(productId);
		
		ProductService productService = new ProductService();
		ProductVO producVO = new ProductVO();
		
		ProductService  porductSvc = new ProductService();
		if((biddingVO !=null)) {
			System.out.println("時間到來下架競標商品"); 
	        System.out.println("更新競標商品的買家資料 : "+ new Date());        
			porductSvc.prductBiddingFinnish(biddingVO.getMemId(), biddingVO.getProductId(),biddingVO.getBiddingPrice());; //更新狀態為售出
			producVO = productService.findProductByPK(productId);
//			System.out.println("producVO.getMemIdBuy"+producVO.getMemIdBuy());
			MemVO forSale = new MemVO();
			MemVO forbuy = new MemVO();
			MemService memService = new MemService();
			forSale = memService.findMemById(producVO.getMemIdSale());
			forbuy = memService.findMemById(biddingVO.getMemId());//拿到買家的
			System.out.println("這裡面有東西嗎forSale"+forSale.toString());
			String subject = "您好這裡是自転車平台";
			String smgMssagforbuy =
		            "恭喜您得標商品 ,請於兩天內至買家中心填寫收件資訊及付款,謝謝！若兩天內未填寫將視同放棄這次的得標商品";
					String smgMssagforSale =
					"親愛的自轉車會員您的競標商品已結束競標，請至賣家中心查看得標會員準備出貨。";
					MailService mail = new MailService();
					mail.sendMail(forbuy.getMem_email(), subject, smgMssagforbuy);
					mail.sendMail(forSale.getMem_email(), subject, smgMssagforSale);
					System.out.println("發信給買家"+forbuy.getMem_email());
					System.out.println("發信給賣家"+forSale.getMem_email());
		Timer timer = new Timer();
		
		timer.schedule(	new ReBiddingMem(productId)	, sqlTimeStamp);
		
		}else {
			porductSvc.updateProductDataStatus(4, productId);//若時間到沒有人出價
			System.out.println("時間到來下架沒人競標商品"); 

		}
	       
		
	}
}
