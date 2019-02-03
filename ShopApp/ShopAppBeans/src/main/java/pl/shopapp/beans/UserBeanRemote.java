package pl.shopapp.beans;

import java.util.List;

import javax.ejb.Remote;

import pl.shopapp.entites.Admin;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Role;
import pl.shopapp.entites.SettingsApp;
import pl.shopapp.entites.User;
import pl.shopapp.entites.UserRole;

@Remote
public interface UserBeanRemote {
	public boolean addCustomer(String login, String password, String firstName, String lastName, String pesel, String zipCode, String country, String city, String street, String streetNo, String unitNo, String email, boolean isCompany, String companyName, String taxNo, String regon);
	public boolean updateCustomer(String login, String password, String firstName, String lastName, String pesel, String zipCode, String country, String city, String street, String streetNo, String unitNo, String email, boolean isCompany, String companyName, String taxNo, String regon, int idUser);
	public Customer findCustomer(User u);
	public boolean setActiveCustomer(int idCustomer, boolean action);
	public SessionData loginUser(String login, String password);
	public boolean addRole(String roleName);
	public List<Role> getRoleList();
	
	public boolean addOperator(String firstName, String lastName, String phoneNo, String email, String login, String password, int idUser);
	public List<Operator> getOperatorsData();
	public boolean updateOperatorData(int idUser, int idOperator, String login, boolean active, String firstName, String lastName, String phoneNo, String email);
	
	public boolean addAdmin(String firstName, String lastName, String phoneNo, String email, String login, String password, int idUser);
	public SessionData loginAdmin(String login, String password);
	public List<Admin> getAdminsData();
	public boolean updateAdminData(int idUser, int idAdmin, String login, boolean active, String firstName, String lastName, String phoneNo, String email);
	
	public User findUser(int idUser);
	public boolean findUserLogin(String login);
	public UserRole addUserRole(User u, int idRole);
	public List<User> getUsersOperatorData();
	public List<User> getUsersAdminData();
	
	public SettingsApp getSettingsApp();
	public boolean setSettingsApp(int minCharInPass, int maxCharInPass, int upperCaseInPass, int numbersInPass, int minCharInLogin, int maxCharInLogin, int sessionTime, int idUser);
}
