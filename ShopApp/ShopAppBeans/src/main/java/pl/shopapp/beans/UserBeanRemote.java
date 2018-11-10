package pl.shopapp.beans;

import java.util.List;

import javax.ejb.Remote;

import pl.shopapp.entites.Customer;
import pl.shopapp.entites.User;
import pl.shopapp.entites.UserRole;

@Remote
public interface UserBeanRemote {
	public boolean addCustomer(Customer c, User u);
	public boolean updateCustomer(Customer c, int id);
	public List<Customer> findCustomer(String lastName, String pesel, String companyName, String taxNo, String regon);
	public boolean deleteCustomer(Customer c, int id);
	public SessionData loginCustomer(String login, String password);
	
	public UserRole addUserRole(User u, int idRole);

}
