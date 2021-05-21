package pl.aticode.service;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.aticode.entity.Category;
import pl.aticode.entity.ProductOrder;
import pl.aticode.entity.Product;

public class InitService implements ServletContextListener  {
	
	private static Session session;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		final Transaction transaction = session.beginTransaction();
		
//		ProductOrder ordering = new ProductOrder();
//		ordering.setDescription("order description");		
//		session.save(ordering);
		
//		Category category = new Category();
//		category.setName("Procesory quad");
//		session.save(category);
//		
//		Category category1 = new Category();
//		category1.setName("Procesory dual");
//		session.save(category1);
//		
//		Category category2 = new Category();
//		category2.setName("Pamięci RAM");
//		session.save(category2);
//		
//		Category category3 = new Category();
//		category3.setName("Płyty główne");
//		session.save(category3);
//		
//		Category category4 = new Category();
//		category4.setName("Dyski HDD");
//		session.save(category4);
//		
//		Category category5 = new Category();
//		category5.setName("Dyski SSD");
//		session.save(category5);
//		
//		session.save(new Product(null, "Dysk SSD 512GB", new BigDecimal("256.55"), 11, category5));
//		session.save(new Product(null, "Dysk SSD 1TB", new BigDecimal("580.44"), 22, category5));
//		session.save(new Product(null, "Dysk HDD 2TB", new BigDecimal("220.12"), 8, category4));
//		session.save(new Product(null, "Dysk HDD 3TB", new BigDecimal("330.12"), 7, category4));
//		session.save(new Product(null, "Pamięć RAM 8GB", new BigDecimal("186.88"), 13, category2));
//		session.save(new Product(null, "Pamięć RAM 4GB", new BigDecimal("92.77"), 18, category2));
//		session.save(new Product(null, "Płyta główna MainBoard", new BigDecimal("273.33"), 21, category3));
//		session.save(new Product(null, "Płyta główna GIGABIT", new BigDecimal("233.33"), 31, category3));
//		session.save(new Product(null, "Procesor INTEL DualCore", new BigDecimal("411.33"), 22, category1));
//		session.save(new Product(null, "Procesor INTEL Core2Duo", new BigDecimal("522.33"), 54, category1));
//		session.save(new Product(null, "Procesor INTEL QuadCore1", new BigDecimal("800.33"), 14, category));
//		session.save(new Product(null, "Procesor INTEL QuadCore2", new BigDecimal("900.33"), 3, category));
		
		transaction.commit();
		
		System.out.println("INIT CONTEXT ");
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@PostConstruct
	public void initMethod() {
		
		System.out.println("INIT POST CONSTRUCT");
		
//		session = HibernateUtil.getSessionFactory().openSession();

	}
	
	static {
		
		System.out.println("INIT STATIC");
		
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public static Session getSession() {
		return session;
	}
	
}
