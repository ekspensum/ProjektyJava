package unit.restcontrollers;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.Visit;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.rest.AuthRestController;
import pl.dentistoffice.rest.VisitAndStatusListWrapper;
import pl.dentistoffice.rest.VisitRestController;
import pl.dentistoffice.rest.WorkingWeekMapWrapper;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

public class VisitRestControllerTest {

	private MockMvc mockMvc;
	private AuthRestController authRestController;
	private WorkingWeekMapWrapper workingWeekMapWrapper;
	private VisitAndStatusListWrapper visitAndStatusListWrapper;
	private VisitRestController visitRestController;
	
	@Mock
	private VisitService visitService;	
	@Mock
	private UserService userService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		authRestController = new AuthRestController();
		workingWeekMapWrapper = new WorkingWeekMapWrapper();
		visitAndStatusListWrapper = new VisitAndStatusListWrapper();
		visitRestController = new VisitRestController(visitService, userService, workingWeekMapWrapper, visitAndStatusListWrapper, authRestController);
		mockMvc = MockMvcBuilders.standaloneSetup(visitRestController).build();
	}

	@Test(expected = Exception.class)
	public void testGetVisitsAndStatusListForPatient() throws Exception {
		VisitStatus visitStatus = new VisitStatus();
		visitStatus.setId(123);
		List<VisitStatus> visitStatusList = new ArrayList<>();
		visitStatusList.add(visitStatus);
		when(visitService.getVisitStatusList()).thenReturn(visitStatusList);
		String patientId = "13";
		Patient patient = new Patient();
		patient.setId(13);
		when(userService.getPatient(Integer.valueOf(patientId))).thenReturn(patient);
		Visit visit = new Visit();
		visit.setId(456);
		List<Visit> visitsList = new ArrayList<>();
		visitsList.add(visit);
		when(visitService.getVisitsByPatient(patient)).thenReturn(visitsList);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/visitStatusList").param("patientId", patientId))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$..[0].id", hasItem(456)))
						.andExpect(MockMvcResultMatchers.jsonPath("$..[1].id", hasItem(123)));
		
		when(visitService.getVisitsByPatient(patient)).thenThrow(new IndexOutOfBoundsException());
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/visitStatusList").param("patientId", patientId))
						.andExpect(MockMvcResultMatchers.status().isInternalServerError())
						.andExpect(MockMvcResultMatchers.content().string("null"));
	}

	@Test(expected = NullPointerException.class)
	public void testGetWorkingWeekFreeTimeMap() throws Exception {
		String doctorId = "333";
		Doctor doctor = new Doctor();
		when(userService.getDoctor(Integer.valueOf(doctorId))).thenReturn(doctor);
		
		String dayStart = "0";
		String dayEnd = "7";
		Map<LocalDate, Map<LocalTime, Boolean>> workingWeekFreeTimeMap = new LinkedHashMap<>();
		Map<LocalTime, Boolean> workingTimeOfDay = new LinkedHashMap<>();
		workingTimeOfDay.put(LocalTime.now().withNano(0).withSecond(0), true);
		workingWeekFreeTimeMap.put(LocalDate.now(), workingTimeOfDay);
		when(visitService.getWorkingWeekFreeTimeMap(doctor, Integer.valueOf(dayStart), Integer.valueOf(dayEnd))).thenReturn(workingWeekFreeTimeMap);
		
		Map<LocalTime, Boolean> workingTimeOfDayExpect = new LinkedHashMap<>();
		workingTimeOfDayExpect.put(LocalTime.now().withNano(0).withSecond(0), true);
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/workingWeekMap")
						.param("doctorId", doctorId)
						.param("dayStart", dayStart)
						.param("dayEnd", dayEnd))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.jsonPath("$.."+LocalDate.now().toString()+"."+LocalTime.now().withNano(0).withSecond(0), hasItem(true)));
		
		when(visitService.getWorkingWeekFreeTimeMap(doctor, Integer.valueOf(dayStart), Integer.valueOf(dayEnd))).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/workingWeekMap")
				.param("doctorId", doctorId)
				.param("dayStart", dayStart)
				.param("dayEnd", dayEnd))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.workingWeekFreeTimeMap", hasItem(null)));
	}

	@Test
	public void testAddNewVisitByMobilePatient() throws Exception {
		int doctorId = 33;
		int patientId = 44;
		String dateTime = "dateTime";
		String treatment1Id = "1";
		String treatment2Id = "2";
		String treatment3Id = "2";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/newVisit")
						.param("doctorId", String.valueOf(doctorId))
						.param("patientId", String.valueOf(patientId))
						.param("dateTime", dateTime)
						.param("treatment1Id", treatment1Id)
						.param("treatment2Id", treatment2Id)
						.param("treatment3Id", treatment3Id))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.content().string("true"));
	}

	@Test(expected = Exception.class)
	public void testDeleteVisit() throws Exception {
		String visitId = "22";
		Visit visit = new Visit();
		when(visitService.getVisit(Integer.valueOf(visitId))).thenReturn(visit);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/deleteVisit")
						.param("visitId", visitId))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.content().string("true"));
		
		when(visitService.getVisit(Integer.valueOf(visitId))).thenThrow(new NullPointerException());
		mockMvc.perform(MockMvcRequestBuilders.post("/mobile/deleteVisit")
						.param("visitId", visitId))
						.andExpect(MockMvcResultMatchers.status().isOk())
						.andExpect(MockMvcResultMatchers.content().string("false"));
	}

}
