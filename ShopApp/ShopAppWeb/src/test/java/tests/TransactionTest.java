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
import pl.shopapp.beans.TransactionBeanLocal;
import pl.shopapp.web.Transaction;

class TransactionTest {
	
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
	@Mock
	TransactionBeanLocal tbl;

	BasketBeanLocal bbl;
	Transaction tr;
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
		bbl = new BasketBean();
		sd = new SessionData();
		tr = new Transaction(bbl, tbl);
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
		
		when(request.getRequestDispatcher("/jsp/transaction.jsp")).thenReturn(rd);
		tr.doGet(request, response);
		assertEquals(614.02, tr.getTotal());
	}

	@Test
	final void testDoPostHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when(session.getAttribute("SessionData")).thenReturn(sd);
		List<BasketData> basketDataList = new ArrayList<>();
		bbl.addBasketRow(1, 13, "productName1", 11.22, basketDataList);
		bbl.addBasketRow(2, 14, "productName2", 33.44, basketDataList);
		sd.setBasketBeanLocal(bbl);
		
		when(tbl.newTransaction(1, basketDataList)).thenReturn(true);
		
		when(request.getRequestDispatcher("/jsp/transaction.jsp")).thenReturn(rd);
		tr.doPost(request, response);
		assertTrue(tbl.newTransaction(1, basketDataList));
	}

}
