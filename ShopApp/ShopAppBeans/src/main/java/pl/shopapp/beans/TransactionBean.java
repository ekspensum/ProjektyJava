package pl.shopapp.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import pl.shopapp.entites.Customer;
import pl.shopapp.entites.Operator;
import pl.shopapp.entites.Product;
import pl.shopapp.entites.Transaction;
import pl.shopapp.entites.User;


/**
 * Session Bean implementation class TransactionBean
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class TransactionBean implements TransactionBeanRemote, TransactionBeanLocal {

	@PersistenceContext(unitName = "ShopAppEntites")
	private EntityManager em;

	@Resource
	private UserTransaction ut;
	
	private SendEmail mail;
	StringBuilder message;
	
    /**
     * Default constructor. 
     */
    public TransactionBean() {
        // TODO Auto-generated constructor stub
		mail = new SendEmail();
    }
    
    //for tests
	public TransactionBean(EntityManager em, UserTransaction ut, SendEmail mail) {
		super();
		this.em = em;
		this.ut = ut;
		this.mail = mail;
	}

	@Override
	public boolean newTransaction(int idUser, List<BasketData> basketList) {
		int newUnitInStock = 0;
		message = new StringBuilder();
		try {
			ut.begin();
			User user = em.find(User.class, idUser);
			Customer customer = (Customer) em.createNamedQuery("customerQuery", Customer.class).setParameter("user", user).getSingleResult();
			Transaction transaction;
			Product product;
			for(int i=0; i<basketList.size(); i++) {
				product = em.find(Product.class, basketList.get(i).getProductId());
				newUnitInStock = product.getUnitsInStock() - basketList.get(i).getQuantity();
				String queryUpdate = "UPDATE Product SET unitsInStock = "+newUnitInStock+" WHERE id = "+product.getId()+"";
				em.createQuery(queryUpdate).executeUpdate();
				transaction = new Transaction();
				transaction.setQuantity(basketList.get(i).getQuantity());
				transaction.setCustomer(customer);
				transaction.setProductName(product.getName());
				transaction.setPrice(product.getPrice());
				transaction.setProduct(product);
				transaction.setDateTime(LocalDateTime.now());
				message.append("<tr><td>").append(i+1).append(". </td><td>").append(basketList.get(i).getProductName()).append("</td><td>").append(basketList.get(i).getQuantity()).append("</td><td>").append(basketList.get(i).getPrice()).append("</td><td>").append(basketList.get(i).getQuantity() * basketList.get(i).getPrice()).append("</td></tr>");
				em.persist(transaction);		
			}
			basketList.clear();
			String mailSubject = "Potwierdzenie dokonania zakupu towarów w sklepie internetowym ShopApp.";
			String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+customer.getFirstName()+" "+customer.getLastName()+"</b><br>"
					+ "Potwierdzamy zawarcie transakcji zakupu następujących towarów:</font><br><br>"
					+ "<table>"
					+ "<tr><th>Lp</th><th>Nazwa produktu</th><th>Ilość</th><th>Cena</th><th>Wartość</th></tr>"
					+ message
					+ "</table><br>"
					+ "<font size='3'>Towar zostanie wysłany w ciągu 48 godzin po otrzymaniu zapłaty.<br><br>"
					+ "Pozdrawiamy<br>Dział Obsługi Klienta</font><br>"+mail.getMailFrom();
			mail.sendEmail(customer.getEmail(), mailSubject, mailText);
			ut.commit();
			return true;
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
		return false;
	}

	@Override
	public List<Transaction> getTransactionsData(int idUser, LocalDateTime dateFrom, LocalDateTime dateTo, String sortBy) {
		User u = em.find(User.class, idUser);
		Customer c = em.createNamedQuery("customerQuery", Customer.class).setParameter("user", u).getSingleResult();
		List<Transaction> tl = em.createNamedQuery("findTransactionsQuery", Transaction.class).setParameter("customer", c).setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).getResultList();
		tl.sort(new Comparator<Transaction>() {

			@Override
			public int compare(Transaction o1, Transaction o2) {
				if(sortBy.equals("productPriceAscending")) {
					if(o1.getPrice() > o2.getPrice())
						return 1;
				}
				if(sortBy.equals("productPriceDescending")) {
					if(o2.getPrice() > o1.getPrice())
						return -1;
				}
				if(sortBy.equals("productNameAscending")) {
					return o1.getProductName().compareTo(o2.getProductName());
				}
				if(sortBy.equals("productNameDescending")) {
					return o2.getProductName().compareTo(o1.getProductName());
				}
				if(sortBy.equals("productIdDescending")) {
					return -1;
				}
				return -1;
			}
		});
		return tl;
	}

	@Override
	public List<Transaction> getNoExecOrder(LocalDateTime dateFrom, LocalDateTime dateTo) {
		return em.createNamedQuery("findNoExecTransactionQuery", Transaction.class).setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).getResultList();
	}

	@Override
	public boolean execOrder(String [] idTransactionArray, int idUser) {
		// TODO Auto-generated method stub
			User u = em.find(User.class, idUser);
			Operator op = em.createNamedQuery("operatorQuery", Operator.class).setParameter("user", u).getSingleResult();
			
			String mailSubject = "Potwierdzenie dokonania wysyłki towarów zakupionych w sklepie internetowym ShopApp.";
			message = new StringBuilder();		
			
//			create statement for get selection transaction
			String idTransaction = idTransactionArray[0];
			for(int i = 1; i<idTransactionArray.length; i++)
				idTransaction += ", "+idTransactionArray[i];
			List<Transaction> trl = em.createQuery("SELECT tr FROM Transaction tr WHERE id IN ("+idTransaction+")", Transaction.class).getResultList();
			
//			group by customer
			List<Customer> customerList = new ArrayList<>();
			customerList.add(trl.get(0).getCustomer());
			boolean flag;
			for(int i=1; i<trl.size(); i++) {
				flag = false;
				for(int j=0; j<customerList.size(); j++) {
					if(trl.get(i).getCustomer().getId() == customerList.get(j).getId())
						flag = true;
				}
			if(!flag)
				customerList.add(trl.get(i).getCustomer());
			}
			
			for(int i=0; i<customerList.size(); i++) {
				message.setLength(0);
				int k = 0;
				try {
					ut.begin();
					idTransaction = "";
					k = 1;
					for(int j=0; j<trl.size(); j++) {
						if(customerList.get(i).getId() == trl.get(j).getCustomer().getId()) {
							message.append("<tr><td>").append(k).append(". </td><td>").append(trl.get(j).getProductName()).append("</td><td>").append(trl.get(j).getQuantity()).append("</td><td>").append(trl.get(j).getPrice()).append("</td><td>").append(trl.get(j).getQuantity() * trl.get(j).getPrice()).append("</td></tr>");
							idTransaction += trl.get(j).getId()+",";
							k++;
						}
					}

					String mailText = "<font color='blue' size='3'>Dzień dobry <b>"+customerList.get(i).getFirstName()+" "+customerList.get(i).getLastName()+"</b><br>"
							+ "Potwierdzamy wysłanie następujących towarów:</font><br><br>"
							+ "<table>"
							+ "<tr><th>Lp</th><th>Nazwa produktu</th><th>Ilość</th><th>Cena</th><th>Wartość</th></tr>"
							+ message
							+ "</table><br><br>"
							+ "<font size='3'>Pozdrawiamy<br>Dział Obsługi Klienta</font><br>"+mail.getMailFrom();
					idTransaction = idTransaction.substring(0, idTransaction.length()-1);
					
					em.createQuery("UPDATE Transaction SET execOrder = true, execOrderDate = '"+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"', execOrderOperator = :operator WHERE id IN ("+idTransaction+")").setParameter("operator", op).executeUpdate();				
					
					mail.sendEmail(customerList.get(i).getEmail(), mailSubject, mailText);
					ut.commit();
				} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException
						| RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					try {
						ut.rollback();
					} catch (IllegalStateException | SecurityException | SystemException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					return false;
				}
			}
					

		return true;
	}

}
