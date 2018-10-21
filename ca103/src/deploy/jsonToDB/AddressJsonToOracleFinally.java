package deploy.jsonToDB;


import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AddressJsonToOracleFinally {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "CA103G2";
	private static final String PASSWORD = "CA103G2";
	private static final String SQL_ADD_CITY = 
			"INSERT INTO add_city VALUES('CT'||lpad(to_char(seq_add_city.nextval),2,'0'), ?, ?)";
	private static final String SQL_ADD_AREA = 
			"INSERT INTO add_area VALUES('AR'||lpad(to_char(seq_add_area.nextval),5,'0'), ?, ?, ? ,?)";
	private static final String SQL_ADD_ROAD = 
			"INSERT INTO add_road VALUES('RD'||lpad(to_char(seq_add_row.nextval),8,'0'), ?, ?,?)";

	public static void main(String[] args) throws Exception {
		JSONParser parser = new JSONParser();
		FileReader fr;
		Object obj;
		JSONObject jsonObject = null;
		JSONArray jsonArrayCity=null, jsonArrayArea=null, jsonArrayRoad=null;
		String cityName, cityEngName, zipCode, areaName, areaEngName, roadName, roadEngName;
		String[] cityIds={"city_id"},areaIds={"area_id"};
		ResultSet cityIdKeys,areaIdKeys;
		String cityId,areaId;
		Long cityNum=0L,areaNum=0L,roadNum=0L,areaNumTotal=0L,roadNumTotal=0L;
		try {
			fr = new FileReader("WebContent/sources/file/secondShop/AllData.json");
			// fr = new FileReader("file/test.text");
			obj = parser.parse(fr);

			jsonArrayCity = (JSONArray) obj;

		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		System.out.println(jsonArrayCity.size());

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Long sDateLong = new Date().getTime();
			
			for (int i = 0; i < jsonArrayCity.size(); i++) {
				cityNum++;
				con = DriverManager.getConnection(URL, USER, PASSWORD);
				
				jsonObject = (JSONObject) jsonArrayCity.get(i);

				cityName = (String) jsonObject.get("CityName");
				cityEngName = (String) jsonObject.get("CityEngName");

				pstmt = con.prepareStatement(SQL_ADD_CITY, cityIds);
				pstmt.setString(1, cityName);
				pstmt.setString(2, cityEngName);
				pstmt.executeUpdate();
				cityIdKeys = pstmt.getGeneratedKeys();
				cityIdKeys.next();
				cityId = cityIdKeys.getString(1);
//con.close();
				
				jsonArrayArea = (JSONArray) jsonObject.get("AreaList");
				for (int j = 0; j < jsonArrayArea.size(); j++) {
					areaNum++;
//con = DriverManager.getConnection(URL, USER, PASSWORD);
					
					jsonObject = (JSONObject) jsonArrayArea.get(j);

					zipCode = (String) jsonObject.get("ZipCode");
					areaName = (String) jsonObject.get("AreaName");
					areaEngName = (String) jsonObject.get("AreaEngName");
					
					pstmt=con.prepareStatement(SQL_ADD_AREA, areaIds);
					
					pstmt.setString(1, zipCode);
					pstmt.setString(2, areaName);
					pstmt.setString(3, areaEngName);
					pstmt.setString(4, cityId);
					pstmt.executeUpdate();
					areaIdKeys = pstmt.getGeneratedKeys();
					areaIdKeys.next();
					areaId=areaIdKeys.getString(1);
					con.close();
							
					con = DriverManager.getConnection(URL, USER, PASSWORD);
					int count  = 0 ;
					jsonArrayRoad = (JSONArray) jsonObject.get("RoadList");
					for (int k = 0; k < jsonArrayRoad.size(); k++) {
						roadNum++;
						
						jsonObject = (JSONObject) jsonArrayRoad.get(k);
						
						roadName = (String) jsonObject.get("RoadName");
						roadEngName = (String) jsonObject.get("RoadEngName");
						
						pstmt=con.prepareStatement(SQL_ADD_ROAD);
						pstmt.setString(1, roadName);
						pstmt.setString(2, roadEngName);
						pstmt.setString(3, areaId);
						pstmt.executeUpdate();
						

						count++;
						if(count>250) {
							count=0;
							con.close();
							con = DriverManager.getConnection(URL, USER, PASSWORD);
						}
					}
					System.out.println(zipCode+"-"+areaName+" add roadNum:"+roadNum );
					roadNumTotal+=roadNum;
					roadNum=0L;
				}
				System.out.println(cityName+" add areaNum:"+areaNum );
				areaNumTotal+=areaNum;
				areaNum=0L;
			}
			System.out.println("cityNum:"+cityNum+" areaNumTotal:"+areaNumTotal+" roadNumTotal:"+roadNumTotal);

			System.out.println("finish~~");
			
			Long eDateLong = new Date().getTime();
			Long durationInMillis = eDateLong-sDateLong;
			
			long millis = durationInMillis % 1000;
			long second = (durationInMillis / 1000) % 60;
			long minute = (durationInMillis / (1000 * 60)) % 60;
			long hour = (durationInMillis / (1000 * 60 * 60)) % 24;

			String time = String.format("%02d:%02d:%02d.%d", hour, minute, second, millis);
			System.out.println(durationInMillis);
			System.out.println(time);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
