package pl.shopapp.beans;

import java.io.Serializable;

public class SessionData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idUser;
	private int idRole;
	private String roleName;	
	private String firstName;
	private String lastName;
	private boolean active;
	private BasketBeanLocal basketBeanLocal;
	
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

	public BasketBeanLocal getBasketBeanLocal() {
		return basketBeanLocal;
	}

	public void setBasketBeanLocal(BasketBeanLocal basketBeanLocal) {
		this.basketBeanLocal = basketBeanLocal;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
}
