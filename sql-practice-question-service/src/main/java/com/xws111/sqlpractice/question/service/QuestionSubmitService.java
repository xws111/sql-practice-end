package com.xws111.sqlpractice.question.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionSubmitResultVO;
import com.xws111.sqlpractice.model.vo.QuestionSubmitVO;

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
     * 当前用户提交结果
     * @param request
     * @param questionSubmitId
     * @return
     */
    QuestionSubmitResultVO getSubmitQuestion(HttpServletRequest request, Long questionSubmitId);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);


    QueryWrapper<QuestionSubmit> getUserQueryWrapper(HttpServletRequest request);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @return
     */
    List<QuestionSubmitVO> getQuestionSubmitVOPage(List<QuestionSubmit> questionSubmitPage);

}
