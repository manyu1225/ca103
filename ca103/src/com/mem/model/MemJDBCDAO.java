package com.mem.model;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemJDBCDAO implements MemDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	// to_char(MEM_BIRTHDAY,'yyyy-mm-dd')
	private static final String INSERT_STMT = "INSERT INTO MEMBER(MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_PHOTO,MEM_CART_PHOTO,MEM_NICKNAME,MEM_ABOUTME,MEM_BIRTHDAY,MEM_ROT_BADTIMES,MEM_ACT_BADTIMES,MEM_GRU_BADTIMES,MEM_POST_BADTIMES,MEM_SALE_BADTIMES)"
			+ "values ('M'||LPAD(to_char(mem_seq.NEXTVAL), 6, '0'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String DELETE_STMT = "DELETE FROM MEMBER where empno = ?";
	private static final String GET_ONE_STMT = "SELECT MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_STATUS,MEM_PHOTO,MEM_CART_PHOTO,MEM_CART_TYPE,MEM_NICKNAME,MEM_BIRTHDAY,MEM_ROT_BADTIMES,MEM_ACT_BADTIMES,MEM_GRU_BADTIMES,MEM_POST_BADTIMES,MEM_SALE_BADTIMES,MEM_ABOUTME FROM MEMBER where mem_ac = ?";
	private static final String GET_ONEbyId_STMT = "SELECT MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_STATUS,MEM_PHOTO,MEM_CART_PHOTO,MEM_CART_TYPE,MEM_NICKNAME,MEM_BIRTHDAY,MEM_ROT_BADTIMES,MEM_ACT_BADTIMES,MEM_GRU_BADTIMES,MEM_POST_BADTIMES,MEM_SALE_BADTIMES,MEM_ABOUTME FROM MEMBER where MEM_ID = ?";
	private static final String GET_ALL_STMT = "SELECT MEM_ID,MEM_AC,MEM_PASSWORD,MEM_FIRSTNAME,MEM_LASTNAME,MEM_TEL,MEM_PHONE,MEM_EMAIL,MEM_STATUS,MEM_PHOTO,MEM_CART_PHOTO,MEM_CART_TYPE,MEM_NICKNAME,MEM_BIRTHDAY,MEM_ROT_BADTIMES,MEM_ACT_BADTIMES,MEM_GRU_BADTIMES,MEM_POST_BADTIMES,MEM_SALE_BADTIMES,MEM_ABOUTME FROM MEMBER order by MEM_ID";
	private static final String UPDATE_ByMaster_STMT = "UPDATE MEMBER SET MEM_LASTNAME=?,MEM_FIRSTNAME=?,MEM_AC=?,MEM_EMAIL=?,MEM_PHONE=?,MEM_NICKNAME=?,MEM_BIRTHDAY=?,MEM_ROT_BADTIMES=?,MEM_ACT_BADTIMES=?,MEM_GRU_BADTIMES=?,MEM_POST_BADTIMES=?,MEM_SALE_BADTIMES=? WHERE MEM_ID =?";
	private static final String STATUS_UPDATE_STMT = "UPDATE MEMBER SET MEM_STATUS=? WHERE MEM_AC=?";
	
	private static final String UPDATE_STMT = 
			"UPDATE MEMBER SET "
			+ "MEM_LASTNAME=?,"
			+ "MEM_FIRSTNAME=?,"
			+ "MEM_TEL=?,"
			+ "MEM_PHONE=?,"
			+ "MEM_EMAIL=?,"
			+ "MEM_PHOTO=?,"
			+ "MEM_CART_PHOTO=?,"
			+ "MEM_NICKNAME=?,"
			+ "MEM_ABOUTME=?,"
			+ "MEM_BIRTHDAY=? "
			+ "WHERE MEM_ID =?";
	
	@Override
	public void insert(MemVO memVO) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, memVO.getMem_ac());
			pstmt.setString(2, memVO.getMem_password());
			pstmt.setString(3, memVO.getMem_firstname());
			pstmt.setString(4, memVO.getMem_lastname());
			pstmt.setString(5, memVO.getMem_tel());
			pstmt.setString(6, memVO.getMem_phone());
			pstmt.setString(7, memVO.getMem_email());
			pstmt.setBytes(8, memVO.getMem_photo());
			pstmt.setBytes(9, memVO.getMem_cart_photo());
			pstmt.setString(10, memVO.getMem_nickname());
			pstmt.setString(11, memVO.getMem_aboutme());
			pstmt.setDate(12, memVO.getMem_birthday());
			pstmt.setInt(13, 0);
			pstmt.setInt(14, 0);
			pstmt.setInt(15, 0);
			pstmt.setInt(16, 0);
			pstmt.setInt(17, 0);
		
			

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void update(MemVO memVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(UPDATE_STMT);
			
				pstmt.setString(1, memVO.getMem_lastname());
				pstmt.setString(2, memVO.getMem_firstname());
				pstmt.setString(3, memVO.getMem_tel());
				pstmt.setString(4, memVO.getMem_phone());
				pstmt.setString(5, memVO.getMem_email());
				pstmt.setBytes(6, memVO.getMem_photo());
				pstmt.setBytes(7, memVO.getMem_cart_photo());
				pstmt.setString(8, memVO.getMem_nickname());
				pstmt.setString(9, memVO.getMem_aboutme());
				pstmt.setDate(10, memVO.getMem_birthday());
				/*where條件*/
				pstmt.setString(11, memVO.getMem_id());

			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (conn != null) {
				try {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	@Override
	public void updateByMaster(MemVO memVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_ByMaster_STMT);

			pstmt.setString(1, memVO.getMem_lastname());
			pstmt.setString(2, memVO.getMem_firstname());
			pstmt.setString(3, memVO.getMem_ac());
			pstmt.setString(4, memVO.getMem_email());
			pstmt.setString(5, memVO.getMem_phone());
			pstmt.setString(6, memVO.getMem_nickname());
			pstmt.setDate(7, memVO.getMem_birthday());
			pstmt.setInt(8, memVO.getMem_rot_badtimes());
			System.out.println(memVO.getMem_rot_badtimes());
			pstmt.setInt(9, memVO.getMem_act_badtimes());
			pstmt.setInt(10, memVO.getMem_gru_badtimes());
			pstmt.setInt(11, memVO.getMem_post_badtimes());
			pstmt.setInt(12, memVO.getMem_sale_badtimes());
			pstmt.setString(13, memVO.getMem_id());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void delete(String eme_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);

			pstmt.setString(1, eme_id);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public MemVO findMemById(String mem_id) {
		MemVO memVO = new MemVO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONEbyId_STMT);

			pstmt.setString(1, mem_id);

			rs = pstmt.executeQuery();

			rs.next();
			memVO.setMem_id(rs.getString("mem_id"));
			memVO.setMem_ac(rs.getString("mem_ac"));
			memVO.setMem_password(rs.getString("mem_password"));
			memVO.setMem_firstname(rs.getString("mem_firstname"));
			memVO.setMem_lastname(rs.getString("mem_lastname"));
			memVO.setMem_tel(rs.getString("mem_tel"));
			memVO.setMem_phone(rs.getString("mem_phone"));
			memVO.setMem_email(rs.getString("mem_email"));
			memVO.setMem_status(new Integer(rs.getInt("mem_status")));
			memVO.setMem_photo(rs.getBytes("mem_photo"));
			memVO.setMem_cart_photo(rs.getBytes("mem_cart_photo"));
			memVO.setMem_cart_type(rs.getString("mem_cart_type"));
			memVO.setMem_nickname(rs.getString("mem_nickname"));
			memVO.setMem_birthday(rs.getDate("mem_birthday"));
			memVO.setMem_rot_badtimes(new Integer(rs.getInt("mem_rot_badtimes")));
			memVO.setMem_act_badtimes(new Integer(rs.getInt("mem_act_badtimes")));
			memVO.setMem_gru_badtimes(new Integer(rs.getInt("mem_gru_badtimes")));
			memVO.setMem_post_badtimes(new Integer(rs.getInt("mem_post_badtimes")));
			memVO.setMem_sale_badtimes(new Integer(rs.getInt("mem_sale_badtimes")));
			memVO.setMem_aboutme(rs.getString("mem_aboutme"));

		} catch (SQLException e) {
			e.printStackTrace();
//			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return memVO;
	}

	@Override
	public MemVO findByPrimarKey(String mem_ac) {

		MemVO memVO = new MemVO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, mem_ac);

			rs = pstmt.executeQuery();

			rs.next();
			memVO.setMem_id(rs.getString("mem_id"));
			memVO.setMem_ac(rs.getString("mem_ac"));
			memVO.setMem_password(rs.getString("mem_password"));
			memVO.setMem_firstname(rs.getString("mem_firstname"));
			memVO.setMem_lastname(rs.getString("mem_lastname"));
			memVO.setMem_tel(rs.getString("mem_tel"));
			memVO.setMem_phone(rs.getString("mem_phone"));
			memVO.setMem_email(rs.getString("mem_email"));
			memVO.setMem_status(new Integer(rs.getInt("mem_status")));
			memVO.setMem_photo(rs.getBytes("mem_photo"));
			memVO.setMem_cart_photo(rs.getBytes("mem_cart_photo"));
			memVO.setMem_cart_type(rs.getString("mem_cart_type"));
			memVO.setMem_nickname(rs.getString("mem_nickname"));
			memVO.setMem_birthday(rs.getDate("mem_birthday"));
			memVO.setMem_rot_badtimes(new Integer(rs.getInt("mem_rot_badtimes")));
			memVO.setMem_act_badtimes(new Integer(rs.getInt("mem_act_badtimes")));
			memVO.setMem_gru_badtimes(new Integer(rs.getInt("mem_gru_badtimes")));
			memVO.setMem_sale_badtimes(new Integer(rs.getInt("mem_sale_badtimes")));
			memVO.setMem_aboutme(rs.getString("mem_aboutme"));

		} catch (SQLException e) {
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return memVO;
	}

	@Override
	public List<MemVO> getAll() {
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				memVO = new MemVO();
				memVO.setMem_id(rs.getString("mem_id"));
				memVO.setMem_ac(rs.getString("mem_ac"));
				memVO.setMem_password(rs.getString("mem_password"));
				memVO.setMem_firstname(rs.getString("mem_firstname"));
				memVO.setMem_lastname(rs.getString("mem_lastname"));
				memVO.setMem_tel(rs.getString("mem_tel"));
				memVO.setMem_phone(rs.getString("mem_phone"));
				memVO.setMem_email(rs.getString("mem_email"));
				memVO.setMem_status(rs.getInt("mem_status"));
				memVO.setMem_photo(rs.getBytes("mem_photo"));
				memVO.setMem_cart_photo(rs.getBytes("mem_cart_photo"));
				memVO.setMem_cart_type(rs.getString("mem_cart_type"));
				memVO.setMem_nickname(rs.getString("mem_nickname"));
				String burnDay = new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("mem_birthday"));
				java.sql.Date date = java.sql.Date.valueOf(burnDay);
				memVO.setMem_birthday(date);
				memVO.setMem_rot_badtimes(rs.getInt("mem_rot_badtimes"));
				memVO.setMem_act_badtimes(rs.getInt("mem_act_badtimes"));
				memVO.setMem_gru_badtimes(rs.getInt("mem_gru_badtimes"));
				memVO.setMem_post_badtimes(rs.getInt("mem_post_badtimes"));
				memVO.setMem_sale_badtimes(rs.getInt("mem_sale_badtimes"));
				memVO.setMem_aboutme(rs.getString("mem_aboutme"));
				list.add(memVO);
			}

		} catch (SQLException e) {
			throw new RuntimeException("資料庫發生錯誤" + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return list;
	}

	public static byte[] getPictureByteArray_photo(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

	@Override
	public void status_set(Integer mem_status, String mem_ac) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(STATUS_UPDATE_STMT);

			pstmt.setInt(1, mem_status);
			pstmt.setString(2, mem_ac);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("發生資料庫錯誤" + e.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void main(String[] args) throws IOException {
//		MemJDBCDAO dao = new MemJDBCDAO();
//		MemVO memVO1 = new MemVO();
//
//
//		MemVO memVO3 = new MemVO();
//		memVO3 = dao.findMemById("M000001");
//		System.out.println(memVO3.getMem_ac());
//		System.out.println("有新增?");

	}

	

	

}
