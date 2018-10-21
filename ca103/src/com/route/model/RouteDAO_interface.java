package com.route.model;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RouteDAO_interface {
	public String insert(RouteVO routeVO);
	public void update_front(RouteVO routeVO);
	public void update_back(RouteVO routeVO);
	public void update_back(String rot_id, Integer rot_popu);
	public void update_messageCount(String rot_id);
	public void delete(RouteVO routeVO);
	public void closeRoute(String rot_id);
	public RouteVO getRouteDetailed(String str);
	public List<RouteVO> getRouteList(String str);
	public List<RouteVO> getRouteCloseList(String str);
	public void getRot_Photo(String rot_id, HttpServletResponse response);
	
}
