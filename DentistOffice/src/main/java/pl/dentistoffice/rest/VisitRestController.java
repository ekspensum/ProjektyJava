package pl.dentistoffice.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@RestController
@RequestMapping(path = "/mobile")
public class VisitRestController {
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WorkingWeekMapWrapper workingWeekMapWrapper;
	
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
	
	@PostMapping(path = "/workingWeekMap")
	public WorkingWeekMapWrapper getWorkingWeekFreeTimeMap(@RequestParam("doctorId") String doctorId, 
																											@RequestParam("dayStart") String dayStart, 
																											@RequestParam("dayEnd") String dayEnd) {
		
		Doctor doctor = userService.getDoctor(Integer.valueOf(doctorId));
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor, Integer.valueOf(dayStart), Integer.valueOf(dayEnd));
		workingWeekMapWrapper.setWorkingWeekFreeTimeMap(workingWeekFreeTimeMap);
		return workingWeekMapWrapper;
	}
	
}
