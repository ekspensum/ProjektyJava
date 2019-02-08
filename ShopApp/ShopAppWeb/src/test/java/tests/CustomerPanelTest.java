package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.TransactionBeanLocal;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.beans.Validation;
import pl.shopapp.entites.SettingsApp;
import pl.shopapp.entites.Transaction;
import pl.shopapp.web.CustomerPanel;

class CustomerPanelTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	UserBeanLocal ubl;
	@Mock
	TransactionBeanLocal tbl;
	@Mock
	HttpSession session;
	@Mock
	RequestDispatcher rd;
	@Mock
	Validation valid;
	
	CustomerPanel cp;
	SettingsApp sa;
	SessionData sd;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		sa = new SettingsApp();
		sa.setMaxCharInPass(20);
		sa.setMinCharInPass(5);
		sa.setMaxCharInLogin(20);
		sa.setMinCharInLogin(5);
		sa.setNumbersInPass(2);
		sa.setUpperCaseInPass(1);
		sd = new SessionData();
		cp = new CustomerPanel(ubl, tbl);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		when(request.getRequestDispatcher("/jsp/customerPanel.jsp")).thenReturn(rd);
		cp.doGet(request, response);
	}

	@Test
	final void testDoPostHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		doNothing().when(request).setCharacterEncoding("UTF-8");
		
//		for buttonAddCustomer parameter
		when(request.getParameter("buttonAddCustomer")).thenReturn("buttonAddCustomer");
		when(request.getParameter("login")).thenReturn("login1");
		when(request.getParameter("password")).thenReturn("Admin11");
		when(request.getParameter("firstName")).thenReturn("firstName");
		when(request.getParameter("lastName")).thenReturn("lastName");
		when(request.getParameter("pesel")).thenReturn("01234567890");
		when(request.getParameter("zipCode")).thenReturn("11-222");
		when(request.getParameter("country")).thenReturn("country");
		when(request.getParameter("city")).thenReturn("city");
		when(request.getParameter("street")).thenReturn("street");
		when(request.getParameter("streetNo")).thenReturn("01");
		when(request.getParameter("unitNo")).thenReturn("01");
		when(request.getParameter("email")).thenReturn("abC.def@Abc.com");
		when(request.getParameter("isCompany")).thenReturn("yes");
		when(request.getParameter("companyName")).thenReturn("companyName");
		when(request.getParameter("taxNo")).thenReturn("1234567890");
		when(request.getParameter("regon")).thenReturn("123456789");
		
		when(ubl.addCustomer("login1", "Admin11", "firstName", "lastName", "01234567890", "11-222", "country", "city", "street", "01", "01", "abC.def@Abc.com", true, "companyName", "1234567890", "123456789")).thenReturn(true);
		sd.setIdUser(1);
		when(ubl.loginUser("login1", "Admin11")).thenReturn(sd);
		
		sa.setSessionTime(13);
		when(ubl.getSettingsApp()).thenReturn(sa);
		when(request.getSession()).thenReturn(session);

//		for buttonOpenEdit parameter		
		when(request.getParameter("buttonOpenEdit")).thenReturn("buttonOpenEdit");
		String pasToCode = "Admin11";
		when(request.getParameter("password")).thenReturn(pasToCode);
		String pass = "dcca2ed163582435afa9d42ce361eb4";
		when(valid.passwordToCode(request.getParameter("password"))).thenReturn(pass);
		
		SessionData loginSD = new SessionData();
		loginSD.setIdUser(1);
		when(ubl.loginUser(request.getParameter("login"), pass)).thenReturn(loginSD);
		SessionData currentSD = new SessionData();
		currentSD.setIdUser(1);
		when((SessionData) session.getAttribute("SessionData")).thenReturn(currentSD);

//		for buttonSaveEdit parameter		
		when(request.getParameter("buttonSaveEdit")).thenReturn("buttonSaveEdit");
		SessionData sdEditButton = new SessionData();
		sdEditButton.setIdUser(1);
		when(session.getAttribute("SessionData")).thenReturn(sdEditButton);
		when(ubl.updateCustomer("login1", "Admin11", "firstName", "lastName", "01234567890", "11-222", "country", "city", "street", "01", "01", "abC.def@Abc.com", true, "companyName", "1234567890", "123456789", sdEditButton.getIdUser())).thenReturn(true);
		
//		for buttonSearchTransaction parameter
		when(request.getParameter("buttonSearchTransaction")).thenReturn("buttonSearchTransaction");
		SessionData sdTrnsaction = new SessionData();
		sdTrnsaction.setIdUser(1);
		when(session.getAttribute("SessionData")).thenReturn(sdTrnsaction);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dateFrom = "2019-01-01"+" 00:00:00";
		String dateTo = "2019-12-31"+" 23:59:59";
		when(request.getParameter("searchProductDateFrom")).thenReturn("2019-01-01");
		when(request.getParameter("searchProductDateTo")).thenReturn("2019-12-31");
		List<Transaction> tl = new ArrayList<>();
		Transaction tr = new Transaction();
		tr.setId(1);
		tl.add(tr);
		when(tbl.getTransactionsData(sdTrnsaction.getIdUser(), LocalDateTime.parse(dateFrom, formatter), LocalDateTime.parse(dateTo, formatter))).thenReturn(tl);	

		when(request.getRequestDispatcher("/jsp/customerPanel.jsp")).thenReturn(rd);
		cp.doPost(request, response);
		
//		for buttonAddCustomer parameter
		assertEquals(13, ubl.getSettingsApp().getSessionTime());
		assertEquals(1, ubl.loginUser("login1", "Admin11").getIdUser());
//		for buttonOpenEdit parameter
		assertEquals(loginSD.getIdUser(), currentSD.getIdUser());
//		for buttonSaveEdit parameter
		assertTrue(ubl.updateCustomer("login1", "Admin11", "firstName", "lastName", "01234567890", "11-222", "country", "city", "street", "01", "01", "abC.def@Abc.com", true, "companyName", "1234567890", "123456789", sdEditButton.getIdUser()));
//		for buttonSearchTransaction parameter
		assertEquals(1, tbl.getTransactionsData(sdTrnsaction.getIdUser(), LocalDateTime.parse(dateFrom, formatter), LocalDateTime.parse(dateTo, formatter)).get(0).getId());
	}
	
}
