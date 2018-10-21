package com.emp_per.model;

import java.util.List;

public class EmpPerService {
	private EmpPerDAO_interface dao;
	
	public  List<EmpPerVO> findOneEmpPere(String employee_id){
		System.out.println("服務器字串:"+employee_id);
		return dao.findOneEmpPere(employee_id);
	}
	
	
	
}
