package pl.aticode.config;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.Transaction;

import pl.aticode.config.HibernateUtil;
import pl.aticode.entity.Category;
import pl.aticode.entity.ProductOrder;
import pl.aticode.entity.Product;

public class InitApplication implements ServletContextListener  {
	
	private static Session session;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		session = HibernateUtil.getSessionFactory().openSession();
		final Transaction transaction = session.beginTransaction();
		
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
//		session.save(new Product("Dysk SSD 512GB", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas vestibulum vitae urna sit amet rutrum.", new BigDecimal("256.55"), 11, category5));
//		session.save(new Product("Dysk SSD 1TB", "In hac habitasse platea dictumst. Mauris id ex vel neque convallis rhoncus. Fusce nec varius leo. ", new BigDecimal("580.44"), 22, category5));
//		session.save(new Product("Dysk HDD 2TB", "Morbi eu aliquam nulla. Nam non lectus non metus eleifend malesuada sed ut erat. ", new BigDecimal("220.12"), 8, category4));
//		session.save(new Product("Dysk HDD 3TB", "Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. ", new BigDecimal("330.12"), 7, category4));
//		session.save(new Product("Pamięć RAM 8GB", "Curabitur commodo cursus iaculis. Aenean vitae consequat orci. ", new BigDecimal("186.88"), 13, category2));
//		session.save(new Product("Pamięć RAM 4GB", "Morbi eget purus venenatis, tempus nibh id, porttitor eros. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.", new BigDecimal("92.77"), 18, category2));
//		session.save(new Product("Płyta główna MainBoard", "Nullam enim lorem, ultrices id tellus in, imperdiet rhoncus quam. Sed iaculis vehicula purus, eget vehicula turpis maximus nec. ", new BigDecimal("273.33"), 21, category3));
//		session.save(new Product("Płyta główna GIGABIT", "Donec aliquam odio a justo dictum sollicitudin. Nunc et lectus nibh. Maecenas sit amet porta dui. Vestibulum sollicitudin eleifend mollis. ", new BigDecimal("233.33"), 31, category3));
//		session.save(new Product("Procesor INTEL DualCore", "Aenean pretium, leo nec molestie vehicula, ex tortor consectetur felis, finibus tempor velit lacus eget quam.", new BigDecimal("411.33"), 22, category1));
//		session.save(new Product("Procesor INTEL Core2Duo", "Mauris vulputate sapien non pretium egestas. Pellentesque vel massa eu leo imperdiet vulputate eu eget justo. ", new BigDecimal("522.33"), 54, category1));
//		session.save(new Product("Procesor INTEL QuadCore1", "Duis fermentum, nunc vitae bibendum vulputate, lectus nisi sollicitudin neque, ac rhoncus erat metus commodo tortor. ", new BigDecimal("800.33"), 14, category));
//		session.save(new Product("Procesor INTEL QuadCore2", "Suspendisse pharetra nisl imperdiet, consequat mi sed, pulvinar metus. Aliquam non mauris justo. ", new BigDecimal("900.33"), 3, category));
		
		transaction.commit();
		
		System.out.println("INIT CONTEXT ");
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	public static Session getSession() {
		return session;
	}
	
}
