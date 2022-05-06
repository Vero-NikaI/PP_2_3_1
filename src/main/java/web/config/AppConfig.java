package web.config;
import java.io.IOException;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
@EnableJpaRepositories("web.dao")
public class AppConfig {

   @Autowired
   private Environment env;
   @Bean
   public JpaVendorAdapter getAdapter() {
      return new HibernateJpaVendorAdapter();
   }

   @Bean
   public EntityManagerFactory entityManagerFactory(DataSource dataSource,@Qualifier("properties") Properties hibernateProperties) {
      final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource);
      em.setPackagesToScan("web");
      em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
      em.setJpaProperties(hibernateProperties);
      em.setPersistenceUnitName("userfull");
      em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
      em.afterPropertiesSet();

      return em.getObject();
   }
   @Bean
   public PlatformTransactionManager ptl(EntityManagerFactory entityManagerFactory) throws IOException {
      return new JpaTransactionManager(entityManagerFactory);
   }
   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
      dataSource.setUrl(env.getRequiredProperty("db.url"));
      dataSource.setUsername(env.getRequiredProperty("db.username"));
      dataSource.setPassword(env.getRequiredProperty("db.password"));
      return dataSource;
   }

   @Bean(name = "properties")
   public Properties getHibernateProperties() throws IOException {
      Properties properties = new Properties();
      properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
      properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
      properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
      return properties;
   }


}
