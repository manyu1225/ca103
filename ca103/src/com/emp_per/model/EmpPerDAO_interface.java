package com.emp_per.model;

import java.util.List;


public interface EmpPerDAO_interface {
	public void insert(EmpPerVO empPerVO);
	public void update(EmpPerVO empPerVO);
	public void delete(String permission_id);
	public EmpPerVO findByPrimarKey(String permission_id);
	public List<EmpPerVO> findOneEmpPere(String employee_id);
	public List<EmpPerVO> getAll();
}
