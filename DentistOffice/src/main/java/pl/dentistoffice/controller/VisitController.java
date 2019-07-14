package pl.dentistoffice.controller;


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
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Controller
@SessionAttributes({"doctor", "patient"})
public class VisitController {

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
	public String visitReservationByPatient(@ModelAttribute("doctor") Doctor doctor, 
											@RequestParam("dateTime") String dateTime, 
											@RequestParam("treatment") String [] treatmentId) {
		
//		Only one query to database
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList();
		List<DentalTreatment> selectTreatments = new ArrayList<>();
		for(int i=0; i<treatmentId.length; i++) {
			for(int j=0; j<treatments.size(); j++) {
				if(Integer.valueOf(treatmentId[i]) == treatments.get(j).getId()) {					
					selectTreatments.add(treatments.get(j));
					break;
				}
			}
		}
		visitService.addNewVisitByPatient(doctor, dateTime, selectTreatments);
		return "/visit/patient/reservation";
	}

	@RequestMapping(path = "/visit/assistant/selectPatient")
	public String visitSelectPatientByAssistant() {
		
		return "/visit/assistant/selectPatient";
	}
	
	@RequestMapping(path = "/visit/assistant/selectDoctor", method = RequestMethod.POST)
	public String visitSelectDoctorByAssistant(@RequestParam("patientId") String idPatient, Model model) {
		Patient patient = userService.getPatient(Integer.valueOf(idPatient));
		model.addAttribute("patient", patient);
		return "/visit/assistant/selectDoctor";
	}
	
	@RequestMapping(path = "/visit/assistant/toReserve", method = RequestMethod.POST)
	public String visitToReserveByAssistant(@RequestParam("doctorId") String idDoctor, Model model) {

		Doctor doctor = userService.getDoctor(Integer.valueOf(idDoctor));
		model.addAttribute("doctor", doctor);
				
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList(); 
		model.addAttribute("treatments", treatments);
		
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor);
		
		model.addAttribute("workingWeekFreeTimeMap", workingWeekFreeTimeMap);
		return "/visit/assistant/toReserve";
	}
	
	@RequestMapping(path = "/visit/assistant/reservation", method = RequestMethod.POST)
	public String visitReservationByAssistant(@ModelAttribute("doctor") Doctor doctor,
											@ModelAttribute("patient") Patient patient,
											@RequestParam("dateTime") String dateTime, 
											@RequestParam("treatment") String [] treatmentId) {
		
//		Only one query to database
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList();
		List<DentalTreatment> selectTreatments = new ArrayList<>();
		for(int i=0; i<treatmentId.length; i++) {
			for(int j=0; j<treatments.size(); j++) {
				if(Integer.valueOf(treatmentId[i]) == treatments.get(j).getId()) {					
					selectTreatments.add(treatments.get(j));
					break;
				}
			}
		}
		visitService.addNewVisitByAssistant(patient, doctor, dateTime, selectTreatments);
		return "/visit/assistant/reservation";
	}
}
