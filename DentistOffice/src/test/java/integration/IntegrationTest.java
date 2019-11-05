package integration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.AdminController;
import pl.dentistoffice.controller.AssistantController;
import pl.dentistoffice.controller.DentalTreatmentController;
import pl.dentistoffice.controller.DoctorController;
import pl.dentistoffice.controller.PatientController;
import pl.dentistoffice.controller.VisitController;
import pl.dentistoffice.dao.TreatmentRepository;
import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.dao.VisitRepository;
import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.service.ActivationService;
import pl.dentistoffice.service.CipherService;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.InitApplicationService;
import pl.dentistoffice.service.ReCaptchaService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestMvcConfig.class, TestRootConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest {
	
    @Autowired
    private WebApplicationContext wac;
	
	private MockMvc mockMvc;    
    private AdminController adminController;
    private AssistantController assistantController;
    private DoctorController doctorController;
    private PatientController patientController;
    private DentalTreatmentController dentalTreatmentController;
    private VisitController visitController;
    
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VisitService visitService;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private TreatmentRepository treatmentRepository;
    @Autowired
    private HibernateSearchService searchsService;
    @Autowired
    private InitApplicationService initApplicationService;
    @Autowired
    private ActivationService activationService;
    @Autowired
    private ReCaptchaService reCaptchaService;
    @Autowired
    private DentalTreatmentService dentalTreatmentService;
    @Autowired
    private CipherService cipherService;
	@Autowired
	private Environment env;
	@Mock
	private BindingResult result;
	@Mock
	private Model model;
	@Mock
	private MultipartFile photo;
	@Mock
	private HttpServletRequest servletRequest;
	@Mock
	private SecurityContextHolder securityContextHolder;
	@Mock
	private Authentication authentication;
	@Mock
	private SecurityContext securityContext;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");

        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void stageA_checkingServletContext() {
	    ServletContext servletContext = wac.getServletContext();
	     
	    assertNotNull(servletContext);
	    assertTrue(servletContext instanceof MockServletContext);
	    assertNotNull(wac.getBean("adminController"));
	}
	
	@Test
	public void stageB_testInitApplication() {
		List<Role> allRoles = userService.getAllRoles();
		assertEquals(5, allRoles.size());
		
		List<VisitStatus> readAllVisitStatus = visitRepository.readAllVisitStatus();
		assertEquals(2, readAllVisitStatus.size());
		
		List<DentalTreatment> readAllDentalTreatment = treatmentRepository.readAllDentalTreatment();
		assertEquals(1, readAllDentalTreatment.size());
		
		List<TreatmentCategory> readAllTreatmentCategory = treatmentRepository.readAllTreatmentCategory();
		assertEquals(1, readAllTreatmentCategory.size());
		
		List<User> readAllUsers = userRepository.readAllUsers();
		assertEquals(1, readAllUsers.size());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void stageC_testAdmin() throws Exception {
		Role role = new Role();
		role.setId(5);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		User user = new User();
		user.setUsername("userAdmin");
		user.setPasswordField("passwordField");
		user.setRoles(roles);
		Admin admin = new Admin();
		admin.setFirstName("firstName");
		admin.setLastName("lastName");
		admin.setPesel("92111757269");
		admin.setPhone("123456789");
		admin.setEmail("email@mail.com");
		admin.setRegisteredDateTime(LocalDateTime.now());
		admin.setUser(user);
		
		adminController = new AdminController(env, userService, searchsService, initApplicationService);
		photo = new MockMultipartFile("photo", "content".getBytes());
		assertEquals("forward:/message/employee/success", adminController.registrationAdminByOwner(admin, result, model, photo));
		
		List<Admin> allAdminsList = (List<Admin>) mockMvc.perform(get("/users/admin/owner/selectToEdit"))
														 .andExpect(MockMvcResultMatchers.status().isOk())
														 .andReturn()
														 .getRequest()
														 .getAttribute("allAdminsList");
		assertEquals("lastName", allAdminsList.get(0).getLastName());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), allAdminsList.get(0).getRegisteredDateTime().withNano(0).withSecond(0));
		assertEquals("content".getBytes()[3], allAdminsList.get(0).getPhoto()[3]);
		
		admin.setFirstName("firstName^");
		assertEquals("/users/admin/owner/register", adminController.registrationAdminByOwner(admin, result, model, photo));
		
		admin.setPhone("0987654321");
		admin.setFirstName("firstName");
		photo = new MockMultipartFile("photo", "".getBytes());
		assertEquals("forward:/message/employee/success", adminController.editAdminByOwner(admin, result, model, 2, photo, "image".getBytes()));
		
		allAdminsList = (List<Admin>) mockMvc.perform(get("/users/admin/owner/selectToEdit"))
											 .andExpect(MockMvcResultMatchers.status().isOk())
											 .andReturn()
											 .getRequest()
											 .getAttribute("allAdminsList");
		assertEquals("0987654321", allAdminsList.get(0).getPhone());
		assertEquals("image".getBytes()[3], allAdminsList.get(0).getPhoto()[3]);
		
		admin.setPesel("59011069029");
		assertEquals("forward:/message/employee/success", adminController.selfEditAdmin(admin, result, model, 2, photo, "2image".getBytes()));
		allAdminsList = (List<Admin>) mockMvc.perform(get("/users/admin/owner/selectToEdit"))
											 .andExpect(MockMvcResultMatchers.status().isOk())
											 .andReturn()
											 .getRequest()
											 .getAttribute("allAdminsList");
		assertEquals("59011069029", allAdminsList.get(0).getPesel());
		assertEquals("2image".getBytes()[3], allAdminsList.get(0).getPhoto()[3]);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void stageD_AssistantTest() throws Exception {
		Role role = new Role();
		role.setId(4);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		User user = new User();
		user.setUsername("userAssist");
		user.setPasswordField("passwordField");
		user.setRoles(roles);
		Assistant assistant = new Assistant();
		assistant.setFirstName("firstName");
		assistant.setLastName("lastName");
		assistant.setPesel("92111757269");
		assistant.setPhone("123456789");
		assistant.setEmail("email@mail.com");
		assistant.setRegisteredDateTime(LocalDateTime.now());
		assistant.setUser(user);
		
		assistantController = new AssistantController(env, userService);
		photo = new MockMultipartFile("photo", "content".getBytes());
		assertEquals("forward:/message/employee/success", assistantController.registrationAssistantByAdmin(assistant, result, model, photo));
		
		List<Assistant> allAssistantsList = (List<Assistant>) mockMvc.perform(get("/users/assistant/admin/selectToEdit"))
																	 .andExpect(MockMvcResultMatchers.status().isOk())
														 			 .andReturn()
														 			 .getRequest()
														 			 .getAttribute("allAssistantsList");
		assertEquals("lastName", allAssistantsList.get(0).getLastName());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), allAssistantsList.get(0).getRegisteredDateTime().withNano(0).withSecond(0));
		assertEquals("content".getBytes()[3], allAssistantsList.get(0).getPhoto()[3]);
		
		assistant.setFirstName("firstName^");
		assertEquals("/users/assistant/admin/register", assistantController.registrationAssistantByAdmin(assistant, result, model, photo));
		
		assistant.setPhone("0987654321");
		assistant.setFirstName("firstName");
		photo = new MockMultipartFile("photo", "".getBytes());
		assertEquals("forward:/message/employee/success", assistantController.editDataAssistantByAdmin(assistant, result, model, 3, photo, "image".getBytes()));
		
		allAssistantsList = (List<Assistant>) mockMvc.perform(get("/users/assistant/admin/selectToEdit"))
													 .andExpect(MockMvcResultMatchers.status().isOk())
													 .andReturn()
													 .getRequest()
													 .getAttribute("allAssistantsList");
		assertEquals("0987654321", allAssistantsList.get(0).getPhone());
		assertEquals("image".getBytes()[3], allAssistantsList.get(0).getPhoto()[3]);

		assistant.setPesel("59011069029");
		assertEquals("forward:/message/employee/success", assistantController.selfEditDataAssistant(assistant, result, model, 3, photo, "abcde".getBytes()));
		allAssistantsList = (List<Assistant>) mockMvc.perform(get("/users/assistant/admin/selectToEdit"))
													 .andExpect(MockMvcResultMatchers.status().isOk())
													 .andReturn()
													 .getRequest()
													 .getAttribute("allAssistantsList");
		assertEquals("59011069029", allAssistantsList.get(0).getPesel());
		assertEquals("abcde".getBytes()[3], allAssistantsList.get(0).getPhoto()[3]);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void stageE_DoctorTest() throws Exception {
		Role role = new Role();
		role.setId(2);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		User user = new User();
		user.setUsername("userDoctor");
		user.setPasswordField("passwordField");
		user.setRoles(roles);
		Doctor doctor= new Doctor();
		doctor.setFirstName("firstName");
		doctor.setLastName("lastName");
		doctor.setPesel("57091352420");
		doctor.setPhone("012345678900987");
		doctor.setEmail("email@mail.com");
		doctor.setRegisteredDateTime(LocalDateTime.now());
		doctor.setUser(user);	
		
		String [] mondayTime = {"08:00", "08:30", "09:30", "10:30", "11:30", "12:00", "13:00", "14:00", "18:30", "19:30"};
		String [] mondayTimeBool = {"true", "false", "true", "true", "false", "true", "true", "true", "false", "true"};
		String [] tuesdayTime = {"08:00", "08:30", "09:30", "10:30", "11:30", "12:00", "13:00", "14:00", "18:30", "19:30"};
		String [] tuesdayTimeBool = {"true", "false", "true", "true", "false", "true", "true", "true", "false", "true"};
		String [] wednesdayTime = {"08:00", "08:30", "09:30", "10:30", "11:30", "12:00", "13:00", "14:00", "18:30", "19:30"};
		String [] wednesdayTimeBool = {"true", "false", "true", "true", "false", "true", "true", "true", "false", "true"};
		String [] thursdayTime = {"08:00", "08:30", "09:30", "10:30", "11:30", "12:00", "13:00", "14:00", "18:30", "19:30"};
		String [] thursdayTimeBool = {"true", "false", "true", "true", "false", "true", "true", "true", "false", "true"};
		String [] fridayTime = {"08:00", "08:30", "09:30", "10:30", "11:30", "12:00", "13:00", "14:00", "18:30", "19:30"};
		String [] fridayTimeBool = {"true", "false", "true", "true", "false", "true", "true", "true", "false", "true"};
		String [] saturdayTime = {"08:00", "08:30", "09:30", "10:30", "11:30", "12:00", "13:00", "14:00", "18:30", "19:30"};
		String [] saturdayTimeBool = {"true", "false", "true", "true", "false", "true", "true", "true", "false", "true"};
		String [] sundayTime = {"08:00", "08:30", "09:30", "10:30", "11:30", "12:00", "13:00", "14:00", "18:30", "19:30"};
		String [] sundayTimeBool = {"true", "false", "true", "true", "false", "true", "true", "true", "false", "true"};
		
		doctorController = new DoctorController(env, userService, visitService, searchsService);
		photo = new MockMultipartFile("photo", "content".getBytes());
		assertEquals("forward:/message/employee/success", doctorController.registerDoctor(doctor, result, model, photo, mondayTime, mondayTimeBool, 
																			tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, 
																			thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, 
																			saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
		
		List<Doctor> allDoctorsList = (List<Doctor>) mockMvc.perform(get("/users/doctor/admin/selectToEdit"))
															.andExpect(MockMvcResultMatchers.status().isOk())
														 	.andReturn()
														 	.getRequest()
														 	.getAttribute("allDoctorsList");
		assertEquals("lastName", allDoctorsList.get(0).getLastName());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), allDoctorsList.get(0).getRegisteredDateTime().withNano(0).withSecond(0));
		assertEquals("content".getBytes()[3], allDoctorsList.get(0).getPhoto()[3]);
		
		doctor.setFirstName("firstName^");
		assertEquals("/users/doctor/admin/register", doctorController.registerDoctor(doctor, result, model, photo, mondayTime, mondayTimeBool, 
																		tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, 
																		thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, 
																		saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
		
		doctor.setPhone("0987654321");
		doctor.setFirstName("firstName");
		photo = new MockMultipartFile("photo", "".getBytes());
		assertEquals("forward:/message/employee/success", doctorController.editDataDoctor(doctor, result, model, 4, photo, "image".getBytes(),
														  mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, 
														  thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, 
														  sundayTime, sundayTimeBool));
		
		allDoctorsList = (List<Doctor>) mockMvc.perform(get("/users/doctor/admin/selectToEdit"))
												.andExpect(MockMvcResultMatchers.status().isOk())
											 	.andReturn()
											 	.getRequest()
											 	.getAttribute("allDoctorsList");
		assertEquals("0987654321", allDoctorsList.get(0).getPhone());
		assertEquals("image".getBytes()[3], allDoctorsList.get(0).getPhoto()[3]);

		doctor.setPesel("59011069029");
		assertEquals("forward:/message/employee/success", doctorController.selfEditDataDoctor(doctor, result, model, 4, photo, "3image".getBytes(),
														  mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, 
														  thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, 
														  sundayTime, sundayTimeBool));
				
		allDoctorsList = (List<Doctor>) mockMvc.perform(get("/users/doctor/admin/selectToEdit"))
												.andExpect(MockMvcResultMatchers.status().isOk())
											 	.andReturn()
											 	.getRequest()
											 	.getAttribute("allDoctorsList");
		assertEquals("59011069029", allDoctorsList.get(0).getPesel());
		assertEquals("3image".getBytes()[3], allDoctorsList.get(0).getPhoto()[3]);
	}
	
	@Test
	public void stageF_PatientTestRegisterAndEditByAssistant() throws Exception {
		Role role = new Role();
		role.setId(3);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		User user = new User();
		user.setUsername("userPatient1");
		user.setPasswordField("passwordField");
		user.setRoles(roles);
		Patient patient = new Patient();
		patient.setFirstName("firstName");
		patient.setLastName("lastName");
		patient.setPesel("62010856280");
		patient.setPhone("33333333333333");
		patient.setEmail("patient@mail.com");
		patient.setCountry("country");
		patient.setZipCode("00-001");
		patient.setCity("city");
		patient.setStreet("street");
		patient.setStreetNo("1A");
		patient.setUnitNo("22");
		patient.setRegisteredDateTime(LocalDateTime.now());
		patient.setUser(user);
		
		patientController = new PatientController(env, userService, visitService, activationService, reCaptchaService, cipherService);
		photo = new MockMultipartFile("photo", "content".getBytes());
		assertEquals("forward:/message/employee/success", patientController.registrationPatientByAssistant(patient, result, model, photo, servletRequest));
		
		Patient patientActual = (Patient) mockMvc.perform(post("/users/patient/assistant/selectedPatientToEdit")
												 .param("patientId", "1"))
												 .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
												 .andReturn()
												 .getFlashMap()
												 .get("patient");
		assertEquals("lastName", patientActual.getLastName());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), patientActual.getRegisteredDateTime().withNano(0).withSecond(0));
		assertEquals("content".getBytes()[5], patientActual.getPhoto()[5]);
		
		patient.setZipCode("");
		assertEquals("/users/patient/assistant/register", patientController.registrationPatientByAssistant(patient, result, model, photo, servletRequest));
	
		patient.setPhone("44444444444444444");
		patient.setZipCode("00-001");
		photo = new MockMultipartFile("photo", "".getBytes());
		assertEquals("forward:/message/employee/success", patientController.editDataPatientByAssistant(patient, result, model, 5, photo, "5image".getBytes()));
		
		patientActual = (Patient) mockMvc.perform(post("/users/patient/assistant/selectedPatientToEdit")
										 .param("patientId", "1"))
										 .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
										 .andReturn()
										 .getFlashMap()
										 .get("patient");
		assertEquals("44444444444444444", patientActual.getPhone());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), patientActual.getEditedDateTime().withNano(0).withSecond(0));
		assertEquals("5image".getBytes()[5], patientActual.getPhoto()[5]);
	}
	
	@Test
	public void stageG_PatientTestSelfRegisterAndEdit() throws Exception {
		Role role = new Role();
		role.setId(3);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		User user = new User();
		user.setUsername("userPatient2");
		user.setPasswordField("passwordField");
		user.setRoles(roles);
		Patient patient = new Patient();
		patient.setFirstName("firstName2");
		patient.setLastName("lastName2");
		patient.setPesel("62010856280");
		patient.setPhone("44444444444444444");
		patient.setEmail("patient@mail.com");
		patient.setCountry("country");
		patient.setZipCode("00-001");
		patient.setCity("city");
		patient.setStreet("street");
		patient.setStreetNo("1A");
		patient.setUnitNo("22");
		patient.setRegisteredDateTime(LocalDateTime.now());
		patient.setUser(user);

		patientController = new PatientController(env, userService, visitService, activationService, reCaptchaService, cipherService);
		photo = new MockMultipartFile("photo", "6photo".getBytes());
		user.setUsername("userPatient2");
		patient.setUser(user);
		when(reCaptchaService.verify("reCaptchaResponse")).thenReturn(true);
		assertEquals("forward:/message/patient/success", patientController.selfRegistrationPatient(patient, result, model, photo, servletRequest, "reCaptchaResponse"));
		
		Patient patientActual = (Patient) mockMvc.perform(post("/users/patient/assistant/selectedPatientToEdit")
												.param("patientId", "2"))
												.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
												.andReturn()
												.getFlashMap()
												.get("patient");
		assertEquals("44444444444444444", patientActual.getPhone());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), patientActual.getRegisteredDateTime().withNano(0).withSecond(0));
		assertEquals("6photo".getBytes()[5], patientActual.getPhoto()[5]);
		
		patient.setPesel("");
		assertEquals("/users/patient/register", patientController.selfRegistrationPatient(patient, result, model, photo, servletRequest, "reCaptchaResponse"));
		
		patient.setPesel("88112511092");
		photo = new MockMultipartFile("photo", "".getBytes());
		assertEquals("forward:/message/patient/success", patientController.selfEditDataPatient(patient, result, model, 6, photo, "6image".getBytes()));
		
		patientActual = (Patient) mockMvc.perform(post("/users/patient/assistant/selectedPatientToEdit")
										.param("patientId", "2"))
										.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
										.andReturn()
										.getFlashMap()
										.get("patient");
		assertEquals("88112511092", patientActual.getPesel());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), patientActual.getEditedDateTime().withNano(0).withSecond(0));
		assertEquals("6image".getBytes()[3], patientActual.getPhoto()[3]);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	
	public void stageH_TreatmentCategoryTest() throws Exception {
		TreatmentCategory treatmentCategory1 = new TreatmentCategory();
		treatmentCategory1.setCategoryName("categoryName1");
		TreatmentCategory treatmentCategory2 = new TreatmentCategory();
		treatmentCategory2.setCategoryName("categoryName2");
		dentalTreatmentController = new DentalTreatmentController(env, dentalTreatmentService);
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getName()).thenReturn("userAdmin");
		
		assertEquals("forward:/message/employee/success", dentalTreatmentController.addTreatmentCategory(treatmentCategory1, result, model));
		
		List<TreatmentCategory> treatmentCategoriesListActual = (List<TreatmentCategory>) mockMvc
								.perform(MockMvcRequestBuilders.get("/control/addTreatment"))
								.andExpect(MockMvcResultMatchers.status().isOk())
								.andReturn()
								.getRequest()
								.getAttribute("treatmentCategoriesList");
		assertEquals("categoryName1", treatmentCategoriesListActual.get(1).getCategoryName());
		
		assertEquals("forward:/message/employee/success", dentalTreatmentController.addTreatmentCategory(treatmentCategory2, result, model));
		treatmentCategory2.setId(3);
		treatmentCategory2.setCategoryName("categoryName33");
		assertEquals("forward:/message/employee/success", dentalTreatmentController.editTreatmentCategory(treatmentCategory2, result, model));
		treatmentCategoriesListActual = (List<TreatmentCategory>) mockMvc
								.perform(MockMvcRequestBuilders.get("/control/addTreatment"))
								.andExpect(MockMvcResultMatchers.status().isOk())
								.andReturn()
								.getRequest()
								.getAttribute("treatmentCategoriesList");
		assertEquals("categoryName33", treatmentCategoriesListActual.get(2).getCategoryName());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), treatmentCategory2.getEditedDateTime().withNano(0).withSecond(0));
		
		treatmentCategory1.setCategoryName("categoryName^");
		when(result.hasErrors()).thenReturn(true); //when categoryName include forbidden char, then result has't error in this case
		assertEquals("/control/addTreatmentCategory", dentalTreatmentController.addTreatmentCategory(treatmentCategory1, result, model));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void stageI_DentalTreatmentTest() throws Exception {
		TreatmentCategory treatmentCategory1 = new TreatmentCategory();
		treatmentCategory1.setId(2);
		treatmentCategory1.setCategoryName("categoryName1");
		TreatmentCategory treatmentCategory2 = new TreatmentCategory();
		treatmentCategory2.setId(3);
		treatmentCategory2.setCategoryName("categoryName2");
		List<TreatmentCategory> treatmentCategoriesList = new ArrayList<>();
		treatmentCategoriesList.add(treatmentCategory1);
		treatmentCategoriesList.add(treatmentCategory2);
		DentalTreatment dentalTreatment1 = new DentalTreatment();
		dentalTreatment1.setName("name");
		dentalTreatment1.setDescription("description");
		dentalTreatment1.setPrice(11.22);
		dentalTreatment1.setTreatmentCategory(treatmentCategoriesList);	
		dentalTreatmentController = new DentalTreatmentController(env, dentalTreatmentService);
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getName()).thenReturn("userAdmin");

		assertEquals("forward:/message/employee/success", dentalTreatmentController.addDentalTreatment(dentalTreatment1, result, model));
		
		List<DentalTreatment> searchedTreatmentList = (List<DentalTreatment>) mockMvc
								.perform(MockMvcRequestBuilders.post("/control/searchResult")
								.param("treatmentData", "description"))
								.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
								.andReturn()
								.getFlashMap()
								.get("searchedTreatmentList");
		assertEquals("description", searchedTreatmentList.get(0).getDescription());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), searchedTreatmentList.get(0).getRegisteredDateTime().withNano(0).withSecond(0));
		
		dentalTreatment1.setName("name123");
		assertEquals("forward:/message/employee/success", dentalTreatmentController.editDentalTreatment(dentalTreatment1, result, model));
		
		searchedTreatmentList = (List<DentalTreatment>) mockMvc
								.perform(MockMvcRequestBuilders.post("/control/searchResult")
								.param("treatmentData", "name12"))
								.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
								.andReturn()
								.getFlashMap()
								.get("searchedTreatmentList");
		assertEquals("description", searchedTreatmentList.get(0).getDescription());
		
		dentalTreatment1.setName("name^");
		when(result.hasErrors()).thenReturn(true); //when categoryName include forbidden char, then result has't error in this case
		assertEquals("/control/addTreatment", dentalTreatmentController.addDentalTreatment(dentalTreatment1, result, model));
	}
	
	@Test
	public void stageJ_VisitTest() throws Exception {
		visitController = new VisitController(env, visitService, userService, dentalTreatmentService);
		Doctor doctor = new Doctor();
		doctor.setId(1);
		Patient patient = new Patient();
		patient.setId(1);
		patient.setFirstName("firstName");
		String [] dateTime = {LocalDate.now().plusDays(3).toString()+";19:30"};
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(authentication.getName()).thenReturn("userPatient1");
		
		assertEquals("forward:/message/patient/success", visitController.visitReservationByPatient(doctor, dateTime, "1", "2", "1", model));
		
		Visit visitActual = (Visit) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/visitToFinalize")
											.param("visitId", "1"))
											.andExpect(MockMvcResultMatchers.status().isOk())
											.andReturn()
											.getRequest()
											.getAttribute("visit");
		assertEquals(1, visitActual.getDoctor().getId());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), visitActual.getReservationDateTime().withNano(0).withSecond(0));

		when(authentication.getName()).thenReturn("userAssist");
		assertEquals("forward:/message/employee/success", visitController.visitReservationByAssistant(doctor, patient, dateTime, "1", "2", "1", model));
		visitActual = (Visit) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/visitToFinalize")
											.param("visitId", "2"))
											.andExpect(MockMvcResultMatchers.status().isOk())
											.andReturn()
											.getRequest()
											.getAttribute("visit");
		assertEquals("firstName", visitActual.getPatient().getFirstName());
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), visitActual.getReservationDateTime().withNano(0).withSecond(0));
		
		assertEquals("forward:/message/employee/success", visitController.saveFinalizedVisitByAssistant(visitActual, "", "treatmentCommentVisit2", 
																														"", "1", "2", "1", model));
		visitActual = (Visit) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/visitToFinalize")
											.param("visitId", "2"))
											.andExpect(MockMvcResultMatchers.status().isOk())
											.andReturn()
											.getRequest()
											.getAttribute("visit");
		assertEquals(LocalDateTime.now().withNano(0).withSecond(0), visitActual.getFinalizedVisitDateTime().withNano(0).withSecond(0));
		
		when(authentication.getName()).thenReturn("userPatient1");
		assertEquals("forward:/message/patient/success", visitController.removeVisitByPatient("1", model));
		visitActual = (Visit) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/visitToFinalize")
											.param("visitId", "1"))
											.andExpect(MockMvcResultMatchers.status().isOk())
											.andReturn()
											.getRequest()
											.getAttribute("visit");
		assertNull(visitActual);
		
		when(authentication.getName()).thenReturn("userAssist");
		assertEquals("forward:/message/employee/success", visitController.removeVisitByAssistant("2", model));
		visitActual = (Visit) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/visitToFinalize")
											.param("visitId", "2"))
											.andExpect(MockMvcResultMatchers.status().isOk())
											.andReturn()
											.getRequest()
											.getAttribute("visit");
		assertNull(visitActual);
	}
}
