package com.emp_per.model;

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

import com.emp_per.model.EmpPerVO;

public class EmpPerJDBCDAO implements EmpPerDAO_interface {
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private String INSERT_STMT = "INSERT INTO EMP_PERMISSION(PERMISSION_ID,EMPLOYEE_ID) VALUES(?,?)";
	private String DELETE_STMT = "DELETE FROM PERMISSION WHERE PERMISSION_ID=?";
	private String GET_ONE_STMT = "SELECT  PER_NO,PERMISSION_ID,EMPLOYEE_ID FROM EMP_PERMISSION WHERE PERMISSION_ID=?";
	private String GET_ONEByPer_STMT = "SELECT PER_NO,PERMISSION_ID,EMPLOYEE_ID FROM EMP_PERMISSION WHERE EMPLOYEE_ID=?";
	private String GET_ALL_STMT = "SELECT * FROM EMP_PERMISSION";
	private String UPDATE_STMT = "UPDATE EMP_PERMISSION SET PERMISSION_ID=?,EMPLOYEE_ID=? WHERE PER_NO=?";

	@Override
	public void insert(EmpPerVO empPerVO) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);

			pstmt.setString(1, empPerVO.getPermission_id());
			pstmt.setString(2, empPerVO.getEmployee_id());

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
	public void update(EmpPerVO empPerVO) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STMT);

			pstmt.setString(2, empPerVO.getEmployee_id());

			pstmt.executeUpdate();

		}  catch (SQLException e) {
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
	public void delete(String permission_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);

			pstmt.setString(1, permission_id);

			pstmt.executeUpdate();

		}  catch (SQLException e) {
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
	public EmpPerVO findByPrimarKey(String permission_id) {
		EmpPerVO empPerVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, permission_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				empPerVO = new EmpPerVO();
				empPerVO.setPermission_id(rs.getString("emp_id"));
				empPerVO.setEmployee_id(rs.getString("emp_accound"));
			}
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

		return empPerVO;
	}

	@Override
	public List<EmpPerVO> getAll() {
		List<EmpPerVO> list = new ArrayList<EmpPerVO>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				EmpPerVO empPerVO = new EmpPerVO();
				empPerVO.setPer_no(rs.getString("per_no"));
				empPerVO.setPermission_id(rs.getString("permission_id"));
				empPerVO.setEmployee_id(rs.getString("employee_id"));
				list.add(empPerVO);
			}
		}  catch (SQLException e) {
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

		return list;
	}

	@Override
	public List<EmpPerVO> findOneEmpPere(String employee_id) {

		List<EmpPerVO> list = new ArrayList<EmpPerVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONEByPer_STMT);
			pstmt.setString(1, employee_id);
			rs = pstmt.executeQuery();
//			empPerVO = new EmpPerVO();
			System.out.println(employee_id);
			while (rs.next()) {
				System.out.println(rs.getString("permission_id"));
				EmpPerVO empPerVO = new EmpPerVO();
				empPerVO.setPer_no(rs.getString("per_no"));
				empPerVO.setPermission_id(rs.getString("permission_id"));
				empPerVO.setEmployee_id(rs.getString("employee_id"));
				list.add(empPerVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			if (conn != null) {
				try {
					conn.close();
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

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}

	public static void main(String[] args) {
//		EmpPerJDBCDAO dao = new EmpPerJDBCDAO();
//		List<EmpPerVO> list = new ArrayList<EmpPerVO>();
//		list = dao.getAll();
//		System.out.println(list.size());

	}

}
