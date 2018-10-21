package com.android.mem.model;

import java.util.List;

public class MemService {
	private MemDAO_interface dao;
	
	public MemService() {
		dao = new MemJDBCDAO();
	}

	public  boolean findisMember(String mem_ac, String password) {
		
		return 	dao.isMember( mem_ac,  password); //having a account return true 
	}
	public MemVO addMem(String mem_ac,String mem_password,String mem_lastname,String mem_firstname,java.sql.Date mem_birthday,String mem_phone,String  mem_tel,byte[] mem_photo,byte[] mem_cart_photo,String mem_email,String mem_aboutme,String mem_nickname) {
		MemVO memVO = new MemVO();
		MemJDBCDAO dao = new MemJDBCDAO();
		memVO.setMem_ac(mem_ac);
		memVO.setMem_password(mem_password);
		memVO.setMem_firstname(mem_firstname);
		memVO.setMem_lastname(mem_lastname);
		memVO.setMem_tel(mem_tel);
		memVO.setMem_birthday(mem_birthday);
		memVO.setMem_email(mem_email);
		memVO.setMem_phone(mem_phone);
		memVO.setMem_cart_photo(mem_cart_photo);
		memVO.setMem_photo(mem_photo);
		memVO.setMem_aboutme(mem_aboutme);
		memVO.setMem_nickname(mem_nickname);
		dao.insert(memVO);
		return memVO;
	}
	public List<MemVO> getAll(){
		return dao.getAll();
	}
	public MemVO findByPrimarKey(String mem_ac) {
		return dao.findByPrimarKey(mem_ac);
	}
	public void delete(String mem_id) {
		dao.delete(mem_id);
	}
	

	
	
	
}
