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
		PrintWriter pw = response.getWriter();
		String [] str = request.getRequestURI().split("/");
		if(request.getMethod().equals("POST")) {
			try {
			Double amt = Double.parseDouble(request.getParameter("amount"));
			AccountDAOImpl adao = new AccountDAOImpl();
			
			Account a = adao.selectAccountById(accountnum);
			AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
			AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
			AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
			a.setStatus(as);
			AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
			a.setType(at);
			
			Account transfer = adao.selectAccountById(Integer.parseInt(request.getParameter("transferaccount")));
			AccountStatus ast = asdao.selectAccountStatusById(transfer.getStatus().getStatusId());
			transfer.setStatus(ast);
			AccountType att = atdao.selectAccountTypeById(transfer.getType().getTypeId());
			transfer.setType(att);
			a.transfer(amt,transfer);
			
			//account deposit logic
			adao.updateAccount(a);
			adao.updateAccount(transfer);
			pw.write("<p>Transfer was successful</p>");
			response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
			} catch(Exception e) {
				pw.write("<p>There was an issue processing your transaction</p>\r\n" + 
						"<p>Please input the amount you would like to transfer and the account number of the recipient</p>\r\n" + 
						"<form action=\"/rocp-project/Accounts/"+str[3]+"/"+str[4]+"\" method=\"post\">\r\n" + 
						"		Transfer to: <input type = \"text\" name  = \"transferaccount\">\r\n" + 
						"		Amount: <input type = \"number\" name  = \"amount\" >\r\n" + 
						"		<button type=\"submit\" >Submit</button>\r\n" + 
						"	</form>");
			}
			
		}else {
			
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
