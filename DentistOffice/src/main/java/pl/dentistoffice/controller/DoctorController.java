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
		userService.addNewDoctor(doctor);
		
		model.addAttribute("rolesList", userService.getAllRoles());
		return "doctorRegister";
	}
	
	@RequestMapping(path = "/doctorEdit")
	public String editDoctor(Model model) {
		model.addAttribute("rolesList", userService.getAllRoles());		
		Doctor doctor = userService.getDoctor(2);
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
		model.addAttribute("rolesList", userService.getAllRoles());
		doctor.setPhoto(photo.getBytes());		
		
////		MONDAY
//		Map<LocalTime, Boolean> mondayTimeMap = new LinkedHashMap<>();
//		for (int i = 0; i < mondayTime.length; i++) {
//			mondayTimeMap.put(LocalTime.parse(mondayTime[i], DateTimeFormatter.ofPattern("HH:mm")),	Boolean.valueOf(mondayTimeBool[i]));
//		}
//		workingWeekMap.put(DayOfWeek.MONDAY, mondayTimeMap);
//
////		TUESDAY
//		Map<LocalTime, Boolean> tuesdayTimeMap = new LinkedHashMap<>();
//		for (int i = 0; i < tuesdayTime.length; i++) {
//			tuesdayTimeMap.put(LocalTime.parse(tuesdayTime[i], DateTimeFormatter.ofPattern("HH:mm")), Boolean.valueOf(tuesdayTimeBool[i]));
//		}
//		workingWeekMap.put(DayOfWeek.TUESDAY, tuesdayTimeMap);
		
//		for(Entry<DayOfWeek, Map<LocalTime, Boolean>> entry : workingWeekMap.entrySet()) {
//			System.out.println(entry.getKey()+ " "+entry.getValue());
//		}
		
		WorkingWeek workingWeek = doctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();
		
		setWorkingWeekMap(workingWeekMap, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, 
				wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool);
		
		workingWeek.setWorkingWeekMap(workingWeekMap);
		doctor.setWorkingWeek(workingWeek);
		userService.editDoctor(doctor);
		
		model.addAttribute("workingWeekMap", workingWeekMap);
		return "doctorEdit";
	}
	
	private void setWorkingWeekMap(Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap,
			String [] mondayTime, String [] mondayTimeBool,
			String [] tuesdayTime, String [] tuesdayTimeBool,
			String [] wednesdayTime, String [] wednesdayTimeBool,
			String [] thursdayTime, String [] thursdayTimeBool,
			String [] fridayTime, String [] fridayTimeBool) {
		
		List<Map<LocalTime, Boolean>> listsMap = new ArrayList<>();
		Map<LocalTime, Boolean> mondayTimeMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> tuesdayTimeMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> wednesdayTimeMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> thursdayTimeMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> fridayTimeMap = new LinkedHashMap<>();
		listsMap.add(mondayTimeMap);
		listsMap.add(tuesdayTimeMap);
		listsMap.add(wednesdayTimeMap);
		listsMap.add(thursdayTimeMap);
		listsMap.add(fridayTimeMap);		
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
		
		for(int i = 0; i<listsMap.size(); i++) {
			for (int j = 0; j < weekDaysTime.get(i).length; j++) {
				listsMap.get(i).put(LocalTime.parse(weekDaysTime.get(i)[j]),	Boolean.valueOf(weekDaysTimeBool.get(i)[j]));
			}
			workingWeekMap.put(daysOfWeek[i], listsMap.get(i));			
		}
	}
}
