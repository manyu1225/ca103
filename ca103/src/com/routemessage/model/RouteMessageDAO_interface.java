package com.routemessage.model;

import java.util.List;
import java.util.Map;

public interface RouteMessageDAO_interface {
	public void insert(RouteMessageVO routeMessage);
	public void update(RouteMessageVO routeMessage);
	public void delete(RouteMessageVO routeMessage);
	public void close(Integer rotMes_id);
	public List<RouteMessageVO> getAll();
	public List<RouteMessageVO> getByRouteId(String rot_id);
	public List<RouteMessageVO> getByStatus(String rotMesRep_status);
	public Map<String, Integer> getRouteMessageCount();
}
