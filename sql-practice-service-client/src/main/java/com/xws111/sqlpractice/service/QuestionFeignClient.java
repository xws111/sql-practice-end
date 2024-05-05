package com.xws111.sqlpractice.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description:
 * @Date 2024/5/3 18:34
 * @Version 1.0
 * @Author xg
 */
@FeignClient(name = "sql-practice-question-service", path = "/api/question")
public interface QuestionFeignClient {
}
