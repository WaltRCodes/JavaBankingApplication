package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.controllers.UserController;
import com.example.dao.AccountDAOImpl;
import com.example.dao.RoleDAOImpl;
import com.example.dao.UserDAOImpl;
import com.example.models.Account;
import com.example.models.User;

/**
 * Servlet implementation class ModifyUser
 */
public class ModifyUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(); 
		User usersession = (User) session.getAttribute("user");
		if(request.getSession(false)==null) {
			System.out.println("There was no user logged into the session");
			response.sendError(400,"There was no user logged into the session");
		} else if(usersession.getRole().getRole().equals("Admin")||usersession.getRole().getRole().equals("Employee")) {
			
			UserDAOImpl udao = new UserDAOImpl();
			AccountDAOImpl adao = new AccountDAOImpl();
			RoleDAOImpl rdao = new RoleDAOImpl();
			//User user = (User) request.getSession(false).getAttribute("user");
			List<User> users = udao.selectAllUsers();
			if(users==null) {
				pw.write("<p>There are no users right now.</p>");
			} else {
				String [] str = request.getRequestURI().split("/");
				System.out.println(str.length);
				if(str.length<4) {
				pw.write("<p>Find the user by Id</p>\r\n" + 
						"	<form action=\"/rocp-project/Users/\" method=\"post\">\r\n" + 
						"		UserId: <input type = \"number\" name  = \"id\">\r\n" + 
						"		<button type=\"submit\" >Submit</button>\r\n" + 
						"	</form>");
				pw.write("<ul>");
				
				for(User u: users) {
					u.setRole(rdao.selectRoleById(u.getRole().getRoleId()));
					u.setAccounts(adao.selectAccountByOwner(u));
					pw.write("<li>");
					pw.write("<div>");
					pw.write(u.toString());
					pw.write("<form action=\"/rocp-project/Users/"+u.getUserId()+"\" method=\"get\">\r\n" + 
							"		<button type=\"submit\" >Edit</button>\r\n" + 
							"	</form>");
//					pw.write("<form action=\"/rocp-project/Users/"+a.getAccountId()+"\" method=\"get\">\r\n" + 
//							"		<button type=\"submit\" >Delete</button>\r\n" + 
//							"	</form>");
					pw.write("<div>");
					pw.write("</li>");
				}
				
				pw.write("</ul>");
				//} else {
				} else if(str.length==4) {
					User user = udao.selectUserById(Integer.parseInt(str[3]));
					user.setRole(rdao.selectRoleById(user.getRole().getRoleId()));
					user.setAccounts(adao.selectAccountByOwner(user));
					UserController.update(request, response, user);
				} else if(str.length==5&&str[4].equals("DELETE")) {
					User user = udao.selectUserById(Integer.parseInt(str[3]));
					user.setRole(rdao.selectRoleById(user.getRole().getRoleId()));
					user.setAccounts(adao.selectAccountByOwner(user));
					System.out.println("Delete button was pressed for "+ user.getUsername());
					if(user.getAccounts()!=null) {
						for(Account a: user.getAccounts()) {
							adao.deleteAccount(a);
						}
					}
					
					udao.deleteUser(user);
					response.sendRedirect("http://localhost:8080/rocp-project/Users/");
				}  else {
					response.sendRedirect("http://localhost:8080/rocp-project/Users/");
				}	
			 //}
			}
		}else {
			pw.write("<p>You do not have permission to view this page</p>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		if(request.getParameter("id")!=null) {
			response.sendRedirect("http://localhost:8080/rocp-project/Users/"+request.getParameter("id"));
		} else {
			doGet(request, response);
		}
	}

}
