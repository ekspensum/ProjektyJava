package unit.controllers;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.EmployeeController;

public class EmployeeControllerTest {
	
	private MockMvc mockMvc;  
	private EmployeeController employeeController;

	@Before
	public void setUp() throws Exception {
		employeeController = new EmployeeController();
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");  
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testAdminPanel() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/panels/employeePanel"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("employeePanel"));
	}

}
