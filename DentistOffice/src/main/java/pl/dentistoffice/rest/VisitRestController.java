package pl.dentistoffice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.service.VisitService;

@RestController
@RequestMapping(path = "/mobile")
public class VisitRestController {
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private AuthRestController authRestController;
	
//	for aspect
	public AuthRestController getAuthRestController() {
		return authRestController;
	}
	
	@PostMapping(path = "/visitStatus")
	public VisitStatus getVisitStatus(@RequestParam("id") String statusId) {
		
		VisitStatus visitStatus = visitService.getVisitStatus(Integer.valueOf(statusId));
		return visitStatus;
	}
}
