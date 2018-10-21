package com.routecollection.model;

import java.util.List;

import com.route.model.RouteVO;

public interface RouteCollectionDAO_interface {
	public void insert(RouteCollectionVO routeCollectionVO);
	public void update(RouteCollectionVO routeCollectionVO);
	public void delete(RouteCollectionVO routeCollectionVO);
	public List<RouteVO> findRouteCollectionList(String mem_id, String sql);
	public int collOrNot(RouteCollectionVO routeCollectionVO);
}
