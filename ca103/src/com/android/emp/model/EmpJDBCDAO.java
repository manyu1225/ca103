package com.android.emp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class EmpJDBCDAO implements EmpMemDAO_interface {
	String driver="oracle.jdbc.driver.OracleDriver";
	String url ="jdbc:oracle:thin:@localhost:1521:XE";
	String user="CA103G2";
	String pw="CA103G2";
	
	String INSERT_STMT = 
			"INSERT INTO EMPLOYEE(EMP_ID,EMP_ACCOUND,EMP_PASSWORD,EMP_FIRSTNAME,EMP_LASTNAME,EMP_PR,EMP_AD,EMP_EMAIL) VALUES('E'||LPAD(to_char(emp_seq.NEXTVAL),6,'0'),?,?,?,?,?,?,?)";
	String UPDATE_STMT = 
			"UPDATE EMPLOYEE SET EMP_ACCOUND=?,EMP_PASSWORD=?,EMP_FIRSTNAME=?,EMP_LASTNAME=?,EMP_PR=?,EMP_AD=?,EMP_EMAIL=? WHERE EMP_ID = ? ";
	String GET_ONE_STMT = 
			"SELECT EMP_ID,EMP_ACCOUND,EMP_PASSWORD,EMP_FIRSTNAME,EMP_LASTNAME,EMP_PR,EMP_AD,EMP_EMAIL FROM EMPLOYEE WHERE EMP_ID =?";
	String GET_ALL_STMT =
			"SELECT EMP_ACCOUND,EMP_PASSWORD,EMP_FIRSTNAME,EMP_LASTNAME,EMP_PR,EMP_AD,EMP_EMAIL FROM EMPLOYEE order by  EMP_ID";
	String DELETE_STMT="DELETE FROM EMPLOYEE WHERE EMP_ID";
	@Override
	public void insert(EmpMemVO empMemVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1,empMemVO.getEmp_accound());
			pstmt.setString(2,empMemVO.getEmp_password());
			pstmt.setString(3,empMemVO.getEmp_firstname());
			pstmt.setString(4,empMemVO.getEmp_lastname());
			pstmt.setInt(5,empMemVO.getEmp_pr());
			pstmt.setString(6,empMemVO.getEmp_ad());
			pstmt.setString(7,empMemVO.getEmp_email());
			
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void update(EmpMemVO empMemVO) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(UPDATE_STMT);
			
			pstmt.setString(1,empMemVO.getEmp_accound());
			pstmt.setString(2,empMemVO.getEmp_password());
			pstmt.setString(3,empMemVO.getEmp_firstname());
			pstmt.setString(4,empMemVO.getEmp_lastname());
			pstmt.setInt(5,empMemVO.getEmp_pr());
			pstmt.setString(6,empMemVO.getEmp_ad());
			pstmt.setString(7,empMemVO.getEmp_email());
			pstmt.setString(8, empMemVO.getEmp_id());
			
			pstmt.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException e) {
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
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(DELETE_STMT);
			
			pstmt.setString(1,"emp_id");
			
			pstmt.executeUpdate();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public EmpMemVO findByPrimarKey(String emp_id) {
		
		EmpMemVO empMemVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(GET_ONE_STMT);
			pstmt.setString(1,emp_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				empMemVO = new EmpMemVO();
				empMemVO.setEmp_id(rs.getNString("emp_id"));
				empMemVO.setEmp_accound(rs.getString("emp_accound"));
				empMemVO.setEmp_firstname(rs.getString("emp_firstname"));
				empMemVO.setEmp_lastname(rs.getString("emp_lastname"));
				empMemVO.setEmp_pr(rs.getInt("emp_pr"));
				empMemVO.setEmp_ad(rs.getString("emp_ad"));
				empMemVO.setEmp_ad(rs.getString("e"));
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return empMemVO;
	}

	@Override
	public List<EmpMemVO> getAll() {
		List<EmpMemVO> list = new ArrayList<EmpMemVO>();
		EmpMemVO empMemVO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, pw);
			pstmt = conn.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				empMemVO = new EmpMemVO();
				empMemVO.setEmp_accound(rs.getString("emp_accound"));
				empMemVO.setEmp_firstname(rs.getString("emp_firstname"));
				empMemVO.setEmp_lastname(rs.getString("emp_lastname"));
				empMemVO.setEmp_pr(rs.getInt("emp_pr"));
				empMemVO.setEmp_ad(rs.getString("emp_ad"));
				list.add(empMemVO);
			}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e ) {
			e.printStackTrace();
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		return list;
	}

	public static void main(String[] args) {
		EmpJDBCDAO dao = new EmpJDBCDAO();
		//�s�W
//		EmpMemVO empMemVO1 = new EmpMemVO();
//		empMemVO1.setEmp_accound("a1");
//		empMemVO1.setEmp_password("a3");
//		empMemVO1.setEmp_firstname("yang");
//		empMemVO1.setEmp_lastname("YUNG_LING");
//		empMemVO1.setEmp_pr(5);
//		empMemVO1.setEmp_ad("��");
//		empMemVO1.setEmp_email("123@gmail.com");
//		dao.insert(empMemVO1);
//		
		//�ק�
//		EmpMemVO empMemVO2 = new EmpMemVO();
//		empMemVO2.setEmp_id("E000002");
//		empMemVO2.setEmp_accound("��s���\");
//		empMemVO2.setEmp_password("��s���\");
//		empMemVO2.setEmp_firstname("��s���\");
//		empMemVO2.setEmp_lastname("��s���\");
//		empMemVO2.setEmp_pr(8);
//		empMemVO2.setEmp_ad("��s���\");
//		empMemVO2.setEmp_email("��s���\");
//		dao.update(empMemVO2);
//		
		//�R��
		
		//�d��
//		EmpMemVO empMemVO3 = new EmpMemVO();
//		EmpMemVO empMemVO3 = dao.findByPrimarKey("E000003");
//		System.out.print(empMemVO3.getEmp_accound() + ",");
//		System.out.print(empMemVO3.getEmp_password() + ",");
//		System.out.print(empMemVO3.getEmp_firstname() + ",");
//		System.out.print(empMemVO3.getEmp_lastname() + ",");
//		System.out.print(empMemVO3.getEmp_email() + ",");
//		System.out.print(empMemVO3.getEmp_ad() + ",");
//		System.out.println("---------------------");
		
		//�d�ߥ���
		List<EmpMemVO> list = dao.getAll();
		for (EmpMemVO empAll : list) {
			System.out.print(empAll.getEmp_accound() + ",");
			System.out.print(empAll.getEmp_password() + ",");
			System.out.print(empAll.getEmp_firstname() + ",");
			System.out.print(empAll.getEmp_lastname() + ",");
			System.out.print(empAll.getEmp_email() + ",");
			System.out.print(empAll.getEmp_ad() + ",");
			System.out.println();
		}
	}

}
