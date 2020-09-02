package com.example.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.AccountDAOImpl;
import com.example.dao.AccountStatusDAOImpl;
import com.example.dao.AccountTypeDAOImpl;
import com.example.dao.RoleDAOImpl;
import com.example.dao.UserDAOImpl;
import com.example.models.Account;
import com.example.models.AccountStatus;
import com.example.models.AccountType;
import com.example.models.User;

public class AccountResultsController {
	
	public static void all(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		AccountDAOImpl adao = new AccountDAOImpl();
		AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
		AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
		RoleDAOImpl rdao = new RoleDAOImpl();
		UserDAOImpl udao = new UserDAOImpl();
		String [] str = request.getRequestURI().split("/");
		if(request.getMethod().equals("POST")) {
			if(request.getParameter("id")!=null) {
				response.sendRedirect("http://localhost:8080/rocp-project/Accounts/"+request.getParameter("id"));
			} else if(request.getParameter("status")!=null) {
				try {
					AccountStatus as = asdao.selectAccountStatusByName(request.getParameter("status"));
					response.sendRedirect("http://localhost:8080/rocp-project/Accounts/Status/"+as.getStatusId());
				}  catch(Exception e) {
					pw.write("<p>Could not find that status</p>");
				}
			} else if(request.getParameter("userid")!=null) {
				try {
					User u = udao.selectUserById(Integer.parseInt(request.getParameter("userid")));
					u.setRole(rdao.selectRoleById(u.getRole().getRoleId()));
					u.setAccounts(adao.selectAccountByOwner(u));
					response.sendRedirect("http://localhost:8080/rocp-project/Accounts/Owner/"+u.getUserId());
				}  catch(Exception e) {
					pw.write("<p>Could not find that userid</p>");
				}
			}
			
			
		}else {
			
			pw.write("<form action=\"/rocp-project/Accounts/All\" method=\"post\">\r\n" + 
					"		Find account by Id: <input type = \"number\" name  = \"id\">\r\n" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>\r\n" + 
					"\r\n" + 
					"	<form action=\"/rocp-project/Accounts/All\" method=\"post\">\r\n" + 
					"		Find account by Status: \r\n" + 
					"		<input type=\"radio\" id=\"Pending\" name=\"status\" value=\"Pending\">\r\n" + 
					"		<label for=\"Pending\">Pending</label>\r\n" + 
					"		<input type=\"radio\" id=\"Open\" name=\"status\" value=\"Open\">\r\n" + 
					"		<label for=\"Open\">Open</label>\r\n" + 
					"		<input type=\"radio\" id=\"Closed\" name=\"status\" value=\"Closed\">\r\n" + 
					"		<label for=\"Closed\">Closed</label>\r\n" + 
					"		<input type=\"radio\" id=\"Denied\" name=\"status\" value=\"Denied\">\r\n" + 
					"		<label for=\"Denied\">Denied</label>" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>\r\n" + 
					"\r\n" + 
					"	<form action=\"/rocp-project/Accounts/All\" method=\"post\">\r\n" + 
					"		Find the user by Owner: <input type = \"number\" name  = \"userid\">\r\n" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>");
			
			List<Account> accounts = adao.selectAllAccounts();
			if(accounts==null) {
				pw.write("<p>There are no accounts right now.</p>"
						+ "<form action=\"/rocp-project/Accounts.html\" method=\"get\">\r\n" + 
						"		<button type=\"submit\">Open New Account</button>\r\n" + 
						"</form>");
			} else {
				pw.write("<form action=\"/rocp-project/Accounts.html\" method=\"get\">\r\n" + 
						"		<button type=\"submit\">Open New Account</button>\r\n" + 
						"</form>");
				pw.write("<p>Here are all the accounts</p>"
						+ "<ul>");
				//loop account logic
				for(Account a: accounts) {
					
					AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
					System.out.println(a.getStatus().getStatusId());
					a.setStatus(as);
					AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
					System.out.println(a.getType().getTypeId());
					a.setType(at);
					User u = udao.selectUserById(adao.getAccountOwner(a));
					u.setRole(rdao.selectRoleById(u.getRole().getRoleId()));
					u.setAccounts(adao.selectAccountByOwner(u));
					pw.write("<li>");
					pw.write("<p>Owner: "+u.getFirstName()+" "+u.getLastName()+"</p>");
					pw.write(a.toString());
					pw.write("<div>");
					pw.write("<form action=\"/rocp-project/Accounts/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Edit</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Withdraw/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Withdraw</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Deposit/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Deposit</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Transfer/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Transfer</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Delete/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Delete</button>\r\n" + 
							"	</form>");
					pw.write("<div>");
					pw.write("</li>");
				}
				pw.write("</ul>");
			}
		}
	}
	
	public static void status(HttpServletRequest request, HttpServletResponse response,AccountStatus status) throws IOException {
		PrintWriter pw = response.getWriter();
		AccountDAOImpl adao = new AccountDAOImpl();
		AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
		AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
		RoleDAOImpl rdao = new RoleDAOImpl();
		UserDAOImpl udao = new UserDAOImpl();
		String [] str = request.getRequestURI().split("/");
		if(request.getMethod().equals("POST")) {
			if(request.getParameter("id")!=null) {
				response.sendRedirect("http://localhost:8080/rocp-project/Accounts/"+request.getParameter("id"));
			} else if(request.getParameter("status")!=null) {
				try {
					AccountStatus as = asdao.selectAccountStatusByName(request.getParameter("status"));
					response.sendRedirect("http://localhost:8080/rocp-project/Accounts/Status/"+as.getStatusId());
				}  catch(Exception e) {
					pw.write("<p>Could not find that status</p>");
				}
			} else if(request.getParameter("userid")!=null) {
				try {
					User u = udao.selectUserById(Integer.parseInt(request.getParameter("userid")));
					u.setRole(rdao.selectRoleById(u.getRole().getRoleId()));
					u.setAccounts(adao.selectAccountByOwner(u));
					response.sendRedirect("http://localhost:8080/rocp-project/Accounts/Owner/"+u.getUserId());
				}  catch(Exception e) {
					pw.write("<p>Could not find that userid</p>");
				}
			}
			
			
		}else {
			
			pw.write("<form action=\"/rocp-project/Accounts/All\" method=\"post\">\r\n" + 
					"		Find account by Id: <input type = \"number\" name  = \"id\">\r\n" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>\r\n" + 
					"\r\n" + 
					"	<form action=\"/rocp-project/Accounts/All\" method=\"post\">\r\n" + 
					"		Find account by Status: \r\n" + 
					"		<input type=\"radio\" id=\"Pending\" name=\"status\" value=\"Pending\">\r\n" + 
					"		<label for=\"Pending\">Pending</label><br>\r\n" + 
					"		<input type=\"radio\" id=\"Open\" name=\"status\" value=\"Open\">\r\n" + 
					"		<label for=\"Open\">Open</label><br>\r\n" + 
					"		<input type=\"radio\" id=\"Closed\" name=\"status\" value=\"Closed\">\r\n" + 
					"		<label for=\"Closed\">Closed</label>\r\n" + 
					"		<input type=\"radio\" id=\"Denied\" name=\"status\" value=\"Denied\">\r\n" + 
					"		<label for=\"Denied\">Denied</label>" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>\r\n" + 
					"\r\n" + 
					"	<form action=\"/rocp-project/Accounts/All\" method=\"post\">\r\n" + 
					"		Find the user by Owner: <input type = \"number\" name  = \"userid\">\r\n" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>");
			
			List<Account> accounts = adao.selectAccountByStatus(status);
			if(accounts==null) {
				pw.write("<p>There are no accounts right now.</p>"
						+ "<form action=\"/rocp-project/Accounts.html\" method=\"get\">\r\n" + 
						"		<button type=\"submit\">Open New Account</button>\r\n" + 
						"</form>");
			} else {
				pw.write("<form action=\"/rocp-project/Accounts.html\" method=\"get\">\r\n" + 
						"		<button type=\"submit\">Open New Account</button>\r\n" + 
						"</form>");
				pw.write("<p>Here are all the accounts for "+status.getStatus()+"</p>"
						+ "<ul>");
				//loop account logic
				for(Account a: accounts) {
					
					AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
					System.out.println(a.getStatus().getStatusId());
					a.setStatus(as);
					AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
					System.out.println(a.getType().getTypeId());
					a.setType(at);
					User u = udao.selectUserById(adao.getAccountOwner(a));
					u.setRole(rdao.selectRoleById(u.getRole().getRoleId()));
					u.setAccounts(adao.selectAccountByOwner(u));
					pw.write("<li>");
					pw.write("<p>Owner: "+u.getFirstName()+" "+u.getLastName()+"</p>");
					pw.write(a.toString());
					pw.write("<div>");
					pw.write("<form action=\"/rocp-project/Accounts/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Edit</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Withdraw/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Withdraw</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Deposit/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Deposit</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Transfer/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Transfer</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Delete/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Delete</button>\r\n" + 
							"	</form>");
					pw.write("<div>");
					pw.write("</li>");
				}
				pw.write("</ul>");
			}
		}
	}
	
	public static void owner(HttpServletRequest request, HttpServletResponse response, User owner) throws IOException {
		PrintWriter pw = response.getWriter();
		AccountDAOImpl adao = new AccountDAOImpl();
		AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
		AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
		RoleDAOImpl rdao = new RoleDAOImpl();
		UserDAOImpl udao = new UserDAOImpl();
		String [] str = request.getRequestURI().split("/");
		if(request.getMethod().equals("POST")) {
			if(request.getParameter("id")!=null) {
				response.sendRedirect("http://localhost:8080/rocp-project/Accounts/"+request.getParameter("id"));
			} else if(request.getParameter("status")!=null) {
				try {
					AccountStatus as = asdao.selectAccountStatusByName(request.getParameter("status"));
					response.sendRedirect("http://localhost:8080/rocp-project/Accounts/Status/"+as.getStatusId());
				}  catch(Exception e) {
					pw.write("<p>Could not find that status</p>");
				}
			} else if(request.getParameter("userid")!=null) {
				try {
					User u = udao.selectUserById(Integer.parseInt(request.getParameter("userid")));
					u.setRole(rdao.selectRoleById(u.getRole().getRoleId()));
					u.setAccounts(adao.selectAccountByOwner(u));
					response.sendRedirect("http://localhost:8080/rocp-project/Accounts/Owner/"+u.getUserId());
				}  catch(Exception e) {
					pw.write("<p>Could not find that userid</p>");
				}
			}
			
			
		}else {
			
			pw.write("<form action=\"/rocp-project/Accounts/All\" method=\"post\">\r\n" + 
					"		Find account by Id: <input type = \"number\" name  = \"id\">\r\n" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>\r\n" + 
					"\r\n" + 
					"	<form action=\"/rocp-project/Accounts/All\" method=\"post\">\r\n" + 
					"		Find account by Status: \r\n" + 
					"		<input type=\"radio\" id=\"Pending\" name=\"status\" value=\"Pending\">\r\n" + 
					"		<label for=\"Pending\">Pending</label><br>\r\n" + 
					"		<input type=\"radio\" id=\"Open\" name=\"status\" value=\"Open\">\r\n" + 
					"		<label for=\"Open\">Open</label><br>\r\n" + 
					"		<input type=\"radio\" id=\"Closed\" name=\"status\" value=\"Closed\">\r\n" + 
					"		<label for=\"Closed\">Closed</label>\r\n" + 
					"		<input type=\"radio\" id=\"Denied\" name=\"status\" value=\"Denied\">\r\n" + 
					"		<label for=\"Denied\">Denied</label>" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>\r\n" + 
					"\r\n" + 
					"	<form action=\"/rocp-project/Accounts/All\" method=\"post\">\r\n" + 
					"		Find the user by Owner: <input type = \"number\" name  = \"userid\">\r\n" + 
					"		<button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>");
			
			List<Account> accounts = adao.selectAccountByOwner(owner);
			if(accounts==null) {
				pw.write("<p>There are no accounts right now.</p>"
						+ "<form action=\"/rocp-project/Accounts.html\" method=\"get\">\r\n" + 
						"		<button type=\"submit\">Open New Account</button>\r\n" + 
						"</form>");
			} else {
				pw.write("<form action=\"/rocp-project/Accounts.html\" method=\"get\">\r\n" + 
						"		<button type=\"submit\">Open New Account</button>\r\n" + 
						"</form>");
				pw.write("<p>Here are all the accounts for "+owner.getFirstName()+" "+owner.getLastName()+"</p>"
						+ "<ul>");
				//loop account logic
				for(Account a: accounts) {
					
					AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
					System.out.println(a.getStatus().getStatusId());
					a.setStatus(as);
					AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
					System.out.println(a.getType().getTypeId());
					a.setType(at);
					User u = udao.selectUserById(adao.getAccountOwner(a));
					u.setRole(rdao.selectRoleById(u.getRole().getRoleId()));
					u.setAccounts(adao.selectAccountByOwner(u));
					pw.write("<li>");
					pw.write("<p>Owner: "+u.getFirstName()+" "+u.getLastName()+"</p>");
					pw.write(a.toString());
					pw.write("<div>");
					pw.write("<form action=\"/rocp-project/Accounts/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Edit</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Withdraw/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Withdraw</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Deposit/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Deposit</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Transfer/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Transfer</button>\r\n" + 
							"	</form>");
					pw.write("<form action=\"/rocp-project/Accounts/Delete/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Delete</button>\r\n" + 
							"	</form>");
					pw.write("<div>");
					pw.write("</li>");
				}
				pw.write("</ul>");
			}
		}
	}
	
	public static void update(HttpServletRequest request, HttpServletResponse response, Account account, User owner) throws IOException {
		PrintWriter pw = response.getWriter();
		AccountDAOImpl adao = new AccountDAOImpl();
		AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
		AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
		RoleDAOImpl rdao = new RoleDAOImpl();
		UserDAOImpl udao = new UserDAOImpl();
		String [] str = request.getRequestURI().split("/");
		if(request.getMethod().equals("POST")) {
//			List<User> users = udao.selectAllUsers();
//			boolean allowed = false;
			try {
			Double balanceinput = Double.parseDouble(request.getParameter("balance"));
			String typeinput = request.getParameter("type");
			String statusinput = request.getParameter("status");
			AccountStatus status = asdao.selectAccountStatusByName(statusinput);
			AccountType type = atdao.selectAccountTypeByName(typeinput);
			account.setBalance(balanceinput);
			account.setStatus(status);
			account.setType(type);
			adao.updateAccount(account);
			pw.write("The account was successfully updated");
			} catch (Exception e) {
				pw.write("There was an issue updating the acount");
				pw.write("<h3>This account belongs to "+owner.getFirstName()+" "+owner.getLastName()+" who is a "+owner.getRole().getRole()+"</h3>\r\n" + 
						"<form action=\"/rocp-project/Accounts/"+account.getAccountId()+"\" method=\"post\">\r\n" + 
						"		Balance: <input type = \"number\" name  = \"balance\" value=\""+account.getBalance()+"\"><br>\r\n"); 
						List<AccountType> types = atdao.selectAllAccountTypes();
						for(AccountType type : types) {
							String checked = "";
							if(type.getType().equals(account.getType().getType())) {checked = "checked";} else {checked="";}
							pw.write("<input type=\"radio\" id=\""+type.getType()+"\" name=\"type\" value=\""+type.getType()+"\""+checked+">\r\n" + 
									"<label for=\""+type.getType()+"\">"+type.getType()+"</label>");
						}
						pw.write("<br>");
						List<AccountStatus> statuses = asdao.selectAllAccountStatus();
						for(AccountStatus status: statuses) {
							String checked = "";
							if(status.getStatus().equals(account.getStatus().getStatus())) {checked = "checked";} else {checked="";}
							pw.write("<input type=\"radio\" id=\""+status.getStatus()+"\" name=\"status\" value=\""+status.getStatus()+"\""+checked+">\r\n" + 
									"<label for=\""+status.getStatus()+"\">"+status.getStatus()+"</label>");
						}

						pw.write("<br>");
						pw.write("		<br><button type=\"submit\" >Submit</button>\r\n" + 
						"	</form>");
			}
			
		}else {
			
			pw.write("<h3>This account belongs to "+owner.getFirstName()+" "+owner.getLastName()+" who is a "+owner.getRole().getRole()+"</h3>\r\n" + 
					"<form action=\"/rocp-project/Accounts/"+account.getAccountId()+"\" method=\"post\">\r\n" + 
					"		Balance: <input type = \"number\" name  = \"balance\" value=\""+account.getBalance()+"\"><br>\r\n"); 
					List<AccountType> types = atdao.selectAllAccountTypes();
					for(AccountType type : types) {
						String checked = "";
						if(type.getType().equals(account.getType().getType())) {checked = "checked";} else {checked="";}
						pw.write("<input type=\"radio\" id=\""+type.getType()+"\" name=\"type\" value=\""+type.getType()+"\""+checked+">\r\n" + 
								"<label for=\""+type.getType()+"\">"+type.getType()+"</label>");
					}
					pw.write("<br>");
					List<AccountStatus> statuses = asdao.selectAllAccountStatus();
					for(AccountStatus status: statuses) {
						String checked = "";
						if(status.getStatus().equals(account.getStatus().getStatus())) {checked = "checked";} else {checked="";}
						pw.write("<input type=\"radio\" id=\""+status.getStatus()+"\" name=\"status\" value=\""+status.getStatus()+"\""+checked+">\r\n" + 
								"<label for=\""+status.getStatus()+"\">"+status.getStatus()+"</label>");
					}

					pw.write("<br>");
					pw.write("		<br><button type=\"submit\" >Submit</button>\r\n" + 
					"	</form>");
			
			
			
		}
	}
}
