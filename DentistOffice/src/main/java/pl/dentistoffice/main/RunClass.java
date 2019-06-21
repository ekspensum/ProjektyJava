package pl.dentistoffice.main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import pl.dentistoffice.config.RootConfig;
import pl.dentistoffice.dao.TreatmentRepository;
import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.dao.VisitRepository;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.entity.WorkingTime;
import pl.dentistoffice.service.UserService;

public class RunClass {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		
//		TreatmentRepository treatmentBean = context.getBean(TreatmentRepository.class);
//		List<TreatmentCategory> treatmentCategory = treatmentRepository.readAllTreatmentCategory();

		VisitRepository visitBean = context.getBean(VisitRepository.class);
		UserRepository userBean = context.getBean(UserRepository.class);
		UserService userServiceBean = context.getBean(UserService.class);
		
//		VisitStatus visitStatus = new VisitStatus();
//		visitStatus.setStatusName("PLANNED");
//		visitBean.saveVistStatus(visitStatus);
		
		Visit visit = new Visit();
		visit.setVisitDateTime(LocalDateTime.now());
		visit.setStatus(visitBean.readVisitStaus(2));
		Doctor doctor2 = userBean.readDoctor(2);
		visit.setDoctor(doctor2);
		visitBean.saveVisit(visit);
		
//		Map<LocalDate, Boolean> workingDate1 = new HashMap<>();
//		workingDate1.put(LocalDate.now(), true);
//		workingDate1.put(LocalDate.now().plusDays(11), false);
//		workingDate1.put(LocalDate.now().plusDays(12), true);
//		workingDate1.put(LocalDate.now().plusDays(13), false);
//		Map<LocalTime, Boolean> workingHours1 = new HashMap<>();
//		WorkingTime workingTime1 = new WorkingTime();
//		Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> workingTimeMap = new HashMap<>();
//		workingTimeMap.put(workingDate1, workingHours1);
//		workingTime1.setWorkingTimeMap(workingTimeMap);
//		userBean.saveWorkingTime(workingTime1);
		
//		Role role1 = new Role();
//		role1.setRole("ADMIN");
//		role1.setRoleName("Administrator");
//		userBean.saveRole(role1);
//		Role role2 = new Role();
//		role2.setRole("DOCTOR");
//		role2.setRoleName("Stomatolog");
//		userBean.saveRole(role2);
		
		
		
//		Map<LocalDate, Boolean> workingDate1 = new LinkedHashMap<>();
//		workingDate1.put(LocalDate.now(), true);
//		Map<LocalDate, Boolean> workingDate2 = new LinkedHashMap<>();
//		workingDate2.put(LocalDate.now().plusDays(1), false);
//		Map<LocalDate, Boolean> workingDate3 = new LinkedHashMap<>();
//		workingDate3.put(LocalDate.now().plusDays(2), true);
//		Map<LocalDate, Boolean> workingDate4 = new LinkedHashMap<>();
//		workingDate4.put(LocalDate.now().plusDays(3), false);
//		
//		Map<LocalTime, Boolean> workingHours = new LinkedHashMap<>();
//		workingHours.put(LocalTime.of(13, 30), true);
//		workingHours.put(LocalTime.of(14, 00), false);
//		workingHours.put(LocalTime.of(14, 30), true);
//		workingHours.put(LocalTime.of(15, 00), false);
//		
//		Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> workingTimeMap = new LinkedHashMap<>();
//		workingTimeMap.put(workingDate1, workingHours);
//		workingTimeMap.put(workingDate2, workingHours);
//		workingTimeMap.put(workingDate3, workingHours);
//		workingTimeMap.put(workingDate4, workingHours);
//		
//		Doctor doctor = new Doctor();
//		doctor.setFirstName("firstName");
//		WorkingTime workingTime = new WorkingTime();
//		workingTime.setWorkingTimeMap(workingTimeMap);
//		doctor.setWorkingTime(workingTime);
//		
//		User user = new User();
//		user.setUsername("username");
//		List<Role> roles = new ArrayList<>();
//		roles.add(userBean.readRole(1));
//		roles.add(userBean.readRole(2));
//		user.setRoles(roles);
//		doctor.setUser(user);
//		userBean.saveDoctor(doctor);
		
//		userServiceBean.setWorkingTime(8, workingDate, workingHours);
			
//		List<Visit> readVisits = visitBean.readVisits(LocalDateTime.now().minusDays(1), LocalDateTime.now(), true);
//		for(int i=0; i<readVisits.size(); i++)
//			System.out.println(readVisits.get(i).getVisitDateTime()+" "+readVisits.get(i).getStatus().getStatusName());
		
//		Doctor doctor2 = userBean.readDoctor(2);
//		WorkingTime workingTime2 = doctor2.getWorkingTime();
//		Map<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> map = workingTime2.getWorkingTimeMap();
//
//		for(Map.Entry<Map<LocalDate, Boolean>, Map<LocalTime, Boolean>> entry : map.entrySet()) {
//			for(int i=0; i<entry.getKey().size(); i++) {
//				System.out.println("Day Key "+entry.getKey().keySet().toArray()[i]+" Day Value "+entry.getKey().values().toArray()[i]);
//				for(int j=0; j<entry.getValue().size(); j++) {
//					System.out.println("Hours Key "+entry.getValue().keySet().toArray()[j]+" Hours Value "+entry.getValue().values().toArray()[j]);									
//				}
//			}
//		}
		
		context.close();
	}

}
