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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

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
	@NamedQuery(name = "readVisitsByDateTimeAndStatusAndDoctor", query = "SELECT visit FROM Visit visit WHERE visit.visitDateTime BETWEEN :dateTimeFrom AND :dateTimeTo AND visit.status = :visitStatus AND visit.doctor = :doctor"),
})
@Indexed
public class Visit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Field(index = Index.YES)
	private LocalDateTime visitDateTime;
	private boolean visitConfirmation;
	
	@OneToOne
	@IndexedEmbedded
	private VisitStatus status;
	
	@OneToOne
	@IndexedEmbedded
	private Patient patient;
	
	@OneToOne
	@IndexedEmbedded
	private Doctor doctor;
	
	@OneToOne
	private Assistant assistant;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private List<DentalTreatment> treatments;
	
	@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@Cascade({CascadeType.PERSIST, CascadeType.SAVE_UPDATE})
	private List<VisitTreatmentComment> visitTreatmentComment;
	
	@OneToOne
	private User userLogged;
	private LocalDateTime reservationDateTime;
	
	private LocalDateTime finalizedVisitDateTime;
}
