package pl.dentistoffice.controller;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Controller
@SessionAttributes({"doctor", "patient", "dayStart"})
@PropertySource(value="classpath:/messages.properties")
public class VisitController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DentalTreatmentService treatmentService;
	
	private int dayStart = 0;
	private int dayEnd = 0;

	@RequestMapping(path = "/visit/patient/selectDoctor")
	public String visitSelectDoctorByPatient(Model model) {
		List<Doctor> allDoctorsList = userService.getAllDoctors();
		model.addAttribute("allDoctorsList", allDoctorsList);
		return "/visit/patient/selectDoctor";
	}	
	
	@RequestMapping(path = "/visit/patient/toReserve", method = RequestMethod.POST)
	public String visitToReserveByPatient(@RequestParam(name = "doctorId", required = false) String idDoctor, 
										@RequestParam(name = "weekResultDriver", required = false) String weekResultDriver,
										@SessionAttribute(name = "doctor", required = false) Doctor doctor,
										@SessionAttribute(name = "dayStart", required = false) Integer dayStartFromSession,
										Model model) {
				
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList(); 
		model.addAttribute("treatments", treatments);
		
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = null;
		if (weekResultDriver == null) {
			doctor = userService.getDoctor(Integer.valueOf(idDoctor));
			model.addAttribute("doctor", doctor);
			workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor, 0, 7);
			model.addAttribute("dayStart", 0);
			model.addAttribute("disableLeftArrow", "YES");
		} else if (weekResultDriver != null) {
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
		return "/visit/patient/toReserve";
	}
	
	@RequestMapping(path = "/visit/patient/reservation", method = RequestMethod.POST)
	public String visitReservationByPatient(@SessionAttribute("doctor") Doctor doctor, 
											@RequestParam(name = "dateTime", required = false) String [] dateTime, 
											@RequestParam("treatment1") String treatment1Id,
											@RequestParam("treatment2") String treatment2Id,
											@RequestParam("treatment3") String treatment3Id,
											Model model) throws Exception {
//		Only one query to database
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList();
		List<DentalTreatment> selectedTreatments = new ArrayList<>();
		String [] treatmentId = {treatment1Id, treatment2Id, treatment3Id};
		for (int i = 0; i < treatmentId.length; i++) {
			for (int j = 0; j < treatments.size(); j++) {
				if (Integer.valueOf(treatmentId[i]) == treatments.get(j).getId()) {
					selectedTreatments.add(treatments.get(j));
					break;
				}
			}
		}
		
		if (dateTime != null && dateTime.length == 1) {
			visitService.addNewVisitByPatient(doctor, dateTime, selectedTreatments);
			model.addAttribute("success", env.getProperty("successAddVisit"));
			return "forward:/success";
		} else {
			throw new Exception("Nie zaznaczono pola wyboru dnia wizyty!");
		}		
	}

	@RequestMapping(path = "/visit/assistant/searchPatient")
	public String visitSearchPatientByAssistant() {	
		return "/visit/assistant/searchPatient";
	}

	@RequestMapping(path = "/visit/assistant/searchResult", method = RequestMethod.POST)
	public String visitSearchPatientByAssistant(@RequestParam(name = "patientData") String patientData, RedirectAttributes redirectAttributes) {
		if(patientData.length() > 20) {
			String substringPatientData = patientData.substring(0, 20);
			List<Patient> searchedPatientList = userService.searchPatient(substringPatientData);			
			redirectAttributes.addFlashAttribute("searchedPatientList", searchedPatientList);
		} else {
			List<Patient> searchedPatientList = userService.searchPatient(patientData);			
			redirectAttributes.addFlashAttribute("searchedPatientList", searchedPatientList);
		}
		return "redirect:/visit/assistant/selectPatient";
	}
	
	@RequestMapping(path = "/visit/assistant/selectPatient")
	public String visitSelectPatientByAssistant() {		
		return "/visit/assistant/selectPatient";
	}

	@RequestMapping(path = "/visit/assistant/selectPatient", method = RequestMethod.POST)
	public String visitSelectPatientByAssistant(@RequestParam(name="patientId", required = false) String idPatient, Model model) {
		Patient patient = userService.getPatient(Integer.valueOf(idPatient));
		model.addAttribute("patient", patient);
		return "redirect:/visit/assistant/selectDoctor";			
	}
	
	@RequestMapping(path = "/visit/assistant/selectDoctor")
	public String visitSelectDoctorByAssistant(@SessionAttribute("patient") Patient patient, Model model) {
		model.addAttribute("patient", patient);
		List<Doctor> allDoctorsList = userService.getAllDoctors();
		model.addAttribute("allDoctorsList", allDoctorsList);
		return "/visit/assistant/selectDoctor";
	}
	
	@RequestMapping(path = "/visit/assistant/toReserve", method = RequestMethod.POST)
	public String visitToReserveByAssistant(@RequestParam(name = "doctorId", required = false) String idDoctor, 
											@RequestParam(name = "weekResultDriver", required = false) String weekResultDriver,
											@SessionAttribute("patient") Patient patient,
											@SessionAttribute(name = "doctor", required = false) Doctor doctor,
											@SessionAttribute(name = "dayStart", required = false) Integer dayStartFromSession,
											Model model) {

		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList(); 
		model.addAttribute("treatments", treatments);
		
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = null;
		if (weekResultDriver == null) {
			doctor = userService.getDoctor(Integer.valueOf(idDoctor));
			model.addAttribute("doctor", doctor);
			model.addAttribute("patient", patient);
			workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor, 0, 7);
			model.addAttribute("dayStart", 0);
			model.addAttribute("disableLeftArrow", "YES");
		} else if (weekResultDriver != null) {
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
		return "/visit/assistant/toReserve";
	}
		
	
	@RequestMapping(path = "/visit/assistant/reservation", method = RequestMethod.POST)
	public String visitReservationByAssistant(@SessionAttribute("doctor") Doctor doctor,
											@SessionAttribute("patient") Patient patient,
											@RequestParam(name = "dateTime", required = false) String [] dateTime, 
											@RequestParam("treatment1") String treatment1Id,
											@RequestParam("treatment2") String treatment2Id,
											@RequestParam("treatment3") String treatment3Id,
											Model model) throws Exception {
		
//		Only one query to database
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList();
		List<DentalTreatment> selectedTreatments = new ArrayList<>();
		String [] treatmentId = {treatment1Id, treatment2Id, treatment3Id};
		for(int i=0; i<treatmentId.length; i++) {
			for(int j=0; j<treatments.size(); j++) {
				if(Integer.valueOf(treatmentId[i]) == treatments.get(j).getId()) {					
					selectedTreatments.add(treatments.get(j));
					break;
				}
			}
		}
				
		if (dateTime != null && dateTime.length == 1) {
			visitService.addNewVisitByAssistant(patient, doctor, dateTime, selectedTreatments);
			model.addAttribute("success", env.getProperty("successAddVisit"));
			return "forward:/success";
		} else {
			throw new Exception("Nie zaznaczono pola wyboru dnia wizyty!");
		}
	}
}
