package com.reported_gp.model;

import java.util.List;

public class Reported_GPService {
	private Reported_GPDAO_interface dao;

	public Reported_GPService() {
		dao = new Reported_GPJNDIDAO();
	}
	
	public void addRepGP(Reported_GPVO repVO) {
		dao.addRepGP(repVO);
	}
	
	public List<Reported_GPVO> getAllRepGP(boolean hasDisposed) {
		return dao.getAllRepGP(hasDisposed);
	}
	
	public void updateRepGP(Reported_GPVO repVO, int status) {
		dao.updateRepGP(repVO, status);
	}
}
