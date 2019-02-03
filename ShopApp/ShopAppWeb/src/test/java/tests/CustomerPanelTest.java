package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.shopapp.web.CustomerPanel;

class CustomerPanelTest {

	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	CustomerPanel cp;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		RequestDispatcher rd = mock(RequestDispatcher.class);
		when(request.getRequestDispatcher("/jsp/customerPanel.jsp")).thenReturn(rd);
	}

	@Test
	final void testDoPostHttpServletRequestHttpServletResponse() {
		fail("Not yet implemented"); // TODO
	}
	
}
