package com.xws111.sqlpractice.question.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xws111.sqlpractice.model.dto.question.QuestionListRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.vo.QuestionAllVO;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author xg
* @description 针对表【question(题目表)】的数据库操作Service
* @createDate 2024-05-03 22:03:56
*/
public interface AdminQuestionService extends IService<Question> {

    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionListRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionListRequest questionListRequest);

    /**
     * 获取题目封装
     *
     * @param id
     * @param request
     * @return
     */
    QuestionVO getQuestionVOById(Long id, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionListRequest
     * @param request
     * @return
     */
    List<QuestionAllVO> getQuestionVOPage(QuestionListRequest questionListRequest, HttpServletRequest request);

    Page<QuestionListVO> getQuestionList(long current, long size);
    //给问题添加标签
    void addQuestionTag(List<String> tags,Long afterInsertQuestionId);
    //移除问题的标签
    void removeQuestionTag(Long id);
    //查询问题的标签
    QuestionAllVO selectQuestionTag(Question question);
}
