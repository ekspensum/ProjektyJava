package pl.shopapp.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import pl.shopapp.beans.interceptors.LoggingInterceptor;
import pl.shopapp.entites.Admin;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Role;
import pl.shopapp.entites.SettingsApp;
import pl.shopapp.entites.User;
import pl.shopapp.entites.UserRole;

/**
 * Session Bean implementation class CustomerOperations
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class UserBean implements UserBeanRemote, UserBeanLocal {

	@PersistenceContext(unitName = "ShopAppEntites")
	private EntityManager em;

	@Resource
	private UserTransaction ut;

	private SettingsApp setting;
	private SendEmail mail;

//	@Resource 
//	private SessionContext sc;

	/**
	 * Default constructor.
	 */
	public UserBean() {
		// TODO Auto-generated constructor stub
		mail = new SendEmail();
	}
	
	//for tests tests
	public UserBean(EntityManager em, UserTransaction ut, SendEmail mail) {
	super();
	this.em = em;
	this.ut = ut;
	this.mail = mail;
}

	@Override
	public boolean addCustomer(String login, String password, String firstName, String lastName, String pesel, String zipCode, String country, String city, String street, String streetNo, String unitNo, String email, boolean isCompany, String companyName, String taxNo, String regon) {

		try {
			ut.begin();
			User u = new User();
			Customer c = new Customer();
			Validation valid = new Validation();
			
			StringBuilder activationString = new StringBuilder();
			StringBuilder activationLink = new StringBuilder();
			Random rand = new Random();
			int number = rand.nextInt(10000);
			activationString.append(number).append(email);
			activationLink.append("<a href=\"").append("http://localhost:8080/ShopAppWeb/CustomerActivation?").append("activationString=").append(valid.stringToCode(activationString.toString())).append("\">Naciśnij ten link aktywacyjny</a>");
			
			String mailSubject = "Potwierdzenie rejestracji konta użytkownika w sklepie internetowym ShopApp.";
			String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+firstName+" "+lastName+"</b><br>Twoje konto zostało zarejestrowane.<br>"
					+ "Twój login to: "+login+". <br>"
					+ "Poniżej znajduje się link aktywacyjny. Prosimy o jego klikniecie celu aktywacji konta. Link będzie ważny 6 godzin. "
					+ "W przypadku braku aktywacji konta w tym czasie, konieczna będzie ponowna rejestracja.<br><br>"
					+ activationLink
					+ "<br><br>Pozdrawiamy<br>Dział Obsługi Klienta</font><br><br>"+mail.getMailFrom();
			
			u.setLogin(login);
			u.setPassword(valid.stringToCode(password));
			u.setActive(false);
			c.setFirstName(firstName);
			c.setLastName(lastName);
			c.setPesel(pesel);
			c.setZipCode(zipCode);
			c.setCountry(country);
			c.setCity(city);
			c.setStreet(street);
			c.setStreetNo(streetNo);
			c.setUnitNo(unitNo);
			c.setEmail(email);
			if (isCompany) {
				c.setCompany(isCompany);
				c.setCompanyName(companyName);
				c.setTaxNo(taxNo);
				c.setRegon(regon);					
			}
			c.setDateRegistration(LocalDateTime.now());
			c.setActivationString(valid.stringToCode(activationString.toString()));
			c.setUser(u);		
			
			UserRole ur = addUserRole(u, 2);
			em.persist(u);
			em.persist(c);
			em.persist(ur);
			mail.sendEmail(email, mailSubject, mailText);
			ut.commit();
			return true;
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public boolean updateCustomer(String login, String password, String firstName, String lastName, String pesel, String zipCode, String country, String city, String street, String streetNo, String unitNo, String email, boolean isCompany, String companyName, String taxNo, String regon, int idUser) {
		// TODO Auto-generated method stub
		try {
			ut.begin();
			Validation valid = new Validation();
			password = valid.stringToCode(password);
			String updateUserQuery = "UPDATE User SET login = '" + login + "', password = '" + password + "' WHERE id = " + idUser + "";
			User user = em.find(User.class, idUser);
			Customer customer = (Customer) em.createNamedQuery("customerQuery", Customer.class).setParameter("user", user).getSingleResult();
			String updateCustomerQuery = "UPDATE Customer SET firstName = '" + firstName + "', lastName = '"
					+ lastName + "', pesel = '" + pesel + "', country = '" + country
					+ "', zipCode = '" + zipCode + "', city = '" + city + "', street = '" + street
					+ "', streetNo = '" + streetNo + "', unitNo = '" + unitNo + "', email = '"
					+ email + "', company = " + isCompany + ", companyName = '" + companyName
					+ "', taxNo = '" + taxNo + "', regon = '" + regon + "' WHERE id = " + customer.getId()	+ "";
			int uuq = em.createQuery(updateUserQuery).executeUpdate();
			int ucq = em.createQuery(updateCustomerQuery).executeUpdate();
			ut.commit();
			if (uuq == 1 && ucq == 1)
				return true;
			else
				ut.rollback();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public Customer findCustomer(User u) {
		// TODO Auto-generated method stub
		Customer c = em.createNamedQuery("customerQuery", Customer.class).setParameter("user", u).getSingleResult();
		return c;
	}

	@Override
	public boolean setActiveCustomer(int idUser, boolean action) {
		try {
			ut.begin();
			String activeCustomerQuery = "UPDATE User SET active = "+action+" WHERE id = "+idUser+" ";
			int acq = em.createQuery(activeCustomerQuery).executeUpdate();
			ut.commit();
			if(acq == 1)
				return true;
			else
				ut.rollback();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	@Override
	public boolean setActiveCustomer(String activationString) {
		// TODO Auto-generated method stub
		List<Customer> cl;
		try {
			cl = em.createNamedQuery("activationStringQuery", Customer.class).setParameter("activationString", activationString).getResultList();
			
			if(cl.size() == 0)
				return false;
			else if(cl.size() == 1 && LocalDateTime.now().isBefore(cl.get(0).getDateRegistration().plusHours(6))) {
				setActiveCustomer(cl.get(0).getUser().getId(), true);
				return true;	
			}
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User findUser(int idUser) {
		// TODO Auto-generated method stub
		User u = em.find(User.class, idUser);
		return u;
	}

	@Override
	public boolean findUserLogin(String login) {
		// TODO examine whether the login is in use
		if (em.createNamedQuery("findUserLoginQuery", User.class).setParameter("login", login).getResultList().size() > 0)
			return true;
		else
			return false;
	}

	@Override
	public UserRole addUserRole(User u, int idRole) {
		Role r = em.find(Role.class, idRole);
		UserRole ur = new UserRole();
		ur.setRole(r);
		ur.setUser(u);
		return ur;
	}

	@Override
	@Interceptors(LoggingInterceptor.class)
	public SessionData loginUser(String login, String password) {
		User user = null;
		try {
			user = em.createNamedQuery("loginQuery", User.class).setParameter("login", login).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		UserRole ur = em.createNamedQuery("userRoleQuery", UserRole.class).setParameter("user", user).getSingleResult();
		if (ur.getRole().getId() == 2) {
			Customer c = em.createNamedQuery("customerQuery", Customer.class).setParameter("user", user).getSingleResult();
			SessionData data = new SessionData();
			data.setIdUser(user.getId());
			data.setFirstName(c.getFirstName());
			data.setLastName(c.getLastName());
			data.setIdRole(ur.getRole().getId());
			data.setRoleName(ur.getRole().getRoleName());
			data.setActive(user.getActive());
			return data;
		} else if (ur.getRole().getId() == 3) {
			Operator op = em.createNamedQuery("operatorQuery", Operator.class).setParameter("user", user).getSingleResult();
			SessionData data = new SessionData();
			data.setIdUser(user.getId());
			data.setFirstName(op.getFirstName());
			data.setLastName(op.getLastName());
			data.setIdRole(ur.getRole().getId());
			data.setRoleName(ur.getRole().getRoleName());
			data.setActive(user.getActive());
			return data;
		}
		return null;
	}

	@Override
	public List<User> getUsersOperatorData() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("getUserOperatorQuery", User.class).getResultList();
	}

	@Override
	public boolean addOperator(String firstName, String lastName, String phoneNo, String email, String login, String password, int idUser) {

		try {
			ut.begin();
			User user = em.find(User.class, idUser);
			Admin adm = em.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", user).getSingleResult();
			
			User u = new User();
			u.setLogin(login);
			u.setPassword(password);
			u.setActive(true);
			
			Operator o = new Operator();
			o.setFirstName(firstName);
			o.setLastName(lastName);
			o.setPhoneNo(phoneNo);
			o.setEmail(email);
			o.setAdmin(adm);
			o.setUser(u);
			o.setDate(LocalDateTime.now());
			
			UserRole ur = addUserRole(u, 3);
			
			String mailSubject = "Potwierdzenie rejestracji konta operatora w sklepie internetowym ShopApp.";
			String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+firstName+" "+lastName+"</b><br>Twoje konto zostało zarejestrowane i możesz rozpocząć pracę.<br>"
					+ "Twój login to: "+login+". <br><br>Pozdrawiamy<br>ShopApp sp. z o.o.</font><br><br>"+mail.getMailFrom();
			
			em.persist(u);
			em.persist(o);
			em.persist(ur);
			mail.sendEmail(email, mailSubject, mailText);
			ut.commit();
			return true;
		} catch (NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException
				| RollbackException | IllegalStateException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public List<Operator> getOperatorsData() {
		// TODO Auto-generated method stub
		return em.createNamedQuery("getAllOperatorsQuery", Operator.class).getResultList();
	}

	@Override
	public boolean updateOperatorData(int idUser, int idOperator, String login, boolean active, String firstName,
			String lastName, String phoneNo, String email) {
		// TODO update User & Operator from ShopAppClient AdminPanel
		String updateUserQuery = "UPDATE User SET login = '" + login + "', active = " + active + " WHERE id = " + idUser+ "";
		String updateOperatorQuery = "UPDATE Operator SET firstName = '" + firstName + "', lastName = '" + lastName
				+ "', phoneNo = '" + phoneNo + "', email = '" + email + "', date = '" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "' WHERE id = " + idOperator + "";
		try {
			ut.begin();
			int uuq = em.createQuery(updateUserQuery).executeUpdate();
			int uoq = em.createQuery(updateOperatorQuery).executeUpdate();
			ut.commit();
			if (uuq == 1 && uoq == 1)
				return true;
			else
				ut.rollback();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addAdmin(String firstName, String lastName, String phoneNo, String email, String login, String password, int idUser) {
		try {
			ut.begin();
			User user = em.find(User.class, idUser);
			Admin adm = null;
			try {
				adm = em.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", user).getSingleResult();
			} catch (NoResultException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				adm = new Admin();
				adm.setFirstName("firstNameDefault");
				adm.setLastName("lastNameDefault");
				adm.setDate(LocalDateTime.now());
				adm.setUser(user);
				adm.setAdmin(adm);
				em.persist(adm);
			}
			
			String mailSubject = "Potwierdzenie rejestracji konta administratora w sklepie internetowym ShopApp.";
			String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+firstName+" "+lastName+"</b><br>Twoje konto zostało zarejestrowane i możesz rozpocząć pracę.<br>"
					+ "Twój login to: "+login+". <br><br>Pozdrawiamy<br>ShopApp sp. z o.o.</font><br><br>"+mail.getMailFrom();
			
			User u = new User();
			u.setLogin(login);
			u.setPassword(password);
			u.setActive(true);
			
			Admin a = new Admin();
			a.setFirstName(firstName);
			a.setLastName(lastName);
			a.setPhoneNo(phoneNo);
			a.setEmail(email);
			a.setAdmin(adm);
			a.setUser(u);
			a.setDate(LocalDateTime.now());
			
			UserRole ur = addUserRole(u, 1);
			
			em.persist(u);
			em.persist(a);
			em.persist(ur);
			mail.sendEmail(email, mailSubject, mailText);
			ut.commit();
			return true;
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public SessionData loginAdmin(String login, String password) {
		User user = null;
		try {
			user = em.createNamedQuery("loginQuery", User.class).setParameter("login", login).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		UserRole ur = em.createNamedQuery("userRoleQuery", UserRole.class).setParameter("user", user).getSingleResult();
		Admin a = em.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", user).getSingleResult();
		SessionData data = new SessionData();
		data.setIdUser(user.getId());
		data.setFirstName(a.getFirstName());
		data.setLastName(a.getLastName());
		data.setIdRole(ur.getRole().getId());
		data.setRoleName(ur.getRole().getRoleName());
		data.setActive(user.getActive());
		return data;
	}

	@Override
	public List<Admin> getAdminsData() {
		// TODO get current admins list
		return em.createNamedQuery("getAllAdminsQuery", Admin.class).getResultList();
	}

	@Override
	public List<User> getUsersAdminData() {
		// TODO get current users list which role is admin
		return em.createNamedQuery("getUserAdminQuery", User.class).getResultList();
	}

	@Override
	public boolean updateAdminData(int idUser, int idAdmin, String login, boolean active, String firstName,
			String lastName, String phoneNo, String email) {
		// TODO update User & Admin from ShopAppClient AdminPanel. Only first Admin have
		// privileges for update other admins.
		String updateUserQuery = "UPDATE User SET login = '" + login + "', active = " + active + " WHERE id = " + idUser + "";
		String updateAdminQuery = "UPDATE Admin SET firstName = '" + firstName + "', lastName = '" + lastName
				+ "', phoneNo = '" + phoneNo + "', email = '" + email + "', date = '" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
				+ "' WHERE id = " + idAdmin + "";
		try {
			ut.begin();
			int uuq = em.createQuery(updateUserQuery).executeUpdate();
			int uaq = em.createQuery(updateAdminQuery).executeUpdate();
			ut.commit();
			if (uuq == 1 && uaq == 1)
				return true;
			else
				ut.rollback();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public SettingsApp getSettingsApp() {
		// TODO get current application setting like parameters password, login, session time
		setting = em.find(SettingsApp.class, 1);
		if(setting == null) {
			setting = new SettingsApp();
			setting.setId(1);
			setting.setMinCharInPass(3);
			setting.setMaxCharInPass(20);
			setting.setUpperCaseInPass(0);
			setting.setNumbersInPass(0);
			setting.setMinCharInLogin(3);
			setting.setMaxCharInLogin(20);
			setting.setSessionTime(30);
			setting.setDateTime(LocalDateTime.now());
			setting.setIdUser(1);
			return setting;
		}
		return setting;
	}

	@Override
	public boolean setSettingsApp(int minCharInPass, int maxCharInPass, int upperCaseInPass, int numbersInPass,	int minCharInLogin, int maxCharInLogin, int sessionTime, int idUser) {
		// TODO insert or update application setting like parameters password, login, session time
		setting = em.find(SettingsApp.class, 1);
		if(setting == null) {
			setting = new SettingsApp();
			setting.setId(1);
			setting.setMinCharInPass(minCharInPass);
			setting.setMaxCharInPass(maxCharInPass);
			setting.setUpperCaseInPass(upperCaseInPass);
			setting.setNumbersInPass(numbersInPass);
			setting.setMinCharInLogin(minCharInLogin);
			setting.setMaxCharInLogin(maxCharInLogin);
			setting.setSessionTime(sessionTime);
			setting.setDateTime(LocalDateTime.now());
			setting.setIdUser(idUser);			
			try {
				ut.begin();
				em.persist(setting);
				ut.commit();
				return true;
			} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException
					| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			String updateSettingAppQuery = "UPDATE SettingsApp SET minCharInPass = "+minCharInPass+", maxCharInPass = "+maxCharInPass+", upperCaseInPass = "+upperCaseInPass+", "
					+ "numbersInPass = "+numbersInPass+", minCharInLogin = "+minCharInLogin+", maxCharInLogin = "+maxCharInLogin+", sessionTime = "+sessionTime+", idUser = "+idUser+", dateTime = '"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"' WHERE id = 1";
			try {
				ut.begin();
				int usq = em.createQuery(updateSettingAppQuery).executeUpdate();
				ut.commit();
				if(usq == 1)
					return true;
				else
					ut.rollback();
			} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
					| HeuristicMixedException | HeuristicRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return false;
	}

	@Override
	public boolean addRole(String roleName) {
		// TODO Auto-generated method stub
		try {
			ut.begin();
			Role r = new Role();
			r.setRoleName(roleName);
			em.persist(r);
			ut.commit();
			return true;
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub		
		return em.createNamedQuery("roleQuery", Role.class).getResultList();
	}

	@Override
	public List<Customer> findCustomerList(String lastName, String pesel) {
		// TODO Auto-generated method stub
		return em.createNamedQuery("customerByLastNamePeselQuery", Customer.class).setParameter("lastName", "%"+lastName+"%").setParameter("pesel", "%"+pesel+"%").getResultList();
	}

}
