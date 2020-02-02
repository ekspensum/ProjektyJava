package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import pl.shopapp.beans.BasketBean;
import pl.shopapp.beans.BasketBeanLocal;
import pl.shopapp.beans.BasketData;
import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;
import pl.shopapp.entites.SettingsApp;
import pl.shopapp.web.LoginServlet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginServletTest {
	
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	UserBeanLocal ubl;
	@Mock
	ProductBeanLocal pbl;
	@Mock
	BasketBean bbl;
	@Mock
	Context ctx;
	@Mock
	HttpSession session;
	@Mock
	RequestDispatcher rd;
	@Mock
	Validation valid;

	SettingsApp sa;
	SessionData sd;
	LoginServlet ls;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sd = new SessionData();
		sa = new SettingsApp();
		sa.setMaxCharInPass(20);
		sa.setMinCharInPass(5);
		sa.setMaxCharInLogin(20);
		sa.setMinCharInLogin(5);
		ls = new LoginServlet(ubl, pbl, bbl, ctx);	
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		doNothing().when(request).setAttribute(anyObject(), anyCollectionOf(Category.class));

		when(request.getSession()).thenReturn(session);
		when((SessionData) request.getSession().getAttribute("SessionData")).thenReturn(sd);
		
		List<BasketData> bdl = bbl.getBasketData();
		BasketBeanLocal bbl = new BasketBean();
		bbl.addBasketRow(1, 1, "productName", 11.22, bdl);
		bbl.addBasketRow(1, 1, "productName", 33.44, bdl);
		bbl.addBasketRow(1, 1, "productName", 55.66, bdl);

		sd.setBasketBeanLocal(bbl);
		
		doNothing().when(request).setAttribute(anyString(), anyDouble());
		when(request.getRequestDispatcher("/jsp/index.jsp")).thenReturn(rd);

		ls.doGet(request, response);

		assertTrue(100.32 == ls.getTotal());
	}

	@Test
	final void testDoPostHttpServletRequestHttpServletResponse() throws NamingException, ServletException, IOException {
		List<BasketData> bdl = bbl.getBasketData();
		BasketBeanLocal bbl = new BasketBean();
		bbl.addBasketRow(1, 1, "productName", 11.22, bdl);
		doNothing().when(request).setCharacterEncoding("UTF-8");
		
		when(request.getParameter("loginButton")).thenReturn("login");
		
		sa.setIdUser(1);
		sa.setSessionTime(1);

		when(ubl.getSettingsApp()).thenReturn(sa);

		String pasToCode = "Admin11";
		when(request.getParameter("password")).thenReturn(pasToCode);
		String pass = "dcca2ed163582435afa9d42ce361eb4";
		when(valid.stringToCode(request.getParameter("password"))).thenReturn(pass);
		
		when(request.getParameter("login")).thenReturn("login");
		when(valid.loginValidation(request.getParameter("login"))).thenReturn(true);

		sd.setIdUser(1);
		when(ubl.loginUser(request.getParameter("login"), pass)).thenReturn(sd);
		
//		for mocking InitialContext
//		spring-mock-1.1.4.jar was added to BuiltPath
		SimpleNamingContextBuilder contextBuilder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
		contextBuilder.bind("java:global/ShopApp/ShopAppBeans/BasketBean!pl.shopapp.beans.BasketBeanLocal", bbl);
		when(ctx.lookup("java:global/ShopApp/ShopAppBeans/BasketBean!pl.shopapp.beans.BasketBeanLocal")).thenReturn(bbl);
		
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/jsp/index.jsp")).thenReturn(rd);
		
		when(request.getParameter("buttonDeleteRowBasket")).thenReturn("1");
		sd.setBasketBeanLocal(bbl);
		when(request.getSession()).thenReturn(session);
		when((SessionData) request.getSession().getAttribute("SessionData")).thenReturn(sd);
		String[] deletedId = {"1"};
		when(request.getParameterValues("chbxDeleteRow")).thenReturn(deletedId);
		
		when(request.getParameter("searchProductButton")).thenReturn("notNull");
		List<Product> pl = new ArrayList<>();	
		when(request.getParameter("searchProductInput")).thenReturn("proc");
		when(pbl.findProduct(request.getParameter("searchProductInput"))).thenReturn(pl);		
		
		ls.doPost(request, response);
		
//		for loginButton parameter
		assertEquals(1, sa.getIdUser());
		assertEquals(1, sd.getIdUser());
		assertEquals(1, ubl.getSettingsApp().getSessionTime());
		
//		for buttonDeleteRowBasket parameter
		assertEquals(1, bbl.getBasketData().size());
		
//		for searchProductButton parameter
		assertNotNull(request.getParameter("searchProductButton"));
		assertNotNull(request.getParameter("searchProductInput"));
	}

}
