package pl.shopapp.web;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.User;

/**
 * Servlet implementation class AddPrivateCustomer
 */
@WebServlet("/AddCustomer")
public class AddCustomer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UserBeanLocal ubl;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("jsp/addCustomer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Validation valid = new Validation();
		
		User u = new User();
		u.setLogin(request.getParameter("login"));
		u.setPassword(valid.passwordToCode(request.getParameter("password")));
		u.setActive(true);
		
		Customer c = new Customer();
		c.setFirstName(request.getParameter("firstName"));
		c.setLastName(request.getParameter("lastName"));
		c.setPesel(request.getParameter("pesel"));
		c.setZipCode(request.getParameter("zipCode"));
		c.setCountry(request.getParameter("country"));
		c.setCity(request.getParameter("city"));
		c.setStreet(request.getParameter("street"));
		c.setStreetNo(request.getParameter("streetNo"));
		c.setUnitNo(request.getParameter("unitNo"));
		c.setEmail(request.getParameter("email"));
		c.setCompany(request.getParameter("isCompany").equals("yes") ? true : false);
		c.setCompanyName(request.getParameter("companyName"));
		c.setTaxNo(request.getParameter("taxNo"));
		c.setRegon(request.getParameter("regon"));
		c.setDateRegistration(LocalDateTime.now());
		c.setUser(u);
		
		ubl.addCustomer(c, u);
		
		SessionData sd = ubl.loginCustomer(u.getLogin(), u.getPassword());
		request.getSession().setAttribute("SessionData", sd);
		
		doGet(request, response);
	}

}
