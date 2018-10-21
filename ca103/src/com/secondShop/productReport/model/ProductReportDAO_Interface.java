package com.secondShop.productReport.model;

import java.util.List;

public interface ProductReportDAO_Interface {
	public void insertProductReport(ProductReportVO productReportVO); //新增一筆檢舉
	public void updateProductReport(ProductReportVO productReportVO);//審核成功
	public List<ProductReportVO> allProductReport(); //後台查看檢舉列表
	
	public List<ProductReportVO> statusProductReport(Integer reportStatus);//審核狀態查詢
	
}
