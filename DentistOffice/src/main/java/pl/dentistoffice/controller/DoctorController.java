package pl.dentistoffice.controller;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.entity.WorkingWeek;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Controller
@SessionAttributes({"doctor", "image", "patient", "visitsByPatientAndStatus", "editUserId"})
public class DoctorController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VisitService visitService;
	
	@Autowired
	private HibernateSearchService searchService;
	
	@RequestMapping(path = "/panels/doctorPanel")
	public String doctorPanel(Model model) {

		return "doctorPanel";
	}
	
	@RequestMapping(path = "/users/doctor/admin/register")
	public String registrationDoctor(Model model) {
		model.addAttribute("rolesList", userService.getAllRoles());
		model.addAttribute("doctor", new Doctor());
		model.addAttribute("templateWorkingWeekMap", userService.getTemplateWorkingWeekMap());
		model.addAttribute("dayOfWeekPolish", userService.dayOfWeekPolish());
		return "/users/doctor/admin/register";
	}
	
	@RequestMapping(path = "/users/doctor/admin/register", method = RequestMethod.POST)
	public String registerDoctor(
								@Valid Doctor doctor,
								BindingResult result,
								Model model,
								@RequestParam("photo") MultipartFile photo,
								String [] mondayTime, String [] mondayTimeBool,
								String [] tuesdayTime, String [] tuesdayTimeBool,
								String [] wednesdayTime, String [] wednesdayTimeBool,
								String [] thursdayTime, String [] thursdayTimeBool,
								String [] fridayTime, String [] fridayTimeBool,
								String [] saturdayTime, String [] saturdayTimeBool,
								String [] sundayTime, String [] sundayTimeBool
								) throws IOException {
		
		boolean dinstinctLogin = userService.checkDinstinctLoginWithRegisterUser(doctor.getUser().getUsername());
		WorkingWeek workingWeek = new WorkingWeek();

		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = new LinkedHashMap<>();
		setWorkingWeekMap(workingWeekMap, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime,
				thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool);

		workingWeek.setWorkingWeekMap(workingWeekMap);
		doctor.setWorkingWeek(workingWeek);
		doctor.setPhoto(photo.getBytes());

		if (!result.hasErrors() && doctor.getUser().getRoles().get(0).getId() != doctor.getUser().getRoles().get(1).getId() && dinstinctLogin) {
			userService.addNewDoctor(doctor);
			model.addAttribute("success", env.getProperty("successRegisterDoctor"));
			return "forward:/message/employee/success";
		} else {
			if (doctor.getUser().getRoles().get(0).getId() == doctor.getUser().getRoles().get(1).getId()) {
				model.addAttribute("roleError", env.getProperty("roleError"));
			}
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			model.addAttribute("rolesList", userService.getAllRoles());
			model.addAttribute("templateWorkingWeekMap", workingWeekMap);
			model.addAttribute("dayOfWeekPolish", userService.dayOfWeekPolish());
			return "/users/doctor/admin/register";
		}
	}
	
	@RequestMapping(path = "/users/doctor/admin/selectToEdit")
	public String selectDoctorToEdit(Model model) {		
		List<Doctor> allDoctorsList = userService.getAllDoctors();
		model.addAttribute("allDoctorsList", allDoctorsList);
		return "/users/doctor/admin/selectToEdit";
	}
	
	@RequestMapping(path = "/users/doctor/admin/selectToEdit", method = RequestMethod.POST)
	public String selectDoctorToEdit(@RequestParam("doctorId") String doctorId, RedirectAttributes redirectAttributes) {
		Doctor doctor = userService.getDoctor(Integer.valueOf(doctorId));
		redirectAttributes.addFlashAttribute("doctor", doctor);
		redirectAttributes.addFlashAttribute("editUserId", doctor.getUser().getId());
		return "redirect:/users/doctor/admin/edit";
	}
	
	@RequestMapping(path = "/users/doctor/admin/edit")
	public String editDoctor(@ModelAttribute("doctor") Doctor doctor, Model model) {
		model.addAttribute("doctor", doctor);
		model.addAttribute("image", doctor.getPhoto());
		model.addAttribute("rolesList", userService.getAllRoles());				
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = doctor.getWorkingWeek().getWorkingWeekMap();
		model.addAttribute("workingWeekMap", workingWeekMap);
		model.addAttribute("dayOfWeekPolish", userService.dayOfWeekPolish());
		return "/users/doctor/admin/edit";
	}
	
	@RequestMapping(path = "/users/doctor/admin/edit", method = RequestMethod.POST)
	public String editDataDoctor(
								@Valid Doctor doctor,
								BindingResult result, 
								Model model,
								@SessionAttribute("editUserId") int editUserId,
								@RequestParam("photo") MultipartFile photo,
								@SessionAttribute(name = "image") byte [] image,
								String [] mondayTime, String [] mondayTimeBool,
								String [] tuesdayTime, String [] tuesdayTimeBool,
								String [] wednesdayTime, String [] wednesdayTimeBool,
								String [] thursdayTime, String [] thursdayTimeBool,
								String [] fridayTime, String [] fridayTimeBool,
								String [] saturdayTime, String [] saturdayTimeBool,
								String [] sundayTime, String [] sundayTimeBool
								) throws IOException {

		boolean dinstinctLogin = userService.checkDinstinctLoginWithEditUser(doctor.getUser().getUsername(), editUserId);
		WorkingWeek workingWeek = doctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();

		setWorkingWeekMap(workingWeekMap, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime,
				wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool);

		workingWeek.setWorkingWeekMap(workingWeekMap);
		doctor.setWorkingWeek(workingWeek);
		if(photo.getBytes().length == 0) {
			doctor.setPhoto(image);			
		}
		if(!result.hasErrors() && doctor.getUser().getRoles().get(0).getId() != doctor.getUser().getRoles().get(1).getId() && dinstinctLogin) {
			userService.editDoctor(doctor);
			model.addAttribute("success", env.getProperty("successUpdateDoctor"));
			return "forward:/message/employee/success";
		} else {
			if(doctor.getUser().getRoles().get(0).getId() == doctor.getUser().getRoles().get(1).getId()) {
				model.addAttribute("roleError", env.getProperty("roleError"));
			}
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			model.addAttribute("rolesList", userService.getAllRoles());
			model.addAttribute("workingWeekMap", workingWeekMap);
			model.addAttribute("dayOfWeekPolish", userService.dayOfWeekPolish());
			return "/users/doctor/admin/edit";
		}
	}
	
	@RequestMapping(path = "/users/doctor/edit")
	public String selfEditDoctor(Model model) {
		Doctor doctor = userService.getLoggedDoctor();
		model.addAttribute("doctor", doctor);
		model.addAttribute("editUserId", doctor.getUser().getId());
		model.addAttribute("image", doctor.getPhoto());
		model.addAttribute("rolesList", userService.getAllRoles());				
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = doctor.getWorkingWeek().getWorkingWeekMap();
		model.addAttribute("workingWeekMap", workingWeekMap);
		model.addAttribute("dayOfWeekPolish", userService.dayOfWeekPolish());
		return "/users/doctor/edit";
	}
	
	@RequestMapping(path = "/users/doctor/edit", method = RequestMethod.POST)
	public String selfEditDataDoctor(
									@Valid Doctor doctor,
									BindingResult result,
									Model model,
									@SessionAttribute("editUserId") int editUserId,
									@RequestParam("photo") MultipartFile photo,
									@SessionAttribute(name = "image") byte [] image,
									String [] mondayTime, String [] mondayTimeBool,
									String [] tuesdayTime, String [] tuesdayTimeBool,
									String [] wednesdayTime, String [] wednesdayTimeBool,
									String [] thursdayTime, String [] thursdayTimeBool,
									String [] fridayTime, String [] fridayTimeBool,
									String [] saturdayTime, String [] saturdayTimeBool,
									String [] sundayTime, String [] sundayTimeBool
									) throws IOException {

		boolean dinstinctLogin = userService.checkDinstinctLoginWithEditUser(doctor.getUser().getUsername(), editUserId);
		WorkingWeek workingWeek = doctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();

		setWorkingWeekMap(workingWeekMap, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime,
				wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool);

		workingWeek.setWorkingWeekMap(workingWeekMap);
		doctor.setWorkingWeek(workingWeek);
		if(photo.getBytes().length == 0) {
			doctor.setPhoto(image);			
		}
		if(!result.hasErrors() && dinstinctLogin) {
			userService.editDoctor(doctor);
			model.addAttribute("success", env.getProperty("successUpdateDoctor"));
			return "forward:/message/employee/success";
		} else {
			if(!dinstinctLogin) {
				model.addAttribute("distinctLoginError", env.getProperty("distinctLoginError"));
			}
			model.addAttribute("workingWeekMap", workingWeekMap);
			model.addAttribute("dayOfWeekPolish", userService.dayOfWeekPolish());
			return "/users/doctor/edit";
		}
	}
		
	@RequestMapping(path = "/users/doctor/searchPatient")
	public String searchPatientByDoctor() {
		return "/users/doctor/searchPatient";
	}
	
	@RequestMapping(path = "/users/doctor/searchResult", method = RequestMethod.POST)
	public String searchPatientByDoctor(@RequestParam(name = "patientData") String patientData, RedirectAttributes redirectAttributes) {
		if(patientData.length() > 20) {
			String substringPatientData = patientData.substring(0, 20);
			Doctor loggedDoctor = userService.getLoggedDoctor();
			List<Patient> searchedPatientList = searchService.searchPatientNamePeselStreetPhoneByKeywordQueryAndDoctor(substringPatientData, loggedDoctor);
			redirectAttributes.addFlashAttribute("searchedPatientList", searchedPatientList);
		} else {
			Doctor loggedDoctor = userService.getLoggedDoctor();
			List<Patient> searchedPatientList = searchService.searchPatientNamePeselStreetPhoneByKeywordQueryAndDoctor(patientData, loggedDoctor);
			redirectAttributes.addFlashAttribute("searchedPatientList", searchedPatientList);
		}
		return "redirect:/users/doctor/selectPatient";
	}
	
	@RequestMapping(path = "/users/doctor/selectPatient")
	public String selectPatientByDoctor() {
		return "/users/doctor/selectPatient";
	}
	
	@RequestMapping(path = "/users/doctor/selectPatient", method = RequestMethod.POST)
	public String selectPatientToEditByDoctor(@RequestParam("patientId") String patientId, RedirectAttributes redirectAttributes) {		
		Patient patient = userService.getPatient(Integer.valueOf(patientId));
		redirectAttributes.addFlashAttribute("patient", patient);
		return "redirect:/users/doctor/showPatient";
	}
		
	@RequestMapping(path = "/users/doctor/showPatient")
	public String showPatientByDoctor(@ModelAttribute(name = "patient") Patient patient, Model model) {
		VisitStatus defaultVisitStatus = visitService.getVisitStatus(2);
		List<Visit> visitsByPatientAndStatus = visitService.getVisitsByPatientAndStatus(patient, defaultVisitStatus);
		List<VisitStatus> visitStatusList = visitService.getVisitStatusList();
		model.addAttribute("visitStatusList", visitStatusList);
		model.addAttribute("defaultVisitStatus", defaultVisitStatus);
		model.addAttribute("visitsByPatientAndStatus", visitsByPatientAndStatus);
		model.addAttribute("patient", patient);
		return "/users/doctor/showPatient";
	}

	@RequestMapping(path = "/users/doctor/showPatient", method = RequestMethod.POST)
	public String showPatientByDoctor(@ModelAttribute(name = "patient") Patient patient, @RequestParam("statusId") String statusId, Model model) {
		VisitStatus actualVisitStatus = visitService.getVisitStatus(Integer.valueOf(statusId));
		List<Visit> visitsByPatientAndStatus = visitService.getVisitsByPatientAndStatus(patient, actualVisitStatus);
		List<VisitStatus> visitStatusList = visitService.getVisitStatusList();
		model.addAttribute("visitStatusList", visitStatusList);
		model.addAttribute("actualVisitStatus", actualVisitStatus);
		model.addAttribute("visitsByPatientAndStatus", visitsByPatientAndStatus);
		model.addAttribute("patient", patient);
		return "/users/doctor/showPatient";
	}
	
	
	
//	Private methods
	private void setWorkingWeekMap(Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap,
			String [] mondayTime, String [] mondayTimeBool,
			String [] tuesdayTime, String [] tuesdayTimeBool,
			String [] wednesdayTime, String [] wednesdayTimeBool,
			String [] thursdayTime, String [] thursdayTimeBool,
			String [] fridayTime, String [] fridayTimeBool,
			String [] saturdayTime, String [] saturdayTimeBool,
			String [] sundayTime, String [] sundayTimeBool) {
		
		DayOfWeek [] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
		List<String[]> weekDaysTime = new ArrayList<>();
		weekDaysTime.add(mondayTime);
		weekDaysTime.add(tuesdayTime);
		weekDaysTime.add(wednesdayTime);
		weekDaysTime.add(thursdayTime);
		weekDaysTime.add(fridayTime);
		weekDaysTime.add(saturdayTime);
		weekDaysTime.add(sundayTime);
		List<String[]> weekDaysTimeBool = new ArrayList<>();
		weekDaysTimeBool.add(mondayTimeBool);
		weekDaysTimeBool.add(tuesdayTimeBool);
		weekDaysTimeBool.add(wednesdayTimeBool);
		weekDaysTimeBool.add(thursdayTimeBool);
		weekDaysTimeBool.add(fridayTimeBool);
		weekDaysTimeBool.add(saturdayTimeBool);
		weekDaysTimeBool.add(sundayTimeBool);
		
		Map<LocalTime, Boolean> weekDayTimeMap;	
		for(int i = 0; i<daysOfWeek.length; i++) {
			weekDayTimeMap = new LinkedHashMap<>();
			for (int j = 0; j < weekDaysTime.get(i).length; j++) {
				weekDayTimeMap.put(LocalTime.parse(weekDaysTime.get(i)[j]),	Boolean.valueOf(weekDaysTimeBool.get(i)[j]));
			}
			workingWeekMap.put(daysOfWeek[i], weekDayTimeMap);			
		}
	}
}
