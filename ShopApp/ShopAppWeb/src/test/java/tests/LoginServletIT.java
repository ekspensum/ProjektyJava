package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.shopapp.beans.BasketBeanLocal;
import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBean;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Admin;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Product;
import pl.shopapp.entites.ProductCategory;
import pl.shopapp.entites.Role;
import pl.shopapp.entites.SettingsApp;
import pl.shopapp.entites.User;
import pl.shopapp.entites.UserRole;
import pl.shopapp.web.LoginServlet;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Transaction;

@RunWith(Arquillian.class)
public class LoginServletIT {

	@Deployment
	public static JavaArchive createDeployment() {
	    return ShrinkWrap.create(JavaArchive.class)
	      .addClasses(UserBean.class, Validation.class, Admin.class, Customer.class, Operator.class, Product.class, ProductCategory.class, Role.class, 
	    		  SettingsApp.class, User.class, UserRole.class, Category.class, Transaction.class)
	      .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml")
	      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	
	@Inject
	private UserBean ub;
	

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testAddCustomer() {
		ub.addRole("admin");
		ub.addRole("customer");
		ub.addRole("operator");
		List<Role> rl = ub.getRoleList();
		System.out.println("size: "+rl.size() );
		ub.addCustomer("login", "password", "firstName", "lastName", "pesel", "zipCode", "country", "city", "street", "streetNo", "unitNo", "email", true, "companyName", "taxNo", "regon");
		System.out.println(ub.findUser(1).getLogin());
//		assertTrue(ub.addCustomer("login", "password", "firstName", "lastName", "pesel", "zipCode", "country", "city", "street", "streetNo", "unitNo", "email", true, "companyName", "taxNo", "regon"));
	}
	
	@Test
	public final void testDoPostHttpServletRequestHttpServletResponse() throws ServletException, IOException {

//		doNothing().when(request).setCharacterEncoding("UTF-8");
//		when(request.getParameter("loginButton")).thenReturn("login");
//
//		when(ubl.getSettingsApp()).thenReturn(sa);
//
//		String pasToCode = "Admin11";
//		when(request.getParameter("password")).thenReturn(pasToCode);
//		String pass = "dcca2ed163582435afa9d42ce361eb4";
//		when(valid.passwordToCode(request.getParameter("password"))).thenReturn(pass);
//		
//		when(request.getParameter("login")).thenReturn("login");
//		when(valid.loginValidation(request.getParameter("login"))).thenReturn(true);

//		when(ubl.loginUser(request.getParameter("login"), pass)).thenReturn(sd);

		
	
//		ls.doPost(request, response);
	}

//	@Test
//	final void testUpdateCustomer() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testFindCustomer() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testSetActiveCustomer() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testFindUser() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testFindUserLogin() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testAddUserRole() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testLoginUser() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetUsersOperatorData() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testAddOperator() {
//		fail("Not yet implemented"); // TODO
//	}

	@Test
	public void testGetOperatorsData() {
//		List<Operator> opl= new ArrayList<>();
		assertTrue(ub.getOperatorsData().isEmpty());
	}
//
//	@Test
//	final void testUpdateOperatorData() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testAddAdmin() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testLoginAdmin() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetAdminsData() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetUsersAdminData() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testUpdateAdminData() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testGetSettingsApp() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	final void testSetSettingsApp() {
//		fail("Not yet implemented"); // TODO
//	}

}
