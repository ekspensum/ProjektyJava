package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

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

import pl.dentistoffice.controller.AssistantController;
import pl.dentistoffice.entity.Assistant;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.service.UserService;

public class AssistantControllerTest {
	
	private MockMvc mockMvc;    
    private AssistantController assistantController;
    
    @Mock
    private UserService userService;
	@Mock
	private Environment env;
	@Mock
	private Model model;
	@Mock
	private BindingResult result;
	@Mock
	private RedirectAttributes redirectAttributes;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");       
        
        assistantController = new AssistantController(env, userService);
		mockMvc = MockMvcBuilders.standaloneSetup(assistantController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testAssistantPanel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/panels/assistantPanel"))
		.andExpect(status().isOk())
		.andExpect(view().name("assistantPanel"));
	}

	@Test
	public void testRegistrationAssistantByAdminModel() throws Exception {
		List<Role> roles = new ArrayList<Role>();
		when(userService.getAllRolesWithoutId(5)).thenReturn(roles);		
		
		assertEquals("/users/assistant/admin/register", assistantController.registrationAssistantByAdmin(model));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users/assistant/admin/register"))
		.andExpect(status().isOk())
		.andExpect(view().name("/users/assistant/admin/register"));
		
	}

	@Test
	public void testRegistrationAssistantByAdminAssistantBindingResultModelMultipartFile() throws Exception {
		Assistant assistant = new Assistant();
		User user = new User();
		user.setUsername("username");
		assistant.setUser(user);		
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/assistant/admin/register")
				.file(photo)
				.sessionAttr("assistant", assistant))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/assistant/admin/register"));
		
		assertEquals("forward:/message/employee/success", assistantController.registrationAssistantByAdmin(assistant, result, model, photo));
		
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(false);
		assertEquals("/users/assistant/admin/register", assistantController.registrationAssistantByAdmin(assistant, result, model, photo));
		
		when(userService.checkDinstinctLoginWithRegisterUser("username")).thenReturn(true);	
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).addNewAssistant(assistant);
		assertEquals("forward:/message/employee/error", assistantController.registrationAssistantByAdmin(assistant, result, model, photo));

	}

	@Test
	public void testSelectAssistantToEditByAdminModel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/users/assistant/admin/selectToEdit"))
		.andExpect(status().isOk())
		.andExpect(view().name("/users/assistant/admin/selectToEdit"));
		
		assertEquals("/users/assistant/admin/selectToEdit", assistantController.selectAssistantToEditByAdmin(model));
	}

	@Test
	public void testSelectAssistantToEditByAdminStringRedirectAttributes() throws Exception {
		User user = new User();
		user.setId(1);
		Assistant assistant = new Assistant();
		assistant.setId(1);
		assistant.setUser(user);
		when(userService.getAssistant(1)).thenReturn(assistant);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users/assistant/admin/selectToEdit")
				.param("assistantId", "1"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/users/assistant/admin/edit"));
		
		assertEquals("redirect:/users/assistant/admin/edit", assistantController.selectAssistantToEditByAdmin("1", redirectAttributes));
	}

	@Test
	public void testEditAssistantByAdmin() throws Exception {
		byte [] imageExpect = new byte[33];
		Assistant assistant = new Assistant();
		assistant.setPhoto(imageExpect);
		
		byte [] imageActual = (byte[]) mockMvc.perform(get("/users/assistant/admin/edit")
				.sessionAttr("assistant", assistant))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/assistant/admin/edit"))
				.andReturn().getRequest().getAttribute("image");
		assertEquals(33, imageActual.length);
	}

	@Test
	public void testEditDataAssistantByAdmin() throws Exception {
		Assistant assistant = new Assistant();
		User user = new User();
		user.setUsername("username");
		assistant.setUser(user);		
		when(userService.checkDinstinctLoginWithEditUser("username", 22)).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		
		byte [] image = new byte[133];
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/assistant/admin/edit")
				.file(photo)
				.sessionAttr("assistant", assistant)
				.sessionAttr("editUserId", 22)
				.sessionAttr("image", image))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/assistant/admin/edit"));
		assertEquals("forward:/message/employee/success", assistantController.editDataAssistantByAdmin(assistant, result, model, 22, photo, image));
		
		Role role = new Role();
		List<Role> rolesExpected = new ArrayList<Role>();
		rolesExpected.add(role);
		when(userService.getAllRoles()).thenReturn(rolesExpected);
		@SuppressWarnings("unchecked")
		List<Role> rolesActual = (List<Role>) mockMvc.perform(multipart("/users/assistant/admin/edit")
				.file(photo)
				.sessionAttr("assistant", assistant)
				.sessionAttr("editUserId", 22)
				.sessionAttr("image", image))
				.andDo(print()).andReturn().getRequest().getAttribute("rolesList");
		assertEquals(rolesExpected.toString(), rolesActual.toString());
		
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).editAssistant(assistant);
		assertEquals("forward:/message/employee/error", assistantController.editDataAssistantByAdmin(assistant, result, model, 22, photo, image));
	}

	@Test
	public void testSelfEditAssistant() throws Exception {
		byte [] imageExpect = new byte[44];
		User user = new User();
		user.setId(11);
		Assistant assistant = new Assistant();
		assistant.setPhoto(imageExpect);
		assistant.setUser(user);
		when(userService.getLoggedAssistant()).thenReturn(assistant);
		
		byte [] imageActual = (byte[]) mockMvc.perform(get("/users/assistant/edit"))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/assistant/edit"))
				.andReturn().getRequest().getAttribute("image");
		assertEquals(44, imageActual.length);
	}

	@Test
	public void testSelfEditDataAssistant() throws Exception {
		Assistant assistant = new Assistant();
		User user = new User();
		user.setUsername("username");
		assistant.setUser(user);		
		when(userService.checkDinstinctLoginWithEditUser(user.getUsername(), 22)).thenReturn(true);		
		MockMultipartFile photo = new MockMultipartFile("photo", "photo".getBytes());
		
		byte [] image = new byte[133];
		mockMvc.perform(MockMvcRequestBuilders.multipart("/users/assistant/edit")
				.file(photo)
				.sessionAttr("assistant", assistant)
				.sessionAttr("editUserId", 22)
				.sessionAttr("image", image))
				.andExpect(status().isOk())
				.andExpect(view().name("/users/assistant/edit"));
		assertEquals("forward:/message/employee/success", assistantController.editDataAssistantByAdmin(assistant, result, model, 22, photo, image));
		
		when(userService.checkDinstinctLoginWithEditUser(user.getUsername(), 22)).thenReturn(true);
		assertEquals("forward:/message/employee/success", assistantController.editDataAssistantByAdmin(assistant, result, model, 22, photo, image));
		doThrow(new Exception("Zaplanowany wyjątek")).when(userService).editAssistant(assistant);
		assertEquals("forward:/message/employee/error", assistantController.editDataAssistantByAdmin(assistant, result, model, 22, photo, image));
	}

}
