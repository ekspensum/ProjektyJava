package pl.aticode.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.aticode.config.InitApplication;
import pl.aticode.entity.ProductOrder;

public final class OrderingRepositoryImpl implements OrderingRepository {

	private static OrderingRepository instance;
	private final Session session;
	
	private OrderingRepositoryImpl() {
		session = InitApplication.getSession();
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
	public void saveOrUpdate(ProductOrder productOrder) throws Exception {
		final Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(productOrder);
		transaction.commit();
	}

	@Override
	public ProductOrder findById(Long id) {
		return session.find(ProductOrder.class, id);
	}

}
