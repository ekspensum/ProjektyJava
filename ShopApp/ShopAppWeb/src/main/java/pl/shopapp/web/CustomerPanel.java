package pl.shopapp.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.TransactionBeanLocal;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Transaction;
import pl.shopapp.entites.User;

/**
 * Servlet implementation class AddPrivateCustomer
 */
@WebServlet("/CustomerPanel")
public class CustomerPanel extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	UserBeanLocal ubl;
	@EJB
	TransactionBeanLocal tbl;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/customerPanel.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub

		if (request.getParameter("buttonAddCustomer") != null) {
			// TODO create new customer and add to database, also create session web with new customer data
			User u = new User();
			Customer c = new Customer();
			if(validationAndSetup(request, u, c)) {
				ubl.addCustomer(c, u);
				SessionData sd = ubl.loginUser(u.getLogin(), u.getPassword());
				request.getSession().setAttribute("SessionData", sd);
				request.getSession().setMaxInactiveInterval(1800);
				request.setAttribute("message", "Klient został dodany do bazy danych!");			
			}		
		}
		
		if(request.getParameter("buttonOpenEdit") != null) {
			// TODO Auto-generated method stub
			Validation valid = new Validation();
			String pass = valid.passwordToCode(request.getParameter("password"));
			if (valid.loginValidation(request.getParameter("login"))) {
				if (ubl.loginUser(request.getParameter("login"), pass) != null) {
					SessionData loginSD = ubl.loginUser(request.getParameter("login"), pass);
					SessionData currentSD = (SessionData) request.getSession().getAttribute("SessionData");
					if(loginSD.getIdUser() == currentSD.getIdUser()) {
						User u = ubl.findUser(currentSD.getIdUser());
						Customer c = ubl.findCustomer(u);
						request.setAttribute("openToEdit", "yes");
						request.setAttribute("userData", u);
						request.setAttribute("customerData", c);						
					} else
						request.setAttribute("message", "Dane logowania niezgodne z bieżącą sesją!");
				} else
					request.setAttribute("message", "Niepoprawne dane logowania lub użytkownik nie jest aktywny!");
			} else
				request.setAttribute("message", "Niepoprawne dane logowania!");
		}
		
		if(request.getParameter("buttonSaveEdit") != null) {
			// TODO Auto-generated method stub
			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
			User u = new User();
			Customer c = new Customer();
			request.setAttribute("saveEdit", "yes");
			if(validationAndSetup(request, u, c)) {
				if(ubl.updateCustomer(u, c, sd.getIdUser())) {
					request.setAttribute("message", "Zaktualizowano dane klienta w bazie danych!");	
					request.setAttribute("openToEdit", "no");
					request.setAttribute("saveEdit", "no");					
				} else
					request.setAttribute("message", "Nie udało się zaktualizować danych klienta!");	
			} else
				request.setAttribute("saveEdit", "yes");
		}
		
		if(request.getParameter("buttonSearchTransaction") != null) {
			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String dateFrom = request.getParameter("searchProductDateFrom")+" 00:00:00";
			String dateTo = request.getParameter("searchProductDateTo")+" 23:59:59";
			try {
				List<Transaction> tr = tbl.getTransactionsData(sd.getIdUser(), LocalDateTime.parse(dateFrom, formatter), LocalDateTime.parse(dateTo, formatter));
				request.setAttribute("transactionsDataList", tr);
			} catch (DateTimeParseException e) {
				// TODO Auto-generated catch block
				request.setAttribute("message", "Proszę uzupełnić oba pola dat!");
				e.printStackTrace();
			}
		}
		
		doGet(request, response);
	}
	
	private boolean validationAndSetup(HttpServletRequest request, User u, Customer c) {
		// TODO validation data entered by customer and setup objects User and Customer
		boolean validOK = true;
		Validation valid = new Validation();
		if (request.getParameter("login").equals("")
				|| !valid.loginValidation(request.getParameter("login"))) {
			validOK = false;
			request.setAttribute("message", "Pole login jest puste lub zawiera niepoprawne znaki!");
		}
		if(request.getAttribute("saveEdit") == null)
			if(ubl.findUserLogin(request.getParameter("login"))) {
				validOK = false;
				request.setAttribute("message", "Login o nazwie: "+request.getParameter("login")+" jest już w użyciu. Proszę podać inny login!");
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
				if (request.getParameter("companyName").equals("")
						|| !valid.nameValidation(request.getParameter("companyName"))) {
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
			u.setLogin(request.getParameter("login"));
			u.setPassword(valid.passwordToCode(request.getParameter("password")));
			u.setActive(true);

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
			return true;
		}
		return false;
	}

}
