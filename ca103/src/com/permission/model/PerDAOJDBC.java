package com.permission.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.emp_per.controller.*;

public class PerDAOJDBC implements PerDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

//	String driver = "oracle.jdbc.driver.OracleDriver";
//	String url = "jdbc:oracle:thin:@localhost:1521:XE";
//	String user = "CA103G2";
//	String pw = "123456";

	private static final String INSERT_STMT = "INSERT INTO PERMISSION(PERMISSION_ID,PERMISSION_ITEM) VALUES('PER'||LPAD(to_char(PER_seq.NEXTVAL), 6, '0'),?) ";
	private static final String UPDATE_STMT = "UPDATE PERMISSION SET PERMISSION_ID=?,PERMISSION_ITEM=? WHERE PERMISSION_ID";
	private static final String GET_ONE_STMT = "SELECT PERMISSION_ID,PERMISSIO_ITEM WHERE PERMISSION_ID=?";
	private static final String GET_ALL_STMT = "SELECT PERMISSION_ID,PERMISSION_ITEM FROM PERMISSION ORDER BY PERISSION_ID";
	private static final String FIND_PERMISSIONById_STMT = "SELECT P.PERMISSION_ITEM FROM PERMISSION P RIGHT JOIN EMP_PERMISSION EP ON EP.PERMISSION_ID = P.PERMISSION_ID WHERE EP.EMPLOYEE_ID=?";

	@Override
	public void insert(PerVO perVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);

			pstmt.setString(1, perVO.getPermission_id());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
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
	public void update(PerVO perVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);

			pstmt.setString(1, perVO.getPermission_id());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
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
	public void delete(PerVO perVO) {

	}

	@Override
	public PerVO findByPrimarKey(String permission_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PerVO perVO = new PerVO();
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);

			pstmt.setString(1, permission_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				perVO = new PerVO();
				perVO.setPermission_id(rs.getString("permission_id"));
				perVO.setPermission_item(rs.getString("permission_item"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
		return perVO;
	}

	@Override
	public List<PerVO> getAll() {
		List<PerVO> list = new ArrayList<PerVO>();
		PerVO perVO = new PerVO();
		Connection conn = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				perVO = new PerVO();
				perVO.setPermission_id(rs.getString("permission_id"));
				perVO.setPermission_item(rs.getString("permission_item"));
				list.add(perVO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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

		return list;
	}

	@Override
	public List<PerVO> findPerNameByEmp(String employee_id) {
		List<PerVO> list = new ArrayList<PerVO>();

		Connection conn = null;
		PreparedStatement pstmt = null;

		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(FIND_PERMISSIONById_STMT);
			pstmt.setString(1, employee_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				System.out.println("有進來");
				PerVO perVO = new PerVO();
				perVO.setPermission_item(rs.getString("permission_item"));
				list.add(perVO);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
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

		return list;
	}

	public static void main(String[] args) {
//		PerDAOJDBC dao = new PerDAOJDBC();
//		PerVO perVO = new PerVO();
//		List<PerVO> list = new ArrayList<PerVO>();
//		list = dao.findPerNameByEmp("E000007");
//		System.out.println(list.size());
//		for(PerVO a1:list) {
//			System.out.println(a1.getPermission_item());
//		}

	}

}
