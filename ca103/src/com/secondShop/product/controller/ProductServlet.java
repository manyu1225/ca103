package com.secondShop.product.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Base64.Decoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.secondShop.currency.model.CurrencyDAO;
import com.secondShop.currency.model.CurrencyService;
import com.secondShop.currency.model.CurrencyVO;
import com.secondShop.product.browserRec.BrowserRec;
import com.secondShop.product.model.ProductDAO;
import com.secondShop.product.model.ProductService;
import com.secondShop.product.model.ProductVO;
import com.secondShop.productPhoto.model.ProductPhotoService;
import com.secondShop.productPhoto.model.ProductPhotoVO;
import com.secondShop.productReport.model.ProductReportService;
import com.util.product.GetTimeDay;
import com.util.product.PorductTool;

@MultipartConfig // (fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024,
					// maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public ProductServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("text/html; charset=UTF-8");

		String action = req.getParameter("action");
		String requestURL = req.getParameter("requestURL");
		String setMemIdSale = req.getParameter("setMemIdSale");
		
		if ("createProduct".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String productName = req.getParameter("productName");
				if(productName == null || productName.trim().length() == 0) {     //檢查商品是否未輸入名稱
					errorMsgs.put("productName","請輸入商品名稱");						
				}
				
				if (productName.trim().length()<5) {							//檢查商品名稱至少五個中文或英文字
					errorMsgs.put("productName","商品名稱必須大於5個字");
				}
				
				String productType = req.getParameter("productType");
				if("0".equals(productType)){									//檢查有是否選擇商品類型
					errorMsgs.put("productType","請選擇商品的類型");
				}
				
				String productStatus = req.getParameter("productStatus");
				if("0".equals(productStatus)){									//檢查有是否選擇商品使用狀態
					errorMsgs.put("productStatus","請選擇商品使用狀態");
				}
				
				String productPriceSt = req.getParameter("productPrice"); 		//從網頁送進來的價格,先放在String變數裡面
				Integer productPrice =null;										//實際上VO的型態
				
				if(productPriceSt == null) { 									//先檢查價格String 是否未輸入
					errorMsgs.put("productPrice","請輸入商品價格");															
				}else{                                                          //String 有輸入資料 	=>空白鍵 或是 國字 或是 0
					if(productPriceSt.trim().length() == 0) {
						errorMsgs.put("productPrice","請輸入商品價格");			//String 資料是空白鍵 就放錯誤訊息
					}else {														//String 資料不是空白鍵 => 國字 或是 0
						try {
							productPrice = Integer.parseInt(productPriceSt);   //String非空值/非null 就轉型Integer存回實際變數裡
							if(productPrice<1) {								//String成功轉為數值,最後再檢查必須大於0
								errorMsgs.put("productPrice","商品價格請勿負值");					//檢查商品價格必須大於0
							}
						} catch (NumberFormatException e) {
							errorMsgs.put("productPrice","請輸入數字類型商品價格");								//價格String如果不是數字,會被這裡catch住
						}
					}
				}
				

				String productDetail = req.getParameter("productDetail");
				if(productDetail == null || productDetail.trim().length() == 0){
					errorMsgs.put("productDetail","請輸入商品描述");
				}
				
				List<byte[]> produtPhotoList = new ArrayList<>();
				PorductTool porductTool = new PorductTool();
				Collection<Part> produtPhotoParts = req.getParts();
				for (Part p : produtPhotoParts) {
					if (p.getSize() > 1000) {
						produtPhotoList.add(porductTool.getbyte(p));
					}
				}
				if(produtPhotoList.size()==0) {
					errorMsgs.put("produtPhotoList","請至少上傳一張商品圖片");
				}
				
				String productSaleType = req.getParameter("productSaleType");
				if(productSaleType==null){
					errorMsgs.put("productSaleType","請選擇銷售類型");
				}

				String productBiddingPriceSt = req.getParameter("productBiddingPrice");
				Integer productBiddingPrice = null;
				
				String productEndBiddingSt = req.getParameter("productEndBidding");
				Timestamp productEndBidding = null;			
				if("競標".equals(productSaleType)) {
					//處理競標底價
					try {
						productBiddingPrice = Integer.parseInt(productBiddingPriceSt);
					}catch(NumberFormatException e){
						errorMsgs.put("productBiddingPrice","商品起標價格請勿空白,請輸入數字類型");
					}

					
					if(productBiddingPrice<1) {
						errorMsgs.put("productBiddingPrice","商品起標價格請勿負值");
					}
					if(productBiddingPrice >= productPrice) {
						errorMsgs.put("productBiddingPrice","商品起標價格請勿高於商品直購價格");
					}
					
					//處理競標時間
					if("0".equals(productEndBiddingSt) ) {
						errorMsgs.put("productEndBidding","請選擇競標期限");
					}else{
						productEndBidding = new GetTimeDay().getTimeDay(Integer.parseInt(productEndBiddingSt));
					}
				}
				
				String productAdSt = req.getParameter("productAd");
				Timestamp productAd = null;
				if("1".equals(productAdSt)) {
					productAd = new GetTimeDay().getTimeDay(3);	
				}
				
				ProductVO productVO = new ProductVO();
				productVO.setProductName(productName);
				productVO.setProductType(productType);
				productVO.setProductStatus(productStatus);
				productVO.setProductPrice(productPrice);
				productVO.setProductDetail(productDetail);
				productVO.setProductSaleType(productSaleType);
				productVO.setProductBiddingPrice(productBiddingPrice);
				
					System.out.println("errorMsgs.isEmpty():"+errorMsgs.isEmpty());
				if (!errorMsgs.isEmpty()) { //只要資料有錯誤就會跑這行，
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的empVO物件,也存入req
					req.setAttribute("productEndBidding", productEndBiddingSt);
					req.setAttribute("productAd", productAdSt);
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);//驗證完在把資料送回此
					failureView.forward(req, res);
					return;
				}
	
				/***************************2.開始新增資料***************************************/
				ProductService productSvc = new ProductService();
				if(productSaleType.equals("競標")) {
					productSvc.insertProductAndPhoto_Bidding(
							setMemIdSale, productName, productType, productDetail, productStatus, productSaleType, productAd,
							productPrice, productEndBidding, productBiddingPrice, produtPhotoList);
				}else {
					productSvc.insertProductAndPhoto(setMemIdSale, productName, productType, productDetail, 
							productStatus, productSaleType, productAd, productPrice, produtPhotoList);
				}
				
				/***************************3.新增完成,準備重導去其他網頁)***********/
				res.sendRedirect(req.getContextPath()+"/front-end/secondShop/productMem-sale.jsp");
				/***************************其他可能的錯誤處理**********************************/				
			}catch(Exception e) {
				errorMsgs.put("error",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
//		==========================
		if("getOneForUpdate".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String productId = req.getParameter("productId");
				ProductService productSvc = new ProductService();
				ProductVO productVO= productSvc.findProductByPK(productId);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("productVO",productVO); // 資料庫取出的empVO物件,存入req
					String url = "/front-end/secondShop/updateProduct.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
					successView.forward(req, res);
				System.out.println("227 getOneForUpdate~~~");

			}catch(Exception e) {
				errorMsgs.add("修改資料取出時失敗:"+e.getMessage());
				System.out.println(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		if("updateProduct".equals(action)) {
			
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String productId = req.getParameter("productId");
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String productName = req.getParameter("productName");
				if(productName == null || productName.trim().length() == 0) {     //檢查商品是否未輸入名稱
					errorMsgs.put("productName","請輸入商品名稱");						
				}
				
				if (productName.trim().length()<5) {							//檢查商品名稱至少五個中文或英文字
					errorMsgs.put("productName","商品名稱必須大於5個字");
				}
				String productStatus = req.getParameter("productStatus");
				if("0".equals(productStatus)){									//檢查有是否選擇商品使用狀態
					errorMsgs.put("productStatus","請選擇商品使用狀態");
				}
				String productType = req.getParameter("productType");
				if("0".equals(productType)){									//檢查有是否選擇商品類型
					errorMsgs.put("productType","請選擇商品的類型");
				}
	
				String productPriceSt = req.getParameter("productPrice"); 		//從網頁送進來的價格,先放在String變數裡面
				Integer productPrice =null;										//實際上VO的型態
				
				if(productPriceSt == null) { 									//先檢查價格String 是否未輸入
					errorMsgs.put("productPrice","請輸入商品價格");															
				}else{                                                          //String 有輸入資料 	=>空白鍵 或是 國字 或是 0
					if(productPriceSt.trim().length() == 0) {
						errorMsgs.put("productPrice","請輸入商品價格");							//String 資料是空白鍵 就放錯誤訊息
					}else {														//String 資料不是空白鍵 => 國字 或是 0
						try {
							productPrice = Integer.parseInt(productPriceSt);   //String非空值/非null 就轉型Integer存回實際變數裡
							if(productPrice<1) {								//String成功轉為數值,最後再檢查必須大於0
								errorMsgs.put("productPrice","商品價格請勿負值");					//檢查商品價格必須大於0
							}
						} catch (NumberFormatException e) {
							errorMsgs.put("productPrice","請輸入數字類型商品價格");								//價格String如果不是數字,會被這裡catch住
						}
					}
				}

				String productDetail = req.getParameter("productDetail");
				if(productDetail == null || productDetail.trim().length() == 0){
					errorMsgs.put("productDetail","請輸入商品描述");
				}
				
				List<byte[]> produtPhotoList = new ArrayList<>();
				PorductTool porductTool = new PorductTool();
				Collection<Part> produtPhotoParts = req.getParts();
				for (Part p : produtPhotoParts) {
					if (p.getSize() > 1000) {
						produtPhotoList.add(porductTool.getbyte(p));
					}
				}
				ProductVO DBproductVO = new ProductService().findProductByPK(productId);
				
				ProductVO productVO = new ProductVO();
				productVO.setProductId(productId);
				productVO.setProductName(productName);
				productVO.setProductType(productType);
				productVO.setProductStatus(productStatus);
				productVO.setProductPrice(productPrice);
				productVO.setProductDetail(productDetail);
				productVO.setProductSaleType(DBproductVO.getProductSaleType());
				productVO.setProductBiddingPrice(DBproductVO.getProductBiddingPrice());
				productVO.setProductEndBidding(DBproductVO.getProductEndBidding());

				if (!errorMsgs.isEmpty()) { //只要資料有錯誤就會跑這行，
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);//驗證完在把資料送回此
					failureView.forward(req, res);
					return;
				}
				/***************************3.開始修改資料,準備轉交(Send the Success view)***********/
				ProductService productSvc = new ProductService();
					productSvc.updateProduct(productName, productDetail, productPrice, productType, productStatus, productId, produtPhotoList);
				/***************************3.修改完成,準備轉交(Send the Success view)***********/
				res.sendRedirect(req.getContextPath()+"/front-end/secondShop/productMem-sale.jsp");
				
			}catch(Exception e){
				errorMsgs.put("erroer",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
			
		//更新商品狀態
		if("shutDownPorduct".equals(action)||"doneDelivery".equals(action)||"againgSale".equals(action)){
			String productId = req.getParameter("productId");
			String productName = req.getParameter("productName");
			Integer productPrice =Integer.parseInt(req.getParameter("productPrice"));
			String memIdSale = req.getParameter("memIdSale");
			ProductService productSvc = new ProductService();
			JSONArray jSONArray = new JSONArray();
			JSONObject obj = new JSONObject();
			if("shutDownPorduct".equals(action)) {
				productSvc.updateProductDataStatus(4, productId); //關閉賣場
			}else if("againgSale".equals(action)) {
				productSvc.updateProductDataStatus(1, productId);//重新販售
			}
			else {
				System.out.println("我來收錢囉");
				productSvc.clickProductDone(3, productId, productName, productPrice, memIdSale);
				res.setContentType("text/plain");   //好像沒有也沒關係
				res.setCharacterEncoding("UTF-8");  //好像沒有也沒關係
				try {
					obj.put("productId", productId);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}	
			PrintWriter out = res.getWriter();
			out.write(obj.toString());
			out.flush();
			out.close();
		}
		
		//單獨購買廣告
		if("porductBuyAD".equals(action)) {
	
			List<String> errorMsgs = new LinkedList<>();
			req.setAttribute("errorMsgs", errorMsgs);
			String productName = req.getParameter("productName");
			String memIdSale = req.getParameter("memIdSale");							 
			String productId = req.getParameter("productId");
			System.out.println("productName"+productName);
			System.out.println("memIdSale"+memIdSale);
			System.out.println("productId"+productId);
			new ProductService().buyProductAd(productId, memIdSale, productName);

			JSONObject obj = new JSONObject();
			try {
				obj.put("productId", productId);
				obj.put("memIdSale", memIdSale);
				obj.put("productName", productName);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			res.setContentType("text/plain");
			res.setCharacterEncoding("UTF-8");
			PrintWriter out = res.getWriter();
			
			out.write(obj.toString());
			out.flush();
			out.close();
		}	
	
		if("getOneForView".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String productId = req.getParameter("productId");
				String memId = req.getParameter("memId");
				ProductService productSvc = new ProductService();
				ProductVO productVO= productSvc.findProductByPK(productId);
				BrowserRec  browserRec = new BrowserRec();
				if(memId!=null) {
					browserRec.insterBrowserRec(memId, productId);
				}		
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("productVO",productVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/secondShop/viewProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);
			}catch(Exception e) {
				errorMsgs.add("查詢資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);

			}
		}
		if("buyPorduct".equals(action)) {
			System.out.println("近來買商品");
			String imgBlob = req.getParameter("imgBlob");
			Decoder xx= Base64.getDecoder();
			byte[] decode = xx.decode(imgBlob);
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String productId = req.getParameter("productId");
			String memIdBuy = req.getParameter("memIdBuy");
			String deliveryAddress = req.getParameter("deliveryAddress");
			String deliveryPhone =req.getParameter("deliveryPhone");
			String deliveryName = req.getParameter("deliveryName");
			Integer productPrice = Integer.parseInt(req.getParameter("productPrice"));
			String productName = req.getParameter("productName");
			Integer memCurrecyTotal = Integer.parseInt(req.getParameter("memCurrecyTotal"));
			String phoneType = "^[0-9]*$";
			try {	
				if(memCurrecyTotal<productPrice) {
					errorMsgs.add("自轉幣不足請先儲值");
				}
				if(deliveryName ==null || deliveryName.trim().length() == 0) {
					errorMsgs.add("請輸入收件入姓名");
				}
				if(deliveryAddress ==null || deliveryAddress.trim().length()==0) {
					errorMsgs.add("請輸入地址");
				}
				if(deliveryPhone ==null || deliveryPhone.trim().length()==0) {
					errorMsgs.add("請輸入收件人電話");
				}
				if(!deliveryPhone.trim().matches(phoneType)) {
						errorMsgs.add("請輸入收件人電話,請輸入數字");
				}
			
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.findProductByPK(productId);
				productVO.setProductName(productName);
				productVO.setMemIdBuy(memIdBuy);
				productVO.setDeliveryAddress(deliveryAddress);
				productVO.setDeliveryPhone(deliveryPhone);
				productVO.setDeliveryName(deliveryName);
				productVO.setBproductSnapshot(decode);
				System.out.println("我來放圖片"+decode);
				if (!errorMsgs.isEmpty()) { //只要資料有錯誤就會跑這行，
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher(requestURL);//驗證完在把資料送回此
					failureView.forward(req, res);
					return;
				}		
				/***************************3.更新購買人資料完成,準備重導(Send the Success view)************/
				productSvc.updateProductBuyMem(productVO);
				
				res.sendRedirect(req.getContextPath()+"/front-end/secondShop/productBuyList.jsp");
			}catch(Exception e) {
				errorMsgs.add("購買商品時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
	
		if("addRating".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			JSONObject obj = new JSONObject();
			String productId = req.getParameter("productId");
			Integer productScore = Integer.parseInt(req.getParameter("productScore"));
			String productRating = req.getParameter("productRating");
			try {
				if(productRating ==null|| productRating.trim().length()<5) {
					obj.put("Error", "評價不得為空且不得小於5個字");
					System.out.print("錯誤評價");
				}else {
					ProductService productSvc = new ProductService();
					productSvc.addRating(productRating, productScore, productId);
					obj.put("Done", "新增評價成功");
					obj.put("productId", productId);
				}
				PrintWriter out = res.getWriter();
				out.write(obj.toString());
				out.flush();
				out.close();
				
			}catch(Exception e) {
				errorMsgs.add("新增評價時失敗:"+e.getMessage());
			}
		}
		if("selectProductName".equals(action)) { //商品列表查詢
			Map<String, String[]> map = req.getParameterMap();
			String requestUR2 = req.getParameter("requestURL");
			ProductService productService = new ProductService();
			List<ProductVO> productVO = new ArrayList<>();
			productVO = productService.findProductName(map);
			req.setAttribute("selectProductList",productVO);
			RequestDispatcher successView = req.getRequestDispatcher(requestUR2); //回去來源網
			successView.forward(req, res);	
		}
		
		if("lokSnapshot".equals(action)) {
			System.out.println("我來讀圖片喔");
			res.setContentType("image/jpeg");
			String productId = req.getParameter("productId");
			ServletOutputStream out = res.getOutputStream();
			ProductService productSvc = new ProductService();
			ProductVO productVO = new ProductVO();
			productVO =productSvc.findProductByPK(productId);
			byte[] productSnapshot = productVO.getBproductSnapshot();
			out.write(productSnapshot,0,productSnapshot.length);
			
//			InputStream is = new ByteArrayInputStream(productSnapshot);
//			BufferedInputStream in = new BufferedInputStream(is);
//			byte [] buf = new byte[in.available()];
//			in.read(buf);
//			out.write(buf);
		}
	}
}
