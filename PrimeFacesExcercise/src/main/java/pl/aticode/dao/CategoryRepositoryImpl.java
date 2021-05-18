package pl.aticode.dao;

import java.util.List;

import org.hibernate.Session;

import pl.aticode.entity.Category;
import pl.aticode.service.InitService;

public class CategoryRepositoryImpl implements CategoryRepository {

	private final Session session;
	
	public CategoryRepositoryImpl() {
		this.session = InitService.getSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findAll() {	
		return session.createQuery("from Category").getResultList();
	}

	@Override
	public void saveOrUpdate(Category category) {
		session.saveOrUpdate(category);
	}

	@Override
	public Category findById(Long id) {
		return session.find(Category.class, id);
	}

}
