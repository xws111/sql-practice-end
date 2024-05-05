package com.xws111.sqlpractice.question;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.xws111.sqlpractice.mapper")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients("com.xws111.sqlpractice.service")
public class SqlPracticeQuestionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlPracticeQuestionServiceApplication.class, args);
    }

}
