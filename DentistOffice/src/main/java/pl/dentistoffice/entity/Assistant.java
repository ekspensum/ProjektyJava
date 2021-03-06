package pl.dentistoffice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Base64;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.pl.PESEL;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@NamedQueries({
	@NamedQuery(name = "findAssistantByUserName", query = "SELECT assist FROM Assistant assist INNER JOIN assist.user user WHERE user.username = :username")
})
public class Assistant implements Serializable {

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
	
	@Email
	@NotEmpty
	private String email;
	
	@Size(min = 8, max = 20)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String phone;

	@PESEL
	private String pesel;
	
	@Size(min=0, max=600000)
	private byte [] photo;
	
	@Valid
	@OneToOne
	@Cascade({CascadeType.PERSIST, CascadeType.SAVE_UPDATE})
	private User user;
	private LocalDateTime registeredDateTime;
	private LocalDateTime editedDateTime;
	
	@JsonIgnore
	public String getBase64Photo() {
		if(photo == null) {
			return "";
		} else {
			return Base64.getEncoder().encodeToString(this.photo);			
		}
	}
}
