package com.android.emp.model;

import java.util.List;

public interface EmpMemDAO_interface {
	public void insert(EmpMemVO empMemVO);
	public void update(EmpMemVO empMemVO);
	public void delete(String emp_id);
	public EmpMemVO findByPrimarKey(String emp_id);
	public List<EmpMemVO> getAll();
}
