package integration;

import static org.mockito.Mockito.when;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
import pl.dentistoffice.service.UserService;
import pl.dentistoffice.service.VisitService;

@Configuration
@ComponentScan(basePackages = {"pl.dentistoffice.controller"})
@EnableWebMvc
public class TestMvcConfig implements WebMvcConfigurer {
	
	@Bean
	public UserService userService() {
		HibernateSearchService searchsService = Mockito.mock(HibernateSearchService.class);
		Environment env = Mockito.mock(Environment.class);
		SendEmail sendEmail = Mockito.mock(SendEmail.class);
		SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
		Session session = Mockito.mock(Session.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);

		UserRepositoryHibernatePostgreSQLImpl userRepository = new UserRepositoryHibernatePostgreSQLImpl(sessionFactory);
		ActivationService activationService = new ActivationService(env, sendEmail);
		NotificationService notificationService = new NotificationService(env, sendEmail); 
		
		return new UserService(userRepository, notificationService, activationService, searchsService);
//		return new UserService();
	}
	
	@Bean
	public UserRepository userRepository() {
		SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
		Session session = Mockito.mock(Session.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		
		return new UserRepositoryHibernatePostgreSQLImpl(sessionFactory);
//		return new UserRepositoryHibernatePostgreSQLImpl();
	}
	
	@Bean
	public SessionFactory sessionFactory() {
//		SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
//		Session session = Mockito.mock(Session.class);
//		when(sessionFactory.getCurrentSession()).thenReturn(session);
		return Mockito.mock(SessionFactory.class);
	}
	
	@Bean
	public NotificationService notificationService() {
//		Environment env = Mockito.mock(Environment.class);
//		SendEmail sendEmail = Mockito.mock(SendEmail.class);
		return new NotificationService();
	}
	
	@Bean
	public ActivationService activationService() {
//		Environment env = Mockito.mock(Environment.class);
//		SendEmail sendEmail = Mockito.mock(SendEmail.class);
		return new ActivationService();
	}
	
	@Bean
	public HibernateSearchService hibernateSearchService() {
		return Mockito.mock(HibernateSearchService.class);
	}
	
	@Bean
	public SendEmail sendEmail() {
		return Mockito.mock(SendEmail.class);
	}
	
	@Bean
	public InitApplicationService initApplicationService() {
		return Mockito.mock(InitApplicationService.class);
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

	@Bean
	public BasicDataSource dataSource() {
		return Mockito.mock(BasicDataSource.class);
	}
}
