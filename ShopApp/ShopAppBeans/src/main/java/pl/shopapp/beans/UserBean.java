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

	@PersistenceContext(unitName="ShopAppEntites")
	private EntityManager em;
	
	@Resource
	private UserTransaction ut;
	
	private List<Customer> cl;
	private List<Operator> ol;
	private List<User> ul;
	
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
			} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException
					| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean updateCustomer(Customer c, int id) {
		try {
			ut.begin();
			Customer cf = em.find(Customer.class, id);
			em.merge(cf);
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
	public List<Customer> findCustomer(String lastName, String pesel, String companyName, String taxNo,	String regon) {
		cl = new ArrayList<>();
		cl = em.createQuery("SELECT c FROM Customer c WHERE c.lastName LIKE '%"+lastName+"%' AND c.companyName LIKE '%"+companyName+"%' AND c.taxNo LIKE '%"+taxNo+"%' AND c.regon LIKE '%"+regon+"%' ", Customer.class).getResultList();
		return cl;
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
			user = em.createNamedQuery("loginQuery", User.class).setParameter("login", login).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		UserRole ur = em.createNamedQuery("userRoleQuery", UserRole.class).setParameter("user", user).getSingleResult();
		if(ur.getRole().getId() == 2) {
			Customer c = em.createNamedQuery("customerQuery", Customer.class).setParameter("user", user).getSingleResult();
			SessionData data = new SessionData();
			data.setIdUser(user.getId());
			data.setFirstName(c.getFirstName());
			data.setLastName(c.getLastName());
			data.setIdRole(ur.getRole().getId());
			data.setRoleName(ur.getRole().getRoleName());
			data.setActive(user.getActive());
			return data;			
		}
		else if(ur.getRole().getId() == 3) {
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
	public List<User> getUsersData() {
		// TODO Auto-generated method stub
		ul = new ArrayList<>();
		ul = em.createNamedQuery("getUserOperatorQuery", User.class).getResultList();
		return ul;
	}

	@Override
	public boolean addOperator(Operator o, User u, int idUser) {
		
		try {
			ut.begin();
			User user = em.find(User.class, idUser);
			Admin adm = em.createNamedQuery("adminLoginQuery", Admin.class).setParameter("user", user).getSingleResult();
			o.setAdmin(adm);
			o.setDate(LocalDateTime.now());
			UserRole ur = addUserRole(u, 3);
			em.persist(u);
			em.persist(o);
			em.persist(ur);
			ut.commit();
			return true;
		} catch (NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException | RollbackException | IllegalStateException | SecurityException e) {
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
	public boolean updateOperatorData(int idOperator, String login, boolean active, String firtName, String lastName, String phoneNo, String email) {
		// TODO Auto-generated method stub
		
		System.out.println(login);
		
		return false;
	}

	@Override
	public boolean addAdmin(Admin a, User u) {
		try {
			ut.begin();
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

	
}
