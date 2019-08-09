package pl.dentistoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {

	@RequestMapping(path = "/success")
	public String success() {
		return "success";
	}
	
	@RequestMapping(path = "/error")
	public String error() {
		return "error";
	}
}
