package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
	@Mock
	RedirectAttributes redirectAttributes;

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
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/admin/owner/register")
				.file(photo)
				.sessionAttr("admin", admin))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/admin/owner/register"));
		
		assertEquals("forward:/message/employee/success", adminController.registrationAdminByOwner(admin, result, model, photo));
		
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(false);
		assertEquals("/users/admin/owner/register", adminController.registrationAdminByOwner(admin, result, model, photo));
		
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(true);	
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).addNewAdmin(admin);
		assertEquals("forward:/message/employee/error", adminController.registrationAdminByOwner(admin, result, model, photo));
	}

	@Test
	public void testSelectAdminToEditByOwnerModel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/admin/owner/selectToEdit"))
		.andExpect(status().isOk())
		.andExpect(view().name("/users/admin/owner/selectToEdit"));
		
		assertEquals("/users/admin/owner/selectToEdit", adminController.selectAdminToEditByOwner(model));
	}

	@Test
	public void testSelectAdminToEditByOwnerStringRedirectAttributes() throws Exception {
		User user = new User();
		user.setId(1);
		Admin admin = new Admin();
		admin.setId(1);
		admin.setUser(user);
		when(userService.getAdmin(1)).thenReturn(admin);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users/admin/owner/selectToEdit")
				.param("adminId", "1"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/users/admin/owner/edit"));
		
		assertEquals("redirect:/users/admin/owner/edit", adminController.selectAdminToEditByOwner("1", redirectAttributes));
	}

	@Test
	public void testEditAdminByOwnerAdminModel() throws Exception {
		Admin admin = new Admin();
		mockMvc.perform(MockMvcRequestBuilders.get("/users/admin/owner/edit")
				.sessionAttr("admin", admin))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/admin/owner/edit"));
		
		assertEquals("/users/admin/owner/edit", adminController.editAdminByOwner(admin, model));
	}

	@Test
	public void testEditAdminByOwnerAdminBindingResultModelIntMultipartFileByteArray() throws Exception {
		Admin admin = new Admin();
		User user = new User();
		user.setUsername("username");
		admin.setUser(user);		
		when(userService.checkDinstinctLoginWithEditUser("username", 1)).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		
		byte [] image = new byte[100];
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/admin/owner/edit")
				.file(photo)
				.sessionAttr("admin", admin)
				.sessionAttr("editUserId", 1)
				.sessionAttr("image", image))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/admin/owner/edit"));
		
		assertEquals("forward:/message/employee/success", adminController.editAdminByOwner(admin, result, model, 1, photo, image));
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).editAdmin(admin);
		assertEquals("forward:/message/employee/error", adminController.editAdminByOwner(admin, result, model, 1, photo, image));
	}

	@Test
	public void testSelfEditAdminModel() throws Exception {
		User user = new User();
		user.setId(13);
		Admin admin = new Admin();
		admin.setUser(user);
		when(userService.getLoggedAdmin()).thenReturn(admin);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users/admin/edit"))
		.andExpect(status().isOk())
		.andExpect(view().name("/users/admin/edit"));
		
		assertEquals("/users/admin/edit", adminController.selfEditAdmin(model));
	}

	@Test
	public void testSelfEditAdminAdminBindingResultModelIntMultipartFileByteArray() throws Exception {
		Admin admin = new Admin();
		User user = new User();
		user.setUsername("username");
		admin.setUser(user);		
		when(userService.checkDinstinctLoginWithEditUser("username", 1)).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		
		byte [] image = new byte[100];
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/admin/edit")
				.file(photo)
				.sessionAttr("admin", admin)
				.sessionAttr("editUserId", 1)
				.sessionAttr("image", image))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/admin/edit"));
		
		assertEquals("forward:/message/employee/success", adminController.selfEditAdmin(admin, result, model, 1, photo, image));
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).editAdmin(admin);
		assertEquals("forward:/message/employee/error", adminController.editAdminByOwner(admin, result, model, 1, photo, image));
	}

	@Test
	public void testIndexingDatabase() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/control/indexing"))
		.andExpect(status().isOk())
		.andExpect(view().name("/control/indexing"));
	}

	@Test
	public void testAdjustGeneratorPrimaryKey() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/control/adjusting"))
		.andExpect(status().isOk())
		.andExpect(view().name("/control/adjusting"));
	}

}
