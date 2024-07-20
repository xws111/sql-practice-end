package com.xws111.sqlpractice.service;

import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.JudgeInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * @Description:
 * @Date 2024/5/3 18:34
 * @Version 1.0
 * @Author xg
 */
@FeignClient(name = "sql-practice-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {
    @GetMapping("/get")
    QuestionSubmit getQuestionSubmitById(@RequestParam("id") long id);

    @GetMapping("/answer")
    String getAnswerById(@RequestParam("id") Long id);
    /**
     * 判题结束，更新提交记录状态及结果
     */
    @PostMapping("/update-result")
    void updateSubmitResult(@RequestBody JudgeInfo judgeInfo);

    @PostMapping("/increment-accepted")
    void incrementAccepted(@RequestBody Long id);

    /**
     * 根据ids拿到对应的题目数据
     * @param idList
     * @return
     */
    @GetMapping("/get/ids")
    List<Question> listByIds(@RequestParam("idList") Collection<Long> idList);


}
