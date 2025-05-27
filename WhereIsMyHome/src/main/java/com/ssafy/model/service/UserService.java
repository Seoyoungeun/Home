package com.ssafy.model.service;

import java.util.List;

import com.ssafy.model.dto.User;

public interface UserService {
	User findByIdAndPassword(User user);
	User insert(User user);
	boolean isEmailExists(String email);
	boolean isAdmin(User user);
	void update(User user);
	List<User> findAll();
	void banUser(int uid);
	void unbanUser(int uid);
	User findByUid(int uid);
	User findByEmail(String email);
}
