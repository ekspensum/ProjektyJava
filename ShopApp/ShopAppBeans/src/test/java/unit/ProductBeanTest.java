package unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pl.shopapp.beans.ProductBean;
import pl.shopapp.entites.Category;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Product;
import pl.shopapp.entites.User;

class ProductBeanTest {

	ProductBean pb;
	EntityManager em;
	UserTransaction ut;
	User user;
	List<Integer> helperListCat;
	Product product;
	Category cat;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		em = Mockito.mock(EntityManager.class);
		ut = Mockito.mock(UserTransaction.class);
		pb = new ProductBean(em, ut);
		user = new User();
		helperListCat = new ArrayList<>();
		helperListCat.add(1);
		helperListCat.add(2);
		helperListCat.add(3);
		helperListCat.add(4);
		helperListCat.add(5);
		helperListCat.add(6);
		helperListCat.add(7);
		helperListCat.add(8);
		helperListCat.add(9);
		product = new Product();
		cat = new Category();
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		ut = null;
		pb = null;
		helperListCat.clear();
		product = null;
		cat = null;
	}

	@Test
	void testAddProduct() throws IllegalStateException, SecurityException, SystemException {
		byte[] buffer = new byte[10];

		when(em.find(User.class, 3)).thenReturn(user);

		Operator operator = new Operator();
		@SuppressWarnings("unchecked")
		TypedQuery<Operator> mockedOperatorQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("operatorQuery", Operator.class)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.setParameter("user", user)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.getSingleResult()).thenReturn(operator);
		product.setOp(operator);

		List<Category> categories = new ArrayList<>();
		for (int i = 0; i < helperListCat.size(); i++) {
			when(em.find(Category.class, helperListCat.get(i))).thenReturn(cat);
			categories.add(cat);
		}
		product.setCategories(categories);

		assertTrue(pb.addProduct("productName", "productDescription", 11.22, 1, buffer, helperListCat, 3));
	}

	@Test
	void testUpdateProductWithOutImage() throws IllegalStateException, SecurityException, SystemException {
		byte[] buffer = new byte[0];

		product.setId(1);
		when(em.find(Product.class, product.getId())).thenReturn(product);
		when(em.find(User.class, 3)).thenReturn(user);
		user.setId(3);
		Operator op = new Operator();
		@SuppressWarnings("unchecked")
		TypedQuery<Operator> mockedOperatorQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("operatorQuery", Operator.class)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.setParameter("user", user)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.getSingleResult()).thenReturn(op);
		op.setId(3);
		product.setOp(op);
		product.setPrice(10.10);

		List<Category> categories = new ArrayList<>();
		for (int i = 0; i < helperListCat.size(); i++) {
			when(em.find(Category.class, helperListCat.get(i))).thenReturn(cat);
			cat.setId(helperListCat.get(i));
			categories.add(cat);
		}
		product.setCategories(categories);

		assertTrue(pb.updateProduct("productName", "productDescription", 11.22, 1, buffer, 1, 0, 3, helperListCat));
		assertEquals(11.22, product.getPrice());
		assertNotEquals(10.10, product.getPrice());
	}

	@Test
	void testUpdateProductWithImage() throws IllegalStateException, SecurityException, SystemException {
		byte[] buffer = new byte[10];

		product.setId(1);
		when(em.find(Product.class, product.getId())).thenReturn(product);
		when(em.find(User.class, 3)).thenReturn(user);
		user.setId(3);
		Operator op = new Operator();
		@SuppressWarnings("unchecked")
		TypedQuery<Operator> mockedOperatorQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("operatorQuery", Operator.class)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.setParameter("user", user)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.getSingleResult()).thenReturn(op);
		op.setId(3);
		product.setOp(op);
		product.setDescription("description");

		product.setProductImage(buffer);

		List<Category> categories = new ArrayList<>();
		for (int i = 0; i < helperListCat.size(); i++) {
			when(em.find(Category.class, helperListCat.get(i))).thenReturn(cat);
			cat.setId(helperListCat.get(i));
			categories.add(cat);
		}
		product.setCategories(categories);

		assertTrue(pb.updateProduct("productName", "productDescription", 11.22, 1, buffer, 1, 1, 3, helperListCat));
		assertEquals(10, product.getProductImage().length);
		assertEquals("productDescription", product.getDescription());
		assertNotEquals("description", product.getDescription());
	}
	
	@Test
	void testFindProductString() {
		product.setName("name");
		String name = "name";
		List<Product> expected = new ArrayList<>();
		expected.add(product);
		@SuppressWarnings("unchecked")
		TypedQuery<Product> mockedProductQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("productsByName", Product.class)).thenReturn(mockedProductQuery);
		when(mockedProductQuery.setParameter("name", "%"+name+"%")).thenReturn(mockedProductQuery);
		when(mockedProductQuery.getResultList()).thenReturn(expected);
		List<Product> actual = pb.findProduct("name");
		assertEquals(expected.get(0).getName(), actual.get(0).getName());
	}

	@Test
	void testFindProductInt() {
		product.setId(1);
		List<Product> expected = new ArrayList<>();
		expected.add(product);
		@SuppressWarnings("unchecked")
		TypedQuery<Product> mockedProductQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("productsByQuantity", Product.class)).thenReturn(mockedProductQuery);
		when(mockedProductQuery.setParameter("quantity", 1)).thenReturn(mockedProductQuery);
		when(mockedProductQuery.getResultList()).thenReturn(expected);
		List<Product> actual = pb.findProduct(1);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	void testAddCategory() throws IllegalStateException, SecurityException, SystemException {
		byte[] buffer = new byte[10];
		when(em.find(User.class, 3)).thenReturn(user);
		user.setId(3);
		Operator op = new Operator();
		op.setId(1);
		@SuppressWarnings("unchecked")
		TypedQuery<Operator> mockedOperatorQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("operatorQuery", Operator.class)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.setParameter("user", user)).thenReturn(mockedOperatorQuery);
		when(mockedOperatorQuery.getSingleResult()).thenReturn(op);
		cat.setOp(op);

		assertTrue(pb.addCategory("categoryName", buffer, user.getId()));
	}

	@Test
	void testListCategory() {
		cat.setId(1);
		List<Category> expected = new ArrayList<>();
		expected.add(cat);
		@SuppressWarnings("unchecked")
		TypedQuery<Category> mockedQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("listCategory", Category.class)).thenReturn(mockedQuery);
		when(mockedQuery.getResultList()).thenReturn(expected);
		List<Category> actual = pb.listCategory();
		assertEquals(expected, actual);
	}

	@Test
	void testListProductByCategory() {
		product.setId(1);
		List<Product> productsExpected = new ArrayList<>();
		productsExpected.add(product);
		cat.setProduct(productsExpected);
		when(em.find(Category.class, 1)).thenReturn(cat);
		List<Product> productsActual = pb.listProductByCategory(1);
		assertEquals(productsExpected, productsActual);
	}

	@Test
	void testGetProduct() {
		product.setId(1);
		when(em.find(Product.class, product.getId())).thenReturn(product);
		Product actual = pb.getProduct(1);
		assertEquals(product, actual);
	}

	@Test
	void testGetProductCategories() {
		product.setId(1);
		cat.setId(1);
		List<Category> expected = new ArrayList<>();
		expected.add(cat);
		@SuppressWarnings("unchecked")
		TypedQuery<Category> mockedQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("productCategoriesQuery", Category.class)).thenReturn(mockedQuery);
		when(mockedQuery.setParameter("product", product)).thenReturn(mockedQuery);
		when(mockedQuery.getResultList()).thenReturn(expected);
		List<Category> actual = pb.getProductCategories(product);
		assertEquals(expected, actual);
	}

}
