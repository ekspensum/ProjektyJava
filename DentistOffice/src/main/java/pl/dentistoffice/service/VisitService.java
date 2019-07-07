package pl.dentistoffice.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.dao.VisitRepository;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.entity.WorkingWeek;

@Service
public class VisitService {
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired
	private UserRepository userRepository;

	public void addNewVisitByPatient(Doctor doctor, String date, String time, List<DentalTreatment> treatments) {
		Visit visit = new Visit();
	
		visit.setDoctor(doctor);
		
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User loggedPatient = (User) authentication.getPrincipal();
//		Patient patient = userRepository.readPatient(loggedPatient.getId());
//		visit.setPatient(patient);
//		visit.setUserLogged(loggedPatient);

//		Assistant assistant = userRepository.readAssistant(1);
//		visit.setAssistant(assistant);
		
		VisitStatus visitStatus = visitRepository.readVisitStaus(1);
		visit.setStatus(visitStatus);
		
		visit.setTreatments(treatments);
		visit.setVisitConfirmation(false);
		
		visit.setVisitDateTime(LocalDateTime.of(2019, 7, 5, 17, 30));
		
		visitRepository.saveVisit(visit);
	}
	
	public Map<LocalDate, Map<LocalTime, Boolean>> getWorkingWeekFreeTimeMap(Doctor doctor){
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = new LinkedHashMap<>();
		
		WorkingWeek workingWeek = doctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();
		
		List<Visit> visitsList = visitRepository.readVisits(LocalDateTime.now(), LocalDateTime.now().plusDays(7), doctor);
		
		Iterator<Entry<DayOfWeek, Map<LocalTime, Boolean>>> iteratorWorkingWeekMap;
		Iterator<Entry<LocalTime, Boolean>> iteratorDayTimeMap;

		for (Visit visit : visitsList) {
			iteratorWorkingWeekMap = workingWeekMap.entrySet().iterator();
			while (iteratorWorkingWeekMap.hasNext()) {
				Entry<DayOfWeek, Map<LocalTime, Boolean>> nextDay = iteratorWorkingWeekMap.next();
				DayOfWeek keyDay = nextDay.getKey();
				if (visit.getVisitDateTime().getDayOfWeek().equals(keyDay)) {
					iteratorDayTimeMap = nextDay.getValue().entrySet().iterator();
					while (iteratorDayTimeMap.hasNext()) {
						Entry<LocalTime, Boolean> nextTime = iteratorDayTimeMap.next();
						LocalTime keyTime = nextTime.getKey();
						Boolean valueTime = nextTime.getValue();
						if (keyTime.equals(visit.getVisitDateTime().toLocalTime()) || !valueTime) {
							iteratorDayTimeMap.remove();
						}
					}
				} else {
					iteratorDayTimeMap = nextDay.getValue().entrySet().iterator();
					while (iteratorDayTimeMap.hasNext()) {
						Entry<LocalTime, Boolean> nextTime = iteratorDayTimeMap.next();
						if (!nextTime.getValue()) {
							iteratorDayTimeMap.remove();
						}
					}
				}
			}
		}
				
//		System.out.println("\n");
//		for (Entry<DayOfWeek, Map<LocalTime, Boolean>> entryWorkingWeekMap : workingWeekMap.entrySet()) {
//			System.out.println("Doctor working time 2 "+entryWorkingWeekMap.getKey()+ " "+entryWorkingWeekMap.getValue());
//		}
//		
//		System.out.println("\n");

		for (int i=0; i<7; i++) {
			for (Entry<DayOfWeek, Map<LocalTime, Boolean>> entryWorkingWeekMap : workingWeekMap.entrySet()) {
				if(LocalDate.now().plusDays(i).getDayOfWeek().equals(entryWorkingWeekMap.getKey())) {
					workingWeekFreeTimeMap.put(LocalDate.now().plusDays(i), entryWorkingWeekMap.getValue());
					break;
				}
			}
		}
		
		return workingWeekFreeTimeMap;
	}
}
