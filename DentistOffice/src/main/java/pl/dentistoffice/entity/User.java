package pl.dentistoffice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;

import lombok.Getter;
import lombok.Setter;


@Entity(name = "Users")
@Getter @Setter
@Indexed
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 12)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	@Field(index = Index.YES)
	private String username;
	
	private String password;
	private boolean enabled;
	
	@Size(min = 4, max = 24)
	@Transient
	private String passwordField;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Role> roles;

}
