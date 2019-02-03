package tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.shopapp.beans.UserBean;
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
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Transaction;

@RunWith(Arquillian.class)
public class LoginServletTestIT {

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
