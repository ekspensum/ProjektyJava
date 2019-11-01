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
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.validator.constraints.pl.PESEL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@NamedQueries({
	@NamedQuery(name = "findPatientByUserName", query = "SELECT patient FROM Patient patient INNER JOIN patient.user user WHERE user.username = :username"),
	@NamedQuery(name = "findPatientByToken", query = "SELECT patient FROM Patient patient WHERE patient.token = :token")
})
@Indexed
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonIgnore
	private String token;
	
	@Size(min = 3, max = 15)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String firstName;
	
	@Size(min = 3, max = 25)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	@Field(index = Index.YES)
	private String lastName;

	@PESEL
	@Field(index = Index.YES)
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
	@Field(index = Index.YES)
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
	@Field(index = Index.YES)
	private String phone;
	
	@Size(min=0, max=600000)
	private byte [] photo;
	
	@Valid
	@OneToOne
	@Cascade({CascadeType.PERSIST, CascadeType.SAVE_UPDATE})
	private User user;
	
	@Field
	@JsonIgnore
	private String activationString;
	
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime registeredDateTime;
	
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
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
