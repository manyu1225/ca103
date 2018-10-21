package com.secondShop.product.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.secondShop.currency.model.CurrencyDAO;
import com.secondShop.currency.model.CurrencyVO;
import com.secondShop.productPhoto.model.ProductPhotoDAO;
import com.secondShop.productPhoto.model.ProductPhotoVO;

import com.util.product.ProductSelect;

public class ProductDAO implements ProductDAO_Interface{

	private static final String PRODUCT_INSERT = 
			"insert into Product ("+
			"PRODUCT_ID,MEM_ID_SALE,PRODUCT_NAME,PRODUCT_TYPE,PRODUCT_DETAIL,PRODUCT_PRICE,PRODUCT_STATUS,"+
			"PRODUCT_UPDATE_DATE,PRODUCT_SALE_TYPE,PRODUCT_AD,PRODUCT_DATA_STATUS)"+
			"values('PRD'||lpad(to_char(product_seq.nextval),3,'0'),?,?,?,?,?,?,current_TIMESTAMP,?,?,1)";
	private static final String PRODUCT_INSERT_BIDDING = 
			"insert into Product ("+
			"PRODUCT_ID,MEM_ID_SALE,PRODUCT_NAME,PRODUCT_TYPE,PRODUCT_DETAIL,PRODUCT_PRICE,PRODUCT_STATUS,"+
			"PRODUCT_BIDDING_PRICE,PRODUCT_UPDATE_DATE,PRODUCT_SALE_TYPE,PRODUCT_END_BIDDING,PRODUCT_AD,PRODUCT_DATA_STATUS)"+
			"values('PRD'||lpad(to_char(product_seq.nextval),3,'0'),?,?,?,?,?,?,?,current_TIMESTAMP,?,?,?,1)";
	private static final String PRODUCT_UPDATE = "update Product set PRODUCT_NAME =? ,"
			+ "PRODUCT_DETAIL = ? ,PRODUCT_PRICE = ?,PRODUCT_STATUS = ?,PRODUCT_UPDATE_DATE=current_TIMESTAMP,"
			+ "PRODUCT_TYPE = ? WHERE PRODUCT_ID = ?" ; //用網頁去分別直購路競標修改
	private static final String UPDATE_PRODUCT_DATA_STATUS  = "update product set PRODUCT_DATA_STATUS = ? , product_end_bidding =null where product_id = ?"; //更新狀態
	
	//得標沒有結帳的會員兩天內移除該會員 商品狀態改為關閉賣場
	private static final String REMOVE_BIDDING_MEM ="update product set PRODUCT_DATA_STATUS = 4,product_end_bidding =null ,MEM_ID_BUY =null where product_id = ?" + 
			"";
	//得標後新增得標人資料
	private static final String UPDATE_BIGGING_MEM_WIN_DELIVERY="update product set Delivery_ADDRESS = ? ,Delivery_Phone = ? ,Delivery_Name = ?,Delivery_Name = ? where product_id = ? and MEM_ID_BUY = ?";

	
	//即在新增取消訂單，
	private static final String UPDATE_BUY_MEM_PRODUCT_DATA_STATUS = 
			"update product set MEM_ID_BUY = ?, PRODUCT_DATA_STATUS = 2 , Delivery_ADDRESS = ? ,Delivery_Phone = ? ,Delivery_Name = ? ,product_Snapshot=?, PRODUCT_UPDATE_DATE=current_TIMESTAMP where product_id = ?";
	//商品列表 顯示的欄位只顯示上架中的產品,有購買廣告的排前面
	private static final String SELECT_ALL_LIST = "SELECT * FROM Product where PRODUCT_DATA_STATUS = ? order by product_ad DESC nulls last"; 
	private static final String SELECT_ALL_LIST2 = "SELECT * FROM Product where PRODUCT_DATA_STATUS = 1 order by product_ad DESC nulls last"; 
	private static final String SELECT_ALL_BUY_LIST = //某個會員全部購買紀錄,商品管理
			"SELECT * FROM Product where MEM_ID_BUY = ?";
	private static final String SELECT_ALL_SALE_LIST_BY_DATA_STATUS = //某個會員全部販售紀錄,商品管理
			"SELECT * FROM Product where MEM_ID_SALE = ? and PRODUCT_DATA_STATUS = ?";
	private static final String SELECT_ALL= "SELECT * FROM Product";//不分狀態
	private static final String SELECT_ONE_PRODUCT="SELECT * FROM Product where PRODUCT_ID = ?";
	private static final String SELECT_TYPE_SALETYPE="SELECT * FROM Product where PRODUCT_SALE_TYPE = ? and product_type = ?";
	private static final String UPDATE_PRODUCT_AD="update product set product_ad = ? where product_id = ?";
	
	private	static final String UPDATE_BIDDING_MEM="update product set  MEM_ID_BUY = ?, PRODUCT_DATA_STATUS =6 , product_bidding_win_price=?,PRODUCT_UPDATE_DATE=current_TIMESTAMP where PRODUCT_ID  = ?";
	
	private static final String MEM_SALE_TOTAL="SElECT COUNT (MEM_ID_SALE)AS SALE_TOTAL FROM Product WHERE MEM_ID_SALE = ?";
	private static final String UPDATE_SCORE_RATING ="update product set PRODUCT_RATING = ?,PRODUCT_SCORE =? where PRODUCT_ID  = ?";
	private static final String UPDATE_BIDDING_ADDR = "update product set Delivery_ADDRESS = ? , Delivery_ADDRESS = ? ,Delivery_Phone = ? ,Delivery_Name = ? where PRODUCT_ID  = ?";
	private static final String MEM_SALE_RAING_COUNT="SElECT COUNT (PRODUCT_SCORE)AS SCORE_COUNT FROM Product WHERE MEM_ID_SALE = ?";
	private static final String MEM_SALE_RAING_AVG="SElECT AVG (PRODUCT_SCORE)AS SCORE_AVG FROM Product WHERE MEM_ID_SALE = ?";
	private static final String SELECT_NAME_PRODUCT="SELECT * FROM Product where PRODUCT_NAME LIKE ?  and PRODUCT_DATA_STATUS=1 ";
//	and PRODUCT_SALE_TYPE LIKE ? and product_type LIKE ? and
	private static final String SELECT_EXPIRED_BIDDING="SELECT * FROM Product where PRODUCT_END_BIDDING < current_timestamp";
	private static final String SELECT_AD="SELECT * FROM Product where PRODUCT_AD < current_timestamp";

	static Context ctx = null;
	static DataSource ds = null;
	
	static {	
			try {
				ctx = new javax.naming.InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");	
			} catch (NamingException e) {
				e.printStackTrace();
			}		
	}
	
	@Override 
	public List<ProductVO> findAllProduct()  { //後台查詢全部商品--測試ＯＫ
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductVO> productVOList = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductVO productVO = new ProductVO();
				productVO.setProductName(rs.getString("PRODUCT_NAME")); 
				productVO.setProductPrice(rs.getInt("PRODUCT_PRICE"));
				productVO.setProductSaleType(rs.getString("PRODUCT_SALE_TYPE"));
				productVO.setProductUpdateDate(rs.getTimestamp("PRODUCT_UPDATE_DATE"));
				productVOList.add(productVO);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return productVOList;
	}
	
	@Override
	public void insertProduct(ProductVO productVO) {//新增商品--測試ＯＫ
		String next_productId = null;//取得一個空字串,存放自增主鍵
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			String cols[] = { "PRODUCT_ID" };
			pstmt = con.prepareStatement(PRODUCT_INSERT,cols);
			con.setAutoCommit(false);
			pstmt.setString(1, productVO.getMemIdSale()); 			
			pstmt.setString(2, productVO.getProductName()); 			
			pstmt.setString(3, productVO.getProductType()); 	
			pstmt.setString(4,productVO.getProductDetail());//商品明細    good
			pstmt.setInt(5, productVO.getProductPrice());//商品價格 直購價格 4000
			pstmt.setString(6, productVO.getProductStatus());//商品使用狀態 二手
			pstmt.setInt(7,productVO.getProductBiddingPrice());//起標價格  null
//			pstmt.setTimestamp(8, productVO.getProductUpdateDate());	//商品更新時間
			pstmt.setString(8,productVO.getProductSaleType());//種類 直購或是競標  直購
			pstmt.setTimestamp(9,productVO.getProductEndBidding());
			pstmt.setTimestamp(10, productVO.getProductAd());//廣告    null
			//productVO.getProductDataStatus()  新增只能是1己經寫在SQL
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();// 取得對應的自增主鍵值
			rs.next();
			next_productId = rs.getString(1);//把自增主鍵值存入next_orderid
			System.out.println("自增主鍵值 = " + next_productId + "(剛新增成功的訂單編號)");
			
			
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public String insertProductAndPhoto(ProductVO productVO, List<byte[]> produtPhotoList) {//新直購商品--測試ＯＫ
		String next_productId = null;//取得一個空字串,存放自增主鍵
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			String cols[] = { "PRODUCT_ID" };
			pstmt = con.prepareStatement(PRODUCT_INSERT,cols);
			con.setAutoCommit(false);
			pstmt.setString(1, productVO.getMemIdSale()); 			
			pstmt.setString(2, productVO.getProductName()); 			
			pstmt.setString(3, productVO.getProductType()); 	
			pstmt.setString(4,productVO.getProductDetail());//商品明細    good
			pstmt.setInt(5, productVO.getProductPrice());//商品價格 直購價格 4000
			pstmt.setString(6, productVO.getProductStatus());//商品使用狀態 二手
			pstmt.setString(7,productVO.getProductSaleType());//種類 直購或是競標  直購
			pstmt.setTimestamp(8, productVO.getProductAd());//廣告    null
			pstmt.executeUpdate();		
			ResultSet rs = pstmt.getGeneratedKeys();// 取得對應的自增主鍵值
			rs.next();
			next_productId = rs.getString(1);//把自增主鍵值存入next_orderid	
			System.out.println("自增主鍵值 = " + next_productId + "(剛新增成功的訂單編號)");
			System.out.println("前往新增圖片～!");
			
			ProductPhotoDAO productPhotoDAO_JNDI = new ProductPhotoDAO();
			ProductPhotoVO productPhotoVO = new ProductPhotoVO();
			productPhotoVO.setProductId(next_productId);
			
			System.out.println("Photos:"+produtPhotoList.size());
			for(byte[] b :produtPhotoList) {
				productPhotoVO.setProductPhoto(b);
				productPhotoDAO_JNDI.insertProducPhoto(con, productPhotoVO);
			}
			//需判斷
			if(productVO.getProductAd() !=null) {
				CurrencyVO	currencyVO = new CurrencyVO();
				currencyVO.setMemId(productVO.getMemIdSale());
				currencyVO.setCurrencyBalance(-100);
				currencyVO.setCurrencyStatus(2);
				currencyVO.setCurrencyDetail("購買< "+"("+next_productId+"):" + productVO.getProductName()+" >廣告");
				new CurrencyDAO().insertCurrency(con, currencyVO);
			}
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return next_productId;
	}
	
	@Override
	public String insertProductAndPhoto_Bidding(ProductVO productVO, List<byte[]> produtPhotoList){//新增競標--測試ＯＫ
		String next_productId = null;//取得一個空字串,存放自增主鍵
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			String cols[] = { "PRODUCT_ID" };
			pstmt = con.prepareStatement(PRODUCT_INSERT_BIDDING,cols);
			con.setAutoCommit(false);
			pstmt.setString(1, productVO.getMemIdSale()); 			
			pstmt.setString(2, productVO.getProductName()); 			
			pstmt.setString(3, productVO.getProductType()); 	
			pstmt.setString(4,productVO.getProductDetail());//商品明細    good
			pstmt.setInt(5, productVO.getProductPrice());//商品價格 直購價格 4000
			pstmt.setString(6, productVO.getProductStatus());//商品使用狀態 二手
			pstmt.setInt(7,productVO.getProductBiddingPrice());//起標價格  null
			pstmt.setString(8,productVO.getProductSaleType());//種類 直購或是競標  直購
			pstmt.setTimestamp(9,productVO.getProductEndBidding());
			pstmt.setTimestamp(10, productVO.getProductAd());//廣告    null
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();// 取得對應的自增主鍵值
			rs.next();
			next_productId = rs.getString(1);//把自增主鍵值存入next_orderid	
			System.out.println("自增主鍵值 = " + next_productId + "(剛新增成功的訂單編號)");
			System.out.println("前往新增圖片～!");
			
			ProductPhotoDAO productPhotoDAO_JNDI = new ProductPhotoDAO();
			ProductPhotoVO productPhotoVO = new ProductPhotoVO();
			productPhotoVO.setProductId(next_productId);
			
			System.out.println("Photos:"+produtPhotoList.size());
			for(byte[] b :produtPhotoList) {
				productPhotoVO.setProductPhoto(b);
				productPhotoDAO_JNDI.insertProducPhoto(con, productPhotoVO);
			}
			//需判斷
			if(productVO.getProductAd() !=null) {
				CurrencyDAO currencyJDBCDAO = new CurrencyDAO();
				CurrencyVO	currencyVO = new CurrencyVO();
				currencyVO.setMemId(productVO.getMemIdSale());
				currencyVO.setCurrencyBalance(-100);
				currencyVO.setCurrencyStatus(0);
				currencyVO.setCurrencyDetail("購買< "+"("+next_productId+"):" + productVO.getProductName()+" >廣告");
				currencyJDBCDAO.insertCurrency(con, currencyVO);
			}
			con.commit();
			
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return next_productId;
	}

	@Override
	public void updateProduct(ProductVO productVO,List<byte[]> produtPhotoList) { //更新商品--測試ＯＫ 
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(PRODUCT_UPDATE);
			con.setAutoCommit(false);
			pstmt.setString(1,productVO.getProductName());//修改商品名稱
			pstmt.setString(2, productVO.getProductDetail());
			pstmt.setInt(3, productVO.getProductPrice());//修改商品價格
			pstmt.setString(4, productVO.getProductStatus());//商品狀況二手或全新
			pstmt.setString(5, productVO.getProductType());//商品類型是單車配件或其他
			pstmt.setString(6, productVO.getProductId());
			pstmt.executeUpdate();	
			ProductPhotoDAO productPhotoDAO_JNDI = new ProductPhotoDAO();
			ProductPhotoVO productPhotoVO = new ProductPhotoVO();
			productPhotoVO.setProductId(productVO.getProductId());
			System.out.println("Photos:"+produtPhotoList.size());
			for(byte[] b :produtPhotoList) {
				productPhotoVO.setProductPhoto(b);
				productPhotoDAO_JNDI.insertProducPhoto(con, productPhotoVO);
			}
			
			con.commit();
			
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				if(con!=null)
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
		
	}

	@Override
	public void updateProductDataStatus (Integer producDataStatus,String productId) {//--測試ＯＫ更新商品目前狀態如銷售中  售出 關閉賣場 
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(UPDATE_PRODUCT_DATA_STATUS);
			pstmt.setInt(1,producDataStatus);
			pstmt.setString(2,productId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
	}

	@Override   
	public List<ProductVO> findSalesListByMemId(String memIdSale, Integer producDataStatus) { //--測試ＯＫ我的銷售清單 可顯示上架中和已經賣出關閉賣場等 1銷售中 2賣出 3關閉賣場 4檢舉 5封存
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO productVO = null;
		List<ProductVO> mySalaMemList = new ArrayList<>();
		try {
			con = ds.getConnection();			
			pstmt = con.prepareStatement(SELECT_ALL_SALE_LIST_BY_DATA_STATUS );
			pstmt.setString(1, memIdSale);
			pstmt.setInt(2, producDataStatus);
			rs = pstmt.executeQuery();//查詢資料列
			while(rs.next()){
				productVO = new ProductVO();
				productVO.setProductAd(rs.getTimestamp("PRODUCT_AD"));
				productVO.setProductId(rs.getString("PRODUCT_ID"));
				productVO.setMemIdSale(rs.getString("MEM_ID_SALE"));
				productVO.setProductName(rs.getString("PRODUCT_NAME"));
				productVO.setProductPrice(rs.getInt("PRODUCT_PRICE"));
				productVO.setProductDetail(rs.getString("PRODUCT_DETAIL"));
				productVO.setProductSaleType(rs.getString("PRODUCT_SALE_TYPE"));
				productVO.setProductUpdateDate(rs.getTimestamp("PRODUCT_UPDATE_DATE"));
				productVO.setProductScore(rs.getInt("PRODUCT_SCORE"));
				productVO.setProductRating(rs.getString("PRODUCT_RATING"));
				productVO.setProductBiddingWinPrice(rs.getInt("product_bidding_win_price"));
				productVO.setDeliveryPhone(rs.getString("Delivery_Phone"));
				productVO.setDeliveryAddress(rs.getString("Delivery_address"));
				productVO.setDeliveryName(rs.getString("Delivery_Name"));
				productVO.setBproductSnapshot(rs.getBytes("product_Snapshot"));
			
				
				mySalaMemList.add(productVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return mySalaMemList;
	}

	@Override
	public List<ProductVO> findBuyListByMemId(String memIdBuy) {//--測試ＯＫ我的敗家清單

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO productVO = null;
		List<ProductVO> myBuyMemList = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_ALL_BUY_LIST);
			pstmt.setString(1, memIdBuy);
			rs = pstmt.executeQuery();//查詢資料列	
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setMemIdSale(rs.getString("MEM_ID_SALE"));
				productVO.setMemIdBuy(rs.getString("MEM_ID_BUY"));
				productVO.setProductId(rs.getString("PRODUCT_ID"));
				productVO.setProductName(rs.getString("PRODUCT_NAME"));
				productVO.setProductPrice(rs.getInt("PRODUCT_PRICE"));
				productVO.setProductDetail(rs.getString("PRODUCT_DETAIL"));
				productVO.setProductSaleType(rs.getString("PRODUCT_SALE_TYPE"));
				productVO.setProductDataStatus(rs.getInt("PRODUCT_DATA_STATUS"));
				productVO.setProductUpdateDate(rs.getTimestamp("PRODUCT_UPDATE_DATE"));
				productVO.setProductScore(rs.getInt("PRODUCT_SCORE"));
				productVO.setProductRating(rs.getString("PRODUCT_RATING"));
				productVO.setProductBiddingWinPrice(rs.getInt("product_bidding_win_price"));
				productVO.setDeliveryPhone(rs.getString("Delivery_Phone"));
				productVO.setDeliveryAddress(rs.getString("Delivery_address"));
				productVO.setDeliveryName(rs.getString("Delivery_Name"));
				productVO.setBproductSnapshot(rs.getBytes("product_Snapshot"));
			
				myBuyMemList.add(productVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return myBuyMemList;
	}

	@Override
	public List<ProductVO> findProductListbyDataStatus(Integer productDataStatus) {//使用狀態找出不同狀態的商品列表--測試ＯＫ 廣告排前面

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO productVO = null;
		List<ProductVO> productVOList = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_ALL_LIST);
			pstmt.setInt(1, productDataStatus);
			rs = pstmt.executeQuery();//查詢資料列
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setProductAd(rs.getTimestamp("PRODUCT_AD"));
				productVO.setProductId(rs.getString("PRODUCT_ID"));
				productVO.setProductName(rs.getString("PRODUCT_NAME"));
				productVO.setProductPrice(rs.getInt("PRODUCT_PRICE"));
				productVO.setProductDetail(rs.getString("PRODUCT_DETAIL"));
				productVO.setProductSaleType(rs.getString("PRODUCT_SALE_TYPE"));
				productVO.setProductUpdateDate(rs.getTimestamp("PRODUCT_UPDATE_DATE"));
				productVO.setProductDataStatus(rs.getInt("product_data_status"));
				productVO.setMemIdSale(rs.getString("mem_id_sale"));
				productVO.setBproductSnapshot(rs.getBytes("product_Snapshot"));
				productVOList.add(productVO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return productVOList;
	}
	
	@Override
	public void updateProductBuyMem(ProductVO productVO) { //購買商品需要新增購買資料更改售出 扣除自轉幣
		Connection con = null;   // 使用getProductId 更新買的會員 更改狀態為售出 
		PreparedStatement pstmt = null;
		try { //
	
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_BUY_MEM_PRODUCT_DATA_STATUS);
			con.setAutoCommit(false);
			pstmt.setString(1,productVO.getMemIdBuy());	
			pstmt.setString(2,productVO.getDeliveryAddress());
			pstmt.setString(3, productVO.getDeliveryPhone());
			pstmt.setString(4, productVO.getDeliveryName());
			pstmt.setBytes(5, productVO.getBproductSnapshot());
			pstmt.setString(6,productVO.getProductId());
			pstmt.executeUpdate();	 
			CurrencyDAO currencyJDBCDAO = new CurrencyDAO();
			CurrencyVO currencyVO = new CurrencyVO();
			currencyVO.setMemId(productVO.getMemIdBuy());
			currencyVO.setCurrencyBalance(productVO.getProductPrice()*(-1));
			currencyVO.setCurrencyStatus(2);
			currencyVO.setCurrencyDetail("購買< "+"("+productVO.getProductId()+"):" + productVO.getProductName()+" >商品");
			currencyJDBCDAO.insertCurrency(con, currencyVO);
		
			con.commit();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
	

	} 
	
	@Override
	public ProductVO findProductByPK(String productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO productVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_ONE_PRODUCT);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();//查詢資料列	
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setMemIdSale(rs.getString("MEM_ID_SALE"));
				productVO.setProductId(rs.getString("PRODUCT_ID"));
				productVO.setProductName(rs.getString("PRODUCT_NAME")); //商品名稱
				productVO.setProductDetail(rs.getString("PRODUCT_DETAIL"));
				productVO.setProductSaleType(rs.getString("PRODUCT_SALE_TYPE"));//直購或競標
				productVO.setProductType(rs.getString("PRODUCT_TYPE"));
				productVO.setProductStatus(rs.getString("PRODUCT_STATUS"));//二手或全新
				productVO.setProductPrice(rs.getInt("PRODUCT_PRICE"));//價格
				productVO.setProductUpdateDate(rs.getTimestamp("PRODUCT_UPDATE_DATE"));//更新時間
				productVO.setProductBiddingPrice(rs.getInt("PRODUCT_BIDDING_PRICE"));
				productVO.setProductEndBidding(rs.getTimestamp(("PRODUCT_END_BIDDING")));
				productVO.setProductScore(rs.getInt("PRODUCT_SCORE"));
				productVO.setProductRating(rs.getString("PRODUCT_RATING"));
				productVO.setProductBiddingWinPrice(rs.getInt("product_bidding_win_price"));
				productVO.setProductDataStatus(rs.getInt("product_data_status"));
				productVO.setBproductSnapshot(rs.getBytes("product_Snapshot"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return productVO;
	}
	
	@Override
	public List<ProductVO> productTypeSaleTypeList(String productType, String productSaleType) {//可以使用查詢 選擇類別單車可以在選擇銷售種類查詢商品
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO productVO = null;
		List<ProductVO> producTypeSaleTypeList = new ArrayList<>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SELECT_TYPE_SALETYPE);
			pstmt.setString(1, productType);
			pstmt.setString(2, productSaleType);
			rs = pstmt.executeQuery();//查詢資料列	
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setProductId(rs.getString("PRODUCT_ID"));
				productVO.setProductName(rs.getString("PRODUCT_NAME"));
				productVO.setProductPrice(rs.getInt("PRODUCT_PRICE"));
				productVO.setProductSaleType(rs.getString("PRODUCT_SALE_TYPE"));
				productVO.setProductUpdateDate(rs.getTimestamp("PRODUCT_UPDATE_DATE"));	
				producTypeSaleTypeList.add(productVO);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return producTypeSaleTypeList;
	}

	
	//下架廣告	
	@Override
	public void updateProductAd(Timestamp timeAd,String productId) { 
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PRODUCT_AD);
			pstmt.setTimestamp(1, timeAd);
			pstmt.setString(2, productId);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ProductVO prductBiddingFinnish(String memIdBuy, String productId, Integer productBiddingWinPrice) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_BIDDING_MEM);
			
			pstmt.setString(1, memIdBuy);
			pstmt.setInt(2, productBiddingWinPrice);
			pstmt.setString(3, productId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

	@Override
	public void buyProductAd(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_PRODUCT_AD);
			pstmt.setTimestamp(1, productVO.getProductAd());
			pstmt.setString(2, productVO.getProductId());
			pstmt.executeUpdate();
			CurrencyVO	currencyVO = new CurrencyVO();
			currencyVO.setMemId(productVO.getMemIdSale());
			currencyVO.setCurrencyBalance(-100);
			currencyVO.setCurrencyStatus(2);
			currencyVO.setCurrencyDetail("購買< "+"("+productVO.getProductId()+"):" + productVO.getProductName()+" >廣告");
			new CurrencyDAO().insertCurrency(con, currencyVO);
			con.commit();
		} catch (SQLException e) {
			try {
				if(con!=null) {
					con.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Integer memSaleProductTotal(String memIdSale) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer saleTotal = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(MEM_SALE_TOTAL);
			pstmt.setString(1, memIdSale);
			rs = pstmt.executeQuery();
			rs.next();
			saleTotal = rs.getInt("SALE_TOTAL");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return saleTotal;
	}

	@Override
	public void addRating(String rating, Integer score ,String productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_SCORE_RATING);
				pstmt.setString(1, rating); //評價
				pstmt.setInt(2, score);//評分
				pstmt.setString(3, productId);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(con!=null) {
					 try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
		
	}

	@Override
	public void updateBiddingBuyMemAddr(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(UPDATE_BIDDING_ADDR);
					pstmt.setString(1, productVO.getDeliveryAddress());
					pstmt.setString(2, productVO.getDeliveryName());
					pstmt.setString(3, productVO.getDeliveryPhone());
					pstmt.setString(4, productVO.getProductId());
					pstmt.executeUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					if(con!=null) {
						try {
							con.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}


		
	}

	@Override
	public Integer memRatingAvg(String memId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer ratingAvg= null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(MEM_SALE_RAING_AVG);
				pstmt.setString(1, memId);
				rs = pstmt.executeQuery();
				rs.next();
				ratingAvg = rs.getInt("SCORE_AVG");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(con!=null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		
		return ratingAvg;
	}
	
	public Integer memRatingCount(String memId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer ratingConut= null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(MEM_SALE_RAING_COUNT);
				pstmt.setString(1, memId);
				rs = pstmt.executeQuery();
				rs.next();
				ratingConut = rs.getInt("SCORE_COUNT");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if(con!=null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		
		return ratingConut;
	}

	@Override
	public List<ProductVO> findProductName(java.util.Map<String, String[]> map) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
		ProductVO productVO =null;
		 List<ProductVO> productVOlist = new ArrayList<>();
		try {	
			String st = ProductSelect.get_WhereCondition(map).trim();
			System.out.println("stL"+st.length());
			con = ds.getConnection();
			if(st.length()==0) {
				pstmt = con.prepareStatement(SELECT_ALL_LIST2);
			}else {
				String finalSQL = "select * from Product "
						+ st + " and PRODUCT_DATA_STATUS=1 order by Product_AD";
				pstmt = con.prepareStatement(finalSQL);
			}
	
			rs =pstmt.executeQuery();
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setMemIdSale(rs.getString("MEM_ID_SALE"));
				productVO.setProductId(rs.getString("PRODUCT_ID"));
				productVO.setProductName(rs.getString("PRODUCT_NAME")); //商品名稱
				productVO.setProductDetail(rs.getString("PRODUCT_DETAIL"));
				productVO.setProductSaleType(rs.getString("PRODUCT_SALE_TYPE"));//直購或競標
				productVO.setProductType(rs.getString("PRODUCT_TYPE"));
				productVO.setProductStatus(rs.getString("PRODUCT_STATUS"));//二手或全新
				productVO.setProductPrice(rs.getInt("PRODUCT_PRICE"));//價格
				productVO.setProductUpdateDate(rs.getTimestamp("PRODUCT_UPDATE_DATE"));//更新時間
				productVO.setProductBiddingPrice(rs.getInt("PRODUCT_BIDDING_PRICE"));
				productVO.setProductEndBidding(rs.getTimestamp(("PRODUCT_END_BIDDING")));
				productVO.setProductScore(rs.getInt("PRODUCT_SCORE"));
				productVO.setProductRating(rs.getString("PRODUCT_RATING"));
				productVOlist.add(productVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productVOlist;
		
	}


	@Override
	public void updateBiddingWinMemDelivery(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con =ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_BIGGING_MEM_WIN_DELIVERY);
			pstmt.setString(1, productVO.getDeliveryAddress());
			pstmt.setString(2, productVO.getDeliveryName());
			pstmt.setString(3,productVO.getDeliveryPhone());
			pstmt.setString(4, productVO.getProductId());
			pstmt.setString(5,productVO.getMemIdBuy());
			pstmt.executeUpdate();
	
			CurrencyDAO currencyJDBCDAO = new CurrencyDAO();
			CurrencyVO	currencyVO = new CurrencyVO();
			currencyVO.setMemId(productVO.getMemIdBuy());
			currencyVO.setCurrencyStatus(1);
			pstmt.setInt(2,productVO.getProductBiddingWinPrice());	
			pstmt.setInt(3,1);
			pstmt.setString(4,("購買< "+"("+productVO.getProductId()+"):" + productVO.getProductName()+" >商品"));
			currencyJDBCDAO.insertCurrency(con, currencyVO);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void clickProductDone(ProductVO productVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(UPDATE_PRODUCT_DATA_STATUS);
			pstmt.setInt(1,productVO.getProductDataStatus());
			pstmt.setString(2,productVO.getProductId());
			pstmt.executeUpdate();
			CurrencyDAO currencyDAO= new CurrencyDAO();
			CurrencyVO currencyVO = new CurrencyVO();
			if(productVO.getProductBiddingWinPrice()!=null) { //如果競標得標就扣除競標價格
				currencyVO.setCurrencyBalance(productVO.getProductBiddingWinPrice());
			}
			currencyVO.setCurrencyBalance(productVO.getProductPrice());
			currencyVO.setCurrencyDetail("銷售< "+"("+productVO.getProductId()+"):" + productVO.getProductName()+" >商品");
			currencyVO.setCurrencyStatus(0);
			currencyVO.setMemId(productVO.getMemIdSale());
			currencyDAO.insertCurrency(con, currencyVO);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}


			
	}

	@Override
	public void removeBIddingMem(String productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
			try {
				con =ds.getConnection();
				pstmt = con.prepareStatement(REMOVE_BIDDING_MEM);
				pstmt.setString(1, productId);
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(con!=null) {
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			
	}

	@Override
	public List<ProductVO> findExpiredBidding() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO productVO =null;
		List<ProductVO> productIdlist = new ArrayList<>();
		try {
			con =ds.getConnection();
			pstmt = con.prepareStatement(SELECT_EXPIRED_BIDDING);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setProductId(rs.getString("PRODUCT_ID"));
				productIdlist.add(productVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return productIdlist;
		
	}

	@Override
	public List<ProductVO> findExpiredAD() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductVO productVO =null;
		List<ProductVO> productIdlist = new ArrayList<>();
		try {
			con =ds.getConnection();
			pstmt = con.prepareStatement(SELECT_AD);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				productVO = new ProductVO();
				productVO.setProductId(rs.getString("PRODUCT_ID"));
				productIdlist.add(productVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return productIdlist;
	}
}

