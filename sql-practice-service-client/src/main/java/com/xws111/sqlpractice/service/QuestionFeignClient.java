package com.xws111.sqlpractice.service;

import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.vo.JudgeInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description:
 * @Date 2024/5/3 18:34
 * @Version 1.0
 * @Author xg
 */
@FeignClient(name = "sql-practice-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {
    @GetMapping("/get")
    QuestionSubmit getQuestionSubmitById(Long id);

    @GetMapping("/answer")
    String getAnswerById(Long id);
    /**
     * 判题结束，更新提交记录状态及结果
     */
    @GetMapping("/update-result")
    void updateSubmitResult(JudgeInfo judgeInfo);
}
