package com.example.dao;

import java.util.List;

import com.example.models.AccountType;

public interface AccountTypeDAO {
	
	public AccountType insertAccountType(String name);
	
	public void updateAccountType(AccountType at);
	
	public List<AccountType> selectAllAccountTypes();
	
	public AccountType selectAccountTypeByName(String name);
	
	public AccountType selectAccountTypeById(int id);
	
	public void deleteAccountType(AccountType at);

}
