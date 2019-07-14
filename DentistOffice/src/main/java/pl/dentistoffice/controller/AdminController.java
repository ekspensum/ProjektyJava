package pl.dentistoffice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/panels/adminPanel")
	public String adminPanel(Model model) {

		return "adminPanel";
	}

}
