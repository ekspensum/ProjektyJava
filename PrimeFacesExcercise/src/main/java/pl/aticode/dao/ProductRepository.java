package pl.aticode.dao;

import java.util.List;

import pl.aticode.entity.Product;

public interface ProductRepository {
	
	List<Product> findAll();
	List<Product> findByCategoryId(Long categoryId);
	void saveOrUpdate(Product product);
	Product findById(Long id);

}
