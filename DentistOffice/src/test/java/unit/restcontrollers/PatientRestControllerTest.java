package unit.restcontrollers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
	public void testRegisterPatient() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditPatientData() {
		fail("Not yet implemented");
	}

}
