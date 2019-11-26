package unit.restcontrollers;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.rest.AuthRestController;
import pl.dentistoffice.rest.PatientRestController;
import pl.dentistoffice.service.CipherService;
import pl.dentistoffice.service.UserService;

public class PatientRestControllerTest {
	
	private MockMvc mockMvc;
	private PatientRestController patientRestController;
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
		authRestController = new AuthRestController();
		patientRestController = new PatientRestController(env, userService, authRestController, cipherService);
		mockMvc = MockMvcBuilders.standaloneSetup(patientRestController).build();
	}

	@Test
	public void testLogout() throws Exception {
		String patientId = "13";
		when(userService.deleteTokenForMobilePatient(Integer.valueOf(patientId))).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/logout")
						.param("patientId", patientId))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.content().string("true"));
		
		when(userService.deleteTokenForMobilePatient(Integer.valueOf(patientId))).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/logout")
				.param("patientId", patientId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("false"));
	}

	@Test
	public void testRegisterPatient() throws Exception {
		String header = "headerToken";
		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(header);
		String decodeToken = LocalDateTime.now().withNano(0).plusMinutes(1).toString()+"decodeToken"; 
		when(cipherService.decodeToken(header)).thenReturn(decodeToken);
		String token = "decodeToken";
		when(env.getProperty("tokenForAddNewPatient")).thenReturn(token);
		Patient patient = new Patient();
		User user = new User();
		user.setUsername("username");
		patient.setUser(user);
		when(userService.checkDinstinctLoginWithRegisterUser(patient.getUser().getUsername())).thenReturn(true);
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
	    String requestJsonPatient = objectWriter.writeValueAsString(patient);
	    
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/registerPatient")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.header(HttpHeaders.AUTHORIZATION, header)
						.content(requestJsonPatient))
						.andExpect(MockMvcResultMatchers.content().string("true"));
		
		header = "";
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/registerPatient")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.header(HttpHeaders.AUTHORIZATION, header)
						.content(requestJsonPatient))
						.andExpect(MockMvcResultMatchers.header().string(HttpHeaders.WARNING, "NoHeader"))
						.andExpect(MockMvcResultMatchers.content().string("error"));
		
		header = "headerToken";
		decodeToken = LocalDateTime.now().withNano(0).minusMinutes(1).toString()+"decodeToken"; 
		when(cipherService.decodeToken(header)).thenReturn(decodeToken);
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/registerPatient")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.header(HttpHeaders.AUTHORIZATION, header)
						.content(requestJsonPatient))
						.andExpect(MockMvcResultMatchers.content().string("Timeout"));
		
		when(userService.checkDinstinctLoginWithRegisterUser(patient.getUser().getUsername())).thenReturn(false);
		decodeToken = LocalDateTime.now().withNano(0).plusMinutes(1).toString()+"decodeToken";
		when(cipherService.decodeToken(header)).thenReturn(decodeToken);
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/registerPatient")
						.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						.header(HttpHeaders.AUTHORIZATION, header)
						.content(requestJsonPatient))
						.andExpect(MockMvcResultMatchers.content().string("NotDistinctLogin"));
	}

	@Test
	public void testEditPatientData() throws Exception {
		Patient patient = new Patient();
		User user = new User();
		user.setId(13);
		user.setUsername("username");
		patient.setUser(user);
		when(userService.checkDinstinctLoginWithEditUser(patient.getUser().getUsername(), patient.getUser().getId())).thenReturn(true);
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
	    String requestJsonPatient = objectWriter.writeValueAsString(patient);
	    
	    mockMvc.perform(MockMvcRequestBuilders.put("/mobile/editPatient")
	    				.contentType(MediaType.APPLICATION_JSON_UTF8)
	    				.content(requestJsonPatient))
	    				.andExpect(MockMvcResultMatchers.status().isOk())
	    				.andExpect(MockMvcResultMatchers.content().string("true"));

	    when(userService.checkDinstinctLoginWithEditUser(patient.getUser().getUsername(), patient.getUser().getId())).thenReturn(false);
	    mockMvc.perform(MockMvcRequestBuilders.put("/mobile/editPatient")
	    				.contentType(MediaType.APPLICATION_JSON_UTF8)
	    				.content(requestJsonPatient))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.content().string("NotDistinctLogin"));
	}

}
