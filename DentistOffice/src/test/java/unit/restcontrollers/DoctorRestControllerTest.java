package unit.restcontrollers;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.rest.DoctorListWrapper;
import pl.dentistoffice.rest.DoctorRestController;
import pl.dentistoffice.service.UserService;

public class DoctorRestControllerTest {
	
	private MockMvc mockMvc;
	private DoctorRestController doctorRestController;
	private DoctorListWrapper doctorListWrapper;
	private List<Doctor> doctorList;
	
	@Mock
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doctorList = new ArrayList<>();
		doctorListWrapper = new DoctorListWrapper();
		doctorRestController = new DoctorRestController(userService, doctorListWrapper);
		mockMvc = MockMvcBuilders.standaloneSetup(doctorRestController).build();
	}

	@Test
	public void testGetDoctors() throws Exception {
		Doctor doctor = new Doctor();
		doctor.setId(13);
		doctor.setLastName("lastName123");
		doctorList.add(doctor);
		when(userService.getAllDoctors()).thenReturn(doctorList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/mobile/doctors"))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$..[0].id", hasItem(13)))
						.andExpect(MockMvcResultMatchers.jsonPath("$..[0].lastName", hasItem("lastName123")));
	}

}
