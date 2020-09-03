package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dao.AccountDAOImpl;
import com.example.dao.AccountStatusDAOImpl;
import com.example.dao.AccountTypeDAOImpl;
import com.example.models.Account;
import com.example.models.AccountStatus;
import com.example.models.AccountType;
import com.example.models.User;

/**
 * Servlet implementation class Accounts
 */
public class Accounts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Accounts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//pw.write("<p>You are logged in</p>");
		if(request.getSession(false)==null) {
			System.out.println("There was no user logged into the session");
			response.sendError(400,"There was no user logged into the session");
		} else {
			//response.sendRedirect("http://localhost:8080/SignInMaven/Success.html");
			//PrintWriter pw = response.getWriter();
			PrintWriter pw = response.getWriter();
			AccountDAOImpl adao = new AccountDAOImpl();
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
			HttpSession session = request.getSession(); 
			User u = (User) session.getAttribute("user");
			if(u.getRole().getRole().contentEquals("Admin")||u.getRole().getRole().contentEquals("Employee")) {
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
			User user = (User) request.getSession(false).getAttribute("user");
			System.out.println(request.getSession(false).getAttribute("user"));
			List<Account> accounts = adao.selectAccountByOwner(user);
			pw.write("<div class=\"main\">\r\n"); 
			if(accounts==null) {
				pw.write("<p>You dont have any accounts right now.</p>"
						+ "<form action=\"/rocp-project/Accounts.html\" method=\"get\">\r\n" + 
						"		<button type=\"submit\">Open New Account</button>\r\n" + 
						"</form>");
			} else {
				pw.write("<form action=\"/rocp-project/Accounts.html\" method=\"get\">\r\n" + 
						"		<button type=\"submit\">Open New Account</button>\r\n" + 
						"</form>");
				pw.write("<p>Here are your accounts</p>"
						+ "<ul>");
				//loop account logic
				for(Account a: accounts) {
					AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
					AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
					AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
					System.out.println(a.getStatus().getStatusId());
					a.setStatus(as);
					AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
					System.out.println(a.getType().getTypeId());
					a.setType(at);
					pw.write("<li>");
					pw.write(a.toString());
					pw.write("<div class=\"row\">");
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
			pw.write("</div>");
			//request.getRequestDispatcher("/Accounts.html").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			AccountDAOImpl adao = new AccountDAOImpl();
			AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
			AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
//			List<User> users = udao.selectAllUsers();
//			boolean allowed = false;
			Double balanceinput = Double.parseDouble(request.getParameter("balance"));
			String typeinput = request.getParameter("type");
			System.out.println(typeinput);
			AccountStatus status = asdao.selectAccountStatusByName("Pending");
			AccountType type = atdao.selectAccountTypeByName(typeinput);
			System.out.println(type.getType());
			Account temp = new Account(-1, balanceinput,status,type, null);
			User user = (User) request.getSession(false).getAttribute("user");
			temp = adao.insertAccount(temp, user);
			
//			AccountStatus as = asdao.selectAccountStatusById(temp.getStatus().getStatusId());
//			temp.setStatus(as);
//			AccountType at = atdao.selectAccountTypeById(temp.getType().getTypeId());
//			temp.setType(at);
//			System.out.println(as.getStatus());
//			System.out.println(at.getType());
			//temp.setAccounts(adao.selectAccountByOwner(temp));
//			if(users.size()>0) {
//				for(User user : users ){{if(user.loginCheck(usernameinput,passwordinput)) allowed = true;}}
//			}
//			if(allowed) {
			//HttpSession session = request.getSession(); //Either return the current session or create a new session.
			//session.setAttribute("user", temp);
			//response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
			this.doGet(request, response);	
//			} else {
//				request.getRequestDispatcher("/FailedLogin.html").forward(request, response);
//			}
		} catch (Exception e) {
			request.getRequestDispatcher("/FailedAccounts.html").forward(request, response);
		}
	}

}
