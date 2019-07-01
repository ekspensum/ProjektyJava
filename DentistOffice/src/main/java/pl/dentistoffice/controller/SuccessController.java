package pl.dentistoffice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SuccessController {

	@RequestMapping(path = "/success")
	public String success() {
		return "success";
	}
}
