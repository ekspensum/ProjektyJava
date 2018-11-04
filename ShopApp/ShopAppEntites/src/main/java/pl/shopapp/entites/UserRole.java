package pl.shopapp.entites;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: UserRole
 *
 */
@Entity
public class UserRole implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private User user;
	
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Role role;
	
	private static final long serialVersionUID = 1L;

	public UserRole() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	

}
