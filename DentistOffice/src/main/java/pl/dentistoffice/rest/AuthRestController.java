package pl.dentistoffice.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
	private UserService userService;
	
	@Autowired
	private CipherService cipherService;
	
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
					response.setHeader("token", encodeTokenBase64);
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

	public boolean authentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			String encodeTokenBase64 = request.getHeader("token");
			if (encodeTokenBase64 != null && !encodeTokenBase64.equals("")) {
				String decodeToken = cipherService.decodeToken(encodeTokenBase64);
				Patient patient = userService.findMobilePatientByToken(decodeToken);
				if (patient != null) {
					return true;
				} else {
					response.sendError(403);
				}
			} else {
				response.sendError(403);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
