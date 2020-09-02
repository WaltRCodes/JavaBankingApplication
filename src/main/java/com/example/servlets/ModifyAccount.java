package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.controllers.DepositController;
import com.example.controllers.TransferController;
import com.example.controllers.WithdrawController;
import com.example.dao.AccountDAOImpl;
import com.example.dao.AccountStatusDAOImpl;
import com.example.dao.AccountTypeDAOImpl;
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
			AccountDAOImpl adao = new AccountDAOImpl();
			User user = (User) request.getSession(false).getAttribute("user");
			List<Account> accounts = adao.selectAccountByOwner(user);
			if(accounts==null) {
				pw.write("<p>You dont have any accounts right now.</p>");
			} else {
				String [] str = request.getRequestURI().split("/");
				boolean allowed = false;
				for(Account a: accounts) {
					AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
					AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
					AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
					a.setStatus(as);
					AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
					a.setType(at);
					if(Integer.parseInt(str[4])==a.getAccountId()) {
						//&&!a.getStatus().getStatus().equals("Pending") add this back in later
						allowed = true;
						break;
					}
				}
			
				if(allowed) {

					System.out.println(str[4]);
					if(str[3].contentEquals("Deposit")) {
						DepositController.deposit(request, response,Integer.parseInt(str[4]));
					} else if(str[3].contentEquals("Withdraw")) {
						WithdrawController.withdraw(request, response, Integer.parseInt(str[4]));
					} else if(str[3].contentEquals("Transfer")) {
						TransferController.transfer(request, response, Integer.parseInt(str[4]));;
					} else if(str[3].contentEquals("Delete")) {
						Account a = adao.selectAccountById(Integer.parseInt(str[4]));
						AccountStatusDAOImpl asdao = new AccountStatusDAOImpl();
						AccountTypeDAOImpl atdao = new AccountTypeDAOImpl();
						AccountStatus as = asdao.selectAccountStatusById(a.getStatus().getStatusId());
						a.setStatus(as);
						AccountType at = atdao.selectAccountTypeById(a.getType().getTypeId());
						a.setType(at);
						System.out.print("Delete button was pressed for "+ a.toString());
					} else {
						response.sendRedirect("http://localhost:8080/rocp-project/Accounts");
					}
				} else {
					pw.write("<p>This account isn't yours or is still in pending approval</p>");
					response.sendError(401,"The requested action is not permitted");
			 }
			}
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
