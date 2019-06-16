package pl.dentistoffice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "Users")
@Getter @Setter
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 12)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String username;
	
	private String password;
	private boolean enabled;
	
	@Size(min = 4, max = 24)
	@Transient
	private String passwordField;
	
	@OneToMany
	private List<Role> roles;

}
