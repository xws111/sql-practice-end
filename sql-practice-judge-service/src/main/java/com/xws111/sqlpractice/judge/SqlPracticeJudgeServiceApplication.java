package com.xws111.sqlpractice.judge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.xws111.sqlpractice.service")
public class SqlPracticeJudgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlPracticeJudgeServiceApplication.class, args);
    }

}
