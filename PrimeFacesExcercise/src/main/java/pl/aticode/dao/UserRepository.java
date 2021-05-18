package pl.aticode.dao;

import java.util.List;

import pl.aticode.entity.User;

public interface UserRepository {
	
	List<User> findAll();
	void saveOrUpdate(User user);
	User findById(Long id);
	User findByUsername(String username);

}
