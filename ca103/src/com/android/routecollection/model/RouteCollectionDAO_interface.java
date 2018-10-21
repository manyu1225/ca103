package com.android.routecollection.model;

import java.util.List;

import com.android.mem.model.MemVO;
import com.android.route.model.RouteVO;
import com.android.team.model.TeamVO;

public interface RouteCollectionDAO_interface {
	public void insert(RouteCollectionVO routeCollectionVO);
	
	public List<RouteVO> findRouteCollection(MemVO memVO);
	
	
	
	
	public void update(RouteCollectionVO routeCollectionVO);
	
	
	
	public void delete(RouteCollectionVO routeCollectionVO);
	
	
	public List<RouteVO> findRouteCollectionList(String mem_id, String sql);
	
	
	
	public int collOrNot(RouteCollectionVO routeCollectionVO);
	
	

	
	
	

}
