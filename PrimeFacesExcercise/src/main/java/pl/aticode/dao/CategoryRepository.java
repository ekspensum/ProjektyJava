package pl.aticode.dao;

import java.util.List;

import pl.aticode.entity.Category;

public interface CategoryRepository {

	List<Category> findAll();
	void saveOrUpdate(Category category);
	Category findById(Long id);
}
