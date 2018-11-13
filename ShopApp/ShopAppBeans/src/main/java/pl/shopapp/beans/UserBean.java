package pl.shopapp.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
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

import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Role;
import pl.shopapp.entites.User;
import pl.shopapp.entites.UserRole;

/**
 * Session Bean implementation class CustomerOperations
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
//@DeclareRoles({"customer", "admin"})
public class UserBean implements UserBeanRemote, UserBeanLocal {

	@PersistenceContext(unitName="ShopAppEntites")
	EntityManager em;
	
	@Resource
	UserTransaction ut;
	
	List<Customer> cl;
    
//	@Resource 
//	private SessionContext sc;
	
	/**
     * Default constructor. 
     */
    public UserBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
//	@RolesAllowed({"customer"})
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
	public SessionData loginCustomer(String login, String password) {
		User user = null; 		
		try {
			user = em.createNamedQuery("loginQuery", User.class).setParameter("login", login).setParameter("password", password).getSingleResult();
		} catch (NoResultException e) {
//			e.printStackTrace();
			return null;
		}
		UserRole ur = em.createNamedQuery("userRoleQuery", UserRole.class).setParameter("user", user).getSingleResult();
		Customer c = em.createNamedQuery("customerQuery", Customer.class).setParameter("user", user).getSingleResult();
		SessionData data = new SessionData();
		data.setIdCustomer(c.getId());
		data.setFirstName(c.getFirstName());
		data.setLastName(c.getLastName());
		data.setIdRole(ur.getRole().getId());
		data.setRoleName(ur.getRole().getRoleName());
		data.setActive(user.getActive());
		return data;
	}

}
