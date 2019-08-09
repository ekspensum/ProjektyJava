package pl.dentistoffice.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@NamedQueries({
	@NamedQuery(name="readBilingsByDate", query="SELECT billing FROM Billing billing WHERE billing.billingDate BETWEEN :dateFrom AND :dateTo"),
	@NamedQuery(name="readBilingsByDateAndPatient", query="SELECT billing FROM Billing billing WHERE billing.billingDate BETWEEN :dateFrom AND :dateTo AND visit.patient = :patient"),
	@NamedQuery(name="readBilingsByDateAndDoctor", query="SELECT billing FROM Billing billing WHERE billing.billingDate BETWEEN :dateFrom AND :dateTo AND visit.doctor = :doctor"),
})
public class Billing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private LocalDate billingDate;
	
	@OneToOne
	private Visit visit;
	
	@OneToOne
	private User userLogged;
}
