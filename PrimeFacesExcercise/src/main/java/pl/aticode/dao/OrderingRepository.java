package pl.aticode.dao;

import java.util.List;

import pl.aticode.entity.ProductOrder;

public interface OrderingRepository {

	List<ProductOrder> findAll();
	void saveOrUpdate(ProductOrder productOrder) throws Exception;
	ProductOrder findById(Long id);
}
