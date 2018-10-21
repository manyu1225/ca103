/*
 *  1. 萬用複合查詢-可由客戶端隨意增減任何想查詢的欄位
 *  2. 為了避免影響效能:
 *        所以動態產生萬用SQL的部份,本範例無意採用MetaData的方式,也只針對個別的Table自行視需要而個別製作之
 * */


package jdbc.util.CompositeQuery;

import java.util.*;

public class jdbcUtil_CompositeQuery_Activity {

	public static String get_aCondition_For_Oracle(String columnName, String value) {

		String aCondition = null;

		if ("ACT_SDATE".equals(columnName) || "ACT_EDATE".equals(columnName)) // 用於日期
			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')='" + value + "'"; // 用於Oracle的date
		else if("ACT_STATE".equals(columnName)) //用於其他
			aCondition = columnName + "=" + value;
		else if("ACT_REGIS_DATE".equals(columnName))
			aCondition = "to_char(" + columnName + ",'yyyy-mm-dd')<= to_char(sysdate,'yyyy-mm-dd')"; //此處為「活動開放報名」的radio表單
		
		return aCondition + " ";
	}

	public static String get_WhereCondition(Map<String, String[]> map) {
		Set<String> keys = map.keySet();
		StringBuffer whereCondition = new StringBuffer();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0	&& !"action".equals(key)) {
				count++;
				String aCondition = get_aCondition_For_Oracle(key, value.trim());

				if (count == 1)
					whereCondition.append(" where " + aCondition);
				else
					whereCondition.append(" between " + aCondition);

				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		
		return whereCondition.toString();
	}

	public static void main(String argv[]) {

		// 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
		Map<String, String[]> map = new TreeMap<String, String[]>();
		map.put("ACT_SDATE", new String[] { "2018-10-16" });
		map.put("ACT_EDATE", new String[] { "2018-10-21" });
//		map.put("ACT_REGIS_DATE", new String[] { "2018-10-01" });
//		map.put("", new String[] { "1981-11-17" });
//		map.put("sal", new String[] { "5000.5" });
//		map.put("ACT_STATE", new String[] { "2" });  //1:為活動開放報名 2:活動未開放報名 3:活動已截止
		map.put("action", new String[] { "getXXX" }); // 注意Map裡面會含有action的key

		String finalSQL = "select * from ACTIVITY"
				          + jdbcUtil_CompositeQuery_Activity.get_WhereCondition(map)
				          + "order by ACT_ID";
		System.out.println("●●finalSQL = " + finalSQL);

	}
}
