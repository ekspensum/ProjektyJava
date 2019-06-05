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
import javax.transaction.UserTransaction;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.shopapp.beans.BasketData;
import pl.shopapp.beans.SendEmail;
import pl.shopapp.beans.TransactionBean;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Product;
import pl.shopapp.entites.Transaction;
import pl.shopapp.entites.User;

class TransactionBeanTest {
	
	TransactionBean tb;
	EntityManager em;
	UserTransaction ut;
	SendEmail mail;
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
		mail = mock(SendEmail.class);
		tb = new TransactionBean(em, ut, mail);
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

		u.setId(5);
		when(em.find(User.class, u.getId())).thenReturn(u);
		Customer c = new Customer();
		c.setId(1);
		c.setEmail("email@gmailtest.com");
		@SuppressWarnings("unchecked")
		TypedQuery<Customer> mockedCustomerQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("customerQuery", Customer.class)).thenReturn(mockedCustomerQuery);
		when(mockedCustomerQuery.setParameter("user", u)).thenReturn(mockedCustomerQuery);
		when(mockedCustomerQuery.getSingleResult()).thenReturn(c);

		Product p = new Product();
		for (int i = 0; i < basketList.size(); i++) {
			when(em.find(Product.class, basketList.get(i).getProductId())).thenReturn(p);
			newUnitInStock = p.getUnitsInStock() - basketList.get(i).getQuantity();
			String queryUpdate = "UPDATE Product SET unitsInStock = " + newUnitInStock + " WHERE id = " + p.getId()	+ "";
			Query mockedUpdateQuery = mock(Query.class);
			when(em.createQuery(queryUpdate)).thenReturn(mockedUpdateQuery);
			when(mockedUpdateQuery.executeUpdate()).thenReturn(1);
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
		List<Transaction> actual = tb.getTransactionsData(u.getId(), dateFrom, dateTo, "productIdDescending");
		assertEquals(expected, actual);
	}
	
	@Test
	void testGetNoExecOrder() {
		LocalDateTime dateFrom = LocalDateTime.now().minusDays(15);
		LocalDateTime dateTo = LocalDateTime.now();
		@SuppressWarnings("unchecked")
		TypedQuery<Transaction> transactionQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("findNoExecTransactionQuery", Transaction.class)).thenReturn(transactionQuery);
		when(transactionQuery.setParameter("dateFrom", dateFrom)).thenReturn(transactionQuery);
		when(transactionQuery.setParameter("dateTo", dateTo)).thenReturn(transactionQuery);
		when(transactionQuery.getResultList()).thenReturn(new ArrayList<Transaction>());
		assertEquals(new ArrayList<Transaction>(), tb.getNoExecOrder(dateFrom, dateTo));
	}
	
	@Test
	void testExecOrder() {
		u.setId(1);
		String [] idTransactionArray = {"1"};
		String idTransaction = idTransactionArray[0];
		for(int i = 1; i<idTransactionArray.length; i++)
			idTransaction += ", "+idTransactionArray[i];
		@SuppressWarnings("unchecked")
		TypedQuery<Transaction> transactionQuery = mock(TypedQuery.class);
		when(em.createQuery("SELECT tr FROM Transaction tr WHERE id IN ("+idTransaction+")", Transaction.class)).thenReturn(transactionQuery);
		Customer customer = new Customer();
		Transaction tr = new Transaction();
		tr.setCustomer(customer);
		List<Transaction> trl = new ArrayList<>();
		tr.setId(1);
		trl.add(tr);
		when(transactionQuery.getResultList()).thenReturn(trl);
		
		when(em.find(User.class, u.getId())).thenReturn(u);
		Operator op = new Operator();
		@SuppressWarnings("unchecked")
		TypedQuery<Operator> operatorQuery = mock(TypedQuery.class);
		when(em.createNamedQuery("operatorQuery", Operator.class)).thenReturn(operatorQuery);
		when(operatorQuery.setParameter("user", u)).thenReturn(operatorQuery);
		when(operatorQuery.getSingleResult()).thenReturn(op);
		Query updateQuery = mock(Query.class);
		when(em.createQuery("UPDATE Transaction SET execOrder = true, execOrderDate = '"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"', execOrderOperator = :operator WHERE id IN ("+idTransaction+")")).thenReturn(updateQuery);
		when(updateQuery.setParameter("operator", op)).thenReturn(updateQuery);
		when(updateQuery.executeUpdate()).thenReturn(1);
		
		assertTrue(tb.execOrder(idTransactionArray, u.getId()));
	}
}
