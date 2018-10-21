package com.joined_gp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.gp.model.GPVO;
import com.mem.model.MemVO;

public class JoinedGPJNDIDAO implements JoinedGPDAO_interface{

	private static final String INSERT_STMT = "insert into JOINED_GP(MEM_ID,GP_ID,JOIN_TIME,PMSN_SETTING,CHK_TIME,CHK_STATUS)" + 
			"values (?,?,?,?,?,?)";
	private static final String Find_MEM_BY_GP = "select MEMBER.* from MEMBER join JOINED_GP on MEMBER.MEM_ID = JOINED_GP.MEM_ID where JOINED_GP.GP_ID=?";
	private static final String Find_JGP = "select GP.* from GP join JOINED_GP on GP.GP_ID = JOINED_GP.GP_ID where JOINED_GP.MEM_ID = ? AND GP_STATUS!=10 ORDER BY gp_status,gp_date";
	private static final String DELETE_JGP = "DELETE FROM JOINED_GP WHERE MEM_ID = ? and GP_ID = ?"; 
	private static final String UPDATE_CHK_STMT = "update JOINED_GP set PMSN_SETTING = ?,CHK_TIME = ? where MEM_ID = ? and GP_ID = ?";
	private static final String FIND_GP_NUM = "select COUNT(*) from JOINED_GP where gp_id = ?";
	
	private static final String FIND_PUBLIC_JGP = "select GP.* from GP join JOINED_GP on GP.GP_ID = JOINED_GP.GP_ID where JOINED_GP.MEM_ID = ? AND gp_status=0 AND pub_set=0";
	
	private static final String FIND_JGPVO_BY_PK = "select * from JOINED_GP  where gp_id=? AND mem_id=?";
	
	private static final String UPDATE_STATUS = "UPDATE JOINED_GP set PMSN_SETTING=? where gp_id=? AND mem_id=?";
	
	// 一個應用程式中,針對一個資料庫 ,共用一個DataSource即可
		private static DataSource ds = null;
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public int findNumberOfGP(GPVO gpVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;;
			int count = 0;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(FIND_GP_NUM);
				pstmt.setString(1,gpVO.getGp_id());
				rs = pstmt.executeQuery();
				rs.next();
				count = rs.getInt(1);
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
			return count;
		}
		
		
		
		@Override
		public void add(JoinedGPVO jgpVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(INSERT_STMT);
				setPstmt1(pstmt,jgpVO);
				pstmt.executeUpdate();
			}catch (SQLException se) {
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
		public void delete(JoinedGPVO jgpVO) {
			Connection con = null;
			PreparedStatement pstmt = null;

			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(DELETE_JGP);

				pstmt.setString(1, jgpVO.getMem_id());
				pstmt.setString(2, jgpVO.getGp_id());
				pstmt.executeUpdate();
				
				// Handle any driver errors
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
		
		@Override
		public void updateStatus(JoinedGPVO jgpVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_STATUS);
				pstmt.setInt(1, jgpVO.getPmsn_setting());
				pstmt.setString(2, jgpVO.getGp_id());
				pstmt.setString(3, jgpVO.getMem_id());
				pstmt.executeUpdate();
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
		public void updateChk(JoinedGPVO jgpVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(UPDATE_CHK_STMT);	
				
				pstmt.setInt(1,jgpVO.getPmsn_setting());
				pstmt.setTimestamp(2,jgpVO.getChk_time());
//				pstmt.setInt(4,jgpVO.getChk_status());
				pstmt.setString(3,jgpVO.getMem_id());
				pstmt.setString(4,jgpVO.getGp_id());
				
				pstmt.executeUpdate();
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
		public List<MemVO> SearchMemberByGP(GPVO gpVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;;
			List<MemVO> list = new ArrayList<MemVO>();
			MemVO memVO = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(Find_MEM_BY_GP);
				pstmt.setString(1,gpVO.getGp_id());
				rs = pstmt.executeQuery();
				while(rs.next()) {
					memVO = new MemVO();
					setmemVO(memVO,rs);
					list.add(memVO);
				}
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
		public List<GPVO> SearchJoinedGPByMember(MemVO memVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			GPVO gpVO = null;
			List<GPVO> list = new ArrayList<GPVO>();
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(Find_JGP);
				pstmt.setString(1, memVO.getMem_id());
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					gpVO = new GPVO();
					setgpVO(gpVO,rs);
					list.add(gpVO);
				}
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
		public List<GPVO> SearchPublicJGP(MemVO memberVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			GPVO gpVO = null;
			List<GPVO> list = new ArrayList<GPVO>();
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(FIND_PUBLIC_JGP);
				pstmt.setString(1, memberVO.getMem_id());
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					gpVO = new GPVO();
					setgpVO(gpVO,rs);
					list.add(gpVO);
				}
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

		
//		private void setjgpVO(JoinedGPVO jGPVO,ResultSet rs) throws SQLException {
//			jGPVO.setMem_id(rs.getString(1));
//			jGPVO.setGp_id(rs.getString(2));
//			jGPVO.setJoin_time(rs.getTimestamp(3));
//			jGPVO.setPmsn_setting(rs.getInt(4));
//			jGPVO.setChk_time(rs.getTimestamp(5));
//			jGPVO.setChk_status(rs.getInt(6));
//		}
		private void setmemVO(MemVO memVO,ResultSet rs) throws SQLException {
//			memVO.setMem_id(rs.getString(1));
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
		}
		private void setPstmt1(PreparedStatement pstmt,JoinedGPVO jGPVO) throws SQLException {
			pstmt.setString(1,jGPVO.getMem_id());
			pstmt.setString(2,jGPVO.getGp_id());
			pstmt.setTimestamp(3,jGPVO.getJoin_time());
			pstmt.setInt(4,jGPVO.getPmsn_setting());
			pstmt.setTimestamp(5,jGPVO.getChk_time());
			pstmt.setInt(6,jGPVO.getChk_status());
		}

		@Override
		public JoinedGPVO SearchjGPVOByPK(MemVO memVO, GPVO gpVO) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;;
			JoinedGPVO jgpVO = null;
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(FIND_JGPVO_BY_PK);
				pstmt.setString(1,gpVO.getGp_id());
				pstmt.setString(2,memVO.getMem_id());
				rs = pstmt.executeQuery();
				if(rs.next()) {
					jgpVO = new JoinedGPVO(rs.getString("mem_id"), rs.getString("gp_id"), rs.getTimestamp("join_time"),
							rs.getInt("pmsn_setting"), rs.getTimestamp("chk_time"), rs.getInt("chk_status"));
				}
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
			return jgpVO;
		}
}
