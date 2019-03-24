package unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;
import pl.shopapp.webservice.ShopResource;
import pl.shopapp.webservice.ShopResourceLocal;

class ShopResourceTest {

	@Mock
	private EntityManager em;
	@Mock
	private UserTransaction ut;
	@Mock
	private ProductBeanLocal pbl;
	@Mock
	private UserBeanLocal ubl;
	
	private ShopResourceLocal srl;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		srl = new ShopResource(em, ut, pbl, ubl);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	final void testGetProcessors() {
		Category cat = new Category();
		cat.setId(1);
		cat.setName("Procesory");
		List<Category> listCategory = new ArrayList<>();
		listCategory.add(cat);
		when(pbl.listCategory()).thenReturn(listCategory);
		
		Product pr = new Product();
		pr.setId(1);
		pr.setName("productName");
		pr.setDescription("productDescription");
		pr.setPrice(11.22);
		pr.setUnitsInStock(1);
		byte [] productImage = new byte[100];
		pr.setProductImage(productImage);
		List<Product> listProduct = new ArrayList<>();
		listProduct.add(pr);
		when(pbl.listProductByCategory(listCategory.get(0).getId())).thenReturn(listProduct);
		
		assertEquals(475, srl.getProcessors().length);
	}

	@Test
	final void testGetHardDisks() {
		Category cat = new Category();
		cat.setId(1);
		cat.setName("Dyski twarde");
		List<Category> listCategory = new ArrayList<>();
		listCategory.add(cat);
		when(pbl.listCategory()).thenReturn(listCategory);
		
		Product pr = new Product();
		pr.setId(1);
		pr.setName("productName");
		pr.setDescription("productDescription");
		pr.setPrice(11.22);
		pr.setUnitsInStock(1);
		byte [] productImage = new byte[100];
		pr.setProductImage(productImage);
		List<Product> listProduct = new ArrayList<>();
		listProduct.add(pr);
		when(pbl.listProductByCategory(listCategory.get(0).getId())).thenReturn(listProduct);
		
		assertEquals(471, srl.getHardDisks().length);
	}

	@Test
	final void testGetProductById() {
		Product pr = new Product();
		pr.setId(1);
		pr.setName("productName");
		pr.setDescription("productDescription");
		pr.setPrice(11.22);
		pr.setUnitsInStock(1);
		byte [] productImage = new byte[100];
		pr.setProductImage(productImage);
		when(pbl.getProduct(pr.getId())).thenReturn(pr);
		
		assertEquals(413, srl.getProductById(pr.getId()).length);
	}

	@Test
	final void testGetProductsById() {
		Product pr = new Product();
		pr.setId(1);
		pr.setName("productName");
		pr.setDescription("productDescription");
		pr.setPrice(11.22);
		pr.setUnitsInStock(1);
		byte [] productImage = new byte[100];
		pr.setProductImage(productImage);
		List<Product> listProduct = new ArrayList<>();
		listProduct.add(pr);
		@SuppressWarnings("unchecked")
		TypedQuery<Product> productQurey = mock(TypedQuery.class);
		when(em.createNamedQuery("productsByIdRange", Product.class)).thenReturn(productQurey);
		when(productQurey.setParameter("idFrom", 1)).thenReturn(productQurey);
		when(productQurey.setParameter("idTo", 1)).thenReturn(productQurey);
		when(productQurey.getResultList()).thenReturn(listProduct);
		
		assertEquals(467, srl.getProductsById(pr.getId(), pr.getId()).length);
	}

	@Test
	final void testGetMainBoardXmls() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetRamMemoryXml() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetAllProcessorsJson() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetAllMainBoardJson() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetAllRamMemoryJson() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetAllHardDisksJson() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetAllCategory() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetProductJsonById() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetProductJsonByName() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testGetProductsJsonByIdRange() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testAddNewProductFormParam() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testAddNewProductFormParamMap() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testAddNewProductJson() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	final void testUpdateProductData() {
		fail("Not yet implemented"); // TODO
	}

}
