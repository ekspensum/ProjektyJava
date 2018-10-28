package pl.shopapp.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.shopapp.dao.FirmCustomer;
import pl.shopapp.dao.PrivateCustomer;

/**
 * Session Bean implementation class Customer
 */
@Stateful
@LocalBean
public class Customer implements CustomerRemote, CustomerLocal {

	List<PrivateCustomer> listPC; 
	
	@PersistenceContext
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public Customer() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void addPrivateCustomer(PrivateCustomer pc) {
		em.persist(pc);
		
	}

	@Override
	public void updatePrivateCustomer(PrivateCustomer pc) {
		em.merge(pc);
		
	}

	@Override
	public List<PrivateCustomer> findPrivateCustomer(String lastName, String pesel) {
		listPC = new ArrayList<>();
		if(lastName != null || pesel != null) {
			listPC = em.createQuery("SELECT pc FROM PrivateCustomer pc WHERE pc.lastName LIKE '%"+lastName+"%' AND pc.pesel LIKE '%"+pesel+"%'", PrivateCustomer.class).getResultList();
			
		}
		return listPC;
//		return null;
	}

	@Override
	public void addFirmCustomer(FirmCustomer fc) {
		em.persist(fc);
		
	}

	@Override
	public void updateFirmCustomer(FirmCustomer pc) {
		em.merge(pc);
		
	}

	@Override
	public List<FirmCustomer> findFirmCustomer(String firmName, String taxNo, String regon) {
		// TODO Auto-generated method stub
		return null;
	}
    
    

}
