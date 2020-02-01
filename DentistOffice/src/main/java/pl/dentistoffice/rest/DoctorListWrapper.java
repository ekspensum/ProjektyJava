package pl.dentistoffice.rest;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import pl.dentistoffice.entity.Doctor;

@Component
@Getter @Setter
public class DoctorListWrapper {

	private List<Doctor> doctorList;
	
}
