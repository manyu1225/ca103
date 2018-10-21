package com.friends.model;

import java.util.List;

public class FriendsService {
	private FriendsDAO_interface dao;
	
	public FriendsService(){
		dao = new FriendsJDBCDAO();
	}
	
	public void insert(FriendsVO friendsVO) {
		
		dao.insert(friendsVO);
	}
	
	public void update(FriendsVO friendsVO) {
		dao.update(friendsVO);
	}
	
	public FriendsVO findByPrimarKey(FriendsVO friendsVO) {
		return dao.findByPrimarKey(friendsVO);
	}
	
	public List<FriendsVO> getAll(String other_id){
		//other=自己的ID
		System.out.println("spass?");
		return dao.getAll(other_id);
	}
	
	public void delete(FriendsVO friendsVO) {
		dao.delete(friendsVO);
	}
}
