package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
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

import pl.dentistoffice.controller.LoginController;
import pl.dentistoffice.service.UserService;

public class LoginControllerTest {
	
	private MockMvc mockMvc;  
	private LoginController loginController;
	
	@Mock
	private Environment env;
	@Mock
	private UserService userService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp"); 
        
        loginController = new LoginController(env, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).setViewResolvers(viewResolver).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLogin() throws Exception {
		when(env.getProperty("loginError")).thenReturn("msg");
		String msg1 = (String) mockMvc.perform(MockMvcRequestBuilders.get("/login")
				.param("logout", (String) null)
				.param("error", "logout"))
		.andExpect(status().isOk())
		.andExpect(view().name("login"))
		.andReturn().getRequest().getAttribute("msg");
		assertTrue(msg1.equals("msg"));
		
		when(env.getProperty("logout")).thenReturn("msg");
		String msg2 = (String) mockMvc.perform(MockMvcRequestBuilders.get("/login")
				.param("logout", "logout")
				.param("error", (String) null))
		.andExpect(status().isOk())
		.andExpect(view().name("login"))
		.andReturn().getRequest().getAttribute("msg");
		assertTrue(msg2.equals("msg"));
	}

	@Test
	public void testLoginSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/loginSuccess"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("forward:/panels/employeePanel"));
	}

	@Test
	public void testGet403() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/403"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("login"));
	}

}
