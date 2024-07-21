package com.xws111.sqlpractice.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xws111.sqlpractice.model.entity.QuestionCollection;
import com.xws111.sqlpractice.model.entity.User;

/**
 * @author wind
 * @description 针对表【question_collection(题目收藏表)】的数据库操作Service
 * @createDate 2024-07-21 14:04:06
 */
public interface QuestionCollectionService extends IService<QuestionCollection> {

    /**
     * 收藏题目
     * @param questionId
     * @param loginUser
     * @return
     */
    boolean doQuestionCollection(Long questionId, User loginUser);

    /**
     * 取消收藏
     * @param questionId
     * @param loginUser
     * @return
     */
    boolean cancelQuestionCollection(Long questionId, User loginUser);
}
