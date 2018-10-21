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

public class ProductPhotoUpload2 {
	// private static final String URL = "jdbc:oracle:thin:@192.168.56.101:1521:xe";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "CA103G2";
	private static final String PASSWORD = "CA103G2";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	private static final String INSERT_PRODUCT_PHOTO = "INSERT INTO product_photo VALUES('PIC'||lpad(to_char(photo_seq.nextval),3,'0'), ?, ?)";

	public static void main(String[] args){
		Connection con = null;
		PreparedStatement pstmt = null;
		String dirName = "";
		String productName = "";
		
		File file = new File("/Users/lili/Desktop/product/pic"); //抓取預設圖片的根目錄 pic
		File[] productlist = file.listFiles();  //抓取pic這個目錄下的 子目錄或檔案 ,存放在File陣列productlist中 PRD001~PRO010
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASSWORD);// 建立連線
			pstmt = con.prepareStatement(INSERT_PRODUCT_PHOTO);

			for (File productDir : productlist) {  		//從productlist陣列中 依序把file物件取出,命名為productDir物件  (會不小心取到系統的檔案 .DSxxxx)
				File[] productPhoto = productDir.listFiles();   //  抓取 productDir物件 目錄下的 子目錄或檔案 ,存放在File陣列productlist中 PRD001~PRO010

				if (!productDir.isFile()) {
					dirName = productDir.getName(); // dirName = ProuctId

					for (File prductPhoto : productPhoto) {
						productName = prductPhoto.getName();
						FileInputStream fis = new FileInputStream(prductPhoto);

						pstmt.setString(1, dirName);
						pstmt.setBinaryStream(2, fis, fis.available());
						pstmt.executeUpdate();

						System.out.println("產品:" + dirName + " 新增圖片" + productName + " 新增成功");
					}
				}
			}
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
