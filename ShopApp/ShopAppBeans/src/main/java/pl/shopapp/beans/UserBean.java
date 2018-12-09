package pl.shopapp.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import pl.shopapp.entites.Admin;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Role;
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

//	@Resource 
//	private SessionContext sc;

	/**
	 * Default constructor.
	 */
	public UserBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addCustomer(Customer c, User u) {

		try {
			ut.begin();
			UserRole ur = addUserRole(u, 2);
			em.persist(u);
			em.persist(c);
			em.persist(ur);
			ut.commit();
			return true;
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateCustomer(User u, Customer c, int idUser) {
		// TODO Auto-generated method stub
		try {
			ut.begin();
			String updateUserQuery = "UPDATE User SET login = '" + u.getLogin() + "', password = '" + u.getPassword() + "', active = " + u.getActive() + " WHERE id = " + idUser + "";
			User user = em.find(User.class, idUser);
			Customer customer = (Customer) em.createNamedQuery("customerQuery", Customer.class).setParameter("user", user).getSingleResult();
			String updateCustomerQuery = "UPDATE Customer SET firstName = '" + c.getFirstName() + "', lastName = '"
					+ c.getLastName() + "', pesel = '" + c.getPesel() + "', country = '" + c.getCountry()
					+ "', zipCode = '" + c.getZipCode() + "', city = '" + c.getCity() + "', street = '" + c.getStreet()
					+ "', streetNo = '" + c.getStreetNo() + "', unitNo = '" + c.getUnitNo() + "', email = '"
					+ c.getEmail() + "', company = " + c.isCompany() + ", companyName = '" + c.getCompanyName()
					+ "', taxNo = '" + c.getTaxNo() + "', regon = '" + c.getRegon() + "' WHERE id = " + customer.getId()
					+ "";
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
	public boolean deleteCustomer(Customer c, int id) {
		try {
			ut.begin();
			Customer cf = em.find(Customer.class, id);
			em.remove(cf);
			ut.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
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
		if (em.createNamedQuery("findUserLoginQuery", User.class).setParameter("login", login).getResultList()
				.size() > 0)
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
	public SessionData loginUser(String login, String password) {
		User user = null;
		try {
			user = em.createNamedQuery("loginQuery", User.class).setParameter("login", login)
					.setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		UserRole ur = em.createNamedQuery("userRoleQuery", UserRole.class).setParameter("user", user).getSingleResult();
		if (ur.getRole().getId() == 2) {
			Customer c = em.createNamedQuery("customerQuery", Customer.class).setParameter("user", user)
					.getSingleResult();
			SessionData data = new SessionData();
			data.setIdUser(user.getId());
			data.setFirstName(c.getFirstName());
			data.setLastName(c.getLastName());
			data.setIdRole(ur.getRole().getId());
			data.setRoleName(ur.getRole().getRoleName());
			data.setActive(user.getActive());
			return data;
		} else if (ur.getRole().getId() == 3) {
			Operator op = em.createNamedQuery("operatorQuery", Operator.class).setParameter("user", user)
					.getSingleResult();
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
		uol = new ArrayList<>();
		uol = em.createNamedQuery("getUserOperatorQuery", User.class).getResultList();
		return uol;
	}

	@Override
	public boolean addOperator(Operator o, User u, int idUser) {

		try {
			ut.begin();
			User user = em.find(User.class, idUser);
			Admin adm = em.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", user)
					.getSingleResult();
			o.setAdmin(adm);
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
			return false;
		}
	}

	@Override
	public List<Operator> getOperatorsData() {
		// TODO Auto-generated method stub
		ol = new ArrayList<>();
		ol = em.createNamedQuery("getAllOperatorsQuery", Operator.class).getResultList();
		return ol;
	}

	@Override
	public boolean updateOperatorData(int idUser, int idOperator, String login, boolean active, String firstName,
			String lastName, String phoneNo, String email) {
		// TODO update User & Operator from ShopAppClient AdminPanel
		String updateUserQuery = "UPDATE User SET login = '" + login + "', active = " + active + " WHERE id = " + idUser
				+ "";
		String updateOperatorQuery = "UPDATE Operator SET firstName = '" + firstName + "', lastName = '" + lastName
				+ "', phoneNo = '" + phoneNo + "', email = '" + email + "', date = '" + LocalDateTime.now()
				+ "' WHERE id = " + idOperator + "";
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
	public boolean addAdmin(Admin a, User u, int idUser) {
		try {
			ut.begin();
			User user = em.find(User.class, idUser);
			Admin adm = em.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", user)
					.getSingleResult();
			adm.setAdmin(adm);
			adm.setDate(LocalDateTime.now());
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
			return false;
		}
	}

	@Override
	public SessionData loginAdmin(String login, String password) {
		User user = null;
		try {
			user = em.createNamedQuery("loginQuery", User.class).setParameter("login", login)
					.setParameter("password", password).getSingleResult();
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
		al = new ArrayList<>();
		al = em.createNamedQuery("getAllAdminsQuery", Admin.class).getResultList();
		return al;
	}

	@Override
	public List<User> getUsersAdminData() {
		// TODO get current users list which role is admin
		ual = new ArrayList<>();
		ual = em.createNamedQuery("getUserAdminQuery", User.class).getResultList();
		return ual;
	}

	@Override
	public boolean updateAdminData(int idUser, int idAdmin, String login, boolean active, String firstName,
			String lastName, String phoneNo, String email) {
		// TODO update User & Admin from ShopAppClient AdminPanel. Only first Admin have
		// privileges for update other admins.
		String updateUserQuery = "UPDATE User SET login = '" + login + "', active = " + active + " WHERE id = " + idUser
				+ "";
		String updateAdminQuery = "UPDATE Admin SET firstName = '" + firstName + "', lastName = '" + lastName
				+ "', phoneNo = '" + phoneNo + "', email = '" + email + "', date = '" + LocalDateTime.now()
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

}
