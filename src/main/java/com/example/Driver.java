package com.example;

import com.example.dao.AccountStatusDAOImpl;
import com.example.dao.AccountTypeDAOImpl;
import com.example.dao.RoleDAOImpl;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
		AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
		RoleDAOImpl rdao = new RoleDAOImpl();
//		rdao.insertRole("Admin");
//		rdao.insertRole("Employee");
//		rdao.insertRole("Standard");
//		rdao.insertRole("Premium");
//		asdao.insertAccountStatus("Pending");
//		asdao.insertAccountStatus("Open");
//		asdao.insertAccountStatus("Closed");
//		asdao.insertAccountStatus("Denied");
//		atdao.insertAccountType("Checking");
//		atdao.insertAccountType("Savings");
		System.out.println(asdao.selectAllAccountStatus());
		System.out.println(atdao.selectAllAccountTypes());
		System.out.println(rdao.selectAllRoles());
	}

}
