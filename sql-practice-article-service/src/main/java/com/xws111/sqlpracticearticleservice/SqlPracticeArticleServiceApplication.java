package com.xws111.sqlpracticearticleservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.xws111.sqlpractice.mapper")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@EnableFeignClients("com.xws111.sqlpractice.service")
@ComponentScan("com.xws111")
public class SqlPracticeArticleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlPracticeArticleServiceApplication.class, args);
    }

}
