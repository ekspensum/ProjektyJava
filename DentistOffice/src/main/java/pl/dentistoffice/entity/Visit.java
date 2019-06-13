package pl.dentistoffice.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;


@Entity
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDateTime visitDateTime;
	
	@OneToOne
	private VisitStatus status;
	
	@OneToOne
	private Patient patient;
	
	@OneToOne
	private Doctor doctor;
	
	@OneToOne
	private Assistant assistant;
	
	@ManyToMany
	private List<DentalTreatment> treatment;
}
