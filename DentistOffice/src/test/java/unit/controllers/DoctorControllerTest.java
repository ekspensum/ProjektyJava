package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
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

import pl.dentistoffice.controller.DoctorController;
import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.entity.VisitStatus;
import pl.dentistoffice.entity.WorkingWeek;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

public class DoctorControllerTest {
	
	private MockMvc mockMvc;    
    private DoctorController doctorController;
    
    @Mock
    private UserService userService;
    @Mock
    private HibernateSearchService searchService;
    @Mock
    private VisitService visitService;
	@Mock
	private Environment env;
	@Mock
	private Model model;
	@Mock
	private BindingResult result;
	@Mock
	RedirectAttributes redirectAttributes;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");       
        
        doctorController = new DoctorController(env, userService, visitService, searchService);
		mockMvc = MockMvcBuilders.standaloneSetup(doctorController).setViewResolvers(viewResolver).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDoctorPanel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/panels/doctorPanel"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("doctorPanel"));
	}

	@Test
	public void testRegistrationDoctor() throws Exception {
		Object doctor = mockMvc.perform(MockMvcRequestBuilders.get("/users/doctor/admin/register"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/users/doctor/admin/register"))
		.andReturn().getRequest().getAttribute("doctor");
		assertNotNull(doctor);
	}

	@Test
	public void testRegisterDoctor() throws Exception {
		User user = new User();
		user.setUsername("username");
		Doctor doctor = new Doctor();
		doctor.setPhoto("photo".getBytes());
		doctor.setUser(user);
		
		MockMultipartFile photo = new MockMultipartFile("photo", new ByteArrayInputStream(doctor.getPhoto()));
		when(userService.checkDinstinctLoginWithRegisterUser(doctor.getUser().getUsername())).thenReturn(true);
		String [] dayOfWeekPolish = {"Zero", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"};
		when(userService.dayOfWeekPolish()).thenReturn(dayOfWeekPolish);
		
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
		
		String [] dayOfWeekPolishActual = (String[]) mockMvc.perform(MockMvcRequestBuilders.multipart("/users/doctor/admin/register")
				.file(photo)
				.param("mondayTime", mondayTime)
				.param("mondayTimeBool", mondayTimeBool)
				.param("tuesdayTime", tuesdayTime)
				.param("tuesdayTimeBool", tuesdayTimeBool)
				.param("wednesdayTime", wednesdayTime)
				.param("wednesdayTimeBool", wednesdayTimeBool)
				.param("thursdayTime", thursdayTime)
				.param("thursdayTimeBool", thursdayTimeBool)
				.param("fridayTime", fridayTime)
				.param("fridayTimeBool", fridayTimeBool)
				.param("saturdayTime", saturdayTime)
				.param("saturdayTimeBool", saturdayTimeBool)
				.param("sundayTime", sundayTime)
				.param("sundayTimeBool", sundayTimeBool)
				.sessionAttr("doctor", doctor))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/admin/register"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("templateWorkingWeekMap"))
				.andReturn().getRequest().getAttribute("dayOfWeekPolish");
		
		assertEquals("Wtorek", dayOfWeekPolishActual[2]);
		assertEquals("forward:/message/employee/success", doctorController.registerDoctor(doctor, result, model, photo, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
		when(result.hasErrors()).thenReturn(true);
		assertEquals("/users/doctor/admin/register", doctorController.registerDoctor(doctor, result, model, photo, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
		when(result.hasErrors()).thenReturn(false);
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).addNewDoctor(doctor);
		assertEquals("forward:/message/employee/error", doctorController.registerDoctor(doctor, result, model, photo, mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
	}

	@Test
	public void testSelectDoctorToEditModel() throws Exception {
		Doctor doctor = new Doctor();
		List<Doctor> allDoctorsList = new ArrayList<>();
		allDoctorsList.add(doctor);
		when(userService.getAllDoctors()).thenReturn(allDoctorsList);
		@SuppressWarnings("unchecked")
		List<Doctor>  allDoctorsListActual = (List<Doctor>) mockMvc.perform(MockMvcRequestBuilders.get("/users/doctor/admin/selectToEdit"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/admin/selectToEdit"))
				.andReturn().getRequest().getAttribute("allDoctorsList");
		assertEquals(allDoctorsList.hashCode(), allDoctorsListActual.hashCode());
	}

	@Test
	public void testSelectDoctorToEditStringRedirectAttributes() throws Exception {
		User user = new User();
		user.setId(11);
		Doctor doctor = new Doctor();
		doctor.setId(22);
		doctor.setUser(user);
		when(userService.getDoctor(doctor.getId())).thenReturn(doctor);
		int editUserId = (int) mockMvc.perform(MockMvcRequestBuilders.post("/users/doctor/admin/selectToEdit")
				.param("doctorId", "22"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/users/doctor/admin/edit"))
				.andReturn().getFlashMap().get("editUserId");
		assertEquals(11, editUserId);
	}

	@Test
	public void testEditDoctor() throws Exception {
		String [] mondayTime = {"08:00", "08:30", "09:30", "10:30", "11:30", "12:00", "13:00", "14:00", "18:30", "19:30"};
		String [] mondayTimeBool = {"true", "false", "true", "true", "false", "true", "true", "true", "false", "true"};
		Map<LocalTime, Boolean> workingTimeMonday = new LinkedHashMap<>();
		for (int i = 0; i < mondayTime.length; i++) {
			workingTimeMonday.put(LocalTime.parse(mondayTime[i]), Boolean.valueOf(mondayTimeBool[i]));			
		}
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = new LinkedHashMap<>();
		workingWeekMap.put(DayOfWeek.MONDAY, workingTimeMonday);
		WorkingWeek workingWeek = new WorkingWeek();
		workingWeek.setWorkingWeekMap(workingWeekMap);
		Doctor doctor = new Doctor();
		doctor.setWorkingWeek(workingWeek);
		
		@SuppressWarnings("unchecked")
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMapActual = (Map<DayOfWeek, Map<LocalTime, Boolean>>) mockMvc
				.perform(MockMvcRequestBuilders.get("/users/doctor/admin/edit")
				.sessionAttr("doctor", doctor))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/admin/edit"))
				.andReturn().getRequest().getAttribute("workingWeekMap");
		assertEquals(LocalTime.parse("11:30"), workingWeekMapActual.get(DayOfWeek.MONDAY).keySet().toArray()[4]);
		assertEquals(false, workingWeekMapActual.get(DayOfWeek.MONDAY).values().toArray()[4]);
	}

	@Test
	public void testEditDataDoctor() throws Exception {
		User user = new User();
		user.setUsername("username");
		Doctor doctor = new Doctor();
		doctor.setPhoto("photo".getBytes());
		doctor.setUser(user);

		MockMultipartFile photo = new MockMultipartFile("photo", doctor.getPhoto());
		when(userService.checkDinstinctLoginWithEditUser(doctor.getUser().getUsername(), 55)).thenReturn(true);
		String [] dayOfWeekPolish = {"Zero", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"};
		when(userService.dayOfWeekPolish()).thenReturn(dayOfWeekPolish);
		
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

		Map<LocalTime, Boolean> workingTimeMonday = new LinkedHashMap<>();
		for (int i = 0; i < mondayTime.length; i++) {
			workingTimeMonday.put(LocalTime.parse(mondayTime[i]), Boolean.valueOf(mondayTimeBool[i]));			
		}
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = new LinkedHashMap<>();
		workingWeekMap.put(DayOfWeek.MONDAY, workingTimeMonday);
		WorkingWeek workingWeek = new WorkingWeek();
		workingWeek.setWorkingWeekMap(workingWeekMap);
		doctor.setWorkingWeek(workingWeek);
		
		String [] dayOfWeekPolishActual = (String[]) mockMvc.perform(MockMvcRequestBuilders.multipart("/users/doctor/admin/edit")
				.file(photo)
				.param("mondayTime", mondayTime)
				.param("mondayTimeBool", mondayTimeBool)
				.param("tuesdayTime", tuesdayTime)
				.param("tuesdayTimeBool", tuesdayTimeBool)
				.param("wednesdayTime", wednesdayTime)
				.param("wednesdayTimeBool", wednesdayTimeBool)
				.param("thursdayTime", thursdayTime)
				.param("thursdayTimeBool", thursdayTimeBool)
				.param("fridayTime", fridayTime)
				.param("fridayTimeBool", fridayTimeBool)
				.param("saturdayTime", saturdayTime)
				.param("saturdayTimeBool", saturdayTimeBool)
				.param("sundayTime", sundayTime)
				.param("sundayTimeBool", sundayTimeBool)
				.sessionAttr("doctor", doctor)
				.sessionAttr("editUserId", 55)
				.sessionAttr("image", "image".getBytes()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/admin/edit"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("workingWeekMap"))
				.andReturn().getRequest().getAttribute("dayOfWeekPolish");
		
		assertEquals("Wtorek", dayOfWeekPolishActual[2]);
		assertEquals("forward:/message/employee/success", doctorController.editDataDoctor(doctor, result, model, 55, photo, "image".getBytes(), mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
		when(result.hasErrors()).thenReturn(true);
		assertEquals("/users/doctor/admin/edit", doctorController.editDataDoctor(doctor, result, model, 55, photo, "image".getBytes(), mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
		when(result.hasErrors()).thenReturn(false);
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).editDoctor(doctor);
		assertEquals("forward:/message/employee/error", doctorController.editDataDoctor(doctor, result, model, 55, photo, "image".getBytes(), mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
	}

	@Test
	public void testSelfEditDoctor() throws Exception {
		String [] mondayTime = {"08:00", "08:30", "09:30", "10:30", "11:30", "12:00", "13:00", "14:00", "18:30", "19:30"};
		String [] mondayTimeBool = {"true", "false", "true", "true", "false", "true", "true", "true", "false", "true"};
		Map<LocalTime, Boolean> workingTimeMonday = new LinkedHashMap<>();
		for (int i = 0; i < mondayTime.length; i++) {
			workingTimeMonday.put(LocalTime.parse(mondayTime[i]), Boolean.valueOf(mondayTimeBool[i]));			
		}
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = new LinkedHashMap<>();
		workingWeekMap.put(DayOfWeek.MONDAY, workingTimeMonday);
		WorkingWeek workingWeek = new WorkingWeek();
		workingWeek.setWorkingWeekMap(workingWeekMap);
		User user = new User();
		user.setId(22);
		Doctor doctor = new Doctor();
		doctor.setUser(user);
		doctor.setWorkingWeek(workingWeek);
		when(userService.getLoggedDoctor()).thenReturn(doctor);

		@SuppressWarnings("unchecked")
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMapActual = (Map<DayOfWeek, Map<LocalTime, Boolean>>) mockMvc
				.perform(MockMvcRequestBuilders.get("/users/doctor/edit")
				.sessionAttr("doctor", doctor))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/edit"))
				.andReturn().getRequest().getAttribute("workingWeekMap");
		assertEquals(LocalTime.parse("11:30"), workingWeekMapActual.get(DayOfWeek.MONDAY).keySet().toArray()[4]);
		assertEquals(false, workingWeekMapActual.get(DayOfWeek.MONDAY).values().toArray()[4]);
	}

	@Test
	public void testSelfEditDataDoctor() throws Exception {
		User user = new User();
		user.setUsername("username");
		Doctor doctor = new Doctor();
		doctor.setPhoto("photo".getBytes());
		doctor.setUser(user);

		MockMultipartFile photo = new MockMultipartFile("photo", doctor.getPhoto());
		when(userService.checkDinstinctLoginWithEditUser(doctor.getUser().getUsername(), 55)).thenReturn(true);
		String [] dayOfWeekPolish = {"Zero", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela"};
		when(userService.dayOfWeekPolish()).thenReturn(dayOfWeekPolish);
		
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

		Map<LocalTime, Boolean> workingTimeMonday = new LinkedHashMap<>();
		for (int i = 0; i < mondayTime.length; i++) {
			workingTimeMonday.put(LocalTime.parse(mondayTime[i]), Boolean.valueOf(mondayTimeBool[i]));			
		}
		Map<DayOfWeek, Map<LocalTime, Boolean>> workingWeekMap = new LinkedHashMap<>();
		workingWeekMap.put(DayOfWeek.MONDAY, workingTimeMonday);
		WorkingWeek workingWeek = new WorkingWeek();
		workingWeek.setWorkingWeekMap(workingWeekMap);
		doctor.setWorkingWeek(workingWeek);
		
		String [] dayOfWeekPolishActual = (String[]) mockMvc.perform(MockMvcRequestBuilders.multipart("/users/doctor/edit")
				.file(photo)
				.param("mondayTime", mondayTime)
				.param("mondayTimeBool", mondayTimeBool)
				.param("tuesdayTime", tuesdayTime)
				.param("tuesdayTimeBool", tuesdayTimeBool)
				.param("wednesdayTime", wednesdayTime)
				.param("wednesdayTimeBool", wednesdayTimeBool)
				.param("thursdayTime", thursdayTime)
				.param("thursdayTimeBool", thursdayTimeBool)
				.param("fridayTime", fridayTime)
				.param("fridayTimeBool", fridayTimeBool)
				.param("saturdayTime", saturdayTime)
				.param("saturdayTimeBool", saturdayTimeBool)
				.param("sundayTime", sundayTime)
				.param("sundayTimeBool", sundayTimeBool)
				.sessionAttr("doctor", doctor)
				.sessionAttr("editUserId", 55)
				.sessionAttr("image", "image".getBytes()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/edit"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("workingWeekMap"))
				.andReturn().getRequest().getAttribute("dayOfWeekPolish");
		
		assertEquals("Wtorek", dayOfWeekPolishActual[2]);
		assertEquals("forward:/message/employee/success", doctorController.editDataDoctor(doctor, result, model, 55, photo, "image".getBytes(), mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
		when(result.hasErrors()).thenReturn(true);
		assertEquals("/users/doctor/admin/edit", doctorController.editDataDoctor(doctor, result, model, 55, photo, "image".getBytes(), mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));
		when(result.hasErrors()).thenReturn(false);
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).editDoctor(doctor);
		assertEquals("forward:/message/employee/error", doctorController.editDataDoctor(doctor, result, model, 55, photo, "image".getBytes(), mondayTime, mondayTimeBool, tuesdayTime, tuesdayTimeBool, wednesdayTime, wednesdayTimeBool, thursdayTime, thursdayTimeBool, fridayTime, fridayTimeBool, saturdayTime, saturdayTimeBool, sundayTime, sundayTimeBool));

	}

	@Test
	public void testSearchPatientByDoctor() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/doctor/searchPatient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/searchPatient"));
	}

	@Test
	public void testSearchPatientByDoctorStringRedirectAttributes() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/users/doctor/searchResult")
				.param("patientData", "patientData"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/users/doctor/selectPatient"));
		
		Patient patient = new Patient();
		patient.setId(33);
		List<Patient> patientList = new ArrayList<>();
		patientList.add(patient);
		String patientData = "patientDataNumberCharsAbove_20";
		Doctor loggedDoctor = new Doctor();
		when(userService.getLoggedDoctor()).thenReturn(loggedDoctor);
		when(searchService.searchPatientNamePeselStreetPhoneByKeywordQueryAndDoctor(patientData.substring(0, 20), loggedDoctor)).thenReturn(patientList);
		
		@SuppressWarnings("unchecked")
		List<Patient> searchedPatientList = (List<Patient>) mockMvc.perform(MockMvcRequestBuilders.post("/users/doctor/searchResult")
				.param("patientData", patientData))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/users/doctor/selectPatient"))
				.andReturn().getFlashMap().get("searchedPatientList");
		assertEquals(33, searchedPatientList.get(0).getId());	
	}

	@Test
	public void testSelectPatientByDoctor() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/doctor/selectPatient"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/selectPatient"));
	}

	@Test
	public void testSelectPatientToEditByDoctor() throws Exception {
		Patient patient = new Patient();
		patient.setId(77);
		when(userService.getPatient(77)).thenReturn(patient);
		Patient patientActual = (Patient) mockMvc.perform(MockMvcRequestBuilders.post("/users/doctor/selectPatient")
				.param("patientId", "77"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/users/doctor/showPatient"))
				.andReturn().getFlashMap().get("patient");
		assertEquals(77, patientActual.getId());
	}

	@Test
	public void testShowPatientByDoctorPatientModel() throws Exception {
		Patient patient = new Patient();
		patient.setId(77);
		VisitStatus defaultVisitStatus = new VisitStatus();
		defaultVisitStatus.setId(2);
		when(visitService.getVisitStatus(2)).thenReturn(defaultVisitStatus);
		
		VisitStatus visitStatusActual = (VisitStatus) mockMvc.perform(MockMvcRequestBuilders.get("/users/doctor/showPatient")
				.sessionAttr("patient", patient))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/showPatient"))
				.andReturn().getRequest().getAttribute("defaultVisitStatus");
		assertEquals(2, visitStatusActual.getId());
		
		Patient patientActual = (Patient) mockMvc.perform(MockMvcRequestBuilders.get("/users/doctor/showPatient")
				.sessionAttr("patient", patient))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/showPatient"))
				.andReturn().getRequest().getAttribute("patient");
		assertEquals(77, patientActual.getId());
	}

	@Test
	public void testShowPatientByDoctorPatientStringModel() throws Exception {
		Patient patient = new Patient();
		patient.setId(77);
		VisitStatus defaultVisitStatus = new VisitStatus();
		defaultVisitStatus.setId(1);
		when(visitService.getVisitStatus(1)).thenReturn(defaultVisitStatus);
		
		VisitStatus visitStatusActual = (VisitStatus) mockMvc.perform(MockMvcRequestBuilders.post("/users/doctor/showPatient")
				.sessionAttr("patient", patient)
				.param("statusId", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/showPatient"))
				.andReturn().getRequest().getAttribute("actualVisitStatus");
		assertEquals(1, visitStatusActual.getId());
		
		Patient patientActual = (Patient) mockMvc.perform(MockMvcRequestBuilders.post("/users/doctor/showPatient")
				.sessionAttr("patient", patient)
				.param("statusId", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/users/doctor/showPatient"))
				.andReturn().getRequest().getAttribute("patient");
		assertEquals(77, patientActual.getId());
	}

}
