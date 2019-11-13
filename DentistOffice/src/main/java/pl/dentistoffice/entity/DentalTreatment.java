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
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@NamedQueries({
	@NamedQuery(name = "readDentalTreatmentByNme", query = "SELECT dt FROM DentalTreatment dt WHERE dt.name LIKE :name")
})
@Indexed
public class DentalTreatment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 100)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	@Field
	private String name;
	
	@Size(min = 10, max = 500)
	@Pattern(regexp="^[^|'%^#~}{\\]\\[;=<>`]*$")
	@Field
	private String description;
	
	@DecimalMax("10000.0") @DecimalMin("0.0") 
	private double price;
	
	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<TreatmentCategory> treatmentCategory;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "treatments")
	@JsonIgnore
	private List<Visit> visits;
	
	@OneToOne
	@JsonIgnore
	private User userLogged;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)	
	private LocalDateTime registeredDateTime;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)	
	private LocalDateTime editedDateTime;
}
