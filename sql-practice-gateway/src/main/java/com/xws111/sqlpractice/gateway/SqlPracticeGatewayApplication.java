package com.xws111.sqlpractice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SqlPracticeGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlPracticeGatewayApplication.class, args);
    }

}
