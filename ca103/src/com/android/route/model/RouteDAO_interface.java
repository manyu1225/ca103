package com.android.route.model;

import java.util.List;

import javax.servlet.http.HttpServletResponse;



public interface RouteDAO_interface {
	
	public RouteVO getByRotid(String rot_id);
//	private static final String GETALLBYPK = "select * from GP where GP_ID=?";
	public List<RouteVO> getByMemId (String mem_id);
//private static final String GETONEBYMEM = "select * from GP where MEM_ID = ?"; 
	public List<RouteVO>  getAll();
//	private static final String GETALLTEAM= "select * from GP";
	public byte[] getImage(String rot_id);
	
	public void getRot_Photo(String rot_id, HttpServletResponse response);

	
}
