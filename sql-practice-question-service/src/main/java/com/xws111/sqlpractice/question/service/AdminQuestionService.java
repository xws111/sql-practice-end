package com.xws111.sqlpractice.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.xws111.sqlpractice.model.dto.question.AdminQuestionRequest;
import com.xws111.sqlpractice.model.entity.Question;

import java.util.List;

/**
* @author xg
* @description 针对表【question(题目表)】的数据库操作Service
* @createDate 2024-05-03 22:03:56
*/
public interface AdminQuestionService extends IService<Question> {

    /**
     * 校验题目是否合法
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 分页获取题目封装
     *
     * @param questionListRequest
     * @return
     */
    PageInfo getQuestionList(AdminQuestionRequest questionListRequest, Integer current, Integer pageSize);

    //给问题添加标签
    void addQuestionTag(List<String> tags,Long afterInsertQuestionId);
    //移除问题的标签
    void removeQuestionTag(Long id);

}
