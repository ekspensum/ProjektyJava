package pl.shopapp.beans;

import java.util.List;

import javax.ejb.Remote;

import pl.shopapp.entites.Admin;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.User;
import pl.shopapp.entites.UserRole;

@Remote
public interface UserBeanRemote {
	public boolean addCustomer(Customer c, User u);
	public boolean updateCustomer(Customer c, int id);
	public List<Customer> findCustomer(String lastName, String pesel, String companyName, String taxNo, String regon);
	public boolean deleteCustomer(Customer c, int id);
	public SessionData loginUser(String login, String password);
	
	public boolean addOperator(Operator o, User u, int idAdmin);
	public List<Operator> getOperatorsData();
	public boolean updateOperatorData(int idUser, int idOperator, String login, boolean active, String firstName, String lastName, String phoneNo, String email);
	
	public boolean addAdmin(Admin a, User u, int idUser);
	public SessionData loginAdmin(String login, String password);
	public List<Admin> getAdminsData();
	public boolean updateAdminData(int idUser, int idAdmin, String login, boolean active, String firstName, String lastName, String phoneNo, String email);
	
	public boolean findUserLogin(String login);
	public UserRole addUserRole(User u, int idRole);
	public List<User> getUsersOperatorData();
	public List<User> getUsersAdminData();
}
