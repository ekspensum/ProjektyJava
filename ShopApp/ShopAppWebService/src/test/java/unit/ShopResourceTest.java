package unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pl.shopapp.beans.ProductBeanLocal;
import pl.shopapp.beans.SessionData;
import pl.shopapp.beans.UserBeanLocal;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Product;
import pl.shopapp.webservice.ShopResource;
import pl.shopapp.webservice.ShopResourceLocal;
import pl.shopapp.webservice.model.CategoryJson;
import pl.shopapp.webservice.model.ProductDataJson;


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
		
		Category category = new Category();
		category.setProduct(listProduct);
		
		@SuppressWarnings("unchecked")
		TypedQuery<Category> productQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllMainBoardXml", Category.class)).thenReturn(productQuery);
		when(productQuery.getSingleResult()).thenReturn(category);
		
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
		
		Category category = new Category();
		category.setProduct(listProduct);
		
		@SuppressWarnings("unchecked")
		TypedQuery<Category> categoryQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllRamMemoryXml", Category.class)).thenReturn(categoryQuery);
		when(categoryQuery.getSingleResult()).thenReturn(category);
		
		assertEquals("productName", srl.getRamMemoryXml().get(0).getName());
	}

	@Test
	final void testGetAllProcessorsJson() {
		byte [] productImage = new byte[100];
		List<Product> listProc = new ArrayList<>();
		Product proc = new Product();
		proc.setId(1);
		proc.setProductImage(productImage);
		proc.setName("Processor-13");
		listProc.add(proc);
		
		Category category = new Category();
		category.setProduct(listProc);
		
		@SuppressWarnings("unchecked")
		TypedQuery<Category> categoryQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllProcessors", Category.class)).thenReturn(categoryQuery);
		when(categoryQuery.getSingleResult()).thenReturn(category);
		
		assertEquals("Processor-13", srl.getAllProcessorsJson().get(0).getName());
	}

	@Test
	final void testGetAllMainBoardJson() {
		byte [] productImage = new byte[100];
		List<Product> listMainBoard = new ArrayList<>();
		Product proc = new Product();
		proc.setId(1);
		proc.setProductImage(productImage);
		proc.setName("MainBoard-13");
		listMainBoard.add(proc);
		
		Category category = new Category();
		category.setProduct(listMainBoard);
		
		@SuppressWarnings("unchecked")
		TypedQuery<Category> categoryQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllMainBoard", Category.class)).thenReturn(categoryQuery);
		when(categoryQuery.getSingleResult()).thenReturn(category);
		
		assertEquals("MainBoard-13", srl.getAllMainBoardJson().get(0).getName());
	}

	@Test
	final void testGetAllRamMemoryJson() {
		byte [] productImage = new byte[100];
		List<Product> listRamMemory = new ArrayList<>();
		Product proc = new Product();
		proc.setId(1);
		proc.setProductImage(productImage);
		proc.setName("RamMemory-13");
		listRamMemory.add(proc);
		
		Category category = new Category();
		category.setProduct(listRamMemory);
		
		@SuppressWarnings("unchecked")
		TypedQuery<Category> categoryQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllRamMemory", Category.class)).thenReturn(categoryQuery);
		when(categoryQuery.getSingleResult()).thenReturn(category);
		
		assertEquals("RamMemory-13", srl.getAllRamMemoryJson().get(0).getName());
	}

	@Test
	final void testGetAllHardDisksJson() {
		byte [] productImage = new byte[100];
		List<Product> listHardDisk = new ArrayList<>();
		Product proc = new Product();
		proc.setId(1);
		proc.setProductImage(productImage);
		proc.setName("HardDisk-13");
		listHardDisk.add(proc);
		
		Category category = new Category();
		category.setProduct(listHardDisk);
		
		@SuppressWarnings("unchecked")
		TypedQuery<Category> categoryQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("getAllHardDisks", Category.class)).thenReturn(categoryQuery);
		when(categoryQuery.getSingleResult()).thenReturn(category);
		
		assertEquals("HardDisk-13", srl.getAllHardDisksJson().get(0).getName());
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
		Category category0 = new Category();
		category0.setId(0);
		Category category1 = new Category();
		category1.setId(1);
		List<Category> categories = new ArrayList<>();
		categories.add(category0);
		categories.add(category1);
		
		Product product = new Product();
		product.setId(1);
		product.setName("productName");
		product.setCategories(categories);
		
		List<Product> productList = new ArrayList<>();
		productList.add(product);
		when(pbl.findProduct(product.getName())).thenReturn(productList);
		
		assertEquals("productName", srl.getProductJsonByName("productName").get(0).getName());
	}

	@Test
	final void testGetProductsJsonByIdRange() {
		Category category = new Category();
		category.setId(1);
		category.setName("Płyty główne");
	
		List<Category> categories = new ArrayList<>();
		categories.add(category);
		
		Product product = new Product();
		product.setId(1);
		product.setName("productName");
		byte [] productImage = new byte[100];
		product.setProductImage(productImage);
		product.setCategories(categories);
		
		List<Product> products = new ArrayList<>();
		products.add(product);
		
		@SuppressWarnings("unchecked")
		TypedQuery<Product> productsListQuery = mock(TypedQuery.class);
		when(productsListQuery.setParameter("idFrom", 1)).thenReturn(productsListQuery);
		when(productsListQuery.setParameter("idTo", 1)).thenReturn(productsListQuery);
		when(em.createNamedQuery("productsByIdRangeJson", Product.class)).thenReturn(productsListQuery);
		when(productsListQuery.getResultList()).thenReturn(products);
		
		assertEquals("productName", srl.getProductsJsonByIdRange(1, 1).get("Płyty główne").get(0).getName());
	}

	@Test
	final void testAddNewProductFormParam() throws IllegalStateException, SecurityException, SystemException {
		SessionData sd = new SessionData();
		sd.setIdRole(3);
		sd.setFirstName("firstName");
		when(ubl.loginUser("login", "password")).thenReturn(sd);
		List<Integer> helperListCat = new ArrayList<>();
		helperListCat.add(24);
		when(pbl.addProduct("productName", "productDescription", 11.22, 33,	Base64.getDecoder().decode("base64Image"), helperListCat, sd.getIdUser())).thenReturn(true);
		
		assertEquals("Produkt został dodany do bazy danych.", srl.addNewProductFormParam("login", "password", "productName", "productDescription", 11.22, 33, "24", null, "base64Image"));
		assertEquals("Nie udało się dodać produktu do bazy danych.", srl.addNewProductFormParam("login", "password", "productName", "productDescription", 11.22, 33, "24", "0", "base64Image"));
		assertEquals("Brak autoryzacji lub autentykacji", srl.addNewProductFormParam("login", "passwordXX", "productName", "productDescription", 11.22, 33, "24", null, "base64Image"));
	}

	@Test
	final void testAddNewProductFormParamMap() throws NumberFormatException, IllegalStateException, SecurityException, SystemException {
		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("login", "login");
		mapParam.put("password", "password");
		mapParam.put("productName", "productName");
		mapParam.put("productDescription", "productDescription");
		mapParam.put("productPrice", "11.22");
		mapParam.put("productUnitsInStock", "33");
		mapParam.put("category1Id", "24");
		mapParam.put("category2Id", "26");
		mapParam.put("image", "value123");
		
		SessionData sd = new SessionData();
		sd.setIdRole(3);
		sd.setFirstName("firstName");
		when(ubl.loginUser(mapParam.get("login"), mapParam.get("password"))).thenReturn(sd);
		
		List<Integer> helperListCat = new ArrayList<>();
		helperListCat.add(Integer.valueOf(mapParam.get("category1Id")));
		if(mapParam.get("category2Id") != null)
			helperListCat.add(Integer.valueOf(mapParam.get("category2Id")));
		byte [] buffer = Base64.getDecoder().decode(mapParam.get("image"));		
		when(pbl.addProduct(mapParam.get("productName"), mapParam.get("productDescription"), Double.valueOf(mapParam.get("productPrice")), Integer.valueOf(mapParam.get("productUnitsInStock")), buffer, helperListCat, sd.getIdUser())).thenReturn(true);
		
		assertEquals("Produkt został dodany do bazy danych.", srl.addNewProductFormParamMap(mapParam));
		mapParam.put("image", "value12");
		assertEquals("Nie udało się dodać produktu do bazy danych.", srl.addNewProductFormParamMap(mapParam));
		mapParam.put("password", "password1");
		mapParam.put("image", "value123");
		assertEquals("Brak autoryzacji lub autentykacji", srl.addNewProductFormParamMap(mapParam));
	}

	@Test
	final void testAddNewProductJson() throws IllegalStateException, SecurityException, SystemException {
		ProductDataJson pdj = new ProductDataJson();
		pdj.setLogin("login");
		pdj.setPassword("password");
		pdj.setName("name");
		pdj.setDescription("description");
		pdj.setPrice(11.22);
		pdj.setUnitsInStock(33);
		pdj.setCategory1Id(24);
		pdj.setCategory2Id(0);
		byte [] image = Base64.getDecoder().decode("image123");	
		pdj.setImage(image);
		SessionData sd = new SessionData();
		sd.setIdRole(3);
		List<Integer> helperListCat = new ArrayList<>();
		helperListCat.add(24);
		helperListCat.add(0);
		when(ubl.loginUser(pdj.getLogin(), pdj.getPassword())).thenReturn(sd);
		when(pbl.addProduct(pdj.getName(), pdj.getDescription(), pdj.getPrice(), pdj.getUnitsInStock(),	pdj.getImage(), helperListCat, sd.getIdUser())).thenReturn(true);
		
		assertEquals("Produkt został dodany do bazy danych.", srl.addNewProductJson(pdj));
		pdj.setLogin("login1");
		assertEquals("Brak autoryzacji lub autentykacji", srl.addNewProductJson(pdj));
		image = Base64.getDecoder().decode("image12");	
		pdj.setLogin("login");
		pdj.setImage(image);
		assertEquals("Nie udało się dodać produktu do bazy danych.", srl.addNewProductJson(pdj));
	}

	@Test
	final void testUpdateProductData() throws IllegalStateException, SecurityException, SystemException {
		ProductDataJson pdj = new ProductDataJson();
		pdj.setLogin("login");
		pdj.setPassword("password");
		pdj.setIdProduct(1);
		pdj.setName("name");
		pdj.setDescription("description");
		pdj.setPrice(11.22);
		pdj.setUnitsInStock(33);
		pdj.setCategory1Id(24);
		pdj.setCategory2Id(0);
		pdj.setPreviousCategory1Id(24);
		pdj.setPreviousCategory2Id(0);
		byte [] image = Base64.getDecoder().decode("image123");	
		pdj.setImage(image);
		SessionData sd = new SessionData();
		sd.setIdRole(3);
		List<Integer> helperListCat = new ArrayList<>();
		helperListCat.add(24);
		helperListCat.add(0);

		when(ubl.loginUser(pdj.getLogin(), pdj.getPassword())).thenReturn(sd);
		when(pbl.updateProduct(pdj.getName(), pdj.getDescription(), pdj.getPrice(), pdj.getUnitsInStock(), pdj.getImage(), pdj.getIdProduct(), pdj.getImageSize(), sd.getIdUser(), helperListCat)).thenReturn(true);
		
		assertEquals("Dane produktu id = "+pdj.getIdProduct()+" zostały zaktualizowane w bazie danych.", srl.updateProductData(pdj));
		pdj.setLogin("login1");
		assertEquals("Brak autoryzacji lub autentykacji", srl.updateProductData(pdj));
		image = Base64.getDecoder().decode("image12");	
		pdj.setLogin("login");
		pdj.setImage(image);
		assertEquals("Nie udało się zaktualizować danych produktu id = "+pdj.getIdProduct()+" w bazie danych.", srl.updateProductData(pdj));
	}

}
