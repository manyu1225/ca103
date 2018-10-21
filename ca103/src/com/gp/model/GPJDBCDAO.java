package com.gp.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mem.model.MemVO;
import com.route.model.RouteVO;



public class GPJDBCDAO implements GPDAO_interface{
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String userid = "CA103G2";
	String passwd = "CA103G2";
	
//	查詢 BY 日期時間
	
//	創建揪團
	private static final String INSERT_STMT = "Insert into GP (GP_ID,MEM_ID,ROT_ID,COM_ID,CRE_TIME,GP_TITLE,GP_DATE,GP_HOUR,GP_TIME,GP_DESC,GP_CONTENT_EDIT,GP_CONTENT,GP_CONTENT_PHOTO,GP_PHOTO,PUB_SET,MIN_NUM,MAX_NUM,SIGN_UP_DD,GP_STATUS,TEAMADDED_QR)  "
			+ "values ('G'||to_char(LPAD(to_char(GP_seq.NEXTVAL), 6, '0')),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//	搜尋某個
	private static final String Find_Keyword_STMT = "select * from GP where GP_STATUS = 0 ";
//	查詢所有的揪團
	private static final String GET_GP_STMT = "select * from GP where GP_ID=?";
//	更新 GP_STATUS BY GP_ID
	private static final String UPDATE_STATUS = "UPDATE GP set GP_STATUS=? where GP_ID = ?";
	private static final String UPDATE_STMT = "update GP set MEM_ID = ?,ROT_ID = ?,COM_ID = ?,CRE_TIME = ?,GP_TITLE = ?,GP_DATE = ?,GP_DESC = ?,GP_PHOTO = ?,PUB_SET = ?,MIN_NUM = ?,MAX_NUM = ?,SIGN_UP_DD = ?,GP_STATUS = ?,TEAMADDED_QR = ?where GP_ID = ?";
	private static final String UPDATE_GP_INFO = "update GP set GP_TITLE = ?,GP_HOUR = ?,GP_TIME = ?,GP_DESC = ?,GP_PHOTO = ?,PUB_SET = ?,MIN_NUM = ?,MAX_NUM = ?,SIGN_UP_DD = ? where GP_ID = ?";
	private static final String Find_CREDGP_BY_MEMBER = "select * from GP where MEM_ID = ?  AND gp_status!=10 AND gp_status!=4 ORDER BY gp_status,pub_set,gp_date";  
	private static final String Find_NUMBER = "select * from GP where MAX_NUM<=? AND MAX_NUM>=?";
	private static final String Find_DATE= "select * from GP where GP_DATE>=? AND GP_DATE<=?";
	private static final String GET_ALL_GP= "select * from GP where pub_set = 0 and gp_status=0";
	private static final String Find_GP_BY_ROT= "select * from GP where ROT_ID = ?";

	private static final String Find_BY_ALL_CONDITION = "select GP.* from GP join ROUTE on GP.ROT_ID = ROUTE.ROT_ID where GP.GP_STATUS = 0 AND PUB_SET = 0 ";
	
	private static final String UPDATE_CONTENT = "update gp set gp_content_edit=?,gp_content=?,gp_content_photo=? where GP_ID=?";

	private static final String FOR_GP_MANAGER= "select * from GP ";
	public static void main(String[] args) {
		GPJDBCDAO dao = new GPJDBCDAO();
		
////	測試照片路徑
//    String filename="result.csv";
//    Path pathToFile = Paths.get(filename);
//    System.out.println(pathToFile.toAbsolutePath());
		
////		 新增
//		插入圖片
//		byte[] photoByte = null;
//		try {
//			File pic = new File("front-end/img", "Tainan.gif");
//			photoByte = Files.readAllBytes(pic.toPath()); // Java 7 : java.nio.file
//		} catch (IOException e) {
//			e.printStackTrace();
//		} 
		
//		GPVO gpVO1 = new GPVO(null,"M000002", "R000001", null, new Timestamp(System.currentTimeMillis()), "一日遊二日吽三日怂",
//				new Date(2018-1900,10,30),9,9,"快來ㄅ吃一雞雞雞", "很多字\n", null, 0, 10, 30, new Date(2018-1900,9,27), 0, "http://");
//		dao.add(gpVO1);
//		GPVO gpVO2 = new GPVO(null,"M000001", "R000001", "C000001", new Timestamp(System.currentTimeMillis()), "雪山隧道一日遊",
//				new Date(2018-1900,9,30), "j94我要ㄉ揪團", null, 0, 10, 30, new Date(2018,9,27), 0, "http://");
//		dao.add(gpVO2);
		
////		修改
//		GPVO gpVO4 = new GPVO();
//		gpVO4.setGp_id("G000006");
//		gpVO4.setGp_status(1);
//		dao.update(gpVO4);
		
//		//搜尋by關鍵字,無關鍵字為查詢所有status為0的揪團
//		List<GPVO> list1 = dao.searchByKw("e","w","w","f");
//		for(GPVO gpVO: list1) {
//			System.out.println(gpVO.getGp_id());
//			System.out.println(gpVO.getGp_title());
//			System.out.println(gpVO.getGp_desc());
//			System.out.println(gpVO.getGp_date());
//			System.out.println();
//		}
		
//		查詢單一揪團
//		GPVO gpVO3 = new GPVO();
//		gpVO3.setGp_id("G000008");
//		dao.searchGPVO(gpVO3);
//		System.out.println(gpVO3.getGp_id());
//		System.out.println(gpVO3.getGp_title());
//		System.out.println(gpVO3.getGp_desc());
//		System.out.println(gpVO3.getGp_date());
//		System.out.println(gpVO3.getCre_time());
////		修改
//		gpVO3.setGp_title("哈囉我被修改囉");
//		dao.update(gpVO3);
////		修改info
//		gpVO3.setGp_title("哈囉我又被修改囉");
//		dao.update(gpVO3);
		
////		搜尋某成員創建的所有揪團
//		MEMBERVO memberVO = new MEMBERVO();
//		memberVO.setMEM_ID("M000001");
//		List<GPVO> list2 = dao.searchCreGP(memberVO);
//		
//		for(GPVO gpVO: list2) {
//			System.out.println(gpVO.getGp_id());
//			System.out.println(gpVO.getGp_title());
//			System.out.println(gpVO.getGp_desc());
//			System.out.println(gpVO.getGp_date());
//			System.out.println();
//		}
		
////		查詢人數
//		List<GPVO> list2 = dao.search(31);		
//		for(GPVO gpVO: list2) {
//			System.out.println(gpVO.getGp_id());
//			System.out.println(gpVO.getGp_title());
//			System.out.println(gpVO.getGp_desc());
//			System.out.println(gpVO.getGp_date());
//			System.out.println();
//		}
		
////		查詢日期
//		List<GPVO> list3 = dao.search(new Date(2018-1900,9-1,28),new Date(2018-1900,9-1,28));		
//		for(GPVO gpVO: list3) {
//			System.out.println(gpVO.getGp_id());
//			System.out.println(gpVO.getGp_title());
//			System.out.println(gpVO.getGp_desc());
//			System.out.println(gpVO.getGp_date());
//			System.out.println();
//		}
		
////		查詢所有揪團
//		List<GPVO> list4 = dao.getAllGP();
//		for(GPVO gpVO: list4) {
//			System.out.println(gpVO.getGp_id());
//			System.out.println(gpVO.getGp_title());
//			System.out.println(gpVO.getGp_desc());
//			System.out.println(gpVO.getGp_date());
//			System.out.println();
//		}
		
		
////		用某路徑查一群揪團
//		RouteVO rotVO = new RouteVO();
//		rotVO.setRot_id("R000001");
//		List<GPVO> gpList = dao.search(rotVO);
//		for(GPVO gpVO : gpList) {
//			System.out.println(gpVO.getGp_id());
//		}
		
////		修改揪團內容
//		GPVO gpVO = new GPVO();
//		gpVO.setGp_id("G000001");
//		gpVO.setGp_content_edit("teeeetettet");
//		gpVO.setGp_content_photo("teeeetettet");
//		gpVO.setGp_content("fewfgwrerewgw");;
//		dao.updateContent(gpVO);
		
//		//for後臺的查詢
//		String str = "where gp_status = 0 order by cre_time";
//		List<GPVO> gpList = dao.forGP_Manager(str);
//		for(GPVO gpVO : gpList) {
//		System.out.println(gpVO.getGp_id());
//		}
	}
	@Override
	public List<GPVO> forGP_Manager(String str) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GPVO gpVO = null;
		List<GPVO> list = new ArrayList<GPVO>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			str = FOR_GP_MANAGER + str;
			pstmt = con.prepareStatement(str);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	
	@Override
	public void updateContent(GPVO gpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_CONTENT);
			pstmt.setString(1, gpVO.getGp_content_edit());
			pstmt.setString(2, gpVO.getGp_content());
			pstmt.setString(3, gpVO.getGp_content_photo());
			pstmt.setString(4, gpVO.getGp_id());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void updateinfo(GPVO gpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_GP_INFO);
			pstmt.setString(1,gpVO.getGp_title());
			pstmt.setInt(2,gpVO.getGp_hour());
			pstmt.setInt(3,gpVO.getGp_time());
			pstmt.setString(4,gpVO.getGp_desc());
			pstmt.setBytes(5,gpVO.getGp_photo());
			pstmt.setInt(6,gpVO.getPub_set());
			pstmt.setInt(7,gpVO.getMin_num());
			pstmt.setInt(8,gpVO.getMax_num());
			pstmt.setDate(9, gpVO.getSign_up_DD());
			pstmt.setString(10, gpVO.getGp_id());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}
	
	@Override
	public void updateStatus(GPVO gpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STATUS);
			pstmt.setInt(1, gpVO.getGp_status());
			pstmt.setString(2, gpVO.getGp_id());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	
	@Override
	public List<GPVO> searchByKw(String...arrKw) {
		List<GPVO> list = new ArrayList<GPVO>();
		GPVO gpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String searchStr = Find_Keyword_STMT;
			String kwString = "AND (RegExp_like(GP_TITLE,?) OR RegExp_like(GP_DESC,?))";
			for(int i=0;i<arrKw.length;i++) {
				searchStr += kwString;
			}
			System.out.println(searchStr);
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(searchStr);
			
			for(int i=0;i<arrKw.length*2;i++) {
				pstmt.setString(i+1, arrKw[i/2]);
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public GPVO searchGPVO(GPVO gpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GPVO gpVO2 = new GPVO();
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_GP_STMT);
			
			pstmt.setString(1,gpVO.getGp_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				setgpVO(gpVO2,rs);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return gpVO2;
	}

	@Override
	public void update(GPVO gpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE_STMT);	
			setPstmt(pstmt,gpVO);
			pstmt.setString(15,gpVO.getGp_id());
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}

	@Override
	public void add(GPVO gpVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			String cols[] = {"GP_ID"};
			pstmt = con.prepareStatement(INSERT_STMT,cols);
			setPstmt(pstmt,gpVO);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				gpVO.setGp_id(rs.getString(1));
			}
		}catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public List<GPVO> searchCreGP(MemVO memberVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GPVO gpVO = null;
		List<GPVO> list = new ArrayList<GPVO>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(Find_CREDGP_BY_MEMBER);
			
			pstmt.setString(1,memberVO.getMem_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	

	@Override
	public List<GPVO> search(RouteVO rotVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GPVO gpVO = null;
		List<GPVO> list = new ArrayList<GPVO>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(Find_GP_BY_ROT);
			
			pstmt.setString(1,rotVO.getRot_id());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<GPVO> search(int maxNum) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GPVO gpVO = null;
		List<GPVO> list = new ArrayList<GPVO>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(Find_NUMBER);
			
			pstmt.setInt(1,maxNum);
			pstmt.setInt(2,maxNum-10);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<GPVO> search(Date dateStart, Date dateEnd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GPVO gpVO = null;
		List<GPVO> list = new ArrayList<GPVO>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(Find_DATE);
			
			pstmt.setDate(1,dateStart);
			pstmt.setDate(2,dateEnd);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<GPVO> getAllGP() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GPVO gpVO = null;
		List<GPVO> list = new ArrayList<GPVO>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_GP);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;

	}
	@Override
	public List<GPVO> searchByCoondition(String searchStr) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		GPVO gpVO = null;
		List<GPVO> list = new ArrayList<GPVO>();
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(Find_BY_ALL_CONDITION + searchStr);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				gpVO = new GPVO();
				setgpVO(gpVO,rs);
				list.add(gpVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	
	private void setPstmt(PreparedStatement pstmt,GPVO gpVO) throws SQLException {
		pstmt.setString(1,gpVO.getMem_id());
		pstmt.setString(2,gpVO.getRot_id());
		pstmt.setString(3,gpVO.getCom_id());
		pstmt.setTimestamp(4,gpVO.getCre_time());
		pstmt.setString(5,gpVO.getGp_title());
		pstmt.setDate(6,gpVO.getGp_date());
		pstmt.setInt(7, gpVO.getGp_hour());
		pstmt.setInt(8, gpVO.getGp_time());
		pstmt.setString(9,gpVO.getGp_desc());
		pstmt.setString(10,gpVO.getGp_content_edit());
		pstmt.setString(11,gpVO.getGp_content());
		pstmt.setString(12,gpVO.getGp_content_photo());
		pstmt.setBytes(13, gpVO.getGp_photo());
		pstmt.setInt(14, gpVO.getPub_set());
		pstmt.setInt(15,gpVO.getMin_num());
		pstmt.setInt(16,gpVO.getMax_num());
		pstmt.setDate(17, gpVO.getSign_up_DD());
		pstmt.setInt(18,gpVO.getGp_status());
		pstmt.setString(19,gpVO.getTeamadded_QR());
	}

	private void setgpVO(GPVO gpVO,ResultSet rs) throws SQLException {
		gpVO.setGp_id(rs.getString(1));
		gpVO.setMem_id(rs.getString(2));
		gpVO.setRot_id(rs.getString(3));
		gpVO.setCom_id(rs.getString(4));
		gpVO.setCre_time(rs.getTimestamp(5));
		gpVO.setGp_title(rs.getString(6));
		gpVO.setGp_date(rs.getDate(7));
		gpVO.setGp_hour(rs.getInt(8));
		gpVO.setGp_time(rs.getInt(9));
		gpVO.setGp_desc(rs.getString(10));
		gpVO.setGp_content_edit(rs.getString(11));
		gpVO.setGp_content(rs.getString(12));
		gpVO.setGp_content_photo(rs.getString(13));
		gpVO.setGp_photo(rs.getBytes(14));
		gpVO.setPub_set(rs.getInt(15));
		gpVO.setMin_num(rs.getInt(16));
		gpVO.setMax_num(rs.getInt(17));
		gpVO.setSign_up_DD(rs.getDate(18));
		gpVO.setGp_status(rs.getInt(19));
		gpVO.setTeamadded_QR(rs.getString(20));
	}



	
	
	
	
}
