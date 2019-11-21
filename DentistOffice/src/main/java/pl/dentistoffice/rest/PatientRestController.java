package pl.dentistoffice.rest;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.service.CipherService;
import pl.dentistoffice.service.UserService;

@RestController
@RequestMapping(path = "/mobile")
public class PatientRestController {
	
	@Autowired
	private Environment env;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthRestController authRestController;
	@Autowired
	private CipherService cipherService;
	
//	for aspect
	public AuthRestController getAuthRestController() {
		return authRestController;
	}
	
	@PostMapping(path = "/registerPatient")
	public String registerPatient(@RequestBody Patient patient, HttpServletRequest request, HttpServletResponse response) {
		try {
			String header = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (header != null && !header.equals("")) {
				String decodeToken = cipherService.decodeToken(header);
				String timeoutString = decodeToken.substring(0, 19);
				String token = decodeToken.substring(19, decodeToken.length());
				LocalDateTime timeout = LocalDateTime.parse(timeoutString);
				if(token.equals(env.getProperty("tokenForAddNewPatient"))) {
					if (LocalDateTime.now().withNano(0).isBefore(timeout)) {
						if(userService.checkDinstinctLoginWithRegisterUser(patient.getUser().getUsername())) {
							userService.addNewPatient(patient, request.getContextPath());
							return "true";							
						} else {
							return "NotDistinctLogin";
						}
					} else {
						return "Timeout";
					}					
				} else {
					response.addHeader(HttpHeaders.WARNING, "NotAuthenticated");
				}
			} else {
				response.addHeader(HttpHeaders.WARNING, "NoHeader");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.addHeader(HttpHeaders.WARNING, e.getMessage());
		}
		return "error";
	}
	
	@PutMapping(path = "/editPatient")
	public String editPatientData(@RequestBody Patient patient, HttpServletResponse response) {	
		try {
			boolean dinstinctLogin = userService.checkDinstinctLoginWithEditUser(patient.getUser().getUsername(), patient.getUser().getId());
			if(dinstinctLogin) {
				userService.editPatient(patient);
				return "true";				
			} else {
				return "NotDistinctLogin";
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.addHeader(HttpHeaders.WARNING, e.getMessage());
		}
		return "error";
	}
}
