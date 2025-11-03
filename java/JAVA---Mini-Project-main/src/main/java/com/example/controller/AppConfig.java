package com.example.controller;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "com.example.controller")
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // IMPORTANT: Replace username/password/url for your environment.
        // Do NOT commit real credentials to GitHub — use placeholders or environment vars.
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        ds.setUsername("root");
        ds.setPassword(""); // put real password locally, or leave empty if none

        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource());

        // Entities are in this package
        factoryBean.setPackagesToScan("com.example.controller");

        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        props.put("hibernate.hbm2ddl.auto", "update"); // creates/updates tables automatically
        props.put("hibernate.show_sql", "true");

        factoryBean.setHibernateProperties(props);
        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sf) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sf);
        return txManager;
    }
}
