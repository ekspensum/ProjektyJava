package pl.shopapp.entites;

import java.io.Serializable;
import java.lang.String;
import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Operator
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="operatorQuery", query="SELECT op FROM Operator op WHERE op.user = :user"),
	@NamedQuery(name="getAllOperatorsQuery", query="SELECT op FROM Operator op"),
})
public class Operator implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNo;
	private String email;
	@OneToOne(cascade = CascadeType.PERSIST)
	private User user;
	@OneToOne
	private Admin admin;
	private LocalDateTime date;

	private static final long serialVersionUID = 1L;

	public Operator() {
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
	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String telNo) {
		this.phoneNo = telNo;
	}   
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}   
	public User getUser() {
		return this.user;
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
