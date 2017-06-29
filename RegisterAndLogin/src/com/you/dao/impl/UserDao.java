package com.you.dao.impl;

import com.you.domain.User;

public interface UserDao {
	public User findByName(String username);
	public User findByEmail(String email);
	public void addUser(User user);
}
