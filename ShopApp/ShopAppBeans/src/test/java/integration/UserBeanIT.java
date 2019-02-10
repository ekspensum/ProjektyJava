package integration;

import static org.junit.Assert.*;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.shopapp.beans.BasketBean;
import pl.shopapp.beans.BasketData;
import pl.shopapp.beans.ProductBean;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.TransactionBean;
import pl.shopapp.beans.UserBean;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Admin;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Product;
import pl.shopapp.entites.ProductCategory;
import pl.shopapp.entites.Role;
import pl.shopapp.entites.SettingsApp;
import pl.shopapp.entites.Transaction;
import pl.shopapp.entites.User;
import pl.shopapp.entites.UserRole;

@RunWith(Arquillian.class)
public class UserBeanIT {
	
	@Deployment
	public static JavaArchive createDeployment() {
	    return ShrinkWrap.create(JavaArchive.class)
	      .addClasses(UserBean.class, Validation.class, Admin.class, Customer.class, Operator.class, Product.class, ProductCategory.class, Role.class, 
	    		  SettingsApp.class, User.class, UserRole.class, Category.class, Transaction.class, BasketBean.class, BasketData.class, ProductBean.class, 
	    		  SessionData.class, TransactionBean.class)
	      .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml")
	      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@EJB
	private UserBeanLocal ubl;
	
	private String login = null;
	private String password = null;
	private String firstName = "firstName";
	private String lastName = "lastName";
	private String pesel = "01234567890";
	private String zipCode = "11-222";
	private String country = "country";
	private String city = "city";
	private String street = "street";
	private String streetNo = "01";
	private String unitNo = "01";
	private String email = "abC.def@Abc.com";
	private boolean isCompany = true;
	private String companyName = "companyName";
	private String taxNo = "1234567890";
	private String regon = "123456789";
	private String phoneNo = "+012 345 67 89";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@InSequence(1)
	public final void testCustomer() {
		ubl.addRole("admin");
		ubl.addRole("customer");
		ubl.addRole("operator");
		ubl.setSettingsApp(6, 20, 1, 2, 5, 12, 30, 1);
		Validation valid = new Validation(ubl.getSettingsApp());
		
		login = "customer1";
		password = "Customer11";
		assertTrue(valid.loginValidation(login));
		assertTrue(valid.passwordValidation(password));
		assertTrue(valid.nameValidation(firstName));
		assertTrue(valid.nameValidation(lastName));
		assertTrue(valid.peselValidation(pesel));
		assertTrue(valid.zipCodeValidation(zipCode));
		assertTrue(valid.nameValidation(country));
		assertTrue(valid.nameValidation(city));
		assertTrue(valid.nameValidation(street));
		assertTrue(valid.locationValidation(streetNo));
		assertTrue(valid.locationValidation(unitNo));
		assertTrue(valid.emailValidation(email));
		assertTrue(valid.nameValidation(companyName));
		assertTrue(valid.nipValidation(taxNo));
		assertTrue(valid.regonValidation(regon));		
		assertTrue(ubl.addCustomer(login, password, firstName, lastName, pesel, zipCode, country, city, street, streetNo, unitNo, email, isCompany, companyName, taxNo, regon));
		assertTrue(ubl.updateCustomer(login, password, firstName, lastName, pesel, zipCode, country, city, street, streetNo, unitNo, email, isCompany, companyName, taxNo, regon, 1));
		User u = ubl.findUser(1);
		Customer c = ubl.findCustomer(u);
		assertEquals("firstName", c.getFirstName());
		assertTrue(ubl.setActiveCustomer(c.getId(), false));
		assertTrue(ubl.setActiveCustomer(c.getId(), true));
	}
	
	@Test
	@InSequence(2)
	public final void testAdmin() {
		login = "admin1";
		password = "Admin11";
		Validation valid = new Validation(ubl.getSettingsApp());
		
		assertTrue(valid.loginValidation(login));
		assertTrue(valid.passwordValidation(password));
		assertTrue(valid.nameValidation(firstName));
		assertTrue(valid.nameValidation(lastName));
		assertTrue(valid.telephoneNoValidation(phoneNo));
		assertTrue(valid.emailValidation(email));
		assertTrue(ubl.addAdmin(firstName, lastName, phoneNo, email, login, password, 1));
		List<Admin> al = ubl.getAdminsData();
		assertEquals(2, al.size());
		assertTrue(ubl.updateAdminData(1, al.get(1).getId(), login, true, firstName, lastName, phoneNo, email));
	}
	
	@Test
	@InSequence(3)
	public final void testOperator() {
		login = "operator1";
		password = "Operator11";
		Validation valid = new Validation(ubl.getSettingsApp());
				
		assertTrue(valid.loginValidation(login));
		assertTrue(valid.passwordValidation(password));
		assertTrue(valid.nameValidation(firstName));
		assertTrue(valid.nameValidation(lastName));
		assertTrue(valid.telephoneNoValidation(phoneNo));
		assertTrue(valid.emailValidation(email));
		assertTrue(ubl.addOperator(firstName, lastName, phoneNo, email, login, password, 1));
		List<Operator> ol = ubl.getOperatorsData();
		assertEquals(1, ol.size());
		assertTrue(ubl.updateOperatorData(1, ol.get(0).getId(), login, true, firstName, lastName, phoneNo, email));
	}

}
