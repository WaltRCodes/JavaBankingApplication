package com.example.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dao.AccountDAOImpl;
import com.example.dao.RoleDAOImpl;
import com.example.dao.UserDAOImpl;
import com.example.models.User;

/**
 * Servlet implementation class login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
//		if(request.getSession(false)!=null) {
//			request.getRequestDispatcher("/Accounts").forward(request, response);
//		} else {
		//this redirects users to the login HTML file
			response.sendRedirect("http://localhost:8080/rocp-project/Login.html");
		//}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		try {
			UserDAOImpl udao = new UserDAOImpl();
			//this draws the list of users
			List<User> users = udao.selectAllUsers();
			boolean allowed = false;
			//this captures the username and password from the login form
			String usernameinput = request.getParameter("username");
			String passwordinput = request.getParameter("password");
			if(users.size()>0) {
				//this checks if the user account exist
				for(User user : users ){{if(user.loginCheck(usernameinput,passwordinput)) allowed = true;}}
			}
			if(allowed) {
				RoleDAOImpl rdao = new RoleDAOImpl();
				AccountDAOImpl adao = new AccountDAOImpl();
				//this gets the user entry based on the unique username and draws their information into an object
				User u = udao.selectUserByUserName(usernameinput);
				//this pulls the accounts that belong to the users
				u.setAccounts(adao.selectAccountByOwner(u));
				//this sets the role related to the database entry from the user entry to the user object
				u.setRole(rdao.selectRoleById(u.getRole().getRoleId()));
				
				HttpSession session = request.getSession(); //Either return the current session or create a new session.
				//this saves the user object in session memory
				session.setAttribute("user", u);
				//this sends the user request to the java file
				response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
				
			} else {
				//this redirects users to the failed login HTML file
				request.getRequestDispatcher("/FailedLogin.html").forward(request, response);
			}
		} catch (Exception e) {
			//this redirects users to the failed login HTML file
			request.getRequestDispatcher("/FailedLogin.html").forward(request, response);
		}
	}

}
