package pl.shopapp.entites;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="loginQuery", query="SELECT u FROM User u WHERE u.login = :login AND u.password = :password AND u.active = true"),
	@NamedQuery(name="findUserLoginQuery", query="SELECT u FROM User u WHERE u.login = :login"),
	@NamedQuery(name="getUserOperatorQuery", query="SELECT u FROM User u WHERE u.role.id = 3"),
	@NamedQuery(name="getUserAdminQuery", query="SELECT u FROM User u WHERE u.role.id = 1")
})
@Table(name = "\"User\"") //for integration test with Derby database
public class User implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String login;
	private String password;
	private boolean active;
	
	@OneToOne
	private Role role; 
	
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}   
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
   
}
