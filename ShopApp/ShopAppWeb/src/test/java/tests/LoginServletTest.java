package tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.shopapp.beans.BasketBean;
import pl.shopapp.beans.BasketBeanLocal;
import pl.shopapp.beans.BasketData;
import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.entites.Category;
import pl.shopapp.web.LoginServlet;

public class LoginServletTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	UserBeanLocal ubl;
	@Mock
	ProductBeanLocal pbl;
	@Mock
	BasketBeanLocal bbl;
	@Mock
	private InitialContext ctx;
	@Mock
	HttpSession session;
	@Mock
	RequestDispatcher rd;

	LoginServlet ls;

	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testDoGetHttpServletRequestHttpServletResponse() throws IOException, ServletException {
		
		doNothing().when(request).setAttribute(anyObject(), anyCollectionOf(Category.class));
		
		SessionData sd = new SessionData();			

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
	
		ls = new LoginServlet(ubl, pbl, bbl, ctx);
		ls.doGet(request, response);

		assertTrue(100.32 == ls.getTotal());
	}

	@Test
	public final void testDoPostHttpServletRequestHttpServletResponse() {
		fail("Not yet implemented"); // TODO
	}

}
