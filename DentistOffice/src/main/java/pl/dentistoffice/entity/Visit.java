package pl.dentistoffice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Visit implements Serializable {

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
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<DentalTreatment> treatments;
}
