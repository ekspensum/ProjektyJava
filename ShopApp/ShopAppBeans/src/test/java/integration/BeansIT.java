package integration;

//import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.shopapp.beans.BasketBean;
import pl.shopapp.beans.BasketBeanLocal;
import pl.shopapp.beans.BasketData;
import pl.shopapp.beans.ProductBean;
import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SendEmail;
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
import pl.shopapp.entites.Role;
import pl.shopapp.entites.SettingsApp;
import pl.shopapp.entites.Transaction;
import pl.shopapp.entites.User;


@RunWith(Arquillian.class)
public class BeansIT {
	
	@Deployment
	public static JavaArchive createDeployment() {
	    return ShrinkWrap.create(JavaArchive.class)
	      .addClasses(UserBean.class, Validation.class, Admin.class, Customer.class, Operator.class, Product.class, Role.class, SendEmail.class,
	    		  SettingsApp.class, User.class, Category.class, Transaction.class, BasketBean.class, BasketData.class, ProductBean.class, 
	    		  SessionData.class, TransactionBean.class)
	      .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml")
	      .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@EJB
	private UserBeanLocal ubl;
	@EJB
	private ProductBeanLocal pbl;
	@EJB
	private BasketBeanLocal bbl;
	@EJB
	private TransactionBean tb;
	
	private byte [] buffer = {49,50,51};
	
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
	
    @PersistenceContext
    EntityManager em;
    
    @Inject
    UserTransaction ut;
	
	@Test
	@InSequence(1)
	public final void prepareTests() throws IllegalStateException, SecurityException, SystemException {
		ubl.addRole("admin");
		ubl.addRole("customer");
		ubl.addRole("operator");
		assertEquals(3, ubl.getRoleList().size());
		assertTrue(ubl.setSettingsApp(6, 20, 1, 2, 5, 12, 30, 1));		
	}
	
	@Test
	@InSequence(2)
	public final void testCustomer() throws InterruptedException, IllegalStateException, SecurityException, SystemException {

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
		assertTrue(ubl.findUserLogin(login));
		SessionData sd = ubl.loginUser(login, valid.stringToCode(password));	
		assertEquals(2, sd.getIdRole());
	}
	
	@Test
	@InSequence(3)
	public final void testAdmin() throws IllegalStateException, SecurityException, SystemException {
		login = "admin1";
		password = "Admin11";
		Validation valid = new Validation(ubl.getSettingsApp());
		
		assertTrue(valid.loginValidation(login));
		assertTrue(valid.passwordValidation(password));
		assertTrue(valid.nameValidation(firstName));
		assertTrue(valid.nameValidation(lastName));
		assertTrue(valid.telephoneNoValidation(phoneNo));
		assertTrue(valid.emailValidation(email));
		assertTrue(ubl.addAdmin(firstName, lastName, phoneNo, email, login, valid.stringToCode(password), 1));
//		note: is 2 admins because first admin was added with addAdmin method (default) 
		assertEquals(2, ubl.getAdminsData().size());
		assertTrue(ubl.updateAdminData(1, ubl.getAdminsData().get(1).getId(), login, true, firstName, lastName, phoneNo, email));
		SessionData sd = ubl.loginAdmin(login, valid.stringToCode(password));
		assertEquals(1, sd.getIdRole());
		assertTrue(ubl.findUserLogin(login));
		assertEquals(ubl.getUsersAdminData().size()+1, ubl.getAdminsData().size());
	}
	
	@Test
	@InSequence(4)
	public final void testOperator() throws IllegalStateException, SecurityException, SystemException {
		login = "operator1";
		password = "Operator11";
		Validation valid = new Validation(ubl.getSettingsApp());
				
		assertTrue(valid.loginValidation(login));
		assertTrue(valid.passwordValidation(password));
		assertTrue(valid.nameValidation(firstName));
		assertTrue(valid.nameValidation(lastName));
		assertTrue(valid.telephoneNoValidation(phoneNo));
		assertTrue(valid.emailValidation(email));
		assertTrue(ubl.addOperator(firstName, lastName, phoneNo, email, login, valid.stringToCode(password), 1));
		List<Operator> ol = ubl.getOperatorsData();
		assertEquals(1, ol.size());
		assertTrue(ubl.updateOperatorData(1, ol.get(0).getId(), login, true, firstName, lastName, phoneNo, email));
		SessionData sd = ubl.loginUser(login, valid.stringToCode(password));
		assertEquals(3, sd.getIdRole());
		assertTrue(ubl.findUserLogin(login));
		assertEquals(ubl.getUsersOperatorData().size(), ubl.getOperatorsData().size());
	}
	
	@Test
	@InSequence(5)
	public final void testCategory() throws IllegalStateException, SecurityException, SystemException {
		assertTrue(pbl.addCategory("categoryName1", buffer, 3));
		assertTrue(pbl.addCategory("categoryName2", buffer, 3));
		assertTrue(pbl.addCategory("categoryName3", buffer, 3));
		assertEquals(3, pbl.listCategory().size());
	}
	
	@Test
	@InSequence(6)
	public final void testProduct() throws IllegalStateException, SecurityException, SystemException {
		byte [] buffer1 = {105, 109, 97, 103, 101, 49, 51};
		List<Integer> helperListCat = new ArrayList<>();
		helperListCat.add(1);
		helperListCat.add(3);
		assertTrue(pbl.addProduct("productName", "productDescription", 11.22, 13, buffer1, helperListCat, 3));
		assertEquals(1, pbl.getProduct(1).getId());
		
		byte [] buffer2 = {105, 109, 97, 103, 101, 54, 54};
		pbl.updateProduct("productName2", "productDescription2", 33.44, 666, buffer2, 1, buffer2.length, 3, helperListCat);
		
		assertEquals("productName2", pbl.getProduct(1).getName());
		assertEquals("productDescription2", pbl.getProduct(1).getDescription());
		assertEquals(33.44, pbl.getProduct(1).getPrice());
		assertEquals(666, pbl.getProduct(1).getUnitsInStock());
		assertEquals("aW1hZ2U2Ng==", pbl.getProduct(1).getBase64Image());
		assertEquals(3, pbl.getProduct(1).getCategories().get(1).getId());
	}
	
	@Test
	@InSequence(7)
	public final void testBasket() {
		List<BasketData> basketDataList = bbl.getBasketData();
		assertTrue(bbl.addBasketRow(1, 133, "productName1", 55.66, basketDataList));
		assertEquals(55.66, basketDataList.get(0).getPrice());
	}
	
	@Test
	@InSequence(8)
	public final void testTransaction() {
		testBasket();
		List<BasketData> basketList = bbl.getBasketData();
		assertTrue(tb.newTransaction(1, basketList));
		assertEquals(133, tb.getTransactionsData(1, LocalDateTime.now().minusDays(1), LocalDateTime.now(), "productIdDescending", 0, 1).get(0).getQuantity());
	}
}
