package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.HomeController;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

public class HomeControllerTest {
	
	private MockMvc mockMvc;
	private HomeController homeController;
	
    @Mock
    private UserService userService;
	@Mock
	private DentalTreatmentService dentalTreatmentService;
	@Mock
	private VisitService visitService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");
        homeController = new HomeController(userService, dentalTreatmentService, visitService);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).setViewResolvers(viewResolver).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHome() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/"))
					.andExpect(status().isOk())
					.andExpect(view().name("home"));
	}

	@Test
	public void testDoctors() throws Exception {
		Doctor doctor = new Doctor();
		List<Doctor> allDoctors = new ArrayList<>();
		allDoctors.add(doctor);
		when(userService.getAllDoctors()).thenReturn(allDoctors);
		
		@SuppressWarnings("unchecked")
		List<Doctor> doctorsList = (List<Doctor>) mockMvc.perform(MockMvcRequestBuilders.get("/doctors"))
					.andExpect(status().isOk())
					.andExpect(view().name("doctors"))
					.andReturn().getRequest().getAttribute("allDoctors");
		assertTrue(allDoctors.equals(doctorsList));
	}

	@Test
	public void testServicesModel() throws Exception {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		List<TreatmentCategory> treatmentCategoriesList = new ArrayList<>();
		treatmentCategoriesList.add(treatmentCategory);
		when(dentalTreatmentService.getTreatmentCategoriesList()).thenReturn(treatmentCategoriesList);
		
		@SuppressWarnings("unchecked")
		List<TreatmentCategory> treatmentCategoriesListActual = (List<TreatmentCategory>) mockMvc.perform(MockMvcRequestBuilders.get("/services"))
					.andExpect(status().isOk())
					.andExpect(view().name("services"))
					.andReturn().getRequest().getAttribute("treatmentCategoriesList");	
		assertTrue(treatmentCategoriesList.equals(treatmentCategoriesListActual));
	}

	@Test
	public void testServicesStringModel() throws Exception {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		List<TreatmentCategory> treatmentCategoriesList = new ArrayList<>();
		treatmentCategoriesList.add(treatmentCategory);
		when(dentalTreatmentService.getTreatmentCategoriesList()).thenReturn(treatmentCategoriesList);
		
		@SuppressWarnings("unchecked")
		List<TreatmentCategory> treatmentCategoriesListActual = (List<TreatmentCategory>) mockMvc
					.perform(MockMvcRequestBuilders.post("/services")
					.param("categoryId", "11"))
					.andExpect(status().isOk())
					.andExpect(view().name("services"))
					.andReturn().getRequest().getAttribute("treatmentCategoriesList");	
		assertTrue(treatmentCategoriesList.equals(treatmentCategoriesListActual));
	}

	@Test
	public void testAgendaModel() throws Exception {
		Doctor doctor = new Doctor();
		List<Doctor> allDoctors = new ArrayList<>();
		allDoctors.add(doctor);
		when(userService.getAllDoctors()).thenReturn(allDoctors);
		
		@SuppressWarnings("unchecked")
		List<Doctor> doctorsList = (List<Doctor>) mockMvc.perform(MockMvcRequestBuilders.get("/agenda"))
					.andExpect(status().isOk())
					.andExpect(view().name("agenda"))
					.andReturn().getRequest().getAttribute("allDoctors");
		assertTrue(allDoctors.equals(doctorsList));
	}

	@Test
	public void testAgendaStringDoctorStringIntegerModel() throws Exception {
		Doctor doctor = new Doctor();
		doctor.setId(13);
		when(userService.getDoctor(13)).thenReturn(doctor);
		List<Doctor> allDoctors = new ArrayList<>();
		allDoctors.add(doctor);
		when(userService.getAllDoctors()).thenReturn(allDoctors);
		
		String disableLeftArrow = (String) mockMvc.perform(MockMvcRequestBuilders.post("/agenda")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 1)
					.param("doctorId", "13")
					.param("weekResultDriver", (String) null))
					.andExpect(status().isOk())
					.andExpect(view().name("agenda"))
					.andReturn().getRequest().getAttribute("disableLeftArrow");
		assertTrue(disableLeftArrow.equals("YES"));
		
		String disableRightArrow = (String) mockMvc.perform(MockMvcRequestBuilders.post("/agenda")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 21)
					.param("doctorId", "13")
					.param("weekResultDriver", "stepRight"))
					.andExpect(status().isOk())
					.andExpect(view().name("agenda"))
					.andReturn().getRequest().getAttribute("disableRightArrow");
		assertTrue(disableRightArrow.equals("YES"));
		
		disableLeftArrow = (String) mockMvc.perform(MockMvcRequestBuilders.post("/agenda")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 0)
					.param("doctorId", "13")
					.param("weekResultDriver", "stepLeft"))
					.andExpect(status().isOk())
					.andExpect(view().name("agenda"))
					.andReturn().getRequest().getAttribute("disableLeftArrow");
		assertTrue(disableLeftArrow.equals("YES"));
		
		@SuppressWarnings("unchecked")
		List<Doctor> allDoctorsActual = (List<Doctor>) mockMvc.perform(MockMvcRequestBuilders.post("/agenda")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 0)
					.param("doctorId", "13")
					.param("weekResultDriver", "otherStep"))
					.andExpect(status().isOk())
					.andExpect(view().name("agenda"))
					.andReturn().getRequest().getAttribute("allDoctors");
		assertTrue(allDoctors.equals(allDoctorsActual));
	}

}
