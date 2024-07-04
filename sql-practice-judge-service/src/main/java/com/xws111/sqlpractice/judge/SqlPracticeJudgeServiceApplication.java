package com.xws111.sqlpractice.judge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.xws111.sqlpractice.service")
@ComponentScan("com.xws111")
@EnableDiscoveryClient
public class SqlPracticeJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlPracticeJudgeServiceApplication.class, args);
    }

}
