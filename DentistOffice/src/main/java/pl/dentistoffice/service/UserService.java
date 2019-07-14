package pl.dentistoffice.service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.entity.WorkingWeek;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

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
	
	public void addNewDoctor(Doctor doctor) {
		userRepository.saveDoctor(doctor);
	}
	
	public void editDoctor(Doctor doctor) {
		userRepository.updateDoctor(doctor);
	}
	
	public Doctor getDoctor(int id) {
		return userRepository.readDoctor(id);
	}
	
	public List<Doctor> getAllDoctors(){
		List<Doctor> allDoctors = userRepository.readAllDoctors();
		allDoctors.sort(new Comparator<Doctor>() {

			@Override
			public int compare(Doctor o1, Doctor o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		return allDoctors;
	}
	
	public void addNewAssistant(Assistant assistant) {
		userRepository.saveAssistant(assistant);
	}
	
	public void editAssistant(Assistant assistant) {
		userRepository.updateAssistant(assistant);
	}
	
	public Assistant getAssistant(int id) {
		return userRepository.readAssistant(id);
	}
	
	public List<Assistant> getAllAssistants(){
		return userRepository.readAllAssistants();
	}
	
	public void addNewPatient(Patient patient) {
		userRepository.savePatient(patient);
	}
	
	public void editPatient(Patient patient) {
		userRepository.updatePatient(patient);
	}
	
	public Patient getPatient(int id) {
		return userRepository.readPatient(id);
	}
	
	public List<Patient> getAllPatients(){
		return userRepository.readAllPatients();
	}

	public Patient getLoggedPatient() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = (User) authentication.getPrincipal();
		Patient patient = userRepository.readPatient(loggedUser.getId());
		return patient;
	}
	
	public Admin getLoggedAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = (User) authentication.getPrincipal();
		Admin admin = userRepository.readAdmin(loggedUser.getId());
		return admin;
	}
	
	public Doctor getLoggedDoctor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = (User) authentication.getPrincipal();
		Doctor doctor = userRepository.readDoctor(loggedUser.getId());
		return doctor;
	}
	
	public Assistant getLoggedAssistant() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = (User) authentication.getPrincipal();
		Assistant assistant = userRepository.readAssistant(loggedUser.getId());
		return assistant;
	}
	
	public Collection<? extends GrantedAuthority> getAuthoritiesLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		return authorities;
	}
	
	public List<Role> getPatientRole() {
		List<Role> allRoles = userRepository.readAllRoles();
		ListIterator<Role> listIterator = allRoles.listIterator();
		while (listIterator.hasNext()) {
			Role role = (Role) listIterator.next();
			if(!role.getRole().equals("ROLE_PATIENT")) {
				listIterator.remove();
			}
		}
		return allRoles;
	}
	
	public List<Role> getAllRoles(){
		List<Role> allRoles = userRepository.readAllRoles();
		allRoles.sort(new Comparator<Role>() {

			@Override
			public int compare(Role o1, Role o2) {
				return o1.getId() - o2.getId();
			}
		});
		return allRoles;
	}
	
	public Map<DayOfWeek, Map<LocalTime, Boolean>> getTemplateWorkingWeekMap() {
		Map<DayOfWeek, Map<LocalTime, Boolean>> templateMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> weekDayTimeMap;
		DayOfWeek [] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY};
		String [] weekDayTime = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", 
								"15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30"};
		
		for(int i = 0; i<daysOfWeek.length; i++) {
			weekDayTimeMap = new LinkedHashMap<>();
			for (int j = 0; j < weekDayTime.length; j++) {
				weekDayTimeMap.put(LocalTime.parse(weekDayTime[j]), false);
			}
			templateMap.put(daysOfWeek[i], weekDayTimeMap);			
		}
		
		return templateMap;
	}
}
