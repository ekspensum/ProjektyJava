package pl.shopapp.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

	private List<Operator> ol;
	private List<Admin> al;
	private List<User> uol;
	private List<User> ual;
	private SettingsApp setting;
	private List<Role> rl;

//	@Resource 
//	private SessionContext sc;

	/**
	 * Default constructor.
	 */
	public UserBean() {
		// TODO Auto-generated constructor stub
	}
	
	//for tests tests
	public UserBean(EntityManager em, UserTransaction ut) {
	super();
	this.em = em;
	this.ut = ut;
}

	@Override
	public boolean addCustomer(String login, String password, String firstName, String lastName, String pesel, String zipCode, String country, String city, String street, String streetNo, String unitNo, String email, boolean isCompany, String companyName, String taxNo, String regon) {

		try {
			ut.begin();
			User u = new User();
			Customer c = new Customer();
			Validation valid = new Validation();
			
			u.setLogin(login);
			u.setPassword(valid.passwordToCode(password));
			u.setActive(true);
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
			c.setUser(u);		
			
			UserRole ur = addUserRole(u, 2);
			em.persist(u);
			em.persist(c);
			em.persist(ur);
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
			password = valid.passwordToCode(password);
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
	public boolean setActiveCustomer(int idCustomer, boolean action) {
		
		return false;
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
		uol = em.createNamedQuery("getUserOperatorQuery", User.class).getResultList();
		return uol;
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
			
			em.persist(u);
			em.persist(o);
			em.persist(ur);
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
		ol = em.createNamedQuery("getAllOperatorsQuery", Operator.class).getResultList();
		return ol;
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
			Admin adm = em.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", user).getSingleResult();
			
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
		al = em.createNamedQuery("getAllAdminsQuery", Admin.class).getResultList();
		return al;
	}

	@Override
	public List<User> getUsersAdminData() {
		// TODO get current users list which role is admin
		ual = em.createNamedQuery("getUserAdminQuery", User.class).getResultList();
		return ual;
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
		return setting;
	}

	@Override
	public boolean setSettingsApp(int minCharInPass, int maxCharInPass, int upperCaseInPass, int numbersInPass,	int minCharInLogin, int maxCharInLogin, int sessionTime, int idUser) {
		// TODO update application setting like parameters password, login, session time
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
		rl = em.createNamedQuery("roleQuery", Role.class).getResultList();
		return rl;
	}

}
