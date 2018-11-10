package pl.shopapp.beans;

import pl.shopapp.entites.Customer;
import pl.shopapp.entites.UserRole;

public class SessionData {
	private int idCustomer;
	private int idRole;
	private String roleName;	
	private String firstName;
	private String lastName;
	private boolean active;
	
	public SessionData getSessionData(Customer c, UserRole ur) {
		this.idCustomer = ur.getUser().getId();
		this.idRole = ur.getRole().getId();
		
		return this;
		
	}

	public int getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getIdRole() {
		return idRole;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
