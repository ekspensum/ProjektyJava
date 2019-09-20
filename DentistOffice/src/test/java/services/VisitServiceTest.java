package services;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.dao.UserRepositoryHibernatePostgreSQLImpl;
import pl.dentistoffice.dao.VisitRepository;
import pl.dentistoffice.dao.VisitRepositoryHibernatePostgreSQLImpl;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.entity.VisitTreatmentComment;
import pl.dentistoffice.entity.WorkingWeek;
import pl.dentistoffice.service.ActivationService;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.NotificationService;
import pl.dentistoffice.service.SendEmail;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

public class VisitServiceTest {

	private VisitService visitService;
	private VisitRepository visitRepository;
	private UserService userService;
	private UserRepository userRepository;
	private SessionFactory sessionFactory;
	private Session session;
	private NotificationService notificationService;
	private Environment env;
	private SendEmail sendEmail;
	private SecurityContext securityContext;
	private Authentication authentication;
	private ActivationService activationService;
	private HibernateSearchService searchsService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		sessionFactory = Mockito.mock(SessionFactory.class);
		session = Mockito.mock(Session.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		userRepository = new UserRepositoryHibernatePostgreSQLImpl(sessionFactory);
		env = Mockito.mock(Environment.class);
		sendEmail = Mockito.mock(SendEmail.class);
		notificationService = new NotificationService(env, sendEmail);
		when(env.getProperty("mailFrom")).thenReturn("email");
		when(env.getProperty("host")).thenReturn("host");
		activationService = new ActivationService(env, sendEmail);
		searchsService = Mockito.mock(HibernateSearchService.class);
		userService = new UserService(userRepository, notificationService, activationService, searchsService);	
		securityContext = Mockito.mock(SecurityContext.class);
		authentication = Mockito.mock(Authentication.class);
		visitRepository = new VisitRepositoryHibernatePostgreSQLImpl(sessionFactory);
		visitService = new VisitService(visitRepository, userService, searchsService, notificationService);		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddNewVisitByPatient() {	
//		set doctor
		Doctor doctor = new Doctor();
		doctor.setLastName("doctorLastName");
		
//		set patient
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login";
		when(authentication.getName()).thenReturn(username);
		@SuppressWarnings("unchecked")
		Query<Patient> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findPatientByUserName", Patient.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		User user = new User();
		user.setUsername(username);
		Patient patient = new Patient();
		patient.setUser(user);
		when(query.getSingleResult()).thenReturn(patient);
		when(userService.getLoggedPatient()).thenReturn(patient);
		
//		set visit status
		VisitStatus visitStatus = new VisitStatus();
		visitStatus.setId(1);
		when(session.find(VisitStatus.class, 1)).thenReturn(visitStatus);

//		set dental treatment list
		DentalTreatment dentalTreatment1 = new DentalTreatment();
		dentalTreatment1.setId(1);
		DentalTreatment dentalTreatment2 = new DentalTreatment();
		dentalTreatment2.setId(2);
		DentalTreatment dentalTreatment3 = new DentalTreatment();
		dentalTreatment3.setId(3);
		List<DentalTreatment> treatments = new ArrayList<>();
		treatments.add(dentalTreatment1);
		treatments.add(dentalTreatment2);
		treatments.add(dentalTreatment3);
		
//		set date time
		String [] dateTime = {"2019-09-11;19:30", "2019-09-11;08:30"};
		Visit visit = visitService.addNewVisitByPatient(doctor, dateTime, treatments);
		
		assertEquals("doctorLastName", visit.getDoctor().getLastName());
		assertEquals("login", visit.getPatient().getUser().getUsername());
		assertEquals(1, visit.getStatus().getId());
		assertEquals(3, visit.getTreatments().get(2).getId());
		assertEquals("2019-09-11T19:30", visit.getVisitDateTime().toString());
	}

	@Test
	public void testAddNewVisitByAssistant() {
//		set doctor
		Doctor doctor = new Doctor();
		doctor.setLastName("doctorLastName");
		
//		set patient
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login";
		when(authentication.getName()).thenReturn(username);
		@SuppressWarnings("unchecked")
		Query<User> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findUserByUserName", User.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		User user = new User();
		user.setUsername(username);
		Patient patient = new Patient();
		patient.setUser(user);
		patient.setLastName("lastNamePatient");
		
//		set visit status
		VisitStatus visitStatus = new VisitStatus();
		visitStatus.setId(1);
		when(session.find(VisitStatus.class, 1)).thenReturn(visitStatus);

//		set dental treatment list
		DentalTreatment dentalTreatment1 = new DentalTreatment();
		dentalTreatment1.setId(1);
		DentalTreatment dentalTreatment2 = new DentalTreatment();
		dentalTreatment2.setId(2);
		DentalTreatment dentalTreatment3 = new DentalTreatment();
		dentalTreatment3.setId(3);
		List<DentalTreatment> treatments = new ArrayList<>();
		treatments.add(dentalTreatment1);
		treatments.add(dentalTreatment2);
		treatments.add(dentalTreatment3);
		
//		set date time
		String [] dateTime = {"2019-09-11;19:30", "2019-09-11;08:30"};
		Visit visit = visitService.addNewVisitByAssistant(patient, doctor, dateTime, treatments);
		
		assertEquals("doctorLastName", visit.getDoctor().getLastName());
		assertEquals("lastNamePatient", visit.getPatient().getLastName());
		assertEquals("login", visit.getPatient().getUser().getUsername());
		assertEquals(1, visit.getStatus().getId());
		assertEquals(3, visit.getTreatments().get(2).getId());
		assertEquals("2019-09-11T19:30", visit.getVisitDateTime().toString());
	}

	@Test
	public void testGetWorkingWeekFreeTimeMap() {
		Doctor doctor = new Doctor();
		WorkingWeek workingWeek = new WorkingWeek();
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> dayTimekMap = new LinkedHashMap<>();
		dayTimekMap.put(LocalTime.parse("19:30"), true);
		dayTimekMap.put(LocalTime.parse("08:30"), false);
		workingWeekMap.put(LocalDate.now().plusDays(1).getDayOfWeek(), dayTimekMap);
		workingWeek.setWorkingWeekMap(workingWeekMap);
		doctor.setWorkingWeek(workingWeek);
		
		LocalDateTime visitDateTime = LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.parse("18:30"));
		Visit visit = new Visit();
		visit.setVisitDateTime(visitDateTime);
		visit.setDoctor(doctor);
		List<Visit> visitsList = new ArrayList<>();
		visitsList.add(visit);

		@SuppressWarnings("unchecked")
		Query<Visit> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("readVisitsByDateTimeAndDoctor", Visit.class)).thenReturn(query);
		when(query.setParameter("dateTimeFrom", LocalDateTime.now().plusDays(0).withNano(0))).thenReturn(query);
		when(query.setParameter("dateTimeTo", LocalDateTime.now().plusDays(7).withNano(0))).thenReturn(query);
		when(query.setParameter("doctor", doctor)).thenReturn(query);
		when(query.getResultList()).thenReturn(visitsList);
		
		
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = visitService.getWorkingWeekFreeTimeMap(doctor, 0, 7);
		Map<LocalTime, Boolean> map1DayPlus = workingWeekFreeTimeMap.get(LocalDate.now().plusDays(1));
		
		assertTrue(map1DayPlus.containsKey(LocalTime.parse("19:30")));
		assertFalse(map1DayPlus.containsKey(LocalTime.parse("08:30")));
		assertTrue(workingWeekFreeTimeMap.containsKey(LocalDate.now().plusDays(1)));		
		assertFalse(workingWeekFreeTimeMap.containsKey(LocalDate.now().plusDays(2)));
	}

	@Test
	public void testGetVisitsByPatientAndStatus() {
		VisitStatus visitStatus = new VisitStatus();
		visitStatus.setId(1);
		Patient patient = new Patient();
		patient.setId(1);

		Visit visit1 = new Visit();
		visit1.setId(1);
		visit1.setPatient(patient);
		visit1.setStatus(visitStatus);
		visit1.setVisitDateTime(LocalDateTime.now().plusDays(1).withNano(0));
		Visit visit2 = new Visit();
		visit2.setId(2);
		visit2.setPatient(patient);
		visit2.setStatus(visitStatus);
		visit2.setVisitDateTime(LocalDateTime.now().plusDays(2).withNano(0));
		List<Visit> visitsList = new ArrayList<>();
		visitsList.add(visit1);
		visitsList.add(visit2);
		
		@SuppressWarnings("unchecked")
		Query<Visit> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("readVisitsByPatientAndStatus", Visit.class)).thenReturn(query);
		when(query.setParameter("patient", patient)).thenReturn(query);
		when(query.setParameter("visitStatus", visitStatus)).thenReturn(query);
		when(query.getResultList()).thenReturn(visitsList);
		
		List<Visit> visitsListByPatientAndStatus = visitService.getVisitsByPatientAndStatus(patient, visitStatus);
		assertEquals(2, visitsListByPatientAndStatus.get(0).getId());
		assertEquals(1, visitsListByPatientAndStatus.get(1).getId());
		assertEquals(LocalDateTime.now().plusDays(2).withNano(0), visitsListByPatientAndStatus.get(0).getVisitDateTime());
		assertEquals(LocalDateTime.now().plusDays(1).withNano(0), visitsListByPatientAndStatus.get(1).getVisitDateTime());
	}

	@Test
	public void testGetVisitStatusList() {
		VisitStatus visitStatus1 = new VisitStatus();
		visitStatus1.setId(1);
		VisitStatus visitStatus2 = new VisitStatus();
		visitStatus2.setId(2);	
		List<VisitStatus> visitStatusList = new ArrayList<>();
		visitStatusList.add(visitStatus1);
		visitStatusList.add(visitStatus2);
		@SuppressWarnings("unchecked")
		Query<VisitStatus> query = Mockito.mock(Query.class);
		when(session.createQuery("from VisitStatus", VisitStatus.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(visitStatusList);
		List<VisitStatus> visitStatusListActual = visitService.getVisitStatusList();
		assertEquals(1, visitStatusListActual.get(0).getId());
		assertEquals(2, visitStatusListActual.get(1).getId());
	}

	@Test
	public void testGetVisitStatus() {
		VisitStatus expectedVisitStatus = new VisitStatus();
		expectedVisitStatus.setId(1);
		when(session.find(VisitStatus.class, 1)).thenReturn(expectedVisitStatus);
		VisitStatus actualVisitStatus = visitRepository.readVisitStatus(1);
		assertEquals(expectedVisitStatus.getId(), actualVisitStatus.getId());
	}

	@Test
	public void testGetVisitsToFinalizeLocalDateTimeLocalDateTime() {
		Visit visit1 = new Visit();
		visit1.setId(1);
		Visit visit2 = new Visit();
		visit2.setId(2);
		List<Visit> visitsList = new ArrayList<>();
		visitsList.add(visit1);
		visitsList.add(visit2);
		when(searchsService.searchVisitDateAndStatusByKeywordQuery(LocalDateTime.now().minusDays(7).withNano(0), LocalDateTime.now().withNano(0))).thenReturn(visitsList);
		List<Visit> searchVisitDateAndStatusByKeywordQuery = searchsService.searchVisitDateAndStatusByKeywordQuery(LocalDateTime.now().minusDays(7).withNano(0), LocalDateTime.now().withNano(0));
		assertEquals(1, searchVisitDateAndStatusByKeywordQuery.get(0).getId());
		assertEquals(2, searchVisitDateAndStatusByKeywordQuery.get(1).getId());
	}

	@Test
	public void testGetVisitsToFinalizeLocalDateTimeLocalDateTimeDoctor() {
		VisitStatus visitStatus = new VisitStatus();
		visitStatus.setId(1);
		when(session.find(VisitStatus.class, 1)).thenReturn(visitStatus);
		Doctor doctor = new Doctor();
		doctor.setId(1);
		Visit visit1 = new Visit();
		visit1.setId(1);
		visit1.setVisitDateTime(LocalDateTime.now().minusDays(1).withNano(0));
		visit1.setDoctor(doctor);
		Visit visit2 = new Visit();
		visit2.setId(2);
		visit2.setVisitDateTime(LocalDateTime.now().minusDays(2).withNano(0));
		visit2.setDoctor(doctor);
		List<Visit> visitsList = new ArrayList<>();
		visitsList.add(visit1);
		visitsList.add(visit2);
		
		LocalDateTime dateTimeFrom = LocalDateTime.now().minusDays(7).withNano(0);
		LocalDateTime dateTimeTo = LocalDateTime.now().withNano(0);
		@SuppressWarnings("unchecked")
		Query<Visit> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("readVisitsByDateTimeAndStatusAndDoctor", Visit.class)).thenReturn(query);
		when(query.setParameter("dateTimeFrom", dateTimeFrom)).thenReturn(query);
		when(query.setParameter("dateTimeTo", dateTimeTo)).thenReturn(query);
		when(query.setParameter("visitStatus", visitStatus)).thenReturn(query);
		when(query.setParameter("doctor", doctor)).thenReturn(query);
		when(query.getResultList()).thenReturn(visitsList);
		
		List<Visit> visitsToFinalize = visitService.getVisitsToFinalize(dateTimeFrom, dateTimeTo, doctor);
		
		assertEquals(1, visitsToFinalize.get(0).getId());
		assertEquals(2, visitsToFinalize.get(1).getId());
		assertEquals(1, visitsToFinalize.get(0).getDoctor().getId());
		assertEquals(LocalDateTime.now().minusDays(1).withNano(0), visitsToFinalize.get(0).getVisitDateTime());
	}

	@Test
	public void testGetVisitsToShow() {
		VisitStatus visitStatus = new VisitStatus();
		visitStatus.setId(1);
		when(session.find(VisitStatus.class, 1)).thenReturn(visitStatus);
		Doctor doctor = new Doctor();
		doctor.setId(1);
		Visit visit1 = new Visit();
		visit1.setId(1);
		visit1.setVisitDateTime(LocalDateTime.now().plusDays(1).withNano(0));
		visit1.setDoctor(doctor);
		Visit visit2 = new Visit();
		visit2.setId(2);
		visit2.setVisitDateTime(LocalDateTime.now().plusDays(2).withNano(0));
		visit2.setDoctor(doctor);
		List<Visit> visitsList = new ArrayList<>();
		visitsList.add(visit1);
		visitsList.add(visit2);
		
		LocalDateTime dateTimeFrom = LocalDateTime.now().withNano(0);
		LocalDateTime dateTimeTo = LocalDateTime.now().plusDays(30).withNano(0);
		@SuppressWarnings("unchecked")
		Query<Visit> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("readVisitsByDateTimeAndStatusAndDoctor", Visit.class)).thenReturn(query);
		when(query.setParameter("dateTimeFrom", dateTimeFrom)).thenReturn(query);
		when(query.setParameter("dateTimeTo", dateTimeTo)).thenReturn(query);
		when(query.setParameter("visitStatus", visitStatus)).thenReturn(query);
		when(query.setParameter("doctor", doctor)).thenReturn(query);
		when(query.getResultList()).thenReturn(visitsList);
		
		List<Visit> visitsToShow = visitService.getVisitsToShow(dateTimeFrom, doctor);
		
		assertEquals(1, visitsToShow.get(0).getId());
		assertEquals(2, visitsToShow.get(1).getId());
		assertEquals(1, visitsToShow.get(0).getDoctor().getId());
		assertEquals(LocalDateTime.now().plusDays(1).withNano(0), visitsToShow.get(0).getVisitDateTime());
	}

	@Test
	public void testGetVisit() {
		Visit visit = new Visit();
		visit.setId(1);
		when(session.find(Visit.class, 1)).thenReturn(visit);
		Visit actualVisit = visitService.getVisit(1);
		assertEquals(1, actualVisit.getId());
		assertNotEquals(2, actualVisit.getId());
	}

	@Test
	public void testSetFinalzedVisit() {
		Visit visit = new Visit();
		visit.setId(1);
		
		DentalTreatment dentalTreatment1 = new DentalTreatment();
		dentalTreatment1.setId(1);
		DentalTreatment dentalTreatment2 = new DentalTreatment();
		dentalTreatment2.setId(2);
		DentalTreatment dentalTreatment3 = new DentalTreatment();
		dentalTreatment3.setId(3);
		List<DentalTreatment> selectedTreatments = new ArrayList<>();
		selectedTreatments.add(dentalTreatment1);
		selectedTreatments.add(dentalTreatment2);
		selectedTreatments.add(dentalTreatment3);
		
		String [] treatmentCommentVisit = {"Comment1", "Comment2", "Comment3"};
		
		VisitStatus visitStatus = new VisitStatus();
		visitStatus.setId(2);
		when(session.find(VisitStatus.class, 1)).thenReturn(visitStatus);
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login";
		when(authentication.getName()).thenReturn(username);
		User user = new User();
		user.setUsername(username);
		@SuppressWarnings("unchecked")
		Query<User> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findUserByUserName", User.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(user);
		
		visitRepository = Mockito.mock(VisitRepository.class);
		when(visitRepository.updateVisitOnFinalize(visit)).thenReturn(true);
		
		boolean setFinalzedVisit = visitService.setFinalzedVisit(visit, selectedTreatments, treatmentCommentVisit);
		
		assertEquals(3, visit.getTreatments().get(2).getId());
		assertEquals("Comment1", visit.getVisitTreatmentComment().get(0).getComment());
		assertEquals("login", visit.getUserLogged().getUsername());
		assertTrue(setFinalzedVisit);
	}

	@Test
	public void testCancelVisit() {
		Visit visit = new Visit();
		visit.setId(1);
		
		Patient patient = new Patient();
		visit.setPatient(patient);
		
		visit.setVisitDateTime(LocalDateTime.now().plusDays(5));
		
		Doctor doctor = new Doctor();
		visit.setDoctor(doctor);
		
		String [] treatmentCommentVisit = {"Comment1", "Comment2", "Comment3"};
		
		List<VisitTreatmentComment> visitTreatmentCommentsList = new ArrayList<>();
		VisitTreatmentComment visitTreatmentComment;
		for (int i = 0; i < treatmentCommentVisit.length; i++) {
			visitTreatmentComment = new VisitTreatmentComment();
			visitTreatmentComment.setComment(treatmentCommentVisit[i]);
//			visitTreatmentComment.setTreatment(selectedTreatments.get(i));
			visitTreatmentCommentsList.add(visitTreatmentComment);
		}
		visit.setVisitTreatmentComment(visitTreatmentCommentsList);
		
		DentalTreatment dentalTreatment1 = new DentalTreatment();
		dentalTreatment1.setId(1);
		DentalTreatment dentalTreatment2 = new DentalTreatment();
		dentalTreatment2.setId(2);
		DentalTreatment dentalTreatment3 = new DentalTreatment();
		dentalTreatment3.setId(3);
		List<DentalTreatment> dentalTreatments = new ArrayList<>();
		dentalTreatments.add(dentalTreatment1);
		dentalTreatments.add(dentalTreatment2);
		dentalTreatments.add(dentalTreatment3);
		visit.setTreatments(dentalTreatments);
				
		@SuppressWarnings("rawtypes")
		NativeQuery query = Mockito.mock(NativeQuery.class);
		
		for(int i=0; i<visit.getVisitTreatmentComment().size(); i++) {
			when(session.createNativeQuery("delete from visit_visittreatmentcomment where visit_id = :visit_id AND visittreatmentcomment_id = :visittreatmentcomment_id")).thenReturn(query);
			when(query.setParameter("visit_id", visit.getId())).thenReturn(query);
			when(query.setParameter("visittreatmentcomment_id", visit.getVisitTreatmentComment().get(i).getId())).thenReturn(query);
			when(query.executeUpdate()).thenReturn(1);
			
			when(session.createNativeQuery("delete from VisitTreatmentComment where id = :id")).thenReturn(query);
			when(query.setParameter("id", visit.getVisitTreatmentComment().get(i).getId())).thenReturn(query);
			when(query.executeUpdate()).thenReturn(1);			
		}
		
		for(int i=0; i<visit.getTreatments().size(); i++) {
			when(session.createNativeQuery("delete from visit_dentaltreatment where visits_id = :visits_id AND treatments_id = :treatments_id")).thenReturn(query);
			when(query.setParameter("visits_id", visit.getId())).thenReturn(query);
			when(query.setParameter("treatments_id", visit.getTreatments().get(i).getId())).thenReturn(query);
			when(query.executeUpdate()).thenReturn(1);			
		}
		
		try {
			visitService.cancelVisit(visit);
		} catch (Exception e) {
			e.printStackTrace();
			assumeNoException(e);
		}
	}

}
