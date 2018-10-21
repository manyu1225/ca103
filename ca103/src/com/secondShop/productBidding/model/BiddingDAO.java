package com.secondShop.productBidding.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.hibernate.Session;
import org.hibernate.query.Query;

import hibernate.util.HibernateUtil;

public class BiddingDAO implements BiddingDAO_Interface {
	private static final String BIDDING_INSERT = "insert into bidding (bidding_id,product_id,mem_id,bidding_price,bidding_date) "
			+ "values('BID'||lpad(to_char(bidding_seq.nextval),3,'0'),?,?,?,current_TIMESTAMP)";
//	private static final String BIDDING_MEM_LIST = "SELECT * FROM bidding WHERE mem_id=?";
//	private static final String BIDDING_PRICE_LIST = "select * FROM bidding where product_id= ? order by bidding_price DESC nulls last;";
//	select * FROM product where MEM_ID_SALE= 'M000003' order by PRODUCT_UPDATE_DATE DESC nulls last;
	private static final String BIDDING_BENEFIT_MEM = 
			"SELECT * FROM (SELECT * FROM bidding WHERE PRODUCT_ID=? ORDER BY bidding_price DESC)WHERE ROWNUM = 1";
//	select * FROM  (select MEM_ID, Max(bidding_price) AS BIDDINGBUYMEM FROM bidding where product_id= 'PRD005' group by MEM_ID) WHERE ROWNUM = 1;
//	
	
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
	public BiddingVO insertBiddingPrice(BiddingVO biddingVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(BIDDING_INSERT);
			pstmt.setString(1,biddingVO.getProductId());
			pstmt.setString(2, biddingVO.getMemId());
			pstmt.setInt(3, biddingVO.getBiddingPrice());
			pstmt.executeUpdate();
		} catch (Exception e) {
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
		return biddingVO;	
	}

	@Override
	public List<BiddingVO> productBiddinglist(String productId) { //傳回某產品競標紀錄 依最高價格至最低排序 使用前端讓最高價格最顯眼
		List<BiddingVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<BiddingVO> query = session.createQuery("from BiddingVO where productId==?0 order by biddingPrice DESC nulls last",BiddingVO.class);
			query.setParameter(0, productId);
			list = query.getResultList();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			System.out.print(ex);
			session.getTransaction().rollback();
			throw ex;
		}
		
		return list;
	}

	@Override
	public List<BiddingVO> allBiddingprice(String memId) { //要傳入競標結束日期在商品的表格 某會員參加的競標紀錄
		List<BiddingVO> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query<BiddingVO> query = session.createQuery("from BiddingVO where memId=?0 order by biddingId",BiddingVO.class);
			query.setParameter(0, memId);
			list = query.getResultList();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			System.out.print(ex);
			session.getTransaction().rollback();
			throw ex;
		}
	
		return list;
	}

	@Override
	public BiddingVO biddingBenefitMem(String productId) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BiddingVO biddingVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(BIDDING_BENEFIT_MEM);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				biddingVO = new BiddingVO();
				biddingVO.setBiddingId(rs.getString("bidding_id"));
				biddingVO.setMemId(rs.getString("mem_id"));
				biddingVO.setProductId(rs.getString("product_id"));
				biddingVO.setBiddingPrice(rs.getInt("bidding_price"));
				biddingVO.setBiddingDate(rs.getTimestamp("bidding_date"));
			}
		} catch (Exception e) {
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
		return biddingVO;
	}
	
	
	public static void main (String [] args) {
		BiddingDAO BiddingDAO2 =new BiddingDAO();
		List<BiddingVO> list =BiddingDAO2.allBiddingprice("M000903");
		for(BiddingVO bv: list) {
			System.out.println(bv.getProductVO().getProductName());
			
		}
		
	}

}
