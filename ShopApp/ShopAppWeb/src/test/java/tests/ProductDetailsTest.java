package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.shopapp.beans.BasketBean;
import pl.shopapp.beans.BasketBeanLocal;
import pl.shopapp.beans.BasketData;
import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.entites.Product;
import pl.shopapp.web.ProductDetails;

class ProductDetailsTest {
	
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	HttpSession session;
	@Mock
	ProductBeanLocal pbl;
	@Mock
	RequestDispatcher rd;

	BasketBeanLocal bbl;
	ProductDetails pd;
	SessionData sd;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);		
		sd= new SessionData();
		bbl = new BasketBean();
		pd = new ProductDetails(pbl, bbl);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("SessionData")).thenReturn(sd);
		List<BasketData> basketDataList = new ArrayList<>();
		bbl.addBasketRow(1, 13, "productName1", 11.22, basketDataList);
		bbl.addBasketRow(2, 14, "productName2", 33.44, basketDataList);
		sd.setBasketBeanLocal(bbl);
			
		when(request.getRequestDispatcher("/jsp/productDetails.jsp")).thenReturn(rd);
		pd.doGet(request, response);
		assertEquals(614.02, pd.getTotal());
	}

	@SuppressWarnings("serial")
	@Test
	final void testDoPostHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		when(pbl.getProduct(Integer.valueOf("1"))).thenReturn(new Product() {{setId(3); setName("name1"); setPrice(55.66);}});		
		when(request.getParameter("buttonToProductDetailsFromCategory")).thenReturn("1");
		when(request.getParameter("buttonToProductDetailsFromMain")).thenReturn("1");
		
//		for buttonToBasketFromDetails parameter
		when(request.getParameter("buttonToBasketFromDetails")).thenReturn("1");
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("SessionData")).thenReturn(sd);
		List<BasketData> basketDataList = new ArrayList<>();
		bbl.addBasketRow(1, 13, "productName1", 11.22, basketDataList);
		bbl.addBasketRow(2, 14, "productName2", 33.44, basketDataList);
		sd.setBasketBeanLocal(bbl);
		
		when(request.getParameter("buttonToBasketFromDetails")).thenReturn("1");
		when(request.getParameter("quantity1")).thenReturn("1");
		
//		for buttonDeleteRowBasket parameter
		when(request.getParameter("buttonDeleteRowBasket")).thenReturn("1");
		when(request.getParameterValues("chbxDeleteRow")).thenReturn(new String [] {"1", "2"});
		
		when(request.getRequestDispatcher("/jsp/productDetails.jsp")).thenReturn(rd);
		pd.doPost(request, response);
		
//		for buttonToBasketFromDetails parameter
		assertTrue(bbl.addBasketRow(pbl.getProduct(1).getId(), 21, pbl.getProduct(1).getName(), pbl.getProduct(1).getPrice(),	bbl.getBasketData()));
//		for buttonDeleteRowBasket parameter - to keep in mind that above assert added one row to basket
		assertEquals(3, bbl.getBasketData().size());
	}

}
