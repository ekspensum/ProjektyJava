package pl.dentistoffice.controller;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Controller
@SessionAttributes("doctor")
public class VisitController {

	@Autowired
	private VisitService visitService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DentalTreatmentService treatmentService;

	@RequestMapping(path = "/visitSelectDoctorByPatient")
	public String visitSelectDoctor() {
		return "visitSelectDoctorByPatient";
	}	
	
//	@RequestMapping(path = "/visitSelectDoctorByPatient", method = RequestMethod.POST)
//	public String visitSelectDoctor(@RequestParam("doctorId") String idDoctor, Model model) {
//		return "visitSelectDoctorByPatient";
//	}
	
	@RequestMapping(path = "/visitToReserveByPatient", method = RequestMethod.POST)
	public String visitToReserve(@RequestParam("doctorId") String idDoctor, Model model) {

		Doctor doctor = userService.getDoctor(Integer.valueOf(idDoctor));
		model.addAttribute("doctor", doctor);
		
//		System.out.println("Post1 "+doctor.getFirstName());
				
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList(); 
		model.addAttribute("treatments", treatments);
		
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor);
		
		model.addAttribute("workingWeekFreeTimeMap", workingWeekFreeTimeMap);
		return "visitToReserveByPatient";
	}
	
	@RequestMapping(path = "/visitReservationByPatient", method = RequestMethod.POST)
	public String visitReservationByPatient(@ModelAttribute("doctor") Doctor doctor, @RequestParam("date") String date, @RequestParam("time") String time, @RequestParam("treatment") String [] treatmentId) {
		System.out.println("Post2 "+doctor.getFirstName());
		
//		Only one query to database
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList();
		List<DentalTreatment> selectTreatments = new ArrayList<>();
		for(int i=0; i<treatmentId.length; i++) {
			selectTreatments.add(treatments.get(Integer.valueOf(treatmentId[i])));			
		}
		visitService.addNewVisitByPatient(doctor, date, time, selectTreatments);
		return "visitReservationByPatient";
	}
}
