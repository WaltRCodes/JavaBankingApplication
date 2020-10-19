package com.example.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.UserDAOImpl;
import com.example.models.User;

public class UserController {
	//this controller handles changes to user account information
	public static void update(HttpServletRequest request, HttpServletResponse response,User u) throws IOException {
		//this PrintWriter object is used to create HTML responses once certain request are called
		PrintWriter pw = response.getWriter();
		String [] str = request.getRequestURI().split("/");
		//first I check if the request is a POST request
		if(request.getMethod().equals("POST")) {
				//if it is then I capture the values of the form fields and match them to the keys in the user object
				u.setUsername(request.getParameter("username"));
				u.setPassword(request.getParameter("password"));
				u.setFirstName(request.getParameter("first"));
				u.setLastName(request.getParameter("last"));
				u.setEmail(request.getParameter("email"));
//				if(!u.getRole().getRole().equals("Premium")) {
//					if(request.getParameter("role").equals("Yes")) {
//						//add payment option
//						RoleDAOImpl rdao = new RoleDAOImpl();
//						Role r = rdao.selectRoleByName("Premium");
//						u.setRole(r);
//					}
//				}
				//I then use the UserDAO to push the updated user object into the respective entry in the database
				UserDAOImpl udao = new UserDAOImpl();
				udao.updateUser(u);
				//I let the user know that there account was successfully updated and redirect them to a new page
				pw.write("<p>Account was updated successfully</p>");
				response.sendRedirect("http://localhost:8080/rocp-project/Users/");
			
		} else {
			//if any other of request rather than a post was sent, I first check if there was a user logged into the session
			if(request.getSession(false)==null) {
				//if not, I send an error
				System.out.println("There was no user logged into the session");
				response.sendError(400,"There was no user logged into the session");
			} else {
				//response.sendRedirect("http://localhost:8080/SignInMaven/Success.html");
				//If a user is logged in, I print out the form that is populated with the user info and can be used to modify the account
				
				pw.write("<h1>Here is your Profile info</h1>\r\n" + 
						"<form action=\"/rocp-project/Users/"+str[3]+"\" method=\"post\">\r\n" + 
						"		Username: <input type = \"text\" name  = \"username\" placeholder = \"username\" value=\""+u.getUsername()+"\"><br>\r\n" + 
						"		Password: <input type = \"password\" name  = \"password\" placeholder = \"password\" value=\""+u.getPassword()+"\"><br>\r\n" + 
						"		First Name: <input type = \"text\" name  = \"first\" placeholder = \"First Name\" value=\""+u.getFirstName()+"\"><br>\r\n" + 
						"		Last Name: <input type = \"text\" name  = \"last\" placeholder = \"Last Name\" value=\""+u.getLastName()+"\"><br>\r\n" + 
						"		Email: <input type = \"email\" name  = \"email\" placeholder = \"email\" value=\""+u.getEmail()+"\"><br>\r\n" 
						);
//				if(!u.getRole().getRole().equals("Premium")) {
//					pw.write("		Upgrade to Premium account?\r\n" + 
//							"		<input type=\"radio\" id=\"Yes\" name=\"role\" value=\"Yes\">\r\n" + 
//							"		<label for=\"Yes\">Yes</label>\r\n" + 
//							"		<input type=\"radio\" id=\"No\" name=\"role\" value=\"No\">\r\n" + 
//							"		<label for=\"No\">No</label>\r\n" 
//							);
//				}
				pw.write("		<button type=\"submit\" >Submit</button>\r\n" + 
							"	</form>");
				
				pw.write("<form action=\"/rocp-project/Users/"+str[3]+"/DELETE\" method=\"get\">\r\n" + 
						"		<button type=\"submit\" >Delete User</button>\r\n" + 
						"	</form>");
			}
			
		}
		
		
	}
}
