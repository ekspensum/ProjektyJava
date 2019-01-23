/**
 * 
 */
package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import pl.shopapp.beans.UserBean;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Role;
import pl.shopapp.entites.User;
import pl.shopapp.entites.UserRole;

/**
 * @author user
 *
 */
public class UserBeanTest {
	
	UserBean ub;
	EntityManager em;
	UserTransaction ut;
	List<User> mockedUsers;
	User user;
	Customer customer;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.em = Mockito.mock(EntityManager.class);
		this.ut = Mockito.mock(UserTransaction.class);
		this.ub = new UserBean(em, ut);
		user = new User();
		customer = new Customer();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#addCustomer(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddCustomer() {
		String expectedPass = "96be325cf35649f073df921686aae2c";
		try {
			ut.begin();
			Validation valid = new Validation();
			String encryptionPass = valid.passwordToCode("Customer11");
			assertEquals(expectedPass, encryptionPass);

			user.setLogin("login");
			user.setPassword(encryptionPass);
			user.setId(6);
			user.setActive(true);
			customer.setFirstName("firstName");
			customer.setLastName("lastName");
			customer.setPesel("pesel");
			customer.setZipCode("zipCode");
			customer.setCountry("country");
			customer.setCity("city");
			customer.setStreet("street");
			customer.setStreetNo("streetNo");
			customer.setUnitNo("unitNo");
			customer.setEmail("email");
			customer.setCompany(true);
			customer.setCompanyName("companyName");
			customer.setTaxNo("taxNo");
			customer.setRegon("regon");
			customer.setId(2);
			customer.setDateRegistration(LocalDateTime.now());
			customer.setUser(user);
			
			UserRole ur = ub.addUserRole(user, 2);
			em.persist(user);
			em.persist(customer);
			em.persist(ur);
			
			ut.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		assertTrue(this.ub.addCustomer("login", "Customer11", "firstName", "lastName", "pesel", "zipCode", "country", "city", "street", "streetNo", "unitNo", "email", true, "companyName", "taxNo", "regon"));
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#updateCustomer(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testUpdateCustomer() {
		String expectedPass = "96be325cf35649f073df921686aae2c";
		Query mockedUserQuery = Mockito.mock(Query.class);
		Query mockedCustomerQuery = Mockito.mock(Query.class);
		try {
			ut.begin();
			Validation valid = new Validation();
			String encryptionPass = valid.passwordToCode("Customer11");
			assertEquals(expectedPass, encryptionPass);

			user.setLogin("login");
			user.setPassword(encryptionPass);
			user.setId(6);
			user.setActive(true);
			when(this.em.find(User.class, 6)).thenReturn(user);
			
			customer.setFirstName("firstName");
			customer.setLastName("lastName");
			customer.setPesel("pesel");
			customer.setZipCode("zipCode");
			customer.setCountry("country");
			customer.setCity("city");
			customer.setStreet("street");
			customer.setStreetNo("streetNo");
			customer.setUnitNo("unitNo");
			customer.setEmail("email");
			customer.setCompany(true);
			customer.setCompanyName("companyName");
			customer.setTaxNo("taxNo");
			customer.setRegon("regon");
			customer.setId(2);
			customer.setUser(user);
			
			@SuppressWarnings("unchecked")
			TypedQuery<Customer> mockedQuery = mock(TypedQuery.class);
			when(em.createNamedQuery("customerQuery", Customer.class)).thenReturn(mockedQuery);
			when(mockedQuery.setParameter("user", user)).thenReturn(mockedQuery);
			when(mockedQuery.getSingleResult()).thenReturn(customer);
			
			String updateUserQuery = "UPDATE User SET login = 'login', password = '96be325cf35649f073df921686aae2c' WHERE id = 6";
			String updateCustomerQuery = "UPDATE Customer SET firstName = 'firstName', lastName = 'lastName', pesel = 'pesel', country = 'country', zipCode = 'zipCode', city = 'city', street = 'street', streetNo = 'streetNo', unitNo = 'unitNo', email = 'email', company = true, companyName = 'companyName', taxNo = 'taxNo', regon = 'regon' WHERE id = 2";
			
			when(em.createQuery(updateUserQuery)).thenReturn(mockedUserQuery);
			when(mockedUserQuery.executeUpdate()).thenReturn(1);
			when(em.createQuery(updateCustomerQuery)).thenReturn(mockedCustomerQuery);
			when(mockedCustomerQuery.executeUpdate()).thenReturn(1);
			
			ut.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		assertTrue(this.ub.updateCustomer("login", "Customer11", "firstName", "lastName", "pesel", "zipCode", "country", "city", "street", "streetNo", "unitNo", "email", true, "companyName", "taxNo", "regon", 6));
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
	 */
	@Test
	public void testSetActiveCustomer() {
		fail("Not yet implemented");
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
		mockedUsers = new ArrayList<>();
		mockedUsers.add(user);
		when(mockedQuery.getResultList()).thenReturn(mockedUsers);
		when(mockedQuery.setParameter("login", "operator1")).thenReturn(mockedQuery);
		when(this.em.createNamedQuery("findUserLoginQuery", User.class)).thenReturn(mockedQuery);
		assertTrue(this.ub.findUserLogin("operator1"));
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#addUserRole(pl.shopapp.entites.User, int)}.
	 */
	@Test
	public void testAddUserRole() {
		Role role = new Role();
		role.setId(2);
		role.setRoleName("customer");
		when(em.find(Role.class, 2)).thenReturn(role);
		UserRole userRoleExpected = new UserRole();
		userRoleExpected.setId(0);
		userRoleExpected.setUser(user);
		userRoleExpected.setRole(role);
		UserRole userRoleActual = ub.addUserRole(user, 2);
		assertEquals(userRoleExpected.getId(), userRoleActual.getId());
		assertEquals(userRoleExpected.getRole(), userRoleActual.getRole());
		assertEquals(userRoleExpected.getUser(), userRoleActual.getUser());
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#loginUser(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLoginUser() {
		fail("Not yet implemented");
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
	 */
	@Test
	public void testAddOperator() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#getOperatorsData()}.
	 */
	@Test
	public void testGetOperatorsData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#updateOperatorData(int, int, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testUpdateOperatorData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#addAdmin(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testAddAdmin() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#loginAdmin(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testLoginAdmin() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#getAdminsData()}.
	 */
	@Test
	public void testGetAdminsData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#getUsersAdminData()}.
	 */
	@Test
	public void testGetUsersAdminData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#updateAdminData(int, int, java.lang.String, boolean, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testUpdateAdminData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#getSettingsApp()}.
	 */
	@Test
	public void testGetSettingsApp() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link pl.shopapp.beans.UserBean#setSettingsApp(int, int, int, int, int, int, int, int)}.
	 */
	@Test
	public void testSetSettingsApp() {
		fail("Not yet implemented");
	}

}
