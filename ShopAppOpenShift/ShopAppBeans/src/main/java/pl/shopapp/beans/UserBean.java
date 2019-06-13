package pl.shopapp.beans;

import java.time.LocalDateTime;
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


/**
 * The UserBean class provide create, read and update method for users. Method delete is not predict to use. Additional, this class include setting application like login and password setting, session time setting.
 * This class also include service e-mail, which is use in create user methods to message purpose.  
 * Each method in this class include transaction if necessary.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class UserBean implements UserBeanRemote, UserBeanLocal {

	@PersistenceContext(unitName = "ShopAppEntites")
	private EntityManager entityManager;

	@Resource
	private UserTransaction userTransaction;

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
	
	/**
	 * This constructor is using only for tests
	 * @param entityManager EntityManager
	 * @param userTransaction UserTransaction
	 * @param mail SendEmail
	 */
	public UserBean(EntityManager em, UserTransaction ut, SendEmail mail) {
	super();
	this.entityManager = em;
	this.userTransaction = ut;
	this.mail = mail;
}

	/**
	 * Adds new customer to database with specified parameters. During the operation will be add data of User, UserRole and Customer class
	 * @return true if operation done 
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Override
	public boolean addCustomer(String login, String password, String firstName, String lastName, String pesel, String zipCode, String country, String city, String street, String streetNo, String unitNo, String email, boolean isCompany, String companyName, String taxNo, String regon) throws IllegalStateException, SecurityException, SystemException {

		try {
			userTransaction.begin();
			User user = new User();
			Customer customer = new Customer();
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
			
			user.setLogin(login);
			user.setPassword(valid.stringToCode(password));
			user.setActive(false);
			
			Role role = entityManager.find(Role.class, 2);
			user.setRole(role);
			
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setPesel(pesel);
			customer.setZipCode(zipCode);
			customer.setCountry(country);
			customer.setCity(city);
			customer.setStreet(street);
			customer.setStreetNo(streetNo);
			customer.setUnitNo(unitNo);
			customer.setEmail(email);
			if (isCompany) {
				customer.setCompany(isCompany);
				customer.setCompanyName(companyName);
				customer.setTaxNo(taxNo);
				customer.setRegon(regon);					
			}
			customer.setDateRegistration(LocalDateTime.now());
			customer.setActivationString(valid.stringToCode(activationString.toString()));
			customer.setUser(user);		
			
			entityManager.persist(user);
			entityManager.persist(customer);
			mail.sendEmail(email, mailSubject, mailText);
			userTransaction.commit();
			return true;
		} catch (Exception e) {
			userTransaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Update existing user data in database with gets parameter values. If any parameter is null (exceptions idUser), then previous parameter value no change in database.
	 * @return true if update done  
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Override
	public boolean updateCustomer(String login, String password, String firstName, String lastName, String pesel, String zipCode, String country, String city, 
			String street, String streetNo, String unitNo, String email, boolean isCompany, String companyName, String taxNo, String regon, int idUser) 
					throws IllegalStateException, SecurityException, SystemException {
		try {
			userTransaction.begin();
			Validation valid = new Validation();
			password = valid.stringToCode(password);

			User user = entityManager.find(User.class, idUser);
			user.setLogin(login);
			user.setPassword(password);
			
			Role role = entityManager.find(Role.class, 2);
			user.setRole(role);
			
			Customer customer = (Customer) entityManager.createNamedQuery("customerQuery", Customer.class).setParameter("user", user).getSingleResult();
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setPesel(pesel);
			customer.setZipCode(zipCode);
			customer.setCountry(country);
			customer.setCity(city);
			customer.setStreet(street);
			customer.setStreetNo(streetNo);
			customer.setUnitNo(unitNo);
			customer.setEmail(email);
			customer.setCompany(isCompany);
			if(isCompany) {
				customer.setCompanyName(companyName);
				customer.setTaxNo(taxNo);
				customer.setRegon(regon);
			}
			customer.setDateRegistration(LocalDateTime.now());
			customer.setUser(user);	

			entityManager.persist(user);
			entityManager.persist(customer);
			
			userTransaction.commit();
			return true;
		} catch (Exception e) {
			userTransaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns Customer object use User parameter.
	 * @return Returns Customer
	 */
	@Override
	public Customer findCustomer(User u) {
		Customer customer = entityManager.createNamedQuery("customerQuery", Customer.class).setParameter("user", u).getSingleResult();
		return customer;
	}

	/**
	 * Set active user. When user is active then can login to shop application, else user can't access to shop. Administrator can change this state.
	 * @return true if operation done
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Override
	public boolean setActiveCustomer(int idUser, boolean action) throws IllegalStateException, SecurityException, SystemException {
		try {
			userTransaction.begin();
			User user = entityManager.find(User.class, idUser);
			user.setActive(action);
			entityManager.persist(user);
			userTransaction.commit();
			return true;
		} catch (Exception e) {
			userTransaction.rollback();
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This method is using by customer for activate his account. Parameter activationString is sending by e-mail and then to send by URL parameter to this method from content e-mail.
	 * @return true if method done   
	 */
	@Override
	public boolean setActiveCustomer(String activationString) {
		// TODO Auto-generated method stub
		List<Customer> cl;
		try {
			cl = entityManager.createNamedQuery("activationStringQuery", Customer.class).setParameter("activationString", activationString).getResultList();
			
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

	/**
	 * Returns User object using id parameter.
	 * @return User
	 */
	@Override
	public User findUser(int idUser) {
		User user = entityManager.find(User.class, idUser);
		return user;
	}

	/**
	 * Checking whether login name is unique (distinct). This method is using with registration new customer.
	 * @return true if login name already using by another user. 
	 */
	@Override
	public boolean findUserLogin(String login) {
		// TODO examine whether the login is in use
		if (entityManager.createNamedQuery("findUserLoginQuery", User.class).setParameter("login", login).getResultList().size() > 0)
			return true;
		else
			return false;
	}

	/**
	 * Returns SessionData if login and password is correct. Returns null if login or password is't correct or user is inactive. Session data object include user data and basket data.
	 * @return SessionData
	 */
	@Override
	@Interceptors(LoggingInterceptor.class)
	public SessionData loginUser(String login, String password) {
		User user = null;
		try {
			user = entityManager.createNamedQuery("loginQuery", User.class).setParameter("login", login).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		
		if (user.getRole().getId() == 2) {
			Customer c = entityManager.createNamedQuery("customerQuery", Customer.class).setParameter("user", user).getSingleResult();
			SessionData data = new SessionData();
			data.setIdUser(user.getId());
			data.setFirstName(c.getFirstName());
			data.setLastName(c.getLastName());
			data.setIdRole(user.getRole().getId());
			data.setRoleName(user.getRole().getRoleName());
			data.setActive(user.getActive());
			return data;
		} else if (user.getRole().getId() == 3) {
			Operator op = entityManager.createNamedQuery("operatorQuery", Operator.class).setParameter("user", user).getSingleResult();
			SessionData data = new SessionData();
			data.setIdUser(user.getId());
			data.setFirstName(op.getFirstName());
			data.setLastName(op.getLastName());
			data.setIdRole(user.getRole().getId());
			data.setRoleName(user.getRole().getRoleName());
			data.setActive(user.getActive());
			return data;
		}
		return null;
	}

	/**
	 * Returns list of all users which role is operator. This method is using by administrator in ShopAppClient.
	 * @return users list.
	 */
	@Override
	public List<User> getUsersOperatorData() {
		return entityManager.createNamedQuery("getUserOperatorQuery", User.class).getResultList();
	}

	/**
	 * Add new operator. This method is using by administrator in ShopAppClient.
	 * @return true if method done.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Override
	public boolean addOperator(String firstName, String lastName, String phoneNo, String email, String login, String password, int idUser) throws IllegalStateException, SecurityException, SystemException {

		try {
			userTransaction.begin();
			User userAdmin = entityManager.find(User.class, idUser);
			Admin admin = entityManager.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", userAdmin).getSingleResult();
			Role role = entityManager.find(Role.class, 3);
			
			User user = new User();
			user.setLogin(login);
			user.setPassword(password);
			user.setActive(true);
			user.setRole(role);
			
			
			Operator operator = new Operator();
			operator.setFirstName(firstName);
			operator.setLastName(lastName);
			operator.setPhoneNo(phoneNo);
			operator.setEmail(email);
			operator.setAdmin(admin);
			operator.setUser(user);
			operator.setDate(LocalDateTime.now());
			
			String mailSubject = "Potwierdzenie rejestracji konta operatora w sklepie internetowym ShopApp.";
			String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+firstName+" "+lastName+"</b><br>Twoje konto zostało zarejestrowane i możesz rozpocząć pracę.<br>"
					+ "Twój login to: "+login+". <br><br>Pozdrawiamy<br>ShopApp sp. z o.o.</font><br><br>"+mail.getMailFrom();
			
			entityManager.persist(user);
			entityManager.persist(operator);
			mail.sendEmail(email, mailSubject, mailText);
			userTransaction.commit();
			return true;
		} catch (Exception e) {
			userTransaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns list of all operator data. This method is using by administrator in ShopAppClient module.
	 * @return operators list.
	 */
	@Override
	public List<Operator> getOperatorsData() {
		return entityManager.createNamedQuery("getAllOperatorsQuery", Operator.class).getResultList();
	}

	/**
	 * Update operator data. This method is using by administrator in ShopAppClient.
	 * @return true if method done.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Override
	public boolean updateOperatorData(int idLoginUser, int idOperator, String login, boolean active, String firstName,
		String lastName, String phoneNo, String email) throws IllegalStateException, SecurityException, SystemException {
		try {
			userTransaction.begin();
			User loginUser = entityManager.find(User.class, idLoginUser);
			Admin loginAdmin = entityManager.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", loginUser).getSingleResult();
			
			Operator operatorToEdit = entityManager.find(Operator.class, idOperator);
			User userToEdit = operatorToEdit.getUser();
			
			userToEdit.setLogin(login);
			userToEdit.setActive(active);
			operatorToEdit.setUser(userToEdit);
			operatorToEdit.setFirstName(firstName);
			operatorToEdit.setLastName(lastName);
			operatorToEdit.setPhoneNo(phoneNo);
			operatorToEdit.setEmail(email);
			operatorToEdit.setAdmin(loginAdmin);
			operatorToEdit.setDate(LocalDateTime.now());

			entityManager.persist(operatorToEdit);
			userTransaction.commit();
			return true;
			
		} catch (Exception e) {
			userTransaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Add new administrator. This method is using by administrator in ShopAppClient. Only first administrator (default) can add other administrator.
	 * @return true if operation done.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Override
	public boolean addAdmin(String firstName, String lastName, String phoneNo, String email, String login, String password, int idLoginUser) throws IllegalStateException, SecurityException, SystemException {
		try {
			userTransaction.begin();
			User userAdmin = entityManager.find(User.class, idLoginUser);
			Admin admin = null;
			try {
				admin = entityManager.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", userAdmin).getSingleResult();
			} catch (NoResultException e) {
//				e.printStackTrace();
				admin = new Admin();
				admin.setFirstName("firstNameDefault");
				admin.setLastName("lastNameDefault");
				admin.setDate(LocalDateTime.now());
				admin.setUser(userAdmin);
				admin.setAdmin(admin);
				entityManager.persist(admin);
			}
			
			String mailSubject = "Potwierdzenie rejestracji konta administratora w sklepie internetowym ShopApp.";
			String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+firstName+" "+lastName+"</b><br>Twoje konto zostało zarejestrowane i możesz rozpocząć pracę.<br>"
					+ "Twój login to: "+login+". <br><br>Pozdrawiamy<br>ShopApp sp. z o.o.</font><br><br>"+mail.getMailFrom();
			
			Role role = entityManager.find(Role.class, 1);
			
			User newUser = new User();
			newUser.setLogin(login);
			newUser.setPassword(password);
			newUser.setActive(true);
			newUser.setRole(role);
			
			Admin newAdmin = new Admin();
			newAdmin.setFirstName(firstName);
			newAdmin.setLastName(lastName);
			newAdmin.setPhoneNo(phoneNo);
			newAdmin.setEmail(email);
			newAdmin.setAdmin(admin);
			newAdmin.setUser(newUser);
			newAdmin.setDate(LocalDateTime.now());
			
			entityManager.persist(newUser);
			entityManager.persist(newAdmin);
			mail.sendEmail(email, mailSubject, mailText);
			userTransaction.commit();
			return true;
		} catch (Exception e) {
			userTransaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns SessionData object if login and password is correct, else return null. SessionData object include administrator data. This method is using by administrator in ShopAppClient module. 
	 * @return SessionData or null.
	 */
	@Override
	public SessionData loginAdmin(String login, String password) {
		User user = null;
		try {
			user = entityManager.createNamedQuery("loginQuery", User.class).setParameter("login", login).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		Admin admin = entityManager.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", user).getSingleResult();
		SessionData data = new SessionData();
		data.setIdUser(user.getId());
		data.setFirstName(admin.getFirstName());
		data.setLastName(admin.getLastName());
		data.setIdRole(user.getRole().getId());
		data.setRoleName(user.getRole().getRoleName());
		data.setActive(user.getActive());
		return data;
	}

	/**
	 * Gets current admins list
	 * @return list containing objects of Admin class 
	 */
	@Override
	public List<Admin> getAdminsData() {
		return entityManager.createNamedQuery("getAllAdminsQuery", Admin.class).getResultList();
	}

	/**
	 * Gets current users list which role is admin
	 * @return list containing objects of User class 
	 */
	@Override
	public List<User> getUsersAdminData() {
		return entityManager.createNamedQuery("getUserAdminQuery", User.class).getResultList();
	}

	/**
	 * Update administrator data and user data in database. Call is from ShopAppClient AdminPanel. Only first Admin have privileges for update other admins.
	 * @return true if update done
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Override
	public boolean updateAdminData(int idLoginUser, int idAdmin, String login, boolean active, String firstName,
			String lastName, String phoneNo, String email) throws IllegalStateException, SecurityException, SystemException {
		try {
			userTransaction.begin();		
			User loginUser = entityManager.find(User.class, idLoginUser);
			Admin loginAdmin = entityManager.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", loginUser).getSingleResult();
			
			Admin adminToEdit = entityManager.find(Admin.class, idAdmin);
			User userToEdit = adminToEdit.getUser();

			userToEdit.setLogin(login);
			userToEdit.setActive(active);
			adminToEdit.setUser(userToEdit);
			adminToEdit.setFirstName(firstName);
			adminToEdit.setLastName(lastName);
			adminToEdit.setPhoneNo(phoneNo);
			adminToEdit.setEmail(email);
			adminToEdit.setAdmin(loginAdmin);
			adminToEdit.setDate(LocalDateTime.now());
			
			entityManager.persist(adminToEdit);
			userTransaction.commit();
			return true;
		} catch (Exception e) {
			userTransaction.rollback();
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Gets current application setting like password, login, session time parameters
	 * @return object of SettingsApp class 
	 */
	@Override
	public SettingsApp getSettingsApp() {
		SettingsApp setting = entityManager.find(SettingsApp.class, 1);
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

	/**
	 * Insert or update application setting like parameters password, login, session time
	 * @return true if add or update setting done
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Override
	public boolean setSettingsApp(int minCharInPass, int maxCharInPass, int upperCaseInPass, int numbersInPass,	int minCharInLogin, int maxCharInLogin, int sessionTime, int idUser) throws IllegalStateException, SecurityException, SystemException {
		SettingsApp setting = entityManager.find(SettingsApp.class, 1);
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
				userTransaction.begin();
				entityManager.persist(setting);
				userTransaction.commit();
				return true;
			} catch (Exception e) {
				userTransaction.rollback();
				e.printStackTrace();
			}
		}
		else {
			try {
				userTransaction.begin();
				setting = getSettingsApp();
				setting.setMinCharInPass(minCharInPass);
				setting.setMaxCharInPass(maxCharInPass);
				setting.setUpperCaseInPass(upperCaseInPass);
				setting.setNumbersInPass(numbersInPass);
				setting.setMinCharInLogin(minCharInLogin);
				setting.setMaxCharInLogin(maxCharInLogin);
				setting.setSessionTime(sessionTime);
				setting.setDateTime(LocalDateTime.now());
				setting.setIdUser(idUser);	
				entityManager.persist(setting);
				userTransaction.commit();
				return true;
			} catch (Exception e) {
				userTransaction.rollback();
				e.printStackTrace();
			}			
		}
		return false;
	}

	/**
	 * Adds the specified role to database
	 * @return true if operation done
	 */
	@Override
	public boolean addRole(String roleName) {
		try {
			userTransaction.begin();
			Role role = new Role();
			role.setRoleName(roleName);
			entityManager.persist(role);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Gets current list of all role in application
	 * @return list of objects Role class
	 */
	@Override
	public List<Role> getRoleList() {
		return entityManager.createNamedQuery("roleQuery", Role.class).getResultList();
	}

	/**
	 * Gets current list of all customers
	 * @return list of objects Customer class
	 */
	@Override
	public List<Customer> findCustomerList(String lastName, String pesel) {
		return entityManager.createNamedQuery("customerByLastNamePeselQuery", Customer.class).setParameter("lastName", "%"+lastName+"%").setParameter("pesel", "%"+pesel+"%").getResultList();
	}

}
