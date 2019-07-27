package pl.dentistoffice.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public void addNewDoctor(Doctor doctor) {
		User user = doctor.getUser();
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordField()));
		doctor.setRegisteredDateTime(LocalDateTime.now());
		userRepository.saveDoctor(doctor);
	}
	
	public void editDoctor(Doctor doctor) {
		User user = doctor.getUser();
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordField()));
		List<Role> currentRolesList = createCurrentRolesList(user);
		user.setRoles(currentRolesList);
		doctor.setEditedDateTime(LocalDateTime.now());
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
	
	public Doctor getLoggedDoctor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Doctor doctor = userRepository.readDoctor(username);
		return doctor;
	}
	
	public void addNewAssistant(Assistant assistant) {
		User user = assistant.getUser();
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordField()));
		assistant.setRegisteredDateTime(LocalDateTime.now());
		userRepository.saveAssistant(assistant);
	}
	
	public void editAssistant(Assistant assistant) {
		User user = assistant.getUser();
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordField()));
		assistant.setEditedDateTime(LocalDateTime.now());
		List<Role> currentRolesList = createCurrentRolesList(user);
		user.setRoles(currentRolesList);
		userRepository.updateAssistant(assistant);
	}
	
	public Assistant getAssistant(int id) {
		return userRepository.readAssistant(id);
	}
	
	public List<Assistant> getAllAssistants(){
		List<Assistant> allAssistants = userRepository.readAllAssistants();
		allAssistants.sort(new Comparator<Assistant>() {

			@Override
			public int compare(Assistant o1, Assistant o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		return allAssistants;
	}

	public Assistant getLoggedAssistant() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Assistant assistant = userRepository.readAssistant(username);
		return assistant;
	}
	
	public void addNewPatient(Patient patient) {
		User user = patient.getUser();
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordField()));
		List<Role> patientRole = getPatientRole();
		user.setRoles(patientRole);
		user.setEnabled(true);
		patient.setUser(user);
		patient.setRegisteredDateTime(LocalDateTime.now());		
		userRepository.savePatient(patient);
	}
	
	public void editPatient(Patient patient) {
		User user = patient.getUser();
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordField()));
		patient.setEditedDateTime(LocalDateTime.now());
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
		String username = authentication.getName();
		Patient patient = userRepository.readPatient(username);
		return patient;
	}
	
	public List<Patient> searchPatient(String text){
		List<Patient> searchPatient = userRepository.searchPatientNamePeselStreetPhoneByKeywordQuery(text);
		searchPatient.sort(new Comparator<Patient>() {

			@Override
			public int compare(Patient o1, Patient o2) {
				return o1.getLastName().compareTo(o2.getLastName());
			}
		});
		return searchPatient;
	}
	
	
	
	public Admin getLoggedAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Admin admin = userRepository.readAdmin(username);
		return admin;
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
		DayOfWeek [] daysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
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
	
	private List<Role> createCurrentRolesList(User user){
		List<Role> selectedIdRoles = user.getRoles(); //only id is selected on page. Role and roleName was't change
		List<Role> currentRolesList = new ArrayList<>();
		Role currentRole;
		for (Role role : selectedIdRoles) {
			currentRole = new Role();
			currentRole.setId(role.getId());
			currentRolesList.add(currentRole);
		}
		return currentRolesList;
	}
	
	public String [] dayOfWeekPolish() {
		String [] dayOfWeekPolish = {"Zero", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"};
		return dayOfWeekPolish;
	}
}
