package com.permission.model;

import java.util.List;

import com.emp.model.EmpVO;
import com.emp_per.model.EmpPerVO;

public interface PerDAO_interface {
	public void insert(PerVO perVO);
	public void update(PerVO perVO);
	public void delete(PerVO perVO);
	public PerVO findByPrimarKey(String permission_id);
	public List<PerVO> findPerNameByEmp(String employee_id);
	public List<PerVO> getAll();
}
