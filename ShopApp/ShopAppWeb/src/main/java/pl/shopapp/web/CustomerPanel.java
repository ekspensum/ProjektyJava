package pl.shopapp.web;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.SystemException;

import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.TransactionBeanLocal;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.beans.Validation;
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
	
//	for tests
	public CustomerPanel() {
		super();
	}

//	for tests
	public CustomerPanel(UserBeanLocal ubl, TransactionBeanLocal tbl) {
	super();
	this.ubl = ubl;
	this.tbl = tbl;
}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/customerPanel.jsp").forward(request, response);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		String login, password, firstName, lastName, pesel, zipCode, country, city, street, streetNo, unitNo, email, companyName, taxNo, regon;
		boolean isCompany;

		if (request.getParameter("buttonAddCustomer") != null) {
			// TODO create new customer and add to database, also create session web with new customer data

			login = request.getParameter("login");
			password = request.getParameter("password");
			firstName = request.getParameter("firstName");
			lastName = request.getParameter("lastName");
			pesel = request.getParameter("pesel");
			zipCode = request.getParameter("zipCode");
			country = request.getParameter("country");
			city = request.getParameter("city");
			street = request.getParameter("street");
			streetNo = request.getParameter("streetNo");
			unitNo = request.getParameter("unitNo");
			email = request.getParameter("email");
			if (request.getParameter("isCompany") != null) {
				isCompany = request.getParameter("isCompany").equals("yes") ? true : false;
				companyName = request.getParameter("companyName");
				taxNo = request.getParameter("taxNo");
				regon = request.getParameter("regon");					
			} else {
				isCompany = false;
				companyName = "";
				taxNo = "";
				regon = "";	
			}
			
			if(validation(request, login, password, firstName, lastName, pesel, zipCode, country, city, street, streetNo, unitNo, email, companyName, taxNo, regon)) {
				try {
					ubl.addCustomer(login, password, firstName, lastName, pesel, zipCode, country, city, street, streetNo, unitNo, email, isCompany, companyName, taxNo, regon);
				} catch (IllegalStateException | SecurityException | SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SessionData sd = ubl.loginUser(login, password);
				request.getSession().setAttribute("SessionData", sd);
				request.getSession().setMaxInactiveInterval(ubl.getSettingsApp().getSessionTime()*60);
				request.setAttribute("message", "Klient został dodany do bazy danych!<br>"
						+ "Na Twój adres e-mail został wysłany mail z linkien aktywacyjnym.<br>"
						+ "Prosimy o potwierdzenie aktywacji poprzez klikniecie na link aktywacyjny.");			
			}		
		}
		
		if(request.getParameter("buttonOpenEdit") != null) {
			// TODO Auto-generated method stub
			Validation valid = new Validation(ubl.getSettingsApp());
			String pass = valid.stringToCode(request.getParameter("password"));
			if (valid.loginValidation(request.getParameter("login"))) {
				if (ubl.loginUser(request.getParameter("login"), pass) != null) {
					SessionData loginSD = ubl.loginUser(request.getParameter("login"), pass);
					SessionData currentSD = (SessionData) request.getSession().getAttribute("SessionData");
					if(loginSD.getIdUser() == currentSD.getIdUser()) {
						User user = ubl.findUser(currentSD.getIdUser());
						request.setAttribute("openToEdit", "yes");
						request.setAttribute("userData", user);
						request.setAttribute("customerData", ubl.findCustomer(user));						
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
			
			login = request.getParameter("login");
			password = request.getParameter("password");
			firstName = request.getParameter("firstName");
			lastName = request.getParameter("lastName");
			pesel = request.getParameter("pesel");
			zipCode = request.getParameter("zipCode");
			country = request.getParameter("country");
			city = request.getParameter("city");
			street = request.getParameter("street");
			streetNo = request.getParameter("streetNo");
			unitNo = request.getParameter("unitNo");
			email = request.getParameter("email");
			if (request.getParameter("isCompany") != null) {
				isCompany = request.getParameter("isCompany").equals("yes") ? true : false;
				companyName = request.getParameter("companyName");
				taxNo = request.getParameter("taxNo");
				regon = request.getParameter("regon");					
			} else {
				isCompany = false;
				companyName = "";
				taxNo = "";
				regon = "";	
			}
					
			request.setAttribute("saveEdit", "yes");
			if(validation(request, login, password, firstName, lastName, pesel, zipCode, country, city, street, streetNo, unitNo, email, companyName, taxNo, regon)) {
				try {
					if(ubl.updateCustomer(login, password, firstName, lastName, pesel, zipCode, country, city, street, streetNo, unitNo, email, isCompany, companyName, taxNo, regon, sd.getIdUser())) {
						request.setAttribute("message", "Zaktualizowano dane klienta w bazie danych!");	
						request.setAttribute("openToEdit", "no");
						request.setAttribute("saveEdit", "no");					
					} else
						request.setAttribute("message", "Nie udało się zaktualizować danych klienta!");
				} catch (IllegalStateException | SecurityException | SystemException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			} else
				request.setAttribute("saveEdit", "yes");
		}
		
		if(request.getParameter("buttonSearchTransaction") != null) {
			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String dateFrom = request.getParameter("searchProductDateFrom")+" 00:00:00";
			String dateTo = request.getParameter("searchProductDateTo")+" 23:59:59";
			String sortBy = "productIdDescending";
			try {
				request.setAttribute("transactionsDataList", tbl.getTransactionsData(sd.getIdUser(), LocalDateTime.parse(dateFrom, formatter), LocalDateTime.parse(dateTo, formatter), sortBy));
			} catch (DateTimeParseException e) {
				request.setAttribute("message", "Proszę uzupełnić oba pola dat!");
				e.printStackTrace();
			}
		}
		
		if(request.getParameter("sortBy") != null) {
			String sortBy = request.getParameter("sortBy");
			SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String dateFrom = request.getParameter("searchProductDateFrom")+" 00:00:00";
			String dateTo = request.getParameter("searchProductDateTo")+" 23:59:59";
			try {
				request.setAttribute("transactionsDataList", tbl.getTransactionsData(sd.getIdUser(), LocalDateTime.parse(dateFrom, formatter), LocalDateTime.parse(dateTo, formatter), sortBy));
			} catch (DateTimeParseException e) {
				request.setAttribute("message", "Proszę uzupełnić oba pola dat!");
				e.printStackTrace();
			}
		}
		
		doGet(request, response);
	}
	
	private boolean validation(HttpServletRequest request, String login, String password, String firstName, String lastName, String pesel, String zipCode, String country, String city, String street, String streetNo, String unitNo, String email, String companyName, String taxNo, String regon) {
		// TODO validation data entered by customer and setup objects User and Customer
		boolean validOK = true;
		Validation valid = new Validation(ubl.getSettingsApp());
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
		
		if (validOK)
			return true;

		return false;
	}

}
