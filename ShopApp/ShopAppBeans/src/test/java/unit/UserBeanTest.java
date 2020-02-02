/**
 * 
 */
package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pl.shopapp.beans.SendEmail;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBean;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Admin;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Role;
import pl.shopapp.entites.SettingsApp;
import pl.shopapp.entites.User;


/**
 * @author user
 *
 */
public class UserBeanTest {
	
	UserBean ub;
	EntityManager em;
	UserTransaction ut;
	User user;
	Customer customer;
	Operator operator;
	SendEmail mail;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		this.em = Mockito.mock(EntityManager.class);
		this.ut = Mockito.mock(UserTransaction.class);
		this.mail = Mockito.mock(SendEmail.class);
		this.ub = new UserBean(em, ut, mail);
		this.user = new User();
		this.customer = new Customer();
		this.operator = new Operator();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	public void tearDown() throws Exception {
		this.em.close();
		this.ut = null;
		this.ub = null;
		this.user = null;
		this.customer = null;
		this.operator = null;
		this.mail = null;
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#addCustomer(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void testAddCustomer() throws IllegalStateException, SecurityException, SystemException {
		String expectedPass = "96be325cf35649f073df921686aae2c";
		Validation valid = new Validation();
		String encryptionPass = valid.stringToCode("Customer11");
		assertEquals(expectedPass, encryptionPass);

		when(mail.sendEmail("email@gmailtest.com", "mailSubject", "mailText")).thenReturn(true);
		assertTrue(ub.addCustomer("login1", "Customer11", "firstName", "lastName", "pesel", "zipCode", "country", "city", "street", "streetNo", "unitNo", "email@gmailtest.com", true, "companyName", "taxNo", "regon"));
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#updateCustomer(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, int)}.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void testUpdateCustomer() throws IllegalStateException, SecurityException, SystemException {
		String expectedPassword = "96be325cf35649f073df921686aae2c";
		Validation valid = new Validation();
		String encryptionPass = valid.stringToCode("Customer11");
		assertEquals(expectedPassword, encryptionPass);

		user.setLogin("login1");
		user.setPassword(encryptionPass);
		user.setId(6);
		user.setActive(true);
		when(this.em.find(User.class, 6)).thenReturn(user);

		customer.setFirstName("firstName");
		customer.setId(2);
		customer.setUser(user);

		@SuppressWarnings("unchecked")
		TypedQuery<Customer> mockedQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("customerQuery", Customer.class)).thenReturn(mockedQuery);
		when(mockedQuery.setParameter("user", user)).thenReturn(mockedQuery);
		when(mockedQuery.getSingleResult()).thenReturn(customer);

		assertTrue(this.ub.updateCustomer("login1234", "Customer11", "firstName1234", "lastName", "pesel", "zipCode",
				"country", "city", "street", "streetNo", "unitNo", "email", true, "companyName", "taxNo", "regon", 6));
		assertEquals("login1234", customer.getUser().getLogin());
		assertNotEquals("login1", customer.getUser().getLogin());
		assertEquals("firstName1234", customer.getFirstName());
		assertEquals(2, customer.getId());
		assertEquals(6, customer.getUser().getId());
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#findCustomer(pl.shopapp.entites.User)}.
	 */
	@Test
	public void testFindCustomer() {
		Customer expected = new Customer();
		@SuppressWarnings("unchecked")
		TypedQuery<Customer> mockedQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("customerQuery", Customer.class)).thenReturn(mockedQuery);
		when(mockedQuery.setParameter("user", user)).thenReturn(mockedQuery);
		when(mockedQuery.getSingleResult()).thenReturn(expected);
		Customer actual = ub.findCustomer(user);
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#setActiveCustomer(int, boolean)}.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void testSetActiveCustomerIdUserArg() throws IllegalStateException, SecurityException, SystemException {
		user.setId(1);
		user.setActive(false);
		when(em.find(User.class, 1)).thenReturn(user);
		assertTrue(ub.setActiveCustomer(1, true));
	}

	@Test
	public void testSetActiveCustomerStringArg() throws IllegalStateException, SecurityException, SystemException {
		List<Customer> cl = new ArrayList<>();
		user.setId(1);
		customer.setUser(user);
		customer.setDateRegistration(LocalDateTime.now().minusMinutes(1));
		cl.add(customer);
		@SuppressWarnings("unchecked")
		TypedQuery<Customer> namedQuery = mock(TypedQuery.class);
		when(namedQuery.setParameter("activationString", "activationString")).thenReturn(namedQuery);
		when(namedQuery.getResultList()).thenReturn(cl);
		when(em.createNamedQuery("activationStringQuery", Customer.class)).thenReturn(namedQuery);
		
		testSetActiveCustomerIdUserArg();
		assertTrue(ub.setActiveCustomer("activationString"));
		
		user = new User();
		user.setId(1);
		customer = new Customer();
		customer.setUser(user);
		customer.setDateRegistration(LocalDateTime.now().minusMinutes(1));
		cl.add(customer);
		
		assertFalse(ub.setActiveCustomer("activationString"));
	}

	
	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#findUser(int)}.
	 */
	@Test
	public void testFindUser() {
		User expected = new User();
		when(this.em.find(User.class, 5)).thenReturn(expected);
		User actual = this.ub.findUser(5);
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#findUserLogin(java.lang.String)}.
	 */
	@Test
	public void testFindUserLogin() {
		@SuppressWarnings("unchecked")
		TypedQuery<User> mockedQuery = mock(TypedQuery.class);
		List<User> mockedUsers = new ArrayList<>();
		mockedUsers.add(user);
		when(mockedQuery.getResultList()).thenReturn(mockedUsers);
		when(mockedQuery.setParameter("login", "operator1")).thenReturn(mockedQuery);
		when(this.em.createNamedQuery("findUserLoginQuery", User.class)).thenReturn(mockedQuery);
		assertTrue(this.ub.findUserLogin("operator1"));
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#loginUser(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLoginUser() {
//		TEST PART 1
		User u1 = new User();
		@SuppressWarnings("unchecked")
		TypedQuery<User> mockedUserQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("loginQuery", User.class)).thenReturn(mockedUserQuery);
		when(mockedUserQuery.setParameter("login", "login1")).thenReturn(mockedUserQuery);
		when(mockedUserQuery.setParameter("password", "Password11")).thenReturn(mockedUserQuery);
		when(mockedUserQuery.getSingleResult()).thenReturn(u1);
		User u2 = null;
		try {
			u2 = em.createNamedQuery("loginQuery", User.class).setParameter("login", "loginA").setParameter("password", "Password11").getSingleResult();
		} catch (Exception e) {
			assertNull(u2);
		}
		User u3 = em.createNamedQuery("loginQuery", User.class).setParameter("login", "login1").setParameter("password", "Password11").getSingleResult();
		assertNotNull(u3);
		
//		TEST PART 2
		User user2 = new User();
		@SuppressWarnings("unchecked")
		TypedQuery<User> mockedUserQuery2 = mock(TypedQuery.class);
		when(em.createNamedQuery("loginQuery", User.class)).thenReturn(mockedUserQuery2);
		when(mockedUserQuery2.setParameter("login", "login2")).thenReturn(mockedUserQuery2);
		when(mockedUserQuery2.setParameter("password", "Password22")).thenReturn(mockedUserQuery2);
		when(mockedUserQuery2.getSingleResult()).thenReturn(user2);
		Role role2 = new Role();
		role2.setId(2);
		user2.setRole(role2);
		
		Customer customer2 = new Customer();
		customer2.setFirstName("customer");
		customer2.setUser(user2);
		@SuppressWarnings("unchecked")
		TypedQuery<Customer> mockedCustomerQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("customerQuery", Customer.class)).thenReturn(mockedCustomerQuery);
		when(mockedCustomerQuery.setParameter("user", user2)).thenReturn(mockedCustomerQuery);
		when(mockedCustomerQuery.getSingleResult()).thenReturn(customer2);
		
		SessionData expected2 = new SessionData();
		expected2.setIdRole(user2.getRole().getId());
		expected2.setFirstName(customer2.getFirstName());
		
		SessionData actual2 = ub.loginUser("login2", "Password22");
		assertEquals(expected2.getIdRole(), actual2.getIdRole());
		assertEquals(expected2.getFirstName(), actual2.getFirstName());
		
//		TEST PART 3
		User user3 = new User();
		@SuppressWarnings("unchecked")
		TypedQuery<User> mockedUserQuery3 = mock(TypedQuery.class);
		when(em.createNamedQuery("loginQuery", User.class)).thenReturn(mockedUserQuery3);
		when(mockedUserQuery3.setParameter("login", "login3")).thenReturn(mockedUserQuery3);
		when(mockedUserQuery3.setParameter("password", "Password33")).thenReturn(mockedUserQuery3);
		when(mockedUserQuery3.getSingleResult()).thenReturn(user3);
		Role role3 = new Role();
		role3.setId(3);
		user3.setRole(role3);
		
		Operator operator3 = new Operator();
		operator3.setFirstName("operator");
		@SuppressWarnings("unchecked")
		TypedQuery<Operator> mockedOperatorQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("operatorQuery", Operator.class)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.setParameter("user", user3)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.getSingleResult()).thenReturn(operator3);
		
		SessionData expected3 = new SessionData();
		expected3.setIdRole(user3.getRole().getId());
		expected3.setFirstName(operator3.getFirstName());
		
		SessionData actual3 = ub.loginUser("login3", "Password33");
		assertEquals(expected3.getIdRole(), actual3.getIdRole());
		assertEquals(expected3.getFirstName(), actual3.getFirstName());
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#getUsersOperatorData()}.
	 */
	@Test
	public void testGetUsersOperatorData() {
		List<User> userOperatorListExpected = new ArrayList<>();
		userOperatorListExpected.add(user);
		@SuppressWarnings("unchecked")
		TypedQuery<User> getUserOperatorQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getUserOperatorQuery", User.class)).thenReturn(getUserOperatorQuery);
		when(getUserOperatorQuery.getResultList()).thenReturn(userOperatorListExpected);
		List<User> userOperatorListActual = ub.getUsersOperatorData();
		assertEquals(userOperatorListExpected, userOperatorListActual);
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#addOperator(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void testAddOperator() throws IllegalStateException, SecurityException, SystemException {
		when(em.find(User.class, 3)).thenReturn(user);

		Admin admin = new Admin();
		admin.setUser(user);
		@SuppressWarnings("unchecked")
		TypedQuery<Admin> mockedQueryAdmin = mock(TypedQuery.class);
		when(em.createNamedQuery("adminLoginQuery", Admin.class)).thenReturn(mockedQueryAdmin);
		when(mockedQueryAdmin.setParameter("user", user)).thenReturn(mockedQueryAdmin);
		when(mockedQueryAdmin.getSingleResult()).thenReturn(admin);

		when(mail.sendEmail("email@gmailtest.com", "mailSubject", "mailText")).thenReturn(true);
		assertTrue(ub.addOperator("firstName", "lastName", "phoneNo", "email@gmailtest.com", "login", "password", 3));
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#getOperatorsData()}.
	 */
	@Test
	public void testGetOperatorsData() {
		List<Operator> operatorListExpected = new ArrayList<>();
		operatorListExpected.add(operator);
		@SuppressWarnings("unchecked")
		TypedQuery<Operator> mockedQuery = mock(TypedQuery.class);
		when(mockedQuery.getResultList()).thenReturn(operatorListExpected);
		when(em.createNamedQuery("getAllOperatorsQuery", Operator.class)).thenReturn(mockedQuery);
		List<Operator> operatorListActual = em.createNamedQuery("getAllOperatorsQuery", Operator.class).getResultList();
		assertEquals(operatorListExpected, operatorListActual);
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#updateOperatorData(int, int, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void testUpdateOperatorData() throws IllegalStateException, SecurityException, SystemException {

		User loginUser = new User();
		loginUser.setId(3);
		loginUser.setLogin("LoginUser456");
		when(em.find(User.class, 3)).thenReturn(loginUser);
		
		Admin loginAdmin = new Admin();
		loginAdmin.setId(1);
		loginAdmin.setUser(loginUser);
		
		@SuppressWarnings("unchecked")
		TypedQuery<Admin> typedQueryAdmin = mock(TypedQuery.class);
		when(em.createNamedQuery("adminLoginQuery", Admin.class)).thenReturn(typedQueryAdmin);
		when(typedQueryAdmin.setParameter("user", loginUser)).thenReturn(typedQueryAdmin);
		when(typedQueryAdmin.getSingleResult()).thenReturn(loginAdmin);
		
		Operator operatorToEdit = new Operator();
		operatorToEdit.setLastName("lastName1234");
		User userToEdit = new User();
		userToEdit.setLogin("login1234");
		operatorToEdit.setUser(userToEdit);
		operatorToEdit.setAdmin(loginAdmin);
		when(em.find(Operator.class, 44)).thenReturn(operatorToEdit);
		
		assertTrue(ub.updateOperatorData(3, 44, "login", true, "firstName", "lastName741", "phoneNo", "email"));
		assertEquals("lastName741", operatorToEdit.getLastName());
		assertEquals("login", operatorToEdit.getUser().getLogin());
		assertEquals("LoginUser456", operatorToEdit.getAdmin().getUser().getLogin());
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#addAdmin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void testAddAdmin() throws IllegalStateException, SecurityException, SystemException {

		when(em.find(User.class, 2)).thenReturn(user);

		Admin admin = new Admin();
		admin.setUser(user);
		@SuppressWarnings("unchecked")
		TypedQuery<Admin> mockedAdminQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("adminLoginQuery", Admin.class)).thenReturn(mockedAdminQuery);
		when(mockedAdminQuery.setParameter("user", user)).thenReturn(mockedAdminQuery);
		when(mockedAdminQuery.getSingleResult()).thenReturn(admin);

		when(mail.sendEmail("email@gmailtest.com", "mailSubject", "mailText")).thenReturn(true);
		assertTrue(ub.addAdmin("firstName", "lastName", "phoneNo", "email@gmailtest.com", "login1", "Password11", 2));
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#loginAdmin(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLoginAdmin() {
		user.setId(2);
		@SuppressWarnings("unchecked")
		TypedQuery<User> mockedUserQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("loginQuery", User.class)).thenReturn(mockedUserQuery);
		when(mockedUserQuery.setParameter("login", "login1")).thenReturn(mockedUserQuery);
		when(mockedUserQuery.setParameter("password", "Password11")).thenReturn(mockedUserQuery);
		when(mockedUserQuery.getSingleResult()).thenReturn(user);
		
		Role role = new Role();
		role.setId(1);
		user.setRole(role);
		
		Admin admin = new Admin();
		admin.setFirstName("firstName");
		admin.setLastName("lastName");
		admin.setUser(user);
		@SuppressWarnings("unchecked")
		TypedQuery<Admin> mockedAdminQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("adminLoginQuery", Admin.class)).thenReturn(mockedAdminQuery);
		when(mockedAdminQuery.setParameter("user", user)).thenReturn(mockedAdminQuery);
		when(mockedAdminQuery.getSingleResult()).thenReturn(admin);
		
		SessionData expectedData = new SessionData();
		expectedData.setIdRole(1);
		expectedData.setIdUser(2);
		expectedData.setFirstName("firstName");
		expectedData.setLastName("Name");
		SessionData actualData = ub.loginAdmin("login1", "Password11");
		assertEquals(expectedData.getIdRole(), actualData.getIdRole());
		assertEquals(expectedData.getIdUser(), actualData.getIdUser());
		assertEquals(expectedData.getFirstName(), actualData.getFirstName());
		assertNotEquals(expectedData.getLastName(), actualData.getLastName());
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#getAdminsData()}.
	 */
	@Test
	public void testGetAdminsData() {
		List<Admin> expected = new ArrayList<>();
		@SuppressWarnings("unchecked")
		TypedQuery<Admin> mockedQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllAdminsQuery", Admin.class)).thenReturn(mockedQuery);
		when(mockedQuery.getResultList()).thenReturn(expected);
		List<Admin> actual = ub.getAdminsData();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#getUsersAdminData()}.
	 */
	@Test
	public void testGetUsersAdminData() {
		List<User> expected = new ArrayList<>();
		@SuppressWarnings("unchecked")
		TypedQuery<User> mockedQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getUserAdminQuery", User.class)).thenReturn(mockedQuery);
		when(mockedQuery.getResultList()).thenReturn(expected);
		List<User> actual = ub.getUsersAdminData();
		assertEquals(expected, actual);
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#updateAdminData(int, int, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void testUpdateAdminData() throws IllegalStateException, SecurityException, SystemException {
		User loginUser = new User();
		loginUser.setId(1);
		loginUser.setLogin("LoginUser789");
		when(em.find(User.class, 1)).thenReturn(loginUser);
		
		Admin loginAdmin = new Admin();
		loginAdmin.setId(1);
		loginAdmin.setUser(loginUser);
		
		@SuppressWarnings("unchecked")
		TypedQuery<Admin> typedQueryAdmin = mock(TypedQuery.class);
		when(em.createNamedQuery("adminLoginQuery", Admin.class)).thenReturn(typedQueryAdmin);
		when(typedQueryAdmin.setParameter("user", loginUser)).thenReturn(typedQueryAdmin);
		when(typedQueryAdmin.getSingleResult()).thenReturn(loginAdmin);
		
		Admin adminToEdit = new Admin();
		adminToEdit.setLastName("lastName1234");
		
		User userToEdit = new User();
		userToEdit.setLogin("login1234");
		adminToEdit.setUser(userToEdit);
		adminToEdit.setAdmin(loginAdmin);
		when(em.find(Admin.class, 13)).thenReturn(adminToEdit);
		
		assertTrue(ub.updateAdminData(1, 13, "login1", true, "firstName", "lastName", "phoneNo", "email"));
		assertEquals("lastName", adminToEdit.getLastName());
		assertEquals("login1", adminToEdit.getUser().getLogin());
		assertEquals("LoginUser789", adminToEdit.getAdmin().getUser().getLogin());
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#getSettingsApp()}.
	 */
	@Test
	public void testGetSettingsApp() {
		SettingsApp settingExpected = new SettingsApp();
		when(em.find(SettingsApp.class, 1)).thenReturn(settingExpected);
		SettingsApp actualSetting = ub.getSettingsApp();
		assertEquals(settingExpected, actualSetting);
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#setSettingsApp(int, int, int, int, int, int, int, int)}.
	 * @throws SystemException 
	 * @throws SecurityException 
	 * @throws IllegalStateException 
	 */
	@Test
	public void testSetSettingsApp() throws IllegalStateException, SecurityException, SystemException {
//		TEST PART 1
		when(em.find(SettingsApp.class, 1)).thenReturn(null);
		assertTrue(ub.setSettingsApp(5, 12, 1, 2, 6, 15, 30, 1));
		
//		TEST PART 2
		SettingsApp settingsApp = new SettingsApp();
		settingsApp.setMaxCharInLogin(33);
		settingsApp.setId(1);
		when(em.find(SettingsApp.class, 1)).thenReturn(settingsApp);
		assertTrue(ub.setSettingsApp(5, 12, 1, 2, 6, 15, 30, 1));
		assertEquals(15, ub.getSettingsApp().getMaxCharInLogin());
		assertEquals(15, settingsApp.getMaxCharInLogin());
	}
	
	@Test
	public void testAddRole() {
		assertTrue(ub.addRole("roleName"));
	}
	
	@Test
	public void testGetRoleList() {
		List<Role> rl = new ArrayList<>();
		@SuppressWarnings("unchecked")
		TypedQuery<Role> roleQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("roleQuery", Role.class)).thenReturn(roleQuery);
		when(roleQuery.getResultList()).thenReturn(rl);
		assertEquals(rl, ub.getRoleList());
	}
	
	@Test
	public void testFindCustomerList() {
		List<Customer> cl = new ArrayList<>();
		@SuppressWarnings("unchecked")
		TypedQuery<Customer> customerQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("customerByLastNamePeselQuery", Customer.class)).thenReturn(customerQuery);
		when(customerQuery.setParameter("lastName", "%lastName%")).thenReturn(customerQuery);
		when(customerQuery.setParameter("pesel", "%pesel%")).thenReturn(customerQuery);
		when(customerQuery.getResultList()).thenReturn(cl);
		assertEquals(cl, ub.findCustomerList("lastName", "pesel"));
	}
}
