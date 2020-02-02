package pl.shopapp.web;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.shopapp.beans.UserBeanLocal;

/**
 * Servlet implementation class CustomerActivation
 */
@WebServlet("/CustomerActivation")
public class CustomerActivation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UserBeanLocal ubl;
	
//	for tests
	public CustomerActivation() {
		super();
	}

	//	for tests
	public CustomerActivation(UserBeanLocal ubl) {
		super();
		this.ubl = ubl;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(ubl.setActiveCustomer(request.getParameter("activationString")))
			request.setAttribute("message", "Aktywacja użytkownika zakończona powodzeniem - można się zalogować.");
		else 
			request.setAttribute("message", "Aktywacja uzytkownika nie powiodła się.<br>"
					+ "Możliwe przyczyny:<br>"
					+ "- czas pomiędzy rejestracją a aktywacją jest dłuższy niż 6 godzin.<br>"
					+ "- użytkownik nie został zidentyfikowany.");
	
		request.getRequestDispatcher("/jsp/customerActivation.jsp").forward(request, response);
	}
}
