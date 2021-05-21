package pl.aticode.dao;

import java.util.List;

import org.hibernate.Session;

import pl.aticode.entity.Product;
import pl.aticode.service.InitService;

public final class ProductRepositoryImpl implements ProductRepository {

	private static ProductRepository instance;
	private final Session session;
	
	private ProductRepositoryImpl() {
		this.session = InitService.getSession();
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
	public void saveOrUpdate(Product product) {
		session.saveOrUpdate(product);		
	}

	@Override
	public Product findById(Long id) {
		return session.find(Product.class, id);
	}

}
