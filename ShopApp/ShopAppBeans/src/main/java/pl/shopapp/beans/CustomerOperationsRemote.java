package pl.shopapp.beans;

import java.util.List;

import javax.ejb.Remote;

import pl.shopapp.entites.Customer;
import pl.shopapp.entites.User;

@Remote
public interface CustomerOperationsRemote {
	public void addCustomer(Customer c, User u);
	public void updateCustomer(Customer c, int id);
	public List<Customer> findCustomer(String lastName, String pesel, String companyName, String taxNo, String regon);
	public void deleteCustomer(Customer c, int id);
}
