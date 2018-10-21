package com.secondShop.productReport.model;

import java.sql.Timestamp;

import com.secondShop.product.model.ProductVO;

public class ProductReportVO implements java.io.Serializable{
	private String reportId;
	private String memId;
	private String productId;
	private Timestamp reportDate;
	private Integer reportStatus;
	private String reportDetailed;	
	private ProductVO productVO ;

	public ProductVO getProductVO() {
		return productVO;
	}
	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}
	public ProductReportVO(String reportId, String memId, String productId, Timestamp reportDate, Integer reportStatus,
			String reportDetailed, ProductVO productVO) {
		super();
		this.reportId = reportId;
		this.memId = memId;
		this.productId = productId;
		this.reportDate = reportDate;
		this.reportStatus = reportStatus;
		this.reportDetailed = reportDetailed;
		this.productVO = productVO;
	}
	public ProductReportVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Timestamp getReportDate() {
		return reportDate;
	}
	public void setReportDate(Timestamp reportDate) {
		this.reportDate = reportDate;
	}
	public Integer getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getReportDetailed() {
		return reportDetailed;
	}
	public void setReportDetailed(String reportDetailed) {
		this.reportDetailed = reportDetailed;
	}
	
	
}