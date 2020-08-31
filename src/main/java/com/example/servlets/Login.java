package com.example.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			List<User> users = udao.selectAllUsers();
			boolean allowed = false;
			String usernameinput = request.getParameter("username");
			String passwordinput = request.getParameter("password");
			if(users.size()>0) {
				for(User user : users ){{if(user.loginCheck(usernameinput,passwordinput)) allowed = true;}}
			}
			if(allowed) {
				HttpSession session = request.getSession(); //Either return the current session or create a new session.
				session.setAttribute("user", udao.selectUserByUserName(usernameinput));
				response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
				
			} else {
				request.getRequestDispatcher("/FailedLogin.html").forward(request, response);
			}
		} catch (Exception e) {
			request.getRequestDispatcher("/FailedLogin.html").forward(request, response);
		}
	}

}
