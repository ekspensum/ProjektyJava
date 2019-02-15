package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;
import pl.shopapp.web.OperatorPanel;

class OperatorPanelTest {

	@Mock
	ProductBeanLocal pbl;
	@Mock
	HttpSession session;
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	@Mock
	RequestDispatcher rd;
	@Mock
	ServletContext sc;
		
	OperatorPanel op;
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
		sd = new SessionData();
		op = new OperatorPanel(pbl);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testDoGetHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		when(request.getSession()).thenReturn(session);
		when((SessionData)session.getAttribute("SessionData")).thenReturn(sd);
		when(request.getRequestDispatcher("jsp/operatorPanel.jsp")).thenReturn(rd);
		op.doGet(request, response);
	}

	@SuppressWarnings("serial")
	@Test
	final void testDoPostHttpServletRequestHttpServletResponse() throws ServletException, IOException {
		doNothing().when(request).setCharacterEncoding("UTF-8");
		
//		for buttonAddProduct parameter
		when(request.getParameter("buttonAddProduct")).thenReturn("buttonAddProduct");
		when(request.getSession()).thenReturn(session);
		when((SessionData)session.getAttribute("SessionData")).thenReturn(sd);
		
		String productName = "productName", productDescription = "productDescription", categoryName = "categoryName";
		double productPrice = 11.22;
		int productUnitsInStock = 100;
		byte[] buffer = {0, 0, 0};
		List<Integer> helperListCat = new ArrayList<>();
		helperListCat.add(24);
		helperListCat.add(25);
		
		when(request.getParameter("productName")).thenReturn(productName);
		when(request.getParameter("productDescription")).thenReturn(productDescription);
		when(request.getParameter("productPrice")).thenReturn("11.22");
		when(request.getParameter("productUnitsInStock")).thenReturn("100");
		when(request.getParameter("category1")).thenReturn("24");
		when(request.getParameter("category2")).thenReturn("25");
		Part filePartProduct = new Part() {
			
			@Override
			public void write(String fileName) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getSubmittedFileName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getSize() {
				// TODO Auto-generated method stub
				return 3;
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				InputStream is = new InputStream() {
					
					@Override
					public int read() throws IOException {
						// TODO Auto-generated method stub
						return 0;
					}
				};
				return is;
			}
			
			@Override
			public Collection<String> getHeaders(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getHeaderNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getHeader(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void delete() throws IOException {
				// TODO Auto-generated method stub
				
			}
		};		
		when(request.getPart("imageProduct")).thenReturn(filePartProduct);
		sd.setIdUser(1);
		when(pbl.addProduct(productName, productDescription, productPrice, productUnitsInStock, buffer, helperListCat, sd.getIdUser())).thenReturn(true);
		
//		for buttonAddCategory parameter
		when(request.getParameter("buttonAddCategory")).thenReturn("buttonAddCategory");
		when(request.getParameter("categoryName")).thenReturn(categoryName);
		Part filePartCategory = new Part() {
			
			@Override
			public void write(String fileName) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public String getSubmittedFileName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getSize() {
				// TODO Auto-generated method stub
				return 3;
			}
			
			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				InputStream is = new InputStream() {
					
					@Override
					public int read() throws IOException {
						// TODO Auto-generated method stub
						return 0;
					}
				};
				return is;
			}
			
			@Override
			public Collection<String> getHeaders(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getHeaderNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getHeader(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void delete() throws IOException {
				// TODO Auto-generated method stub
				
			}
		};
		when(request.getPart("imageCategory")).thenReturn(filePartCategory);
		when(pbl.addCategory(categoryName, buffer, sd.getIdUser())).thenReturn(true);
		
//		for searchPruductButton parameter
		when(request.getParameter("searchPruductButton")).thenReturn("searchPruductButton");
		when(request.getParameter("searchPruductByName")).thenReturn("something");
		when(pbl.findProduct("something")).thenReturn(new ArrayList<>());
		when(request.getServletContext()).thenReturn(sc);
		
		
//		for searchQuantityButton parameter
		when(request.getParameter("searchQuantityButton")).thenReturn("searchQuantityButton");
		when(request.getParameter("searchPruductByQuantity")).thenReturn("5");
		when(pbl.findProduct("5")).thenReturn(new ArrayList<>());
		
//		for editButton parameter
		when(request.getParameter("editButton")).thenReturn("editButton");
		when(request.getParameter("idProduct")).thenReturn("3");
		Product p = new Product();
		p.setId(3);
		when(pbl.getProduct(Integer.valueOf("3"))).thenReturn(p);
		when(request.getParameterValues("idProduct")).thenReturn(new String [1]);
		when(pbl.getProductCategories(p)).thenReturn(Arrays.asList(new Category() {{setId(1);}}, new Category(){{setId(2);}}));
		when(sc.getAttribute("button")).thenReturn("searchPruductButton");
		

//		for saveButton parameter
		when(request.getParameter("saveButton")).thenReturn("saveButton");
		when(pbl.updateProduct(productName, productDescription, productPrice, productUnitsInStock, buffer, 3, new int [] {1,2}, (int)filePartProduct.getSize(), sd.getIdUser(), helperListCat)).thenReturn(true);
		when(sc.getAttribute("button")).thenReturn("searchQuantityButton");	
		
		when(request.getRequestDispatcher("jsp/operatorPanel.jsp")).thenReturn(rd);
	
		op.doPost(request, response);
		
//		for buttonAddProduct parameter
		assertTrue(pbl.addProduct(productName, productDescription, productPrice, productUnitsInStock, buffer, helperListCat, sd.getIdUser()));
//		for buttonAddCategory parameter
		assertTrue(pbl.addCategory(categoryName, buffer, sd.getIdUser()));
//		for saveButton parameter
		assertTrue(pbl.updateProduct(productName, productDescription, productPrice, productUnitsInStock, buffer, 3, new int [] {1,2}, (int)filePartProduct.getSize(), sd.getIdUser(), helperListCat));
	}

}
