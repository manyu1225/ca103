package com.emp.model;

import java.util.List;

public interface EmpDAO_interface {
	public void insert(EmpVO empMemVO);
	public void update(EmpVO empVO);
	public void updateByMaster(EmpVO memVO);
	public void delete(String emp_id);
	public EmpVO findByPrimarKey(String emp_account);
	public EmpVO findOneById(String emp_id);
	public List<EmpVO> getAll();
}
