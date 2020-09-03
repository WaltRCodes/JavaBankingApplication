package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.controllers.AccountResultsController;
import com.example.controllers.DepositController;
import com.example.controllers.TransferController;
import com.example.controllers.WithdrawController;
import com.example.dao.AccountDAOImpl;
import com.example.dao.AccountStatusDAOImpl;
import com.example.dao.AccountTypeDAOImpl;
import com.example.dao.RoleDAOImpl;
import com.example.dao.UserDAOImpl;
import com.example.models.Account;
import com.example.models.AccountStatus;
import com.example.models.AccountType;
import com.example.models.User;

/**
 * Servlet implementation class ModifyAccount
 */
public class ModifyAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyAccount() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getSession(false)==null) {
			System.out.println("There was no user logged into the session");
			response.sendError(400,"There was no user logged into the session");
		} else {
			
			PrintWriter pw = response.getWriter();
			pw.write("<style>\r\n" + 
					"*{\r\n" + 
					"padding:0;\r\n" + 
					"margin:0;}"+
					".main {display: flex;\r\n" + 
					"flex-direction: column;\r\n" + 
					"align-items:center;}\r\n" + 
					".main > *{\r\n" + 
					"padding-top:25px;}\r\n" 
							+ ".row{\r\n" + 
							"display:flex;\r\n" + 
							"flex-direction:row;}\r\n" + 
							".row>*{\r\n" + 
							"padding-left:10px;}"+
							"button{\r\n" + 
							"background-color:coral;\r\n" + 
							"border-style:none;\r\n" + 
							"border-radius:30px;\r\n" + 
							"color:white;\r\n" + 
							"padding:10px;}" +
							".navbar{\r\n" + 
							"display:flex;\r\n" + 
							"flex-direction:row;\r\n" + 
							"background-color:coral;\r\n" + 
							"width:100%;}"+
					"</style>");
			pw.write("<div class=\"navbar\">\r\n"); 
			//HttpSession session = request.getSession(); 
			User user = (User) request.getSession(false).getAttribute("user");
			if(user.getRole().getRole().contentEquals("Admin")||user.getRole().getRole().contentEquals("Employee")) {
					pw.write("	<form action=\"/rocp-project/Users/\" method=\"get\">\r\n" + 
					"		<button type=\"submit\">Users</button>\r\n" + 
					"	</form>\r\n");
			
					pw.write("	<form action=\"/rocp-project/Accounts/All\" method=\"get\">\r\n" + 
							"		<button type=\"submit\">All Accounts</button>\r\n" + 
							"	</form>\r\n");
			}	
					pw.write("	<form action=\"/rocp-project/ProfileSettings\" method=\"get\">\r\n" + 
					"		<button type=\"submit\">Profile</button>\r\n" + 
					"	</form>\r\n" + 
					"	<form action=\"/rocp-project/Logout\" method=\"post\">\r\n" + 
					"		<button type=\"submit\" >Logout</button>\r\n" + 
					"	</form>\r\n" + 
					"</div>");
			pw.write("<div class=\"main\">\r\n"); 
			AccountDAOImpl adao = new AccountDAOImpl();
			//User user = (User) request.getSession(false).getAttribute("user");
			List<Account> accounts = adao.selectAccountByOwner(user);
			if(accounts==null) {
				pw.write("<p>You dont have any accounts right now.</p>");
			} else {
				String [] str = request.getRequestURI().split("/");
				//boolean allowed = false;
				//boolean allowed = true;
				for(Account a: accounts) {
					AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
					AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
					AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
					a.setStatus(as);
					AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
					a.setType(at);
//					if(Integer.parseInt(str[4])==a.getAccountId()) {
//						//&&!a.getStatus().getStatus().equals("Pending") add this back in later
//						allowed = true;
//						break;
//					}
					//move this down
				}
				boolean access = false;
				if(user.getRole().getRole().contentEquals("Admin")||user.getRole().getRole().contentEquals("Employee")) {
					access = true;
				}
				try {

					System.out.println(str.length);
					if(str[3].contentEquals("Deposit")) {
						Account a = adao.selectAccountById(Integer.parseInt(str[4]));
						AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
						AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
						AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
						a.setStatus(as);
						AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
						a.setType(at);
						if(a.getStatus().getStatus().equals("Open")) {
							DepositController.deposit(request, response,Integer.parseInt(str[4]));
						} else {
							pw.write("<p>This account needs to approved before it can be used</p>");
						}
						
					} else if(str[3].contentEquals("Withdraw")) {
						Account a = adao.selectAccountById(Integer.parseInt(str[4]));
						AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
						AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
						AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
						a.setStatus(as);
						AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
						a.setType(at);
						if(a.getStatus().getStatus().equals("Open")) {
						WithdrawController.withdraw(request, response, Integer.parseInt(str[4]));
						} else {
							pw.write("<p>This account needs to approved before it can be used</p>");
						}
					} else if(str[3].contentEquals("Transfer")) {
						Account a = adao.selectAccountById(Integer.parseInt(str[4]));
						AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
						AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
						AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
						a.setStatus(as);
						AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
						a.setType(at);
						if(a.getStatus().getStatus().equals("Open")) {
						TransferController.transfer(request, response, Integer.parseInt(str[4]));
						} else {
							pw.write("<p>This account needs to approved before it can be used</p>");
						}
					} else if(str[3].contentEquals("All")&&access) {
						//can only dwt if its not pending which can only be changed by admin on specific page which only they can see/ have access to
						AccountResultsController.all(request, response);
					} else if(str[3].contentEquals("Status")&&access) {
						//can only dwt if its not pending which can only be changed by admin on specific page which only they can see/ have access to
						//check length
						AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
						AccountStatus status = asdao.selectAccountStatusById(Integer.parseInt(str[4]));
						AccountResultsController.status(request, response, status);
					} else if(str[3].contentEquals("Owner")&&access) {
						//can only dwt if its not pending which can only be changed by admin on specific page which only they can see/ have access to
						//check length
						UserDAOImpl udao = new UserDAOImpl();
						User owner = udao.selectUserById(Integer.parseInt(str[4]));
						AccountResultsController.owner(request, response, owner);
					}  else if(str[3].contentEquals("Delete")) {
						Account a = adao.selectAccountById(Integer.parseInt(str[4]));
						AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
						AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
						AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
						a.setStatus(as);
						AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
						a.setType(at);
						System.out.print("Delete button was pressed for "+ a.toString());
						adao.deleteAccount(a);
						response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
					} else {
						//length conditional
						if(str.length>3) {
							AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
							AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
							RoleDAOImpl rdao = new RoleDAOImpl();
							UserDAOImpl udao = new UserDAOImpl();
							Account a = adao.selectAccountById(Integer.parseInt(str[3]));
							AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
							a.setStatus(as);
							AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
							a.setType(at);
							User owner = udao.selectUserById(adao.getAccountOwner(a));
							owner.setRole(rdao.selectRoleById(owner.getRole().getRoleId()));
							AccountResultsController.update(request, response, a, owner);
						} else {
							response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
						}
						
					}
				} catch (Exception e) {
					pw.write("<p>The resource you are looking for is not available</p>");
					response.sendError(401,"The requested action is not permitted");
			 }
			}
			pw.write("</div>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
