package unit.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

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
import org.springframework.security.crypto.password.PasswordEncoder;

import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.dao.UserRepositoryHibernatePostgreSQLImpl;
import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.service.ActivationService;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.NotificationService;
import pl.dentistoffice.service.SendEmail;
import pl.dentistoffice.service.UserService;

public class UserServiceTest {
	
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
	private PasswordEncoder passwordEncoder;

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
		passwordEncoder = Mockito.mock(PasswordEncoder.class);
		userService = new UserService(userRepository, notificationService, activationService, searchsService, passwordEncoder);	
		securityContext = Mockito.mock(SecurityContext.class);
		authentication = Mockito.mock(Authentication.class);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddNewDoctor() {
		User user = new User();
		user.setPasswordField("user");
		Doctor doctor = new Doctor();
		doctor.setUser(user);
		try {
			userService.addNewDoctor(doctor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(doctor.getRegisteredDateTime().compareTo(LocalDateTime.now().minusSeconds(2)), 1);
		assertNotNull(doctor.getUser().getPassword());
	}

	@Test
	public void testEditDoctor() {
		User user = new User();
		user.setId(13);
		user.setUsername("user1");
		Role role1 = new Role();
		role1.setId(1);
		Role role2 = new Role();
		role2.setId(5);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		user.setRoles(roles);
		user.setPasswordField("user1");
		Doctor doctor = new Doctor();
		doctor.setUser(user);
		try {
			userService.editDoctor(doctor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(doctor.getEditedDateTime().compareTo(LocalDateTime.now().minusSeconds(2)), 1);
		assertNotNull(doctor.getUser().getPassword());
	}

	@Test
	public void testGetDoctor() {
		Doctor doctorExpected = new Doctor();
		doctorExpected.setId(13);
		doctorExpected.setFirstName("doctor1");
		when(session.find(Doctor.class, 13)).thenReturn(doctorExpected);
		Doctor doctorActual = userService.getDoctor(13);
		assertEquals(doctorExpected.getFirstName(), doctorActual.getFirstName());
	}

	@Test
	public void testGetAllDoctors() {
		Doctor doctor1 = new Doctor();
		doctor1.setId(11);
		doctor1.setLastName("abc");
		Doctor doctor2 = new Doctor();
		doctor2.setId(12);
		doctor2.setLastName("bcd");
		List<Doctor> doctors = new ArrayList<>();
		doctors.add(doctor1);
		doctors.add(doctor2);
		@SuppressWarnings("unchecked")
		Query<Doctor> query = Mockito.mock(Query.class);
		when(session.createQuery("from Doctor", Doctor.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(doctors);
		List<Doctor> allDoctors = userService.getAllDoctors();
		assertEquals(allDoctors.get(0).getId(), 11);
		assertEquals(allDoctors.get(1).getId(), 12);
		assertNotEquals(allDoctors.get(0).getId(), 12);
	}

	@Test
	public void testGetLoggedDoctor() {
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login";
		when(authentication.getName()).thenReturn(username);
		@SuppressWarnings("unchecked")
		Query<Doctor> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findDoctorByUserName", Doctor.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		User user = new User();
		user.setUsername(username);
		Doctor doctor = new Doctor();
		doctor.setUser(user);
		when(query.getSingleResult()).thenReturn(doctor);
		Doctor loggedDoctor = userService.getLoggedDoctor();
		User userActual = loggedDoctor.getUser();
		assertTrue("login".equals(userActual.getUsername()));
	}

	@Test
	public void testAddNewAssistant() {
		User user = new User();
		user.setPasswordField("user");
		Assistant assistant = new Assistant();
		assistant.setUser(user);
		try {
			userService.addNewAssistant(assistant);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(assistant.getRegisteredDateTime().compareTo(LocalDateTime.now().minusSeconds(2)), 1);
		assertNotNull(assistant.getUser().getPassword());
	}

	@Test
	public void testEditAssistant() {
		User user = new User();
		user.setId(13);
		user.setUsername("user1");
		Role role1 = new Role();
		role1.setId(1);
		Role role2 = new Role();
		role2.setId(5);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		user.setRoles(roles);
		user.setPasswordField("user1");
		Assistant assistant = new Assistant();
		assistant.setUser(user);
		try {
			userService.editAssistant(assistant);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(assistant.getEditedDateTime().compareTo(LocalDateTime.now().minusSeconds(2)), 1);
		assertNotNull(assistant.getUser().getPassword());
	}

	@Test
	public void testGetAssistant() {
		Assistant assistantExpected = new Assistant();
		assistantExpected.setId(13);
		assistantExpected.setFirstName("assistant1");
		when(session.find(Assistant.class, 13)).thenReturn(assistantExpected);
		Assistant assistantActual = userService.getAssistant(13);
		assertEquals(assistantExpected.getFirstName(), assistantActual.getFirstName());
	}

	@Test
	public void testGetAllAssistants() {
		Assistant assistant1 = new Assistant();
		assistant1.setId(11);
		assistant1.setLastName("abc");
		Assistant assistant2 = new Assistant();
		assistant2.setId(12);
		assistant2.setLastName("bcd");
		List<Assistant> assistants = new ArrayList<>();
		assistants.add(assistant1);
		assistants.add(assistant2);
		@SuppressWarnings("unchecked")
		Query<Assistant> query = Mockito.mock(Query.class);
		when(session.createQuery("from Assistant", Assistant.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(assistants);
		List<Assistant> allAssistants = userService.getAllAssistants();
		assertEquals(allAssistants.get(0).getId(), 11);
		assertEquals(allAssistants.get(1).getId(), 12);
		assertNotEquals(allAssistants.get(0).getId(), 12);
	}

	@Test
	public void testGetLoggedAssistant() {
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login";
		when(authentication.getName()).thenReturn(username);
		@SuppressWarnings("unchecked")
		Query<Assistant> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findAssistantByUserName", Assistant.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		User user = new User();
		user.setUsername(username);
		Assistant assistant = new Assistant();
		assistant.setUser(user);
		when(query.getSingleResult()).thenReturn(assistant);
		Assistant loggedAssistant = userService.getLoggedAssistant();
		User userActual = loggedAssistant.getUser();
		assertTrue("login".equals(userActual.getUsername()));
	}

	@Test
	public void testAddNewPatient() {
		Role role1 = new Role();
		role1.setId(1);
		role1.setRole("ROLE_PATIENT");
		Role role2 = new Role();
		role2.setId(5);
		role2.setRole("ROLE_ADMIN");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		@SuppressWarnings("unchecked")
		Query<Role> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findRoleWithoutOwner", Role.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(roles);
		
		User user = new User();
		user.setPasswordField("user");
		user.setEnabled(true);
		Patient patient = new Patient();
		patient.setEmail("email");
		patient.setUser(user);
		String contextPath = "contextPath";
		try {
			userService.addNewPatient(patient, contextPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(patient.getRegisteredDateTime().compareTo(LocalDateTime.now().minusSeconds(2)), 1);
		assertNotNull(patient.getUser().getPassword());
		assertEquals(false, patient.getUser().isEnabled());
		assertNotNull(patient.getActivationString());
	}

	@Test
	public void testEditPatient() {
		User user = new User();
		user.setPasswordField("user1");
		Patient patient = new Patient();
		patient.setUser(user);
		try {
			userService.editPatient(patient);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(patient.getEditedDateTime().compareTo(LocalDateTime.now().minusSeconds(2)), 1);
		assertNotNull(patient.getUser().getPassword());
	}

	@Test
	public void testGetPatient() {
		Patient patientExpected = new Patient();
		patientExpected.setId(13);
		patientExpected.setFirstName("assistant1");
		when(session.find(Patient.class, 13)).thenReturn(patientExpected);
		Patient patientActual = userService.getPatient(13);
		assertEquals(patientExpected.getFirstName(), patientActual.getFirstName());
	}

	@Test
	public void testGetLoggedPatient() {
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
		Patient loggedPatient = userService.getLoggedPatient();
		User userActual = loggedPatient.getUser();
		assertTrue("login".equals(userActual.getUsername()));
	}

	@Test
	public void testSearchPatient() {
		Patient patient1 = new Patient();
		patient1.setId(11);
		patient1.setLastName("abc");
		Patient patient2 = new Patient();
		patient2.setId(12);
		patient2.setLastName("bcd");
		List<Patient> patients = new ArrayList<>();
		patients.add(patient1);
		patients.add(patient2);
		when(searchsService.searchPatientNamePeselStreetPhoneByKeywordQuery("text")).thenReturn(patients);
		List<Patient> searchedPatients = userService.searchPatient("text");
		assertEquals(searchedPatients.get(0).getId(), 11);
		assertEquals(searchedPatients.get(1).getId(), 12);
		assertNotEquals(searchedPatients.get(0).getId(), 12);
	}
	
	@Test
	public void testFindMobilePatientByToken() {
		@SuppressWarnings("unchecked")
		Query<Patient> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findPatientByToken", Patient.class)).thenReturn(query);
		when(query.setParameter("token", "token")).thenReturn(query);
		Patient patient = new Patient();
		patient.setToken("token");
		when(query.getSingleResult()).thenReturn(patient);	
		Patient findMobilePatientByToken = userService.findMobilePatientByToken("token");
		
		assertEquals(findMobilePatientByToken.getToken(), "token");
	}
	
	@Test
	public void testLoginMobilePatient() {
		String username = "login";
		@SuppressWarnings("unchecked")
		Query<Patient> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findPatientByUserName", Patient.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		User user = new User();
		user.setEnabled(true);
		user.setUsername(username);
		user.setPassword("encodedPassword");
		Patient patient = new Patient();
		patient.setUser(user);

		when(passwordEncoder.matches("rawPassword", patient.getUser().getPassword())).thenReturn(true);		
		when(query.getSingleResult()).thenReturn(patient);	
		Patient loginMobilePatient = userService.loginMobilePatient(username, "rawPassword");
		
		assertEquals(loginMobilePatient.getUser().getPassword(), "encodedPassword");
	}
	
	@Test
	public void testAddNewAdmin() {
		User user = new User();
		user.setPasswordField("user");
		Admin admin = new Admin();
		admin.setUser(user);
		try {
			userService.addNewAdmin(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(admin.getRegisteredDateTime().compareTo(LocalDateTime.now().minusSeconds(2)), 1);
		assertNotNull(admin.getUser().getPassword());
	}

	@Test
	public void testGetLoggedAdmin() {
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login";
		when(authentication.getName()).thenReturn(username);
		@SuppressWarnings("unchecked")
		Query<Admin> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findAdminByUserName", Admin.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		User user = new User();
		user.setUsername(username);
		Admin admin = new Admin();
		admin.setUser(user);
		when(query.getSingleResult()).thenReturn(admin);
		Admin loggedAdmin = userService.getLoggedAdmin();
		User userActual = loggedAdmin.getUser();
		assertTrue("login".equals(userActual.getUsername()));
	}

	@Test
	public void testGetAdmin() {
		Admin adminExpected = new Admin();
		adminExpected.setId(13);
		adminExpected.setFirstName("admin1");
		when(session.find(Admin.class, 13)).thenReturn(adminExpected);
		Admin adminActual = userService.getAdmin(13);
		assertEquals(adminExpected.getFirstName(), adminActual.getFirstName());
	}

	@Test
	public void testGetAllAdmins() {
		Admin admin1 = new Admin();
		admin1.setId(11);
		admin1.setLastName("abc");
		Admin admin2 = new Admin();
		admin2.setId(12);
		admin2.setLastName("bcd");
		List<Admin> admins = new ArrayList<>();
		admins.add(admin1);
		admins.add(admin2);
		@SuppressWarnings("unchecked")
		Query<Admin> query = Mockito.mock(Query.class);
		when(session.createQuery("from Admin", Admin.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(admins);
		List<Admin> allAdmins = userService.getAllAdmins();
		assertEquals(allAdmins.get(0).getId(), 11);
		assertEquals(allAdmins.get(1).getId(), 12);
		assertNotEquals(allAdmins.get(0).getId(), 12);
	}

	@Test
	public void testEditAdmin() {
		User user = new User();
		user.setId(13);
		user.setUsername("user1");
		Role role1 = new Role();
		role1.setId(1);
		Role role2 = new Role();
		role2.setId(5);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		user.setRoles(roles);
		user.setPasswordField("user1");
		Admin admin = new Admin();
		admin.setUser(user);
		try {
			userService.editAdmin(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(admin.getEditedDateTime().compareTo(LocalDateTime.now().minusSeconds(2)), 1);
		assertNotNull(admin.getUser().getPassword());
	}

	@Test
	public void testGetPatientRole() {
		Role role1 = new Role();
		role1.setId(1);
		role1.setRole("ROLE_PATIENT");
		Role role2 = new Role();
		role2.setId(5);
		role2.setRole("ROLE_ADMIN");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		@SuppressWarnings("unchecked")
		Query<Role> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findRoleWithoutOwner", Role.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(roles);
		List<Role> patientRole = userService.getPatientRole();
		assertEquals(1, patientRole.size());
		assertEquals("ROLE_PATIENT", patientRole.get(0).getRole());
	}

	@Test
	public void testGetAllRoles() {
		Role role1 = new Role();
		role1.setId(1);
		role1.setRole("ROLE_PATIENT");
		Role role2 = new Role();
		role2.setId(5);
		role2.setRole("ROLE_ADMIN");
		Role role3 = new Role();
		role3.setId(2);
		role3.setRole("ROLE_DOCTOR");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		roles.add(role3);
		@SuppressWarnings("unchecked")
		Query<Role> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findRoleWithoutOwner", Role.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(roles);
		List<Role> allRoles = userService.getAllRoles();
		assertEquals(3, allRoles.size());
		assertEquals(1, allRoles.get(0).getId());
		assertEquals(2, allRoles.get(1).getId());
		assertEquals(5, allRoles.get(2).getId());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetEmployeeRolesWithoutId() {
		Role role1 = new Role();
		role1.setId(3);
		role1.setRole("ROLE_PATIENT");
		Role role2 = new Role();
		role2.setId(5);
		role2.setRole("ROLE_ADMIN");
		Role role3 = new Role();
		role3.setId(2);
		role3.setRole("ROLE_DOCTOR");
		Role role4 = new Role();
		role4.setId(4);
		role4.setRole("ROLE_ASSISTANT");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		roles.add(role3);
		roles.add(role4);
		@SuppressWarnings("unchecked")
		Query<Role> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findRoleWithoutOwner", Role.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(roles);
		List<Role> allRoles = userService.getEmployeeRolesWithoutId(5);
		assertEquals(2, allRoles.size());
		assertEquals(2, allRoles.get(0).getId());
		assertEquals(4, allRoles.get(1).getId());
		assertNull("For role Admin - abowve", allRoles.get(2).getId());
	}

	@Test
	public void testGetTemplateWorkingWeekMap() {
		Map<DayOfWeek, Map<LocalTime, Boolean>> templateWorkingWeekMap = userService.getTemplateWorkingWeekMap();
		Object[] keyArray = templateWorkingWeekMap.keySet().toArray();
		assertEquals("MONDAY", keyArray[0].toString());
		Collection<Map<LocalTime, Boolean>> values = templateWorkingWeekMap.values();
		Iterator<Map<LocalTime, Boolean>> iterator = values.iterator();
		Map<LocalTime, Boolean> map = iterator.next();
		assertEquals("19:30", map.keySet().toArray()[23].toString());
	}

	@Test
	public void testDayOfWeekPolish() {
		String[] dayOfWeekPolish = userService.dayOfWeekPolish();
		assertEquals("Poniedziałek", dayOfWeekPolish[1]);
	}

	@Test
	public void testGetLoggedUser() {
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
		User loggedUser = userService.getLoggedUser();
		assertEquals("login", loggedUser.getUsername());
		assertNotEquals("login2", loggedUser.getUsername());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCheckDinstinctLoginWithRegisterUser() {
		Query<User> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findUserByUserName", User.class)).thenReturn(query);
		when(query.setParameter("username", "login")).thenReturn(query);
		when(query.getSingleResult()).thenThrow(NoResultException.class);
		assertTrue(userService.checkDinstinctLoginWithRegisterUser("login"));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCheckDinstinctLoginWithEditUser() {
		User user = new User();
		user.setId(13);
		user.setUsername("login");
		when(session.find(User.class, 13)).thenReturn(user);
		assertTrue("user is the same - login is distnct", userService.checkDinstinctLoginWithEditUser("login", 13));
		
		Query<User> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findUserByUserName", User.class)).thenReturn(query);
		when(query.setParameter("username", "login13")).thenReturn(query);
		when(query.getSingleResult()).thenThrow(NoResultException.class);
		assertTrue("changing login is distinct", userService.checkDinstinctLoginWithEditUser("login13", 13));
	}

	@Test
	public void testCreateCurrentRolesList() {
		User user = new User();
		user.setId(13);
		user.setUsername("user1");
		Role role1 = new Role();
		role1.setId(1);
		Role role2 = new Role();
		role2.setId(5);
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		user.setRoles(roles);			

		class InnerHelperClass extends UserService {
			List<Role> createCurrentRolesList;
			public List<Role>  expectedRoleList() {		
				createCurrentRolesList = super.createCurrentRolesList(user);
				return createCurrentRolesList;
			}
		}
		InnerHelperClass helperClass = new InnerHelperClass();
		List<Role> expectedRoles = helperClass.expectedRoleList();
		assertEquals(expectedRoles.get(0).getId(), 1);
		assertEquals(expectedRoles.get(1).getId(), 5);
	}
}

