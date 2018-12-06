package pl.shopapp.entites;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: UserRole
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="userRoleQuery", query="SELECT ur FROM UserRole ur WHERE ur.user = :user"),
	@NamedQuery(name="getUserOperatorQuery", query="SELECT u FROM UserRole ur INNER JOIN ur.user u WHERE ur.role.id = 3")
})
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
