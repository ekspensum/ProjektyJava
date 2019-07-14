package pl.dentistoffice.controller;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.WorkingWeek;
import pl.dentistoffice.service.UserService;

@Controller
@SessionAttributes({"doctor", "loggedDoctor"})
public class DoctorController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/panels/doctorPanel")
	public String doctorPanel(Model model) {

		return "doctorPanel";
	}
	
	@RequestMapping(path = "/users/doctor/admin/register")
	public String registrationDoctor(Model model) {
		model.addAttribute("rolesList", userService.getAllRoles());
		model.addAttribute("doctor", new Doctor());
		model.addAttribute("templateWorkingWeekMap", userService.getTemplateWorkingWeekMap());
		return "/users/doctor/admin/register";
	}
	
	@RequestMapping(path = "/users/doctor/admin/register", method = RequestMethod.POST)
	public String registerDoctor(
			@Valid
			Doctor doctor,
			BindingResult result, 
			@RequestParam("photo") 
			MultipartFile photo,
			String [] mondayTime, String [] mondayTimeBool,
			String [] tuesdayTime, String [] tuesdayTimeBool,
			String [] wednesdayTime, String [] wednesdayTimeBool,
			String [] thursdayTime, String [] thursdayTimeBool,
			String [] fridayTime, String [] fridayTimeBool,
			String [] saturdayTime, String [] saturdayTimeBool,
			Model model) throws IOException {
		
			WorkingWeek workingWeek = new WorkingWeek();

			Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = new LinkedHashMap<>();
			setWorkingWeekMap(workingWeekMap, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime,
					wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool);

			workingWeek.setWorkingWeekMap(workingWeekMap);
			doctor.setWorkingWeek(workingWeek);
			doctor.setPhoto(photo.getBytes());
			doctor.setRegisteredDateTime(LocalDateTime.now());
			
			if(!result.hasErrors() && doctor.getUser().getRoles().get(0).getId() != doctor.getUser().getRoles().get(1).getId()) {
			userService.addNewDoctor(doctor);
			model.addAttribute("success", env.getProperty("successRegisterDoctor"));
			return "forward:/success";
		} else {
			if(doctor.getUser().getRoles().get(0).getId() == doctor.getUser().getRoles().get(1).getId()) {
				model.addAttribute("roleError", env.getProperty("roleError"));
			}
			model.addAttribute("rolesList", userService.getAllRoles());
			model.addAttribute("templateWorkingWeekMap", workingWeekMap);
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
		return "redirect:/users/doctor/admin/edit";
	}
	
	@RequestMapping(path = "/users/doctor/admin/edit")
	public String editDoctor(@ModelAttribute("doctor") Doctor doctor, Model model) {
		model.addAttribute("doctor", doctor);
		model.addAttribute("rolesList", userService.getAllRoles());				
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = doctor.getWorkingWeek().getWorkingWeekMap();
		model.addAttribute("workingWeekMap", workingWeekMap);
		return "/users/doctor/admin/edit";
	}
	
	@RequestMapping(path = "/users/doctor/admin/edit", method = RequestMethod.POST)
	public String editDataDoctor(
			@Valid
			Doctor doctor,
			BindingResult result, 
			@RequestParam("photo") 
			MultipartFile photo,
			String [] mondayTime, String [] mondayTimeBool,
			String [] tuesdayTime, String [] tuesdayTimeBool,
			String [] wednesdayTime, String [] wednesdayTimeBool,
			String [] thursdayTime, String [] thursdayTimeBool,
			String [] fridayTime, String [] fridayTimeBool,
			String [] saturdayTime, String [] saturdayTimeBool,
			Model model) throws IOException {

		WorkingWeek workingWeek = doctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();

		setWorkingWeekMap(workingWeekMap, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime,
				wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool);

		workingWeek.setWorkingWeekMap(workingWeekMap);
		doctor.setWorkingWeek(workingWeek);
		doctor.setPhoto(photo.getBytes());
		doctor.setEditedDateTime(LocalDateTime.now());

		if(!result.hasErrors() && doctor.getUser().getRoles().get(0).getId() != doctor.getUser().getRoles().get(1).getId()) {
			userService.editDoctor(doctor);
			model.addAttribute("success", env.getProperty("successUpdateDoctor"));
			return "forward:/success";
		} else {
			if(doctor.getUser().getRoles().get(0).getId() == doctor.getUser().getRoles().get(1).getId()) {
				model.addAttribute("roleError", env.getProperty("roleError"));
			}
			model.addAttribute("rolesList", userService.getAllRoles());
			model.addAttribute("workingWeekMap", workingWeekMap);
			return "/users/doctor/admin/edit";
		}
	}
	
	@RequestMapping(path = "/users/doctor/edit")
	public String selfEditDoctor(Model model) {
		Doctor loggedDoctor = userService.getLoggedDoctor();
		model.addAttribute("loggedDoctor", loggedDoctor);
		model.addAttribute("rolesList", userService.getAllRoles());				
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = loggedDoctor.getWorkingWeek().getWorkingWeekMap();
		model.addAttribute("workingWeekMap", workingWeekMap);
		return "/users/doctor/admin/edit";
	}
	
	@RequestMapping(path = "/users/doctor/edit", method = RequestMethod.POST)
	public String selfEditDataDoctor(
			@Valid
			@ModelAttribute(name = "loggedDoctor")
			Doctor loggedDoctor,
			BindingResult result, 
			@RequestParam("photo") 
			MultipartFile photo,
			String [] mondayTime, String [] mondayTimeBool,
			String [] tuesdayTime, String [] tuesdayTimeBool,
			String [] wednesdayTime, String [] wednesdayTimeBool,
			String [] thursdayTime, String [] thursdayTimeBool,
			String [] fridayTime, String [] fridayTimeBool,
			String [] saturdayTime, String [] saturdayTimeBool,
			Model model) throws IOException {

		WorkingWeek workingWeek = loggedDoctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();

		setWorkingWeekMap(workingWeekMap, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime,
				wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool);

		workingWeek.setWorkingWeekMap(workingWeekMap);
		loggedDoctor.setWorkingWeek(workingWeek);
		loggedDoctor.setPhoto(photo.getBytes());
		loggedDoctor.setEditedDateTime(LocalDateTime.now());

		if(!result.hasErrors() && loggedDoctor.getUser().getRoles().get(0).getId() != loggedDoctor.getUser().getRoles().get(1).getId()) {
			userService.editDoctor(loggedDoctor);
			model.addAttribute("success", env.getProperty("successUpdateDoctor"));
			return "forward:/success";
		} else {
			if(loggedDoctor.getUser().getRoles().get(0).getId() == loggedDoctor.getUser().getRoles().get(1).getId()) {
				model.addAttribute("roleError", env.getProperty("roleError"));
			}
			model.addAttribute("rolesList", userService.getAllRoles());
			model.addAttribute("workingWeekMap", workingWeekMap);
			return "/users/doctor/admin/edit";
		}
	}
	
	private void setWorkingWeekMap(Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap,
			String [] mondayTime, String [] mondayTimeBool,
			String [] tuesdayTime, String [] tuesdayTimeBool,
			String [] wednesdayTime, String [] wednesdayTimeBool,
			String [] thursdayTime, String [] thursdayTimeBool,
			String [] fridayTime, String [] fridayTimeBool,
			String [] saturdayTime, String [] saturdayTimeBool) {
		
		DayOfWeek [] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
		List<String[]> weekDaysTime = new ArrayList<>();
		weekDaysTime.add(mondayTime);
		weekDaysTime.add(tuesdayTime);
		weekDaysTime.add(wednesdayTime);
		weekDaysTime.add(thursdayTime);
		weekDaysTime.add(fridayTime);
//		weekDaysTime.add(saturdayTime);
		List<String[]> weekDaysTimeBool = new ArrayList<>();
		weekDaysTimeBool.add(mondayTimeBool);
		weekDaysTimeBool.add(tuesdayTimeBool);
		weekDaysTimeBool.add(wednesdayTimeBool);
		weekDaysTimeBool.add(thursdayTimeBool);
		weekDaysTimeBool.add(fridayTimeBool);
//		weekDaysTimeBool.add(saturdayTimeBool);
		
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
