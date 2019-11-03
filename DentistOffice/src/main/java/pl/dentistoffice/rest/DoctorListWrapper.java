package pl.dentistoffice.rest;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import pl.dentistoffice.entity.Doctor;

@Getter @Setter
public class DoctorListWrapper {

	private List<Doctor> doctorList;
	
}
