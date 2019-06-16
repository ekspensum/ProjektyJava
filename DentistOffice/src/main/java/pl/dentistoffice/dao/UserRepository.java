package pl.dentistoffice.dao;

import java.util.List;

import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;



public interface UserRepository {

	boolean saveUser(User user, int idRole1, int idRole2);
	List<Role> fetchAllRoles(); 
}
