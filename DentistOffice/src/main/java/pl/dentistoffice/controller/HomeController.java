package pl.dentistoffice.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.dentistoffice.service.UserService;


@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;


	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model) {

		
		return "home";
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String selectDoctor(Model model) {

		return "home";
	}
}

