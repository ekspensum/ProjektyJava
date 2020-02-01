package pl.dentistoffice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@NamedQueries({
	@NamedQuery(name = "findTreatmentCategoryAboveFirstId", query = "SELECT tc FROM TreatmentCategory tc WHERE tc.id > 1")
})
public class TreatmentCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 100)
	@Pattern(regexp="^[^|'\":%^#~}{\\]\\[;=<>`]*$")
	private String categoryName;
	
	@ManyToMany(mappedBy = "treatmentCategory")
	@LazyCollection(LazyCollectionOption.FALSE) //dedicated for hibernate
	private List<DentalTreatment> dentalTreatment;
	
	@OneToOne
	private User userLogged;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)	
	private LocalDateTime registeredDateTime;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)	
	private LocalDateTime editedDateTime;
}
