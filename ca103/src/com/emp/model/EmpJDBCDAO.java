package com.emp.model;

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

public class EmpJDBCDAO implements EmpDAO_interface {
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

	String INSERT_STMT = "INSERT INTO EMPLOYEE(EMP_ID,EMP_ACCOUNT,EMP_PASSWORD,EMP_FIRSTNAME,EMP_LASTNAME,EMP_PR,EMP_AD,EMP_EMAIL) VALUES('E'||LPAD(to_char(emp_seq.NEXTVAL),6,'0'),?,?,?,?,?,?,?)";
	String UPDATE_STMT = "UPDATE EMPLOYEE SET EMP_ACCOUNT=?,EMP_PASSWORD=?,EMP_FIRSTNAME=?,EMP_LASTNAME=?,EMP_PR=?,EMP_AD=?,EMP_EMAIL=? WHERE EMP_ID = ? ";
	String UPDATE_Master_STMT = "UPDATE EMPLOYEE SET EMP_FIRSTNAME=?,EMP_LASTNAME=?,EMP_AD=?,EMP_EMAIL=?,EMP_PR=? WHERE EMP_ID =?";
	String GET_ONE_STMT = "SELECT EMP_ID,EMP_ACCOUNT,EMP_PASSWORD,EMP_FIRSTNAME,EMP_LASTNAME,EMP_PR,EMP_AD,EMP_EMAIL FROM EMPLOYEE WHERE EMP_ACCOUNT =?";
	String GET_ONE_BYID_STMT = "SELECT EMP_ID,EMP_ACCOUNT,EMP_PASSWORD,EMP_FIRSTNAME,EMP_LASTNAME,EMP_PR,EMP_AD,EMP_EMAIL FROM EMPLOYEE WHERE EMP_ID =?";
	String GET_ALL_STMT = "SELECT EMP_ID,EMP_ACCOUNT,EMP_PASSWORD,EMP_FIRSTNAME,EMP_LASTNAME,EMP_PR,EMP_AD,EMP_EMAIL FROM EMPLOYEE order by EMP_ID";
	String DELETE_STMT = "DELETE FROM EMPLOYEE WHERE EMP_ID = ?";

	@Override
	public void insert(EmpVO empVO) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(INSERT_STMT);

			pstmt.setString(1, empVO.getEmp_account());
			pstmt.setString(2, empVO.getEmp_password());
			pstmt.setString(3, empVO.getEmp_firstname());
			pstmt.setString(4, empVO.getEmp_lastname());
			pstmt.setString(5, empVO.getEmp_pr());
			pstmt.setString(6, empVO.getEmp_ad());
			pstmt.setString(7, empVO.getEmp_email());

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
	public void update(EmpVO empVO) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_STMT);

			pstmt.setString(1, empVO.getEmp_account());
			pstmt.setString(2, empVO.getEmp_password());
			pstmt.setString(3, empVO.getEmp_firstname());
			pstmt.setString(4, empVO.getEmp_lastname());
			pstmt.setString(5, empVO.getEmp_pr());
			pstmt.setString(6, empVO.getEmp_ad());
			pstmt.setString(7, empVO.getEmp_email());
			pstmt.setString(8, empVO.getEmp_id());

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
	public void updateByMaster(EmpVO empVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(UPDATE_Master_STMT);
			System.out.println("DAO有進來");
			pstmt.setString(1, empVO.getEmp_firstname());
			System.out.println(empVO.getEmp_firstname());
			pstmt.setString(2, empVO.getEmp_lastname());
			System.out.println(empVO.getEmp_lastname());
			pstmt.setString(3, empVO.getEmp_ad());
			System.out.println(empVO.getEmp_ad());
			pstmt.setString(4, empVO.getEmp_email());
			System.out.println(empVO.getEmp_email());
			pstmt.setString(5, empVO.getEmp_pr());
			System.out.println(empVO.getEmp_pr());
			pstmt.setString(6, empVO.getEmp_id());
			System.out.println(empVO.getEmp_id());
			pstmt.executeUpdate();
			
			System.out.println("修改完畢");

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
	public void delete(String emp_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(DELETE_STMT);

			pstmt.setString(1, "emp_id");

			pstmt.executeUpdate();
			System.out.println("刪除員工成功");
		}  catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public EmpVO findOneById(String emp_id) {
		EmpVO empVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_BYID_STMT);
			pstmt.setString(1, emp_id);
			rs = pstmt.executeQuery();
			empVO = new EmpVO();

			while (rs.next()) {
				System.out.println("有進去");
				empVO.setEmp_id(rs.getString("emp_id"));
				empVO.setEmp_account(rs.getString("emp_account"));
				empVO.setEmp_password(rs.getString("emp_password"));
				empVO.setEmp_firstname(rs.getString("emp_firstname"));
				empVO.setEmp_lastname(rs.getString("emp_lastname"));
				empVO.setEmp_pr(rs.getString("emp_pr"));
				empVO.setEmp_ad(rs.getString("emp_ad"));
				empVO.setEmp_email(rs.getString("emp_email"));
			}
			
		}  catch (SQLException e) {
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

		return empVO;
	}

	@Override
	public EmpVO findByPrimarKey(String emp_account) {

		EmpVO empVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
//			Class.forName(driver);
//			conn = DriverManager.getConnection(url, user, pw);
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1, emp_account);
			rs = pstmt.executeQuery();
			empVO = new EmpVO();

			while (rs.next()) {
				System.out.println("DAO有進來");
				empVO.setEmp_id(rs.getString("emp_id"));
				empVO.setEmp_account(rs.getString("emp_account"));
				empVO.setEmp_password(rs.getString("emp_password"));
				empVO.setEmp_firstname(rs.getString("emp_firstname"));
				empVO.setEmp_lastname(rs.getString("emp_lastname"));
				empVO.setEmp_pr(rs.getString("emp_pr"));
				empVO.setEmp_ad(rs.getString("emp_ad"));
				empVO.setEmp_email(rs.getString("emp_email"));
				System.out.println("資料庫抓來的" + empVO.getEmp_password());
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

		return empVO;
	}

	@Override
	public List<EmpVO> getAll() {
		List<EmpVO> list = new ArrayList<EmpVO>();
		EmpVO empVO = null;
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
				empVO = new EmpVO();
				empVO.setEmp_id(rs.getString("emp_id"));
				empVO.setEmp_account(rs.getString("emp_account"));
				empVO.setEmp_firstname(rs.getString("emp_firstname"));
				empVO.setEmp_lastname(rs.getString("emp_lastname"));
				empVO.setEmp_pr(rs.getString("emp_pr"));
				empVO.setEmp_ad(rs.getString("emp_ad"));
				empVO.setEmp_email(rs.getString("emp_email"));
				list.add(empVO);
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

	public static void main(String[] args) {
		
//		EmpVO empVO = new EmpVO();
//		EmpJDBCDAO dao = new EmpJDBCDAO();
//		
//		empVO = dao.findByPrimarKey("kans1234");
//		System.out.println(empVO.getEmp_ad());

	}

	

	

}
