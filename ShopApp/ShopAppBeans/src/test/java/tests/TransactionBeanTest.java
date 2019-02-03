package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
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

import pl.shopapp.beans.BasketData;
import pl.shopapp.beans.TransactionBean;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Product;
import pl.shopapp.entites.Transaction;
import pl.shopapp.entites.User;

class TransactionBeanTest {
	
	TransactionBean tb;
	EntityManager em;
	UserTransaction ut;
	User u;
	List<BasketData> basketList;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		em = mock(EntityManager.class);
		ut = mock(UserTransaction.class);
		tb = new TransactionBean(em, ut);
		u = new User();
		basketList = new ArrayList<>();
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		ut = null;
		tb = null;
		u = null;
		basketList.clear();
	}

	@Test
	void testNewTransaction() {
		BasketData bd1 = new BasketData();
		bd1.setProductId(1);
		bd1.setQuantity(3);
		BasketData bd2 = new BasketData();
		bd2.setProductId(2);
		bd2.setQuantity(3);
		basketList.add(bd1);
		basketList.add(bd2);
		int newUnitInStock = 0;		
		try {
			ut.begin();
			u.setId(5);
			when(em.find(User.class, u.getId())).thenReturn(u);
			Customer c = new Customer();
			c.setId(1);
			@SuppressWarnings("unchecked")
			TypedQuery<Customer> mockedCustomerQuery = mock(TypedQuery.class);
			when(em.createNamedQuery("customerQuery", Customer.class)).thenReturn(mockedCustomerQuery);
			when(mockedCustomerQuery.setParameter("user", u)).thenReturn(mockedCustomerQuery);
			when(mockedCustomerQuery.getSingleResult()).thenReturn(c);
			Transaction t;
			Product p = new Product();
			for(int i=0; i<basketList.size(); i++) {
				when(em.find(Product.class, basketList.get(i).getProductId())).thenReturn(p);
				newUnitInStock = p.getUnitsInStock() - basketList.get(i).getQuantity();
				String queryUpdate = "UPDATE Product SET unitsInStock = "+newUnitInStock+" WHERE id = "+p.getId()+"";
				Query mockedUpdateQuery = mock(Query.class);
				when(em.createQuery(queryUpdate)).thenReturn(mockedUpdateQuery);
				when(mockedUpdateQuery.executeUpdate()).thenReturn(1);
				t = new Transaction();
				t.setQuantity(basketList.get(i).getQuantity());
				t.setCustomer(c);
				t.setProductName(p.getName());
				t.setPrice(p.getPrice());
				t.setProduct(p);
				t.setDateTime(LocalDateTime.now());
				em.persist(t);		
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
		assertTrue(tb.newTransaction(5, basketList));
	}

	@Test
	void testGetTransactionsData() {
		LocalDateTime dateFrom = LocalDateTime.now().minusDays(15);
		LocalDateTime dateTo = LocalDateTime.now();
		Transaction t = new Transaction();
		t.setId(1);
		List<Transaction> expected = new ArrayList<>();
		expected.add(t);
		u.setId(5);
		when(em.find(User.class, u.getId())).thenReturn(u);
		Customer c = new Customer();
		c.setId(1);
		@SuppressWarnings("unchecked")
		TypedQuery<Customer> mockedCustomerQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("customerQuery", Customer.class)).thenReturn(mockedCustomerQuery);
		when(mockedCustomerQuery.setParameter("user", u)).thenReturn(mockedCustomerQuery);
		when(mockedCustomerQuery.getSingleResult()).thenReturn(c);
		@SuppressWarnings("unchecked")
		TypedQuery<Transaction> mockedTransactionQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("findTransactionsQuery", Transaction.class)).thenReturn(mockedTransactionQuery);
		when(mockedTransactionQuery.setParameter("customer", c)).thenReturn(mockedTransactionQuery);
		when(mockedTransactionQuery.setParameter("dateFrom", dateFrom)).thenReturn(mockedTransactionQuery);
		when(mockedTransactionQuery.setParameter("dateTo", dateTo)).thenReturn(mockedTransactionQuery);
		when(mockedTransactionQuery.getResultList()).thenReturn(expected);
		List<Transaction> actual = tb.getTransactionsData(u.getId(), dateFrom, dateTo);
		assertEquals(expected, actual);
	}

}
