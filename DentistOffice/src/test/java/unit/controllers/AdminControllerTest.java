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
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.AdminController;
import pl.dentistoffice.entity.Admin;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.InitApplicationService;
import pl.dentistoffice.service.UserService;

public class AdminControllerTest {
	
	private MockMvc mockMvc;    
    private AdminController adminController;
    
    @Mock
    private UserService userService;
    @Mock
    private HibernateSearchService searchsService;
    @Mock
    private InitApplicationService initApplicationService;
	@Mock
	private Environment env;
	@Mock
	private Model model;
	@Mock
	private BindingResult result;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");       
        
        adminController = new AdminController(env, userService, searchsService, initApplicationService);
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).setViewResolvers(viewResolver).build();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testAdminPanel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/panels/adminPanel"))
		.andExpect(status().isOk())
		.andExpect(view().name("adminPanel"));
	}

	@Test
	public void testRegistrationAdmin() throws Exception {
		List<Role> roles = new ArrayList<Role>();
		when(userService.getAllRolesWithoutId(5)).thenReturn(roles);		
		
		assertEquals("/users/admin/owner/register", adminController.registrationAdmin(model));
		mockMvc.perform(MockMvcRequestBuilders.get("/users/admin/owner/register"))
		.andExpect(status().isOk())
		.andExpect(view().name("/users/admin/owner/register"));
	}

	@Test
	public void testRegistrationAdminByOwner() throws Exception {
		Admin admin = new Admin();
		User user = new User();
		user.setUsername("username");
		admin.setUser(user);		
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		
		assertEquals("forward:/message/employee/success", adminController.registrationAdminByOwner(admin, result, model, photo));
	}

	@Test
	public void testSelectAdminToEditByOwnerModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectAdminToEditByOwnerStringRedirectAttributes() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditAdminByOwnerAdminModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testEditAdminByOwnerAdminBindingResultModelIntMultipartFileByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelfEditAdminModel() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelfEditAdminAdminBindingResultModelIntMultipartFileByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testIndexingDatabase() {
		fail("Not yet implemented");
	}

	@Test
	public void testAdjustGeneratorPrimaryKey() {
		fail("Not yet implemented");
	}

}
