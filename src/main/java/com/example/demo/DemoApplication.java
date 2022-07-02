package com.example.demo;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.IsolationLevelDataSourceAdapter;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.TransactionDefinition;

@SpringBootApplication
public class DemoApplication {

   public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
   }

   @Bean
   @ConfigurationProperties("spring.datasource")
   public DataSourceProperties dataSourceProperties() {
      return new DataSourceProperties();
   }

   @Bean
   @ConfigurationProperties("spring.datasource.secondary")
   public DataSourceProperties secondaryDataSourceProperties() {
      return new DataSourceProperties();
   }

   @Bean
   public DataSource dataSource() {
      var dataSource = dataSourceProperties().initializeDataSourceBuilder().type(BasicDataSource.class).build();
      var adapter = new IsolationLevelDataSourceAdapter();
      adapter.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
      adapter.setTargetDataSource(dataSource);
      return adapter;
   }

   @Bean
   public DataSource secondaryDataSource() {
      var dataSource = secondaryDataSourceProperties().initializeDataSourceBuilder().type(BasicDataSource.class).build();
      var adapter = new IsolationLevelDataSourceAdapter();
      adapter.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
      adapter.setTargetDataSource(dataSource);
      return adapter;
   }

   @Bean
   public DataSourceTransactionManager transactionManager() {
      return new JdbcTransactionManager(dataSource());
   }

   @Bean
   public DataSourceTransactionManager secondaryTransactionManager() {
      return new JdbcTransactionManager(secondaryDataSource());
   }

}
