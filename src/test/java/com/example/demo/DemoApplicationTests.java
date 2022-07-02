package com.example.demo;

import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

   @Autowired
   private DemoService service;

   @Test
   void test() {
      service.read();
      // The read-only flag on the connection (com.mysql.cj.jdbc.ConnectionImpl) against the primary database has not
      // been reset (i.e. it's still set to true), so the following write operation fails:
      assertThatNoException().isThrownBy(service::write);
   }

}
