package services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		fail("Not yet implemented");
	}

	@Test
	public void testGetVisitsByPatientAndStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVisitStatusList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVisitStatus() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVisitsToFinalizeLocalDateTimeLocalDateTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVisitsToFinalizeLocalDateTimeLocalDateTimeDoctor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVisitsToShow() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVisit() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetFinalzedVisit() {
		fail("Not yet implemented");
	}

	@Test
	public void testCancelVisit() {
		fail("Not yet implemented");
	}

}
