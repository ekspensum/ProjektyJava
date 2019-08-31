package pl.dentistoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {
	
	@RequestMapping(path = "/message/error")
	public String error() {
		return "/message/error";
	}

	@RequestMapping(path = "/message/employee/success")
	public String employeeSuccess() {
		return "/message/employee/success";
	}
	
	@RequestMapping(path = "/message/employee/error")
	public String employeeError() {
		return "/message/employee/error";
	}
	
	@RequestMapping(path = "/message/patient/success")
	public String patientSuccess() {
		return "/message/patient/success";
	}
	
	@RequestMapping(path = "/message/patient/error")
	public String patientError() {
		return "/message/patient/error";
	}
}
