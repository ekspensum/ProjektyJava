package pl.aticode.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.aticode.config.InitApplication;
import pl.aticode.entity.Category;

public final class CategoryRepositoryImpl implements CategoryRepository {

	private static CategoryRepository instance;
	private final Session session;
	
	private CategoryRepositoryImpl() {
		this.session = InitApplication.getSession();
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
	public void saveOrUpdate(Category category) throws Exception {
		final Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(category);
		transaction.commit();
	}

	@Override
	public Category findById(Long id) {
		return session.find(Category.class, id);
	}

}
