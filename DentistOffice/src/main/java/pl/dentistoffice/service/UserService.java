package pl.dentistoffice.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.WorkingWeek;



@Service
public class UserService {
	
	@Autowired
	public UserRepository userRepository;

//	public void setWorkingWeek(int idDoctor, DayOfWeek workingDays, Map<LocalTime, Boolean> workingHours) {
//		Doctor doctor = userRepository.readDoctor(idDoctor);
//		WorkingWeek workingWeek = doctor.getWorkingWeek();
//		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();
//		workingWeekMap.put(workingDays, workingHours);
//		workingWeek.setWorkingWeekMap(workingWeekMap);
//		doctor.setWorkingWeek(workingWeek);
//		userRepository.saveDoctor(doctor);
//	}
//	
//	public WorkingWeek getWorkingWeek(Doctor doctor) {
//		return doctor.getWorkingWeek();
//	}
	
	public boolean addNewDoctor(Doctor doctor) {
		if(userRepository.saveDoctor(doctor)) {
			return true;
		}
		return false;
	}
	
	public boolean editDoctor(Doctor doctor) {
		if(userRepository.updateDoctor(doctor)) {
			return true;
		}
		return false;		
	}
	
	public Doctor getDoctor(int id) {
		return userRepository.readDoctor(id);
	}
	
	public List<Role> getAllRoles(){
		return userRepository.readAllRoles();
	}
	
	public Map<DayOfWeek, Map<LocalTime, Boolean>> getTemplateWorkingWeekMap() {
		Map<DayOfWeek, Map<LocalTime, Boolean>> templateMap = new LinkedHashMap<>();
		List<Map<LocalTime, Boolean>> dayListsMap = new ArrayList<>();
		Map<LocalTime, Boolean> mondayTimeMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> tuesdayTimeMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> wednesdayTimeMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> thursdayTimeMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> fridayTimeMap = new LinkedHashMap<>();
		dayListsMap.add(mondayTimeMap);
		dayListsMap.add(tuesdayTimeMap);
		dayListsMap.add(wednesdayTimeMap);
		dayListsMap.add(thursdayTimeMap);
		dayListsMap.add(fridayTimeMap);
		DayOfWeek [] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
		String [] weekDayTime = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"};
		
		for(int i = 0; i<dayListsMap.size(); i++) {
			for (int j = 0; j < weekDayTime.length; j++) {
				dayListsMap.get(i).put(LocalTime.parse(weekDayTime[j]), false);
			}
			templateMap.put(daysOfWeek[i], dayListsMap.get(i));			
		}
		
		return templateMap;
	}
}
