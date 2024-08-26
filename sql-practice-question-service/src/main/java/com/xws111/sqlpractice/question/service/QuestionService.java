package com.xws111.sqlpractice.question.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.model.dto.question.QuestionListRequest;
import com.xws111.sqlpractice.model.entity.Question;
import com.xws111.sqlpractice.model.vo.QuestionListVO;
import com.xws111.sqlpractice.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author xg
* @description 针对表【question(题目表)】的数据库操作Service
* @createDate 2024-05-03 22:03:56
*/
public interface QuestionService extends IService<Question> {

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
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionListVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

    PageInfo<QuestionListVO> getQuestionListPage(QuestionListRequest pageRequest);

    List<Integer> getQuestionACList(Long userId);

}
