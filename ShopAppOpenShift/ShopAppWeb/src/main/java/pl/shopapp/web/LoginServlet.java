package pl.shopapp.web;

import java.io.IOException;
import javax.ejb.EJB;
import javax.naming.Context;
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

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserBeanLocal ubl;
	@EJB
	private ProductBeanLocal pbl;
//	@EJB
	private BasketBeanLocal bbl;
	private Context ctx;
	private double total = 0.0;


	//for tests
	public LoginServlet() {
		super();
	}
	//for tests	
	public LoginServlet(UserBeanLocal ubl, ProductBeanLocal pbl, BasketBeanLocal bbl, Context ctx) {
	super();
	this.ubl = ubl;
	this.pbl = pbl;
	this.bbl = bbl;
	this.ctx = ctx;
	}
	//	for tests
	public double getTotal() {
			return total;
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
	    request.setAttribute("catList", pbl.listCategory());
	    
		SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
		if(sd != null) {
			total = 0.0;
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
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		if (request.getParameter("loginButton") != null) {
			Validation valid = new Validation(ubl.getSettingsApp());
			String pass = valid.stringToCode(request.getParameter("password"));
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
					request.getSession().setMaxInactiveInterval(ubl.getSettingsApp().getSessionTime()*60);
					if (sd.getIdRole() == 3)
						response.sendRedirect("/ShopAppWeb/OperatorPanel");
				} else
					request.setAttribute("message", "Niepoprawne dane logowania lub użytkownik nie jest aktywny!");
			} else
				request.setAttribute("message", "Niepoprawne dane logowania!");
			
		} else	if (request.getParameter("buttonDeleteRowBasket") != null) {
//		allows delete product from basket
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
			
		} else	if(request.getParameter("searchProductButton") != null) {
//		for searching products
			request.setAttribute("resultSearchProducts", pbl.findProduct(request.getParameter("searchProductInput")));	
		}
		
		doGet(request, response);
	}
	

}
