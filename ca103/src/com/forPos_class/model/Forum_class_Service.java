package com.forPos_class.model;

import java.util.List;

public class Forum_class_Service {

	private Forum_class_DAO_interface dao;

	public Forum_class_Service() {
		dao = new Forum_class_DAO();

	}

	public List<Forum_class_VO> getAll_ForClass() {

		return dao.getAll();

	}

	public Forum_class_VO getOneClass(String forClass_ID) {

		return dao.findByPrimaryKey(forClass_ID);

	}

	public void deleteForClass(Integer forClass_ID) {

		dao.delete(forClass_ID);

	}

}
