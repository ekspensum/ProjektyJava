package pl.dentistoffice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.service.UserService;

@RestController
@RequestMapping(path = "/mobile")
public class PatientRestController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthRestController authRestController;
	
//	for aspect
	public AuthRestController getAuthRestController() {
		return authRestController;
	}
	
	@PutMapping(path = "/editPatient")
	public Boolean editPatientData(@RequestBody Patient patient) {	
		try {
			userService.editPatient(patient);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
