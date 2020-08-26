package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.models.AccountStatus;
import com.example.util.ConnectionFactory;

public class AccountStatusDAOImpl implements AccountStatusDAO {

	@Override
	public AccountStatus insertAccountStatus(String name) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "INSERT INTO account_status (status_name) values "
				+ "(?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return this.selectAccountStatusByName(name);
	}

	@Override
	public void updateAccountStatus(AccountStatus as) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "UPDATE account_status SET status_name = ? WHERE status_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, as.getStatus());
			ps.setInt(2, as.getStatusId());
			
			ps.execute();
			
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<AccountStatus> selectAllAccountStatus() {
		// TODO Auto-generated method stub
		List<AccountStatus> statuses = new ArrayList<>();
		
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "SELECT * FROM account_status";
		
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				statuses.add(new AccountStatus(rs.getInt(1),rs.getString(2)));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return statuses;
	}

	@Override
	public AccountStatus selectAccountStatusByName(String name) {
		// TODO Auto-generated method stub
		List<AccountStatus> statuses  = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM account_status WHERE status_name = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				statuses.add(new AccountStatus(rs.getInt(1),rs.getString(2)));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(statuses.size()==0) {
			return null;
		}
		return statuses.get(0);
	}

	@Override
	public AccountStatus selectAccountStatusById(int id) {
		// TODO Auto-generated method stub
		List<AccountStatus> statuses  = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM account_status WHERE status_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				statuses.add(new AccountStatus(rs.getInt(1),rs.getString(2)));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(statuses.size()==0) {
			return null;
		}
		return statuses.get(0);
	}

	@Override
	public void deleteAccountStatus(AccountStatus as) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "DELETE FROM account_status WHERE status_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, as.getStatusId());
			
			ps.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
