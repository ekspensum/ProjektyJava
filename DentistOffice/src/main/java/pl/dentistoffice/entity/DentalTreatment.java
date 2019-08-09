package pl.dentistoffice.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@NamedQueries({
	@NamedQuery(name = "readDentalTreatmentByNme", query = "SELECT dt FROM DentalTreatment dt WHERE dt.name LIKE :name")
})
public class DentalTreatment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String name;
	private String description;
	private double price;
	
	@ManyToMany
	private List<TreatmentCategory> treatmentCategory;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "treatments")
	private List<Visit> visits;
	
	@OneToOne
	private User userLogged;
}
