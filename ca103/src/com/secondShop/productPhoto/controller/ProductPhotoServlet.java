package com.secondShop.productPhoto.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.util.ImageUtil;
import com.secondShop.product.model.ProductVO;
import com.secondShop.productPhoto.model.ProductPhotoService;
import com.secondShop.productPhoto.model.ProductPhotoVO;

/**
 * Servlet implementation class ProductPhotoServlet
 */
@WebServlet("/ProductPhotoServlet")
public class ProductPhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("image/gif");
		String action = req.getParameter("action");
		
		 if("deletePhoto".equals(action)) {
			 System.out.println("我來砍照片");
			 String photoId = req.getParameter("photoId");
			 ProductPhotoService productPhotoSvc = new ProductPhotoService();
			 productPhotoSvc.deleteProductPhoto(photoId);
				res.setContentType("text/plain");
				res.setCharacterEncoding("UTF-8");
				PrintWriter out = res.getWriter();
				JSONObject obj = new JSONObject();
				try {
					obj.put("photoId", photoId);
					obj.put("AjaxReturn", "OK");

				} catch (JSONException e) {
					e.printStackTrace();
				}
				out.write(obj.toString());
				out.flush();
				out.close();
		 }

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8"); // 處理中文檔名
		res.setContentType("image/gif");
		String action = req.getParameter("action");
		
//		if("photoForOne".equals(action)) {
//			String productId = req.getParameter("productId");
//			ServletOutputStream out = res.getOutputStream();
//			ProductPhotoService productPhotoSvc = new ProductPhotoService();
//			List<ProductPhotoVO> productPhotoforOne = productPhotoSvc.ProductPhotoforOne(productId);
//			ProductPhotoVO productPhotoVO = null;
//			for(int i = 0 ;i<1;i++) {
//				productPhotoVO = productPhotoforOne.get(i);
//			}
//
//			byte[] productPhoto = productPhotoVO.getProductPhoto();
//			InputStream is = new ByteArrayInputStream(productPhoto);
//			BufferedInputStream in = new BufferedInputStream(is);
//			byte [] buf = new byte[in.available()];
//			in.read(buf);
//			out.write(buf);
//		}
		
		//用圖片ID查出圖片
		if("findOnePhotoByPhotoId".equals(action)) {
			String photoId = req.getParameter("photoId");
			ServletOutputStream out = res.getOutputStream();	
			ProductPhotoVO productPhotoVO = new ProductPhotoService().findOneByPhotoId(photoId);			
			byte[] productPhoto = productPhotoVO.getProductPhoto();
			InputStream is = new ByteArrayInputStream(productPhoto);
			BufferedInputStream in = new BufferedInputStream(is);
			byte [] buf = new byte[in.available()];
			in.read(buf);
			out.write(buf);
			out.close();
		}
		
		//顯示單一商品全部相片
		if("photoForOne".equals(action)) {
			String productId = req.getParameter("productId");
			ServletOutputStream out = res.getOutputStream();
			ProductPhotoService productPhotoSvc = new ProductPhotoService();
			List<ProductPhotoVO> productPhotolist = productPhotoSvc.ProductPhotoforOne(productId);
			for(ProductPhotoVO photo : productPhotolist) {
				byte[] productPhoto = photo.getProductPhoto();
				byte [] smllPhoto = new ImageUtil().shrink(productPhoto, 300);
				InputStream is = new ByteArrayInputStream(smllPhoto);
				BufferedInputStream in = new BufferedInputStream(is);
				byte [] buf = new byte[in.available()];
				
//				InputStream is = new ByteArrayInputStream(productPhoto);
//				BufferedInputStream in = new BufferedInputStream(is);
//				byte [] buf = new byte[in.available()];
				
			
				
				in.read(buf);
				out.write(buf);
				
			}
			out.close();		
			
//			ProductPhotoVO photoVO = it.next();
//			byte[] productPhoto = photoVO.getProductPhoto();
//			InputStream is = new ByteArrayInputStream(productPhoto);
//			BufferedInputStream in = new BufferedInputStream(is);
//			byte [] buf = new byte[4*1024];
//			int len;
//			while((len = in.read(buf))!=-1){
//				out.write(buf, 0, len);
//			}
		}
		
		if("deleteProductPhoto".equals(action)) {
			String photoId = req.getParameter("productId");
			ProductPhotoService productPhotoSvc = new ProductPhotoService();
			productPhotoSvc.deleteProductPhoto(photoId);
			System.out.println("我來刪除囉");
		}

	}


}
