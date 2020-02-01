package pl.dentistoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController {
	
	@RequestMapping(path = "/panels/employeePanel")
	public String adminPanel(Model model) {		
		return "employeePanel";
	}

}
