package com.example.dao;

import java.util.List;

import com.example.models.Role;

public interface RoleDAO {
	
	public Role insertRole(String name);
	
	public void updateRole(Role r);
	
	public List<Role> selectAllRoles();
	
	public Role selectRoleByName(String name);
	
	public Role selectRoleById(int id);
	
	public void deleteRole(Role r);

}
