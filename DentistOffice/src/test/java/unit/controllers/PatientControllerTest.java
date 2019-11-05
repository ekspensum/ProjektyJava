package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.PatientController;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.service.ActivationService;
import pl.dentistoffice.service.CipherService;
import pl.dentistoffice.service.ReCaptchaService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

public class PatientControllerTest {

	private MockMvc mockMvc;
	private PatientController patientController;
	
	@Mock
	private Environment env;
	@Mock
	private UserService userService;
	@Mock
	private VisitService visitService;
	@Mock
	private ActivationService activationService;
	@Mock
	private ReCaptchaService reCaptchaService;
	@Mock
	private CipherService cipherService;
	@Mock
	private Model model;
	@Mock
	private BindingResult result;
	@Mock
	private RedirectAttributes redirectAttributes;
	@Mock
	private HttpServletRequest servletRequest;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp"); 
        
        patientController = new PatientController(env, userService, visitService, activationService, reCaptchaService, cipherService);
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testPatientPanel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/panels/patientPanel"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("patientPanel"));
	}

	@Test
	public void testSearchPatientByAssistant() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/patient/assistant/searchPatient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/assistant/searchPatient"));
	}

	@Test
	public void testSearchPatientByAssistantStringRedirectAttributes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/users/patient/assistant/searchResult")
				.param("patientData", "patientData"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/users/patient/assistant/selectPatient"));
		
		Patient patient = new Patient();
		patient.setId(33);
		List<Patient> patientsList = new ArrayList<>();
		patientsList.add(patient);
		String patientData = "patientDataNumberCharsAbove_20";
		when(userService.searchPatient(patientData.substring(0, 20))).thenReturn(patientsList);
		@SuppressWarnings("unchecked")
		List<Patient> searchedPatientList = (List<Patient>) mockMvc
				.perform(MockMvcRequestBuilders.post("/users/patient/assistant/searchResult")
				.param("patientData", patientData))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/users/patient/assistant/selectPatient"))
				.andReturn().getFlashMap().get("searchedPatientList");
		assertEquals(33, searchedPatientList.get(0).getId());
	}

	@Test
	public void testSelectPatientToEditByAssistant() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/patient/assistant/selectPatient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/assistant/selectPatient"));
	}

	@Test
	public void testRegistrationPatientByAssistantModel() throws Exception {
		Patient patient = (Patient) mockMvc.perform(MockMvcRequestBuilders.get("/users/patient/assistant/register"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/assistant/register"))
				.andReturn().getRequest().getAttribute("patient");
		assertNotNull(patient);
	}

	@Test
	public void testRegistrationPatientByAssistantPatientBindingResultModelMultipartFileHttpServletRequest() throws Exception {
		Patient patient = new Patient();
		User user = new User();
		user.setUsername("username");
		patient.setUser(user);		
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/patient/assistant/register")
				.file(photo)
				.sessionAttr("patient", patient))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/patient/assistant/register"));
		
		assertEquals("forward:/message/employee/success", patientController.registrationPatientByAssistant(patient, result, model, photo, servletRequest));
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(false);
		assertEquals("/users/patient/assistant/register", patientController.registrationPatientByAssistant(patient, result, model, photo, servletRequest));
		
		when(servletRequest.getContextPath()).thenReturn("contextPath");
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(true);	
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).addNewPatient(patient, "contextPath");
		assertEquals("forward:/message/patient/error", patientController.registrationPatientByAssistant(patient, result, model, photo, servletRequest));
	}

	@Test
	public void testSelectPatientToEditByAssistantStringRedirectAttributes() throws Exception {
		User user = new User();
		user.setId(77);
		Patient patient = new Patient();
		patient.setId(66);
		patient.setUser(user);
		when(userService.getPatient(77)).thenReturn(patient);
		Patient patientActual = (Patient) mockMvc.perform(MockMvcRequestBuilders.post("/users/patient/assistant/selectedPatientToEdit")
				.param("patientId", "77"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/users/patient/assistant/edit"))
				.andReturn().getFlashMap().get("patient");
		assertEquals(66, patientActual.getId());
	}

	@Test
	public void testEditPatientByAssistant() throws Exception {
		Patient patient = new Patient();
		patient.setPhoto("photo".getBytes());
		byte [] image = (byte[]) mockMvc.perform(MockMvcRequestBuilders.get("/users/patient/assistant/edit")
				.sessionAttr("patient", patient))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/assistant/edit"))
				.andReturn().getRequest().getAttribute("image");
		assertEquals(patient.getPhoto(), image);
	}

	@Test
	public void testEditDataPatientByAssistant() throws Exception {
		Patient patient = new Patient();
		User user = new User();
		user.setId(22);
		user.setUsername("username");
		patient.setUser(user);		
		when(userService.checkDinstinctLoginWithEditUser("username", user.getId())).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/patient/assistant/edit")
				.file(photo)
				.sessionAttr("patient", patient)
				.sessionAttr("editUserId", user.getId())
				.sessionAttr("image", "image".getBytes()))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/patient/assistant/edit"));
		
		assertEquals("forward:/message/employee/success", patientController.editDataPatientByAssistant(patient, result, model, user.getId(), photo, "".getBytes()));
		when(userService.checkDinstinctLoginWithEditUser("username", user.getId())).thenReturn(false);
		assertEquals("/users/patient/assistant/edit", patientController.editDataPatientByAssistant(patient, result, model, user.getId(), photo, "".getBytes()));
		
		photo = new MockMultipartFile("photo", "".getBytes());
		when(userService.checkDinstinctLoginWithEditUser("username", user.getId())).thenReturn(true);
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).editPatient(patient);
		assertEquals("forward:/message/employee/error", patientController.editDataPatientByAssistant(patient, result, model, user.getId(), photo, "image".getBytes()));
		
		assertEquals("image".getBytes()[2], patient.getPhoto()[2]);
	}

	@Test
	public void testSelectPatientForBrowseByAssistant() throws Exception {
		User user = new User();
		user.setId(77);
		Patient patient = new Patient();
		patient.setId(66);
		patient.setUser(user);
		when(userService.getPatient(77)).thenReturn(patient);
		Patient patientActual = (Patient) mockMvc.perform(MockMvcRequestBuilders.post("/users/patient/assistant/selectedPatientToEdit")
				.param("patientId", "77"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/users/patient/assistant/edit"))
				.andReturn().getFlashMap().get("patient");
		assertEquals(66, patientActual.getId());
	}

	@Test
	public void testShowPatientForBrowseByAssistantPatientModel() throws Exception {
		Patient patient = new Patient();
		patient.setId(77);
		VisitStatus defaultVisitStatus = new VisitStatus();
		defaultVisitStatus.setId(2);
		when(visitService.getVisitStatus(2)).thenReturn(defaultVisitStatus);
		
		VisitStatus visitStatusActual = (VisitStatus) mockMvc.perform(MockMvcRequestBuilders.get("/users/patient/assistant/showPatient")
				.sessionAttr("patient", patient))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/assistant/showPatient"))
				.andReturn().getRequest().getAttribute("defaultVisitStatus");
		assertEquals(2, visitStatusActual.getId());
		
		Patient patientActual = (Patient) mockMvc.perform(MockMvcRequestBuilders.get("/users/patient/assistant/showPatient")
				.sessionAttr("patient", patient))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/assistant/showPatient"))
				.andReturn().getRequest().getAttribute("patient");
		assertEquals(77, patientActual.getId());
	}

	@Test
	public void testShowPatientForBrowseByAssistantPatientStringModel() throws Exception {
		Patient patient = new Patient();
		patient.setId(77);
		VisitStatus visitStatus = new VisitStatus();
		visitStatus.setId(2);
		when(visitService.getVisitStatus(2)).thenReturn(visitStatus);
		
		VisitStatus visitStatusActual = (VisitStatus) mockMvc.perform(MockMvcRequestBuilders.post("/users/patient/assistant/showPatient")
				.sessionAttr("patient", patient)
				.param("statusId", "2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/assistant/showPatient"))
				.andReturn().getRequest().getAttribute("actualVisitStatus");
		assertEquals(2, visitStatusActual.getId());
		
		Patient patientActual = (Patient) mockMvc.perform(MockMvcRequestBuilders.post("/users/patient/assistant/showPatient")
				.sessionAttr("patient", patient)
				.param("statusId", "2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/assistant/showPatient"))
				.andReturn().getRequest().getAttribute("patient");
		assertEquals(77, patientActual.getId());
	}

	@Test
	public void testSelfRegistrationPatientModel() throws Exception {
		Patient patient = (Patient) mockMvc.perform(MockMvcRequestBuilders.get("/users/patient/register"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/register"))
				.andReturn().getRequest().getAttribute("patient");
		assertNotNull(patient);
	}

	@Test
	public void testSelfRegistrationPatientPatientBindingResultModelMultipartFileHttpServletRequestString() throws Exception {
		Patient patient = new Patient();
		User user = new User();
		user.setUsername("username");
		patient.setUser(user);		
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		String reCaptchaResponse = "reCaptchaResponse";
		when(reCaptchaService.verify(reCaptchaResponse)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/patient/register")
				.file(photo)
				.sessionAttr("patient", patient)
				.param("g-recaptcha-response", reCaptchaResponse))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/patient/register"));
		
		assertEquals("forward:/message/patient/success", patientController.selfRegistrationPatient(patient, result, model, photo, servletRequest, reCaptchaResponse));
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(false);
		assertEquals("/users/patient/register", patientController.selfRegistrationPatient(patient, result, model, photo, servletRequest, reCaptchaResponse));
		
		when(servletRequest.getContextPath()).thenReturn("contextPath");
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(true);	
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).addNewPatient(patient, "contextPath");
		assertEquals("forward:/message/patient/error", patientController.selfRegistrationPatient(patient, result, model, photo, servletRequest, reCaptchaResponse));
		
		when(reCaptchaService.verify(reCaptchaResponse)).thenReturn(false);
		assertEquals("/users/patient/register", patientController.selfRegistrationPatient(patient, result, model, photo, servletRequest, reCaptchaResponse));
	}

	@Test
	public void testGetActivationString() throws Exception {
		when(cipherService.decodeToken("encodeTokenBase64")).thenReturn("activationString");
		mockMvc.perform(MockMvcRequestBuilders.get("/users/patient/activation")
				.param("activationString", "activationString"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/activation"));
	}

	@Test
	public void testSelfEditPatient() throws Exception {
		User user = new User();
		user.setId(77);
		Patient patient = new Patient();
		patient.setUser(user);
		patient.setPhoto("photo".getBytes());
		when(userService.getLoggedPatient()).thenReturn(patient);
		
		byte [] image = (byte[]) mockMvc.perform(MockMvcRequestBuilders.get("/users/patient/edit"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/patient/edit"))
				.andReturn().getRequest().getAttribute("image");
		assertEquals(patient.getPhoto(), image);
	}

	@Test
	public void testSelfEditDataPatient() throws Exception {
		Patient patient = new Patient();
		User user = new User();
		user.setId(22);
		user.setUsername("username");
		patient.setUser(user);		
		when(userService.checkDinstinctLoginWithEditUser("username", user.getId())).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/patient/edit")
				.file(photo)
				.sessionAttr("patient", patient)
				.sessionAttr("editUserId", user.getId())
				.sessionAttr("image", "image".getBytes()))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/patient/edit"));
		
		assertEquals("forward:/message/patient/success", patientController.selfEditDataPatient(patient, result, model, user.getId(), photo, "".getBytes()));
		when(userService.checkDinstinctLoginWithEditUser("username", user.getId())).thenReturn(false);
		assertEquals("/users/patient/edit", patientController.selfEditDataPatient(patient, result, model, user.getId(), photo, "".getBytes()));
		
		photo = new MockMultipartFile("photo", "".getBytes());
		when(userService.checkDinstinctLoginWithEditUser("username", user.getId())).thenReturn(true);
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).editPatient(patient);
		assertEquals("forward:/message/patient/error", patientController.selfEditDataPatient(patient, result, model, user.getId(), photo, "image".getBytes()));
		
		assertEquals("image".getBytes()[2], patient.getPhoto()[2]);
	}

}
