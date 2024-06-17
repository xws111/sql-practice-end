package com.xws111.sqlpractice.question.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author xg
* @description 针对表【question_submit(提交记录表)】的数据库操作Service
* @createDate 2024-05-03 22:04:06
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);


    /**
     * 获取用户某道题目的所有提交信息
     * @param request
     * @param questionId
     * @return
     */
    List<QuestionSubmit> getQuestionSubmissionByQuestionId(HttpServletRequest request, Long questionId);


}
