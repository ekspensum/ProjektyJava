package pl.dentistoffice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.pl.PESEL;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String firstName;
	private String lastName;
	
	@PESEL
	private String pesel;
	
	private String email;
	private String phone;
	
	@OneToOne
	private User user;
	private LocalDateTime registeredDateTime;
	private LocalDateTime editedDateTime;
}
