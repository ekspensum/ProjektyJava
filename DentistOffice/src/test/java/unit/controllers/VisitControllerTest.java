package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
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
import org.springframework.ui.Model;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.VisitController;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
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
	@Mock
	private Model model;
	
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
	public void testVisitToReserveByPatient() throws Exception {
		Doctor doctor = new Doctor();
		doctor.setId(13);
		when(userService.getDoctor(13)).thenReturn(doctor);
		String [] dayOfWeekPolish = {"Zero", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"};
		when(userService.dayOfWeekPolish()).thenReturn(dayOfWeekPolish);
		
		String disableLeftArrow = (String) mockMvc.perform(MockMvcRequestBuilders.post("/visit/patient/toReserve")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 1)
					.param("doctorId", "13")
					.param("weekResultDriver", (String) null))
					.andExpect(status().isOk())
					.andExpect(view().name("/visit/patient/toReserve"))
					.andExpect(MockMvcResultMatchers.model().attribute("doctor", doctor))
					.andReturn().getRequest().getAttribute("disableLeftArrow");
		assertTrue(disableLeftArrow.equals("YES"));
		
		String disableRightArrow = (String) mockMvc.perform(MockMvcRequestBuilders.post("/visit/patient/toReserve")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 21)
					.param("doctorId", "13")
					.param("weekResultDriver", "stepRight"))
					.andExpect(status().isOk())
					.andExpect(view().name("/visit/patient/toReserve"))
					.andExpect(MockMvcResultMatchers.model().attribute("doctor", doctor))
					.andReturn().getRequest().getAttribute("disableRightArrow");
		assertTrue(disableRightArrow.equals("YES"));
		
		disableLeftArrow = (String) mockMvc.perform(MockMvcRequestBuilders.post("/visit/patient/toReserve")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 0)
					.param("doctorId", "13")
					.param("weekResultDriver", "stepLeft"))
					.andExpect(status().isOk())
					.andExpect(view().name("/visit/patient/toReserve"))
					.andExpect(MockMvcResultMatchers.model().attribute("doctor", doctor))
					.andReturn().getRequest().getAttribute("disableLeftArrow");
		assertTrue(disableLeftArrow.equals("YES"));
		
		String [] dayOfWeekPolishActual = (String []) mockMvc.perform(MockMvcRequestBuilders.post("/visit/patient/toReserve")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 0)
					.param("doctorId", "13")
					.param("weekResultDriver", "otherStep"))
					.andExpect(status().isOk())
					.andExpect(view().name("/visit/patient/toReserve"))
					.andExpect(MockMvcResultMatchers.model().attribute("doctor", doctor))
					.andReturn().getRequest().getAttribute("dayOfWeekPolish");
		assertTrue(dayOfWeekPolish[5].equals(dayOfWeekPolishActual[5]));
	}

	@Test(expected = Exception.class)
	public void testVisitReservationByPatient() throws Exception {
		Doctor doctor = new Doctor();
		doctor.setId(13);
		String [] dateTime = {"dateTime"};
		
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/patient/reservation")
				.sessionAttr("doctor", doctor)
				.param("dateTime", dateTime)
				.param("treatment1", "1")
				.param("treatment2", "2")
				.param("treatment3", "3"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/patient/success"));
		
		String [] dateTime2 = {"dateTime", "dateTime"};
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/patient/reservation")
				.sessionAttr("doctor", doctor)
				.param("dateTime", dateTime2)
				.param("treatment1", "1")
				.param("treatment2", "2")
				.param("treatment3", "3"));
		
		assertEquals("forward:/message/patient/success", visitController.visitReservationByPatient(doctor, dateTime, "1", "2", "3", model));
		doThrow(new Exception("Zaplanowany wyjątek")).when(treatmentService).getDentalTreatmentsList();
	}

	@Test
	public void testVisitSearchPatientByAssistant() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/visit/assistant/searchPatient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/assistant/searchPatient"));
		
	}

	@Test
	public void testVisitSearchPatientByAssistantStringRedirectAttributes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/searchResult")
				.param("patientData", "patientData"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/visit/assistant/selectPatient"));
		
		Patient patient = new Patient();
		patient.setId(33);
		List<Patient> patientsList = new ArrayList<>();
		patientsList.add(patient);
		String patientData = "patientDataNumberCharsAbove_20";
		when(userService.searchPatient(patientData.substring(0, 20))).thenReturn(patientsList);
		@SuppressWarnings("unchecked")
		List<Patient> searchedPatientList = (List<Patient>) mockMvc
				.perform(MockMvcRequestBuilders.post("/visit/assistant/searchResult")
				.param("patientData", patientData))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/visit/assistant/selectPatient"))
				.andReturn().getFlashMap().get("searchedPatientList");
		assertEquals(33, searchedPatientList.get(0).getId());
	}

	@Test
	public void testVisitSelectPatientByAssistant() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/visit/assistant/selectPatient"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/visit/assistant/selectPatient"));
	}

	@Test
	public void testVisitSelectPatientByAssistantStringRedirectAttributes() throws Exception {
		Patient patient = new Patient();
		patient.setId(11);
		when(userService.getPatient(11)).thenReturn(patient);
		Patient patientActual = (Patient) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/selectPatient")
				.param("patientId", "11"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/visit/assistant/selectDoctor"))
				.andReturn().getFlashMap().get("patient");
		assertEquals(11, patientActual.getId());
	}

	@Test
	public void testVisitSelectDoctorByAssistant() throws Exception {
		Patient patient = new Patient();
		patient.setId(33);
		Doctor doctor = new Doctor();
		List<Doctor> allDoctorsList = new ArrayList<>();
		allDoctorsList.add(doctor);
		when(userService.getAllDoctors()).thenReturn(allDoctorsList);
		
		@SuppressWarnings("unchecked")
		List<Doctor>  allDoctorsListActual = (List<Doctor>) mockMvc.perform(MockMvcRequestBuilders.get("/visit/assistant/selectDoctor")
				.sessionAttr("patient", patient))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/assistant/selectDoctor"))
				.andReturn().getRequest().getAttribute("allDoctorsList");
		assertEquals(allDoctorsList.hashCode(), allDoctorsListActual.hashCode());
	}

	@Test
	public void testVisitToReserveByAssistant() throws Exception {
		Patient patient = new Patient();
		Doctor doctor = new Doctor();
		doctor.setId(13);
		when(userService.getDoctor(13)).thenReturn(doctor);
		String [] dayOfWeekPolish = {"Zero", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"};
		when(userService.dayOfWeekPolish()).thenReturn(dayOfWeekPolish);
		
		String disableLeftArrow = (String) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/toReserve")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 1)
					.sessionAttr("patient", patient)
					.param("doctorId", "13")
					.param("weekResultDriver", (String) null))
					.andExpect(status().isOk())
					.andExpect(view().name("/visit/assistant/toReserve"))
					.andExpect(MockMvcResultMatchers.model().attribute("patient", patient))
					.andExpect(MockMvcResultMatchers.model().attribute("doctor", doctor))
					.andReturn().getRequest().getAttribute("disableLeftArrow");
		assertTrue(disableLeftArrow.equals("YES"));
		
		String disableRightArrow = (String) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/toReserve")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 21)
					.sessionAttr("patient", patient)
					.param("doctorId", "13")
					.param("weekResultDriver", "stepRight"))
					.andExpect(status().isOk())
					.andExpect(view().name("/visit/assistant/toReserve"))
					.andExpect(MockMvcResultMatchers.model().attribute("patient", patient))
					.andExpect(MockMvcResultMatchers.model().attribute("doctor", doctor))
					.andReturn().getRequest().getAttribute("disableRightArrow");
		assertTrue(disableRightArrow.equals("YES"));
		
		disableLeftArrow = (String) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/toReserve")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 0)
					.sessionAttr("patient", patient)
					.param("doctorId", "13")
					.param("weekResultDriver", "stepLeft"))
					.andExpect(status().isOk())
					.andExpect(view().name("/visit/assistant/toReserve"))
					.andExpect(MockMvcResultMatchers.model().attribute("patient", patient))
					.andExpect(MockMvcResultMatchers.model().attribute("doctor", doctor))
					.andReturn().getRequest().getAttribute("disableLeftArrow");
		assertTrue(disableLeftArrow.equals("YES"));
		
		String [] dayOfWeekPolishActual = (String []) mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/toReserve")
					.sessionAttr("doctor", doctor)
					.sessionAttr("dayStart", 0)
					.sessionAttr("patient", patient)
					.param("doctorId", "13")
					.param("weekResultDriver", "otherStep"))
					.andExpect(status().isOk())
					.andExpect(view().name("/visit/assistant/toReserve"))
					.andExpect(MockMvcResultMatchers.model().attribute("patient", patient))
					.andExpect(MockMvcResultMatchers.model().attribute("doctor", doctor))
					.andReturn().getRequest().getAttribute("dayOfWeekPolish");
		assertTrue(dayOfWeekPolish[5].equals(dayOfWeekPolishActual[5]));
	}

	@Test(expected = Exception.class)
	public void testVisitReservationByAssistant() throws Exception {
		Patient patient = new Patient();
		Doctor doctor = new Doctor();
		doctor.setId(13);
		String [] dateTime = {"dateTime"};
		
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/reservation")
				.sessionAttr("doctor", doctor)
				.sessionAttr("patient", patient)
				.param("dateTime", dateTime)
				.param("treatment1", "1")
				.param("treatment2", "2")
				.param("treatment3", "3"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/success"));
		
		String [] dateTime2 = {"dateTime", "dateTime"};
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/reservation")
				.sessionAttr("doctor", doctor)
				.sessionAttr("patient", patient)
				.param("dateTime", dateTime2)
				.param("treatment1", "1")
				.param("treatment2", "2")
				.param("treatment3", "3"));
		
		assertEquals("forward:/message/patient/success", visitController.visitReservationByAssistant(doctor, patient, dateTime, "1", "2", "3", model));
		doThrow(new Exception("Zaplanowany wyjątek")).when(treatmentService).getDentalTreatmentsList();
	}

	@Test
	public void testSearchVisitsToFinalizeByAssistantModel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/visit/assistant/searchVisitToFinalize"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/assistant/searchVisitToFinalize"))
				.andExpect(MockMvcResultMatchers.request().attribute("dateTo", LocalDate.now()))
				.andExpect(MockMvcResultMatchers.request().attribute("dateFrom", LocalDate.now().minusDays(3)))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visitsToFinalize"));
	}

	@Test
	public void testSearchVisitsToFinalizeByAssistantStringStringModel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/searchVisitToFinalize")
				.param("dateFrom", "2019-01-01")
				.param("dateTo", "2019-01-02"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/assistant/searchVisitToFinalize"))
				.andExpect(MockMvcResultMatchers.request().attribute("dateFrom", "2019-01-01"))
				.andExpect(MockMvcResultMatchers.request().attribute("dateTo", "2019-01-02"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visitsToFinalize"));
	}

	@Test
	public void testSetVisitsToFinalizeByAssistant() throws Exception {
		Visit visit = new Visit();
		when(visitService.getVisit(13)).thenReturn(visit);
		List<DentalTreatment> dentalTreatmentsList = new ArrayList<>();
		when(treatmentService.getDentalTreatmentsList()).thenReturn(dentalTreatmentsList);
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/visitToFinalize")
				.param("visitId", "13"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/assistant/visitToFinalize"))
				.andExpect(MockMvcResultMatchers.request().attribute("visit", visit))
				.andExpect(MockMvcResultMatchers.request().attribute("dentalTreatmentsList", dentalTreatmentsList));
	}

	@Test
	public void testSaveFinalizedVisitByAssistant() throws Exception {
		Visit visit = new Visit();		
		String treatmentCommentVisit1 = "treatmentCommentVisit1";
		String treatmentCommentVisit2 = "treatmentCommentVisit2";
		String treatmentCommentVisit3 = "treatmentCommentVisit3";
		DentalTreatment dentalTreatment1 = new DentalTreatment();
		dentalTreatment1.setId(2);
		DentalTreatment dentalTreatment2 = new DentalTreatment();
		dentalTreatment2.setId(3);
		DentalTreatment dentalTreatment3 = new DentalTreatment();
		dentalTreatment3.setId(4);
		List<DentalTreatment> dentalTreatmentsList = new ArrayList<>();
		dentalTreatmentsList.add(dentalTreatment1);
		dentalTreatmentsList.add(dentalTreatment2);
		dentalTreatmentsList.add(dentalTreatment3);
		when(treatmentService.getDentalTreatmentsList()).thenReturn(dentalTreatmentsList);
		
		String [] treatmentCommentVisit = {treatmentCommentVisit1, treatmentCommentVisit2, treatmentCommentVisit3};
		List<DentalTreatment> selectedTreatments = dentalTreatmentsList;
		when(visitService.setFinalzedVisit(visit, selectedTreatments, treatmentCommentVisit)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/finalizedVisit")
				.sessionAttr("visit", visit)
				.param("treatmentCommentVisit1", treatmentCommentVisit1)
				.param("treatmentCommentVisit2", treatmentCommentVisit2)
				.param("treatmentCommentVisit3", treatmentCommentVisit3)
				.param("treatment1", "2")
				.param("treatment2", "3")
				.param("treatment3", "4"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/success"));

		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/finalizedVisit")
				.sessionAttr("visit", visit)
				.param("treatmentCommentVisit1", treatmentCommentVisit1)
				.param("treatmentCommentVisit2", treatmentCommentVisit2)
				.param("treatmentCommentVisit3", treatmentCommentVisit3)
				.param("treatment1", "2")
				.param("treatment2", "3")
				.param("treatment3", "4"));
		

		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/finalizedVisit")
				.sessionAttr("visit", visit)
				.param("treatmentCommentVisit1", treatmentCommentVisit1+"^")
				.param("treatmentCommentVisit2", treatmentCommentVisit2)
				.param("treatmentCommentVisit3", treatmentCommentVisit3)
				.param("treatment1", "2")
				.param("treatment2", "3")
				.param("treatment3", "4"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/assistant/finalizedVisit"))
				.andExpect(MockMvcResultMatchers.request().attribute("dentalTreatmentsList", dentalTreatmentsList));

		when(visitService.setFinalzedVisit(visit, selectedTreatments, treatmentCommentVisit)).thenReturn(false);
		assertEquals("forward:/message/employee/error", visitController.saveFinalizedVisitByAssistant(visit, "treatmentCommentVisit1", "treatmentCommentVisit2", "treatmentCommentVisit3", "2", "3", "4", model));
	}

	@Test
	public void testSearchVisitsToRemoveByAssistantModel() throws Exception {
		List<Visit> visitsToRemove = new ArrayList<>();
		LocalDate today = LocalDate.now();
		when(visitService.getVisitsToFinalizeOrRemove(today.atStartOfDay(), today.atStartOfDay().plusDays(7))).thenReturn(visitsToRemove);
		mockMvc.perform(MockMvcRequestBuilders.get("/visit/assistant/searchVisitToRemove"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/assistant/searchVisitToRemove"))
				.andExpect(MockMvcResultMatchers.request().attribute("dateFrom", LocalDate.now()))
				.andExpect(MockMvcResultMatchers.request().attribute("dateTo", LocalDate.now().plusDays(7)))
				.andExpect(MockMvcResultMatchers.request().attribute("visitsToRemove", visitsToRemove));
	}

	@Test
	public void testSearchVisitsToRemoveByAssistantStringStringModel() throws Exception {
		List<Visit> visitsToRemove = new ArrayList<>();
		LocalDate today = LocalDate.now();
		when(visitService.getVisitsToFinalizeOrRemove(today.atStartOfDay(), today.atStartOfDay().plusDays(7))).thenReturn(visitsToRemove);
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/searchVisitToRemove")
				.param("dateFrom", "2019-01-01")
				.param("dateTo", "2019-01-02"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/assistant/searchVisitToRemove"))
				.andExpect(MockMvcResultMatchers.request().attribute("dateFrom", "2019-01-01"))
				.andExpect(MockMvcResultMatchers.request().attribute("dateTo", "2019-01-02"))
				.andExpect(MockMvcResultMatchers.request().attribute("visitsToRemove", visitsToRemove));
	}

	@Test
	public void testRemoveVisitByAssistant() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/assistant/removeVisit")
				.param("visitId", "13"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/success"));
		
		Visit visit = new Visit();
		when(visitService.getVisit(13)).thenReturn(visit);
		doThrow(new Exception("Zaplanowany wyjątek")).when(visitService).cancelVisit(visit);
		assertEquals("forward:/message/employee/error", visitController.removeVisitByAssistant("13", model));
	}

	@Test
	public void testRemoveVisitByPatient() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/patient/removeVisit")
				.param("visitId", "13"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/patient/success"));
		
		Visit visit = new Visit();
		when(visitService.getVisit(13)).thenReturn(visit);
		doThrow(new Exception("Zaplanowany wyjątek")).when(visitService).cancelVisit(visit);
		assertEquals("forward:/message/patient/error", visitController.removeVisitByPatient("13", model));
	}

	@Test
	public void testSearchVisitsToFinalizeByDoctorModel() throws Exception {
		Doctor doctor = new Doctor();
		when(userService.getLoggedDoctor()).thenReturn(doctor);
		mockMvc.perform(MockMvcRequestBuilders.get("/visit/doctor/searchVisitToFinalize"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/visit/doctor/searchVisitToFinalize"))
		.andExpect(MockMvcResultMatchers.request().attribute("dateTo", LocalDate.now()))
		.andExpect(MockMvcResultMatchers.request().attribute("dateFrom", LocalDate.now().minusDays(7)))
		.andExpect(MockMvcResultMatchers.model().attributeExists("visitsToFinalize"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("doctor"));
	}

	@Test
	public void testSearchVisitsToFinalizeByDoctorDoctorStringStringModel() throws Exception {
		Doctor doctor = new Doctor();
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/doctor/searchVisitToFinalize")
				.sessionAttr("doctor", doctor)
				.param("dateFrom", "2019-01-01")
				.param("dateTo", "2019-01-02"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/doctor/searchVisitToFinalize"))
				.andExpect(MockMvcResultMatchers.request().attribute("dateFrom", "2019-01-01"))
				.andExpect(MockMvcResultMatchers.request().attribute("dateTo", "2019-01-02"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visitsToFinalize"));
	}

	@Test
	public void testSetVisitsToFinalizeByDoctor() throws Exception {
		Visit visit = new Visit();
		when(visitService.getVisit(13)).thenReturn(visit);
		List<DentalTreatment> dentalTreatmentsList = new ArrayList<>();
		when(treatmentService.getDentalTreatmentsList()).thenReturn(dentalTreatmentsList);
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/doctor/visitToFinalize")
				.param("visitId", "13"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/doctor/visitToFinalize"))
				.andExpect(MockMvcResultMatchers.request().attribute("visit", visit))
				.andExpect(MockMvcResultMatchers.request().attribute("dentalTreatmentsList", dentalTreatmentsList));
	}

	@Test
	public void testSaveFinalizedVisitByDoctor() throws Exception {
		Visit visit = new Visit();
		Doctor doctor = new Doctor();
		String treatmentCommentVisit1 = "treatmentCommentVisit1";
		String treatmentCommentVisit2 = "treatmentCommentVisit2";
		String treatmentCommentVisit3 = "treatmentCommentVisit3";
		DentalTreatment dentalTreatment1 = new DentalTreatment();
		dentalTreatment1.setId(2);
		DentalTreatment dentalTreatment2 = new DentalTreatment();
		dentalTreatment2.setId(3);
		DentalTreatment dentalTreatment3 = new DentalTreatment();
		dentalTreatment3.setId(4);
		List<DentalTreatment> dentalTreatmentsList = new ArrayList<>();
		dentalTreatmentsList.add(dentalTreatment1);
		dentalTreatmentsList.add(dentalTreatment2);
		dentalTreatmentsList.add(dentalTreatment3);
		when(treatmentService.getDentalTreatmentsList()).thenReturn(dentalTreatmentsList);
		
		String [] treatmentCommentVisit = {treatmentCommentVisit1, treatmentCommentVisit2, treatmentCommentVisit3};
		List<DentalTreatment> selectedTreatments = dentalTreatmentsList;
		when(visitService.setFinalzedVisit(visit, selectedTreatments, treatmentCommentVisit)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/doctor/finalizedVisit")
				.sessionAttr("visit", visit)
				.sessionAttr("doctor", doctor)
				.param("treatmentCommentVisit1", treatmentCommentVisit1)
				.param("treatmentCommentVisit2", treatmentCommentVisit2)
				.param("treatmentCommentVisit3", treatmentCommentVisit3)
				.param("treatment1", "2")
				.param("treatment2", "3")
				.param("treatment3", "4"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/message/employee/success"));

		mockMvc.perform(MockMvcRequestBuilders.post("/visit/doctor/finalizedVisit")
				.sessionAttr("visit", visit)
				.sessionAttr("doctor", doctor)
				.param("treatmentCommentVisit1", treatmentCommentVisit1)
				.param("treatmentCommentVisit2", treatmentCommentVisit2)
				.param("treatmentCommentVisit3", treatmentCommentVisit3)
				.param("treatment1", "2")
				.param("treatment2", "3")
				.param("treatment3", "4"));
		

		mockMvc.perform(MockMvcRequestBuilders.post("/visit/doctor/finalizedVisit")
				.sessionAttr("visit", visit)
				.sessionAttr("doctor", doctor)
				.param("treatmentCommentVisit1", treatmentCommentVisit1+"^")
				.param("treatmentCommentVisit2", treatmentCommentVisit2)
				.param("treatmentCommentVisit3", treatmentCommentVisit3)
				.param("treatment1", "2")
				.param("treatment2", "3")
				.param("treatment3", "4"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/doctor/finalizedVisit"))
				.andExpect(MockMvcResultMatchers.request().attribute("dentalTreatmentsList", dentalTreatmentsList));

		when(visitService.setFinalzedVisit(visit, selectedTreatments, treatmentCommentVisit)).thenReturn(false);
		assertEquals("forward:/message/employee/error", visitController.saveFinalizedVisitByDoctor(doctor, visit, "treatmentCommentVisit1", "treatmentCommentVisit2", "treatmentCommentVisit3", "2", "3", "4", model));
	}

	@Test
	public void testShowMyVisitsPatientModel() throws Exception {
		List<VisitStatus> visitStatusList = new ArrayList<>();
		when(visitService.getVisitStatusList()).thenReturn(visitStatusList);
		VisitStatus defaultVisitStatus = new VisitStatus();
		when(visitService.getVisitStatus(2)).thenReturn(defaultVisitStatus);
		Patient patient = new Patient();
		when(userService.getLoggedPatient()).thenReturn(patient);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/visit/patient/myVisits"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/patient/myVisits"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visitStatusList"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("defaultVisitStatus"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visitsByPatientAndStatus"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("patient"));
	}

	@Test
	public void testShowMyVisitsPatientPatientStringModel() throws Exception {
		List<VisitStatus> visitStatusList = new ArrayList<>();
		when(visitService.getVisitStatusList()).thenReturn(visitStatusList);
		Patient patient = new Patient();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/patient/myVisits")
				.sessionAttr("patient", patient)
				.param("statusId", "2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/patient/myVisits"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visitStatusList"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visitsByPatientAndStatus"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("patient"));
	}

	@Test
	public void testShowMyVisitsPdfPatient() throws Exception {
		Patient patient = new Patient();
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/patient/myVisitsPdf")
				.sessionAttr("patient", patient)
				.param("statusId", "1"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("patientVisits"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("patientVisits"));
	}

	@Test
	public void testShowMyVisitsDoctorModel() throws Exception {
		Doctor doctor = new Doctor();
		when(userService.getLoggedDoctor()).thenReturn(doctor);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/visit/doctor/showMyVisits"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/doctor/showMyVisits"))
				.andExpect(MockMvcResultMatchers.request().attribute("dateFrom", LocalDate.now()))
				.andExpect(MockMvcResultMatchers.request().attribute("doctor", doctor))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visitsToShow"));
	}

	@Test
	public void testShowMyVisitsDoctorDoctorStringModel() throws Exception {
		Doctor doctor = new Doctor();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/doctor/showMyVisits")
				.sessionAttr("doctor", doctor)
				.param("dateFrom", "2019-01-01"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/visit/doctor/showMyVisits"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("visitsToShow"));
	}

	@Test
	public void testShowMyVisitsPdfDoctor() throws Exception {
		Doctor doctor = new Doctor();
		mockMvc.perform(MockMvcRequestBuilders.post("/visit/doctor/myVisitsPdf")
				.sessionAttr("doctor", doctor)
				.param("dateFrom", "2019-01-01"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("doctorVisits"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("doctorVisits"));
	}

}
