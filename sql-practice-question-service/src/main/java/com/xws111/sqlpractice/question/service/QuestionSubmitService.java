package com.xws111.sqlpractice.question.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xws111.sqlpractice.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xws111.sqlpractice.model.entity.QuestionSubmit;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionSubmitVO;
import com.xws111.sqlpractice.model.vo.RankListVO;

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


    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 将所有的QuestionSubmit对象转为QuestionSubmitVO
     * @param questionSubmitPage
     * @param request
     * @return
     */
    List<QuestionSubmitVO> getQuestionSubmitVOList (List<QuestionSubmit> questionSubmitPage, HttpServletRequest request);

    List<RankListVO> getOverallRankList(HttpServletRequest request);
    List<RankListVO> getQuestionRankList(HttpServletRequest request, Integer id);

}
