package com.forPos.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.forPos_pic.model.ForPost_pic_Service;
import com.forPos_pic.model.ForPost_picture_DAO;
import com.forPos_pic.model.ForPost_picture_VO;

public class Forum_post_DAO implements Forum_post_DAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CA103G2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO FORUM_POST"
			+ "(FORPOST_ID, MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, FORPOST_CONTENT,FORPOST_THEME,FORPOST_STATE)"
			+ "values(FORUM_POST_SEQ.NEXTVAL, ?, ?,  current_TIMESTAMP, 0, ?, ?, ?)";
	
	private static final String GET_POS_BY_MEM=
			"SELECT FORPOST_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE "
			+ "FROM FORUM_POST WHERE MEM_ID=?";
	
	
//	private static final String GET_NEW_POST = 
//			"SELECT  FORPOST_ID MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE "
//					+ "FROM FORUM_POST order by FORPOST_ID";
			
	
	private static final String GET_ALL_STMT = "SELECT FORPOST_ID, MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, "
			+ "FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE FROM FORUM_POST ORDER BY forPost_time desc";
	
	
	private static final String GET_ALL_STMT_BY_STATE = "SELECT FORPOST_ID, MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, "
			+ "FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE FROM FORUM_POST WHERE FORPOST_STATE = ? ORDER BY FORPOST_ID desc";
	
	
	

	private static final String GET_ONE_STMT = "SELECT FORPOST_ID, MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, "
			+ "FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE FROM FORUM_POST WHERE FORPOST_ID = ?";
	
	
	private static final String GET_POS_IN_STATE = "SELECT FORPOST_ID, MEM_ID, FORCLASS_ID, FORPOST_TIME, FORPOST_VIEW, "
			+ "FORPOST_CONTENT, FORPOST_THEME, FORPOST_STATE FROM FORUM_POST WHERE FORPOST_ID = ? MEM_ID=?"; //被作者設為文章不公開

	

	private static final String DELETE = "DELETE FROM FORUM_POST WHERE FORPOST_ID = ?";

	private static final String UPDATE = "UPDATE FORUM_POST SET  FORCLASS_ID = ? , FORPOST_TIME=current_TIMESTAMP , "
			 + "FORPOST_CONTENT = ?, FORPOST_THEME = ? ,FORPOST_STATE = ? where FORPOST_ID = ?";
	
	
	private static final String GET_VIEW_NUM = "UPDATE FORUM_POST SET FORPOST_VIEW= (FORPOST_VIEW+1) WHERE FORPOST_ID=?";
	
//	數據統計---
	private static final String GET_ALL_FORPOS_VIEW = "SELECT * FROM FORUM_POST ORDER BY FORPOST_TIME DESC";
//	private static final String GET_VIEW_BY_DATE= "select * from forum_post where to_char(forPost_time,'yyyy-mm-dd') between ? and ?  order by forPost_id";
	private static final String GET_VIEW_BY_DATE= "select forPost_view from forum_post where to_char(forPost_time,'yyyy-mm-dd')=? ";
//-----------------------
	
	//檢舉
	private static final String GET_ALL = "SELECT * FROM FORUM_POST ";
	private static final String CLOSE = "UPDATE FORUM_POST SET FORPOST_STATE = ? WHERE FORPOST_ID = ?"; //被下架


	@Override
	public void insert(Forum_post_VO forPostVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;


	
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			
			pstmt.setString(1, forPostVO.getMem_ID());
			pstmt.setString(2, forPostVO.getForClass_ID());
			pstmt.setString(3, forPostVO.getForPost_content());
			pstmt.setString(4, forPostVO.getForPost_theme());
			pstmt.setInt(5, forPostVO.getForPost_state());

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

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}

	}

	@Override
	public void update(Forum_post_VO forPostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
//			pstmt.setString(1, forPostVO.getMem_ID());
			pstmt.setString(1, forPostVO.getForClass_ID());
//			pstmt.setTimestamp(2, forPostVO.getForPost_time());
			pstmt.setString(2, forPostVO.getForPost_content());
			pstmt.setString(3, forPostVO.getForPost_theme());
			pstmt.setInt(4, forPostVO.getForPost_state());
			pstmt.setInt(5, forPostVO.getForPost_ID());


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

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}

	}
	
	
	
	
	@Override
	public void updateView(Forum_post_VO forPostVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_VIEW_NUM);
//			pstmt.setInt(1, forPostVO.getForPost_view());
			pstmt.setInt(1, forPostVO.getForPost_ID());


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

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}

		}

	}
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public void delete(Integer forPost_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, forPost_ID);

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

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public Forum_post_VO findByPrimaryKey(Integer forPost_id) {
		Forum_post_VO forum_post_vo = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			pstmt.setInt(1, forPost_id);
			rs = pstmt.executeQuery();
		
			
			while (rs.next()) {
				forum_post_vo = new Forum_post_VO();
				forum_post_vo.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_vo.setMem_ID(rs.getString("mem_ID"));
				forum_post_vo.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_vo.setForPost_time(rs.getTimestamp("forPost_time"));
				forum_post_vo.setForPost_view(rs.getInt("forPost_view"));
				forum_post_vo.setForPost_content(rs.getString("forPost_content"));
				forum_post_vo.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_vo.setForPost_state(rs.getInt("forPost_state"));

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

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
		return forum_post_vo;

	}
	
	@Override
	public List<Forum_post_VO> findPostByMem_ID(String mem_ID) {
		Forum_post_VO forum_post_vo = null;
		List<Forum_post_VO> list = new ArrayList<Forum_post_VO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_POS_BY_MEM);
			pstmt.setString(1, mem_ID);
			rs = pstmt.executeQuery();
		
			
			while (rs.next()) {
				forum_post_vo = new Forum_post_VO();
				forum_post_vo.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_vo.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_vo.setForPost_time(rs.getTimestamp("forPost_time"));
				forum_post_vo.setForPost_view(rs.getInt("forPost_view"));
				forum_post_vo.setForPost_content(rs.getString("forPost_content"));
				forum_post_vo.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_vo.setForPost_state(rs.getInt("forPost_state"));
				list.add(forum_post_vo);

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

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
		return list;

	}
	
	
	
//	******************************(x)設定文章給特定權限的會員
	
	@Override
	public Forum_post_VO getPostInState(Integer forPost_ID, String mem_ID) {
		Forum_post_VO forum_post_vo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_POS_IN_STATE);
			pstmt.setInt(1, forPost_ID);
			pstmt.setString(2, mem_ID);
			rs = pstmt.executeQuery();
		
			
			while (rs.next()) {
				forum_post_vo = new Forum_post_VO();
				forum_post_vo.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_vo.setForPost_ID(rs.getInt("mem_ID"));
				forum_post_vo.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_vo.setForPost_time(rs.getTimestamp("forPost_time"));
				forum_post_vo.setForPost_view(rs.getInt("forPost_view"));
				forum_post_vo.setForPost_content(rs.getString("forPost_content"));
				forum_post_vo.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_vo.setForPost_state(rs.getInt("forPost_state"));

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

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}
		return forum_post_vo;

	}
	
	
//	*****************設公開才能在文章列表顯示***********************
	
	
	@Override
	public List<Forum_post_VO> getAllByState(Integer forPost_state) {
		List<Forum_post_VO> list = new ArrayList<Forum_post_VO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT_BY_STATE);
			pstmt.setInt(1, forPost_state);
			rs = pstmt.executeQuery();
		

			while (rs.next()) {
				Forum_post_VO forum_post_VO = new Forum_post_VO();
				forum_post_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_VO.setMem_ID(rs.getString("mem_ID"));
				forum_post_VO.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_VO.setForPost_time(rs.getTimestamp("forPost_time"));
				forum_post_VO.setForPost_view(rs.getInt("forPost_view"));
				forum_post_VO.setForPost_content(rs.getString("forPost_content"));
				forum_post_VO.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_VO.setForPost_state(rs.getInt("forPost_state"));
				list.add(forum_post_VO);
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	@Override
	public List<Forum_post_VO> getAll() {
		List<Forum_post_VO> list = new ArrayList<Forum_post_VO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Forum_post_VO forum_post_VO = new Forum_post_VO();
				forum_post_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_VO.setMem_ID(rs.getString("mem_ID"));
				forum_post_VO.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_VO.setForPost_time(rs.getTimestamp("forPost_time"));
				forum_post_VO.setForPost_view(rs.getInt("forPost_view"));
				forum_post_VO.setForPost_content(rs.getString("forPost_content"));
				forum_post_VO.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_VO.setForPost_state(rs.getInt("forPost_state"));
				list.add(forum_post_VO);
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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	
	@Override
	public List<Forum_post_VO> getReportAll() {
		List<Forum_post_VO> list = new ArrayList<Forum_post_VO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Forum_post_VO forum_post_VO = new Forum_post_VO();
				forum_post_VO.setForPost_ID(rs.getInt("forPost_ID"));
				forum_post_VO.setMem_ID(rs.getString("mem_ID"));
				forum_post_VO.setForClass_ID(rs.getString("forClass_ID"));
				forum_post_VO.setForPost_time(rs.getTimestamp("forPost_time"));
				forum_post_VO.setForPost_view(rs.getInt("forPost_view"));
				forum_post_VO.setForPost_content(rs.getString("forPost_content"));
				forum_post_VO.setForPost_theme(rs.getString("forPost_theme"));
				forum_post_VO.setForPost_state(rs.getInt("forPost_state"));
				list.add(forum_post_VO);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (rs != null) {try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
			if (con != null) {try {con.close();} catch (SQLException e) {e.printStackTrace();}}
		}
		return list;
	}

	//******************************************************************************
	@Override
	public void closeforPos(Integer forPost_ID, Integer forPos_rep_state) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(CLOSE);
			pstmt.setInt(1, forPos_rep_state);
			pstmt.setInt(2, forPost_ID);
			pstmt.executeUpdate();

			
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
			if (con != null) {try {con.close();} catch (SQLException e) {e.printStackTrace();}}
		}
	}

//	*******************************文章狀態**************************************
	
	
	@Override
	public List <Forum_post_VO> getViewByDate(String date) {
		List<Forum_post_VO> list = new ArrayList<Forum_post_VO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Forum_post_VO forum_post_VO= null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_VIEW_BY_DATE);
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			


			while (rs.next()) {
				forum_post_VO = new Forum_post_VO();
				forum_post_VO.setForPost_view(rs.getInt("forPost_view"));
				list.add(forum_post_VO);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (rs != null) {try {rs.close();} catch (SQLException e) {e.printStackTrace();}}
			if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}}
			if (con != null) {try {con.close();} catch (SQLException e) {e.printStackTrace();}}
		}
		return list;
	}

	@Override
	public void insertAndphoto(Forum_post_VO forPostVO, ForPost_picture_VO forPost_pictureVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println("dao我來上傳照片囉");

	
		try {
			con = ds.getConnection();
			String[] picId = {"FORPOST_ID"};
			Integer next_picId = null;
			pstmt = con.prepareStatement(INSERT_STMT,picId);
			con.setAutoCommit(false);
			pstmt.setString(1, forPostVO.getMem_ID());
			pstmt.setString(2, forPostVO.getForClass_ID());
			pstmt.setString(3, forPostVO.getForPost_content());
			pstmt.setString(4, forPostVO.getForPost_theme());
			pstmt.setInt(5, forPostVO.getForPost_state());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();// 取得對應的自增主鍵值
			rs.next();
			next_picId = rs.getInt(1);//把自增主鍵值存入next_orderid
			ForPost_picture_DAO ForPost_picture_DAO = new ForPost_picture_DAO();
			forPost_pictureVO.setForPost_ID(next_picId);
			forPost_pictureVO.setForPostPic(forPost_pictureVO.getForPostPic());
			System.out.println("675POSTDAo");
			ForPost_picture_DAO.insert(forPost_pictureVO, con);
//			pstmt.executeUpdate();
			System.out.println("678commit前");
			con.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			if(con!=null){
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {

			if (pstmt != null) {

				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}

			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	

	
}
