package pl.dentistoffice.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan(basePackages = "pl.dentistoffice")
//@PropertySource(value= {"/static/properties/prompt.properties", "/static/properties/mail.properties", "/static/properties/application.properties"}) //for Heroku server
@PropertySource(value= {"/static/properties/database.properties", "/static/properties/prompt.properties", 
						"/static/properties/mail.properties", "/static/properties/application.properties"}) //for localhost
@EnableTransactionManagement
public class RootConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(env.getProperty("url"));
		dataSource.setDriverClassName(env.getProperty("driver"));
		dataSource.setUsername(env.getProperty("db_user"));
		dataSource.setPassword(env.getProperty("db_password"));
//		if 0 then no limits
		dataSource.setMaxOpenPreparedStatements(0);
//		pool waiting to infinity: -1
		dataSource.setMaxWait(3000);
//		if 0 then no limits
		dataSource.setMaxActive(10);
		return dataSource;
	}
	
//  @Bean
//  public BasicDataSource dataSource() throws URISyntaxException {
//      URI dbUri = new URI(System.getenv("DATABASE_URL"));
//
//      String username = dbUri.getUserInfo().split(":")[0];
//      String password = dbUri.getUserInfo().split(":")[1];
//      String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
//
//      BasicDataSource basicDataSource = new BasicDataSource();
//      basicDataSource.setUrl(dbUrl);
//      basicDataSource.setUsername(username);
//      basicDataSource.setPassword(password);
//
//      return basicDataSource;
//  }
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() throws URISyntaxException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("pl.dentistoffice.entity");
		
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "false");
        hibernateProperties.setProperty("hibernate.format_sql", "false");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty("jadira.usertype.autoRegisterUserTypes", "true");
		sessionFactory.setHibernateProperties(hibernateProperties);
		
		return sessionFactory;
	}
	
    @Bean
    public PlatformTransactionManager hibernateTransactionManager() throws URISyntaxException {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

}
