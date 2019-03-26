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
import pl.shopapp.entites.ProductCategory;
import pl.shopapp.webservice.ShopResource;
import pl.shopapp.webservice.ShopResourceLocal;
import pl.shopapp.webservice.model.CategoryJson;
import pl.shopapp.webservice.model.HardDisksJson;
import pl.shopapp.webservice.model.MainBoardJson;
import pl.shopapp.webservice.model.ProcessorsJson;
import pl.shopapp.webservice.model.RamMemoryJson;

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
		TypedQuery<Product> productQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllMainBoardXml", Product.class)).thenReturn(productQuery);
		when(productQuery.getResultList()).thenReturn(listProduct);
		
		assertEquals("productName", srl.getMainBoardXmls().get(0).getName());
	}

	@Test
	final void testGetRamMemoryXml() {
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
		TypedQuery<Product> productQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllRamMemoryXml", Product.class)).thenReturn(productQuery);
		when(productQuery.getResultList()).thenReturn(listProduct);
		
		assertEquals("productName", srl.getRamMemoryXml().get(0).getName());
	}

	@Test
	final void testGetAllProcessorsJson() {
		List<ProcessorsJson> listProc = new ArrayList<>();
		ProcessorsJson proc = new ProcessorsJson();
		proc.setId(1);
		proc.setBase64Image("base64Image");
		listProc.add(proc);
		@SuppressWarnings("unchecked")
		TypedQuery<ProcessorsJson> procQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllProcessorsJson")).thenReturn(procQuery);
		when(procQuery.getResultList()).thenReturn(listProc);
		
		assertEquals("base64Image", srl.getAllProcessorsJson().get(0).getBase64Image());
	}

	@Test
	final void testGetAllMainBoardJson() {
		List<MainBoardJson> listMB = new ArrayList<>();
		MainBoardJson mb = new MainBoardJson();
		mb.setId(1);
		mb.setBase64Image("base64Image");
		listMB.add(mb);
		@SuppressWarnings("unchecked")
		TypedQuery<MainBoardJson> mbQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllMainBoardJson")).thenReturn(mbQuery);
		when(mbQuery.getResultList()).thenReturn(listMB);
		
		assertEquals("base64Image", srl.getAllMainBoardJson().get(0).getBase64Image());
	}

	@Test
	final void testGetAllRamMemoryJson() {
		List<RamMemoryJson> listRam = new ArrayList<>();
		RamMemoryJson ram = new RamMemoryJson();
		ram.setId(1);
		ram.setBase64Image("base64Image");
		listRam.add(ram);
		@SuppressWarnings("unchecked")
		TypedQuery<RamMemoryJson> ramQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllRamMemoryJson")).thenReturn(ramQuery);
		when(ramQuery.getResultList()).thenReturn(listRam);
		
		assertEquals("base64Image", srl.getAllRamMemoryJson().get(0).getBase64Image());
	}

	@Test
	final void testGetAllHardDisksJson() {
		List<HardDisksJson> listHdd = new ArrayList<>();
		HardDisksJson hdd = new HardDisksJson();
		hdd.setId(1);
		hdd.setBase64Image("base64Image");
		listHdd.add(hdd);
		@SuppressWarnings("unchecked")
		TypedQuery<HardDisksJson> hddQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllHardDisksJson")).thenReturn(hddQuery);
		when(hddQuery.getResultList()).thenReturn(listHdd);
		
		assertEquals("base64Image", srl.getAllHardDisksJson().get(0).getBase64Image());
	}

	@Test
	final void testGetAllCategory() {
		List<CategoryJson> listCat = new ArrayList<>();
		CategoryJson cj = new CategoryJson();
		cj.setId(1);
		cj.setName("Category name");
		listCat.add(cj);
		@SuppressWarnings("unchecked")
		TypedQuery<CategoryJson> catQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("listCategoryJson")).thenReturn(catQuery);
		when(catQuery.getResultList()).thenReturn(listCat);
		
		assertEquals("Category name", srl.getAllCategory().get(0).getName());
	}

	@Test
	final void testGetProductJsonById() {
		Product pr = new Product();
		pr.setId(1);
		pr.setName("productName");
		when(pbl.getProduct(pr.getId())).thenReturn(pr);
		
		assertEquals("productName", srl.getProductJsonById(1).getName());
	}

	@Test
	final void testGetProductJsonByName() {
		Product pr = new Product();
		pr.setId(1);
		pr.setName("productName");
		List<Product> productList = new ArrayList<>();
		productList.add(pr);
		when(pbl.findProduct(pr.getName())).thenReturn(productList);
		
		Category cat = new Category();
		cat.setId(1);
		cat.setName("CategoryName");
		ProductCategory pc = new ProductCategory();
		pc.setProduct(pr);
		pc.setCategory(cat);
		List<ProductCategory> lpc = new ArrayList<>();
		lpc.add(pc);
		@SuppressWarnings("unchecked")
		TypedQuery<ProductCategory> pcQuery = mock(TypedQuery.class);
		when(pcQuery.getResultList()).thenReturn(lpc);
		when(em.createNamedQuery("getAllProductCategory", ProductCategory.class)).thenReturn(pcQuery);
		
		assertEquals("productName", srl.getProductJsonByName("productName").get(0).getName());
	}

	@Test
	final void testGetProductsJsonByIdRange() {
		Product pr = new Product();
		pr.setId(1);
		pr.setName("productName");
		byte [] productImage = new byte[100];
		pr.setProductImage(productImage);
		Category cat = new Category();
		cat.setId(1);
		cat.setName("Płyty główne");
		ProductCategory pc = new ProductCategory();
		pc.setProduct(pr);
		pc.setCategory(cat);
		List<ProductCategory> lpc = new ArrayList<>();
		lpc.add(pc);
		@SuppressWarnings("unchecked")
		TypedQuery<ProductCategory> pcQuery = mock(TypedQuery.class);
		when(pcQuery.setParameter("idFrom", 1)).thenReturn(pcQuery);
		when(pcQuery.setParameter("idTo", 1)).thenReturn(pcQuery);
		when(em.createNamedQuery("productsByIdRangeJson", ProductCategory.class)).thenReturn(pcQuery);
		when(pcQuery.getResultList()).thenReturn(lpc);
		
		assertEquals("productName", srl.getProductsJsonByIdRange(1, 1).get("Płyty główne").get(0).getName());
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
