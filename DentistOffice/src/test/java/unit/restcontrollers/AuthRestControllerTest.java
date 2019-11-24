package unit.restcontrollers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.rest.AuthRestController;
import pl.dentistoffice.service.CipherService;
import pl.dentistoffice.service.UserService;

public class AuthRestControllerTest {
	
	private MockMvc mockMvc;
	private AuthRestController authRestController;
	
	@Mock
	private Environment env;
	@Mock
	private UserService userService;
	@Mock
	private CipherService cipherService;
	@Mock
	private HttpServletRequest request;
	@Mock
	private HttpServletResponse response;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		authRestController = new AuthRestController(env, userService, cipherService, request, response);
		mockMvc = MockMvcBuilders.standaloneSetup(authRestController).build();
	}

	@Test
	public void testLogin() throws Exception {
		String username = "username";
		String password = "password";
		Patient patient = new Patient();
		String token = "token123";
		patient.setId(13);
		patient.setLastName("lastName");
		patient.setToken(token);
		when(userService.loginMobilePatient(username, password)).thenReturn(patient);
		String encodeTokenBase64 = "encodeTokenBase64";
		when(cipherService.encodeToken(token)).thenReturn(encodeTokenBase64);
		when(env.getProperty("patientLoggedValidTime")).thenReturn("66");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/login").param("username", username).param("password", password))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.lastName", is("lastName")));
		
		encodeTokenBase64 = null;
		when(cipherService.encodeToken(token)).thenReturn(encodeTokenBase64);
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/login").param("username", username).param("password", password))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		
		patient = null;
		when(userService.loginMobilePatient(username, password)).thenReturn(patient);
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/login").param("username", username).param("password", password))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void testAuthentication() {
		String encodeTokenBase64 = "encodeTokenBase64";
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(encodeTokenBase64);
		Patient patient = new Patient();
		patient.setId(13);
		String decodeToken = "decodeToken";
		when(cipherService.decodeToken(encodeTokenBase64)).thenReturn(decodeToken);
		when(userService.findMobilePatientByToken(decodeToken)).thenReturn(patient);
		when(env.getProperty("patientLoggedValidTime")).thenReturn("20");
		
		Map<Integer, LocalDateTime> patientLoggedMap = authRestController.getPatientLoggedMap();
		patientLoggedMap.put(patient.getId(), LocalDateTime.now().plusSeconds(20).withNano(0));
		authRestController.setPatientLoggedMap(patientLoggedMap);
		
		boolean authentication = authRestController.authentication();
		assertTrue(authentication);
		
		encodeTokenBase64 = "";
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(encodeTokenBase64);
		authentication = authRestController.authentication();
		assertFalse(authentication);
		
		patient = null;
		encodeTokenBase64 = "encodeTokenBase64";
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(encodeTokenBase64);
		when(userService.findMobilePatientByToken(decodeToken)).thenReturn(patient);
		authentication = authRestController.authentication();
		assertFalse(authentication);
		
		patient = new Patient();
		patient.setId(13);
		when(userService.findMobilePatientByToken(decodeToken)).thenReturn(patient);
		patientLoggedMap.put(patient.getId(), null);
		authRestController.setPatientLoggedMap(patientLoggedMap);
		authentication = authRestController.authentication();
		assertFalse(authentication);
		
		encodeTokenBase64 = "encodeTokenBase64";
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(encodeTokenBase64);
		patientLoggedMap.put(patient.getId(), LocalDateTime.now().plusSeconds(0).withNano(0));
		authRestController.setPatientLoggedMap(patientLoggedMap);
		authentication = authRestController.authentication();
		assertFalse(authentication);
	}

}
