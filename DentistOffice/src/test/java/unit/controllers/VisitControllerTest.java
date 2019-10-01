package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.VisitController;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

public class VisitControllerTest {

	private MockMvc mockMvc;
	private VisitController visitController;
	
	@Mock
	private Environment env;
	@Mock
	private UserService userService;
	@Mock
	private VisitService visitService;
	@Mock
	private DentalTreatmentService treatmentService;
	
	private int dayStart = 0;
	private int dayEnd = 0;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);		
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");
        visitController = new VisitController(env, visitService, userService, treatmentService, dayStart, dayEnd);
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testVisitSelectDoctorByPatient() throws Exception {
		Doctor doctor = new Doctor();
		List<Doctor> allDoctorsList = new ArrayList<>();
		allDoctorsList.add(doctor);
		when(userService.getAllDoctors()).thenReturn(allDoctorsList);
		
		@SuppressWarnings("unchecked")
		List<Doctor> allDoctorsListActual = (List<Doctor>) mockMvc.perform(MockMvcRequestBuilders.get("/visit/patient/selectDoctor"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/patient/selectDoctor"))
				.andReturn().getRequest().getAttribute("allDoctorsList");
		assertTrue(allDoctorsList.equals(allDoctorsListActual));
	}

	@Test
	public void testVisitToReserveByPatient() {
		fail("Not yet implemented");
	}

	@Test
	public void testVisitReservationByPatient() {
		fail("Not yet implemented");
	}

	@Test
	public void testVisitSearchPatientByAssistant() {
		fail("Not yet implemented");
	}

	@Test
	public void testVisitSearchPatientByAssistantStringRedirectAttributes() {
		fail("Not yet implemented");
	}

	@Test
	public void testVisitSelectPatientByAssistant() {
		fail("Not yet implemented");
	}

	@Test
	public void testVisitSelectPatientByAssistantStringRedirectAttributes() {
		fail("Not yet implemented");
	}

	@Test
	public void testVisitSelectDoctorByAssistant() {
		fail("Not yet implemented");
	}

	@Test
	public void testVisitToReserveByAssistant() {
		fail("Not yet implemented");
	}

	@Test
	public void testVisitReservationByAssistant() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchVisitsToFinalizeByAssistantModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchVisitsToFinalizeByAssistantStringStringModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetVisitsToFinalizeByAssistant() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveFinalizedVisitByAssistant() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchVisitsToRemoveByAssistantModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchVisitsToRemoveByAssistantStringStringModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveVisitByAssistant() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveVisitByPatient() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchVisitsToFinalizeByDoctorModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSearchVisitsToFinalizeByDoctorDoctorStringStringModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetVisitsToFinalizeByDoctor() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveFinalizedVisitByDoctor() {
		fail("Not yet implemented");
	}

	@Test
	public void testShowMyVisitsPatientModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testShowMyVisitsPatientPatientStringModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testShowMyVisitsPdfPatient() {
		fail("Not yet implemented");
	}

	@Test
	public void testShowMyVisitsDoctorModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testShowMyVisitsDoctorDoctorStringModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testShowMyVisitsPdfDoctor() {
		fail("Not yet implemented");
	}

}
