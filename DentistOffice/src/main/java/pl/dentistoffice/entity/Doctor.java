package pl.dentistoffice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Doctor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 12)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String firstName;
	
	@Size(min = 3, max = 20)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String lastName;
	
	@Email
	@NotEmpty
	private String email;
	
	@Size(min = 8, max = 20)
	private String phone;
	
	@Range(min=1, max=200)
	private int age;
	
	@Size(min=0, max=600000)
	private byte [] photo;
	@Transient
	private String base64Photo;
	
	@Valid
	@OneToOne
	private User user;
	
	@OneToOne
	private WorkingWeek workingWeek;
}
