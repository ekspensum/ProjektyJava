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
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;
import pl.shopapp.web.ProductByCategory;

class ProductByCategoryTest {
	
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
	ProductByCategory pbc;
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
		pbc = new ProductByCategory(pbl, bbl);
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
			
		when(request.getRequestDispatcher("/jsp/productByCategory.jsp")).thenReturn(rd);
		pbc.doGet(request, response);
		assertEquals(614.02, pbc.getTotal());
	}

	@SuppressWarnings("serial")
	@Test
	final void testDoPostHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		Category cat0 = new Category();
		cat0.setId(0);
		Category cat1 = new Category();
		cat1.setId(1);
		Category cat2 = new Category();
		cat2.setId(2);
		Category cat3 = new Category();
		cat3.setId(3);
		Category cat4 = new Category();
		cat4.setId(4);
		Category cat5 = new Category();
		cat5.setId(5);
		Category cat6 = new Category();
		cat6.setId(6);
		Category cat7 = new Category();
		cat7.setId(7);
		Category cat8 = new Category();
		cat8.setId(8);
		
		List<Category> cl = new ArrayList<>();
		cl.add(cat0);
		cl.add(cat1);
		cl.add(cat2);
		cl.add(cat3);
		cl.add(cat4);
		cl.add(cat5);
		cl.add(cat6);
		cl.add(cat7);
		cl.add(cat8);
		
		when(pbl.listCategory()).thenReturn(cl);
		when(request.getParameter(String.valueOf(cl.get(1).getId()))).thenReturn("1");
		when(request.getParameter(String.valueOf(cl.get(2).getId()))).thenReturn("2");
		when(request.getParameter(String.valueOf(cl.get(3).getId()))).thenReturn("3");
		when(request.getParameter(String.valueOf(cl.get(4).getId()))).thenReturn("4");
		when(request.getParameter(String.valueOf(cl.get(5).getId()))).thenReturn("5");
		when(request.getParameter(String.valueOf(cl.get(6).getId()))).thenReturn("6");
		when(request.getParameter(String.valueOf(cl.get(7).getId()))).thenReturn("7");
		when(request.getParameter(String.valueOf(cl.get(8).getId()))).thenReturn("8");
		
//		for buttonToBasketFromCategory parameter
		when(request.getParameter("buttonToBasketFromCategory")).thenReturn("1");
		
		when(pbl.getProduct(1)).thenReturn(new Product() {{setId(1); setName("name"); setPrice(17.17);}});
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("SessionData")).thenReturn(sd);
		List<BasketData> basketDataList = new ArrayList<>();
		bbl.addBasketRow(1, 13, "productName1", 11.22, basketDataList);
		bbl.addBasketRow(2, 14, "productName2", 33.44, basketDataList);
		sd.setBasketBeanLocal(bbl);
		when(request.getParameter("buttonToBasketFromCategory")).thenReturn("1");
		when(request.getParameter("quantity1")).thenReturn("1");
		
//		for buttonDeleteRowBasket parameter
		when(request.getParameter("buttonDeleteRowBasket")).thenReturn("1");
		when(request.getParameterValues("chbxDeleteRow")).thenReturn(new String [] {"1", "2"});
			
		when(request.getSession()).thenReturn(session);
		when(request.getRequestDispatcher("/jsp/productByCategory.jsp")).thenReturn(rd);
		pbc.doPost(request, response);
//		for buttonToBasketFromCategory parameter
		assertTrue(bbl.addBasketRow(1, 1, "name", 11.22, bbl.getBasketData()));
//		for buttonDeleteRowBasket parameter - d't forget that assert above add one row		
		assertEquals(4, bbl.getBasketData().size());
	}

}
