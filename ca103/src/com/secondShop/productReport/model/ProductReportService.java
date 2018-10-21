package com.secondShop.productReport.model;

import java.util.List;

public class ProductReportService {
	private ProductReportDAO_Interface dao;
	
	public ProductReportService() {
		dao = new ProductReportDAO();
	}
	
	public void insertProductReport(ProductReportVO productReportVO) {
		dao.insertProductReport(productReportVO);
	} //新增一筆檢舉
	public void updateProductReport(ProductReportVO productReportVO) {
		dao.updateProductReport(productReportVO);
	}//審核成功
	public List<ProductReportVO> allProductReport(){
		return dao.allProductReport();
		
	}//後台查看檢舉列表
	
	public List<ProductReportVO> statusProductReport(Integer reportStatus){
		return dao.statusProductReport(reportStatus);
		
	}//審核狀態查詢

}
