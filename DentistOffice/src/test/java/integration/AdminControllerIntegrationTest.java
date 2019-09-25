package integration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.config.WebConfig;
import pl.dentistoffice.controller.AdminController;
import pl.dentistoffice.entity.Role;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.InitApplicationService;
import pl.dentistoffice.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebConfig.class, TestMvcConfig.class})
public class AdminControllerIntegrationTest {
	
//    @Autowired
//    private WebApplicationContext wac;
	
	private MockMvc mockMvc;    
    private AdminController adminController;
    
    @Autowired
    private UserService userService;
    @Autowired
    private HibernateSearchService searchsService;
    @Autowired
    private InitApplicationService initApplicationService;
	@Autowired
	private Environment env;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");       
        
        adminController = new AdminController(env, userService, searchsService, initApplicationService);
		mockMvc = MockMvcBuilders.standaloneSetup(adminController).setViewResolvers(viewResolver).build();
		
//        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
//        this.mockMvc = builder.build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRegistrationAdmin() throws Exception {
		
//		mockMvc.perform(get("/users/admin/owner/register"))
//				.andExpect(status().isOk())
//				.andExpect(view().name("/users/admin/owner/register"));
		
		List<Role> roles = new ArrayList<Role>();
		when(userService.getAllRolesWithoutId(5)).thenReturn(roles);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users/admin/owner/register"))
		.andExpect(status().isOk())
		.andExpect(view().name("/users/admin/owner/register"));
	}

	
}
