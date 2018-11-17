package pl.shopapp.client;
import java.util.Hashtable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;

import pl.shopapp.beans.UserBeanRemote;
import pl.shopapp.entites.Customer;
import pl.shopapp.entites.User;

public class Main {
	
	private static UserBeanRemote ubr; 
	
	public static void main(String[] args) {
		

		Context context;
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		
//        Properties props = new Properties();  
//        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");  
//        props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");   // NOTICE: "http-remoting" and port "8080"  
//        props.put("jboss.naming.client.ejb.context", true);
//        props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        
        try {
			context = new InitialContext(env);
			ubr = (UserBeanRemote) context.lookup("ejb:ShopApp/ShopAppBeans/UserBean!pl.shopapp.beans.UserBeanRemote");
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		
//		
//		try {
//			context = new InitialContext();
//			cor = (CustomerBeanRemote) context.lookup("ejb:ShopApp/ShopAppBeans/CustomerBean!pl.shopapp.beans.CustomerBeanRemote?stateful");
//		} catch (NamingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ShopAppClient");
//        EntityManager em = emf.createEntityManager();
        
		User u = new User();
		u.setLogin("jkowalski");
		u.setPassword("jkowalski");
		u.setActive(true);
		
		Customer c = new Customer();
		c.setFirstName("Jan");
		c.setLastName("Kowalski");
		c.setCompany(false);
		c.setUser(u);
		
//		em.getTransaction().begin();
//		em.persist(u);
//		em.persist(c);
//		em.getTransaction().commit();
		
		ubr.addCustomer(c, u);
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
	}

}