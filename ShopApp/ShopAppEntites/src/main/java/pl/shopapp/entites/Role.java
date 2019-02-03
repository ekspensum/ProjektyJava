package pl.shopapp.entites;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Role
 *
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "roleQuery", query = "SELECT r FROM Role r")
		})
public class Role implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String roleName;
	private static final long serialVersionUID = 1L;

	public Role() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}   

   
}
