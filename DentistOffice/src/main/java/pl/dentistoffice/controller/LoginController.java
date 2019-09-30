package pl.dentistoffice.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pl.dentistoffice.service.UserService;

@Controller
@RequestMapping(path = "/")
@PropertySource(value = "classpath:/messages.properties")
public class LoginController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	public LoginController() {
	}

	public LoginController(Environment env, UserService userService) {
		this.env = env;
		this.userService = userService;
	}

	@RequestMapping(path = "/login")
	public String login(
						@RequestParam(value = "logout", required = false) String logout,
						@RequestParam(value = "error", required = false) String error,
						Model model) {
		
		if(error != null) {
			model.addAttribute("msg", env.getProperty("loginError"));
		}
		if(logout != null) {
			model.addAttribute("msg", env.getProperty("logout"));			
		}

		return "login";
	}

	@RequestMapping(path = "/loginSuccess")
	public String loginSuccess() {
		Collection<? extends GrantedAuthority> authoritiesLoggedUser = userService.getAuthoritiesLoggedUser();
		for (GrantedAuthority grantedAuthority : authoritiesLoggedUser) {
			if(grantedAuthority.getAuthority().equals("ROLE_PATIENT")) {
				return "forward:/panels/patientPanel";	
			}			
		}
		return "forward:/panels/employeePanel";
	}
	
	@RequestMapping(path = "/403")
	public String get403(Model model) {
		model.addAttribute("msg", env.getProperty("accessDenied"));
		return "login";
	}
}
