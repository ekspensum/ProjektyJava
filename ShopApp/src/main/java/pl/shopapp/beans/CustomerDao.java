package pl.shopapp.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.shopapp.entites.FirmCustomer;
import pl.shopapp.entites.PrivateCustomer;

/**
 * Session Bean implementation class CustomerDao
 */
@Stateful
@LocalBean
public class CustomerDao implements CustomerDaoRemote, CustomerDaoLocal {
	
	List<PrivateCustomer> listPC; 
	
	@PersistenceContext
	EntityManager em;

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

		return null;
	}

    
	
	
}
