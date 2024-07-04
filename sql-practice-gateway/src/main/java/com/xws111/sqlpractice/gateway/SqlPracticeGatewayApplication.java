package com.xws111.sqlpractice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //mybatis plus会去配置数据库
@ComponentScan("com.xws111")
@EnableDiscoveryClient
public class SqlPracticeGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlPracticeGatewayApplication.class, args);
    }

}
