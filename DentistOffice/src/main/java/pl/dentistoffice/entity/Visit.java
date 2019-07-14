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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
@NamedQueries({
	@NamedQuery(name = "readVisitsByDateTime", query = "SELECT visit FROM Visit visit WHERE visit.visitDateTime BETWEEN :dateTimeFrom AND :dateTimeTo"),
	@NamedQuery(name = "readVisitsByDateTimeAndStatus", query = "SELECT visit FROM Visit visit WHERE visit.visitDateTime BETWEEN :dateTimeFrom AND :dateTimeTo AND visit.status = :visitStatus"),
	@NamedQuery(name = "readVisitsByDateTimeAndConfirmation", query = "SELECT visit FROM Visit visit WHERE visit.visitDateTime BETWEEN :dateTimeFrom AND :dateTimeTo AND visit.visitConfirmation = :visitConfirmation"),
	@NamedQuery(name = "readVisitsByPatientAndStatus", query = "SELECT visit FROM Visit visit WHERE visit.patient = :patient AND visit.status = :visitStatus"),
	@NamedQuery(name = "readVisitsByDateTimeAndDoctor", query = "SELECT visit FROM Visit visit WHERE visit.visitDateTime BETWEEN :dateTimeFrom AND :dateTimeTo AND visit.doctor = :doctor"),
})
public class Visit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDateTime visitDateTime;
	private boolean visitConfirmation;
	
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
	
	@OneToOne
	private User userLogged;
	private LocalDateTime reservationDateTime;
}
