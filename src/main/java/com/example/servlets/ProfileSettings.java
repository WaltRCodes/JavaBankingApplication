package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.UserDAOImpl;
import com.example.models.User;

/**
 * Servlet implementation class ProfileSettings
 */
public class ProfileSettings extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileSettings() {
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
			//response.sendRedirect("http://localhost:8080/SignInMaven/Success.html");
			PrintWriter pw = response.getWriter();
			User user = (User) request.getSession(false).getAttribute("user");
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
			//User user = (User) request.getSession(false).getAttribute("user");
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
			pw.write("<h1>Here is your Profile info</h1>\r\n" + 
					"<form action=\"/rocp-project/ProfileSettings\" method=\"post\">\r\n" + 
					"		Username: <input type = \"text\" name  = \"username\" placeholder = \"username\" value=\""+user.getUsername()+"\"><br>\r\n" + 
					"		Password: <input type = \"password\" name  = \"password\" placeholder = \"password\" value=\""+user.getPassword()+"\"><br>\r\n" + 
					"		First Name: <input type = \"text\" name  = \"first\" placeholder = \"First Name\" value=\""+user.getFirstName()+"\"><br>\r\n" + 
					"		Last Name: <input type = \"text\" name  = \"last\" placeholder = \"Last Name\" value=\""+user.getLastName()+"\"><br>\r\n" + 
					"		Email: <input type = \"email\" name  = \"email\" placeholder = \"email\" value=\""+user.getEmail()+"\"><br>\r\n" 
					);
//			if(!user.getRole().getRole().equals("Premium")) {
//				pw.write("		Upgrade to Premium account?\r\n" + 
//						"		<input type=\"radio\" id=\"Yes\" name=\"role\" value=\"Yes\">\r\n" + 
//						"		<label for=\"Yes\">Yes</label>\r\n" + 
//						"		<input type=\"radio\" id=\"No\" name=\"role\" value=\"No\">\r\n" + 
//						"		<label for=\"No\">No</label>\r\n" 
//						);
//			}
			pw.write("		<button type=\"submit\" >Submit</button>\r\n" + 
						"	</form>");
			pw.write("</div>");
			//for the delete make new accounts in closed status
			//request.getRequestDispatcher("/ProfileSettings.html").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		User user = (User) request.getSession(false).getAttribute("user");
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setFirstName(request.getParameter("first"));
		user.setLastName(request.getParameter("last"));
		user.setEmail(request.getParameter("email"));
//		if(!user.getRole().getRole().equals("Premium")) {
//			if(request.getParameter("role").equals("Yes")) {
//				//add payment option
//				RoleDAOImpl rdao = new RoleDAOImpl();
//				Role r = rdao.selectRoleByName("Premium");
//				user.setRole(r);
//			}
//		}
		UserDAOImpl udao = new UserDAOImpl();
		udao.updateUser(user);
		PrintWriter pw = response.getWriter();
		pw.write("<p>Account was updated successfully</p>");
		response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
	}

}
