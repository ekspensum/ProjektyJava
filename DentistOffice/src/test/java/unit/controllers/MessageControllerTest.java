package unit.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.MessageController;

public class MessageControllerTest {

	private MockMvc mockMvc;  
	private MessageController messageController;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp");  
        messageController = new MessageController();
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).setViewResolvers(viewResolver).build();
	}

	@Test
	public void testError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/message/error"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/message/error"));
	}

	@Test
	public void testEmployeeSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/message/employee/success"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/message/employee/success"));
	}

	@Test
	public void testEmployeeError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/message/employee/error"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/message/employee/error"));
	}

	@Test
	public void testPatientSuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/message/patient/success"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/message/patient/success"));
	}

	@Test
	public void testPatientError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/message/patient/error"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("/message/patient/error"));
	}

}
