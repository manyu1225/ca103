package com.android.emp.model;

import java.util.List;

public class EmpMemService {
	private EmpMemDAO_interface dao;
	
	public EmpMemService(){
		dao = new EmpJDBCDAO();
		//new�X����ɡA�P�ɳЫ�MODEL�̭���DAO����
	}

	public EmpMemVO addEmp(String emp_id,String emp_accound,String emp_password,String emp_firstname,String emp_lastname,Integer emp_pr,String emp_ad,String emp_email) {
		EmpMemVO empMemVO = new EmpMemVO();
		//�]���s�W��ק�ҭn�ʪ����e���@�ˡA�ҥH�S����k�ƥ�NEW�b�غc�l�̭��A�ݭn�U��NEW�~��(���OUPDATE�N���ק�id�A�ҥH�|��ʪ����N�������@�ˤF)
		empMemVO.setEmp_id(emp_id);
		empMemVO.setEmp_accound(emp_accound);
		empMemVO.setEmp_password(emp_password);
		empMemVO.setEmp_firstname(emp_firstname);
		empMemVO.setEmp_lastname(emp_lastname);
		empMemVO.setEmp_pr(emp_pr);
		empMemVO.setEmp_ad(emp_ad);
		empMemVO.setEmp_email(emp_email);
		dao.insert(empMemVO);
		
		return empMemVO;
		//�粒����n��s�ƭ�
	}

	public EmpMemVO updateEmp(String emp_firstname,String emp_lastname,Integer emp_pr,String emp_ad,String emp_email) {
		EmpMemVO empMemVO = new EmpMemVO();
		empMemVO.setEmp_firstname(emp_firstname);
		empMemVO.setEmp_lastname(emp_lastname);
		empMemVO.setEmp_pr(emp_pr);
		empMemVO.setEmp_ad(emp_ad);
		empMemVO.setEmp_email(emp_email);
		dao.update(empMemVO);
		
		return empMemVO;
		
	}
	
	public void deleteEmp(String emp_id) {
		dao.delete(emp_id);
		
	}
	
	public EmpMemVO getOneEmp(String emp_id) {
		return dao.findByPrimarKey(emp_id);
	}
	
	public List<EmpMemVO> getAll() {
		return dao.getAll();
		
	}
}
