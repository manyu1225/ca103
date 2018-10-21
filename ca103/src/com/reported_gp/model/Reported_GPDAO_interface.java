package com.reported_gp.model;

import java.util.List;

public interface Reported_GPDAO_interface {
	public void addRepGP(Reported_GPVO repVO);
	public void updateRepGP(Reported_GPVO repVO,int status);
	public List<Reported_GPVO> getAllRepGP(boolean hasDisposed); 
	
	
}
