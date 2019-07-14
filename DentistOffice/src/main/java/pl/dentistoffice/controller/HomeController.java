package pl.dentistoffice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import pl.dentistoffice.service.UserService;


@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;


	@RequestMapping(path="/")
	public String home(Model model) {

		
		return "home";
	}
	
	@RequestMapping(path="/doctors")
	public String doctors(Model model) {

		
		return "doctors";
	}
	@RequestMapping(path="/services")
	public String services(Model model) {

		
		return "services";
	}
	@RequestMapping(path="/agenda")
	public String agenda(Model model) {

		
		return "agenda";
	}

}

