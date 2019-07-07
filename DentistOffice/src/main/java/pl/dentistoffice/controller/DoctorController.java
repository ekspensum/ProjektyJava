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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.WorkingWeek;
import pl.dentistoffice.service.UserService;

@Controller
@SessionAttributes("doctor")
public class DoctorController {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(path = "/doctorRegister")
	public String registrationDoctor(Model model) {
		model.addAttribute("rolesList", userService.getAllRoles());
		model.addAttribute("doctor", new Doctor());
		model.addAttribute("templateWorkingWeekMap", userService.getTemplateWorkingWeekMap());
		return "doctorRegister";
	}
	
	@RequestMapping(path = "/doctorRegister", method = RequestMethod.POST)
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
			Model model) throws IOException {
		
			doctor.setPhoto(photo.getBytes());
			WorkingWeek workingWeek = new WorkingWeek();

			Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = new LinkedHashMap<>();
			setWorkingWeekMap(workingWeekMap, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime,
					wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool);

			workingWeek.setWorkingWeekMap(workingWeekMap);
			doctor.setWorkingWeek(workingWeek);
			
			if (!result.hasErrors()) {
			userService.addNewDoctor(doctor);
			model.addAttribute("success", env.getProperty("successRegisterDoctor"));
			
			return "forward:success";
		} else {
			model.addAttribute("rolesList", userService.getAllRoles());
			model.addAttribute("templateWorkingWeekMap", workingWeekMap);
			return "doctorRegister";
		}
	}
	
	@RequestMapping(path = "/doctorEdit")
	public String editDoctor(Model model) {
		model.addAttribute("rolesList", userService.getAllRoles());		
		Doctor doctor = userService.getDoctor(3);
		model.addAttribute("doctor", doctor);
		
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = doctor.getWorkingWeek().getWorkingWeekMap();
		model.addAttribute("workingWeekMap", workingWeekMap);

//		for(Entry<DayOfWeek, Map<LocalTime, Boolean>> entry : workingWeekMap.entrySet()) {
//			System.out.println(entry.getKey().getValue()+ " "+entry.getValue());
//		}
		
		model.addAttribute("workingWeekMap", workingWeekMap);
		return "doctorEdit";
	}
	
	@RequestMapping(path = "/doctorEdit", method = RequestMethod.POST)
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
			Model model) throws IOException {

		WorkingWeek workingWeek = doctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();

		setWorkingWeekMap(workingWeekMap, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime,
				wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool);

		workingWeek.setWorkingWeekMap(workingWeekMap);
		doctor.setWorkingWeek(workingWeek);

		if (!result.hasErrors()) {
			doctor.setPhoto(photo.getBytes());
			userService.editDoctor(doctor);
			model.addAttribute("success", env.getProperty("successUpdateDoctor"));
			return "forward:success";
		} else {
			model.addAttribute("rolesList", userService.getAllRoles());
			model.addAttribute("workingWeekMap", workingWeekMap);
			return "doctorEdit";
		}
	}
	
	private void setWorkingWeekMap(Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap,
			String [] mondayTime, String [] mondayTimeBool,
			String [] tuesdayTime, String [] tuesdayTimeBool,
			String [] wednesdayTime, String [] wednesdayTimeBool,
			String [] thursdayTime, String [] thursdayTimeBool,
			String [] fridayTime, String [] fridayTimeBool) {
		
		DayOfWeek [] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
		List<String[]> weekDaysTime = new ArrayList<>();
		weekDaysTime.add(mondayTime);
		weekDaysTime.add(tuesdayTime);
		weekDaysTime.add(wednesdayTime);
		weekDaysTime.add(thursdayTime);
		weekDaysTime.add(fridayTime);
		List<String[]> weekDaysTimeBool = new ArrayList<>();
		weekDaysTimeBool.add(mondayTimeBool);
		weekDaysTimeBool.add(tuesdayTimeBool);
		weekDaysTimeBool.add(wednesdayTimeBool);
		weekDaysTimeBool.add(thursdayTimeBool);
		weekDaysTimeBool.add(fridayTimeBool);
		
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
