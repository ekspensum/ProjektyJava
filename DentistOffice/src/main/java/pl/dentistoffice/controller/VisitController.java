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
@SessionAttributes({"doctor", "patient"})
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

	@RequestMapping(path = "/visit/patient/selectDoctor")
	public String visitSelectDoctorByPatient(Model model) {
		List<Doctor> allDoctorsList = userService.getAllDoctors();
		model.addAttribute("allDoctorsList", allDoctorsList);
		return "/visit/patient/selectDoctor";
	}	
	
	@RequestMapping(path = "/visit/patient/toReserve", method = RequestMethod.POST)
	public String visitToReserveByPatient(@RequestParam("doctorId") String idDoctor, Model model) {

		Doctor doctor = userService.getDoctor(Integer.valueOf(idDoctor));
		model.addAttribute("doctor", doctor);
				
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList(); 
		model.addAttribute("treatments", treatments);
		
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor);
		
		model.addAttribute("workingWeekFreeTimeMap", workingWeekFreeTimeMap);
		return "/visit/patient/toReserve";
	}
	
	@RequestMapping(path = "/visit/patient/reservation", method = RequestMethod.POST)
	public String visitReservationByPatient(@SessionAttribute("doctor") Doctor doctor, 
											@RequestParam(name = "dateTime", required = false) String [] dateTime, 
											@RequestParam("treatment1") String treatment1Id,
											@RequestParam("treatment2") String treatment2Id,
											@RequestParam("treatment3") String treatment3Id,
											Model model) {
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
			model.addAttribute("msg", env.getProperty("oneTermLimit"));
			model.addAttribute("treatments", treatments);
			Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor);			
			model.addAttribute("workingWeekFreeTimeMap", workingWeekFreeTimeMap);
		}		
		return "/visit/patient/reservation";
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
	public String visitToReserveByAssistant(@RequestParam("doctorId") String idDoctor, 
											@SessionAttribute("patient") Patient patient, 
											Model model) {

		Doctor doctor = userService.getDoctor(Integer.valueOf(idDoctor));
		model.addAttribute("doctor", doctor);
		model.addAttribute("patient", patient);
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList(); 
		model.addAttribute("treatments", treatments);
		
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor);
		
		model.addAttribute("workingWeekFreeTimeMap", workingWeekFreeTimeMap);
		return "/visit/assistant/toReserve";
	}
	
	@RequestMapping(path = "/visit/assistant/reservation", method = RequestMethod.POST)
	public String visitReservationByAssistant(@SessionAttribute("doctor") Doctor doctor,
											@SessionAttribute("patient") Patient patient,
											@RequestParam(name = "dateTime", required = false) String [] dateTime, 
											@RequestParam("treatment1") String treatment1Id,
											@RequestParam("treatment2") String treatment2Id,
											@RequestParam("treatment3") String treatment3Id,
											Model model) {
		
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
			model.addAttribute("msg", env.getProperty("oneTermLimit"));
			model.addAttribute("treatments", treatments);
			Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor);			
			model.addAttribute("workingWeekFreeTimeMap", workingWeekFreeTimeMap);
		}
		
		return "/visit/assistant/reservation";
	}
}
