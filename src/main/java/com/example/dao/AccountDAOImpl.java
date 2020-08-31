package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.models.Account;
import com.example.models.AccountStatus;
import com.example.models.AccountType;
import com.example.models.User;
import com.example.util.ConnectionFactory;

public class AccountDAOImpl implements AccountDAO {

	@Override
	public Account insertAccount(Account a, User u) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "INSERT INTO account (balance,account_type_id,account_status_id,owner_id,creation_date) values "
				+ "(?,?,?,?,?)";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, a.getBalance());
			ps.setInt(2, a.getType().getTypeId());
			ps.setInt(3, a.getStatus().getStatusId());
			ps.setInt(4, u.getUserId());
			ps.setDate(5, a.getCreationDate());
			ps.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return null;
		List<Account> accounts =  this.selectAccountByOwner(u);
		return accounts.get(accounts.size()-1);
	}

	@Override
	public void updateAccount(Account a) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "UPDATE account SET balance = ? WHERE account_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDouble(1, a.getBalance());
			ps.setInt(2, a.getAccountId());
			
			ps.execute();
			
			
		} catch (SQLException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Account> selectAllAccounts() {
		// TODO Auto-generated method stub
		List<Account> accounts = new ArrayList<>();
		
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "SELECT * FROM account";
		
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				accounts.add(new Account(rs.getInt(1),
						rs.getDouble(2),
						new AccountStatus(rs.getInt(3),null),
						new AccountType(rs.getInt(4),null),
						rs.getDate(6)));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return accounts;
	}


	@Override
	public Account selectAccountById(int id) {
		// TODO Auto-generated method stub
		List<Account> accounts = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM account WHERE account_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				accounts.add(new Account(rs.getInt(1),
						rs.getDouble(2),
						new AccountStatus(rs.getInt(3),null),
						new AccountType(rs.getInt(4),null),
						rs.getDate(6)));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(accounts.size()==0) {
			return null;
		}
		return accounts.get(0);
	}

	@Override
	public void deleteAccount(Account a) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionFactory.getConnection();
		
		String sql = "DELETE FROM account WHERE account_id = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, a.getAccountId());
			
			ps.execute();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Account> selectAccountByOwner(User u) {
		// TODO Auto-generated method stub
		List<Account> accounts = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM account WHERE owner_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, u.getUserId());
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				accounts.add(new Account(rs.getInt(1),
						rs.getDouble(2),
						new AccountStatus(rs.getInt(3),null),
						new AccountType(rs.getInt(4),null),
						rs.getDate(6)));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(accounts.size()==0) {
			return null;
		}
		return accounts;
	}

	@Override
	public List<Account> selectAccountByStatus(AccountStatus as) {
		// TODO Auto-generated method stub
		List<Account> accounts = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getConnection()){ 
			String sql = "SELECT * FROM account WHERE account_status_id = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, as.getStatusId());
			ResultSet rs = ps.executeQuery();
			
			//System.out.println(rs);
			
			while(rs.next()) {
				accounts.add(new Account(rs.getInt(1),
						rs.getDouble(2),
						new AccountStatus(rs.getInt(3),null),
						new AccountType(rs.getInt(4),null),
						rs.getDate(6)));
				//System.out.println(rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(accounts.size()==0) {
			return null;
		}
		return accounts;
	}

}
