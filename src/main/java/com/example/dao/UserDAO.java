package com.example.dao;

import java.util.List;

import com.example.models.User;

public interface UserDAO {
	
	public User insertUser(User u);
	
	public void updateUser(User u);
	
	public List<User> selectAllUsers();
	
	public User selectUserByUserName(String username);
	
	public User selectUserByEmail(User u);
	
	public User selectUserById(int id);
	
	public void deleteUser(User u);

}
