package com.xws111.sqlpractice.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

/**
 * @Description:
 * @Date 2024/5/3 13:49
 * @Version 1.0
 * @Author xg
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.xws111.sqlpractice.mapper")
@EnableFeignClients(basePackages = "com.xws111.sqlpractice.service")
public class SqlPracticeUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlPracticeUserServiceApplication.class);
    }

}
