package com.example.dao;

import java.util.List;

import com.example.models.Account;
import com.example.models.AccountStatus;
import com.example.models.User;


public interface AccountDAO {
	
	public Account insertAccount(Account a, User u);
	
	public void updateAccount(Account a);
	
	public List<Account> selectAllAccounts();
	
	public List<Account> selectAccountByOwner(User u);
	
	public List<Account> selectAccountByStatus(AccountStatus as);
	
	public Account selectAccountById(int id);
	
	public void deleteAccount(Account a);

}
