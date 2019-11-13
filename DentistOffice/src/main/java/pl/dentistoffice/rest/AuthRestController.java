package pl.dentistoffice.rest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.service.CipherService;
import pl.dentistoffice.service.UserService;

@RestController
@RequestMapping(path = "/mobile")
public class AuthRestController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CipherService cipherService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	private Map<Integer, LocalDateTime> patientLoggedMap;
	

	public AuthRestController() {
		patientLoggedMap = new HashMap<>();
	}

	public Map<Integer, LocalDateTime> getPatientLoggedMap() {
		return patientLoggedMap;
	}

	@PostMapping(path = "/login")
	public Patient login(@RequestParam("username") final String username,
						@RequestParam("password") final String password, 
						HttpServletResponse response) {

		try {
			Patient loggedPatient = userService.loginMobilePatient(username, password);
			if (loggedPatient != null) {
				String token = loggedPatient.getToken();
				String encodeTokenBase64 = cipherService.encodeToken(token);
				if (encodeTokenBase64 != null) {
					response.setHeader(HttpHeaders.AUTHORIZATION, encodeTokenBase64);
					patientLoggedMap.put(loggedPatient.getId(), LocalDateTime.now()
									.plusSeconds(Integer.valueOf(env.getProperty("patientLoggedValidTime"))).withNano(0));
					return loggedPatient;
				} else {
					response.sendError(401);
				}				
			} else {
				response.sendError(401);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping(path = "/logout")
	public String logout() {
		
		return "logout";
	}

	public boolean authentication() {
		try {
			String encodeTokenBase64 = request.getHeader(HttpHeaders.AUTHORIZATION);

			System.out.println("AuthRestController - token from request: " + encodeTokenBase64);

			if (encodeTokenBase64 != null && !encodeTokenBase64.equals("")) {
				String decodeToken = cipherService.decodeToken(encodeTokenBase64);
				Patient patient = userService.findMobilePatientByToken(decodeToken);
				if (patient != null) {
					LocalDateTime validDateTime = patientLoggedMap.get(patient.getId());
					if (validDateTime != null) {
						if (LocalDateTime.now().withNano(0).isBefore(validDateTime)) {
							patientLoggedMap.put(patient.getId(), LocalDateTime.now()
											.plusSeconds(Integer.valueOf(env.getProperty("patientLoggedValidTime"))).withNano(0));
							return true;
						} else {
							response.sendError(403);
							System.out.println("Forbidden-1");
						}
					} else {
						response.sendError(403);
						System.out.println("Forbidden-2");
					}
				} else {
					response.sendError(403);
					System.out.println("Forbidden-3");
				}
			} else {
				response.sendError(403);
				System.out.println("Forbidden-4");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
