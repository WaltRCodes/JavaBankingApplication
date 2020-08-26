package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.models.AccountType;
import com.example.util.ConnectionFactory;

public class AccountTypeDAOImpl implements AccountTypeDAO {

	@Override
	public AccountType insertAccountType(String name) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "INSERT INTO account_type (type_name) values "
				+ "(?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.selectAccountTypeByName(name);
	}

	@Override
	public void updateAccountType(AccountType at) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "UPDATE account_type SET type_name = ? WHERE type_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, at.getType());
			ps.setInt(2, at.getTypeId());
			
			ps.execute();
			
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<AccountType> selectAllAccountTypes() {
		// TODO Auto-generated method stub
		List<AccountType> types = new ArrayList<>();
		
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "SELECT * FROM account_type";
		
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				types.add(new AccountType(rs.getInt(1),rs.getString(2)));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return types;
	}

	@Override
	public AccountType selectAccountTypeByName(String name) {
		// TODO Auto-generated method stub
		List<AccountType> types  = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM account_type WHERE type_name = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				types.add(new AccountType(rs.getInt(1),rs.getString(2)));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(types.size()==0) {
			return null;
		}
		return types.get(0);
	}

	@Override
	public AccountType selectAccountTypeById(int id) {
		// TODO Auto-generated method stub
		List<AccountType> types  = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM account_type WHERE type_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				types.add(new AccountType(rs.getInt(1),rs.getString(2)));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(types.size()==0) {
			return null;
		}
		return types.get(0);
	}

	@Override
	public void deleteAccountType(AccountType at) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "DELETE FROM account_type WHERE type_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, at.getTypeId());
			
			ps.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
