package pl.shopapp.entites;

import java.io.Serializable;
import java.lang.String;
import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Admin
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="adminLoginQuery", query="SELECT a FROM Admin a WHERE a.user = :user"),
	@NamedQuery(name="getAllAdminsQuery", query="SELECT ad FROM Admin ad")
})
public class Admin implements Serializable {
	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String email;
	@OneToOne
	private User user;
	@OneToOne
	private Admin admin;
	private LocalDateTime date;
	private static final long serialVersionUID = 1L;

	public Admin() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getTelNo() {
		return getPhoneNo();
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setTelNo(String telNo) {
		setPhoneNo(telNo);
	}

	public void setPhoneNo(String telNo) {
		this.phoneNo = telNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}   
	
}
