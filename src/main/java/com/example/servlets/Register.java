package com.example.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dao.AccountDAOImpl;
import com.example.dao.RoleDAOImpl;
import com.example.dao.UserDAOImpl;
import com.example.models.Account;
import com.example.models.Role;
import com.example.models.User;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getSession(false)!=null) {
			request.getRequestDispatcher("/Accounts").forward(request, response);
		} else {
			response.sendRedirect("http://localhost:8080/rocp-project/Register.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		try {
			UserDAOImpl udao = new UserDAOImpl();
			RoleDAOImpl rdao = new RoleDAOImpl();
			AccountDAOImpl adao = new AccountDAOImpl();
//			List<User> users = udao.selectAllUsers();
//			boolean allowed = false;
			String usernameinput = request.getParameter("username");
			String passwordinput = request.getParameter("password");
			String firstname = request.getParameter("first");
			String lastname = request.getParameter("last");
			String email = request.getParameter("email");
			String role = request.getParameter("role");
			Role r = rdao.selectRoleByName(role);
			User temp = new User(-1, usernameinput, passwordinput,  firstname, lastname, email,
					r, new ArrayList<>());
			temp = udao.insertUser(temp);
			temp.setAccounts(adao.selectAccountByOwner(temp));
//			if(users.size()>0) {
//				for(User user : users ){{if(user.loginCheck(usernameinput,passwordinput)) allowed = true;}}
//			}
//			if(allowed) {
			HttpSession session = request.getSession(); //Either return the current session or create a new session.
			session.setAttribute("user", temp);
			response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
				
//			} else {
//				request.getRequestDispatcher("/FailedLogin.html").forward(request, response);
//			}
		} catch (Exception e) {
			request.getRequestDispatcher("/FailedRegister.html").forward(request, response);
		}
	}

}
