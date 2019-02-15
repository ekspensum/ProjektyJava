package tests;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.web.CustomerActivation;

class CustomerActivationTest {
	
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	UserBeanLocal ubl;
	@Mock
	RequestDispatcher rd;
	
	CustomerActivation ca;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ca = new CustomerActivation(ubl);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		when(request.getParameter("activationString")).thenReturn("activationString");
		when(ubl.setActiveCustomer("activationString")).thenReturn(true);
		when(request.getRequestDispatcher("/jsp/customerActivation.jsp")).thenReturn(rd);
		ca.doGet(request, response);
	}

}
