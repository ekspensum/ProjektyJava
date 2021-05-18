package pl.aticode.dao;

import java.util.List;

import pl.aticode.entity.Ordering;

public interface OrderingRepository {

	List<Ordering> findAll();
	void saveOrUpdate(Ordering ordering);
	Ordering findById(Long id);
}
