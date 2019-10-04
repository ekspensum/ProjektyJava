package pl.dentistoffice.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Controller
@SessionAttributes({"doctor", "patient", "dayStart", "visit"})
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

	public VisitController() {
	}

	public VisitController(Environment env, VisitService visitService, UserService userService,
			DentalTreatmentService treatmentService, int dayStart, int dayEnd) {
		this.env = env;
		this.visitService = visitService;
		this.userService = userService;
		this.treatmentService = treatmentService;
		this.dayStart = dayStart;
		this.dayEnd = dayEnd;
	}

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
		} else {
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
		
		List<DentalTreatment> selectedTreatments = setDentalTreatmentsList(treatment1Id, treatment2Id, treatment3Id);
		
		if (dateTime != null && dateTime.length == 1) {
			try {
				visitService.addNewVisitByPatient(doctor, dateTime, selectedTreatments);
				model.addAttribute("success", env.getProperty("successAddVisit"));
				return "forward:/message/patient/success";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("exception", env.getProperty("exceptionAddVisit"));
				return "forward:/message/employee/error";				
			}
		} else {
			throw new Exception("Nie zaznaczono pola wyboru dnia wizyty!"); // additional security
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
	public String visitSelectPatientByAssistant(@RequestParam(name="patientId", required = false) String idPatient, RedirectAttributes redirectAttributes) {
		Patient patient = userService.getPatient(Integer.valueOf(idPatient));
		redirectAttributes.addFlashAttribute("patient", patient);
		return "redirect:/visit/assistant/selectDoctor";			
	}
	
	@RequestMapping(path = "/visit/assistant/selectDoctor")
	public String visitSelectDoctorByAssistant(@ModelAttribute("patient") Patient patient, Model model) {
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
		} else {
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
		
		List<DentalTreatment> selectedTreatments = setDentalTreatmentsList(treatment1Id, treatment2Id, treatment3Id);
				
		if (dateTime != null && dateTime.length == 1) {
			try {
				visitService.addNewVisitByAssistant(patient, doctor, dateTime, selectedTreatments);
				model.addAttribute("success", env.getProperty("successAddVisit"));
				return "forward:/message/employee/success";
			} catch (Exception e) {
				e.printStackTrace();
				model.addAttribute("exception", env.getProperty("exceptionAddVisit"));
				return "forward:/message/employee/error";
			}										
		} else {
			throw new Exception("Nie zaznaczono pola wyboru dnia wizyty!"); // additional security
		}
	}
	
	@RequestMapping(path = "/visit/assistant/searchVisitToFinalize")
	public String searchVisitsToFinalizeByAssistant(Model model) {
		LocalDate today = LocalDate.now();
		model.addAttribute("dateFrom", today.minusDays(3));
		model.addAttribute("dateTo", today);
		List<Visit> visitsToFinalize = visitService.getVisitsToFinalizeOrRemove(today.atStartOfDay().minusDays(3), LocalDateTime.now());
		model.addAttribute("visitsToFinalize", visitsToFinalize);
		return "/visit/assistant/searchVisitToFinalize";
	}

	@RequestMapping(path = "/visit/assistant/searchVisitToFinalize", method = RequestMethod.POST)
	public String searchVisitsToFinalizeByAssistant(@RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, Model model) {
		LocalDateTime dateTimeFrom = LocalDateTime.parse(dateFrom + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		LocalDateTime dateTimeTo = LocalDateTime.parse(dateTo + " "+LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		List<Visit> visitsToFinalize = visitService.getVisitsToFinalizeOrRemove(dateTimeFrom, dateTimeTo);
		model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);
		model.addAttribute("visitsToFinalize", visitsToFinalize);
		return "/visit/assistant/searchVisitToFinalize";
	}
	
	@RequestMapping(path = "/visit/assistant/visitToFinalize", method = RequestMethod.POST)
	public String setVisitsToFinalizeByAssistant(@RequestParam("visitId") String visitId, Model model) {
		
		List<DentalTreatment> dentalTreatmentsList = treatmentService.getDentalTreatmentsList();
		model.addAttribute("dentalTreatmentsList", dentalTreatmentsList);

		Visit visit = visitService.getVisit(Integer.valueOf(visitId));
		model.addAttribute("visit", visit);
		
		return "/visit/assistant/visitToFinalize";
	}	
	
	@RequestMapping(path = "/visit/assistant/finalizedVisit", method = RequestMethod.POST)
	public String saveFinalizedVisitByAssistant(@SessionAttribute("visit") Visit visit,
												@RequestParam("treatmentCommentVisit1") String treatmentCommentVisit1,
												@RequestParam("treatmentCommentVisit2") String treatmentCommentVisit2,
												@RequestParam("treatmentCommentVisit3") String treatmentCommentVisit3,
												@RequestParam("treatment1") String treatment1Id,
												@RequestParam("treatment2") String treatment2Id,
												@RequestParam("treatment3") String treatment3Id,
												Model model) throws Exception {
			
		String [] treatmentCommentVisit = {treatmentCommentVisit1, treatmentCommentVisit2, treatmentCommentVisit3};
		List<DentalTreatment> selectedTreatments = setDentalTreatmentsList(treatment1Id, treatment2Id, treatment3Id);
		if(validComments(treatmentCommentVisit, selectedTreatments, model)) {
			if(visitService.setFinalzedVisit(visit, selectedTreatments, treatmentCommentVisit)) {
				model.addAttribute("success", env.getProperty("successFinalizedVisit"));
				return "forward:/message/employee/success";							
			} else {
				model.addAttribute("exception", env.getProperty("exceptionFinalizedVisit"));
				return "forward:/message/employee/error";
			}
		} else {
			List<DentalTreatment> dentalTreatmentsList = treatmentService.getDentalTreatmentsList();
			model.addAttribute("dentalTreatmentsList", dentalTreatmentsList);
			return "/visit/assistant/finalizedVisit";
		}
	}
	
	@RequestMapping(path = "/visit/assistant/searchVisitToRemove")
	public String searchVisitsToRemoveByAssistant(Model model) {
		LocalDate today = LocalDate.now();
		model.addAttribute("dateFrom", today);
		model.addAttribute("dateTo", today.plusDays(7));
		List<Visit> visitsToRemove = visitService.getVisitsToFinalizeOrRemove(today.atStartOfDay(), today.atStartOfDay().plusDays(7));
		model.addAttribute("visitsToRemove", visitsToRemove);
		return "/visit/assistant/searchVisitToRemove";
	}

	@RequestMapping(path = "/visit/assistant/searchVisitToRemove", method = RequestMethod.POST)
	public String searchVisitsToRemoveByAssistant(@RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, Model model) {
		LocalDateTime dateTimeFrom = LocalDateTime.parse(dateFrom + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		LocalDateTime dateTimeTo = LocalDateTime.parse(dateTo + " "+LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		List<Visit> visitsToRemove = visitService.getVisitsToFinalizeOrRemove(dateTimeFrom, dateTimeTo);
		model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);
		model.addAttribute("visitsToRemove", visitsToRemove);
		return "/visit/assistant/searchVisitToRemove";
	}
	
	@RequestMapping(path = "/visit/assistant/removeVisit", method = RequestMethod.POST)
	public String removeVisitByAssistant(@RequestParam("visitId") String visitId, Model model) {
		try {
			Visit visit = visitService.getVisit(Integer.valueOf(visitId));
			visitService.cancelVisit(visit);
			model.addAttribute("success", env.getProperty("successCanceledVisit"));
			return "forward:/message/employee/success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", env.getProperty("exceptionCanceledVisit"));
			return "forward:/message/employee/error";
		}		
	}

	@RequestMapping(path = "/visit/patient/removeVisit", method = RequestMethod.POST)
	public String removeVisitByPatient(@RequestParam("visitId") String visitId, Model model) {
		try {
			Visit visit = visitService.getVisit(Integer.valueOf(visitId));
			visitService.cancelVisit(visit);
			model.addAttribute("success", env.getProperty("successCanceledVisit"));
			return "forward:/message/patient/success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("exception", env.getProperty("exceptionCanceledVisit"));
			return "forward:/message/patient/error";
		}		
	}
	
	@RequestMapping(path = "/visit/doctor/searchVisitToFinalize")
	public String searchVisitsToFinalizeByDoctor(Model model) {
		LocalDate today = LocalDate.now();
		model.addAttribute("dateFrom", today.minusDays(7));
		model.addAttribute("dateTo", today);
		Doctor loggedDoctor = userService.getLoggedDoctor();
		List<Visit> visitsToFinalize = visitService.getVisitsToFinalize(today.atStartOfDay().minusDays(3), LocalDateTime.now(), loggedDoctor);
		model.addAttribute("visitsToFinalize", visitsToFinalize);
		model.addAttribute("doctor", loggedDoctor);
		return "/visit/doctor/searchVisitToFinalize";
	}

	@RequestMapping(path = "/visit/doctor/searchVisitToFinalize", method = RequestMethod.POST)
	public String searchVisitsToFinalizeByDoctor(@SessionAttribute("doctor") Doctor doctor, @RequestParam("dateFrom") String dateFrom, @RequestParam("dateTo") String dateTo, Model model) {
		LocalDateTime dateTimeFrom = LocalDateTime.parse(dateFrom + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		LocalDateTime dateTimeTo = LocalDateTime.parse(dateTo + " "+LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		List<Visit> visitsToFinalize = visitService.getVisitsToFinalize(dateTimeFrom, dateTimeTo, doctor);
		model.addAttribute("dateFrom", dateFrom);
		model.addAttribute("dateTo", dateTo);
		model.addAttribute("visitsToFinalize", visitsToFinalize);
		return "/visit/doctor/searchVisitToFinalize";
	}
	
	@RequestMapping(path = "/visit/doctor/visitToFinalize", method = RequestMethod.POST)
	public String setVisitsToFinalizeByDoctor(@RequestParam("visitId") String visitId, Model model) {
		
		List<DentalTreatment> dentalTreatmentsList = treatmentService.getDentalTreatmentsList();
		model.addAttribute("dentalTreatmentsList", dentalTreatmentsList);

		Visit visit = visitService.getVisit(Integer.valueOf(visitId));
		model.addAttribute("visit", visit);
		
		return "/visit/doctor/visitToFinalize";
	}	
	
	@RequestMapping(path = "/visit/doctor/finalizedVisit", method = RequestMethod.POST)
	public String saveFinalizedVisitByDoctor(@SessionAttribute("doctor") Doctor doctor,
											@SessionAttribute("visit") Visit visit,
											@RequestParam("treatmentCommentVisit1") String treatmentCommentVisit1,
											@RequestParam("treatmentCommentVisit2") String treatmentCommentVisit2,
											@RequestParam("treatmentCommentVisit3") String treatmentCommentVisit3,
											@RequestParam("treatment1") String treatment1Id,
											@RequestParam("treatment2") String treatment2Id,
											@RequestParam("treatment3") String treatment3Id,
											Model model) throws Exception {
			
		String [] treatmentCommentVisit = {treatmentCommentVisit1, treatmentCommentVisit2, treatmentCommentVisit3};
		List<DentalTreatment> selectedTreatments = setDentalTreatmentsList(treatment1Id, treatment2Id, treatment3Id);
			
		if(validComments(treatmentCommentVisit, selectedTreatments, model)) {
			if(visitService.setFinalzedVisit(visit, selectedTreatments, treatmentCommentVisit)) {
				model.addAttribute("success", env.getProperty("successFinalizedVisit"));
				return "forward:/message/employee/success";							
			} else {
				model.addAttribute("exception", env.getProperty("exceptionFinalizedVisit"));
				return "forward:/message/employee/error";
			}
		} else {
			List<DentalTreatment> dentalTreatmentsList = treatmentService.getDentalTreatmentsList();
			model.addAttribute("dentalTreatmentsList", dentalTreatmentsList);
			return "/visit/doctor/finalizedVisit";
		}
	}	
	
//	FOR SELF BROWSE AND DELETE VISITS PATIENT
	@RequestMapping(path = "/visit/patient/myVisits")
	public String showMyVisitsPatient(Model model) {
		VisitStatus defaultVisitStatus = visitService.getVisitStatus(2);
		Patient loggedPatient = userService.getLoggedPatient();
		List<Visit> visitsByPatientAndStatus = visitService.getVisitsByPatientAndStatus(loggedPatient, defaultVisitStatus);
		List<VisitStatus> visitStatusList = visitService.getVisitStatusList();
		model.addAttribute("visitStatusList", visitStatusList);
		model.addAttribute("defaultVisitStatus", defaultVisitStatus);
		model.addAttribute("visitsByPatientAndStatus", visitsByPatientAndStatus);
		model.addAttribute("patient", loggedPatient);
		return "/visit/patient/myVisits";
	}
	
	@RequestMapping(path = "/visit/patient/myVisits", method = RequestMethod.POST)
	public String showMyVisitsPatient(@SessionAttribute(name = "patient") Patient patient, @RequestParam("statusId") String statusId, Model model) {
		VisitStatus actualVisitStatus = visitService.getVisitStatus(Integer.valueOf(statusId));
		List<Visit> visitsByPatientAndStatus = visitService.getVisitsByPatientAndStatus(patient, actualVisitStatus);
		List<VisitStatus> visitStatusList = visitService.getVisitStatusList();
		model.addAttribute("visitStatusList", visitStatusList);
		model.addAttribute("actualVisitStatus", actualVisitStatus);
		model.addAttribute("visitsByPatientAndStatus", visitsByPatientAndStatus);
		return "/visit/patient/myVisits";
	}	
	
	@RequestMapping(path = "/visit/patient/myVisitsPdf", method = RequestMethod.POST)
	public ModelAndView showMyVisitsPdfPatient(@SessionAttribute(name = "patient") Patient patient, 
											   @RequestParam("statusId") String statusId, 
											   ModelAndView modelAndView) {
		
		VisitStatus actualVisitStatus = visitService.getVisitStatus(Integer.valueOf(statusId));
		List<Visit> visitsByPatientAndStatus = visitService.getVisitsByPatientAndStatus(patient, actualVisitStatus);
		modelAndView.addObject("patientVisits", visitsByPatientAndStatus);
		modelAndView.setViewName("patientVisits");
		return modelAndView;
	}
		
	@RequestMapping(path = "/visit/doctor/showMyVisits")
	public String showMyVisitsDoctor(Model model) {
		LocalDate today = LocalDate.now();
		model.addAttribute("dateFrom", today);
		Doctor loggedDoctor = userService.getLoggedDoctor();
		model.addAttribute("doctor", loggedDoctor);
		List<Visit> visitsToShow = visitService.getVisitsToShow(LocalDateTime.now(), loggedDoctor);
		model.addAttribute("visitsToShow", visitsToShow);
		return "/visit/doctor/showMyVisits";
	}
	
	@RequestMapping(path = "/visit/doctor/showMyVisits", method = RequestMethod.POST)
	public String showMyVisitsDoctor(@SessionAttribute("doctor") Doctor loggedDoctor,
							   		 @RequestParam("dateFrom") String dateFrom, 
							   		 Model model) {
		
		LocalDate selectedDate = LocalDate.parse(dateFrom);
		LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, LocalTime.now());
		model.addAttribute("dateFrom", selectedDate);
		List<Visit> visitsToShow = visitService.getVisitsToShow(selectedDateTime, loggedDoctor);
		model.addAttribute("visitsToShow", visitsToShow);
		return "/visit/doctor/showMyVisits";
	}

	@RequestMapping(path = "/visit/doctor/myVisitsPdf", method = RequestMethod.POST)
	public ModelAndView showMyVisitsPdfDoctor(@SessionAttribute(name = "doctor") Doctor loggedDoctor, 
											  @RequestParam("dateFrom") String dateFrom, 
											  ModelAndView modelAndView) {
		
		LocalDate selectedDate = LocalDate.parse(dateFrom);
		LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, LocalTime.now());
		List<Visit> doctorVisits = visitService.getVisitsToShow(selectedDateTime, loggedDoctor);
		modelAndView.addObject("doctorVisits", doctorVisits);
		modelAndView.setViewName("doctorVisits");
		return modelAndView;
	}	
	
	
//PRIVATE METHODS
	private boolean validComments(String [] treatmentCommentVisit, List<DentalTreatment> selectedTreatments, Model model) {
		String forbidenChar = "|'\":%^#~}{\\]\\[;=<>`";
		int treatmentCount = 0;
		for (int i = 0; i < treatmentCommentVisit.length; i++) {
			if(!treatmentCommentVisit[i].matches("^[^"+forbidenChar+"]{0,250}$")) {
				model.addAttribute("msgError", "Komentarz do zabiegu nr "+ (i+1) +" zawiera niedozwolone znaki ("+forbidenChar+ ") lub zbyt dużą ilość znaków (max 250)!");
				return false;
			}
			if(selectedTreatments.get(i).getId() == 1 && !treatmentCommentVisit[i].equals("")) {
				model.addAttribute("msgError", "Zawarto komentarz do zabiegu nr "+ (i+1) +" lecz nie wybrano nazwy zabiegu!");
				return false;
			}
			if(selectedTreatments.get(i).getId() == 1) {
				treatmentCount++;
			}
		}
		if(treatmentCount == 3) {
			model.addAttribute("msgError", "Należy wybrać conajmniej jeden rodzaj zabiegu!");
			return false;
		}
		return true;
	}
	
	private List<DentalTreatment> setDentalTreatmentsList(String treatment1Id, String treatment2Id, String treatment3Id){
//		Only one query to database
		List<DentalTreatment> treatments = treatmentService.getDentalTreatmentsList();
		List<DentalTreatment> selectedTreatments = new ArrayList<>();
		String[] treatmentId = { treatment1Id, treatment2Id, treatment3Id };
		for (int i = 0; i < treatmentId.length; i++) {
			for (int j = 0; j < treatments.size(); j++) {
				if (Integer.valueOf(treatmentId[i]) == treatments.get(j).getId()) {
					selectedTreatments.add(treatments.get(j));
					break;
				}
			}
		}
		return selectedTreatments;
	}
	
}
