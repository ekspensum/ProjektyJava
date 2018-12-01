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
import pl.shopapp.beans.Validation;
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
		request.getRequestDispatcher("/jsp/addCustomer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
//		set the helper class, which keeps data in fields form after sent request
		RequestAttribute ra = new RequestAttribute();
		ra.setLogin(request.getParameter("login"));
		ra.setPassword(request.getParameter("password"));
		ra.setFirstName(request.getParameter("firstName"));
		ra.setLastName(request.getParameter("lastName"));
		ra.setPesel(request.getParameter("pesel"));
		ra.setCountry(request.getParameter("country"));
		ra.setZipCode(request.getParameter("zipCode"));
		ra.setCity(request.getParameter("city"));
		ra.setStreet(request.getParameter("street"));
		ra.setStreetNo(request.getParameter("streetNo"));
		ra.setUnitNo(request.getParameter("unitNo"));
		ra.setEmail(request.getParameter("email"));
		if (request.getParameter("isCompany") != null) {
			if(request.getParameter("isCompany").equals("yes")) {
				ra.setIsCompany("checked");
				ra.setCompnyName(request.getParameter("compnyName"));
				ra.setTaxNo(request.getParameter("taxNo"));
				ra.setRegon(request.getParameter("regon"));				
			}

		} else {
			ra.setIsCompany("");
			ra.setCompnyName(request.getParameter(""));
			ra.setTaxNo(request.getParameter(""));
			ra.setRegon(request.getParameter(""));
		}	
		request.setAttribute("RequestAttribute", ra);
		
//		Validation
		if (request.getParameter("buttonAddCustomer") != null) {
			boolean validOK = true;
			Validation valid = new Validation();
			if (request.getParameter("login").equals("")
					|| !valid.loginValidation(request.getParameter("login"))) {
				validOK = false;
				request.setAttribute("message", "Pole login jest puste lub zawiera niepoprawne znaki!");
			}
			if (request.getParameter("password").equals("")
					|| !valid.passwordValidation(request.getParameter("password"))) {
				validOK = false;
				request.setAttribute("message", "Pole hasło jest puste lub zawiera znaki niezgodne z polityką bezpieczeństwa!");
			}
			if (request.getParameter("firstName").equals("")
					|| !valid.nameValidation(request.getParameter("firstName"))) {
				validOK = false;
				request.setAttribute("message", "Pole imię jest puste lub zawiera niepoprawne znaki!");
			}
			if (request.getParameter("lastName").equals("")
					|| !valid.nameValidation(request.getParameter("lastName"))) {
				validOK = false;
				request.setAttribute("message", "Pole nazwisko jest puste lub zawiera niepoprawne znaki!");
			}
			if (request.getParameter("pesel").equals("")
					|| !valid.peselValidation(request.getParameter("pesel"))) {
				validOK = false;
				request.setAttribute("message", "Pole pesel jest puste lub zawiera niepoprawne znaki!");
			}
			if (request.getParameter("country").equals("")
					|| !valid.nameValidation(request.getParameter("country"))) {
				validOK = false;
				request.setAttribute("message", "Pole kraj jest puste lub zawiera niepoprawne znaki!");
			}
			if (request.getParameter("zipCode").equals("")
					|| !valid.zipCodeValidation(request.getParameter("zipCode"))) {
				validOK = false;
				request.setAttribute("message", "Pole kod pocztowy jest puste lub zawiera niepoprawne znaki!");
			}
			if (request.getParameter("city").equals("")
					|| !valid.nameValidation(request.getParameter("city"))) {
				validOK = false;
				request.setAttribute("message", "Pole miasto jest puste lub zawiera niepoprawne znaki!");
			}
			if (request.getParameter("street").equals("")
					|| !valid.nameValidation(request.getParameter("street"))) {
				validOK = false;
				request.setAttribute("message", "Pole ulica jest puste lub zawiera niepoprawne znaki!");
			}
			if (request.getParameter("streetNo").equals("")
					|| !valid.locationValidation(request.getParameter("streetNo"))) {
				validOK = false;
				request.setAttribute("message", "Pole nr domu jest puste lub zawiera niepoprawne znaki!");
			}
			if(!request.getParameter("unitNo").equals(""))
				if (!valid.locationValidation(request.getParameter("unitNo"))) {
					validOK = false;
					request.setAttribute("message", "Pole nr lokalu zawiera niepoprawne znaki!");
				}
			if (request.getParameter("email").equals("")
					|| !valid.emailValidation(request.getParameter("email"))) {
				validOK = false;
				request.setAttribute("message", "Pole e-mail jest puste lub zawiera niepoprawne znaki!");
			}
			if (request.getParameter("isCompany") != null) {
				if(request.getParameter("isCompany").equals("yes")) {
					if (request.getParameter("compnyName").equals("")
							|| !valid.nameValidation(request.getParameter("compnyName"))) {
						validOK = false;
						request.setAttribute("message", "Pole nazwa firmy jest puste lub zawiera niepoprawne znaki!");
					}				
					if (request.getParameter("taxNo").equals("")
							|| !valid.nipValidation(request.getParameter("taxNo"))) {
						validOK = false;
						request.setAttribute("message", "Pole NIP jest puste lub zawiera niepoprawne znaki!");
					}					
					if (request.getParameter("regon").equals("")
							|| !valid.regonValidation(request.getParameter("regon"))) {
						validOK = false;
						request.setAttribute("message", "Pole REGON jest puste lub zawiera niepoprawne znaki!");
					}						
				}
							
			}
			
			if (validOK) {
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
				if (request.getParameter("isCompany") != null) {
					c.setCompany(request.getParameter("isCompany").equals("yes") ? true : false);
					c.setCompanyName(request.getParameter("companyName"));
					c.setTaxNo(request.getParameter("taxNo"));
					c.setRegon(request.getParameter("regon"));					
				}
				c.setDateRegistration(LocalDateTime.now());
				c.setUser(u);

				ubl.addCustomer(c, u);
				SessionData sd = ubl.loginUser(u.getLogin(), u.getPassword());
				request.getSession().setAttribute("SessionData", sd);
				request.getSession().setMaxInactiveInterval(1800);
				request.setAttribute("message", "Klient został dodany do bazy danych!");
			}
		}
		
		doGet(request, response);
	}

}
