package pl.shopapp.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import pl.shopapp.entites.Customer;
import pl.shopapp.entites.User;

/**
 * Session Bean implementation class CustomerOperations
 */
@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class CustomerBean implements CustomerBeanRemote, CustomerBeanLocal {

	@PersistenceContext(unitName="ShopAppEntites")
	EntityManager em;
	
	@Resource
	UserTransaction ut;
	
	List<Customer> cl;
    
	/**
     * Default constructor. 
     */
    public CustomerBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addCustomer(Customer c, User u) {

			try {
				ut.begin();

				em.persist(u);
				em.persist(c);

				ut.commit();

			} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException
					| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

	@Override
	public void updateCustomer(Customer c, int id) {
		em.getTransaction().begin();
		Customer cf = em.find(Customer.class, id);
		em.merge(cf);
		em.getTransaction().commit();
	}

	@Override
	public List<Customer> findCustomer(String lastName, String pesel, String companyName, String taxNo,	String regon) {
		cl = new ArrayList<>();
		cl = em.createQuery("SELECT c FROM Customer c WHERE c.lastName LIKE '%"+lastName+"%' AND c.companyName LIKE '%"+companyName+"%' AND c.taxNo LIKE '%"+taxNo+"%' AND c.regon LIKE '%"+regon+"%' ", Customer.class).getResultList();
		return cl;
	}

	@Override
	public void deleteCustomer(Customer c, int id) {
		em.getTransaction().begin();
		Customer cf = em.find(Customer.class, id);
		em.remove(cf);
		em.getTransaction().commit();
		
	}

}
