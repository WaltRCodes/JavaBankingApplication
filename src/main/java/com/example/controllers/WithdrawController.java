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

public class WithdrawController {

	public static void withdraw(HttpServletRequest request, HttpServletResponse response,int accountnum) throws IOException {
		//this is what will be used to print HTML tags as a response to request
		PrintWriter pw = response.getWriter();
		String [] str = request.getRequestURI().split("/");
		if(request.getMethod().equals("POST")) {
			try {
				//this retrieves the amount to be withdrawn from the account
				Double amt = Double.parseDouble(request.getParameter("amount"));
				AccountDAOImpl adao = new AccountDAOImpl();
				//this retrieves the account info from the database and inputs it into an account object
				Account a = adao.selectAccountById(accountnum);
				AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
				AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
				//this retrieves the latest status details tied to the account into an AccountStatus object 
				AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
				//this updates the account object with the retrieved AccountStatus object
				a.setStatus(as);
				//this retrieves the AccountType info based on what's in the account object
				AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
				//this sets the new AccountType into the account
				a.setType(at);
				//this withdraws the amount from the account
				a.withdraw(amt);
				//this sends the updated account object to be used in order to update the entries in the database
				adao.updateAccount(a);
				//if the update is successful then a success message is printed prior to a redirect 
				pw.write("<p>Withdraw was successful</p>");
				response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
			} catch(Exception e) {
				//if the account cannot be updated an error and the form are printed onto the page
				pw.write("<p>There was an issue processing your transaction</p>\r\n" + 
						"<p>Please input the amount you would like to withdraw</p>\r\n" + 
						"<form action=\"/rocp-project/Accounts/"+str[3]+"/"+str[4]+"\" method=\"post\">\r\n" + 
						"		Amount: <input type = \"number\" name  = \"amount\" >\r\n" + 
						"		<button type=\"submit\" >Submit</button>\r\n" + 
						"	</form>");
			}
			
		}else {
			//if the request is not a post request then the widraw form is outputed at this page
			pw.write("<p>Please input the amount you would like to withdraw</p>\r\n" + 
					"<form action=\"/rocp-project/Accounts/"+str[3]+"/"+str[4]+"\" method=\"post\">\r\n" + 
					"		Amount: <input type = \"number\" name  = \"amount\" >\r\n" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>");
			//response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
			
		}
		
		
	}
}
