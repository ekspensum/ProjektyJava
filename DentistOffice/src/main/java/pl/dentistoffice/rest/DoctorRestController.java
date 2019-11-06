package pl.dentistoffice.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private AuthRestController authRestController;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@GetMapping(path = "/doctors")
	public DoctorListWrapper getDoctors(){
		
		List<Doctor> allDoctors = userService.getAllDoctors();

		DoctorListWrapper doctorListWrapper = new DoctorListWrapper();
		doctorListWrapper.setDoctorList(allDoctors);

		boolean authentication = authRestController.authentication(request, response);
		if(authentication) {	
			return doctorListWrapper;
		}
		return null;		
	}

	
}
