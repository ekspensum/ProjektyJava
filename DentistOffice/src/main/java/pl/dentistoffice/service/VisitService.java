package pl.dentistoffice.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.dentistoffice.dao.VisitRepository;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.entity.WorkingWeek;

@Service
public class VisitService {
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired
	private UserService userService;

	public void addNewVisitByPatient(Doctor doctor, String [] dateTime, List<DentalTreatment> treatments) {
		Visit visit = new Visit();
		visit.setDoctor(doctor);
		Patient patient = userService.getLoggedPatient();
		visit.setPatient(patient);
		visit.setUserLogged(patient.getUser());
		
		VisitStatus visitStatus = visitRepository.readVisitStaus(1);
		visit.setStatus(visitStatus);
		
		visit.setTreatments(treatments);
		visit.setVisitConfirmation(false);
		
		String[] splitDateTime = dateTime[0].split(";");
		
		LocalDateTime visitDateTime = LocalDateTime.of(LocalDate.parse(splitDateTime[0]), LocalTime.parse(splitDateTime[1]));
		visit.setVisitDateTime(visitDateTime);
		visit.setReservationDateTime(LocalDateTime.now());
		try {
			visitRepository.saveVisit(visit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addNewVisitByAssistant(Patient patient, Doctor doctor, String [] dateTime, List<DentalTreatment> treatments) {
		Visit visit = new Visit();
		visit.setDoctor(doctor);
		
		Assistant assistant = userService.getLoggedAssistant();
		visit.setAssistant(assistant);
		
		visit.setUserLogged(assistant.getUser());
		visit.setPatient(patient);
		
		VisitStatus visitStatus = visitRepository.readVisitStaus(1);
		visit.setStatus(visitStatus);
		
		visit.setTreatments(treatments);
		visit.setVisitConfirmation(false);
		
		String[] splitDateTime = dateTime[0].split(";");
		
		LocalDateTime visitDateTime = LocalDateTime.of(LocalDate.parse(splitDateTime[0]), LocalTime.parse(splitDateTime[1]));
		visit.setVisitDateTime(visitDateTime);
		visit.setReservationDateTime(LocalDateTime.now());
		try {
			visitRepository.saveVisit(visit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Map<LocalDate, Map<LocalTime, Boolean>> getWorkingWeekFreeTimeMap(Doctor doctor){
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = new LinkedHashMap<>();
		
		WorkingWeek workingWeek = doctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();
		
		List<Visit> visitsList = visitRepository.readVisits(LocalDateTime.now(), LocalDateTime.now().plusDays(7), doctor);
		
		Iterator<Entry<DayOfWeek, Map<LocalTime, Boolean>>> iteratorWorkingWeekMap;
		Iterator<Entry<LocalTime, Boolean>> iteratorDayTimeMap;

		/*
		 * In this method part, working week map for this doctor is processing - removing is every record from time map, which is taken or value is false  
		 */
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
		
		/*
		 * This part of method create map, which keys are date 7 days forward. From created map removing is working hours after current time (LocalTime.now())
		 */
		Iterator<Entry<DayOfWeek, Map<LocalTime, Boolean>>> iteratorForFreeWorkingWeekMap;
		for (int i=0; i<7; i++) {
			iteratorForFreeWorkingWeekMap = workingWeekMap.entrySet().iterator();
			while(iteratorForFreeWorkingWeekMap.hasNext()) {
				Entry<DayOfWeek, Map<LocalTime, Boolean>> nextFreeDay = iteratorForFreeWorkingWeekMap.next();
				if(LocalDate.now().plusDays(i).getDayOfWeek().equals(nextFreeDay.getKey())) {
					if(i == 0) {
						Iterator<Entry<LocalTime, Boolean>> iteratorForFreeTime = nextFreeDay.getValue().entrySet().iterator();
						while(iteratorForFreeTime.hasNext()) {
							Entry<LocalTime, Boolean> nextFreeTime = iteratorForFreeTime.next();
							if(LocalTime.now().compareTo(nextFreeTime.getKey()) > 0) {
								iteratorForFreeTime.remove();
							}
						}
					}					
					workingWeekFreeTimeMap.put(LocalDate.now().plusDays(i), nextFreeDay.getValue());
					break;
				}
			}
		}
		
		return workingWeekFreeTimeMap;
	}
}
