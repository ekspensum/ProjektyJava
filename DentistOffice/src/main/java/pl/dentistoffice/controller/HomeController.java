package pl.dentistoffice.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;


@Controller
@SessionAttributes({"doctor", "dayStart"})
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DentalTreatmentService dentalTreatmentService;
	
	@Autowired
	private VisitService visitService;
	
	private int dayStart = 0;
	private int dayEnd = 0;

	@RequestMapping(path="/")
	public String home() {		
		return "home";
	}
	
	@RequestMapping(path="/doctors")
	public String doctors(Model model) {
		List<Doctor> allDoctors = userService.getAllDoctors();
		model.addAttribute("allDoctors", allDoctors);
		return "doctors";
	}
	
	@RequestMapping(path="/services")
	public String services(Model model) {
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);		
		return "services";
	}
	@RequestMapping(path="/services", method = RequestMethod.POST)
	public String services(@RequestParam("categoryId") String categoryId, Model model) {
		TreatmentCategory selectedTreatmentCategory = dentalTreatmentService.getTreatmentCategory(Integer.valueOf(categoryId));
		model.addAttribute("selectedTreatmentCategory", selectedTreatmentCategory);	
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		model.addAttribute("treatmentCategoriesList", treatmentCategoriesList);		
		return "services";
	}	
	
	@RequestMapping(path="/agenda")
	public String agenda(Model model) {
		List<Doctor> allDoctors = userService.getAllDoctors();
		model.addAttribute("allDoctors", allDoctors);		
		return "agenda";
	}
	@RequestMapping(path="/agenda", method = RequestMethod.POST)
	public String agenda(@RequestParam(name = "doctorId", required = false) String doctorId,
						@SessionAttribute(name = "doctor", required = false) Doctor doctorFromSession,
						@RequestParam(name = "weekResultDriver", required = false) String weekResultDriver,
						@SessionAttribute(name = "dayStart", required = false) Integer dayStartFromSession,
						Model model) {
		
		Doctor doctor;
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = null;
		if (weekResultDriver == null) {
			doctor = userService.getDoctor(Integer.valueOf(doctorId));
			model.addAttribute("doctor", doctor);
			workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor, 0, 7);
			model.addAttribute("dayStart", 0);
			model.addAttribute("disableLeftArrow", "YES");
		} else {
			doctor = doctorFromSession;
			if (weekResultDriver.equals("stepRight")) {
				if (dayStartFromSession < 21) {
					dayStart = dayStartFromSession + 7;
					dayEnd = dayStart + 7;
					model.addAttribute("dayStart", dayStart);
					if(dayStart == 21) {
						model.addAttribute("disableRightArrow", "YES");						
					}
				} else {
					dayStartFromSession = 21;
					dayEnd = dayStartFromSession + 7;
					model.addAttribute("disableRightArrow", "YES");
				}
				workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor, dayStart, dayEnd);

			} else if (weekResultDriver.equals("stepLeft")) {
				if (dayStartFromSession > 0) {
					dayStart = dayStartFromSession - 7;
					dayEnd = dayStart + 7;
					model.addAttribute("dayStart", dayStart);
					if(dayStart == 0) {
						model.addAttribute("disableLeftArrow", "YES");						
					}
				} else {
					dayStartFromSession = 0;
					dayEnd = dayStartFromSession + 7;
					model.addAttribute("disableLeftArrow", "YES");
				}
				workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor, dayStart, dayEnd);
			}
		}
				
		model.addAttribute("workingWeekFreeTimeMap", workingWeekFreeTimeMap);
		model.addAttribute("dayOfWeekPolish", userService.dayOfWeekPolish());
		
		List<Doctor> allDoctors = userService.getAllDoctors();
		model.addAttribute("allDoctors", allDoctors);		
		return "agenda";
	}

}

