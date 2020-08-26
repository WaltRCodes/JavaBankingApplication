package com.example.dao;

import java.util.List;

import com.example.models.AccountStatus;


public interface AccountStatusDAO {
	
	public AccountStatus insertAccountStatus(String name);
	
	public void updateAccountStatus(AccountStatus as);
	
	public List<AccountStatus> selectAllAccountStatus();
	
	public AccountStatus selectAccountStatusByName(String name);
	
	public AccountStatus selectAccountStatusById(int id);
	
	public void deleteAccountStatus(AccountStatus as);

}
