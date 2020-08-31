package com.example.models;

import java.sql.Date;

//should keep this in mind
public class Account {

	
	private int accountId;
	private double balance;
	private AccountStatus status;
	private AccountType type;
	private Date creationDate;
	
	public Account(int accountId, double balance, AccountStatus status, AccountType type, Date creationDate) {
		super();
		this.accountId = accountId;
		this.balance = balance;
		this.status = status;
		this.type = type;
		this.creationDate = creationDate;
	}
	
	
	public void deposit(double amount) {
		this.balance +=amount;
	}
	
	public void withdraw(double amount) {
		this.balance -=amount;
	}
	
	public void transfer(double amount, Account a) {
		this.withdraw(amount);
		a.deposit(amount);
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public AccountStatus getStatus() {
		return status;
	}
	public void setStatus(AccountStatus status) {
		this.status = status;
	}
	public AccountType getType() {
		return type;
	}
	public void setType(AccountType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return status.getStatus() + " " + type.getType() + " Account: $" + balance + " Created on " + creationDate.toString();
	}
	
}
