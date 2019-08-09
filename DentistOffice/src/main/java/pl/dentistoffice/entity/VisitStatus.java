package pl.dentistoffice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.search.annotations.Field;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class VisitStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	
	@Field
	private String statusName;
	private String description;
	
	@OneToOne
	private User userLogged;
}
