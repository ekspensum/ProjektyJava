package pl.shopapp.web;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.shopapp.beans.BasketBeanLocal;
import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Category;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UserBeanLocal ubl;
	@EJB
	ProductBeanLocal pbl;
//	@EJB
	BasketBeanLocal bbl;
	private InitialContext ctx;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> catList = pbl.listCategory();
	    request.setAttribute("catList", catList);
		request.getRequestDispatcher("/jsp/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if (request.getParameter("loginButton") != null) {
			Validation valid = new Validation();
			String pass = valid.passwordToCode(request.getParameter("password"));
			if (valid.loginValidation(request.getParameter("login"))) {
				if (ubl.loginUser(request.getParameter("login"), pass) != null) {
					SessionData sd = ubl.loginUser(request.getParameter("login"), pass);
					try {
						ctx = new InitialContext();
						bbl = (BasketBeanLocal) ctx.lookup("java:global/ShopApp/ShopAppBeans/BasketBean!pl.shopapp.beans.BasketBeanLocal");
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sd.setBasketBeanLocal(bbl);
					request.getSession().setAttribute("SessionData", sd);
					request.getSession().setMaxInactiveInterval(1800);
					if (sd.getIdRole() == 3)
						response.sendRedirect("http://localhost:8080/ShopAppWeb/OperatorPanel");
				} else
					request.setAttribute("message", "Niepoprawne dane logowania");
			} else
				request.setAttribute("message", "Niepoprawne dane logowania");
		}

		doGet(request, response);
	}


}
