package com.friends.model;

import java.util.List;

public interface FriendsDAO_interface {
	public void insert(FriendsVO friendsVO);
	public void update(FriendsVO friendsVO);
	public void delete(FriendsVO friendsVO);
	public FriendsVO findByPrimarKey(FriendsVO friendsVO);
	public FriendsVO findOtherNicknameById(String char_no);
	public List<FriendsVO> getAll(String other_id);
}
