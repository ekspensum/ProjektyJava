package unit.controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.controller.ContactUsController;
import pl.dentistoffice.service.EmailContactService;
import pl.dentistoffice.service.ReCaptchaService;
import pl.dentistoffice.service.SendEmail;

public class ContactUsControllerTest {
	
	private MockMvc mockMvc;    
    private ContactUsController contactUsController;
    private EmailContactService emailContactService;
    
    @Mock
    private SendEmail sendEmail;
    @Mock
    private ReCaptchaService reCaptchaService;
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

        emailContactService = new EmailContactService();
        emailContactService.setMessage("message123");
        emailContactService.setReplyMail("replyMail@gmail.com");
        emailContactService.setSubject("subject");
        emailContactService.setAttachment("attachment".getBytes());
        
        contactUsController = new ContactUsController(env, emailContactService, sendEmail, reCaptchaService);
		mockMvc = MockMvcBuilders.standaloneSetup(contactUsController).setViewResolvers(viewResolver).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testShowContactPage() throws Exception {
		EmailContactService emailContactServiceActual = (EmailContactService) mockMvc
							.perform(MockMvcRequestBuilders.get("/contact"))
							.andExpect(MockMvcResultMatchers.status().isOk())
							.andExpect(MockMvcResultMatchers.view().name("contact"))
							.andReturn().getRequest().getAttribute("emailContactService");
		
		assertEquals("message123", emailContactServiceActual.getMessage());
	}

	@Test
	public void testSendMessage() throws Exception {
		when(reCaptchaService.verify("reCaptchaResponse")).thenReturn(true);
		MockMultipartFile file = new MockMultipartFile("file", "file".getBytes());
		
        EmailContactService emailContactService2 = new EmailContactService();
        emailContactService2.setMessage("message123");
        emailContactService2.setReplyMail("replyMail@gmail.com");
        emailContactService2.setSubject("subject");
        emailContactService2.setAttachment("attachment".getBytes());
		
		mockMvc.perform(MockMvcRequestBuilders.multipart("/contact")
				.file("attachment", "attachment".getBytes())
				.sessionAttr("emailContactService", emailContactService2)
				.param("g-recaptcha-response", "reCaptchaResponse"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("contact"));
		
		assertEquals("contact", contactUsController.sendMessage(emailContactService2, result, file, model, "reCaptchaResponse"));
	}

}
