package pl.dentistoffice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Base64;

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

import org.hibernate.validator.constraints.pl.PESEL;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 15)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String firstName;
	
	@Size(min = 3, max = 25)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String lastName;

	@PESEL
	private String pesel;
	
	@Size(min = 2, max = 20)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String country;
	
	@Pattern(regexp = "^[0-9]{2}-[0-9]{3}$")
	private String zipCode;
	
	@Size(min = 2, max = 20)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String city;
	
	@Size(min = 2, max = 20)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String street;
	
	@Size(min = 1, max = 10)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String streetNo;
	
	@Size(min = 0, max = 10)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String unitNo;
	
	@Email
	@NotEmpty
	private String email;
	
	@Size(min = 8, max = 20)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String phone;
	
	@Size(min=0, max=600000)
	private byte [] photo;
	
	@Transient
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private String base64Photo;
	
	@Valid
	@OneToOne
	private User user;
	private LocalDateTime registeredDateTime;
	private LocalDateTime editedDateTime;
	
	public String getBase64Photo() {
		return Base64.getEncoder().encodeToString(this.photo);
	}
}
