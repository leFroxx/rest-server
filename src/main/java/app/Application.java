package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@SpringBootApplication
@Configuration
public class Application {
	@Autowired
	static EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public DataSource dataSource() {
		Properties props = readPropertyFile();

		BasicDataSource datasource = new BasicDataSource();
		datasource.setDriverClassName(props.getProperty("jdbc.driverClassName"));
		datasource.setUrl(props.getProperty("jdbc.url"));
		datasource.setUsername(props.getProperty("jdbc.username"));
		datasource.setPassword(props.getProperty("jdbc.password"));
		return datasource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		Properties props = readPropertyFile();
		
		LocalContainerEntityManagerFactoryBean containerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		containerEntityManagerFactoryBean.setDataSource(dataSource());
		HibernateJpaVendorAdapter adaptor = new HibernateJpaVendorAdapter();
		containerEntityManagerFactoryBean.setJpaVendorAdapter(adaptor);
		containerEntityManagerFactoryBean.setPackagesToScan("app");
		containerEntityManagerFactoryBean.setJpaProperties(props);
		return containerEntityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setDataSource(dataSource());
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
		return jpaTransactionManager;
	}

	private Properties readPropertyFile() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("src/main/resources/application.properties");
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop;
	}
}