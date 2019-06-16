package pl.dentistoffice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class WorkingTime implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Lob
	private Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> workingTimeMap;
}
