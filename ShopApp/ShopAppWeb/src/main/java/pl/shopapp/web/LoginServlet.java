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
		SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
		if(sd != null) {
			double total = 0.0;
			for(int i=0; i<sd.getBasketBeanLocal().getBasketData().size(); i++){
				total += sd.getBasketBeanLocal().getBasketData().get(i).getPrice() * sd.getBasketBeanLocal().getBasketData().get(i).getQuantity(); 
			}
			request.setAttribute("total", total);			
		}
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
		
//		allows delete product from basket
		if (request.getParameter("buttonDeleteRowBasket") != null) {
			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
			bbl = sd.getBasketBeanLocal();
			String[] deletedId = request.getParameterValues("chbxDeleteRow");
			if (deletedId != null) {
				for (int i = 0; i < deletedId.length; i++) {
					for (int j = 0; j < bbl.getBasketData().size(); j++)
						if (bbl.getBasketData().get(j).getProductId() == Integer.valueOf(deletedId[i])) {
							bbl.getBasketData().remove(j);
							break;
						}
				}
			} else
				request.setAttribute("message", "Proszę zaznaczyć co njmniej jeden produkt do usunięcia!");
		}
		
		doGet(request, response);
	}


}
