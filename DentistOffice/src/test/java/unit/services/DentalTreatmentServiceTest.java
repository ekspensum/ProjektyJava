package unit.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.dentistoffice.dao.TreatmentRepository;
import pl.dentistoffice.dao.TreatmentRepositoryHibernatePostgreSQLImpl;
import pl.dentistoffice.dao.UserRepository;
import pl.dentistoffice.dao.UserRepositoryHibernatePostgreSQLImpl;
import pl.dentistoffice.entity.DentalTreatment;
import pl.dentistoffice.entity.TreatmentCategory;
import pl.dentistoffice.entity.User;
import pl.dentistoffice.service.ActivationService;
import pl.dentistoffice.service.DentalTreatmentService;
import pl.dentistoffice.service.HibernateSearchService;
import pl.dentistoffice.service.NotificationService;
import pl.dentistoffice.service.SendEmail;
import pl.dentistoffice.service.UserService;

public class DentalTreatmentServiceTest {

	private UserService userService;
	private UserRepository userRepository;
	private SessionFactory sessionFactory;
	private Session session;
	private NotificationService notificationService;
	private Environment env;
	private SendEmail sendEmail;
	private SecurityContext securityContext;
	private Authentication authentication;
	private ActivationService activationService;
	private HibernateSearchService searchsService;
	private TreatmentRepository treatmentRepository;
	private DentalTreatmentService dentalTreatmentService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		sessionFactory = Mockito.mock(SessionFactory.class);
		session = Mockito.mock(Session.class);
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		userRepository = new UserRepositoryHibernatePostgreSQLImpl(sessionFactory);
		env = Mockito.mock(Environment.class);
		sendEmail = Mockito.mock(SendEmail.class);
		notificationService = new NotificationService(env, sendEmail);
		when(env.getProperty("mailFrom")).thenReturn("email");
		when(env.getProperty("host")).thenReturn("host");
		activationService = new ActivationService(env, sendEmail);
		searchsService = Mockito.mock(HibernateSearchService.class);
		userService = new UserService(userRepository, notificationService, activationService, searchsService);	
		securityContext = Mockito.mock(SecurityContext.class);
		authentication = Mockito.mock(Authentication.class);
		treatmentRepository = new TreatmentRepositoryHibernatePostgreSQLImpl(sessionFactory);
		dentalTreatmentService = new DentalTreatmentService(treatmentRepository, userService, searchsService);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddNewDentalTreatment() {
		DentalTreatment dentalTreatment = new DentalTreatment();
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login";
		when(authentication.getName()).thenReturn(username);
		User user = new User();
		user.setUsername(username);
		@SuppressWarnings("unchecked")
		Query<User> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findUserByUserName", User.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(user);
		boolean addNewDentalTreatmentConfirm = dentalTreatmentService.addNewDentalTreatment(dentalTreatment);
		
		assertTrue(addNewDentalTreatmentConfirm);
		assertEquals("login", dentalTreatment.getUserLogged().getUsername());
		assertEquals(LocalDateTime.now().withNano(0), dentalTreatment.getRegisteredDateTime().withNano(0));
	}

	@Test
	public void testEditDentalTreatment() {
		DentalTreatment dentalTreatment = new DentalTreatment();
		
		List<TreatmentCategory> categoryList = new ArrayList<>();
		TreatmentCategory treatmentCategory1 = new TreatmentCategory();
		treatmentCategory1.setId(1);
		TreatmentCategory treatmentCategory2 = new TreatmentCategory();
		treatmentCategory2.setId(2);
		categoryList.add(treatmentCategory1);
		categoryList.add(treatmentCategory2);
		dentalTreatment.setTreatmentCategory(categoryList);
		
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login";
		when(authentication.getName()).thenReturn(username);
		User user = new User();
		user.setUsername(username);
		@SuppressWarnings("unchecked")
		Query<User> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findUserByUserName", User.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(user);
		boolean addNewDentalTreatmentConfirm = dentalTreatmentService.editDentalTreatment(dentalTreatment);
		
		assertTrue(addNewDentalTreatmentConfirm);
		assertEquals("login", dentalTreatment.getUserLogged().getUsername());
		assertEquals(2, dentalTreatment.getTreatmentCategory().get(1).getId());
		assertEquals(LocalDateTime.now().withNano(0), dentalTreatment.getEditedDateTime().withNano(0));
	}

	@Test
	public void testGetDentalTreatmentsList() {
		DentalTreatment dentalTreatment1 = new DentalTreatment();
		dentalTreatment1.setId(1);
		DentalTreatment dentalTreatment2 = new DentalTreatment();
		dentalTreatment2.setId(2);
		List<DentalTreatment> dentalTreatmentsList = new ArrayList<>();
		dentalTreatmentsList.add(dentalTreatment1);
		dentalTreatmentsList.add(dentalTreatment2);
		
		@SuppressWarnings("unchecked")
		Query<DentalTreatment> query = Mockito.mock(Query.class);
		when(session.createQuery("from DentalTreatment", DentalTreatment.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(dentalTreatmentsList);
		
		List<DentalTreatment> dentalTreatmentsListActual = dentalTreatmentService.getDentalTreatmentsList();
		
		assertEquals(1, dentalTreatmentsListActual.get(0).getId());
		assertEquals(2, dentalTreatmentsListActual.get(1).getId());
	}

	@Test
	public void testGetDentalTreatment() {
		DentalTreatment dentalTreatment = new DentalTreatment();
		dentalTreatment.setId(13);
		when(session.find(DentalTreatment.class, 13)).thenReturn(dentalTreatment);
		DentalTreatment dentalTreatmentActual = dentalTreatmentService.getDentalTreatment(13);
		
		assertEquals(13, dentalTreatmentActual.getId());
	}

	@Test
	public void testSearchDentalTreatment() {
		DentalTreatment dentalTreatment1 = new DentalTreatment();
		dentalTreatment1.setName("name1");
		DentalTreatment dentalTreatment2 = new DentalTreatment();
		dentalTreatment2.setName("name2");
		List<DentalTreatment> dentalTreatmentsList = new ArrayList<>();
		dentalTreatmentsList.add(dentalTreatment1);
		dentalTreatmentsList.add(dentalTreatment2);
		when(searchsService.searchDentalTreatmentNameDescriptionByKeywordQuery("text")).thenReturn(dentalTreatmentsList);
		
		List<DentalTreatment> searchDentalTreatment = dentalTreatmentService.searchDentalTreatment("text");
		
		assertEquals("name1", searchDentalTreatment.get(0).getName());
		assertEquals("name2", searchDentalTreatment.get(1).getName());
	}

	@Test
	public void testAddTreatmentCategory() {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		treatmentCategory.setCategoryName("categoryName");
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login";
		when(authentication.getName()).thenReturn(username);
		User user = new User();
		user.setUsername(username);
		@SuppressWarnings("unchecked")
		Query<User> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findUserByUserName", User.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(user);
		
		boolean addTreatmentCategoryConfirm = dentalTreatmentService.addTreatmentCategory(treatmentCategory);
		
		assertTrue(addTreatmentCategoryConfirm);
		assertEquals("login", treatmentCategory.getUserLogged().getUsername());
		assertEquals(LocalDateTime.now().withNano(0), treatmentCategory.getRegisteredDateTime().withNano(0));
	}

	@Test
	public void testGetTreatmentCategoriesList() {
		List<TreatmentCategory> categoryList = new ArrayList<>();
		TreatmentCategory treatmentCategory1 = new TreatmentCategory();
		treatmentCategory1.setId(1);
		TreatmentCategory treatmentCategory2 = new TreatmentCategory();
		treatmentCategory2.setId(2);
		categoryList.add(treatmentCategory1);
		categoryList.add(treatmentCategory2);
		
		@SuppressWarnings("unchecked")
		Query<TreatmentCategory> query = Mockito.mock(Query.class);
		when(session.createQuery("from TreatmentCategory", TreatmentCategory.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(categoryList);
		
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesList();
		
		assertEquals(1, treatmentCategoriesList.get(0).getId());
		assertEquals(2, treatmentCategoriesList.get(1).getId());
	}

	@Test
	public void testGetTreatmentCategoriesListToEdit() {
		List<TreatmentCategory> categoryList = new ArrayList<>();
		TreatmentCategory treatmentCategory1 = new TreatmentCategory();
		treatmentCategory1.setId(2);
		TreatmentCategory treatmentCategory2 = new TreatmentCategory();
		treatmentCategory2.setId(3);
		categoryList.add(treatmentCategory1);
		categoryList.add(treatmentCategory2);
		
		@SuppressWarnings("unchecked")
		Query<TreatmentCategory> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findTreatmentCategoryAboveFirstId", TreatmentCategory.class)).thenReturn(query);
		when(query.getResultList()).thenReturn(categoryList);
		
		List<TreatmentCategory> treatmentCategoriesList = dentalTreatmentService.getTreatmentCategoriesListToEdit();
		
		assertEquals(2, treatmentCategoriesList.get(0).getId());
		assertEquals(3, treatmentCategoriesList.get(1).getId());
	}

	@Test
	public void testGetTreatmentCategory() {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		treatmentCategory.setId(13);
		when(session.find(TreatmentCategory.class, 13)).thenReturn(treatmentCategory);
		
		TreatmentCategory treatmentCategoryActual = dentalTreatmentService.getTreatmentCategory(13);
		
		assertEquals(13, treatmentCategoryActual.getId());
	}

	@Test
	public void testEditTreatmentCategory() {
		TreatmentCategory treatmentCategory = new TreatmentCategory();
		treatmentCategory.setCategoryName("categoryName");
		
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String username = "login2";
		when(authentication.getName()).thenReturn(username);
		User user = new User();
		user.setUsername(username);
		@SuppressWarnings("unchecked")
		Query<User> query = Mockito.mock(Query.class);
		when(session.createNamedQuery("findUserByUserName", User.class)).thenReturn(query);
		when(query.setParameter("username", username)).thenReturn(query);
		when(query.getSingleResult()).thenReturn(user);
		
		boolean editTreatmentCategoryConfirm = dentalTreatmentService.addTreatmentCategory(treatmentCategory);
		
		assertTrue(editTreatmentCategoryConfirm);
		assertEquals("login2", treatmentCategory.getUserLogged().getUsername());
		assertEquals(LocalDateTime.now().withNano(0), treatmentCategory.getRegisteredDateTime().withNano(0));
	}

}
