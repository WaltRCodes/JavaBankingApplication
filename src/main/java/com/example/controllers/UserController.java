package com.example.controllers;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.example.models.Role;
import com.example.models.User;

public class UserController {

	public static void update(HttpServletRequest request, HttpServletResponse response,User u) throws IOException {
		PrintWriter pw = response.getWriter();
		String [] str = request.getRequestURI().split("/");
		if(request.getMethod().equals("POST")) {
			
				u.setUsername(request.getParameter("username"));
				u.setPassword(request.getParameter("password"));
				u.setFirstName(request.getParameter("first"));
				u.setLastName(request.getParameter("last"));
				u.setEmail(request.getParameter("email"));
				if(!u.getRole().getRole().equals("Premium")) {
					if(request.getParameter("role").equals("Yes")) {
						//add payment option
						RoleDAOImpl rdao = new RoleDAOImpl();
						Role r = rdao.selectRoleByName("Premium");
						u.setRole(r);
					}
				}
				UserDAOImpl udao = new UserDAOImpl();
				udao.updateUser(u);
				
				pw.write("<p>Account was updated successfully</p>");
				response.sendRedirect("http://localhost:8080/rocp-project/Users/");
			
		} else {
			
			if(request.getSession(false)==null) {
				System.out.println("There was no user logged into the session");
				response.sendError(400,"There was no user logged into the session");
			} else {
				//response.sendRedirect("http://localhost:8080/SignInMaven/Success.html");
				
				
				pw.write("<h1>Here is your Profile info</h1>\r\n" + 
						"<form action=\"/rocp-project/Users/"+str[3]+"\" method=\"post\">\r\n" + 
						"		Username: <input type = \"text\" name  = \"username\" placeholder = \"username\" value=\""+u.getUsername()+"\">\r\n" + 
						"		Password: <input type = \"password\" name  = \"password\" placeholder = \"password\" value=\""+u.getPassword()+"\">\r\n" + 
						"		First Name: <input type = \"text\" name  = \"first\" placeholder = \"First Name\" value=\""+u.getFirstName()+"\">\r\n" + 
						"		Last Name: <input type = \"text\" name  = \"last\" placeholder = \"Last Name\" value=\""+u.getLastName()+"\">\r\n" + 
						"		Email: <input type = \"email\" name  = \"email\" placeholder = \"email\" value=\""+u.getEmail()+"\">\r\n" 
						);
				if(!u.getRole().getRole().equals("Premium")) {
					pw.write("		Upgrade to Premium account?\r\n" + 
							"		<input type=\"radio\" id=\"Yes\" name=\"role\" value=\"Yes\">\r\n" + 
							"		<label for=\"Yes\">Yes</label>\r\n" + 
							"		<input type=\"radio\" id=\"No\" name=\"role\" value=\"No\">\r\n" + 
							"		<label for=\"No\">No</label>\r\n" 
							);
				}
				pw.write("		<button type=\"submit\" >Submit</button>\r\n" + 
							"	</form>");
				
				pw.write("<form action=\"/rocp-project/Users/"+str[3]+"/DELETE\" method=\"get\">\r\n" + 
						"		<button type=\"submit\" >Delete User</button>\r\n" + 
						"	</form>");
			}
			
		}
		
		
	}
}
