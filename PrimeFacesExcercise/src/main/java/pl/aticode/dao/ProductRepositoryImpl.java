package pl.aticode.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.aticode.config.InitApplication;
import pl.aticode.entity.Product;

public final class ProductRepositoryImpl implements ProductRepository {

	private static ProductRepository instance;
	private final Session session;
	
	private ProductRepositoryImpl() {
		this.session = InitApplication.getSession();
	}
	
	public static ProductRepository getInstance() {
		if(instance == null) {
			instance = new ProductRepositoryImpl();
		}
		return instance;
	}
	
	@Override
	public List<Product> findAll() {
		return session.createQuery("from Product", Product.class).getResultList();
	}

	@Override
	public List<Product> findByCategoryId(Long categoryId) {
		return session.createNamedQuery("findProductByCategory", Product.class).setParameter("categoryId", categoryId).getResultList();
	}

	@Override
	public void saveOrUpdate(Product product) throws Exception {
		final Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(product);		
		transaction.commit();
	}

	@Override
	public Product findById(Long id) {
		return session.find(Product.class, id);
	}

	@Override
	public List<Product> getTopProdcts() {
		List<Long> listOfProductId = new ArrayList<>();
		listOfProductId.add(1L);
		listOfProductId.add(3L);
		listOfProductId.add(5L);
		return session.createNamedQuery("getTopProducts", Product.class).setParameterList("topProductId", listOfProductId).getResultList();
	}

}
