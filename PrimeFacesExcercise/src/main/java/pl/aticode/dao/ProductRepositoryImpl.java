package pl.aticode.dao;

import java.util.List;

import org.hibernate.Session;

import pl.aticode.entity.Category;
import pl.aticode.entity.Product;
import pl.aticode.service.InitService;

public class ProductRepositoryImpl implements ProductRepository {

	private final Session session;
	
	public ProductRepositoryImpl() {
		this.session = InitService.getSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findAll() {
		return session.createQuery("from Product").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findByCategoryId(Long categoryId) {
		return session.createNamedQuery("findProductByCategory").setParameter("categoryId", categoryId).getResultList();
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
