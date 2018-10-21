package deploy.deploy.fileUpload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductPhotoUpload {
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "CA103G2";
	private static final String PASSWORD = "CA103G2";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	private static final String INSERT_PRODUCT_PHOTO = "INSERT INTO product_photo VALUES('PIC'||lpad(to_char(photo_seq.nextval),3,'0'), ?, ?)";

	public static void main(String[] args) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
			
		File file = new File("src/deploy/sql/pic/"); 	//取得pic2目錄的 file物件
		String[] fileArray = file.list();
		System.out.println(fileArray.length);
		//取得pic2資料夾內所有的檔案名稱,存放在陣列fileArray 中
		List<String> fileArrayList = new ArrayList<>();				//建立一個ArrayList, 等等要放篩選過的名稱
		
		for(String st: fileArray) {									//承接27行 把陣列fileArray內的每個檔案名稱一個一個取出
			//System.out.print(st+", ");							//印出取到的名字
			if(!".DS_Store".equals(st)) {							//如果取到的名字   不是.DS_Store 
				fileArrayList.add(st);								//把這個名字放到集合fileArrayList中
			}
		}															//迴圈跑完之後 集合fileArrayList裡面不會有.DS_Store這個名稱 ,等到45行在做事
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);// 建立連線
			pstmt = con.prepareStatement(INSERT_PRODUCT_PHOTO);
			File productPhoto = null;
			FileInputStream fi = null;
			
			for(String photoName :fileArrayList) {						//拿37行得到的集合fileArrayList, 一個一個拿出來(拿出來的是檔案名稱)
				String[] photoNameCut = photoName.split("-");			//把拿到的檔案名稱用"-"切割,會得到字串陣列 [PRDXXX,X.jpg]
				
				productPhoto = new File("src/deploy/sql/pic/"+photoName);  //利用45行拿到的檔案名稱,建立一個File物件
				fi = new FileInputStream(productPhoto);								     //file 放到inputStream中
				
				//"INSERT INTO product_photo VALUES('PIC'||lpad(to_char(photo_seq.nextval),3,'0'), ?, ?)";
				pstmt.setString(1, photoNameCut[0]);									//46行拿到的陣列 第0個索引資料當作 FK	
				pstmt.setBinaryStream(2, fi, fi.available());							//射圖片進去
				pstmt.executeUpdate();													//更新
				System.out.println("產品："+photoNameCut[0]+" 新增圖片"+photoName+"成功");
			}
			System.out.println("圖片新增完成");
			
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		} finally {
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}
