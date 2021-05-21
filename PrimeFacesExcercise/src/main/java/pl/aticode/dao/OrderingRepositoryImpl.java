package pl.aticode.dao;

import java.util.List;

import org.hibernate.Session;

import pl.aticode.entity.ProductOrder;
import pl.aticode.service.InitService;

public final class OrderingRepositoryImpl implements OrderingRepository {

	private static OrderingRepository instance;
	private final Session session;
	
	private OrderingRepositoryImpl() {
		session = InitService.getSession();
	}
	
	public static OrderingRepository getInstance() {
		if(instance == null) {
			instance = new OrderingRepositoryImpl();
		}
		return instance;
	}
	
	@Override
	public List<ProductOrder> findAll() {
		return session.createQuery("from ProductOrder", ProductOrder.class).getResultList();
	}

	@Override
	public void saveOrUpdate(ProductOrder productOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ProductOrder findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
