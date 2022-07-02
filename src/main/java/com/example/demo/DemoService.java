package com.example.demo;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService {

   private final JdbcTemplate jdbcTemplate;

   public DemoService(DataSource dataSource) {
      this.jdbcTemplate = new JdbcTemplate(dataSource);
   }

   @Transactional(transactionManager = "transactionManager")
   public void write() {
      // Some write operation against the primary database
      jdbcTemplate.update("CREATE TABLE foo (some_column int)");
   }

   @Transactional(transactionManager = "secondaryTransactionManager", readOnly = true)
   public void read() {
      // Some read operation against the primary database when there is an ongoing read-only transaction against the
      // secondary database.
      jdbcTemplate.query("SELECT 1", rs -> {});
   }

}
