package pl.dentistoffice.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;


@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private SessionFactory session;
	
	protected Session getSession() {
		return session.getCurrentSession();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean saveUser(User user, int idRole1, int idRole2) {
		try {
			Role firstRole = new Role();
			firstRole.setId(idRole1);
			Role secondRole = new Role();
			secondRole.setId(idRole2);
			List<Role> roles = new ArrayList<>();
			roles.add(firstRole);
			roles.add(secondRole);
			user.setRoles(roles);
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordField()));
			getSession().persist(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public List<Role> fetchAllRoles() {

		return null;
	}

}
