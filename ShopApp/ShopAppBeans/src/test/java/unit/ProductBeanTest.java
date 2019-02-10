package unit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
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
import pl.shopapp.entites.ProductCategory;
import pl.shopapp.entites.User;

class ProductBeanTest {

	ProductBean pb;
	EntityManager em;
	UserTransaction ut;
	User user;
	List<Integer> helperListCat;
	Product p;
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
		p = new Product();
		cat = new Category();
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		ut = null;
		pb = null;
		helperListCat.clear();
		p = null;
		cat = null;
	}

	@Test
	void testAddProduct() {
		byte [] buffer = new byte[10];
		try {
			ut.begin();
			when(em.find(User.class, 3)).thenReturn(user);

			Operator operator = new Operator();
			@SuppressWarnings("unchecked")
			TypedQuery<Operator> mockedOperatorQuery = mock(TypedQuery.class);
			when(em.createNamedQuery("operatorQuery", Operator.class)).thenReturn(mockedOperatorQuery);
			when(mockedOperatorQuery.setParameter("user", user)).thenReturn(mockedOperatorQuery);
			when(mockedOperatorQuery.getSingleResult()).thenReturn(operator);

			p.setOp(operator);
			em.persist(p);
			for(int i = 0; i<helperListCat.size(); i++) {
				when(em.find(Category.class, helperListCat.get(i))).thenReturn(cat);
				ProductCategory pc = new ProductCategory();
				pc.setCategory(cat);
				pc.setProduct(p);
				em.persist(pc);
			}
			ut.commit();

		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		assertTrue(pb.addProduct("productName", "productDescription", 11.22, 1, buffer, helperListCat, 3));
	}

	@Test
	void testUpdateProductWithOutImage() {
		int [] categoryToEdit = new int [9];
		byte [] buffer = new byte[10];
		try {
			ut.begin();
			p.setId(1);
			when(em.find(Product.class, p.getId())).thenReturn(p);
			when(em.find(User.class, 3)).thenReturn(user);
			user.setId(3);
			Operator op = new Operator();
			@SuppressWarnings("unchecked")
			TypedQuery<Operator> mockedOperatorQuery = mock(TypedQuery.class);
			when(em.createNamedQuery("operatorQuery", Operator.class)).thenReturn(mockedOperatorQuery);
			when(mockedOperatorQuery.setParameter("user", user)).thenReturn(mockedOperatorQuery);
			when(mockedOperatorQuery.getSingleResult()).thenReturn(op);
			op.setId(3);
			String updateProductWithoutImage = "UPDATE Product SET name = 'productName', description = 'productDescription', price = 11.22, unitsInStock = 1, dateTime = '"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"', op_id = 3 WHERE id = "+p.getId()+"";
			Query mockedQueryWithOutImage = mock(Query.class);
			when(em.createQuery(updateProductWithoutImage)).thenReturn(mockedQueryWithOutImage);
			when(mockedQueryWithOutImage.executeUpdate()).thenReturn(1);
			for(int i = 0; i<helperListCat.size(); i++) {
				when(em.find(Category.class, categoryToEdit[i])).thenReturn(cat);
				ProductCategory pc = new ProductCategory();
				pc.setId(1);
				@SuppressWarnings("unchecked")
				TypedQuery<ProductCategory> mockedProductCategoryQuery = mock(TypedQuery.class);
				when(em.createNamedQuery("getProductCategoryQuery", ProductCategory.class)).thenReturn(mockedProductCategoryQuery);
				when(mockedProductCategoryQuery.setParameter("category", cat)).thenReturn(mockedProductCategoryQuery);
				when(mockedProductCategoryQuery.setParameter("product", p)).thenReturn(mockedProductCategoryQuery);
				when(mockedProductCategoryQuery.getSingleResult()).thenReturn(pc);
				String updateProductCategory = "UPDATE ProductCategory SET category_id = "+helperListCat.get(i)+", product_id = 1 WHERE id = "+pc.getId()+"";
				Query mockedUpdateProductCategoryQuery = mock(Query.class);
				when(em.createQuery(updateProductCategory)).thenReturn(mockedUpdateProductCategoryQuery);
				when(mockedUpdateProductCategoryQuery.executeUpdate()).thenReturn(1);
			}			
			ut.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		assertTrue(pb.updateProduct("productName", "productDescription", 11.22, 1, buffer, 1, categoryToEdit, 0, 3, helperListCat));
	}

	@Test
	void testUpdateProductWithImage() {
		int [] categoryToEdit = new int [9];
		byte [] buffer = new byte[10];
		try {
			ut.begin();
			p.setId(1);
			when(em.find(Product.class, p.getId())).thenReturn(p);
			when(em.find(User.class, 3)).thenReturn(user);
			user.setId(3);
			Operator op = new Operator();
			@SuppressWarnings("unchecked")
			TypedQuery<Operator> mockedOperatorQuery = mock(TypedQuery.class);
			when(em.createNamedQuery("operatorQuery", Operator.class)).thenReturn(mockedOperatorQuery);
			when(mockedOperatorQuery.setParameter("user", user)).thenReturn(mockedOperatorQuery);
			when(mockedOperatorQuery.getSingleResult()).thenReturn(op);
			op.setId(3);
			String updateProductWithImage = "UPDATE Product SET name = 'productName', description = 'productDescription', price = 11.22, unitsInStock = 1, productImage = :productImage, dateTime = '"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"', op_id = 3 WHERE id = 1";
			Query mockedQueryWithImage = mock(Query.class);
			when(em.createQuery(updateProductWithImage)).thenReturn(mockedQueryWithImage);
			when(mockedQueryWithImage.setParameter("productImage", buffer)).thenReturn(mockedQueryWithImage);
			when(mockedQueryWithImage.executeUpdate()).thenReturn(1);			
			for(int i = 0; i<helperListCat.size(); i++) {
				when(em.find(Category.class, categoryToEdit[i])).thenReturn(cat);
				ProductCategory pc = new ProductCategory();
				pc.setId(1);
				@SuppressWarnings("unchecked")
				TypedQuery<ProductCategory> mockedProductCategoryQuery = mock(TypedQuery.class);
				when(em.createNamedQuery("getProductCategoryQuery", ProductCategory.class)).thenReturn(mockedProductCategoryQuery);
				when(mockedProductCategoryQuery.setParameter("category", cat)).thenReturn(mockedProductCategoryQuery);
				when(mockedProductCategoryQuery.setParameter("product", p)).thenReturn(mockedProductCategoryQuery);
				when(mockedProductCategoryQuery.getSingleResult()).thenReturn(pc);
				String updateProductCategory = "UPDATE ProductCategory SET category_id = "+helperListCat.get(i)+", product_id = 1 WHERE id = "+pc.getId()+"";
				Query mockedUpdateProductCategoryQuery = mock(Query.class);
				when(em.createQuery(updateProductCategory)).thenReturn(mockedUpdateProductCategoryQuery);
				when(mockedUpdateProductCategoryQuery.executeUpdate()).thenReturn(1);
			}
			ut.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				ut.rollback();
			} catch (IllegalStateException | SecurityException | SystemException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		assertTrue(pb.updateProduct("productName", "productDescription", 11.22, 1, buffer, 1, categoryToEdit, 1, 3, helperListCat));
	}
	
	@Test
	void testFindProductString() {
		p.setName("name");
		String name = "name";
		List<Product> expected = new ArrayList<>();
		expected.add(p);
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
		p.setId(1);
		List<Product> expected = new ArrayList<>();
		expected.add(p);
		@SuppressWarnings("unchecked")
		TypedQuery<Product> mockedProductQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("productsByQuantity", Product.class)).thenReturn(mockedProductQuery);
		when(mockedProductQuery.setParameter("quantity", 1)).thenReturn(mockedProductQuery);
		when(mockedProductQuery.getResultList()).thenReturn(expected);
		List<Product> actual = pb.findProduct(1);
		assertEquals(expected.get(0).getId(), actual.get(0).getId());
	}

	@Test
	void testAddCategory() {
		byte [] buffer = new byte [10];
		try {
			ut.begin();
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
			em.persist(cat);
			ut.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		p.setId(1);
		List<Product> expected = new ArrayList<>();
		expected.add(p);
		@SuppressWarnings("unchecked")
		TypedQuery<Product> mockedQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("productsByCategory", Product.class)).thenReturn(mockedQuery);
		when(mockedQuery.setParameter(1, 1)).thenReturn(mockedQuery);
		when(mockedQuery.getResultList()).thenReturn(expected);
		List<Product> actual = pb.listProductByCategory(1);
		assertEquals(expected, actual);
	}

	@Test
	void testGetProduct() {
		p.setId(1);
		when(em.find(Product.class, p.getId())).thenReturn(p);
		Product actual = pb.getProduct(1);
		assertEquals(p, actual);
	}

	@Test
	void testGetProductCategories() {
		p.setId(1);
		cat.setId(1);
		List<Category> expected = new ArrayList<>();
		expected.add(cat);
		@SuppressWarnings("unchecked")
		TypedQuery<Category> mockedQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("productCategoriesQuery", Category.class)).thenReturn(mockedQuery);
		when(mockedQuery.setParameter("product", p)).thenReturn(mockedQuery);
		when(mockedQuery.getResultList()).thenReturn(expected);
		List<Category> actual = pb.getProductCategories(p);
		assertEquals(expected, actual);
	}

}
