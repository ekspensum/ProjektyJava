package pl.dentistoffice.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;
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
	private VisitAndStatusListWrapper visitAndStatusListWrapper;
	@Autowired
	private AuthRestController authRestController;
	
	public VisitRestController() {
	}

	public VisitRestController(VisitService visitService, UserService userService, WorkingWeekMapWrapper workingWeekMapWrapper, 
											VisitAndStatusListWrapper visitAndStatusListWrapper, AuthRestController authRestController) {
		
		this.visitService = visitService;
		this.userService = userService;
		this.workingWeekMapWrapper = workingWeekMapWrapper;
		this.visitAndStatusListWrapper = visitAndStatusListWrapper;
		this.authRestController = authRestController;
	}

	//	for aspect
	public AuthRestController getAuthRestController() {
		return authRestController;
	}
	
	@PostMapping(path = "/visitStatusList")
	public VisitAndStatusListWrapper getVisitsAndStatusListForPatient(@RequestParam("patientId") String patientId, HttpServletResponse response) {
		try {
			List<VisitStatus> visitStatusList = visitService.getVisitStatusList();
			visitAndStatusListWrapper.setVisitStatusList(visitStatusList);
			Patient patient = userService.getPatient(Integer.valueOf(patientId));
			List<Visit> visitsList = visitService.getVisitsByPatient(patient);
			visitAndStatusListWrapper.setVisitsList(visitsList);
			return visitAndStatusListWrapper;
		} catch (Exception e) {
			e.printStackTrace();
			response.addHeader(HttpHeaders.WARNING, e.getMessage());
		}
		return null;
	}
	
	@PostMapping(path = "/workingWeekMap")
	public WorkingWeekMapWrapper getWorkingWeekFreeTimeMap(@RequestParam("doctorId") String doctorId, 
																											@RequestParam("dayStart") String dayStart, 
																											@RequestParam("dayEnd") String dayEnd,
																											HttpServletResponse response) {
		
		try {
			Doctor doctor = userService.getDoctor(Integer.valueOf(doctorId));
			Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor, Integer.valueOf(dayStart), Integer.valueOf(dayEnd));
			workingWeekMapWrapper.setWorkingWeekFreeTimeMap(workingWeekFreeTimeMap);
			return workingWeekMapWrapper;
		} catch (Exception e) {
			e.printStackTrace();
			response.addHeader(HttpHeaders.WARNING, e.getMessage());
		}
		return null;
	}
	
	@PostMapping(path = "/newVisit")
	public boolean addNewVisitByMobilePatient(@RequestParam("doctorId") int doctorId, 
																			@RequestParam("patientId") int patientId, 
																			@RequestParam("dateTime") String dateTime, 
																			@RequestParam("treatment1Id") String treatment1Id,
																			@RequestParam("treatment2Id") String treatment2Id,
																			@RequestParam("treatment3Id") String treatment3Id,
																			HttpServletResponse response) {
		
		List<DentalTreatment> treatments = new ArrayList<>();
		treatments.add(new DentalTreatment(Integer.valueOf(treatment1Id)));
		treatments.add(new DentalTreatment(Integer.valueOf(treatment2Id)));
		treatments.add(new DentalTreatment(Integer.valueOf(treatment3Id)));
		try {
			visitService.addNewVisitByMobilePatient(doctorId, patientId, dateTime, treatments);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			response.addHeader(HttpHeaders.WARNING, e.getMessage());
		}
		return false;
	}
	
	@PostMapping(path = "/deleteVisit")
	public boolean deleteVisit(@RequestParam("visitId") String visitId,	HttpServletResponse response) {
		try {
			Visit visit = visitService.getVisit(Integer.valueOf(visitId));
			visitService.cancelVisit(visit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			response.addHeader(HttpHeaders.WARNING, e.getMessage());
		}
		return false;
	}
}
