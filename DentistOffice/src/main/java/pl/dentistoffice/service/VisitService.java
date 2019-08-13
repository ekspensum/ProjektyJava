package pl.dentistoffice.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
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
import pl.dentistoffice.entity.VisitTreatmentComment;
import pl.dentistoffice.entity.WorkingWeek;

@Service
public class VisitService {
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private HibernateSearchService searchsService;

	public boolean addNewVisitByPatient(Doctor doctor, String [] dateTime, List<DentalTreatment> treatments) {
		Visit visit = new Visit();
		visit.setDoctor(doctor);
		Patient patient = userService.getLoggedPatient();
		visit.setPatient(patient);
		visit.setUserLogged(patient.getUser());
		
		VisitStatus visitStatus = visitRepository.readVisitStatus(1);
		visit.setStatus(visitStatus);
		
		visit.setTreatments(treatments);
		visit.setVisitConfirmation(false);
		
		List<VisitTreatmentComment> visitTreatmentCommentsList = new ArrayList<>();
		VisitTreatmentComment visitTreatmentComment;
		for (int i = 0; i < 3; i++) {
			visitTreatmentComment = new VisitTreatmentComment();
			visitTreatmentComment.setComment("");
			visitTreatmentComment.setTreatment(treatments.get(i));
			visitTreatmentCommentsList.add(visitTreatmentComment);
		}
		visit.setVisitTreatmentComment(visitTreatmentCommentsList);
		
		String[] splitDateTime = dateTime[0].split(";");
		
		LocalDateTime visitDateTime = LocalDateTime.of(LocalDate.parse(splitDateTime[0]), LocalTime.parse(splitDateTime[1]));
		visit.setVisitDateTime(visitDateTime);
		visit.setReservationDateTime(LocalDateTime.now());
		if(visitRepository.saveVisit(visit)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addNewVisitByAssistant(Patient patient, Doctor doctor, String [] dateTime, List<DentalTreatment> treatments) {
		Visit visit = new Visit();
		visit.setDoctor(doctor);
		
		Assistant assistant = userService.getLoggedAssistant();
		visit.setAssistant(assistant);
		
		visit.setUserLogged(assistant.getUser());
		visit.setPatient(patient);
		
		VisitStatus visitStatus = visitRepository.readVisitStatus(1);
		visit.setStatus(visitStatus);
		
		visit.setTreatments(treatments);
		visit.setVisitConfirmation(false);
		
		List<VisitTreatmentComment> visitTreatmentCommentsList = new ArrayList<>();
		VisitTreatmentComment visitTreatmentComment;
		for (int i = 0; i < 3; i++) {
			visitTreatmentComment = new VisitTreatmentComment();
			visitTreatmentComment.setComment("");
			visitTreatmentComment.setTreatment(treatments.get(i));
			visitTreatmentCommentsList.add(visitTreatmentComment);
		}
		visit.setVisitTreatmentComment(visitTreatmentCommentsList);
		
		String[] splitDateTime = dateTime[0].split(";");
		
		LocalDateTime visitDateTime = LocalDateTime.of(LocalDate.parse(splitDateTime[0]), LocalTime.parse(splitDateTime[1]));
		visit.setVisitDateTime(visitDateTime);
		visit.setReservationDateTime(LocalDateTime.now());
		if(visitRepository.saveVisit(visit)) {
			return true;
		} else {
			return false;
		}
	}
	
	public Map<LocalDate, Map<LocalTime, Boolean>> getWorkingWeekFreeTimeMap(Doctor doctor, int dayStart, int dayEnd){
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = new LinkedHashMap<>();
		
		WorkingWeek workingWeek = doctor.getWorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = workingWeek.getWorkingWeekMap();
		
		List<Visit> visitsList = visitRepository.readVisits(LocalDateTime.now().plusDays(dayStart), LocalDateTime.now().plusDays(dayEnd), doctor);
		
		Iterator<Entry<DayOfWeek, Map<LocalTime, Boolean>>> iteratorWorkingWeekMap;
		Iterator<Entry<LocalTime, Boolean>> iteratorDayTimeMap;

		/*
		 * In this method part, working week map for this doctor is processing - removing is every record from time map, which value is false  
		 */
		iteratorWorkingWeekMap = workingWeekMap.entrySet().iterator();
		while (iteratorWorkingWeekMap.hasNext()) {
			Entry<DayOfWeek, Map<LocalTime, Boolean>> nextDay = iteratorWorkingWeekMap.next();
			iteratorDayTimeMap = nextDay.getValue().entrySet().iterator();
			while (iteratorDayTimeMap.hasNext()) {
				Entry<LocalTime, Boolean> nextTime = iteratorDayTimeMap.next();
				if (!nextTime.getValue()) {
					iteratorDayTimeMap.remove();
				}
			}
		}

		/**
		 * In this method part, if visit list size is greaten then 0, working week map for this doctor is processing - removing is every record from time map, which is taken 
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
						if (keyTime.equals(visit.getVisitDateTime().toLocalTime())) {
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
		for (int i=dayStart; i<dayEnd; i++) {
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
	
	public List<Visit> getVisitsByPatientAndStatus(Patient patient, VisitStatus visitStatus){
		List<Visit> visits = visitRepository.readVisits(patient, visitStatus);
		visits.sort(new Comparator<Visit>() {

			@Override
			public int compare(Visit o1, Visit o2) {
				return o2.getVisitDateTime().compareTo(o1.getVisitDateTime());
			}
		});
		return visits;
	}
	
	public List<VisitStatus> getVisitStatusList(){
		List<VisitStatus> allVisitStatus = visitRepository.readAllVisitStatus();
		allVisitStatus.sort(new Comparator<VisitStatus>() {

			@Override
			public int compare(VisitStatus o1, VisitStatus o2) {
				return o1.getId() - o2.getId();
			}
		});
		return allVisitStatus;
	}
	
	public VisitStatus getVisitStatus(int stausId) {
		return visitRepository.readVisitStatus(stausId);
	}
	
	public List<Visit> getVisitsToFinalize(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
//		VisitStatus visitStatus = visitRepository.readVisitStatus(1);
//		List<Visit> visits = visitRepository.readVisits(dateTimeFrom, dateTimeTo, visitStatus);
		List<Visit> visits = searchsService.searchVisitDateAndStatusByKeywordQuery(dateTimeFrom, dateTimeTo);
		visits.sort(new Comparator<Visit>() {

			@Override
			public int compare(Visit o1, Visit o2) {
				return o2.getVisitDateTime().compareTo(o1.getVisitDateTime());
			}
		});
		return visits;
	}
	
	public List<Visit> getVisitsToFinalize(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, Doctor doctor) {
		VisitStatus visitStatus = visitRepository.readVisitStatus(1);
		List<Visit> visits = visitRepository.readVisits(dateTimeFrom, dateTimeTo, visitStatus, doctor);
		visits.sort(new Comparator<Visit>() {

			@Override
			public int compare(Visit o1, Visit o2) {
				return o2.getVisitDateTime().compareTo(o1.getVisitDateTime());
			}
		});
		return visits;
	}
	
	public Visit getVisit(int visitId) {
		return visitRepository.readVisit(visitId);
	}
	
	public boolean setFinalzedVisit(Visit visit, List<DentalTreatment> selectedTreatments, String [] treatmentCommentVisit) {
		List<VisitTreatmentComment> visitTreatmentCommentsList = new ArrayList<>();
		VisitTreatmentComment visitTreatmentComment;
		for (int i = 0; i < treatmentCommentVisit.length; i++) {
			visitTreatmentComment = new VisitTreatmentComment();
			visitTreatmentComment.setComment(treatmentCommentVisit[i]);
			visitTreatmentComment.setTreatment(selectedTreatments.get(i));
			visitTreatmentCommentsList.add(visitTreatmentComment);
		}
		visit.setVisitTreatmentComment(visitTreatmentCommentsList);
		VisitStatus visitStatus = visitRepository.readVisitStatus(2);
		visit.setStatus(visitStatus);
		visit.setFinalizedVisitDateTime(LocalDateTime.now());
		visit.setTreatments(selectedTreatments);
		if (visitRepository.updateVisitOnFinalize(visit)) {
			return true;
		} else {
			return false;
		}
	}
		
	public boolean cancelVisit(Visit visit) {
		if(visitRepository.removeVisit(visit)) {
			return true;
		} else {
			return false;
		}
	}
}
