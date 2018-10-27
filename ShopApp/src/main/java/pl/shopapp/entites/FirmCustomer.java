package pl.shopapp.entites;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: FirmCustomer
 *
 */
@Entity
public class FirmCustomer implements Serializable {

	   
	@Id
	private int id;
	private String firmaName;
	private String zipCode;
	private String City;
	private String Street;
	private String streetNo;
	private String unitNo;
	private String taxNo;
	private String regon;
	private static final long serialVersionUID = 1L;

	public FirmCustomer() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getFirmaName() {
		return this.firmaName;
	}

	public void setFirmaName(String firmaName) {
		this.firmaName = firmaName;
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
	public String getStreet() {
		return this.Street;
	}

	public void setStreet(String Street) {
		this.Street = Street;
	}   
	public String getStreetNo() {
		return this.streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}   
	public String getUnitNo() {
		return this.unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}   
	public String getTaxNo() {
		return this.taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}   
	public String getRegon() {
		return this.regon;
	}

	public void setRegon(String regon) {
		this.regon = regon;
	}
   
}
