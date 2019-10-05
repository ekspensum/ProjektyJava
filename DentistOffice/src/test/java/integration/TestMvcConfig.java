package integration;

import java.io.IOException;
import java.net.URISyntaxException;

import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pl.dentistoffice.dao.TreatmentRepository;
import pl.dentistoffice.dao.TreatmentRepositoryHibernatePostgreSQLImpl;
import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.dao.UserRepositoryHibernatePostgreSQLImpl;
import pl.dentistoffice.dao.VisitRepository;
import pl.dentistoffice.dao.VisitRepositoryHibernatePostgreSQLImpl;
import pl.dentistoffice.service.ActivationService;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.EmailContactService;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.InitApplicationService;
import pl.dentistoffice.service.NotificationService;
import pl.dentistoffice.service.ReCaptchaService;
import pl.dentistoffice.service.SendEmail;
import pl.dentistoffice.service.SendEmailGoogleService;
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Configuration
@ComponentScan(basePackages = {"pl.dentistoffice.controller"})
@EnableWebMvc
public class TestMvcConfig implements WebMvcConfigurer {
	
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
//	}

	@Bean
	public CommonsMultipartResolver multipartResolver() throws IOException {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    return multipartResolver;
	}
	
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        return messageSource;
    }
    
//    @Bean
//    public TilesConfigurer tilesConfigurer(){
//        TilesConfigurer tilesConfigurer = new TilesConfigurer();
//        tilesConfigurer.setDefinitions(new String[] {"/WEB-INF/views/**/tiles.xml"});
//        tilesConfigurer.setCheckRefresh(true);
//        return tilesConfigurer;
//    }
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
//		WebMvcConfigurer.super.configureViewResolvers(registry);
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/pages/");
        viewResolver.setSuffix(".jsp"); 
		
        registry.viewResolver(viewResolver);
        
//      TilesViewResolver tilesViewResolver = new TilesViewResolver();
//		registry.viewResolver(tilesViewResolver);
	}

	@Bean
	public UserService userService() throws URISyntaxException {
		return new UserService();
	}
	
	@Bean
	public UserRepository userRepository() throws URISyntaxException {
		return new UserRepositoryHibernatePostgreSQLImpl();
	}

	@Bean
	public NotificationService notificationService() {
//		return new NotificationService();
		return Mockito.mock(NotificationService.class);
	}
	
	@Bean
	public ActivationService activationService() {
		return new ActivationService();
	}
	
	@Bean
	public HibernateSearchService hibernateSearchService() {
		return new HibernateSearchService();
	}
	
	@Bean
	public SendEmail sendEmail() {
//		return new SendEmailGoogleService();
		return Mockito.mock(SendEmailGoogleService.class);
	}
	
	@Bean
	public InitApplicationService initApplicationService() {
		return new InitApplicationService();
	}
	
	@Bean
	public VisitRepository visitRepository() {
		return new VisitRepositoryHibernatePostgreSQLImpl();
	}
	
	@Bean
	public TreatmentRepository treatmentRepository() {
		return new TreatmentRepositoryHibernatePostgreSQLImpl();
	}
	
	@Bean
	public EmailContactService emailContactService() {
		return new EmailContactService();
	}
	
	@Bean
	public ReCaptchaService reCaptchaService() {
		return new ReCaptchaService();
	}
	
	@Bean
	public DentalTreatmentService dentalTreatmentService() {
		return new DentalTreatmentService();
	}
	
	@Bean
	public VisitService visitService() {
		return new VisitService();
	}

}
