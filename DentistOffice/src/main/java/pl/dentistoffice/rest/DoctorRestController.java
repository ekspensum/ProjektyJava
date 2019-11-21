package pl.dentistoffice.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.service.UserService;

@RestController
@RequestMapping(path = "/mobile")
public class DoctorRestController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private DoctorListWrapper doctorListWrapper;
	
	@GetMapping(path = "/doctors")
	public DoctorListWrapper getDoctors(){
		List<Doctor> allDoctors = userService.getAllDoctors();
		doctorListWrapper.setDoctorList(allDoctors);
		return doctorListWrapper;
	}

	
}
