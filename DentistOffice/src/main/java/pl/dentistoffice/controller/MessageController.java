package pl.dentistoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {

	@RequestMapping(path = "/message/employee/success")
	public String success() {
		return "/message/employee/success";
	}
	
	@RequestMapping(path = "/message/employee/error")
	public String error() {
		return "/message/employee/error";
	}
	
	@RequestMapping(path = "/message/patient/success")
	public String patientSuccess() {
		return "/message/patient/success";
	}
}
