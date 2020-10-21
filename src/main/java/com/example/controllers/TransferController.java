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

public class TransferController {

	public static void transfer(HttpServletRequest request, HttpServletResponse response,int accountnum) throws IOException {
		//the PrintWriter object prints HTML takes as a response from request
		PrintWriter pw = response.getWriter();
		String [] str = request.getRequestURI().split("/");
		if(request.getMethod().equals("POST")) {
			try {
				//this draws the amount needed to be transfered into between accounts
				Double amt = Double.parseDouble(request.getParameter("amount"));
				AccountDAOImpl adao = new AccountDAOImpl();
				//This draws the user account entry into a new account object
				Account a = adao.selectAccountById(accountnum);
				AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
				AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
				//This draws the updated AccountStatus and AccountType into the account object
				AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
				a.setStatus(as);
				AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
				a.setType(at);
				//this draws the transfer account info into an account object and then pulls the respective AccountType and AccountStatus
				Account transfer = adao.selectAccountById(Integer.parseInt(request.getParameter("transferaccount")));
				AccountStatus ast = asdao.selectAccountStatusById(transfer.getStatus().getStatusId());
				transfer.setStatus(ast);
				AccountType att = atdao.selectAccountTypeById(transfer.getType().getTypeId());
				transfer.setType(att);
				//this initiates the transfer between two account objects
				a.transfer(amt,transfer);
			
				//this updates the user account
				adao.updateAccount(a);
				//this updates the transfer account
				adao.updateAccount(transfer);
				//this outputs a success message to the user to show before redirecting them
				pw.write("<p>Transfer was successful</p>");
				response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
			} catch(Exception e) {
				//this outputs an error message with the form so users can try again
				pw.write("<p>There was an issue processing your transaction</p>\r\n" + 
						"<p>Please input the amount you would like to transfer and the account number of the recipient</p>\r\n" + 
						"<form action=\"/rocp-project/Accounts/"+str[3]+"/"+str[4]+"\" method=\"post\">\r\n" + 
						"		Transfer to: <input type = \"text\" name  = \"transferaccount\">\r\n" + 
						"		Amount: <input type = \"number\" name  = \"amount\" >\r\n" + 
						"		<button type=\"submit\" >Submit</button>\r\n" + 
						"	</form>");
			}
			
		}else {
			//this outputs a form used to transfer money between accounts
			pw.write("<p>Please input the amount you would like to transfer and the account number of the recipient</p>\r\n" + 
					"<form action=\"/rocp-project/Accounts/"+str[3]+"/"+str[4]+"\" method=\"post\">\r\n" + 
					"		Transfer to: <input type = \"text\" name  = \"transferaccount\">\r\n" + 
					"		Amount: <input type = \"number\" name  = \"amount\" >\r\n" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>");
			//+str[3]+"/"+str[4]+"\"
			
		}
		
		
	}
}
