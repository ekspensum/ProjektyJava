package pl.shopapp.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.beans.Validation;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UserBeanLocal ubl;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		onLoadPage();
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {

		if (request.getParameter("loginButton") != null) {
			Validation valid = new Validation();
			String pass = valid.passwordToCode(request.getParameter("password"));
			String login = "";
			if (valid.loginValidation(request.getParameter("login"))) {
				login = request.getParameter("login");
				SessionData sd = null;
				if (ubl.loginCustomer(login, pass) != null) {
					sd = ubl.loginCustomer(login, pass);
					if (request.getSession().getAttribute("SessionData") == null) {
						request.getSession().setAttribute("SessionData", sd);
						request.getSession().setMaxInactiveInterval(1800);
					}
				} else
					request.setAttribute("message", "Niepoprawne dane logowania");
			} else
				request.setAttribute("message", "Niepoprawne dane logowania");
		}

		doGet(request, response);
	}
	
	public void onLoadPage(){
		
	}

}
