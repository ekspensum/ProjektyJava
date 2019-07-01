package pl.dentistoffice.controller;


import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.WorkingWeek;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Controller
public class VisitController {

	@Autowired
	private VisitService visitService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/visit")
	public String reserveVisit(Model model) {
		
		
		Doctor doctor = userService.getDoctor(2);
		WorkingWeek workingWeek = doctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();
		
		
		
		
		Visit visit = new Visit();
		visit.setDoctor(doctor);		
		visitService.addNewVisit(visit);
		return "visit";
	}
}
