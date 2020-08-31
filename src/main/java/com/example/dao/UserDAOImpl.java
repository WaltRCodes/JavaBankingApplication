package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.example.models.Role;
import com.example.models.User;
import com.example.util.ConnectionFactory;

public class UserDAOImpl implements UserDAO {

	@Override
	public User insertUser(User u) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "INSERT INTO users (username,user_password,first_name,last_name,email,role_id) values "
				+ "(?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			ps.setString(5, u.getEmail());
			ps.setInt(6, u.getRole().getRoleId());
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null;
		return  this.selectUserByUserName(u.getUsername());
	}

	@Override
	public void updateUser(User u) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "UPDATE users SET username = ?, user_password = ?, first_name = ?, last_name = ?, email = ?, role_id = ? WHERE user_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getFirstName());
			ps.setString(4, u.getLastName());
			ps.setString(5, u.getEmail());
			ps.setInt(6, u.getRole().getRoleId());
			ps.setInt(7, u.getUserId());
			ps.execute();
			
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<User> selectAllUsers() {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "SELECT * FROM users";
		
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				users.add(new User(
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
						new Role(rs.getInt(7),null), new ArrayList<>()));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	}

	@Override
	public User selectUserByUserName(String username) {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM users WHERE username = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				users.add(new User(
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
						new Role(rs.getInt(7),null), new ArrayList<>()));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(users.size()==0) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public User selectUserByEmail(User u) {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM users WHERE email = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, u.getEmail());
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				users.add(new User(
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
						new Role(rs.getInt(7),null), new ArrayList<>()));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(users.size()==0) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public User selectUserById(int id) {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM users WHERE user_id  = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				users.add(new User(
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),
						new Role(rs.getInt(7),null), new ArrayList<>()));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(users.size()==0) {
			return null;
		}
		return users.get(0);
	}

	@Override
	public void deleteUser(User u) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "DELETE FROM users WHERE user_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getUserId());
			
			ps.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
