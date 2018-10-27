package pl.shopapp.entites;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: PrivateCustomer
 *
 */
@Entity
public class PrivateCustomer implements Serializable {

	   
	@Id
	private int id;
	private String firstName;
	private String lastName;
	private String zipCode;
	private String City;
	private String pesel;
	private static final long serialVersionUID = 1L;

	public PrivateCustomer() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}   
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}   
	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}   
	public String getCity() {
		return this.City;
	}

	public void setCity(String City) {
		this.City = City;
	}   
	public String getPesel() {
		return this.pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}
   
}
