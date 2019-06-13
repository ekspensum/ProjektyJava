package pl.dentistoffice.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Billing {

	private int id;
	
	@OneToOne
	private Visit visit;
	
	@OneToOne
	private Doctor doctor;
	
	@ManyToMany(mappedBy = "treatment")
	private List<DentalTreatment> treatment;
}
