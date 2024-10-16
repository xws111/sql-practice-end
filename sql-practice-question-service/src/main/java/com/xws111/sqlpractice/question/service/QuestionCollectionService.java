package com.xws111.sqlpractice.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xws111.sqlpractice.model.entity.QuestionCollection;
import com.xws111.sqlpractice.model.entity.User;
import com.xws111.sqlpractice.model.vo.QuestionListVO;

import java.util.List;

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

    /**
     * 获取用户收藏的题目列表
     * @param loginUser
     * @return
     */
    List<QuestionListVO> getQuestionCollectionList(User loginUser);

    /**
     * 获取题目的收藏数
     * @param questionId
     * @return
     */
    long getQuestionCollectionCount(Long questionId);
}
