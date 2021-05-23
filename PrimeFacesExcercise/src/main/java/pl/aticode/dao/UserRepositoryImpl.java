package pl.aticode.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.aticode.config.InitApplication;
import pl.aticode.entity.User;

public final class UserRepositoryImpl implements UserRepository {
	
	private static UserRepository instance;
	private final Session session;

	private UserRepositoryImpl() {
		session = InitApplication.getSession();
	}
	
	public static UserRepository getInstance() {
		if(instance == null) {
			instance = new UserRepositoryImpl();
		}
		return instance;
	}

	@Override
	public List<User> findAll() {
		return session.createQuery("from User", User.class).getResultList();
	}

	@Override
	public void saveOrUpdate(User user) throws Exception {
		final Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(user);
		transaction.commit();
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
