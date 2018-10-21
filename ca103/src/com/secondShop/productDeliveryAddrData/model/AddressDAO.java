package com.secondShop.productDeliveryAddrData.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class AddressDAO{
	
	private static final String ADDRESS_CITY ="SELECT *FROM ADD_CITY"; 
	private static final String ADDRESS_AREA  ="SELECT *FROM ADD_AREA Where CITY_ID = ?"; 
	private static final String ADDRESS_ROAD = "SELECT *FROM ADD_ROAD Where AREA_ID = ?";

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

	public List<CityVO> getCity() {
		List<CityVO> cityList = new ArrayList<>();
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADDRESS_CITY);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				CityVO cityVO = new CityVO();
				cityVO.setCityId(rs.getString("City_Id"));
				cityVO.setCityName(rs.getString("City_Name"));
				cityVO.setCityEngName(rs.getString("City_Eng_Name"));
				cityList.add(cityVO);
			}
		} catch (SQLException e) {
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
		return cityList;
	}
	
	public List<AreaVO> getArea(String cityId){
		List<AreaVO> areaList = new ArrayList<>();

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADDRESS_AREA);
			pstmt.setString(1, cityId);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				AreaVO areaVO = new AreaVO();
				areaVO.setAreaId(rs.getString("Area_Id"));
				areaVO.setAreaName(rs.getString("Area_Name"));
				areaVO.setAreaEngName(rs.getString("Area_Eng_Name"));
				areaList.add(areaVO);
			}
		} catch (SQLException e) {
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
		return areaList;
		
	}
	
	public List<RoadVO> getRoad(String areaId){
		List<RoadVO> roadList = new ArrayList<>();

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(ADDRESS_ROAD);
			pstmt.setString(1, areaId);
			rs= pstmt.executeQuery();
			while(rs.next()) {
				RoadVO roadVO = new RoadVO();
				roadVO.setRoadId(rs.getString("Road_Id"));
				roadVO.setRoadName(rs.getString("Road_Name"));
				roadVO.setRoadEngName(rs.getString("Road_Eng_Name"));
				roadList.add(roadVO);
			}
		} catch (SQLException e) {
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
		return roadList;
		
	}
}
