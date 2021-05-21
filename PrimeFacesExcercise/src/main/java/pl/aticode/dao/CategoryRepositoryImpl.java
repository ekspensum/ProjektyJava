package pl.aticode.dao;

import java.util.List;

import org.hibernate.Session;

import pl.aticode.entity.Category;
import pl.aticode.service.InitService;

public final class CategoryRepositoryImpl implements CategoryRepository {

	private static CategoryRepository instance;
	private final Session session;
	
	private CategoryRepositoryImpl() {
		this.session = InitService.getSession();
	}

	public static CategoryRepository getInstance() {
		if(instance == null){
			instance = new CategoryRepositoryImpl();
		}
		return instance;
	}
	
	@Override
	public List<Category> findAll() {	
		return session.createQuery("from Category", Category.class).getResultList();
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
