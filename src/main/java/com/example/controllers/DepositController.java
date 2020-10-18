package com.example.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.AccountDAOImpl;
import com.example.dao.AccountStatusDAOImpl;
import com.example.dao.AccountTypeDAOImpl;
import com.example.models.Account;
import com.example.models.AccountStatus;
import com.example.models.AccountType;

public class DepositController {
	//This controller handles deposits into user accounts
	public static void deposit(HttpServletRequest request, HttpServletResponse response,int accountnum) throws IOException {
		//the printwriter object allows one to out html tags as a response to certain quest
		PrintWriter pw = response.getWriter();
		String [] str = request.getRequestURI().split("/");
		if(request.getMethod().equals("POST")) {
			try {
				//this captures the amount to be deposited into the account
				Double amt = Double.parseDouble(request.getParameter("amount"));
				AccountDAOImpl adao = new AccountDAOImpl();
				//this object connects to the database and draws the stored database entry into an account object
				Account a = adao.selectAccountById(accountnum);
				AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
				AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
				//This draws the account status from the database
				AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
				//this updates the Account object with the latest Status from the database
				a.setStatus(as);
				//this draws the AccountType entry stored in the database to a new AccountType object
				AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
				//this updates the AccountType object in the Account object with the AccountType entry stored in the database
				a.setType(at);
				//this updates the Account object with amount entered into the phone
				a.deposit(amt);
				//this pushes the updated Account object info to the database
				adao.updateAccount(a);
				//these lines confirm that the deposit was successful
				pw.write("<p>Deposit was successful</p>");
				//this redirects the page after a successful transfer 
				response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
			} catch(Exception e) {
				//This prints out the deposit form with an error message at top of the page 
				pw.write("<p>There was an issue processing your transaction</p>\r\n" + 
						"<p>Please input the amount you would like to deposit</p>\r\n" + 
						"<form action=\"/rocp-project/Accounts/"+str[3]+"/"+str[4]+"\" method=\"post\">\r\n" + 
						"		Amount: <input type = \"number\" name  = \"amount\" >\r\n" + 
						"		<button type=\"submit\" >Submit</button>\r\n" + 
						"	</form>");
			}
			
		}else {
			//this prints out the deposit form
			pw.write("<p>Please input the amount you would like to deposit</p>\r\n" + 
					"<form action=\"/rocp-project/Accounts/"+str[3]+"/"+str[4]+"\" method=\"post\">\r\n" + 
					"		Amount: <input type = \"number\" name  = \"amount\" >\r\n" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>");
			//response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
			
		}
		
		
	}

}
